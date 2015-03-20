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
package org.regenstrief.hl7.convert;

import java.util.Map;

import javax.xml.transform.OutputKeys;

import org.regenstrief.util.Util;

/**
 * <p>
 * Title: OutputUtil
 * </p>
 * <p>
 * Description: Utility methods for converter output
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: Regenstrief Institute
 * </p>
 * 
 * @author Andrew Martin
 * @version 1.0
 */
public final class OutputUtil {
    
    // Version constants
    public final static String V2_3_1 = "2.3.1";
    
    public final static String V2_4 = "2.4";
    
    public final static String V2_5 = "2.5";
    
    public final static String V2_6 = "2.6";
    
    public final static String V2_7 = "2.7";
    
    // Output constants
    public final static String YES = "yes";
    
    public final static String NO = "no";
    
    public final static String DEFAULT_VERSION = "1.0";
    
    public final static String DEFAULT_ENCODING = "UTF-8";
    
    // Package constants
    public final static String HL7_PACKAGE = "org.regenstrief.hl7.";
    
    public final static String CONVERT_PACKAGE = HL7_PACKAGE + "convert.";
    
    // System property constants
    public final static String SYS_PROP_VERSION = HL7_PACKAGE + "version";
    
    // Output property constants (in addition to javax.xml.transform.OutputKeys)
    // If output should be indented (see javax.xml.transform.OutputKeys.INDENT), the value of this property can be used for each tab
    public final static String OUT_PROP_TAB = CONVERT_PACKAGE + "tab";
    
    // If output should be indented (see javax.xml.transform.OutputKeys.INDENT), the value of this property can be used for each line break
    public final static String OUT_PROP_BR = CONVERT_PACKAGE + "br";
    
    // If empty tags <tag></tag> should be collapsed to <tag/>
    public final static String OUT_PROP_COLLAPSE_EMPTY = CONVERT_PACKAGE + "collapseEmpty";
    
    /**
     * Retrieves the desired property
     * 
     * @param prop the Map
     * @param key the String key of the desired property
     * @return the desired property String
     **/
    public final static String getProperty(final Map<String, String> prop, final String key) {
        return prop == null ? null : prop.get(key);
    }
    
    /**
     * Retrieves the desired property, or a default if none is present
     * 
     * @param prop the Map
     * @param key the String key of the desired property
     * @param def the default String value
     * @return the desired property String
     **/
    public final static String getPropertyDefault(final Map<String, String> prop, final String key, final String def) {
        final String val = getProperty(prop, key);
        return Util.length(val) == 0 ? def : val;
    }
    
    /**
     * Retrieves the desired boolean property, or true if none is present
     * 
     * @param prop the Map
     * @param key the String key of the desired property
     * @return the desired property String
     **/
    public final static boolean getPropertyBooleanDefaultYes(final Map<String, String> prop, final String key) {
        return NO.equals(getProperty(prop, key)) ? false : true;
    }
    
    /**
     * Retrieves the desired boolean property, or false if none is present
     * 
     * @param prop the Map
     * @param key the String key of the desired property
     * @return the desired property String
     **/
    public final static boolean getPropertyBooleanDefaultNo(final Map<String, String> prop, final String key) {
        return YES.equals(getProperty(prop, key)) ? true : false;
    }
    
    /**
     * Retrieves the document type declaration
     * 
     * @param prop the Map
     * @param root the String name of the root Element
     * @return the document type declaration String
     **/
    public final static String getDocumentTypeDeclaration(final Map<String, String> prop, final String root) {
        final String doctypeSystem = getProperty(prop, OutputKeys.DOCTYPE_SYSTEM);
        String doctypePublic;
        
        if (Util.length(doctypeSystem) == 0) {
            return "";
        }
        
        doctypePublic = getProperty(prop, OutputKeys.DOCTYPE_PUBLIC);
        return "<!DOCTYPE " + root
                + (Util.length(doctypePublic) == 0 ? " SYSTEM \"" : " PUBLIC \"" + doctypePublic + "\" \"") + doctypeSystem
                + "\">";
    }
    
    /**
     * Retrieves the XML declaration if the omit property is "no" or missing
     * 
     * @param prop the Map
     * @return the XML declaration String
     **/
    public final static String getXMLDeclaration(final Map<String, String> prop) {
        return getPropertyBooleanDefaultNo(prop, OutputKeys.OMIT_XML_DECLARATION) ? "" : "<?xml version=\""
                + getPropertyDefault(prop, OutputKeys.VERSION, DEFAULT_VERSION) + "\" encoding=\""
                + getPropertyDefault(prop, OutputKeys.ENCODING, DEFAULT_ENCODING) + "\"?>";
    }
}
