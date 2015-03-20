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

/**
 * <p>
 * Title: DLD
 * </p>
 * <p>
 * Description: HL7 Discharge To Location And Date
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
public class DLD extends HL7DataType {
    
    public final static String DLD_XML = "DLD";
    
    public final static String DISCHARGE_LOCATION_XML = "DLD.1";
    
    public final static String EFFECTIVE_DATE_XML = "DLD.2";
    
    private CWE dischargeLocation = null;
    
    private TS effectiveDate = null;
    
    /**
     * Constructs an empty DLD
     * 
     * @param prop the HL7Properties
     **/
    public DLD(final HL7Properties prop) {
        super(prop);
    }
    
    public static DLD parsePiped(final HL7Parser parser, final String line, final int start, final char delim, final int stop) {
        if (stop <= start) {
            return null;
        }
        final DLD dld = new DLD(parser);
        dld.readPiped(parser, line, start, delim, stop);
        return dld;
    }
    
    @Override
    public void readPiped(final HL7Parser parser, final String line, int start, final char delim, final int stop) {
        int next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        final char c = parser.getSubcomponentSeparator();
        this.dischargeLocation = CWE.parsePiped(parser, line, start, c, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.effectiveDate = TS.parsePiped(parser, line, start, c, next);
        
        assertLast(line, next + 1, delim, stop);
    }
    
    @Override
    public void toPiped(final Writer w, final int level) throws IOException {
        int last = 1;
        
        last = addComponent(w, this.dischargeLocation, last, 1, level);
        last = addComponent(w, this.effectiveDate, last, 2, level);
    }
    
    /**
     * Retrieves the discharge location
     * 
     * @return the discharge location
     **/
    public CWE getDischargeLocation() {
        return this.dischargeLocation;
    }
    
    /**
     * Retrieves the effective date
     * 
     * @return the effective date
     **/
    public TS getEffectiveDate() {
        return this.effectiveDate;
    }
    
    /**
     * Modifies the discharge location
     * 
     * @param dischargeLocation the new discharge location
     **/
    public void setDischargeLocation(final CWE dischargeLocation) {
        this.dischargeLocation = dischargeLocation;
    }
    
    /**
     * Modifies the effective date
     * 
     * @param effectiveDate the new effective date
     **/
    public void setEffectiveDate(final TS effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
}
