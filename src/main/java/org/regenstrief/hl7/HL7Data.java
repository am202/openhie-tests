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

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.regenstrief.hl7.convert.Escaper;
import org.regenstrief.hl7.datatype.HL7DataType;
import org.regenstrief.hl7.segment.HL7Segment;
import org.regenstrief.hl7.segment.MSH;
import org.regenstrief.hl7.util.BaseData;
import org.regenstrief.hl7.util.HL7ParseException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.regenstrief.util.Util;
import org.regenstrief.util.reflect.ReflectUtil;

/**
 * <p>
 * Title: HL7Data
 * </p>
 * <p>
 * Description: A piece of data from an HL7 message
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Regenstrief Institute
 * </p>
 * 
 * @author Andrew Martin
 * @version 1.0
 */
public abstract class HL7Data implements BaseData {
    
    private static final Log log = LogFactory.getLog(HL7Data.class);
    
    private final static Escaper defaultEscaper = new Escaper();
    
    protected final HL7Properties prop;
    
    protected final HL7Parser parser;
    
    protected HL7Data transformSource = null;
    
    protected String currTag = null;
    
    protected String delivString = null;
    
    /**
     * Constructs a new HL7Data with the given HL7Properties
     * 
     * @param prop the HL7Properties
     **/
    public HL7Data(final HL7Properties prop) {
        this.prop = prop;
        this.parser = prop instanceof HL7Parser ? (HL7Parser) prop : null;
    }
    
    /**
     * Retrieves the tag name
     * 
     * @return the tag name
     **/
    public String getTagName() {
        return getClassName();
    }
    
    protected final String getClassName() {
        return Util.getClassName(getClass());
    }
    
    protected final Integer getTokenInteger(final String line, final int start, final int stop) {
        final String token = getToken(line, start, stop);
        return token == null ? null : new Integer(token);
    }
    
    /*protected final static DT getTokenDT(final HL7Parser parser, final String line, final int start, final int stop)
    {
    	final String token = getToken(line, start, stop);
    	return token == null ? null : new DT(parser, token);
    }

    protected final static NM getTokenNM(final HL7Parser parser, final String line, final int start, final int stop)
    {	return NM.create(parser, getToken(line, start, stop));
    }*/

    protected final String getRawToken(final String line, final int start, final int stop) {
        return start == stop ? null : line.substring(start, stop);
    }
    
    protected final String getToken(final String line, final int start, final int stop) {
        final String token = getRawToken(line, start, stop);
        if (token == null) {
            return null;
        }
        
        /*
        Manual - 2.5
        Section - 2.5.3 Fields
        Sending the null value, which is transmitted as two double quote marks (""), is different from omitting an optional
        data field. The difference appears when the contents of a message will be used to update a record in a database
        rather than create a new one. If no value is sent, (i.e., it is omitted) the old value should remain unchanged.
        If the null value is sent, the old value should be changed to null.
        */
        /*
        So we definitely don't want to parse and store "" as is.
        Returning null here conforms to the standard if the message is an insert.
        It also conforms for an update of a field that was already null.
        For an update of a field that previously had a value,
        we would violate the standard and preserve the old value.
        */
        if ("\"\"".equals(token)) {
            return null;
        }
        
        if ((this.parser != null) && !this.parser.allowComplex) {
            assertNotContains(line, token, this.parser.getFieldSeparator());
            assertNotContains(line, token, this.parser.getComponentSeparator());
            assertNotContains(line, token, this.parser.getSubcomponentSeparator());
            assertNotContains(line, token, this.parser.getRepetitionSeparator());
        }
        return getEscaper().unescape(token);
    }
    
    private final Escaper getEscaper() {
        return Util.nvl(this.parser == null ? null : this.parser.delimiters, defaultEscaper);
    }
    
    protected final HL7Delimiters getDelimiters() {
        return this.parser;
    }
    
    private final void assertNotContains(final String line, final String token, final char sep) {
        if (Util.contains(token, sep)) {
            final String loc;
            if (this.parseIndex == 0) {
                // In a primitive, use parent
                final HL7Data parent = this.parser.stack.peek();
                loc = parent.getTagName() + "." + parent.getParseIndex();
            } else {
                loc = getTagName() + "." + getParseIndex();
            }
            throw new HL7ParseException("Unexpected character " + token + " (" + sep + ") found in " + loc + " of:"
                    + Util.getLineSeparator() + line);
        }
    }
    
