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
 * Title: MimeData
 * </p>
 * <p>
 * Description: Interface for HL7 MIME data
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
public interface MimeData extends BaseData {
    
    /**
     * Retrieves the type of data
     * 
     * @return the type of data
     **/
    public String getTypeOfData();
    
    /**
     * Retrieves the data subtype
     * 
     * @return the data subtype
     **/
    public String getDataSubtype();
    
    /**
     * Modifies the type of data
     * 
     * @param typeOfData the new type of data
     **/
    public void setTypeOfData(final String typeOfData);
    
    /**
     * Modifies the data subtype
     * 
     * @param dataSubtype the new data subtype
     **/
    public void setDataSubtype(final String dataSubtype);
}
