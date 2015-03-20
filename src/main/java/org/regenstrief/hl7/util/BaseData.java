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

/**
 * <p>
 * Title: BaseData
 * </p>
 * <p>
 * Description: A piece of data from an HL7 message
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
public interface BaseData {
    
    /**
     * Retrieves the piped representation of the object
     * 
     * @return the piped representation of the object
     **/
    public String toPiped();
    
    /**
     * Retrieves the display representation of the object
     * 
     * @return the display representation of the object
     **/
    public String toDisplay();
    
    /**
     * Retrieves the HL7Properties
     * 
     * @return the HL7Properties
     **/
    public HL7Properties getProp();
    
    /**
     * Clears the BaseData
     **/
    public void clear();
}