    private int parseIndex = 0;
    
    private final int getParseIndex() {
        return MSH.MSH_XML.equals(getTagName()) ? this.parseIndex + 1 : this.parseIndex;
    }
    
    protected final void assertLast(final String line, final int start, final char delim, final int stop) {
        if (this.parser != null) {
            this.parser.stack.pop();
        }
        if ((this.parser != null) && this.parser.ignoreExtra) {
            return;
        }
        if (start < stop) {
            for (int i = start; i < stop; i++) {
                if (line.charAt(i) != delim) {
                    throw new HL7ParseException("Extra unrecognized content " + line.substring(start, stop)
                            + " found in " + getTagName() + "." + (getParseIndex() + 1) + " at position " + start + " of:" + Util.getLineSeparator() + line);
                }
            }
        }
    }
    
    private final void push() {
        if ((this.parser != null) && (this.parser.stack.empty() || (this.parser.stack.peek() != this))) {
            this.parser.stack.push(this);
        }
    }
    
    protected final int getNext(final String line, final int start, final char delim) {
        push();
        this.parseIndex++;
        final int next = line.indexOf(delim, start);
        if (next < 0) {
            int last = line.length() - 1;
            while ((last >= start) && isBreak(line.charAt(last))) {
                last--;
            }
            return last + 1;
        }
        return next;
    }
    
    private final static boolean isBreak(final char c) {
        return (c == '\r') || (c == '\n');
    }
    
    protected final int getNext(final String line, final int start, final char delim, final int stop) {
        push();
        if (!(this instanceof HL7Segment)) {
            this.parseIndex++;
        }
        for (int i = start; i < stop; i++) {
            if (line.charAt(i) == delim) {
                return i;
            }
        }
        return stop;
    }
    
    protected Object parseVaries(final HL7Parser parser, final String line, final int start, final char delim, final int stop, final String valueType) {
        if (Util.in(valueType, "ID", "IS", "FT", "ST", "TX")) {
            return getToken(line, start, stop);
        } else {
            final HL7DataType value = (HL7DataType) parser.getInstance(valueType);
            value.readPiped(parser, line, start, delim, stop);
            return value;
        }
    }
    
    /**
     * Retrieves the piped representation of the object
     * 
     * @return the piped representation of the object
     **/
    @Override
    public abstract String toPiped();
    
    protected final void writePiped(final Writer w, final Object o, final int level) throws IOException {
        if (o instanceof HL7DataType) {
            ((HL7DataType) o).toPiped(w, level);
        } else {
            writePiped(w, Util.toString(o));
        }
    }
    
    protected final void writePiped(final Writer w, final String s) throws IOException {
        if (s != null) {
            getEscaper().escape(w, s);
        }
    }
    
    protected final void writePipedPrimitive(final Writer w) throws IOException {
        writePiped(w, toDisplay());
    }
    
    /**
     * Retrieves the display representation of the object
     * 
     * @return the display representation of the object
     **/
    @Override
    public String toDisplay() {
        return super.toString();
    }
    
    public final static String toDisplay(final BaseData data) {
        return data == null ? null : data.toDisplay();
    }
    
    /**
     * Retrieves the String representation of the object
     * 
     * @return the String representation of the object
     **/
    @Override
    public final String toString() {
        //throw new Error("HL7Data.toString() invoked");
        // Could add a system property to indicate if toPiped(), toXml(), or toDisplay() should be returned by toString();
        // should handle HL7DataTree.toString same way
        //return toXml();
        return toPiped();
    }
    
    public final static String toString(final Object data) {
        return (data instanceof BaseData) ? ((BaseData) data).toDisplay() : Util.toString(data);
    }
    
    // Should we use generics so that addRequired returns something more specific for subclasses?
    // That could be a problem if we implement multiple interfaces (CWE 2.6 implementing CWE 2.5 and CE 2.6)
    // Maybe it just shouldn't return this
    /**
     * Adds missing required fields
     * 
     * @return the HL7Data with all required fields
     **/
    public HL7Data addRequired() {
        return this;
    }
    
