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
package org.regenstrief.hl7;

/**
 * Title: HL7 Properties Description: Properties found in HL7 importer and exporter, required for
 * various HL7 methods Copyright: Copyright (c) 2005 Company: Regenstrief Institute
 * 
 * @author Andrew Martin
 * @version 1.0
 **/
public interface HL7Properties {
    
    /**
     * Retrieves the message's properties
     * 
     * @return the message's properties
     **/
    public MessageProperties getMessageProp();
}
