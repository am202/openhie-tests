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
import java.io.Writer;
import java.util.List;

import org.regenstrief.hl7.HL7Parser;
import org.regenstrief.hl7.HL7Properties;
import org.regenstrief.util.Util;

/**
 * <p>
 * Title: UCMP
 * </p>
 * <p>
 * Description: Unrecognized HL7 component
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
public class UCMP extends HL7DataType {
    
    public final static String UCMP_XML = "UCMP";
    
    private List<String> sub = null;
    
    /**
     * Constructs an empty UCMP
     * 
     * @param prop the HL7Properties
     **/
    public UCMP(final HL7Properties prop) {
        super(prop);
    }
    
    public static UCMP parsePiped(final HL7Parser parser, final String line, final int start, final char delim,
                                  final int stop) {
        if (stop <= start) {
            return null;
        }
        final UCMP cmp = new UCMP(parser);
        cmp.readPiped(parser, line, start, delim, stop);
        return cmp;
    }
    
    @Override
    public void readPiped(final HL7Parser parser, final String line, int start, final char delim, final int stop) {
        for (int i = 1;; i++) {
            final int next = getNext(line, start, delim, stop);
            if (stop < start) {
                return;
            }
            set(i, getToken(line, start, next));
            start = next + 1;
        }
    }
    
    @Override
    public void toPiped(final Writer w, final int level) throws IOException {
        final int size = Util.size(this.sub);
        int last = 1;
        for (int i = 0; i < size; i++) {
            last = addComponent(w, this.sub.get(i), last, i + 1, level);
        }
    }
    
    /**
     * Retrieves the desired subcomponent
     * 
     * @param i the index of the subcomponent
     * @return the value of the subcomponent
     **/
    @Override
    public String get(final int i) {
        return UFLD.get(this.sub, i - 1);
    }
    
    public int size() {
        return Util.size(this.sub);
    }
    
    public static int size(final UCMP cmp) {
        return cmp == null ? 0 : cmp.size();
    }
    
    @Override
    public void set(final int i, final Object o) {
        set(i, (String) o);
    }
    
    /**
     * Modifies the desired subcomponent
     * 
     * @param i the index of the subcomponent
     * @param s the new value of the subcomponent
     **/
    public void set(final int i, final String s) {
        this.sub = Util.set(this.sub, i - 1, s);
    }
    
    /**
     * Retrieves the first subcomponent
     * 
     * @return the first subcomponent
     */
    public String getValue() {
        return get(1);
    }
    
    public final static String getValue(final UCMP cmp) {
        return cmp == null ? null : cmp.getValue();
    }
}
