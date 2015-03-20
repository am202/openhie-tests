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
package org.regenstrief.hl7.datatype;

import org.regenstrief.hl7.HL7Parser;
import org.regenstrief.hl7.HL7Properties;
import org.regenstrief.hl7.util.AbstractExpandedConceptData;

/**
 * <p>
 * Title: CWE
 * </p>
 * <p>
 * Description: HL7 Coded With Exceptions
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Regenstrief Institute
 * </p>
 * 
 * @author Andrew Martin
 * @version 1.0
 */
public class CWE extends AbstractExpandedConceptData {
    
    // In HL7 2.5, CWE and CNE were introduced to replace CE.
    // CE was "retained for backward compatibility only as of v 2.5. Refer to the CNE and CWE data types."
    // (2.5 manual 2-132), but in some places it was replaced by one of the new types (SPS data type).
    // So in HL7, CWE and CNE are like subclasses of CE.
    // So they should have the same relationship in Java.
    // If we create separate classes for each HL7 2.X version,
    // then a 2.6 CWE would extend a 2.6 CE and a 2.5 CWE.
    // So we'd need to use interfaces,
    // since a class can't extend two parent classes.
    // We should use interfaces for all data types
    // so that we can achieve any relationships we want between them
    // whether or not the underlying classes are related.
    public final static String CWE_XML = "CWE";
    
    public final static String IDENTIFIER_XML = "CWE.1";
    
    public final static String TEXT_XML = "CWE.2";
    
    public final static String NAME_OF_CODING_SYSTEM_XML = "CWE.3";
    
    public final static String ALTERNATE_IDENTIFIER_XML = "CWE.4";
    
    public final static String ALTERNATE_TEXT_XML = "CWE.5";
    
    public final static String NAME_OF_ALTERNATE_CODING_SYSTEM_XML = "CWE.6";
    
    public final static String CODING_SYSTEM_VERSION_ID_XML = "CWE.7";
    
    public final static String ALTERNATE_CODING_SYSTEM_VERSION_ID_XML = "CWE.8";
    
    public final static String ORIGINAL_TEXT_XML = "CWE.9";
    
    public final static String SECOND_ALTERNATE_IDENTIFIER_XML = "CWE.10";
    
    public final static String SECOND_ALTERNATE_TEXT_XML = "CWE.11";
    
    public final static String SECOND_NAME_OF_ALTERNATE_CODING_SYSTEM_XML = "CWE.12";
    
    public final static String SECOND_ALTERNATE_CODING_SYSTEM_VERSION_ID_XML = "CWE.13";
    
    public final static String CODING_SYSTEM_OID_XML = "CWE.14";
    
    public final static String VALUE_SET_OID_XML = "CWE.15";
    
    public final static String VALUE_SET_VERSION_ID_XML = "CWE.16";
    
    public final static String ALTERNATE_CODING_SYSTEM_OID_XML = "CWE.17";
    
    public final static String ALTERNATE_VALUE_SET_OID_XML = "CWE.18";
    
    public final static String ALTERNATE_VALUE_SET_VERSION_ID_XML = "CWE.19";
    
    public final static String SECOND_ALTERNATE_CODING_SYSTEM_OID_XML = "CWE.20";
    
    public final static String SECOND_ALTERNATE_VALUE_SET_OID_XML = "CWE.21";
    
    public final static String SECOND_ALTERNATE_VALUE_SET_VERSION_ID_XML = "CWE.22";
    
    /**
     * Constructs an empty CWE
     * 
     * @param prop the HL7Properties
     **/
    public CWE(final HL7Properties prop) {
        super(prop);
    }
    
    /**
     * Constructs a new CWE with the given text
     * 
     * @param prop the HL7Properties
     * @param text the CWE text
     * @return the CWE
     **/
    public static CWE createCWE(final HL7Properties prop, final String text) {
        if (text == null) {
            return null;
        }
        
        final CWE cwe = new CWE(prop);
        cwe.setText(text);
        
        return cwe;
    }
    
    public static CWE toCWE(final HL7Properties prop, final String identifier) {
        if (identifier == null) {
            return null;
        }
        
        final CWE cwe = new CWE(prop);
        cwe.setIdentifier(identifier);
        
        return cwe;
    }
    
    /**
     * Constructs a new CWE with the given values
     * 
     * @param prop the HL7Properties
     * @param identifier the CWE identifier
     * @param text the CWE text
     * @param nameOfCodingSystem the CWE name of coding system
     * @return the CWE
     **/
    public static CWE createCWE(final HL7Properties prop, final String identifier, final String text, final String nameOfCodingSystem) {
        if ((identifier == null) && (text == null) && (nameOfCodingSystem == null)) {
            return null;
        }
        
        final CWE cwe = new CWE(prop);
        cwe.setIdentifier(identifier);
        cwe.setText(text);
        cwe.setNameOfCodingSystem(nameOfCodingSystem);
        
        return cwe;
    }
    
    /**
     * Retrieves the CWE, overrides the ExpandedConceptData implementation
     * 
     * @return the CWE
     **/
    @Override
    public CWE toCWE() {
        return this;
    }
    
    public static CWE parsePiped(final HL7Parser parser, final String line, final int start, final char delim, final int stop) {
        if (stop <= start) {
            return null;
        }
        final CWE cwe = new CWE(parser);
        cwe.readPiped(parser, line, start, delim, stop);
        return cwe;
    }
}
