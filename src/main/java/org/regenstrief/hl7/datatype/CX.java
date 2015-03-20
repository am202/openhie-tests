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
import org.regenstrief.hl7.util.AbstractExpandedIdData;
import org.regenstrief.person.PersonIdentifier;
import org.regenstrief.util.Util;

/**
 * <p>
 * Title: CX
 * </p>
 * <p>
 * Description: HL7 Extended Composite ID With Check Digit
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
public class CX extends AbstractExpandedIdData {
    
    public final static String CX_XML = "CX";
    
    public final static String ID_XML = "CX.1";
    
    public final static String CHECK_DIGIT_XML = "CX.2";
    
    public final static String CODE_IDENTIFYING_THE_CHECK_DIGIT_SCHEME_EMPLOYED_XML = "CX.3";
    
    public final static String ASSIGNING_AUTHORITY_XML = "CX.4";
    
    public final static String IDENTIFIER_TYPE_CODE_XML = "CX.5";
    
    public final static String ASSIGNING_FACILITY_XML = "CX.6";
    
    public final static String EFFECTIVE_DATE_XML = "CX.7";
    
    public final static String EXPIRATION_DATE_XML = "CX.8";
    
    public final static String ASSIGNING_JURISDICTION_XML = "CX.9";
    
    public final static String ASSIGNING_AGENCY_OR_DEPARTMENT_XML = "CX.10";
    
    public final static String SECURITY_CHECK_XML = "CX.11";
    
    public final static String SECURITY_CHECK_SCHEME_XML = "CX.12";
    
    private DT effectiveDate = null;
    
    private DT expirationDate = null;
    
    /**
     * Constructs an empty CX
     * 
     * @param prop the HL7Properties
     **/
    public CX(final HL7Properties prop) {
        super(prop);
    }
    
    public final static CX create(final HL7Properties prop, final String id, final String namespaceID) {
        if (id == null) {
            return null;
        }
        final CX cx = new CX(prop);
        cx.id = id;
        cx.assigningAuthority = HD.createHD(prop, namespaceID);
        return cx;
    }
    
    /**
     * Converts this into an XCN
     * 
     * @return this as an XCN
     **/
    public XCN toXCN() {
        final XCN xcn = new XCN(this.prop);
        
        xcn.setID(this.id);
        xcn.setCheckDigit(this.checkDigit);
        xcn.setCheckDigitScheme(this.checkDigitScheme);
        xcn.setAssigningAuthority(this.assigningAuthority);
        xcn.setIdentifierTypeCode(this.identifierTypeCode);
        xcn.setAssigningFacility(this.assigningFacility);
        xcn.setSecurityCheck(this.securityCheck);
        xcn.setSecurityCheckScheme(this.securityCheckScheme);
        
        return xcn;
    }
    
    public static CX parsePiped(final HL7Parser parser, final String line, final int start, final char delim, final int stop) {
        if (stop <= start) {
            return null;
        }
        final CX cx = new CX(parser);
        cx.readPiped(parser, line, start, delim, stop);
        return cx;
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
        final char c = parser.getSubcomponentSeparator();
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
        this.effectiveDate = DT.parsePiped(parser, line, start, c, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.expirationDate = DT.parsePiped(parser, line, start, c, next);
        
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
        last = addComponent(w, this.checkDigit, last, 2, level);
        last = addComponent(w, this.checkDigitScheme, last, 3, level);
        last = addComponent(w, this.assigningAuthority, last, 4, level);
        last = addComponent(w, this.identifierTypeCode, last, 5, level);
        last = addComponent(w, this.assigningFacility, last, 6, level);
        last = addComponent(w, this.effectiveDate, last, 7, level);
        last = addComponent(w, this.expirationDate, last, 8, level);
        last = addComponent(w, this.assigningJurisdiction, last, 9, level);
        last = addComponent(w, this.assigningAgencyOrDepartment, last, 10, level);
        last = addComponent(w, this.securityCheck, last, 11, level);
        last = addComponent(w, this.securityCheckScheme, last, 12, level);
    }
    
    /**
     * Compares to the given Object
     * 
     * @param o the Object to compare to this
     * @return true if this equals the given Object
     **/
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof CX)) {
            return false;
        }
        final CX cx = (CX) o;
        if (!Util.equals(this.checkDigit, cx.getCheckDigit())) {
            return false;
        }
        final String id = this.id, cid = cx.getID();
        return Util.equals(id, cid) || Util.equals(PersonIdentifier.format(id), PersonIdentifier.format(cid));
    }
    
    /**
     * Retrieves the effective date
     * 
     * @return the effective date
     **/
    public DT getEffectiveDate() {
        return this.effectiveDate;
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
     * Modifies the effective date
     * 
     * @param effectiveDate the new effective date
     **/
    public void setEffectiveDate(final DT effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
    
    /**
     * Modifies the expiration date
     * 
     * @param expirationDate the new expiration date
     **/
    public void setExpirationDate(final DT expirationDate) {
        this.expirationDate = expirationDate;
    }
}
