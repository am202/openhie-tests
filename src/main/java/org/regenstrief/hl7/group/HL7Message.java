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
package org.regenstrief.hl7.group;

import org.regenstrief.hl7.HL7Properties;

/**
 * <p>
 * Title: HL7Message
 * </p>
 * <p>
 * Description: An HL7 message
 * </p>
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * <p>
 * Company: Regenstrief Institute
 * </p>
 * 
 * @author Andrew Martin
 * @version 1.0
 */
public abstract class HL7Message extends HL7Group {
    
    public HL7Message(final HL7Properties prop) {
        super(prop);
    }
    
    @Override
    public String getGroupName() {
        return getClassName();
    }
}
