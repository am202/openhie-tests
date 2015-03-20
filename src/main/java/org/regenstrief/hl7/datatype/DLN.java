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
 * Title: DLN
 * </p>
 * <p>
 * Description: HL7 Driver's License Number
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
public class DLN extends HL7DataType {
    
    public final static String DLN_XML = "DLN";
    
    public final static String LICENSE_NUMBER_XML = "DLN.1";
    
    public final static String ISSUING_STATE_PROVINCE_COUNTRY_XML = "DLN.2";
    
    public final static String EXPIRATION_DATE_XML = "DLN.3";
    
    private String licenseNumber = null;
    
    private CWE issuingStateProvinceCountry = null;
    
    private DT expirationDate = null;
    
    /**
     * Constructs an empty DLN
     * 
     * @param prop the HL7Properties
     **/
    public DLN(final HL7Properties prop) {
        super(prop);
    }
    
    public static DLN parsePiped(final HL7Parser parser, final String line, final int start, final char delim, final int stop) {
        if (stop <= start) {
            return null;
        }
        final DLN dln = new DLN(parser);
        dln.readPiped(parser, line, start, delim, stop);
        return dln;
    }
    
    @Override
    public void readPiped(final HL7Parser parser, final String line, int start, final char delim, final int stop) {
        int next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.licenseNumber = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        final char c = parser.getSubcomponentSeparator();
        this.issuingStateProvinceCountry = CWE.parsePiped(parser, line, start, c, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.expirationDate = DT.parsePiped(parser, line, start, c, next);
        
        assertLast(line, next + 1, delim, stop);
    }
    
    @Override
    public void toPiped(final Writer w, final int level) throws IOException {
        int last = 1;
        
        last = addComponent(w, this.licenseNumber, last, 1, level);
        last = addComponent(w, this.issuingStateProvinceCountry, last, 2, level);
        last = addComponent(w, this.expirationDate, last, 3, level);
    }
    
    /**
     * Retrieves the expiration date
     * 
     * @return the expiration date
     **/
    public DT getExpirationDate() {
        return this.expirationDate;
    }
    
    /**
     * Retrieves the license number
     * 
     * @return the license number
     **/
    public String getLicenseNumber() {
        return this.licenseNumber;
    }
    
    /**
     * Retrieves the issuing state/province/country
     * 
     * @return the issuing state/province/country
     **/
    public CWE getIssuingStateProvinceCountry() {
        return this.issuingStateProvinceCountry;
    }
    
    /**
     * Modifies the expiration date
     * 
     * @param expirationDate the new expiration date
     **/
    public void setExpirationDate(final DT expirationDate) {
        this.expirationDate = expirationDate;
    }
    
    /**
     * Modifies the license number
     * 
     * @param licenseNumber the new license number
     **/
    public void setLicenseNumber(final String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }
    
    /**
     * Modifies the issuing state/province/country
     * 
     * @param issuingStateProvinceCountry the new issuing state/province/country
     **/
    public void setIssuingStateProvinceCountry(final CWE issuingStateProvinceCountry) {
        this.issuingStateProvinceCountry = issuingStateProvinceCountry;
    }
}
