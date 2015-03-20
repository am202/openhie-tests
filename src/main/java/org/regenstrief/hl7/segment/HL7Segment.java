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
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import org.regenstrief.hl7.HL7Data;
import org.regenstrief.hl7.HL7Delimiters;
import org.regenstrief.hl7.HL7Parser;
import org.regenstrief.hl7.HL7Properties;
import org.regenstrief.hl7.convert.Escaper;
import org.regenstrief.hl7.util.UtilHL7;
import org.regenstrief.util.Util;

/**
 * <p>
 * Title: HL7Segment
 * </p>
 * <p>
 * Description: An HL7 segment
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
public abstract class HL7Segment extends HL7Data {
    
    /**
     * Constructs a new HL7Segment with the given HL7Properties
     * 
     * @param prop the HL7Properties
     **/
    public HL7Segment(final HL7Properties prop) {
        super(prop);
    }
    
    public static HL7Segment parsePiped(final HL7Parser parser, final String line) {
        if (Util.isEmpty(line)) {
            return null;
        }
        final int stop = line.indexOf(parser.getFieldSeparator());
        final HL7Segment d = (HL7Segment) UtilHL7.getInstance(parser, line.substring(0, stop < 0 ? line.length() : stop));
        d.readPiped(parser, line);
        return d;
    }
    
    public abstract void readPiped(final HL7Parser parser, final String line);
    
    /**
     * Retrieves the piped representation of the object
     * 
     * @return the piped representation of the object
     **/
    @Override
    public final String toPiped() {
        final StringWriter w = new StringWriter();
        try {
            toPiped(w);
        } catch (final IOException e) {
            throw new RuntimeException(e); // StringWriter shouldn't throw IOException
        }
        return w.toString();
        //return toPiped(toHL7XML());
    }
    
    public abstract void toPiped(final Writer w) throws IOException;
    
    protected final int startSegment(final Writer w, final String name) throws IOException {
        w.write(name);
        return 0;
    }
    
    protected final int addField(final Writer w, final Object field, final int last, final int curr) throws IOException {
        if (field == null) {
            return last;
        }
        final HL7Delimiters d = getDelimiters();
        final char f = Escaper.getFieldSeparator(d);
        for (int i = last; i < curr; i++) {
            w.write(f);
        }
        if (field instanceof List) {
            final List<?> list = (List<?>) field;
            final char r = Escaper.getRepetitionSeparator(d);
            boolean first = true;
            for (final Object o : list) {
                if (first) {
                    first = false;
                } else {
                    w.write(r);
                }
                writePiped(w, o, 1);
            }
        } else {
            writePiped(w, field, 1);
        }
        return curr;
    }
    
    /**
     * Performs a deep copy of this HL7Segment
     * 
     * @return the copy
     */
    public HL7Segment copy() {
        final HL7Properties prop = getProp();
        final HL7Parser parser = prop instanceof HL7Parser ? (HL7Parser) prop : new HL7Parser();
        return parsePiped(parser, toPiped());
    }
    
    protected final void endSegment(final Writer w) throws IOException {
        // line break belongs in HL7DataTree
        w.flush();
    }
    
    /**
     * Retrieves the field at the given index
     * 
     * @param i the index
     * @return the field
     **/
    @Override
    public Object get(final int i) {
        return super.get(i); // Overriding just to change visibility from protected to public
    }
    
    /**
     * Modifies the field at the given index
     *
     * @param o the field
     * @param i the index
     **/
    @Override
    public void set(final int i, final Object o) {
        super.set(i, o); // Overriding just to change visibility from protected to public
    }
}
