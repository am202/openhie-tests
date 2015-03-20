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
package org.regenstrief.hl7.segment;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import org.regenstrief.hl7.HL7Parser;
import org.regenstrief.hl7.HL7Properties;
import org.regenstrief.hl7.SegmentReader;
import org.regenstrief.hl7.datatype.UCMP;
import org.regenstrief.hl7.datatype.UFLD;
import org.regenstrief.hl7.util.HL7ParseException;
import org.regenstrief.util.Util;

/**
 * <p>
 * Title: USEG
 * </p>
 * <p>
 * Description: Unrecognized HL7 segment This allows us to parse a piped HL7 message with an
 * unrecognized Z-segment, transform segments that we understand, and then serialize back to a piped
 * HL7 message, preserving the Z-segment that would have previously been discarded by the parser.
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: Regenstrief Institute
 * </p>
 * 
 * @author Andrew Martin
 * @version 1.0
 */
public class USEG extends HL7Segment {
    
    //private static HL7Parser parser = null;
    
    private final String name;
    
    private final HL7ParseException cause;
    
    // Outer List - fields
    // Inner List - repetitions of individual fields
    private List<List<UFLD>> fld = null;
    
    protected USEG(final HL7Properties prop) {
        this(prop, "USEG");
    }
    
    /**
     * Constructs an empty USEG
     * 
     * @param name the segment name
     * @param prop the HL7Properties
     **/
    public USEG(final HL7Properties prop, final String name) {
        this(prop, name, null);
    }
    
    /**
     * Constructs an empty USEG
     * 
     * @param name the segment name
     * @param prop the HL7Properties
     * @param cause the reason why a USEG was created rather than a more specific class
     **/
    public USEG(final HL7Properties prop, final String name, final HL7ParseException cause) {
        super(prop);
        
        this.name = name;
        this.cause = cause;
    }
    
    /**
     * Retrieves the tag name
     * 
     * @return the tag name
     **/
    @Override
    public String getTagName() {
        return this.name;
    }
    
    public static USEG parsePiped(final HL7Parser parser, final String line) {
        return parsePiped(parser, SegmentReader.getName(parser, line), line);
    }
    
    public static USEG parsePiped(final HL7Parser parser, final String name, final String line) {
        return parsePiped(parser, name, line, null);
    }
    
    public static USEG parsePiped(final HL7Parser parser, final String name, final String line, final HL7ParseException cause) {
        final USEG seg = new USEG(parser, name, cause);
        seg.readPiped(parser, line);
        return seg;
    }
    
    @Override
    public void readPiped(final HL7Parser parser, final String line) {
        final char f = parser.getFieldSeparator();
        int start = line.indexOf(f) + 1;
        if (start <= 0) {
            return;
        }
        final int first;
        final boolean msh = isMSH();
        if (msh) {
            first = 2;
            addSub(1, Character.toString(f));
        } else {
            first = 1;
        }
        int stop;
        
        final char r = parser.getRepetitionSeparator();
        final char c = parser.getComponentSeparator();
        for (int i = first;; i++) {
            stop = getNext(line, start, f);
            if (stop < start) {
                return;
            }
            if (msh && (i == 2)) {
                addSub(2, getRawToken(line, start, stop));
            } else {
                while (start < stop) {
                    final int next = getNext(line, start, r, stop);
                    addFld(i, UFLD.parsePiped(parser, line, start, c, next));
                    start = next + 1;
                }
            }
            start = stop + 1;
        }
    }
    
    @Override
    public void toPiped(final Writer w) throws IOException {
        final int size = Util.size(this.fld);
        int last = startSegment(w, this.name);
        final int first;
        if (isMSH()) {
            last = MSH.writeDelimiters(w, getSub(1, 0, 1, 1), getSub(2, 0, 1, 1), getDelimiters(), last);
            first = 3;
        } else {
            first = 1;
        }
        for (int i = first; i <= size; i++) {
            last = addField(w, this.fld.get(i - 1), last, i);
        }
    }
    
    private final boolean isMSH() {
        return MSH.MSH_XML.equals(getTagName());
    }
    
    /**
     * Retrieves the desired field
     * 
     * @param i the index of the field
     * @return the value of the field
     **/
    @Override
    public List<UFLD> get(final int i) {
        return SFLD.fget(this.fld, i - 1);
    }
    
    protected final static class SFLD extends UFLD {

        protected SFLD() {
            super(null);
        }
        
        protected final static <E> E fget(final List<E> list, final int i) {
            return get(list, i);
        }
    }
    
