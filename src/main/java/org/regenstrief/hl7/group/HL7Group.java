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

import org.regenstrief.hl7.HL7Data;
import org.regenstrief.hl7.HL7DataTree;
import org.regenstrief.hl7.HL7Properties;

/**
 * <p>
 * Title: HL7Group
 * </p>
 * <p>
 * Description: An HL7 group
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
public abstract class HL7Group extends HL7Data {
    
    /**
     * Constructs a new HL7Group with the given HL7Properties
     * 
     * @param prop the HL7Properties
     **/
    public HL7Group(final HL7Properties prop) {
        super(prop);
    }
    
    // These are similar to HL7Segment.
    // Need to refactor.
    
    /**
     * Retrieves the piped representation of the object
     * 
     * @return the piped representation of the object
     **/
    @Override
    public final String toPiped() {
        return "";
    }
    
    @Override
    public final String getTagName() {
        return getGroupName(); // Force subclasses to do something other than HL7Data.getTagName() which won't work for groups
    }
    
    protected abstract String getGroupName();
    
    public HL7DataTree addRequiredChildren(final HL7DataTree tree) {
        return tree;
    }
}
