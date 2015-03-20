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
 * Title: UFLD
 * </p>
 * <p>
 * Description: Unrecognized HL7 field
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
public class UFLD extends HL7DataType {
    
    public final static String UFLD_XML = "UFLD";
    
    private List<UCMP> cmp = null;
    
    /**
     * Constructs an empty UFLD
     * 
     * @param prop the HL7Properties
     **/
    public UFLD(final HL7Properties prop) {
        super(prop);
    }
    
    public static UFLD parsePiped(final HL7Parser parser, final String line, final int start, final char delim,
                                  final int stop) {
        if (stop <= start) {
            return null;
        }
        final UFLD fld = new UFLD(parser);
        fld.readPiped(parser, line, start, delim, stop);
        return fld;
    }
    
    @Override
    public void readPiped(final HL7Parser parser, final String line, int start, final char delim, final int stop) {
        final char c = parser.getSubcomponentSeparator();
        for (int i = 1;; i++) {
            final int next = getNext(line, start, delim, stop);
            if (stop < start) {
                return;
            }
            set(i, UCMP.parsePiped(parser, line, start, c, next));
            start = next + 1;
        }
    }
    
    @Override
    public void toPiped(final Writer w, final int level) throws IOException {
        final int size = Util.size(this.cmp);
        int last = 1;
        for (int i = 0; i < size; i++) {
            last = addComponent(w, this.cmp.get(i), last, i + 1, level);
        }
    }
    
    /**
     * Retrieves the desired component
     * 
     * @param i the index of the component
     * @return the value of the component
     **/
    @Override
    public UCMP get(final int i) {
        return get(this.cmp, i - 1);
    }
    
    protected static <E> E get(final List<E> list, final int i) {
        return i < 0 ? list.get(i) : Util.get(list, i);
    }
    
    public int size() {
        return Util.size(this.cmp);
    }
    
    public static int size(final UFLD fld) {
        return fld == null ? 0 : fld.size();
    }
    
    public boolean cmpEquals(final int i, final String val) {
        final UCMP cmp = get(i);
        final int size = UCMP.size(cmp);
        if (size > 1) {
            return false;
        } else if (size == 1) {
            return Util.equals(val, cmp.get(1));
        } else {
            return val == null;
        }
    }
    
    @Override
    public void set(final int i, final Object c) {
        if (c instanceof UCMP) {
            set(i, (UCMP) c);
        } else {
            final UCMP cmp = new UCMP(this.prop);
            cmp.set(1, c);
            set(i, cmp);
        }
    }
    
    /**
     * Modifies the desired component
     * 
     * @param i the index of the component
     * @param c the new value of the component
     **/
    public void set(final int i, final UCMP c) {
        this.cmp = Util.set(this.cmp, i - 1, c);
    }
    
    public void prune() {
        for (int i = (size() - 1); i >= 0; i--) {
            if (this.cmp.get(i) == null) {
                this.cmp.remove(i);
            } else {
                break;
            }
        }
    }
    
    public void removeAfter(final int index) {
        // Remove index too; List is 0-based, but argument is 1-based
        for (int i = (size() - 1); i >= index; i--) {
            this.cmp.remove(i);
        }
    }
    
    /**
     * Retrieves the first subcomponent of the first component
     * 
     * @return the first subcomponent of the first component
     */
    public String getValue() {
        return getValue(1);
    }
    
    /**
     * Retrieves the first subcomponent of the desired component
     * 
     * @param i the index of the component
     * @return the first subcomponent of the first component
     */
    public String getValue(final int i) {
        return UCMP.getValue(get(i));
    }
    
    public final static String getValue(final UFLD fld) {
        return fld == null ? null : fld.getValue();
    }
}
