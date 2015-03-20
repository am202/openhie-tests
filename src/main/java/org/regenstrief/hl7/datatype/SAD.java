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
import org.regenstrief.util.Util;

/**
 * <p>
 * Title: SAD
 * </p>
 * <p>
 * Description: HL7 Street Address
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
public class SAD extends HL7DataType {
    
    public final static String SAD_XML = "SAD";
    
    public final static String STREET_OR_MAILING_ADDRESS_XML = "SAD.1";
    
    public final static String STREET_NAME_XML = "SAD.2";
    
    public final static String DWELLING_NUMBER_XML = "SAD.3";
    
    private String streetOrMailingAddress = null;
    
    private String streetName = null;
    
    private String dwellingNumber = null;
    
    /**
     * Constructs an empty SAD
     * 
     * @param prop the HL7Properties
     **/
    public SAD(final HL7Properties prop) {
        super(prop);
    }
    
    /**
     * Constructs an SAD with the given street or mailing address
     * 
     * @param prop the HL7Properties
     * @param streetOrMailingAddress the street or mailing address
     **/
    public SAD(final HL7Properties prop, final String streetOrMailingAddress) {
        this(prop);
        this.streetOrMailingAddress = streetOrMailingAddress;
    }
    
    /**
     * Determines if this equals the given Object
     * 
     * @param o the Object to compare to this
     * @return true if this equals the given Object
     **/
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof SAD)) {
            return false;
        }
        final SAD sad = (SAD) o;
        
        return Util.equalsIgnoreCase(toDisplay(), sad.toDisplay());
    }
    
    /**
     * Retrieves the hash code
     * 
     * @return the hash code
     **/
    @Override
    public int hashCode() {
        return Util.hashCode(Util.toUpperCase(toDisplay()));
    }
    
    public static SAD parsePiped(final HL7Parser parser, final String line, final int start, final char delim, final int stop) {
        if (stop <= start) {
            return null;
        }
        final SAD sad = new SAD(parser);
        sad.readPiped(parser, line, start, delim, stop);
        return sad;
    }
    
    @Override
    public void readPiped(final HL7Parser parser, final String line, int start, final char delim, final int stop) {
        int next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.streetOrMailingAddress = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.streetName = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.dwellingNumber = getToken(line, start, next);
        
        assertLast(line, next + 1, delim, stop);
    }
    
    @Override
    public void toPiped(final Writer w, final int level) throws IOException {
        int last = 1;
        
        last = addComponent(w, this.streetOrMailingAddress, last, 1, level);
        last = addComponent(w, this.streetName, last, 2, level);
        last = addComponent(w, this.dwellingNumber, last, 3, level);
    }
    
    /**
     * Retrieves the street or mailing address
     * 
     * @return the street or mailing address
     **/
    public String getStreetOrMailingAddress() {
        return this.streetOrMailingAddress;
    }
    
    /**
     * Retrieves the street name
     * 
     * @return the street name
     **/
    public String getStreetName() {
        return this.streetName;
    }
    
    /**
     * Retrieves the dwelling number
     * 
     * @return the dwelling number
     **/
    public String getDwellingNumber() {
        return this.dwellingNumber;
    }
    
    /**
     * Modifies the street or mailing address
     * 
     * @param streetOrMailingAddress the new street or mailing address
     **/
    public void setStreetOrMailingAddress(final String streetOrMailingAddress) {
        this.streetOrMailingAddress = streetOrMailingAddress;
    }
    
    /**
     * Modifies the street name
     * 
     * @param streetName the new street name
     **/
    public void setStreetName(final String streetName) {
        this.streetName = streetName;
    }
    
    /**
     * Modifies the dwelling number
     * 
     * @param dwellingNumber the new dwelling number
     **/
    public void setDwellingNumber(final String dwellingNumber) {
        this.dwellingNumber = dwellingNumber;
    }
    
    /**
     * Retrieves the SAD as a String
     * 
     * @return the SAD as a String
     **/
    @Override
    public String toDisplay() {
        return (this.streetName != null) && (this.dwellingNumber != null) ? this.dwellingNumber + ' ' + this.streetName
                : this.streetOrMailingAddress;
    }
    
    /**
     * Retrieves the mailing address of the given SAD, or null if the SAD is null
     * 
     * @param streetAddress the SAD
     * @return the mailing address
     **/
    public static String getMailingAddress(final SAD streetAddress) {
        return toDisplay(streetAddress);
    }
    
    /**
     * Modifies the mailing address of the given SAD, instantiating or nulling it if needed
     * 
     * @param prop the HL7Properties
     * @param streetAddress the SAD
     * @param mailingAddress the mailing address
     * @return the SAD
     **/
    public static SAD setMailingAddress(final HL7Properties prop, SAD streetAddress, final String mailingAddress) {
        if (mailingAddress == null) {
            streetAddress = null;
        } else {
            if (streetAddress == null) {
                streetAddress = new SAD(prop);
            } else {
                streetAddress.setStreetName(null);
                streetAddress.setDwellingNumber(null);
            }
            streetAddress.setStreetOrMailingAddress(mailingAddress);
        }
        
        return streetAddress;
    }
}
