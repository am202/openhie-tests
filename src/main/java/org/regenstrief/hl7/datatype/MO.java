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

/**
 * <p>
 * Title: MO
 * </p>
 * <p>
 * Description: HL7 Money
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
public class MO extends AbstractNumericData {
    
    public final static String MO_XML = "MO";
    
    public final static String QUANTITY_XML = "MO.1";
    
    public final static String DENOMINATION_XML = "MO.2";
    
    private NM quantity = null;
    
    private String denomination = null;
    
    /**
     * Constructs an empty MO
     * 
     * @param prop the HL7Properties
     **/
    public MO(final HL7Properties prop) {
        super(prop);
    }
    
    /**
     * Constructs an MO with the given quantity
     * 
     * @param prop the HL7Properties
     * @param quantity the quantity
     **/
    public MO(final HL7Properties prop, final NM quantity) {
        this(prop);
        this.quantity = quantity;
    }
    
    /**
     * Constructs an MO with the given quantity
     * 
     * @param prop the HL7Properties
     * @param quantity the quantity
     **/
    public MO(final HL7Properties prop, final double quantity) {
        this(prop);
        this.quantity = new NM(prop, quantity);
    }
    
    public final static MO create(final HL7Properties prop, final String quantity) {
        return create(prop, NM.create(prop, quantity));
    }
    
    public final static MO create(final HL7Properties prop, final NM nm) {
        return nm == null ? null : new MO(prop, nm);
    }
    
    /**
     * Retrieves the quantity in cents or null if denomination is not USD
     * 
     * @return the quantity in cents
     **/
    @Override
    public Double toDouble() {
        if ((this.quantity != null) && ((this.denomination == null) || this.denomination.equalsIgnoreCase("USD"))) {
            //return (new Double(quantity.doubleValue() * 100)); // 36.67 * 100.0 = 3636.9999999999995
            return new Double(toPennies(this.quantity.toDisplay()));
        }
        
        return null;
    }
    
    public static String toPennies(String dollars) {
        if (!UtilHL7.isNumeric(dollars)) {
            throw new RuntimeException("Not numeric: " + dollars);
        }
        dollars = dollars + "00";
        int i = dollars.indexOf('.');
        if (i == 0) {
            dollars = "0" + dollars;
            i++;
        }
        
        if (i >= 1) { // Double.toString puts ".0" on integers, but check for "." in case it came from somewhere else
            dollars = dollars.substring(0, i) + dollars.substring(i + 1, i + 3) + "." + dollars.substring(i + 3);
        }
        
        return dollars;
    }
    
    /**
     * Converts the money value into a String
     * 
     * @return the money value as a String
     **/
    @Override
    public String toDisplay() {
        return UtilHL7.concat(this.quantity, this.denomination, " ");
    }
    
    public static MO parsePiped(final HL7Parser parser, final String line, final int start, final char delim, final int stop) {
        if (stop <= start) {
            return null;
        }
        final MO mo = new MO(parser);
        mo.readPiped(parser, line, start, delim, stop);
        return mo;
    }
    
    @Override
    public void readPiped(final HL7Parser parser, final String line, int start, final char delim, final int stop) {
        int next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        final char c = parser.getSubcomponentSeparator();
        this.quantity = NM.parsePiped(parser, line, start, c, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.denomination = getToken(line, start, next);
        
        assertLast(line, next + 1, delim, stop);
    }
    
    @Override
    public void toPiped(final Writer w, final int level) throws IOException {
        int last = 1;
        
        last = addComponent(w, this.quantity, last, 1, level);
        last = addComponent(w, this.denomination, last, 2, level);
    }
    
    /**
     * Retrieves the quantity
     * 
     * @return the quantity
     **/
    public NM getQuantity() {
        return this.quantity;
    }
    
    /**
     * Retrieves the denomination
     * 
     * @return the denomination
     **/
    public String getDenomination() {
        return this.denomination;
    }
    
    /**
     * Modifies the quantity
     * 
     * @param quantity the new quantity
     **/
    public void setQuantity(final NM quantity) {
        this.quantity = quantity;
    }
    
    /**
     * Modifies the denomination
     * 
     * @param denomination the new denomination
     **/
    public void setDenomination(final String denomination) {
        this.denomination = denomination;
    }
    
    public final static NM getQuantity(final MO mo) {
        return mo == null ? null : mo.getQuantity();
    }
}
