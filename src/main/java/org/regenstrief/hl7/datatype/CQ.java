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
 * Title: CQ
 * </p>
 * <p>
 * Description: HL7 Composite Quantity With Units
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
public class CQ extends AbstractNumericData {
    
    public final static String CQ_XML = "CQ";
    
    public final static String QUANTITY_XML = "CQ.1";
    
    public final static String UNITS_XML = "CQ.2";
    
    private NM quantity = null;
    
    private CE units = null;
    
    /**
     * Constructs an empty CQ
     * 
     * @param prop the HL7Properties
     **/
    public CQ(final HL7Properties prop) {
        super(prop);
    }
    
    /**
     * Constructs a CQ
     * 
     * @param prop the HL7Properties
     * @param quantity the quantity
     **/
    public CQ(final HL7Properties prop, final double quantity) {
        this(prop);
        this.quantity = new NM(prop, quantity);
    }
    
    /**
     * Converts the CQ to a Double
     * 
     * @return the CQ as a Double
     **/
    @Override
    public Double toDouble() {
        return this.quantity == null ? null : this.quantity.toDouble();
    }
    
    /**
     * Converts the quantity value into a String
     * 
     * @return the quantity value as a String
     **/
    @Override
    public String toDisplay() {
        return UtilHL7.concat(this.quantity, toDisplay(this.units), " ");
    }
    
    public static CQ parsePiped(final HL7Parser parser, final String line, final int start, final char delim, final int stop) {
        if (stop <= start) {
            return null;
        }
        final CQ cq = new CQ(parser);
        cq.readPiped(parser, line, start, delim, stop);
        return cq;
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
        this.units = CE.parsePiped(parser, line, start, c, next);
        
        assertLast(line, next + 1, delim, stop);
    }
    
    @Override
    public void toPiped(final Writer w, final int level) throws IOException {
        int last = 1;
        
        last = addComponent(w, this.quantity, last, 1, level);
        last = addComponent(w, this.units, last, 2, level);
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
     * Retrieves the units
     * 
     * @return the units
     **/
    public CE getUnits() {
        return this.units;
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
     * Modifies the units
     * 
     * @param units the new units
     **/
    public void setUnits(final CE units) {
        this.units = units;
    }
    
    public final static NM getQuantity(final CQ cq) {
        return cq == null ? null : cq.getQuantity();
    }
}
