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

import org.regenstrief.hl7.datatype.CE;
import org.regenstrief.hl7.datatype.DR;
import org.regenstrief.hl7.datatype.TS;


/**
 * <p>
 * Title: ExpandedNameData
 * </p>
 * <p>
 * Description: Interface for HL7 name data
 * </p>
 * <p>
 * Copyright: Copyright (c) 2010
 * </p>
 * <p>
 * Company: Regenstrief Institute
 * </p>
 * 
 * @author Andrew Martin
 * @version 1.0
 */
public interface ExpandedNameData extends NameData {
    
    /**
     * Retrieves the name type code
     * 
     * @return the name type code
     **/
    public String getNameTypeCode();
    
    /**
     * Retrieves the name representation code
     * 
     * @return the name representation code
     **/
    public String getNameRepresentationCode();
    
    /**
     * Retrieves the name context
     * 
     * @return the name context
     **/
    public CE getNameContext();
    
    /**
     * Retrieves the name validity range
     * 
     * @return the name validity range
     **/
    public DR getNameValidityRange();
    
    /**
     * Retrieves the name assembly order
     * 
     * @return the name assembly order
     **/
    public String getNameAssemblyOrder();
    
    /**
     * Retrieves the effective date
     * 
     * @return the effective date
     **/
    public TS getEffectiveDate();
    
    /**
     * Retrieves the expiration date
     * 
     * @return the expiration date
     **/
    public TS getExpirationDate();
    
    /**
     * Retrieves the professional suffix
     * 
     * @return the professional suffix
     **/
    public String getProfessionalSuffix();
    
    /**
     * Modifies the name type code
     * 
     * @param nameTypeCode the new name type code
     **/
    public void setNameTypeCode(final String nameTypeCode);
    
    /**
     * Modifies the name representation code
     * 
     * @param nameRepresentationCode the new name representation code
     **/
    public void setNameRepresentationCode(final String nameRepresentationCode);
    
    /**
     * Modifies the name context
     * 
     * @param nameContext the new name context
     **/
    public void setNameContext(final CE nameContext);
    
    /**
     * Modifies the name validity range
     * 
     * @param nameValidityRange the new name validity range
     **/
    public void setNameValidityRange(final DR nameValidityRange);
    
    /**
     * Modifies the name assembly order
     * 
     * @param nameAssemblyOrder the new name assembly order
     **/
    public void setNameAssemblyOrder(final String nameAssemblyOrder);
    
    /**
     * Modifies the effective date
     * 
     * @param effectiveDate the new effective date
     **/
    public void setEffectiveDate(final TS effectiveDate);
    
    /**
     * Modifies the expiration date
     * 
     * @param expirationDate the new expiration date
     **/
    public void setExpirationDate(final TS expirationDate);
    
    /**
     * Modifies the professional suffix
     * 
     * @param professionalSuffix the new professional suffix
     **/
    public void setProfessionalSuffix(final String professionalSuffix);
}
