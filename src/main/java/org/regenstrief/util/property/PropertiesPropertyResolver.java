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

/**
 * PropertiesPropertyResolver
 */
public class PropertiesPropertyResolver extends AbstractPropertyResolver {
    
    private Properties prop = null;
    
    public PropertiesPropertyResolver() {
    }
    
    public PropertiesPropertyResolver(final Properties prop) {
        setProperties(prop);
    }
    
    /**
     * @see org.regenstrief.util.property.AbstractPropertyResolver#getProperties()
     */
    @Override
    protected Properties getProperties() {
        return this.prop;
    }
    
    public void setProperties(final Properties prop) {
        this.prop = prop;
    }
}
