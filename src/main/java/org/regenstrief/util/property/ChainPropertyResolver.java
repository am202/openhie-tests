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
package org.regenstrief.util.property;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * ChainPropertyResolver
 */
public class ChainPropertyResolver implements PropertyResolver {
    
    private final List<PropertyResolver> chain = new ArrayList<PropertyResolver>();
    
    /**
     * @see org.regenstrief.util.property.PropertyResolver#getProperty(java.lang.String)
     */
    @Override
    public String getProperty(final String name) {
        for (final PropertyResolver r : this.chain) {
            if (r.hasProperty(name)) {
                return r.getProperty(name);
            }
        }
        return null;
    }
    
    /**
     * @see org.regenstrief.util.property.PropertyResolver#hasProperty(java.lang.String)
     */
    @Override
    public boolean hasProperty(final String name) {
        for (final PropertyResolver r : this.chain) {
            if (r.hasProperty(name)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public Set<String> getNames() {
        final Set<String> names = new HashSet<String>();
        for (final PropertyResolver r : this.chain) {
            names.addAll(r.getNames());
        }
        return names;
    }
    
    public void addFirstPriority(final PropertyResolver resolver) {
        this.chain.add(0, resolver);
    }
    
    public void addLastPriority(final PropertyResolver resolver) {
        this.chain.add(resolver);
    }
    
    public final List<PropertyResolver> getChain() {
        return Collections.unmodifiableList(this.chain);
    }
}
