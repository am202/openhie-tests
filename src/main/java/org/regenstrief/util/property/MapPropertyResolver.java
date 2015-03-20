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

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.regenstrief.util.Util;

/**
 * MapPropertyResolver
 */
public class MapPropertyResolver implements PropertyResolver {
    
    private final Map<?, ?> map;
    
    public MapPropertyResolver(final Map<?, ?> map) {
        this.map = map;
    }
    
    /**
     * @see org.regenstrief.util.property.PropertyResolver#getProperty(java.lang.String)
     */
    @Override
    public String getProperty(final String name) {
        return Util.toString(this.map.get(name));
    }

    /**
     * @see org.regenstrief.util.property.PropertyResolver#hasProperty(java.lang.String)
     */
    @Override
    public boolean hasProperty(final String name) {
        return this.map.containsKey(name);
    }
    
    @Override
    public Set<String> getNames() {
        final Set<?> keys = this.map.keySet();
        final Set<String> names = new HashSet<String>(keys.size());
        for (final Object key : keys) {
            names.add(Util.toString(key));
        }
        return names;
    }
}
