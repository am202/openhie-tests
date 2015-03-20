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
import org.regenstrief.hl7.datatype.HD;
import org.regenstrief.hl7.datatype.HL7DataType;

/**
 * <p>
 * Title: AbstractScopeData
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
public abstract class AbstractScopeData extends HL7DataType implements ScopeData {
    
    protected String namespaceID = null;
    
    protected String universalID = null;
    
    protected String universalIDType = null;
    
    /**
     * Constructs an empty AbstractScopeData
     * 
     * @param prop the HL7Properties
     **/
    public AbstractScopeData(final HL7Properties prop) {
        super(prop);
    }
    
    /**
     * Retrieves the namespace ID
     * 
     * @return the namespace ID
     **/
    @Override
    public String getNamespaceID() {
        return this.namespaceID;
    }
    
    /**
     * Retrieves the universal ID
     * 
     * @return the universal ID
     **/
    @Override
    public String getUniversalID() {
        return this.universalID;
    }
    
    /**
     * Retrieves the universal ID type
     * 
     * @return the universal ID type
     **/
    @Override
    public String getUniversalIDType() {
        return this.universalIDType;
    }
    
    /**
     * Modifies the namespace ID
     * 
     * @param namespaceID the new namespace ID
     **/
    @Override
    public void setNamespaceID(final String namespaceID) {
        this.namespaceID = namespaceID;
    }
    
    /**
     * Modifies the universal ID
     * 
     * @param universalID the new universal ID
     **/
    @Override
    public void setUniversalID(final String universalID) {
        this.universalID = universalID;
    }
    
    /**
     * Modifies the universal ID type
     * 
     * @param universalIDType the new universal ID type
     **/
    @Override
    public void setUniversalIDType(final String universalIDType) {
        this.universalIDType = universalIDType;
    }
    
    /**
     * Retrieves the namespace ID
     * 
     * @param hd the HD
     * @return the namespace ID
     **/
    public static String getNamespaceID(final HD hd) {
        return hd == null ? null : hd.getNamespaceID();
    }
}
