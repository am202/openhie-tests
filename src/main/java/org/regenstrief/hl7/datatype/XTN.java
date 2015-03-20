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
 * Title: XTN
 * </p>
 * <p>
 * Description: HL7 Extended Telecommunication Number
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
public class XTN extends HL7DataType {
    
    public final static String XTN_XML = "XTN";
    
    public final static String NUMBER_XML = "XTN.1";
    
    public final static String TELECOMMUNICATION_USE_CODE_XML = "XTN.2";
    
    public final static String TELECOMMUNICATION_EQUIPMENT_TYPE_XML = "XTN.3";
    
    public final static String EMAIL_ADDRESS_XML = "XTN.4";
    
    public final static String COUNTRY_CODE_XML = "XTN.5";
    
    public final static String AREA_CITY_CODE_XML = "XTN.6";
    
    public final static String PHONE_NUMBER_XML = "XTN.7";
    
    public final static String EXTENSION_XML = "XTN.8";
    
    public final static String ANY_TEXT_XML = "XTN.9";
    
    public final static String EXTENSION_PREFIX_XML = "XTN.10";
    
    public final static String SPEED_DIAL_CODE_XML = "XTN.11";
    
    public final static String UNFORMATTED_TELEPHONE_NUMBER_XML = "XTN.12";
    
    public final static String EFFECTIVE_DATE_XML = "XTN.13";
    
    public final static String EXPIRATION_DATE_XML = "XTN.14";
    
    public final static String EXPIRATION_REASON_XML = "XTN.15";
    
    public final static String PROTECTION_CODE_XML = "XTN.16";
    
    public final static String SHARED_TELECOMMUNICATION_IDENTIFIER_XML = "XTN.17";
    
    public final static String PREFERENCE_ORDER_XML = "XTN.18";
    
    // Telecommunication use codes
    public final static String USE_PRN = "PRN"; // Primary Residence Number
    
    public final static String USE_ORN = "ORN"; // Other Residence Number
    
    public final static String USE_WPN = "WPN"; // Work Number
    
    public final static String USE_VHN = "VHN"; // Vacation Home Number
    
    public final static String USE_ASN = "ASN"; // Answering Service Number
    
    public final static String USE_EMR = "EMR"; // Emergency Number
    
    public final static String USE_NET = "NET"; // Network (email) Address
    
    public final static String USE_BPN = "BPN"; // Beeper Number
    
    private String number = null; //[(999)]999-9999[X99999][C any text]
    
    private String telecommunicationUseCode = null;
    
    private String telecommunicationEquipmentType = null;
    
    private String emailAddress = null;
    
    private NM countryCode = null;
    
    private NM areaCityCode = null;
    
    private NM phoneNumber = null;
    
    private NM extension = null;
    
    private String anyText = null;
    
    private String extensionPrefix = null;
    
    private String speedDialCode = null;
    
    private String unformattedTelephoneNumber = null;
    
    private DTM effectiveDate = null;
    
    private DTM expirationDate = null;
    
    private CWE expirationReason = null;
    
    private CWE protectionCode = null;
    
    private EI sharedTelecommunicationIdentifier = null;
    
    private NM preferenceOrder = null;
    
    /**
     * Constructs an empty XTN
     * 
     * @param prop the HL7Properties
     **/
    public XTN(final HL7Properties prop) {
        super(prop);
    }
    
    /**
     * Constructs an XTN from the given number String
     * 
     * @param prop the HL7Properties
     * @param number the number
     * @return the XTN
     **/
    public static XTN create(final HL7Properties prop, final String number) {
        if (Util.isEmpty(number)) {
            return null;
        }
        
        final XTN xtn = new XTN(prop);
        xtn.setNumber(number);
        
        return xtn;
    }
    
