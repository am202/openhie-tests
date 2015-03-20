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
 * Title: CP
 * </p>
 * <p>
 * Description: HL7 Composite Price
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
public class CP extends AbstractNumericData {
    
    public final static String CP_XML = "CP";
    
    public final static String PRICE_XML = "CP.1";
    
    public final static String PRICE_TYPE_XML = "CP.2";
    
    public final static String FROM_VALUE_XML = "CP.3";
    
    public final static String TO_VALUE_XML = "CP.4";
    
    public final static String RANGE_UNITS_XML = "CP.5";
    
    public final static String RANGE_TYPE_XML = "CP.6";
    
    private MO price = null;
    
    private String priceType = null;
    
    private NM fromValue = null;
    
    private NM toValue = null;
    
    private CE rangeUnits = null;
    
    private String rangeType = null;
    
    /**
     * Constructs an empty CP
     * 
     * @param prop the HL7Properties
     **/
    public CP(final HL7Properties prop) {
        super(prop);
    }
    
    public final static CP create(final HL7Properties prop, final MO price) {
        if (price == null) {
            return null;
        }
        final CP cp = new CP(prop);
        cp.price = price;
        return cp;
    }
    
    public final static CP create(final HL7Properties prop, final NM quantity) {
        return create(prop, MO.create(prop, quantity));
    }
    
    public final static CP create(final HL7Properties prop, final String quantity) {
        return create(prop, MO.create(prop, quantity));
    }
    
    public final static CP create(final HL7Properties prop, final Number quantity) {
        return create(prop, NM.create(prop, quantity));
    }
    
    /**
     * Converts this price into a Double
     * 
     * @return the price as a Double
     **/
    @Override
    public Double toDouble() {
        return this.price == null ? null : this.price.toDouble();
    }
    
    /**
     * Converts this price into a String
     * 
     * @return the price as a String
     **/
    @Override
    public String toDisplay() {
        return UtilHL7.toString(this.price);
    }
    
    public static CP parsePiped(final HL7Parser parser, final String line, final int start, final char delim, final int stop) {
        if (stop <= start) {
            return null;
        }
        final CP cp = new CP(parser);
        cp.readPiped(parser, line, start, delim, stop);
        return cp;
    }
    
    @Override
    public void readPiped(final HL7Parser parser, final String line, int start, final char delim, final int stop) {
        int next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        final char c = parser.getSubcomponentSeparator();
        this.price = MO.parsePiped(parser, line, start, c, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.priceType = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.fromValue = NM.parsePiped(parser, line, start, c, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.toValue = NM.parsePiped(parser, line, start, c, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.rangeUnits = CE.parsePiped(parser, line, start, c, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.rangeType = getToken(line, start, next);
        
        assertLast(line, next + 1, delim, stop);
    }
    
    @Override
    public void toPiped(final Writer w, final int level) throws IOException {
        int last = 1;
        
        last = addComponent(w, this.price, last, 1, level);
        last = addComponent(w, this.priceType, last, 2, level);
        last = addComponent(w, this.fromValue, last, 3, level);
        last = addComponent(w, this.toValue, last, 4, level);
        last = addComponent(w, this.rangeUnits, last, 5, level);
        last = addComponent(w, this.rangeType, last, 6, level);
    }
    
    /**
     * Retrieves the price
     * 
     * @return the price
     **/
    public MO getPrice() {
        return this.price;
    }
    
    /**
     * Retrieves the price type
     * 
     * @return the price type
     **/
    public String getPriceType() {
        return this.priceType;
    }
    
    /**
     * Retrieves the from value
     * 
     * @return the from value
     **/
    public NM getFromValue() {
        return this.fromValue;
    }
    
    /**
     * Retrieves the to value
     * 
     * @return the to value
     **/
    public NM getToValue() {
        return this.toValue;
    }
    
    /**
     * Retrieves the range units
     * 
     * @return the range units
     **/
    public CE getRangeUnits() {
        return this.rangeUnits;
    }
    
    /**
     * Retrieves the range type
     * 
     * @return the range type
     **/
    public String getRangeType() {
        return this.rangeType;
    }
    
    /**
     * Modifies the price
     * 
     * @param price the new price
     **/
    public void setPrice(final MO price) {
        this.price = price;
    }
    
    /**
     * Modifies the price type
     * 
     * @param priceType the new price type
     **/
    public void setPriceType(final String priceType) {
        this.priceType = priceType;
    }
    
    /**
     * Modifies the from value
     * 
     * @param fromValue the new from value
     **/
    public void setFromValue(final NM fromValue) {
        this.fromValue = fromValue;
    }
    
    /**
     * Modifies the to value
     * 
     * @param toValue the new to value
     **/
    public void setToValue(final NM toValue) {
        this.toValue = toValue;
    }
    
    /**
     * Modifies the range units
     * 
     * @param rangeUnits the new range units
     **/
    public void setRangeUnits(final CE rangeUnits) {
        this.rangeUnits = rangeUnits;
    }
    
    /**
     * Modifies the range type
     * 
     * @param rangeType the new range type
     **/
    public void setRangeType(final String rangeType) {
        this.rangeType = rangeType;
    }
}
