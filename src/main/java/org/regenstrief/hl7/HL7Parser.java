/**
 * The contents of this file are subject to the Regenstrief Public License
 * Version 1.0 (the "License"); you may not use this file except in compliance with the License.
 * Please contact Regenstrief Institute if you would like to obtain a copy of the license.
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) Regenstrief Institute.  All Rights Reserved.
 */
package org.regenstrief.hl7;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.regenstrief.hl7.convert.Escaper;
import org.regenstrief.hl7.group.HL7Message;
import org.regenstrief.hl7.group.UMSG_Z01;
import org.regenstrief.hl7.segment.HL7Segment;
import org.regenstrief.hl7.segment.USEG;
import org.regenstrief.hl7.transform.SegmentFilter;
import org.regenstrief.hl7.util.BaseData;
import org.regenstrief.hl7.util.HL7Exception;
import org.regenstrief.hl7.util.UtilHL7;
import org.regenstrief.io.IoUtil;
import org.regenstrief.util.Util;
import org.regenstrief.util.reflect.ReflectUtil;
import org.xml.sax.Attributes;

/**
 * Title: HL7 Parser Description: Parses HL7 XML data from an HL7 message or just a segment.
 * Copyright: Copyright (c) 2005 Company: Regenstrief Institute
 * 
 * @author Andrew Martin
 * @version 1.0
 **/
public class HL7Parser implements HL7Properties, HL7Delimiters {
    
    public final static String PROP_VERSION = "org.regenstrief.hl7.Version";
    
    public final static String PROP_FILTERS = "org.regenstrief.hl7.SegmentFilters";
    
    public final static String PROP_ALLOW_COMPLEX = "org.regenstrief.hl7.AllowComplex";
    
    public final static String PROP_IGNORE_EXTRA = "org.regenstrief.hl7.ignoreExtra";
    
    public final static boolean DEFAULT_LAX_UNDERSTANDING = false;
    
    protected final static int MARK_LIMIT = 128;
    
    protected final static Class<?>[] HL7_DATA_CONSTRUCTOR_PARAMETERS = { HL7Properties.class };
    
    protected HL7DataTree tree = null;
    
    protected String root = null, defaultMessageType = "ORU_R01";
    
    protected final MessageProperties mp = new MessageProperties();
    
    protected final ParseProperties parseProperties = new ParseProperties();
    
    protected boolean allowComplex = false;
    
    protected boolean ignoreExtra = false;
    
    protected boolean laxUnderstanding = DEFAULT_LAX_UNDERSTANDING;
    
    protected Escaper delimiters = new Escaper();
    
    public HL7DataTree currNode = null;
    
    /**
     * Constructs a new HL7Parser
     **/
    public HL7Parser() {
        this.allowComplex = Util.isProperty(PROP_ALLOW_COMPLEX, this.allowComplex);
        this.ignoreExtra = Util.isProperty(PROP_IGNORE_EXTRA, this.ignoreExtra);
    }
    
    public final static HL7Parser createLaxParser() {
        return create(false);
    }
    
    public final static HL7Parser createStrictParser() {
        return create(true);
    }
    
    public final static HL7Parser create(final boolean strict) {
        final HL7Parser parser = new HL7Parser();
        configure(parser, !strict);
        return parser;
    }
    
    public final static void configureLax(final HL7Parser parser) {
        configure(parser, true);
    }
    
    public final static void configureStrict(final HL7Parser parser) {
        configure(parser, false);
    }
    
    private final static void configure(final HL7Parser parser, final boolean lax) {
        parser.setAllowComplex(lax);
        parser.setIgnoreExtra(lax);
        parser.setLaxUnderstanding(lax);
        final ParseProperties pp = parser.getParseProperties();
        pp.setFlatXML(true);
        pp.setSilent(lax);
        pp.setStrict(!lax);
    }
    
    public final void setConfiguration(final HL7Parser src) {
        setAllowComplex(src.getAllowComplex());
        setIgnoreExtra(src.getIgnoreExtra());
        setLaxUnderstanding(src.getLaxUnderstanding());
        setDefaultMessageType(src.getDefaultMessageType());
        getParseProperties().load(src.getParseProperties());
    }
    
    /**
     * Parses an HL7 message
     * 
     * @param in the Reader of the HL7 message
     * @throws Exception if a problem occurs during parsing
     **/
    public void run(final Reader in) throws Exception {
        this.preparsed = true;
        final BufferedReader b = Util.getBufferedReader(in);
        
        runPiped(b);
    }
    