    /**
     * Determines if this equals the given Object
     * 
     * @param o the Object to compare to this
     * @return true if this equals o, false otherwise
     **/
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof XTN)) {
            return false;
        }
        
        return equals((XTN) o);
    }
    
    /**
     * Determines if this equals the given XTN
     * 
     * @param xtn the XTN to compare to this
     * @return true if this equals xtn, false otherwise
     **/
    public boolean equals(final XTN xtn) {
        if (xtn == null) {
            return false;
        }
        
        return xtn.number.equalsIgnoreCase(this.number);
    }
    
    public static XTN parsePiped(final HL7Parser parser, final String line, final int start, final char delim, final int stop) {
        if (stop <= start) {
            return null;
        }
        final XTN xtn = new XTN(parser);
        xtn.readPiped(parser, line, start, delim, stop);
        return xtn;
    }
    
    @Override
    public void readPiped(final HL7Parser parser, final String line, int start, final char delim, final int stop) {
        int next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        final char c = parser.getSubcomponentSeparator();
        this.number = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.telecommunicationUseCode = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.telecommunicationEquipmentType = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.emailAddress = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.countryCode = NM.parsePiped(parser, line, start, c, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.areaCityCode = NM.parsePiped(parser, line, start, c, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.phoneNumber = NM.parsePiped(parser, line, start, c, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.extension = NM.parsePiped(parser, line, start, c, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.anyText = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.extensionPrefix = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.speedDialCode = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.unformattedTelephoneNumber = getToken(line, start, next);
        
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
        this.protectionCode = CWE.parsePiped(parser, line, start, c, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.sharedTelecommunicationIdentifier = EI.parsePiped(parser, line, start, c, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.preferenceOrder = NM.parsePiped(parser, line, start, c, next);
        
        assertLast(line, next + 1, delim, stop);
    }
    
    @Override
    public void toPiped(final Writer w, final int level) throws IOException {
        int last = 1;
        
        last = addComponent(w, this.number, last, 1, level);
        last = addComponent(w, this.telecommunicationUseCode, last, 2, level);
        last = addComponent(w, this.telecommunicationEquipmentType, last, 3, level);
        last = addComponent(w, this.emailAddress, last, 4, level);
        last = addComponent(w, this.countryCode, last, 5, level);
        last = addComponent(w, this.areaCityCode, last, 6, level);
        last = addComponent(w, this.phoneNumber, last, 7, level);
        last = addComponent(w, this.extension, last, 8, level);
        last = addComponent(w, this.anyText, last, 9, level);
        last = addComponent(w, this.extensionPrefix, last, 10, level);
        last = addComponent(w, this.speedDialCode, last, 11, level);
        last = addComponent(w, this.unformattedTelephoneNumber, last, 12, level);
        last = addComponent(w, this.effectiveDate, last, 13, level);
        last = addComponent(w, this.expirationDate, last, 14, level);
        last = addComponent(w, this.expirationReason, last, 15, level);
        last = addComponent(w, this.protectionCode, last, 16, level);
        last = addComponent(w, this.sharedTelecommunicationIdentifier, last, 17, level);
        last = addComponent(w, this.preferenceOrder, last, 18, level);
    }
    
    /**
     * Retrieves the number
     * 
     * @return the number
     **/
    public String getNumber() {
        return this.number;
    }
    
    /**
     * Retrieves the telecommunication use code
     * 
     * @return the telecommunication use code
     **/
    public String getTelecommunicationUseCode() {
        return this.telecommunicationUseCode;
    }
    
    /**
     * Retrieves the telecommunication equipment type
     * 
     * @return the telecommunication equipment type
     **/
    public String getTelecommunicationEquipmentType() {
        return this.telecommunicationEquipmentType;
    }
    
    /**
     * Retrieves the email address
     * 
     * @return the email address
     **/
    public String getEmailAddress() {
        return this.emailAddress;
    }
    
    /**
     * Retrieves the country code
     * 
     * @return the country code
     **/
    public NM getCountryCode() {
        return this.countryCode;
    }
    
    /**
     * Retrieves the area city code
     * 
     * @return the area city code
     **/
    public NM getAreaCityCode() {
        return this.areaCityCode;
    }
    
    /**
     * Retrieves the phone number
     * 
     * @return the phone number
     **/
    public NM getPhoneNumber() {
        return this.phoneNumber;
    }
    
    /**
     * Retrieves the extension
     * 
     * @return the extension
     **/
    public NM getExtension() {
        return this.extension;
    }
    
    /**
     * Retrieves the any text
     * 
     * @return the any text
     **/
    public String getAnyText() {
        return this.anyText;
    }
    
    /**
     * Retrieves the extension prefix
     * 
     * @return the extension prefix
     **/
    public String getExtensionPrefix() {
        return this.extensionPrefix;
    }
    
    /**
     * Retrieves the speed dial code
     * 
     * @return the speed dial code
     **/
    public String getSpeedDialCode() {
        return this.speedDialCode;
    }
    
    /**
     * Retrieves the unformatted telephone number
     * 
     * @return the unformatted telephone number
     **/
    public String getUnformattedTelephoneNumber() {
        return this.unformattedTelephoneNumber;
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
     * Retrieves the Protection Code
     * 
     * @return the Protection Code
     **/
    public CWE getProtectionCode() {
        return this.protectionCode;
    }
    
    /**
     * Retrieves the Shared Telecommunication Identifier
     * 
     * @return the Shared Telecommunication Identifier
     **/
    public EI getSharedTelecommunicationIdentifier() {
        return this.sharedTelecommunicationIdentifier;
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
     * Modifies the number
     * 
     * @param number the new number
     **/
    public void setNumber(final String number) {
        this.number = number;
    }
    
    /**
     * Modifies the telecommunication use code
     * 
     * @param telecommunicationUseCode the new telecommunication use code
     **/
    public void setTelecommunicationUseCode(final String telecommunicationUseCode) {
        this.telecommunicationUseCode = telecommunicationUseCode;
    }
    
    /**
     * Modifies the telecommunication equipment type
     * 
     * @param telecommunicationEquipmentType the new telecommunication equipment type
     **/
    public void setTelecommunicationEquipmentType(final String telecommunicationEquipmentType) {
        this.telecommunicationEquipmentType = telecommunicationEquipmentType;
    }
    
    /**
     * Modifies the email address
     * 
     * @param emailAddress the new email address
     **/
    public void setEmailAddress(final String emailAddress) {
        this.emailAddress = emailAddress;
    }
    
    /**
     * Modifies the country code
     * 
     * @param countryCode the new country code
     **/
    public void setCountryCode(final NM countryCode) {
        this.countryCode = countryCode;
    }
    
    /**
     * Modifies the area city code
     * 
     * @param areaCityCode the new area city code
     **/
    public void setAreaCityCode(final NM areaCityCode) {
        this.areaCityCode = areaCityCode;
    }
    
    /**
     * Modifies the phone number
     * 
     * @param phoneNumber the new phone number
     **/
    public void setPhoneNumber(final NM phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    /**
     * Modifies the extension
     * 
     * @param extension the new extension
     **/
    public void setExtension(final NM extension) {
        this.extension = extension;
    }
    
    /**
     * Modifies the any text
     * 
     * @param anyText the new any text
     **/
    public void setAnyText(final String anyText) {
        this.anyText = anyText;
    }
    
    /**
     * Modifies the extension prefix
     * 
     * @param extensionPrefix the new extension prefix
     **/
    public void setExtensionPrefix(final String extensionPrefix) {
        this.extensionPrefix = extensionPrefix;
    }
    
    /**
     * Modifies the speed dial code
     * 
     * @param speedDialCode the new speed dial code
     **/
    public void setSpeedDialCode(final String speedDialCode) {
        this.speedDialCode = speedDialCode;
    }
    
    /**
     * Modifies the unformatted telephone number
     * 
     * @param unformattedTelephoneNumber the new unformatted telephone number
     **/
    public void setUnformattedTelephoneNumber(final String unformattedTelephoneNumber) {
        this.unformattedTelephoneNumber = unformattedTelephoneNumber;
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
     * Modifies the Protection Code
     * 
     * @param protectionCode the new Protection Code
     **/
    public void setProtectionCode(final CWE protectionCode) {
        this.protectionCode = protectionCode;
    }
    
    /**
     * Modifies the Shared Telecommunication Identifier
     * 
     * @param sharedTelecommunicationIdentifier the new Shared Telecommunication Identifier
     **/
    public void setSharedTelecommunicationIdentifier(final EI sharedTelecommunicationIdentifier) {
        this.sharedTelecommunicationIdentifier = sharedTelecommunicationIdentifier;
    }
    
    /**
     * Modifies the Preference Order
     * 
     * @param preferenceOrder the new Preference Order
     **/
    public void setPreferenceOrder(final NM preferenceOrder) {
        this.preferenceOrder = preferenceOrder;
    }
}
