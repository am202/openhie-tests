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
import org.regenstrief.hl7.datatype.HL7DataType;

/**
 * <p>
 * Title: AbstractMimeData
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
public abstract class AbstractMimeData extends HL7DataType implements MimeData {
    
    protected String typeOfData = null;
    
    protected String dataSubtype = null;
    
    /**
     * Constructs an empty AbstractMimeData
     * 
     * @param prop the HL7Properties
     **/
    public AbstractMimeData(final HL7Properties prop) {
        super(prop);
    }
    
    /**
     * Retrieves the type of data
     * 
     * @return the type of data
     **/
    @Override
    public String getTypeOfData() {
        return this.typeOfData;
    }
    
    /**
     * Retrieves the data subtype
     * 
     * @return the data subtype
     **/
    @Override
    public String getDataSubtype() {
        return this.dataSubtype;
    }
    
    /**
     * Modifies the type of data
     * 
     * @param typeOfData the new type of data
     **/
    @Override
    public void setTypeOfData(final String typeOfData) {
        this.typeOfData = typeOfData;
    }
    
    /**
     * Modifies the data subtype
     * 
     * @param dataSubtype the new data subtype
     **/
    @Override
    public void setDataSubtype(final String dataSubtype) {
        this.dataSubtype = dataSubtype;
    }
}
