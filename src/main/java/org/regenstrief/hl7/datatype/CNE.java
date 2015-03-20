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
 * Title: CNE
 * </p>
 * <p>
 * Description: HL7 Coded No Exceptions
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
public class CNE extends AbstractExpandedConceptData {
    
    public final static String CNE_XML = "CNE";
    
    public final static String IDENTIFIER_XML = "CNE.1";
    
    public final static String TEXT_XML = "CNE.2";
    
    public final static String NAME_OF_CODING_SYSTEM_XML = "CNE.3";
    
    public final static String ALTERNATE_IDENTIFIER_XML = "CNE.4";
    
    public final static String ALTERNATE_TEXT_XML = "CNE.5";
    
    public final static String NAME_OF_ALTERNATE_CODING_SYSTEM_XML = "CNE.6";
    
    public final static String CODING_SYSTEM_VERSION_ID_XML = "CNE.7";
    
    public final static String ALTERNATE_CODING_SYSTEM_VERSION_ID_XML = "CNE.8";
    
    public final static String ORIGINAL_TEXT_XML = "CNE.9";
    
    public final static String SECOND_ALTERNATE_IDENTIFIER_XML = "CNE.10";
    
    public final static String SECOND_ALTERNATE_TEXT_XML = "CNE.11";
    
    public final static String SECOND_NAME_OF_ALTERNATE_CODING_SYSTEM_XML = "CNE.12";
    
    public final static String SECOND_ALTERNATE_CODING_SYSTEM_VERSION_ID_XML = "CNE.13";
    
    public final static String CODING_SYSTEM_OID_XML = "CNE.14";
    
    public final static String VALUE_SET_OID_XML = "CNE.15";
    
    public final static String VALUE_SET_VERSION_ID_XML = "CNE.16";
    
    public final static String ALTERNATE_CODING_SYSTEM_OID_XML = "CNE.17";
    
    public final static String ALTERNATE_VALUE_SET_OID_XML = "CNE.18";
    
    public final static String ALTERNATE_VALUE_SET_VERSION_ID_XML = "CNE.19";
    
    public final static String SECOND_ALTERNATE_CODING_SYSTEM_OID_XML = "CNE.20";
    
    public final static String SECOND_ALTERNATE_VALUE_SET_OID_XML = "CNE.21";
    
    public final static String SECOND_ALTERNATE_VALUE_SET_VERSION_ID_XML = "CNE.22";
    
    /**
     * Constructs an empty CNE
     * 
     * @param prop the HL7Properties
     **/
    public CNE(final HL7Properties prop) {
        super(prop);
    }
    
    /**
     * Constructs a new CNE with the given text
     * 
     * @param prop the HL7Properties
     * @param text the CNE text
     **/
    public CNE(final HL7Properties prop, final String text) {
        this(prop);
        this.text = text;
    }
    
    /**
     * Constructs a new CNE from the given CE
     * 
     * @param ce the CE
     **/
    public CNE(final CE ce) {
        this(ce.getProp());
        update(ce);
    }
    
    /**
     * Constructs a new CNE with the given values
     * 
     * @param prop the HL7Properties
     * @param identifier the CNE identifier
     * @param text the CNE text
     * @param nameOfCodingSystem the CNE name of coding system
     * @return the CNE
     **/
    public static CNE createCNE(final HL7Properties prop, final String identifier, final String text, final String nameOfCodingSystem) {
        if ((identifier == null) && (text == null) && (nameOfCodingSystem == null)) {
            return null;
        }
        
        final CNE cne = new CNE(prop);
        cne.setIdentifier(identifier);
        cne.setText(text);
        cne.setNameOfCodingSystem(nameOfCodingSystem);
        
        return cne;
    }
    
    public static CNE parsePiped(final HL7Parser parser, final String line, final int start, final char delim, final int stop) {
        if (stop <= start) {
            return null;
        }
        final CNE cne = new CNE(parser);
        cne.readPiped(parser, line, start, delim, stop);
        return cne;
    }
}
