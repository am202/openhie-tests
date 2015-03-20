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
import org.regenstrief.hl7.util.AbstractConceptData;
import org.regenstrief.hl7.util.ConceptData;

/**
 * <p>
 * Title: CE
 * </p>
 * <p>
 * Description: HL7 Coded Element
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
public class CE extends AbstractConceptData {
    
    public final static String CE_XML = "CE";
    
    public final static String IDENTIFIER_XML = "CE.1";
    
    public final static String TEXT_XML = "CE.2";
    
    public final static String NAME_OF_CODING_SYSTEM_XML = "CE.3";
    
    public final static String ALTERNATE_IDENTIFIER_XML = "CE.4";
    
    public final static String ALTERNATE_TEXT_XML = "CE.5";
    
    public final static String NAME_OF_ALTERNATIVE_CODING_SYSTEM_XML = "CE.6";
    
    /**
     * Constructs an empty CE
     * 
     * @param prop the HL7Properties
     **/
    public CE(final HL7Properties prop) {
        super(prop);
    }
    
    /**
     * Constructs a free text CE
     * 
     * @param prop the HL7Properties
     * @param text the text
     **/
    public CE(final HL7Properties prop, final String text) {
        this(prop);
        this.text = text;
    }
    
    /**
     * Constructs a CE
     * 
     * @param prop the HL7Properties
     * @param identifier the identifier
     * @param text the text
     * @param nameOfCodingSystem the name of coding system
     **/
    public CE(final HL7Properties prop, final String identifier, final String text, final String nameOfCodingSystem) {
        this(prop, text);
        setIdentifier(identifier);
        this.nameOfCodingSystem = nameOfCodingSystem;
    }
    
    /**
     * Constructs a new CE as a copy of the given ConceptData
     * 
     * @param cd the ConceptData to copy
     **/
    public CE(final ConceptData cd) {
        this(cd.getProp(), cd.getIdentifier(), cd.getText(), cd.getNameOfCodingSystem());
        this.alternateIdentifier = cd.getAlternateIdentifier();
        this.alternateText = cd.getAlternateText();
        this.nameOfAlternateCodingSystem = cd.getNameOfAlternateCodingSystem();
    }
    
    /**
     * Constructs a CE
     * 
     * @param prop the HL7Properties
     * @param identifier the identifier
     * @param text the text
     * @param nameOfCodingSystem the name of coding system
     * @return the CE
     **/
    public static CE create(final HL7Properties prop, final String identifier, final String text,
                            final String nameOfCodingSystem) {
        return (identifier == null) && (text == null) ? null : new CE(prop, identifier, text, nameOfCodingSystem);
    }
    
    /**
     * Retrieves the given CE or a copy if nulls can be replaced with default values
     * 
     * @param prop the HL7Properties
     * @param toCopy the CE
     * @param defaultText the default text
     * @param defaultSys the default name of coding system
     * @return the CE
     **/
    public static CE createCEWithDefaults(final HL7Properties prop, final CE toCopy, final String defaultText,
                                          final String defaultSys) {
        final CE toReturn;
        final String text = toCopy == null ? null : toCopy.getText(), sys = toCopy == null ? null : toCopy
                .getNameOfCodingSystem();
        
        if (((sys == null) && (defaultSys != null)) || ((text == null) && (defaultText != null))) {
            if (toCopy == null) {
                toReturn = new CE(prop, null, defaultText, defaultSys);
            } else {
                toReturn = new CE(toCopy);
                if (sys == null) {
                    toReturn.setNameOfCodingSystem(defaultSys);
                }
                if (text == null) {
                    toReturn.setText(defaultText);
                }
            }
        } else {
            toReturn = toCopy;
        }
        
        return toReturn;
    }
    
    /**
     * Creates a new CE with the given identifier
     * 
     * @param prop the HL7Properties
     * @param identifier the identifier
     * @return the CE
     **/
    public static CE createCEWithIdentifier(final HL7Properties prop, final String identifier) {
        if (identifier == null) {
            return null;
        }
        
        final CE ce = new CE(prop);
        ce.setIdentifier(identifier);
        
        return ce;
    }
    
    /**
     * Creates a new CE with the given text
     * 
     * @param prop the HL7Properties
     * @param text the text
     * @return the CE
     **/
    public static CE createCEWithText(final HL7Properties prop, final String text) {
        if (text == null) {
            return null;
        }
        
        final CE ce = new CE(prop);
        ce.setText(text);
        
        return ce;
    }
    
    /**
     * Retrieves the CE, overrides the ConceptData implementation
     * 
     * @return the CE
     **/
    @Override
    public CE getCE() {
        return this;
    }
    
    public static CE parsePiped(final HL7Parser parser, final String line, final int start, final char delim, final int stop) {
        if (stop <= start) {
            return null;
        }
        final CE ce = new CE(parser);
        ce.readPiped(parser, line, start, delim, stop);
        return ce;
    }
}
