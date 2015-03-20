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
package org.regenstrief.hl7.util;

import org.regenstrief.hl7.datatype.CE;
import org.regenstrief.hl7.datatype.CWE;
import org.regenstrief.hl7.datatype.CodedIdentifier;
import org.regenstrief.hl7.datatype.DTM;

/**
 * <p>
 * Title: ConceptData
 * </p>
 * <p>
 * Description: Interface for HL7 coded data
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: Regenstrief Institute
 * </p>
 * 
 * @author Andrew Martin
 * @version 1.0
 */
public interface ConceptData extends BaseData {
    
    /**
     * Retrieves the identifier
     * 
     * @return the identifier
     **/
    public String getIdentifier();
    
    /**
     * Retrieves the identifier with its suffix
     * 
     * @return the identifier with its suffix
     **/
    public CodedIdentifier getIdentifierWithSuffix();
    
    /**
     * Retrieves the text
     * 
     * @return the text
     **/
    public String getText();
    
    /**
     * Retrieves the name of coding system
     * 
     * @return the name of coding system
     **/
    public String getNameOfCodingSystem();
    
    /**
     * Retrieves the alternate identifier
     * 
     * @return the alternate identifier
     **/
    public String getAlternateIdentifier();
    
    /**
     * Retrieves the alternate text
     * 
     * @return the alternate text
     **/
    public String getAlternateText();
    
    /**
     * Retrieves the name of alternate coding system
     * 
     * @return the name of alternate coding system
     **/
    public String getNameOfAlternateCodingSystem();
    
    /**
     * Modifies the identifier
     * 
     * @param identifier the new identifier
     **/
    public void setIdentifier(String identifier);
    
    /**
     * Modifies the identifier with its suffix
     * 
     * @param identifierWithSuffix the new identifier with its suffix
     **/
    public void setIdentifierWithSuffix(CodedIdentifier identifierWithSuffix);
    
    /**
     * Modifies the text
     * 
     * @param text the new text
     **/
    public void setText(String text);
    
    /**
     * Modifies the name of coding system
     * 
     * @param nameOfCodingSystem the new name of coding system
     **/
    public void setNameOfCodingSystem(String nameOfCodingSystem);
    
    /**
     * Modifies the alternate identifier
     * 
     * @param alternateIdentifier the new alternate identifier
     **/
    public void setAlternateIdentifier(String alternateIdentifier);
    
    /**
     * Modifies the alternate text
     * 
     * @param alternateText the new alternate text
     **/
    public void setAlternateText(String alternateText);
    
    /**
     * Modifies the name of alternate coding system
     * 
     * @param nameOfAlternateCodingSystem the new name of alternate coding system
     **/
    public void setNameOfAlternateCodingSystem(String nameOfAlternateCodingSystem);
    
    /**
     * Retrieves the coding system version ID
     * 
     * @return the coding system version ID
     **/
    public String getCodingSystemVersionID();
    
    /**
     * Retrieves the alternate coding system version ID
     * 
     * @return the alternate coding system version ID
     **/
    public String getAlternateCodingSystemVersionID();
    
    /**
     * Retrieves the original text
     * 
     * @return the original text
     **/
    public String getOriginalText();
    
    /**
     * Retrieves the second alternate identifier
     * 
     * @return the second alternate identifier
     **/
    public String getSecondAlternateIdentifier();
    
    /**
     * Retrieves the second alternate text
     * 
     * @return the second alternate text
     **/
    public String getSecondAlternateText();
    
    /**
     * Retrieves the second name of alternate coding system
     * 
     * @return the second name of alternate coding system
     **/
    public String getSecondNameOfAlternateCodingSystem();
    
    /**
     * Retrieves the second alternate coding system version ID
     * 
     * @return the second alternate coding system version ID
     **/
    public String getSecondAlternateCodingSystemVersionID();
    
    /**
     * Retrieves the coding system OID
     * 
     * @return the coding system OID
     **/
    public String getCodingSystemOID();
    
    /**
     * Retrieves the value set OID
     * 
     * @return the value set OID
     **/
    public String getValueSetOID();
    
    /**
     * Retrieves the value set version ID
     * 
     * @return the value set version ID
     **/
    public DTM getValueSetVersionID();
    
    /**
     * Retrieves the alternate coding system OID
     * 
     * @return the alternate coding system OID
     **/
    public String getAlternateCodingSystemOID();
    
    /**
     * Retrieves the alternate value set OID
     * 
     * @return the alternate value set OID
     **/
    public String getAlternateValueSetOID();
    
    /**
     * Retrieves the alternate value set version ID
     * 
     * @return the alternate value set version ID
     **/
    public DTM getAlternateValueSetVersionID();
    
    /**
     * Retrieves the second alternate coding system OID
     * 
     * @return the second alternate coding system OID
     **/
    public String getSecondAlternateCodingSystemOID();
    
    /**
     * Retrieves the second alternate value set OID
     * 
     * @return the second alternate value set OID
     **/
    public String getSecondAlternateValueSetOID();
    
