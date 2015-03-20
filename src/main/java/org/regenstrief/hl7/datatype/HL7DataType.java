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
package org.regenstrief.hl7.datatype;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.regenstrief.hl7.HL7Data;
import org.regenstrief.hl7.HL7Delimiters;
import org.regenstrief.hl7.HL7Parser;
import org.regenstrief.hl7.HL7Properties;
import org.regenstrief.hl7.convert.Escaper;
import org.regenstrief.hl7.util.UtilHL7;
import org.regenstrief.util.Util;

/**
 * <p>
 * Title: HL7DataType
 * </p>
 * <p>
 * Description: An HL7 data type
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
public abstract class HL7DataType extends HL7Data {
    
    /**
     * Constructs a new HL7DataType with the given HL7Properties
     * 
     * @param prop the HL7Properties
     **/
    public HL7DataType(final HL7Properties prop) {
        super(prop);
    }
    
    public final static <T extends HL7DataType> T parsePiped(final Class<T> typeClass, final HL7Parser parser,
                                                             final String line) {
        if (Util.isEmpty(line)) {
            return null;
        }
        final T t = UtilHL7.getInstance(parser, typeClass);
        t.readPiped(parser, line, 0, parser.getComponentSeparator(), line.length());
        return t;
    }
    
    public abstract void readPiped(final HL7Parser parser, final String line, int start, final char delim, final int stop);
    
    /**
     * Retrieves the piped representation of the object
     * 
     * @return the piped representation of the object
     **/
    @Override
    public final String toPiped() {
        final StringWriter w = new StringWriter();
        try {
            toPiped(w, 1);
        } catch (final IOException e) {
            throw new RuntimeException(e); // StringWriter shouldn't throw IOException
        }
        return w.toString();
        //return toPiped(toHL7XML());
    }
    
    public abstract void toPiped(final Writer w, final int level) throws IOException;
    
    protected final int addComponent(final Writer w, final Object field, final int last, final int curr, final int level)
                                                                                                                         throws IOException {
        if ((field == null) || ((level > 2) && (curr > 1))) { // Might want warning if skipped because it's too deep
            return last;
        } else if (last < curr) {
            final HL7Delimiters d = getDelimiters();
            final char delim = level == 1 ? Escaper.getComponentSeparator(d) : Escaper.getSubcomponentSeparator(d);
            for (int i = last; i < curr; i++) {
                w.write(delim);
            }
        }
        writePiped(w, field, level + 1);
        return curr;
    }
    
    /**
     * Retrieves the component at the given index
     * 
     * @param i the index
     * @return the component
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
