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

import org.regenstrief.hl7.datatype.HD;

/**
 * <p>
 * Title: IdData
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
public interface IdData extends BaseData {
    
    /**
     * Retrieves the ID
     * 
     * @return the ID
     **/
    public String getID();
    
    /**
     * Retrieves the assigning authority
     * 
     * @return the assigning authority
     **/
    public HD getAssigningAuthority();
    
    /**
     * Modifies the ID
     * 
     * @param id the new ID
     **/
    public void setID(String id);
    
    /**
     * Modifies the assigning authority
     * 
     * @param assigningAuthority the new assigning authority
     **/
    public void setAssigningAuthority(HD assigningAuthority);
    
    /**
     * Retrieves the id plus a check digit (if any)
     * 
     * @return the id plus a check digit (if any)
     */
    public String getFullIdentifier();
}
