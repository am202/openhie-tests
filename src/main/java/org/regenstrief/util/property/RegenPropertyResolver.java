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

import java.util.Properties;
import java.util.Set;

import org.regenstrief.util.Util;

/**
 * RegenPropertyResolver<br/>
 * Checks 1 System properties, 2 the properties file, 3 the injected Properties instance
 */
public class RegenPropertyResolver implements PropertyResolver {
    
    private final static Properties fileProperties = new Properties();
    
    private static boolean propertiesLoaded = false;
    
    private static boolean propertiesLoading = false;
    
    private final ChainPropertyResolver chain;
    
    private final SystemPropertyResolver systemResolver;
    
    private final PropertiesPropertyResolver fileResolver;
    
    private final PropertiesPropertyResolver lastResolver;
    
    {
        this.chain = new ChainPropertyResolver();
        this.systemResolver = new SystemPropertyResolver();
        this.fileResolver = new PropertiesPropertyResolver(fileProperties);
        this.lastResolver = new PropertiesPropertyResolver();
        this.chain.addFirstPriority(this.systemResolver);
        this.chain.addLastPriority(this.fileResolver);
        this.chain.addLastPriority(this.lastResolver);
    }
    
    /**
     * @see org.regenstrief.util.property.PropertyResolver#getProperty(java.lang.String)
     */
    @Override
    public String getProperty(final String name) {
        loadProperties();
        return this.chain.getProperty(name);
    }
    
    /**
     * @see org.regenstrief.util.property.PropertyResolver#hasProperty(java.lang.String)
     */
    @Override
    public boolean hasProperty(final String name) {
        loadProperties();
        return this.chain.hasProperty(name);
    }
    
    @Override
    public Set<String> getNames() {
        loadProperties();
        return this.chain.getNames();
    }
    
    /**
     * Loads the properties file, the second source, used if no System property is found
     **/
    public final static void loadProperties() {
        if (!propertiesLoaded) {
            if (propertiesLoading) {
                return;
            }
            propertiesLoading = true;
            try {
                final String propertiesFile = System.getProperty(Util.PROPERTIES);
                if (propertiesFile != null) {
                    Util.loadProperties(fileProperties, propertiesFile);
                } else if (Util.isStreamAvailable(Util.DEFAULT_PROPERTIES)) {
                    Util.loadProperties(fileProperties, Util.DEFAULT_PROPERTIES);
                }
                for (final Object key : fileProperties.keySet()) {
                    if (!(key instanceof String)) {
                        continue;
                    }
                    final String name = (String) key;
                    if (name.startsWith("javax.net") && !System.getProperties().containsKey(name)) {
                        System.setProperty(name, fileProperties.getProperty(name));
                    }
                }
            } finally {
                propertiesLoaded = true; // If no properties have been specified, don't keep looking for them
                propertiesLoading = false;
            }
        }
    }
    
    /**
     * Injects a Properties instance, 3rd source, used if no System property/file property is found
     * 
     * @param prop the Properties instance
     */
    public final void setProperties(final Properties prop) {
        this.lastResolver.setProperties(prop);
    }
}
