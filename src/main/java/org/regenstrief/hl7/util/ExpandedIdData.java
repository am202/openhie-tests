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

import org.regenstrief.hl7.datatype.CWE;
import org.regenstrief.hl7.datatype.HD;

/**
 * <p>
 * Title: ExpandedIdData
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
public interface ExpandedIdData extends CheckedIdData {
    
    /**
     * Retrieves the identifier type code
     * 
     * @return the identifier type code
     **/
    public String getIdentifierTypeCode();
    
    /**
     * Retrieves the assigning facility
     * 
     * @return the assigning facility
     **/
    public HD getAssigningFacility();
    
    /**
     * Retrieves the assigning jurisdiction
     * 
     * @return the assigning jurisdiction
     **/
    public CWE getAssigningJurisdiction();
    
    /**
     * Retrieves the assigning agency or department
     * 
     * @return the assigning agency or department
     **/
    public CWE getAssigningAgencyOrDepartment();
    
    /**
     * Retrieves the security check
     * 
     * @return the security check
     **/
    public String getSecurityCheck();
    
    /**
     * Retrieves the security check scheme
     * 
     * @return the security check scheme
     **/
    public String getSecurityCheckScheme();
    
    /**
     * Modifies the identifier type code
     * 
     * @param identifierTypeCode the new identifier type code
     **/
    public void setIdentifierTypeCode(String identifierTypeCode);
    
    /**
     * Modifies the assigning facility
     * 
     * @param assigningFacility the new assigning facility
     **/
    public void setAssigningFacility(HD assigningFacility);
    
    /**
     * Modifies the assigning jurisdiction
     * 
     * @param assigningJurisdiction the new assigning jurisdiction
     **/
    public void setAssigningJurisdiction(final CWE assigningJurisdiction);
    
    /**
     * Modifies the assigning agency or department
     * 
     * @param assigningAgencyOrDepartment the new assigning agency or department
     **/
    public void setAssigningAgencyOrDepartment(final CWE assigningAgencyOrDepartment);
    
    /**
     * Modifies the security check
     * 
     * @param securityCheck the new security check
     **/
    public void setSecurityCheck(String securityCheck);
    
    /**
     * Modifies the security check scheme
     * 
     * @param securityCheckScheme the new security check scheme
     **/
    public void setSecurityCheckScheme(String securityCheckScheme);
}
