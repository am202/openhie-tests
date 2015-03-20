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

import org.regenstrief.hl7.HL7Parser;
import org.regenstrief.hl7.HL7Properties;
import org.regenstrief.hl7.util.UtilHL7;
import org.regenstrief.util.Util;

public class SI extends HL7DataType {
    
    public final static String SI_XML = "SI";
    
    private String si = null;
    
    private Integer i = null;
    
    private SI(final HL7Properties prop) {
        super(prop);
    }
    
    /**
     * Constructs a new SI with from the given numeric String
     * 
     * @param prop the HL7Properties
     * @param si the numeric String
     **/
    private SI(final HL7Properties prop, final String si) {
        super(prop);
        this.si = si;
    }
    
    /**
     * Constructs a new SI with from the given numeric String
     * 
     * @param prop the HL7Properties
     * @param si the numeric String
     * @return the SI
     **/
    public final static SI create(final HL7Properties prop, final String si) {
        return Util.isEmpty(si) ? null : new SI(prop, si); // prop could define whether non-numeric Strings should be treated as null or throw an exception
    }
    
    /**
     * Constructs a new SI with from the given Integer
     * 
     * @param prop the HL7Properties
     * @param i the Integer
     **/
    private SI(final HL7Properties prop, final Integer i) {
        this(prop, UtilHL7.toString(i));
        this.i = i;
    }
    
    /**
     * Constructs a new SI with from the given Integer
     * 
     * @param prop the HL7Properties
     * @param i the Integer
     * @return the SI
     **/
    public final static SI create(final HL7Properties prop, final Integer i) {
        return i == null ? null : new SI(prop, i);
    }
    
    public final static SI create(final HL7Properties prop, final Number n) {
        return n == null ? null : n instanceof Integer ? create(prop, (Integer) n) : new SI(prop, n.intValue());
    }
    
    /**
     * Constructs a new SI with from the given int
     * 
     * @param prop the HL7Properties
     * @param i the int
     **/
    public SI(final HL7Properties prop, final int i) {
        this(prop, new Integer(i));
    }
    
    /**
     * Converts the SI to a Integer
     * 
     * @return the SI as a Integer
     **/
    public Integer toInteger() {
        if ((this.i == null) && (this.si != null)) {
            try {
                this.i = new Integer(this.si);
            } catch (final NumberFormatException e) {
                if (!HL7Parser.getLaxUnderstanding(this.prop)) {
                    throw e;
                }
            }
        }
        
        return this.i;
    }
    
    /**
     * Converts this into a int
     * 
     * @return this as a int
     **/
    public int intValue() {
        return toInteger().intValue();
    }
    
    /**
     * Converts the SI to a Integer
     * 
     * @param si the SI
     * @return the SI as a Integer
     **/
    public static Integer toInteger(final SI si) {
        return si == null ? null : si.toInteger();
    }
    
    /**
     * Converts the SI to a String
     * 
     * @return the SI as a String
     **/
    @Override
    public String toDisplay() {
        return this.si;
    }
    
    /**
     * Retrieves the numeric String
     * 
     * @return the numeric String
     **/
    public String getSI() {
        return this.si;
    }
    
    /**
     * Modifies the numeric String
     * 
     * @param si the new numeric String
     **/
    public void setSI(final String si) {
        this.si = si;
        this.i = null;
    }
    
    public static SI parsePiped(final HL7Parser parser, final String line, final int start, final char delim, final int stop) {
        if (stop <= start) {
            return null;
        }
        final SI si = new SI(parser);
        si.readPiped(parser, line, start, delim, stop);
        return si;
    }
    
    @Override
    public void readPiped(final HL7Parser parser, final String line, final int start, final char delim, final int stop) {
        setSI(getToken(line, start, stop));
    }
    
    @Override
    public void toPiped(final Writer w, final int level) throws IOException {
        writePipedPrimitive(w);
    }
    
    /**
     * Retrieves the hash code
     * 
     * @return the hash code
     **/
    @Override
    public int hashCode() {
        return this.si.hashCode();
    }
    
    /**
     * Retrieves whether the SI equals the given object
     * 
     * @return whether the SI equals the given object
     **/
    @Override
    public boolean equals(final Object o) {
        return o instanceof SI ? toInteger().equals(((SI) o).toInteger()) : false;
    }
    
    /**
     * Retrieves a positive integer if this follows the given Object, 0 if they're equal, otherwise
     * a negative integer
     * 
     * @param o the Object to compare to this
     * @return a positive integer if this follows the given Object, 0 if they're equal, otherwise a
     *         negative integer
     **/
    //@Override // Should we implement Comparable?
    public int compareTo(final Object o) {
        return toInteger().compareTo(((SI) o).toInteger());
    }
    
    /**
     * Retrieves the value of the given SI, or null if the SI is null
     * 
     * @param si the SI
     * @return the value
     **/
    public static String getValue(final SI si) {
        return si == null ? null : si.getSI();
    }
    
    /**
     * Modifies the value of the given SI, instantiating or nulling it if needed
     * 
     * @param prop the HL7Properties
     * @param si the SI
     * @param value the value
     * @return the SI
     **/
    public static SI setValue(final HL7Properties prop, SI si, final String value) {
        if (value == null) {
            si = null;
        } else {
            if (si == null) {
                si = new SI(prop, value);
            } else {
                si.setSI(value);
            }
        }
        
        return si;
    }
}
