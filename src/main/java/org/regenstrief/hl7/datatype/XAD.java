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
import org.regenstrief.util.Util;

/**
 * <p>
 * Title: XAD
 * </p>
 * <p>
 * Description: HL7 Extended Address
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
public class XAD extends AbstractAddressData {
    
    public final static String XAD_XML = "XAD";
    
    public final static String STREET_ADDRESS_XML = "XAD.1";
    
    public final static String OTHER_DESIGNATION_XML = "XAD.2";
    
    public final static String CITY_XML = "XAD.3";
    
    public final static String STATE_OR_PROVINCE_XML = "XAD.4";
    
    public final static String ZIP_OR_POSTAL_CODE_XML = "XAD.5";
    
    public final static String COUNTRY_XML = "XAD.6";
    
    public final static String ADDRESS_TYPE_XML = "XAD.7";
    
    public final static String OTHER_GEOGRAPHIC_DESIGNATION_XML = "XAD.8";
    
    public final static String COUNTY_PARISH_CODE_XML = "XAD.9";
    
    public final static String CENSUS_TRACT_XML = "XAD.10";
    
    public final static String ADDRESS_REPRESENTATION_CODE_XML = "XAD.11";
    
    public final static String ADDRESS_VALIDITY_RANGE_XML = "XAD.12";
    
    public final static String EFFECTIVE_DATE_XML = "XAD.13";
    
    public final static String EXPIRATION_DATE_XML = "XAD.14";
    
    public final static String EXPIRATION_REASON_XML = "XAD.15";
    
    public final static String TEMPORARY_INDICATOR_XML = "XAD.16";
    
    public final static String BAD_ADDRESS_INDICATOR_XML = "XAD.17";
    
    public final static String ADDRESS_USAGE_XML = "XAD.18";
    
    public final static String ADDRESSEE_XML = "XAD.19";
    
    public final static String COMMENT_XML = "XAD.20";
    
    public final static String PREFERENCE_ORDER_XML = "XAD.21";
    
    public final static String PROTECTION_CODE_XML = "XAD.22";
    
    public final static String ADDRESS_IDENTIFIER_XML = "XAD.23";
    
    private SAD streetAddress = null;
    
    private CWE countyParishCode = null;
    
    private CWE censusTract = null;
    
    private String addressRepresentationCode = null;
    
    private DR addressValidityRange = null;
    
    private DTM effectiveDate = null;
    
    private DTM expirationDate = null;
    
    private CWE expirationReason = null;
    
    private String temporaryIndicator = null;
    
    private String badAddressIndicator = null;
    
    private String addressUsage = null;
    
    private String addressee = null;
    
    private String comment = null;
    
    private NM preferenceOrder = null;
    
    private CWE protectionCode = null;
    
    private EI addressIdentifier = null;
    
    /**
     * Constructs an empty XAD
     * 
     * @param prop the HL7Properties
     **/
    public XAD(final HL7Properties prop) {
        super(prop);
    }
    
    /**
     * Constructs an XAD from the given street address, other designation, city, state, and zip code
     * 
     * @param prop the HL7Properties
     * @param streetAddress the street address
     * @param otherDesignation the other designation
     * @param city the city
     * @param stateOrProvince the state or province
     * @param zipOrPostalCode the zip or postal code
     **/
    public XAD(final HL7Properties prop, final String streetAddress, final String otherDesignation, final String city,
        final String stateOrProvince, final String zipOrPostalCode) {
        this(prop);
        this.setMailingAddress(streetAddress);
        this.otherDesignation = otherDesignation;
        this.city = city;
        this.stateOrProvince = stateOrProvince;
        this.zipOrPostalCode = zipOrPostalCode;
    }
    
    public XAD(final XAD xad) {
        super(xad);
        this.streetAddress = xad.streetAddress;
        this.countyParishCode = xad.countyParishCode;
        this.censusTract = xad.censusTract;
        this.addressRepresentationCode = xad.addressRepresentationCode;
        this.addressValidityRange = xad.addressValidityRange;
    }
    
    public static XAD parsePiped(final HL7Parser parser, final String line, final int start, final char delim, final int stop) {
        if (stop <= start) {
            return null;
        }
        final XAD xad = new XAD(parser);
        xad.readPiped(parser, line, start, delim, stop);
        return xad;
    }
    
    @Override
    public void readPiped(final HL7Parser parser, final String line, int start, final char delim, final int stop) {
        int next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        final char c = parser.getSubcomponentSeparator();
        this.streetAddress = SAD.parsePiped(parser, line, start, c, next);
        
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
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.countyParishCode = CWE.parsePiped(parser, line, start, c, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.censusTract = CWE.parsePiped(parser, line, start, c, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.addressRepresentationCode = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.addressValidityRange = DR.parsePiped(parser, line, start, c, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.effectiveDate = DTM.parsePiped(parser, line, start, c, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.expirationDate = DTM.parsePiped(parser, line, start, c, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.expirationReason = CWE.parsePiped(parser, line, start, c, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.temporaryIndicator = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.badAddressIndicator = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.addressUsage = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.addressee = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.comment = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.preferenceOrder = NM.parsePiped(parser, line, start, c, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.protectionCode = CWE.parsePiped(parser, line, start, c, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.addressIdentifier = EI.parsePiped(parser, line, start, c, next);
        
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
        last = addComponent(w, this.countyParishCode, last, 9, level);
        last = addComponent(w, this.censusTract, last, 10, level);
        last = addComponent(w, this.addressRepresentationCode, last, 11, level);
        last = addComponent(w, this.addressValidityRange, last, 12, level);
        last = addComponent(w, this.effectiveDate, last, 13, level);
        last = addComponent(w, this.expirationDate, last, 14, level);
        last = addComponent(w, this.expirationReason, last, 15, level);
        last = addComponent(w, this.temporaryIndicator, last, 16, level);
        last = addComponent(w, this.badAddressIndicator, last, 17, level);
        last = addComponent(w, this.addressUsage, last, 18, level);
        last = addComponent(w, this.addressee, last, 19, level);
        last = addComponent(w, this.comment, last, 20, level);
        last = addComponent(w, this.preferenceOrder, last, 21, level);
        last = addComponent(w, this.protectionCode, last, 22, level);
        last = addComponent(w, this.addressIdentifier, last, 23, level);
    }
    
    /**
     * Compares to the given Object
     * 
     * @param o the Object to compare to this
     * @return true if this equals the given Object
     **/
    @Override
    public boolean equals(final Object o) {
        if ((o == null) || !(o instanceof XAD)) {
            return false;
        }
        
        final XAD xad = (XAD) o;
        if (!Util.equals(this.streetAddress, xad.streetAddress)) {
            return false;
        } else if (!Util.equalsIgnoreCase(this.otherDesignation, xad.otherDesignation)) {
            return false;
        } else if (!Util.equalsIgnoreCase(this.city, xad.city)) {
            return false;
        } else if (!Util.equalsIgnoreCase(this.stateOrProvince, xad.stateOrProvince)) {
            return false;
        } else if (!Util.equals(getZipWithoutExtension(), xad.getZipWithoutExtension())) {
            return false;
        } else if (!Util.equals(getZipExtension(), xad.getZipExtension())) {
            return false;
        } else if (!Util.equals(this.countyParishCode, xad.countyParishCode)) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Retrieves the hash code
     * 
     * @return the hash code
     **/
    @Override
    public int hashCode() {
        final String s = "" + this.streetAddress + this.otherDesignation + this.city + this.stateOrProvince
                + this.zipOrPostalCode + this.countyParishCode;
        
        return s.toUpperCase().hashCode();
    }
    
    /**
     * Retrieves the street address
     * 
     * @return the street address
     **/
    public SAD getStreetAddress() {
        return this.streetAddress;
    }
    
    /**
     * Retrieves the mailing address
     * 
     * @return the mailing address
     **/
    @Override
    public String getMailingAddress() {
        return SAD.getMailingAddress(this.streetAddress);
    }
    
    /**
     * Retrieves the county parish code
     * 
     * @return the county parish code
     **/
    public CWE getCountyParishCode() {
        return this.countyParishCode;
    }
    
    /**
     * Retrieves the census tract
     * 
     * @return the census tract
     **/
    public CWE getCensusTract() {
        return this.censusTract;
    }
    
    /**
     * Retrieves the address representation code
     * 
     * @return the address representation code
     **/
    public String getAddressRepresentationCode() {
        return this.addressRepresentationCode;
    }
    
    /**
     * Retrieves the address validity range
     * 
     * @return the address validity range
     **/
    public DR getAddressValidityRange() {
        return this.addressValidityRange;
    }
    
    /**
     * Retrieves the Effective Date
     * 
     * @return the Effective Date
     **/
    public DTM getEffectiveDate() {
        return this.effectiveDate;
    }
    
    /**
     * Retrieves the Expiration Date
     * 
     * @return the Expiration Date
     **/
    public DTM getExpirationDate() {
        return this.expirationDate;
    }
    
    /**
     * Retrieves the Expiration Reason
     * 
     * @return the Expiration Reason
     **/
    public CWE getExpirationReason() {
        return this.expirationReason;
    }
    
    /**
     * Retrieves the Temporary Indicator
     * 
     * @return the Temporary Indicator
     **/
    public String getTemporaryIndicator() {
        return this.temporaryIndicator;
    }
    
    /**
     * Retrieves the Bad Address Indicator
     * 
     * @return the Bad Address Indicator
     **/
    public String getBadAddressIndicator() {
        return this.badAddressIndicator;
    }
    
    /**
     * Retrieves the Address Usage
     * 
     * @return the Address Usage
     **/
    public String getAddressUsage() {
        return this.addressUsage;
    }
    
    /**
     * Retrieves the Addressee
     * 
     * @return the Addressee
     **/
    public String getAddressee() {
        return this.addressee;
    }
    
    /**
     * Retrieves the Comment
     * 
     * @return the Comment
     **/
    public String getComment() {
        return this.comment;
    }
    
    /**
     * Retrieves the Preference Order
     * 
     * @return the Preference Order
     **/
    public NM getPreferenceOrder() {
        return this.preferenceOrder;
    }
    
    /**
     * Retrieves the Protection Code
     * 
     * @return the Protection Code
     **/
    public CWE getProtectionCode() {
        return this.protectionCode;
    }
    
    /**
     * Retrieves the Address Identifier
     * 
     * @return the Address Identifier
     **/
    public EI getAddressIdentifier() {
        return this.addressIdentifier;
    }
    
    /**
     * Modifies the street address
     * 
     * @param streetAddress the new street address
     **/
    public void setStreetAddress(final SAD streetAddress) {
        this.streetAddress = streetAddress;
    }
    
    /**
     * Modifies the mailing address
     * 
     * @param mailingAddress the new mailing address
     **/
    @Override
    public void setMailingAddress(final String mailingAddress) {
        this.streetAddress = SAD.setMailingAddress(this.prop, this.streetAddress, mailingAddress);
    }
    
    /**
     * Modifies the county parish code
     * 
     * @param countyParishCode the new county parish code
     **/
    public void setCountyParishCode(final CWE countyParishCode) {
        this.countyParishCode = countyParishCode;
    }
    
    /**
     * Modifies the census tract
     * 
     * @param censusTract the new census tract
     **/
    public void setCensusTract(final CWE censusTract) {
        this.censusTract = censusTract;
    }
    
    /**
     * Modifies the address representation code
     * 
     * @param addressRepresentationCode the new address representation code
     **/
    public void setAddressRepresentationCode(final String addressRepresentationCode) {
        this.addressRepresentationCode = addressRepresentationCode;
    }
    
    /**
     * Modifies the address validity range
     * 
     * @param addressValidityRange the new address validity range
     **/
    public void setAddressValidityRange(final DR addressValidityRange) {
        this.addressValidityRange = addressValidityRange;
    }
    
    /**
     * Modifies the Effective Date
     * 
     * @param effectiveDate the new Effective Date
     **/
    public void setEffectiveDate(final DTM effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
    
    /**
     * Modifies the Expiration Date
     * 
     * @param expirationDate the new Expiration Date
     **/
    public void setExpirationDate(final DTM expirationDate) {
        this.expirationDate = expirationDate;
    }
    
    /**
     * Modifies the Expiration Reason
     * 
     * @param expirationReason the new Expiration Reason
     **/
    public void setExpirationReason(final CWE expirationReason) {
        this.expirationReason = expirationReason;
    }
    
    /**
     * Modifies the Temporary Indicator
     * 
     * @param temporaryIndicator the new Temporary Indicator
     **/
    public void setTemporaryIndicator(final String temporaryIndicator) {
        this.temporaryIndicator = temporaryIndicator;
    }
    
    /**
     * Modifies the Bad Address Indicator
     * 
     * @param badAddressIndicator the new Bad Address Indicator
     **/
    public void setBadAddressIndicator(final String badAddressIndicator) {
        this.badAddressIndicator = badAddressIndicator;
    }
    
    /**
     * Modifies the Address Usage
     * 
     * @param addressUsage the new Address Usage
     **/
    public void setAddressUsage(final String addressUsage) {
        this.addressUsage = addressUsage;
    }
    
    /**
     * Modifies the Addressee
     * 
     * @param addressee the new Addressee
     **/
    public void setAddressee(final String addressee) {
        this.addressee = addressee;
    }
    
    /**
     * Modifies the Comment
     * 
     * @param comment the new Comment
     **/
    public void setComment(final String comment) {
        this.comment = comment;
    }
    
    /**
     * Modifies the Preference Order
     * 
     * @param preferenceOrder the new Preference Order
     **/
    public void setPreferenceOrder(final NM preferenceOrder) {
        this.preferenceOrder = preferenceOrder;
    }
    
    /**
     * Modifies the Protection Code
     * 
     * @param protectionCode the new Protection Code
     **/
    public void setProtectionCode(final CWE protectionCode) {
        this.protectionCode = protectionCode;
    }
    
    /**
     * Modifies the Address Identifier
     * 
     * @param addressIdentifier the new Address Identifier
     **/
    public void setAddressIdentifier(final EI addressIdentifier) {
        this.addressIdentifier = addressIdentifier;
    }
}
