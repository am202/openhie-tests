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
package org.regenstrief.util.criterion;

import org.regenstrief.util.XMLUtil;
import org.w3c.dom.Node;

public class LocalNameCriterion implements Criterion {
    
    private final String name;
    
    private final boolean caseSensitive;
    
    public LocalNameCriterion(final String name, final boolean caseSensitive) {
        this.name = XMLUtil.getLocalName(name);
        this.caseSensitive = caseSensitive;
    }
    
    public LocalNameCriterion(final String name) {
        this(name, true);
    }
    
    @Override
    public boolean isMet(final Object o) {
        if (!(o instanceof Node)) {
            return false;
        }
        final String oname = XMLUtil.getLocalName((Node) o);
        return this.caseSensitive ? this.name.equals(oname) : this.name.equalsIgnoreCase(oname);
    }
    
    @Override
    public String toString() {
        return "localName.equals" + (this.caseSensitive ? "" : "IgnoreCase") + '(' + this.name + ')';
    }
}
