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
 * Title: UMSG_Z01
 * </p>
 * <p>
 * Description: Unrecognized HL7 message This allows us to parse a piped HL7 message with an
 * unrecognized message type.
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
public class UMSG_Z01 extends HL7Message {
    
    private final String name;
    
    /**
     * Constructs an empty UMSG_Z01
     * 
     * @param prop the HL7Properties
     **/
    public UMSG_Z01(final HL7Properties prop) {
        this(prop, null);
    }
    
    /**
     * Constructs an empty UMSG_Z01 with the given name
     * 
     * @param prop the HL7Properties
     * @param name the name
     **/
    public UMSG_Z01(final HL7Properties prop, final String name) {
        super(prop);
        this.name = name == null ? super.getGroupName() : name;
    }
    
    @Override
    public final String getGroupName() {
        return this.name;
    }
}
