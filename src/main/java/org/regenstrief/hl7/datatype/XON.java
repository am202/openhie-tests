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
import org.regenstrief.hl7.util.AbstractCheckedIdData;

/**
 * <p>
 * Title: XON
 * </p>
 * <p>
 * Description: HL7 Extended Composite Name And Identification Number For Organizations
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
public class XON extends AbstractCheckedIdData {
    
    public final static String XON_XML = "XON";
    
    public final static String ORGANIZATION_NAME_XML = "XON.1";
    
    public final static String ORGANIZATION_NAME_TYPE_CODE_XML = "XON.2";
    
    public final static String ID_NUMBER_XML = "XON.3";
    
    public final static String CHECK_DIGIT_XML = "XON.4";
    
    public final static String CHECK_DIGIT_SCHEME_XML = "XON.5";
    
    public final static String ASSIGNING_AUTHORITY_XML = "XON.6";
    
    public final static String IDENTIFIER_TYPE_CODE_XML = "XON.7";
    
    public final static String ASSIGNING_FACILITY_XML = "XON.8";
    
    public final static String NAME_REPRESENTATION_CODE_XML = "XON.9";
    
    public final static String ORGANIZATION_IDENTIFIER_XML = "XON.10";
    
    private String organizationName = null;
    
    private CWE organizationNameTypeCode = null;
    
    private String identifierTypeCode = null;
    
    private HD assigningFacility = null;
    
    private String nameRepresentationCode = null;
    
    private String organizationIdentifier = null;
    
    /**
     * Constructs an empty XON
     * 
     * @param prop the HL7Properties
     **/
    public XON(final HL7Properties prop) {
        super(prop);
    }
    
    /**
     * Returns the name and id number, or only one if the other is null, or null if both are null
     * 
     * @return the name and id number, or only one if the other is null, or null if both are null
     **/
    @Override
    public String toDisplay() {
        final String id = super.toDisplay();
        
        if (this.organizationName != null) {
            String s = this.organizationName;
            if (id != null) {
                s += " (" + id + ')';
            }
            return s;
        } else {
            return id;
        }
    }
    
    /**
     * Retrieves the name of an XON
     * 
     * @param xon the XON
     * @return the name
     **/
    public static String getName(final XON xon) {
        return xon == null ? null : xon.getOrganizationName();
    }
    
    /**
     * Constructs an XON with the given organization name
     * 
     * @param prop the HL7Properties
     * @param organizationName the organization name
     * @return the XON
     **/
    public static XON create(final HL7Properties prop, final String organizationName) {
        if (organizationName == null) {
            return null;
        }
        
        final XON xon = new XON(prop);
        xon.setOrganizationName(organizationName);
        
        return xon;
    }
    
    /**
     * Determines if this is equal to the given Object
     * 
     * @param o the Object to compare to this
     * @return true if this equals o, false otherwise
     **/
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof XON)) {
            return false;
        }
        
        return equals((XON) o);
    }
    
    public boolean equals(final XON xon) {
        return xon == null ? false : this.organizationName.equals(xon.organizationName);
    }
    
    public static XON parsePiped(final HL7Parser parser, final String line, final int start, final char delim, final int stop) {
        if (stop <= start) {
            return null;
        }
        final XON xon = new XON(parser);
        xon.readPiped(parser, line, start, delim, stop);
        return xon;
    }
    
    @Override
    public void readPiped(final HL7Parser parser, final String line, int start, final char delim, final int stop) {
        int next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.organizationName = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        final char c = parser.getSubcomponentSeparator();
        this.organizationNameTypeCode = CWE.parsePiped(parser, line, start, c, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.idNumber = NM.parsePiped(parser, line, start, c, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.checkDigitNumber = NM.parsePiped(parser, line, start, c, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.checkDigitScheme = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.assigningAuthority = HD.parsePiped(parser, line, start, c, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.identifierTypeCode = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.assigningFacility = HD.parsePiped(parser, line, start, c, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.nameRepresentationCode = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.organizationIdentifier = getToken(line, start, next);
        
        assertLast(line, next + 1, delim, stop);
    }
    
    @Override
    public void toPiped(final Writer w, final int level) throws IOException {
        int last = 1;
        
        last = addComponent(w, this.organizationName, last, 1, level);
        last = addComponent(w, this.organizationNameTypeCode, last, 2, level);
        last = addComponent(w, this.idNumber, last, 3, level);
        last = addComponent(w, this.checkDigitNumber, last, 4, level);
        last = addComponent(w, this.checkDigitScheme, last, 5, level);
        last = addComponent(w, this.assigningAuthority, last, 6, level);
        last = addComponent(w, this.identifierTypeCode, last, 7, level);
        last = addComponent(w, this.assigningFacility, last, 8, level);
        last = addComponent(w, this.nameRepresentationCode, last, 9, level);
        last = addComponent(w, this.organizationIdentifier, last, 10, level);
    }
    
    /**
     * Retrieves the organization name
     * 
     * @return the organization name
     **/
    public String getOrganizationName() {
        return this.organizationName;
    }
    
    /**
     * Retrieves the organization name type code
     * 
     * @return the organization name type code
     **/
    public CWE getOrganizationNameTypeCode() {
        return this.organizationNameTypeCode;
    }
    
    /**
     * Retrieves the identifier type code
     * 
     * @return the identifier type code
     **/
    public String getIdentifierTypeCode() {
        return this.identifierTypeCode;
    }
    
    /**
     * Retrieves the assigning facility
     * 
     * @return the assigning facility
     **/
    public HD getAssigningFacility() {
        return this.assigningFacility;
    }
    
    /**
     * Retrieves the name representation code
     * 
     * @return the name representation code
     **/
    public String getNameRepresentationCode() {
        return this.nameRepresentationCode;
    }
    
    /**
     * Retrieves the organization identifier
     * 
     * @return the organization identifier
     **/
    public String getOrganizationIdentifier() {
        return this.organizationIdentifier;
    }
    
    /**
     * Modifies the organization name
     * 
     * @param organizationName the new organization name
     **/
    public void setOrganizationName(final String organizationName) {
        this.organizationName = organizationName;
    }
    
    /**
     * Modifies the organization name type code
     * 
     * @param organizationNameTypeCode the new organization name type code
     **/
    public void setOrganizationNameTypeCode(final CWE organizationNameTypeCode) {
        this.organizationNameTypeCode = organizationNameTypeCode;
    }
    
    /**
     * Modifies the identifier type code
     * 
     * @param identifierTypeCode the new identifier type code
     **/
    public void setIdentifierTypeCode(final String identifierTypeCode) {
        this.identifierTypeCode = identifierTypeCode;
    }
    
    /**
     * Modifies the assigning facility
     * 
     * @param assigningFacility the new assigning facility
     **/
    public void setAssigningFacility(final HD assigningFacility) {
        this.assigningFacility = assigningFacility;
    }
    
    /**
     * Modifies the name representation code
     * 
     * @param nameRepresentationCode the new name representation code
     **/
    public void setNameRepresentationCode(final String nameRepresentationCode) {
        this.nameRepresentationCode = nameRepresentationCode;
    }
    
    /**
     * Modifies the organization identifier
     * 
     * @param organizationIdentifier the new organization identifier
     **/
    public void setOrganizationIdentifier(final String organizationIdentifier) {
        this.organizationIdentifier = organizationIdentifier;
    }
}
