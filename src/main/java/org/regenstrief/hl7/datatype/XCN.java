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
import org.regenstrief.hl7.util.AbstractExpandedIdNameData;

/**
 * <p>
 * Title: XCN
 * </p>
 * <p>
 * Description: HL7 Extended Composite ID Number And Name For Persons
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
public class XCN extends AbstractExpandedIdNameData {
    
    public final static String XCN_XML = "XCN";
    
    public final static String ID_NUMBER_XML = "XCN.1";
    
    public final static String FAMILY_NAME_XML = "XCN.2";
    
    public final static String GIVEN_NAME_XML = "XCN.3";
    
    public final static String MIDDLE_INITIAL_OR_NAME_XML = "XCN.4";
    
    public final static String SUFFIX_XML = "XCN.5";
    
    public final static String PREFIX_XML = "XCN.6";
    
    public final static String DEGREE_XML = "XCN.7";
    
    public final static String SOURCE_TABLE_XML = "XCN.8";
    
    public final static String ASSIGNING_AUTHORITY_XML = "XCN.9";
    
    public final static String NAME_TYPE_CODE_XML = "XCN.10";
    
    public final static String IDENTIFIER_CHECK_DIGIT_XML = "XCN.11";
    
    public final static String CODE_IDENTIFYING_THE_CHECK_DIGIT_SCHEME_EMPLOYED_XML = "XCN.12";
    
    public final static String IDENTIFIER_TYPE_CODE_XML = "XCN.13";
    
    public final static String ASSIGNING_FACILITY_XML = "XCN.14";
    
    public final static String NAME_REPRESENTATION_CODE_XML = "XCN.15";
    
    public final static String NAME_CONTEXT_XML = "XCN.16";
    
    public final static String NAME_VALIDITY_RANGE_XML = "XCN.17";
    
    public final static String NAME_ASSEMBLY_ORDER_XML = "XCN.18";
    
    public final static String EFFECTIVE_DATE_XML = "XCN.19";
    
    public final static String EXPIRATION_DATE_XML = "XCN.20";
    
    public final static String PROFESSIONAL_SUFFIX_XML = "XCN.21";
    
    public final static String ASSIGNING_JURISDICTION_XML = "XCN.22";
    
    public final static String ASSIGNING_AGENCY_OR_DEPARTMENT_XML = "XCN.23";
    
    public final static String SECURITY_CHECK_XML = "XCN.24";
    
    public final static String SECURITY_CHECK_SCHEME_XML = "XCN.25";
    
    /**
     * Constructs an empty XCN
     * 
     * @param prop the HL7Properties
     **/
    public XCN(final HL7Properties prop) {
        super(prop);
    }
    
    /**
     * Constructs an XCN from the given first, last, and middle name and suffix/prefix
     * 
     * @param prop the HL7Properties
     * @param familyName the last name
     * @param givenName the first name
     * @param middleInitialOrName the middle initial or name
     * @param suffix the suffix
     * @param prefix the prefix
     **/
    public XCN(final HL7Properties prop, final String familyName, final String givenName, final String middleInitialOrName,
        final String suffix, final String prefix) {
        this(prop);
        setName(familyName, givenName, middleInitialOrName, suffix, prefix);
    }
    
    /**
     * Constructs an XCN from the given first, last, and middle name
     * 
     * @param prop the HL7Properties
     * @param familyName the last name
     * @param givenName the first name
     * @param middleInitialOrName the middle initial or name
     **/
    public XCN(final HL7Properties prop, final String familyName, final String givenName, final String middleInitialOrName) {
        this(prop, familyName, givenName, middleInitialOrName, null, null);
    }
    
    /**
     * Constructs an XCN from the given first and last name
     * 
     * @param prop the HL7Properties
     * @param familyName the last name
     * @param givenName the first name
     **/
    public XCN(final HL7Properties prop, final String familyName, final String givenName) {
        this(prop, familyName, givenName, null, null, null);
    }
    
    public static XCN parsePiped(final HL7Parser parser, final String line, final int start, final char delim, final int stop) {
        if (stop <= start) {
            return null;
        }
        final XCN xcn = new XCN(parser);
        xcn.readPiped(parser, line, start, delim, stop);
        return xcn;
    }
    
    @Override
    public void readPiped(final HL7Parser parser, final String line, int start, final char delim, final int stop) {
        int next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.id = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        final char c = parser.getSubcomponentSeparator();
        this.familyName = FN.parsePiped(parser, line, start, c, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.givenName = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.middleInitialOrName = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.suffix = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.prefix = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.degree = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.sourceTable = CWE.parsePiped(parser, line, start, c, next);
        
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
        this.nameTypeCode = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.checkDigit = getToken(line, start, next);
        
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
        this.nameContext = CE.parsePiped(parser, line, start, c, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.nameValidityRange = DR.parsePiped(parser, line, start, c, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.nameAssemblyOrder = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.effectiveDate = TS.parsePiped(parser, line, start, c, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.expirationDate = TS.parsePiped(parser, line, start, c, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.professionalSuffix = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.assigningJurisdiction = CWE.parsePiped(parser, line, start, c, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.assigningAgencyOrDepartment = CWE.parsePiped(parser, line, start, c, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.securityCheck = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.securityCheckScheme = getToken(line, start, next);
        
        assertLast(line, next + 1, delim, stop);
    }
    
    @Override
    public void toPiped(final Writer w, final int level) throws IOException {
        int last = 1;
        
        last = addComponent(w, this.id, last, 1, level);
        last = addComponent(w, this.familyName, last, 2, level);
        last = addComponent(w, this.givenName, last, 3, level);
        last = addComponent(w, this.middleInitialOrName, last, 4, level);
        last = addComponent(w, this.suffix, last, 5, level);
        last = addComponent(w, this.prefix, last, 6, level);
        last = addComponent(w, this.degree, last, 7, level);
        last = addComponent(w, this.sourceTable, last, 8, level);
        last = addComponent(w, this.assigningAuthority, last, 9, level);
        last = addComponent(w, this.nameTypeCode, last, 10, level);
        last = addComponent(w, this.checkDigit, last, 11, level);
        last = addComponent(w, this.checkDigitScheme, last, 12, level);
        last = addComponent(w, this.identifierTypeCode, last, 13, level);
        last = addComponent(w, this.assigningFacility, last, 14, level);
        last = addComponent(w, this.nameRepresentationCode, last, 15, level);
        last = addComponent(w, this.nameContext, last, 16, level);
        last = addComponent(w, this.nameValidityRange, last, 17, level);
        last = addComponent(w, this.nameAssemblyOrder, last, 18, level);
        last = addComponent(w, this.effectiveDate, last, 19, level);
        last = addComponent(w, this.expirationDate, last, 20, level);
        last = addComponent(w, this.professionalSuffix, last, 21, level);
        last = addComponent(w, this.assigningJurisdiction, last, 22, level);
        last = addComponent(w, this.assigningAgencyOrDepartment, last, 23, level);
        last = addComponent(w, this.securityCheck, last, 24, level);
        last = addComponent(w, this.securityCheckScheme, last, 25, level);
    }
    
    /**
     * Retrieves the component at the given index
     * 
     * @param i the index
     * @return the component
     **/
    @Override
    public Object get(final int i) {
        switch (i) {
            case 1:
                return this.id;
            case 2:
                return this.familyName;
            case 3:
                return this.givenName;
            case 4:
                return this.middleInitialOrName;
            case 5:
                return this.suffix;
            case 6:
                return this.prefix;
            case 7:
                return this.degree;
            case 8:
                return this.sourceTable;
            case 9:
                return this.assigningAuthority;
            case 10:
                return this.nameTypeCode;
            case 11:
                return this.checkDigit;
            case 12:
                return this.checkDigitScheme;
            case 13:
                return this.identifierTypeCode;
            case 14:
                return this.assigningFacility;
            case 15:
                return this.nameRepresentationCode;
            case 16:
                return this.nameContext;
            case 17:
                return this.nameValidityRange;
            case 18:
                return this.nameAssemblyOrder;
            case 19:
                return this.effectiveDate;
            case 20:
                return this.expirationDate;
            case 21:
                return this.professionalSuffix;
            case 22:
                return this.assigningJurisdiction;
            case 23:
                return this.assigningAgencyOrDepartment;
            case 24:
                return this.securityCheck;
            case 25:
                return this.securityCheckScheme;
        }
        throw new ArrayIndexOutOfBoundsException(i);
    }
}
