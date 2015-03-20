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
import org.regenstrief.hl7.util.AbstractAddressData;

/**
 * <p>
 * Title: AD
 * </p>
 * <p>
 * Description: HL7 Address
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
public class AD extends AbstractAddressData {
    
    public final static String AD_XML = "AD";
    
    public final static String STREET_ADDRESS_XML = "AD.1";
    
    public final static String OTHER_DESIGNATION_XML = "AD.2";
    
    public final static String CITY_XML = "AD.3";
    
    public final static String STATE_OR_PROVINCE_XML = "AD.4";
    
    public final static String ZIP_OR_POSTAL_CODE_XML = "AD.5";
    
    public final static String COUNTRY_XML = "AD.6";
    
    public final static String ADDRESS_TYPE_XML = "AD.7";
    
    public final static String OTHER_GEOGRAPHIC_DESIGNATION_XML = "AD.8";
    
    private String streetAddress = null;
    
    /**
     * Constructs an empty AD
     * 
     * @param prop the HL7Properties
     **/
    public AD(final HL7Properties prop) {
        super(prop);
    }
    
    /**
     * Constructs an AD from the given street address, other designation, city, state, and zip code
     * 
     * @param prop the HL7Properties
     * @param streetAddress the street address
     * @param otherDesignation the other designation
     * @param city the city
     * @param stateOrProvince the state or province
     * @param zipOrPostalCode the zip or postal code
     **/
    public AD(final HL7Properties prop, final String streetAddress, final String otherDesignation, final String city,
        final String stateOrProvince, final String zipOrPostalCode) {
        this(prop);
        this.streetAddress = streetAddress;
        this.otherDesignation = otherDesignation;
        this.city = city;
        this.stateOrProvince = stateOrProvince;
        this.zipOrPostalCode = zipOrPostalCode;
    }
    
    public static AD parsePiped(final HL7Parser parser, final String line, final int start, final char delim, final int stop) {
        if (stop <= start) {
            return null;
        }
        final AD ad = new AD(parser);
        ad.readPiped(parser, line, start, delim, stop);
        return ad;
    }
    
    @Override
    public void readPiped(final HL7Parser parser, final String line, int start, final char delim, final int stop) {
        int next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.streetAddress = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.otherDesignation = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.city = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.stateOrProvince = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.zipOrPostalCode = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.country = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.addressType = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.otherGeographicDesignation = getToken(line, start, next);
        
        assertLast(line, next + 1, delim, stop);
    }
    
    @Override
    public void toPiped(final Writer w, final int level) throws IOException {
        int last = 1;
        
        last = addComponent(w, this.streetAddress, last, 1, level);
        last = addComponent(w, this.otherDesignation, last, 2, level);
        last = addComponent(w, this.city, last, 3, level);
        last = addComponent(w, this.stateOrProvince, last, 4, level);
        last = addComponent(w, this.zipOrPostalCode, last, 5, level);
        last = addComponent(w, this.country, last, 6, level);
        last = addComponent(w, this.addressType, last, 7, level);
        last = addComponent(w, this.otherGeographicDesignation, last, 8, level);
    }
    
    /**
     * Retrieves the street address
     * 
     * @return the street address
     **/
    @Override
    public String getMailingAddress() {
        return this.streetAddress;
    }
    
    /**
     * Modifies the street address
     * 
     * @param streetAddress the new street address
     **/
    @Override
    public void setMailingAddress(final String streetAddress) {
        this.streetAddress = streetAddress;
    }
}