    /**
     * Retrieves the second alternate value set version ID
     * 
     * @return the second alternate value set version ID
     **/
    public DTM getSecondAlternateValueSetVersionID();
    
    /**
     * Modifies the coding system version ID
     * 
     * @param codingSystemVersionID the new coding system version ID
     **/
    public void setCodingSystemVersionID(String codingSystemVersionID);
    
    /**
     * Modifies the alternate coding system version ID
     * 
     * @param alternateCodingSystemVersionID the new alternate coding system version ID
     **/
    public void setAlternateCodingSystemVersionID(String alternateCodingSystemVersionID);
    
    /**
     * Modifies the original text
     * 
     * @param originalText the new original text
     **/
    public void setOriginalText(String originalText);
    
    /**
     * Modifies the second alternate identifier
     * 
     * @param secondAlternateIdentifier the new second alternate identifier
     **/
    public void setSecondAlternateIdentifier(String secondAlternateIdentifier);
    
    /**
     * Modifies the second alternate text
     * 
     * @param secondAlternateText the new second alternate text
     **/
    public void setSecondAlternateText(String secondAlternateText);
    
    /**
     * Modifies the second name of alternate coding system
     * 
     * @param secondNameOfAlternateCodingSystem the new second name of alternate coding system
     **/
    public void setSecondNameOfAlternateCodingSystem(String secondNameOfAlternateCodingSystem);
    
    /**
     * Modifies the second alternate coding system version ID
     * 
     * @param secondAlternateCodingSystemVersionID the new second alternate coding system version ID
     **/
    public void setSecondAlternateCodingSystemVersionID(String secondAlternateCodingSystemVersionID);
    
    /**
     * Modifies the coding system OID
     * 
     * @param codingSystemOID the new coding system OID
     **/
    public void setCodingSystemOID(String codingSystemOID);
    
    /**
     * Modifies the value set OID
     * 
     * @param valueSetOID the new value set OID
     **/
    public void setValueSetOID(String valueSetOID);
    
    /**
     * Modifies the value set version ID
     * 
     * @param valueSetVersionID the new value set version ID
     **/
    public void setValueSetVersionID(DTM valueSetVersionID);
    
    /**
     * Modifies the alternate coding system OID
     * 
     * @param alternateCodingSystemOID the new alternate coding system OID
     **/
    public void setAlternateCodingSystemOID(String alternateCodingSystemOID);
    
    /**
     * Modifies the alternate value set OID
     * 
     * @param alternateValueSetOID the new alternate value set OID
     **/
    public void setAlternateValueSetOID(String alternateValueSetOID);
    
    /**
     * Modifies the alternate value set version ID
     * 
     * @param alternateValueSetVersionID the new alternate value set version ID
     **/
    public void setAlternateValueSetVersionID(DTM alternateValueSetVersionID);
    
    /**
     * Modifies the second alternate coding system OID
     * 
     * @param secondAlternateCodingSystemOID the new second alternate coding system OID
     **/
    public void setSecondAlternateCodingSystemOID(String secondAlternateCodingSystemOID);
    
    /**
     * Modifies the second alternate value set OID
     * 
     * @param secondAlternateValueSetOID the new second alternate value set OID
     **/
    public void setSecondAlternateValueSetOID(String secondAlternateValueSetOID);
    
    /**
     * Modifies the second alternate value set version ID
     * 
     * @param secondAlternateValueSetVersionID the new second alternate value set version ID
     **/
    public void setSecondAlternateValueSetVersionID(DTM secondAlternateValueSetVersionID);
    
    /**
     * Retrieves the CE values of this
     * 
     * @return the CE values of this
     **/
    public CE getCE();
    
    /**
     * Converts this into a CWE object
     * 
     * @return this as a CWE object
     **/
    public CWE toCWE();
    
    /**
     * Retrieves the alternate code as a new ConceptData
     * 
     * @return the alternate code ConceptData
     **/
    public ConceptData getAlt();
    
    public ConceptData getAlt2();
    
    public ConceptData getSuffix();
    
    public ConceptData getPrefix(final String obrCode);
    
    /**
     * Strips all fields besides free text from this ConceptData instance
     * 
     * @return the this ConceptData instance after stripping
     **/
    public ConceptData toFreeText();
    
    /**
     * Determines if the ConceptData represents free text
     * 
     * @return true if the ConceptData represents free text, false otherwise
     **/
    public boolean isFreeText();
    
    /**
     * Retrieves the ConceptData's code
     * 
     * @return the ConceptData's code
     **/
    public String toCode();
    
    /**
     * Retrieves the ConceptData's text
     * 
     * @return the ConceptData's text
     **/
    public String toText();
    
    /**
     * Retrieves the code, or the text if no code is present
     * 
     * @return the code or text
     **/
    public String getCodeOrText();
    
    /**
     * Retrieves a String containing the identifier, text, and name of coding system
     * 
     * @return the String
     **/
    public String toFullString();
}
