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

import java.util.List;

import org.regenstrief.util.Util;

/**
 * Title: HL7 Data Tree Iterator Description: Provides access to HL7 data; can be extended to
 * provide stream access. Copyright: Copyright (c) 2005 Company: Regenstrief Institute
 * 
 * @author Andrew Martin
 * @version 1.0
 **/
public class HL7DataTreeIterator {
    
    protected List<HL7DataTree> list = null;
    
    protected HL7DataTree next = null;
    
    /**
     * Constructs a new HL7DataTreeIterator
     * 
     * @param list the List of HL7 data
     **/
    public HL7DataTreeIterator(final List<HL7DataTree> list) {
        this.list = list;
        this.next = Util.size(list) == 0 ? null : list.get(0);
    }
    
    /**
     * Retrieves the next HL7DataTree, or null if none is left
     * 
     * @return the next HL7DataTree, or null if none is left
     **/
    public HL7DataTree next() {
        final int size = Util.size(this.list), i = this.next == null ? size : this.list.indexOf(this.next) + 1;
        final HL7DataTree curr = this.next;
        
        this.next = i == size ? more() : this.list.get(i);
        
        return curr;
    }
    
    /**
     * Can be over-ridden to create more data from an InputStream or Reader
     * 
     * @return the next HL7DataTree, or null if none is left
     **/
    protected HL7DataTree more() {
        return null;
    }
}