    /**
     * Retrieves the HL7Properties
     * 
     * @return the HL7Properties
     **/
    @Override
    public HL7Properties getProp() {
        return this.prop;
    }
    
    /**
     * Retrieves the delivered String
     * 
     * @return the delivered String
     **/
    public String getDelivString() {
        return this.delivString;
    }
    
    /**
     * Modifies the delivered String
     * 
     * @param delivString the new delivered String
     **/
    public void setDelivString(final String delivString) {
        this.delivString = delivString;
    }
    
    public HL7Data getTransformSource() {
        return this.transformSource;
    }
    
    public void setTransformSource(final HL7Data transformSource) {
        this.transformSource = transformSource;
    }
    
    private final static Map<Class<? extends HL7Data>, List<Field>> fields = new HashMap<Class<? extends HL7Data>, List<Field>>();
    
    protected Object get(final int i) {
        // Can't return HL7DataType, since some fields are String and some are List.
        // Not public, because inappropriate for HL7Group
        /*
        TODO
        Decide if we should allow extra unrecognized fields to be stored.
        */
        try {
            return getField(i).get(this);
        } catch (final IllegalAccessException e) {
            throw Util.toRuntimeException(e);
        }
    }
    
    protected void set(final int i, final Object o) {
        try {
            getField(i).set(this, o);
        } catch (final IllegalAccessException e) {
            throw Util.toRuntimeException(e);
        }
    }
    
    private final Field getField(final int i) {
        initFields();
        final Field f = fields.get(getClass()).get(i - 1);
        if (f == null) {
            throw new IllegalStateException("Could not find field " + i);
        }
        return f;
    }
    
    private final void initFields() {
        final Class<? extends HL7Data> c = getClass();
        if (fields.get(c) != null) {
            return;
        }
        final List<Field> all = ReflectUtil.getFieldList(c);
        final String pre = c.getSimpleName() + '.';
        final int istart = pre.length();
        final List<String> fnames = new ArrayList<String>();
        for (final Field f : all) {
            final int m = f.getModifiers();
            if (!Modifier.isFinal(m) || !Modifier.isStatic(m) || !Modifier.isPublic(m)) {
                continue;
            }
            final String fname = f.getName();
            if (!fname.endsWith("_XML") || !f.getType().equals(String.class)) {
                continue;
            }
            final String fval;
            try {
                fval = (String) f.get(null);
            } catch (final Exception e) {
                throw Util.toRuntimeException(e);
            }
            if (fval.startsWith(pre)) {
                final int fend = fval.length();
                if (Util.isAllDigits(fval, istart, fend)) {
                    Util.set(fnames, Util.parseInt(fval, istart, fend) - 1, frm(fname.substring(0, fname.length() - 4)));
                }
            }
        }
        final int numFields = fnames.size();
        final List<Field> fields = new ArrayList<Field>(numFields);
        for (final Field f : all) {
            final int m = f.getModifiers();
            // Fields will be private if defined in this class or protected if defined in super class
            if (Modifier.isFinal(m) || Modifier.isStatic(m) || Modifier.isPublic(m)) {
                continue;
            }
            final int i = fnames.indexOf(frm(f.getName()));
            if (i < 0) {
                continue;
            }
            f.setAccessible(true);
            Util.set(fields, i, f);
        }
        for (int i = 0; i < numFields; i++) {
            if (fields.get(i) == null) {
                final String msg = "Error initializing field " + (i + 1) + " (" + fnames.get(i) + ")";
                //throw new IllegalStateException(msg);
                log.warn(msg);
            }
        }
        HL7Data.fields.put(c, fields);
    }
    
    private final static String frm(final String s) {
        final StringBuilder b = new StringBuilder();
        final int size = s.length();
        for (int i = 0; i < size; i++) {
            final char c = Character.toLowerCase(s.charAt(i));
            if ((c >= 'a') && (c <= 'z')) {
                b.append(c);
            }
        }
        return b.toString();
    }
    
    /**
     * Clears the HL7Data
     **/
    @Override
    public void clear() {
        this.delivString = null;
        this.currTag = null;
    }
    
    public static String toPiped(final BaseData data) {
        return data == null ? null : data.toPiped();
    }
}
