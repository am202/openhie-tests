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
import org.regenstrief.hl7.util.AbstractNumericData;
import org.regenstrief.hl7.util.UtilHL7;
import org.regenstrief.util.Util;

/**
 * <p>
 * Title: NM
 * </p>
 * <p>
 * Description: HL7 Numeric
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
public class NM extends AbstractNumericData {
    
    public final static String NM_XML = "NM";
    
    private String nm = null;
    
    private Double d = null;
    
    private NM(final HL7Properties prop) {
        super(prop);
    }
    
    /**
     * Constructs a new NM with from the given numeric String
     * 
     * @param prop the HL7Properties
     * @param nm the numeric String
     **/
    private NM(final HL7Properties prop, final String nm) {
        super(prop);
        this.nm = nm;
    }
    
    /**
     * Constructs a new NM with from the given numeric String
     * 
     * @param prop the HL7Properties
     * @param nm the numeric String
     * @return the NM
     **/
    public final static NM create(final HL7Properties prop, final String nm) {
        return Util.isEmpty(nm) ? null : new NM(prop, nm); // prop could define whether non-numeric Strings should be treated as null or throw an exception
    }
    
    /**
     * Constructs a new NM with from the given Double
     * 
     * @param prop the HL7Properties
     * @param d the Double
     **/
    private NM(final HL7Properties prop, final Double d) {
        this(prop, UtilHL7.toString(d));
        this.d = d;
    }
    
    /**
     * Constructs a new NM with from the given Double
     * 
     * @param prop the HL7Properties
     * @param d the Double
     * @return the NM
     **/
    public final static NM create(final HL7Properties prop, final Double d) {
        return d == null ? null : new NM(prop, d);
    }
    
    public final static NM create(final HL7Properties prop, final Number n) {
        return n == null ? null : n instanceof Double ? create(prop, (Double) n) : new NM(prop, n.doubleValue());
    }
    
    /**
     * Constructs a new NM with from the given double
     * 
     * @param prop the HL7Properties
     * @param d the double
     **/
    public NM(final HL7Properties prop, final double d) {
        this(prop, new Double(d));
    }
    
    /**
     * Converts the NM to a Double
     * 
     * @return the NM as a Double
     **/
    @Override
    public Double toDouble() {
        if ((this.d == null) && (this.nm != null)) {
            try {
                this.d = new Double(this.nm);
            } catch (final NumberFormatException e) {
                if (!HL7Parser.getLaxUnderstanding(this.prop)) {
                    throw e;
                }
            }
        }
        
        return this.d;
    }
    
    /**
     * Converts the NM to a String
     * 
     * @return the NM as a String
     **/
    @Override
    public String toDisplay() {
        return this.nm;
    }
    
    /**
     * Retrieves the numeric String
     * 
     * @return the numeric String
     **/
    public String getNM() {
        return this.nm;
    }
    
    /**
     * Modifies the numeric String
     * 
     * @param nm the new numeric String
     **/
    public void setNM(final String nm) {
        this.nm = nm;
        this.d = null;
    }
    
    public static NM parsePiped(final HL7Parser parser, final String line, final int start, final char delim, final int stop) {
        if (stop <= start) {
            return null;
        }
        final NM nm = new NM(parser);
        nm.readPiped(parser, line, start, delim, stop);
        return nm;
    }
    
    @Override
    public void readPiped(final HL7Parser parser, final String line, final int start, final char delim, final int stop) {
        setNM(getToken(line, start, stop));
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
        return this.nm.hashCode();
    }
    
    /**
     * Retrieves whether the NM equals the given object
     * 
     * @return whether the NM equals the given object
     **/
    @Override
    public boolean equals(final Object o) {
        return o instanceof NM ? toDouble().equals(((NM) o).toDouble()) : false;
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
        return toDouble().compareTo(((NM) o).toDouble());
    }
    
    /**
     * Retrieves the value of the given NM, or null if the NM is null
     * 
     * @param nm the NM
     * @return the value
     **/
    public static String getValue(final NM nm) {
        return nm == null ? null : nm.getNM();
    }
    
    /**
     * Modifies the value of the given NM, instantiating or nulling it if needed
     * 
     * @param prop the HL7Properties
     * @param nm the NM
     * @param value the value
     * @return the NM
     **/
    public static NM setValue(final HL7Properties prop, NM nm, final String value) {
        if (value == null) {
            nm = null;
        } else {
            if (nm == null) {
                nm = new NM(prop, value);
            } else {
                nm.setNM(value);
            }
        }
        
        return nm;
    }
}
