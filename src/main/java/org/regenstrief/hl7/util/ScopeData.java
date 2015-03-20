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

/**
 * <p>
 * Title: ScopeData
 * </p>
 * <p>
 * Description: Interface for HL7 scoping/namespace data
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
public interface ScopeData extends BaseData {
    
    /**
     * Retrieves the namespace ID
     * 
     * @return the namespace ID
     **/
    public String getNamespaceID();
    
    /**
     * Retrieves the universal ID
     * 
     * @return the universal ID
     **/
    public String getUniversalID();
    
    /**
     * Retrieves the universal ID type
     * 
     * @return the universal ID type
     **/
    public String getUniversalIDType();
    
    /**
     * Modifies the namespace ID
     * 
     * @param namespaceID the new namespace ID
     **/
    public void setNamespaceID(String namespaceID);
    
    /**
     * Modifies the universal ID
     * 
     * @param universalID the new universal ID
     **/
    public void setUniversalID(String universalID);
    
    /**
     * Modifies the universal ID type
     * 
     * @param universalIDType the new universal ID type
     **/
    public void setUniversalIDType(String universalIDType);
}