    /**
     * Parses an HL7 message
     * 
     * @param in the InputStream of the HL7 message
     * @throws Exception if a problem occurs during parsing
     **/
    public void run(final InputStream in) throws Exception {
        this.preparsed = true;
        final BufferedInputStream b = Util.getBufferedInputStream(in);
        
        runPiped(new InputStreamReader(b));
    }
    
    protected boolean preparsed = false; // Probably temporary
    
    /**
     * Parses a message
     * 
     * @param in the HL7DataTree of the HL7 message
     * @throws Exception if a problem occurs during parsing
     **/
    public void run(final HL7DataTree in) throws Exception {
        this.preparsed = true;
        this.currNode = this.tree = in;
        this.root = this.currNode.getValue().getTagName();
        runFromHL7DataTree();
    }
    
    /**
     * Parses a message
     * 
     * @throws Exception if a problem occurs during parsing
     **/
    protected void runFromHL7DataTree() throws Exception {
        final String name = this.currNode.getValue().getTagName();
        processStartElement(name);
        final HL7DataTreeIterator iter = this.currNode.getChildrenIterator();
        HL7DataTree t;
        while ((t = iter.next()) != null) {
            this.currNode = t;
            runFromHL7DataTree();
            this.currNode = this.currNode.getParent();
        }
        processEndElement(name);
    }
    
    protected final Stack<HL7Data> stack = new Stack<HL7Data>();
    
    private final void runPiped(final Reader in) throws Exception {
        this.currNode = this.tree = new HL7DataTree(newDefaultMessage());
        final String messageName = getDefaultMessageType();
        processStartElement(messageName);
        final SegmentReader b = getSegmentReader(in);
        HL7Segment data;
        this.stack.clear();
        while ((data = b.readSegment()) != null) {
            this.currNode = this.tree.addChild(data);
            final String name = data.getTagName();
            processStartElement(name);
            processEndElement(name);
            this.stack.clear();
        }
        this.currNode = this.tree;
        processEndElement(messageName);
    }
    
    public SegmentReader getSegmentReader(final Reader in) {
        SegmentReader b = new SegmentReader(this, in);
        for (final SegmentFilter f : getFilters()) {
            f.setParent(b);
            b = f;
        }
        return b;
    }
    
    protected List<? extends SegmentFilter> filters = null;
    
    public void setFilters(final List<? extends SegmentFilter> filters) {
        this.filters = filters;
    }
    
    public List<? extends SegmentFilter> getFilters() {
        if (this.filters == null) {
            final String[] classNames = Util.splitExact(Util.getProperty(PROP_FILTERS), ';');
            final int size = Util.length(classNames);
            final List<SegmentFilter> filters = new ArrayList<SegmentFilter>(size);
            for (int i = 0; i < size; i++) {
                final SegmentFilter filter = Util.cast(ReflectUtil.newInstance(classNames[i]));
                filters.add(filter);
            }
            this.filters = filters;
        }
        return this.filters;
    }
    
    /**
     * Retrieves the parsed HL7DataTree
     * 
     * @return the parsed HL7DataTree
     **/
    public HL7DataTree getTree() {
        return this.tree;
    }
    
    /**
     * Retrieves whether the given Reader contains XML data
     * 
     * @param in the Reader
     * @return whether the given Reader contains XML data
     * @throws IOException if an I/O problem occurs
     **/
    public static boolean isXML(final Reader in) throws IOException {
        int c;
        
        in.mark(MARK_LIMIT);
        while ((c = in.read()) != -1) {
            if (!Character.isWhitespace((char) c)) {
                break;
            }
        }
        in.reset();
        
        return c == '<';
    }
    
    /**
     * Retrieves whether the given InputStream contains XML data
     * 
     * @param in the InputStream
     * @return whether the given InputStream contains XML data
     * @throws IOException if an I/O problem occurs
     **/
    public static boolean isXML(final InputStream in) throws IOException {
        int c;
        
        in.mark(MARK_LIMIT);
        while ((c = in.read()) != -1) {
            if (!Character.isWhitespace((char) c)) {
                break;
            }
        }
        in.reset();
        
        return c == '<';
    }
    
    /**
     * Does processing not related to parsing
     * 
     * @param tagName the XML tag name
     * @throws HL7Exception if a problem occurs
     **/
    protected void processStartElement(final String tagName) throws HL7Exception {
    }
    
