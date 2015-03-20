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
package org.regenstrief.hl7.util;

import org.regenstrief.hl7.HL7Properties;
import org.regenstrief.hl7.datatype.CWE;
import org.regenstrief.hl7.datatype.HD;
import org.regenstrief.util.Util;

/**
 * <p>
 * Title: AbstractExpandedIdData
 * </p>
 * <p>
 * Description: Interface for HL7 identifier data
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: Regenstrief Institute
 * </p>
 * 
 * @author Andrew Martin
 * @version 1.0
 */
public abstract class AbstractExpandedIdData extends AbstractIdData implements ExpandedIdData {
    
    protected String checkDigit = null;
    
    protected String checkDigitScheme = null;
    
    protected String identifierTypeCode = null;
    
    protected HD assigningFacility = null;
    
    protected CWE assigningJurisdiction = null;
    
    protected CWE assigningAgencyOrDepartment = null;
    
    protected String securityCheck = null;
    
    protected String securityCheckScheme = null;
    
    /**
     * Constructs an empty AbstractExpandedIdData
     * 
     * @param prop the HL7Properties
     **/
    public AbstractExpandedIdData(final HL7Properties prop) {
        super(prop);
    }
    
    /**
     * Retrieves the check digit
     * 
     * @return the check digit
     **/
    @Override
    public String getCheckDigit() {
        return this.checkDigit;
    }
    
    /**
     * Retrieves the check digit scheme
     * 
     * @return the check digit scheme
     **/
    @Override
    public String getCheckDigitScheme() {
        return this.checkDigitScheme;
    }
    
    /**
     * Retrieves the identifier type code
     * 
     * @return the identifier type code
     **/
    @Override
    public String getIdentifierTypeCode() {
        return this.identifierTypeCode;
    }
    
    /**
     * Retrieves the assigning facility
     * 
     * @return the assigning facility
     **/
    @Override
    public HD getAssigningFacility() {
        return this.assigningFacility;
    }
    
    /**
     * Retrieves the assigning jurisdiction
     * 
     * @return the assigning jurisdiction
     **/
    @Override
    public CWE getAssigningJurisdiction() {
        return this.assigningJurisdiction;
    }
    
    /**
     * Retrieves the assigning agency or department
     * 
     * @return the assigning agency or department
     **/
    @Override
    public CWE getAssigningAgencyOrDepartment() {
        return this.assigningAgencyOrDepartment;
    }
    
    /**
     * Retrieves the security check
     * 
     * @return the security check
     **/
    @Override
    public String getSecurityCheck() {
        return this.securityCheck;
    }
    
    /**
     * Retrieves the security check scheme
     * 
     * @return the security check scheme
     **/
    @Override
    public String getSecurityCheckScheme() {
        return this.securityCheckScheme;
    }
    
    /**
     * Modifies the check digit
     * 
     * @param checkDigit the new check digit
     **/
    @Override
    public void setCheckDigit(final String checkDigit) {
        this.checkDigit = checkDigit;
    }
    
    /**
     * Modifies the the new check digit scheme
     * 
     * @param checkDigitScheme the new check digit scheme
     **/
    @Override
    public void setCheckDigitScheme(final String checkDigitScheme) {
        this.checkDigitScheme = checkDigitScheme;
    }
    
    /**
     * Modifies the identifier type code
     * 
     * @param identifierTypeCode the new identifier type code
     **/
    @Override
    public void setIdentifierTypeCode(final String identifierTypeCode) {
        this.identifierTypeCode = identifierTypeCode;
    }
    
    /**
     * Modifies the assigning facility
     * 
     * @param assigningFacility the new assigning facility
     **/
    @Override
    public void setAssigningFacility(final HD assigningFacility) {
        this.assigningFacility = assigningFacility;
    }
    
    /**
     * Modifies the assigning jurisdiction
     * 
     * @param assigningJurisdiction the new assigning jurisdiction
     **/
    @Override
    public void setAssigningJurisdiction(final CWE assigningJurisdiction) {
        this.assigningJurisdiction = assigningJurisdiction;
    }
    
    /**
     * Modifies the assigning agency or department
     * 
     * @param assigningAgencyOrDepartment the new assigning agency or department
     **/
    @Override
    public void setAssigningAgencyOrDepartment(final CWE assigningAgencyOrDepartment) {
        this.assigningAgencyOrDepartment = assigningAgencyOrDepartment;
    }
    
    /**
     * Modifies the security check
     * 
     * @param securityCheck the new security check
     **/
    @Override
    public void setSecurityCheck(final String securityCheck) {
        this.securityCheck = securityCheck;
    }
    
    /**
     * Modifies the security check scheme
     * 
     * @param securityCheckScheme the new security check scheme
     **/
    @Override
    public void setSecurityCheckScheme(final String securityCheckScheme) {
        this.securityCheckScheme = securityCheckScheme;
    }
    
    /**
     * Retrieves the id plus a check digit (if any)
     * 
     * @return the id plus a check digit (if any)
     */
    @Override
    public String getFullIdentifier() {
        return getFullIdentifier(getID(), this.checkDigit);
    }
    
    /*package*/ final static String getFullIdentifier(final String id, final String checkDigit) {
        return Util.concatWithDelim(id, checkDigit, "");
    }
}
