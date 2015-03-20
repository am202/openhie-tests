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
 * Title: AbstractIdData
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
public abstract class AbstractIdData extends HL7DataType implements IdData {
    
    protected String id = null;
    
    protected HD assigningAuthority = null;
    
    /**
     * Constructs an empty AbstractIdData
     * 
     * @param prop the HL7Properties
     **/
    public AbstractIdData(final HL7Properties prop) {
        super(prop);
    }
    
    /**
     * Retrieves the ID
     * 
     * @return the ID
     **/
    @Override
    public String getID() {
        return this.id;
    }
    
    /**
     * Retrieves the assigning authority
     * 
     * @return the assigning authority
     **/
    @Override
    public HD getAssigningAuthority() {
        return this.assigningAuthority;
    }
    
    /**
     * Modifies the ID
     * 
     * @param id the new ID
     **/
    @Override
    public void setID(final String id) {
        this.id = id;
    }
    
    /**
     * Modifies the assigning authority
     * 
     * @param assigningAuthority the new assigning authority
     **/
    @Override
    public void setAssigningAuthority(final HD assigningAuthority) {
        this.assigningAuthority = assigningAuthority;
    }
    
    public static String getID(final AbstractIdData data) {
        return data == null ? null : data.getID();
    }
    
    /**
     * Retrieves the id; subclasses might include a check digit
     * 
     * @return the id
     */
    @Override
    public String getFullIdentifier() {
        return this.id;
    }
    
    public final static String getFullIdentifier(final IdData data) {
        return data == null ? null : data.getFullIdentifier();
    }
}