    /**
     * Starts processing the root
     * 
     * @param tagName the XML tag name
     * @param attrs the XML Attributes
     * @return whether the tag was a root tag
     **/
    public boolean startRoot(final String tagName, final Attributes attrs) {
        HL7Data data = getInstance(tagName);
        
        // Are there other places where we wouldn't want a USEG?
        // Should there be two getInstance methods?
        if ((data == null) || (data instanceof USEG)) {
            data = newDefaultMessage();
        }
        this.tree = this.currNode = new HL7DataTree(data);
        this.root = tagName;
        
        return true;
    }
    
    /**
     * Does processing not related to parsing
     * 
     * @param tagName the XML tag name
     * @throws HL7Exception if a problem occurs
     **/
    protected void processEndElement(final String tagName) throws HL7Exception {
    }
    
    /**
     * Ends processing the root
     * 
     * @param tagName the XML tag name
     * @return whether the tag was a root tag
     **/
    public boolean endRoot(final String tagName) {
        if (this.root.equals(tagName)) {
            this.currNode = null;
            return true;
        }
        
        return false;
    }
    
    /**
     * Retrieves the message's properties
     * 
     * @return the message's properties
     **/
    @Override
    public MessageProperties getMessageProp() {
        return this.mp;
    }
    
    /**
     * Retrieves an instance Object of the desired class
     * 
     * @param valueType the desired class
     * @return the instance Object
     **/
    public HL7Data getInstance(final String valueType) {
        HL7Data data = UtilHL7.getInstance(this, valueType);
        
        if ((data == null) && !isStrict()) {
            // Should we check currNode to make sure we're ready for a new segment, not in the middle of another segment?
            if (!Util.contains(valueType, '.')) {
                data = new USEG(this, valueType);
            }
        }
        if (data == null) {
            this.parseProperties.errUnrecognizedContent(valueType);
        }
        
        return data;
    }
    
    /**
     * Retrieves the ParseProperties
     * 
     * @return the ParseProperties
     **/
    public ParseProperties getParseProperties() {
        return this.parseProperties;
    }
    
    /**
     * Retrieves whether processing is strict (can't be if using flat XML)
     * 
     * @return whether processing is strict
     **/
    public boolean isStrict() {
        return this.parseProperties.isStrict() && !this.parseProperties.isFlatXML();
    }
    
    public String getDefaultMessageType() {
        return this.defaultMessageType;
    }
    
    public HL7Message newDefaultMessage() {
        final String messageType = getDefaultMessageType();
        if (messageType == null) {
            return new UMSG_Z01(this);
        } else {
            final HL7Data data = getInstance(formatMessageStructureDefaultSeparator(messageType));
            return data instanceof HL7Message ? (HL7Message) data : new UMSG_Z01(this, messageType);
        }
    }
    
    public static final String formatMessageStructureDefaultSeparator(final String messageStructure) {
        return messageStructure.replace(Escaper.DEFAULT_COMPONENT_SEPARATOR, '_');
    }
    
    /**
     * Modifies the default message type
     * 
     * @param defaultMessageType the new default message type
     **/
    public void setDefaultMessageType(final String defaultMessageType) {
        this.defaultMessageType = defaultMessageType;
    }
    
    /**
     * Creates a group HL7Data instance
     * 
     * @param name the group name
     * @return the HL7Data
     **/
    public HL7Data createGroup(final String name) {
        try {
            return (HL7Data) Class.forName("org.regenstrief.hl7.group." + name).getConstructor(
                HL7_DATA_CONSTRUCTOR_PARAMETERS).newInstance(new Object[] { this });
        } catch (final Exception e) {
            try {
                return (HL7Data) Class.forName("org.regenstrief.hl7.ext.group." + name).getConstructor(
                    HL7_DATA_CONSTRUCTOR_PARAMETERS).newInstance(new Object[] { this });
            } catch (final Exception e2) {
                throw Util.toRuntimeException(e);
            }
        }
    }
    
    public <T extends HL7Data> T push(final T value) {
        this.currNode = this.currNode.addChild(value);
        return value;
    }
    
    public void pop() {
        this.currNode = this.currNode.getParent();
        this.currNode.removeLastChild();
    }
    
    /**
     * Prepares the parser to be used again for a new message
     **/
    public void reset() {
        this.tree = null;
        this.root = null;
        this.currNode = null;
    }
    