    public int size() {
        return Util.size(this.fld);
    }
    
    public UFLD getRep(final int fld, final int rep) {
        return Util.get(get(fld), rep);
    }
    
    public UCMP getCmp(final int fld, final int rep, final int cmp) {
        final UFLD ufld = getRep(fld, rep);
        return ufld == null ? null : ufld.get(cmp);
    }
    
    public String getSub(final int fld, final int rep, final int cmp, final int sub) {
        final UCMP ucmp = getCmp(fld, rep, cmp);
        return ucmp == null ? null : ucmp.get(sub);
    }
    
    public final static String getSub(final USEG useg, final int fld, final int rep, final int cmp, final int sub) {
        return useg == null ? null : useg.getSub(fld, rep, cmp, sub);
    }
    
    public boolean fldEquals(final int i, final String val) {
        final List<UFLD> flds = get(i);
        final int size = Util.size(flds);
        if (size > 1) {
            return false;
        } else if (size == 1) {
            final UFLD fld = flds.get(0);
            if (fld == null) {
                return val == null;
            }
            return fld.cmpEquals(1, val);
        } else {
            return val == null;
        }
    }
    
    @Override
    public void set(final int i, final Object o) {
        final List<UFLD> fld = Util.cast(o);
        set(i, fld);
    }
    
    /**
     * Modifies the desired field
     * 
     * @param i the index of the field
     * @param fld the new value of the field
     **/
    public void set(final int i, final List<UFLD> fld) {
        this.fld = Util.set(this.fld, i - 1, fld);
    }
    
    /**
     * Modifies the desired field
     * 
     * @param i the index of the field
     * @param fld the new value of the field
     **/
    public void addFld(final int i, final UFLD fld) {
        set(i, Util.add(get(i), fld));
    }
    
    private void addSub(final int i, final String sub) {
        final UFLD fld = new UFLD(this.parser);
        final UCMP cmp = new UCMP(this.parser);
        cmp.set(1, sub);
        fld.set(1, cmp);
        addFld(i, fld);
    }
    
    /**
     * Retrieves the HL7ParseException that caused a USEG to be created instead of a more specific
     * class
     * 
     * @return the HL7ParseException
     **/
    public HL7ParseException getCause() {
        return this.cause;
    }
    
    public HL7Segment lax() {
        return reparse(getParser());
    }
    
    public HL7Segment reparse() {
        return reparse(getParser(this));
    }
    
    private final HL7Segment reparse(final HL7Parser _parser) {
        final HL7Parser parser = toSafeParser(_parser);
        try {
            parser.runFromString(toPiped());
        } catch (final Exception e) {
            throw Util.toRuntimeException(e);
        }
        return parser.getTree().getDescendantValue(HL7Segment.class);
    }
    
    protected final static HL7Parser toSafeParser(final HL7Parser parser) {
        /*
         * HL7Import.runFromString won't just parse, it will try to store.
         * HL7Import with AllProcessor will throw Exception if we parse one segment without an MSH first.
         */
        final Class<?> c = parser.getClass();
        if (c == HL7Parser.class) {
            return parser;
        }
        final HL7Parser p = new HL7Parser();
        p.setConfiguration(parser);
        return p;
    }
    
    public final static USEG toUSEG(final HL7Segment segment) {
        if (segment == null) {
            return null;
        } else if (segment instanceof USEG) {
            return (USEG) segment;
        }
        return parsePiped(getParser(segment), segment.getTagName(), segment.toPiped());
    }
    
    public final static USEG toUSEG(final String name, final String segment) {
        if (Util.isEmpty(segment)) {
            return null;
        }
        return parsePiped(getParser(), name, segment);
    }
    
    public final static USEG toUSEG(final String segment) {
        // Like parsePiped, but doesn't require a parser argument
        if (Util.isEmpty(segment)) {
            return null;
        }
        return parsePiped(getParser(), segment);
    }
    
    private final static HL7Parser getParser(final HL7Segment segment) {
        // Like HL7Segment.getParser(), but the default settings for creating a new HL7Parser are different
        final HL7Properties prop = segment == null ? null : segment.getProp();
        return prop instanceof HL7Parser ? (HL7Parser) prop : getParser();
    }
    
    private final static HL7Parser getParser() {
        //if (parser == null)
        HL7Parser parser; // Avoid synchronization issues by using unique instance each time
        {
            parser = HL7Parser.createLaxParser();
        }
        return parser;
    }
}
