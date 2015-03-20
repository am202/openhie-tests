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

import java.util.Collections;
import java.util.Properties;
import java.util.Set;

/**
 * AbstractPropertyResolver
 */
public abstract class AbstractPropertyResolver implements PropertyResolver {
    
    protected abstract Properties getProperties();
    
    /**
     * @see org.regenstrief.util.property.PropertyResolver#getProperty(java.lang.String)
     */
    @Override
    public String getProperty(final String name) {
        final Properties prop = getProperties();
        return prop == null ? null : prop.getProperty(name);
    }
    
    /**
     * @see org.regenstrief.util.property.PropertyResolver#hasProperty(java.lang.String)
     */
    @Override
    public boolean hasProperty(final String name) {
        final Properties prop = getProperties();
        return prop == null ? false : prop.containsKey(name);
    }
    
    @Override
    public Set<String> getNames() {
        final Properties prop = getProperties();
        if (prop == null) {
            return Collections.emptySet();
        }
        return Collections.unmodifiableSet(prop.stringPropertyNames());
    }
}