    @Override
    public final char getFieldSeparator() {
        return this.delimiters.getFieldSeparator();
    }
    
    @Override
    public final char getComponentSeparator() {
        return this.delimiters.getComponentSeparator();
    }
    
    @Override
    public final char getRepetitionSeparator() {
        return this.delimiters.getRepetitionSeparator();
    }
    
    @Override
    public final char getEscapeCharacter() {
        return this.delimiters.getEscapeCharacter();
    }
    
    @Override
    public final char getSubcomponentSeparator() {
        return this.delimiters.getSubcomponentSeparator();
    }
    
    /**
     * Modifies the field separator
     * 
     * @param fieldSeparator the new char field separator
     **/
    @Override
    public final void setFieldSeparator(final char fieldSeparator) {
        this.delimiters.setFieldSeparator(fieldSeparator);
    }
    
    /**
     * Modifies the component separator
     * 
     * @param componentSeparator the new char component separator
     **/
    @Override
    public final void setComponentSeparator(final char componentSeparator) {
        this.delimiters.setComponentSeparator(componentSeparator);
    }
    
    /**
     * Modifies the subcomponent separator
     * 
     * @param subcomponentSeparator the new char subcomponent separator
     **/
    @Override
    public final void setSubcomponentSeparator(final char subcomponentSeparator) {
        this.delimiters.setSubcomponentSeparator(subcomponentSeparator);
    }
    
    /**
     * Modifies the repetition separator
     * 
     * @param repetitionSeparator the new char repetition separator
     **/
    @Override
    public final void setRepetitionSeparator(final char repetitionSeparator) {
        this.delimiters.setRepetitionSeparator(repetitionSeparator);
    }
    
    /**
     * Modifies the escape character
     * 
     * @param escapeCharacter the new char escape character
     **/
    @Override
    public final void setEscapeCharacter(final char escapeCharacter) {
        this.delimiters.setEscapeCharacter(escapeCharacter);
    }
    
    //TODO Harmonize these methods with ParseProperties
    public final void setAllowComplex(final boolean allowComplex) {
        this.allowComplex = allowComplex;
    }
    
    /**
     * Retrieves whether we allow complex data where primitives are expected instead of throwing an
     * Exception
     * 
     * @return whether we allow complex data where primitives are expected
     **/
    public final boolean getAllowComplex() {
        return this.allowComplex;
    }
    
    public final void setIgnoreExtra(final boolean ignoreExtra) {
        this.ignoreExtra = ignoreExtra;
    }
    
    /**
     * Retrieves whether we ignore extra fields/components/repetitions/etc. instead of throwing an
     * Exception
     * 
     * @return whether we ignore extra fields/components/repetitions/etc.
     **/
    public final boolean getIgnoreExtra() {
        return this.ignoreExtra;
    }
    
    public final void setLaxUnderstanding(final boolean laxUnderstanding) {
        this.laxUnderstanding = laxUnderstanding;
    }
    
    // Might not belong in HL7Parser.  We don't care about this until we convert to java.util.Date, int, etc.
    /**
     * Retrieves whether we ignore data that can be parsed into HL7 but not understood (text in a
     * numeric or date field would be treated as null) instead of throwing an Exception
     * 
     * @return whether we ignore data that can be parsed into HL7 but not understood
     **/
    public final boolean getLaxUnderstanding() {
        return this.laxUnderstanding;
    }
    
    public final static boolean getLaxUnderstanding(final HL7Parser parser) {
        return parser == null ? DEFAULT_LAX_UNDERSTANDING : parser.laxUnderstanding;
    }
    
    public final static boolean getLaxUnderstanding(final HL7Properties prop) {
        return prop instanceof HL7Parser ? getLaxUnderstanding((HL7Parser) prop) : DEFAULT_LAX_UNDERSTANDING;
    }
    
    public final static boolean getLaxUnderstanding(final BaseData data) {
        return data == null ? DEFAULT_LAX_UNDERSTANDING : getLaxUnderstanding(data.getProp());
    }
    
    public void runFromString(final String in) throws Exception {
        final StringReader r = new StringReader(in);
        try {
            run(r);
        } finally {
            r.close();
        }
    }
    
    public void runFromLocation(final String in) throws Exception {
        final InputStream s = Util.getStream(in);
        try {
            run(s);
        } finally {
            IoUtil.close(s);
        }
    }
}
