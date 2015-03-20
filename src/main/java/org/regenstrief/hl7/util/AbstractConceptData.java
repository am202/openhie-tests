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

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.regenstrief.hl7.HL7Parser;
import org.regenstrief.hl7.HL7Properties;
import org.regenstrief.hl7.datatype.CE;
import org.regenstrief.hl7.datatype.CWE;
import org.regenstrief.hl7.datatype.CodedIdentifier;
import org.regenstrief.hl7.datatype.DTM;
import org.regenstrief.hl7.datatype.HL7DataType;
import org.regenstrief.util.Util;

/**
 * <p>
 * Title: AbstractConceptData
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
public abstract class AbstractConceptData extends HL7DataType implements ConceptData {
    
    protected CodedIdentifier identifier = null;
    
    protected String text = null;
    
    protected String nameOfCodingSystem = null;
    
    protected String alternateIdentifier = null;
    
    protected String alternateText = null;
    
    protected String nameOfAlternateCodingSystem = null;
    
    protected String codingSystemVersionID = null;
    
    protected String alternateCodingSystemVersionID = null;
    
    protected String originalText = null;
    
    protected String secondAlternateIdentifier = null;
    
    protected String secondAlternateText = null;
    
    protected String secondNameOfAlternateCodingSystem = null;
    
    protected String secondAlternateCodingSystemVersionID = null;
    
    protected String codingSystemOID = null;
    
    protected String valueSetOID = null;
    
    protected DTM valueSetVersionID = null;
    
    protected String alternateCodingSystemOID = null;
    
    protected String alternateValueSetOID = null;
    
    protected DTM alternateValueSetVersionID = null;
    
    protected String secondAlternateCodingSystemOID = null;
    
    protected String secondAlternateValueSetOID = null;
    
    protected DTM secondAlternateValueSetVersionID = null;
    
    /**
     * Constructs an empty ConceptData
     * 
     * @param prop the HL7Properties
     **/
    public AbstractConceptData(final HL7Properties prop) {
        super(prop);
    }
    
    /**
     * Retrieves the identifier
     * 
     * @return the identifier
     **/
    @Override
    public String getIdentifier() {
        return CodedIdentifier.getIdentifier(this.identifier);
    }
    
    /**
     * Retrieves the identifier with its suffix
     * 
     * @return the identifier with its suffix
     **/
    @Override
    public CodedIdentifier getIdentifierWithSuffix() {
        return this.identifier;
    }
    
    /**
     * Retrieves the text
     * 
     * @return the text
     **/
    @Override
    public String getText() {
        return this.text;
    }
    
    /**
     * Retrieves the name of coding system
     * 
     * @return the name of coding system
     **/
    @Override
    public String getNameOfCodingSystem() {
        return this.nameOfCodingSystem;
    }
    
    /**
     * Retrieves the alternate identifier
     * 
     * @return the alternate identifier
     **/
    @Override
    public String getAlternateIdentifier() {
        return this.alternateIdentifier;
    }
    
    /**
     * Retrieves the alternate text
     * 
     * @return the alternate text
     **/
    @Override
    public String getAlternateText() {
        return this.alternateText;
    }
    
    /**
     * Retrieves the name of alternate coding system
     * 
     * @return the name of alternate coding system
     **/
    @Override
    public String getNameOfAlternateCodingSystem() {
        return this.nameOfAlternateCodingSystem;
    }
    
    /**
     * Modifies the identifier
     * 
     * @param identifier the new identifier
     **/
    @Override
    public void setIdentifier(final String identifier) {
        this.identifier = CodedIdentifier.setIdentifier(this.prop, this.identifier, identifier);
    }
    
    /**
     * Modifies the identifier with its suffix
     * 
     * @param identifierWithSuffix the new identifier with its suffix
     **/
    @Override
    public void setIdentifierWithSuffix(final CodedIdentifier identifierWithSuffix) {
        this.identifier = identifierWithSuffix;
    }
    
    /**
     * Modifies the text
     * 
     * @param text the new text
     **/
    @Override
    public void setText(final String text) {
        this.text = text;
    }
    
    /**
     * Modifies the name of coding system
     * 
     * @param nameOfCodingSystem the new name of coding system
     **/
    @Override
    public void setNameOfCodingSystem(final String nameOfCodingSystem) {
        this.nameOfCodingSystem = nameOfCodingSystem;
    }
    
    /**
     * Modifies the alternate identifier
     * 
     * @param alternateIdentifier the new alternate identifier
     **/
    @Override
    public void setAlternateIdentifier(final String alternateIdentifier) {
        this.alternateIdentifier = alternateIdentifier;
    }
    
    /**
     * Modifies the alternate text
     * 
     * @param alternateText the new alternate text
     **/
    @Override
    public void setAlternateText(final String alternateText) {
        this.alternateText = alternateText;
    }
    
    /**
     * Modifies the name of alternate coding system
     * 
     * @param nameOfAlternateCodingSystem the new name of alternate coding system
     **/
    @Override
    public void setNameOfAlternateCodingSystem(final String nameOfAlternateCodingSystem) {
        this.nameOfAlternateCodingSystem = nameOfAlternateCodingSystem;
    }
    
    /**
     * Retrieves the coding system version ID
     * 
     * @return the coding system version ID
     **/
    @Override
    public String getCodingSystemVersionID() {
        return this.codingSystemVersionID;
    }
    
    /**
     * Retrieves the alternate coding system version ID
     * 
     * @return the alternate coding system version ID
     **/
    @Override
    public String getAlternateCodingSystemVersionID() {
        return this.alternateCodingSystemVersionID;
    }
    
    /**
     * Retrieves the original text
     * 
     * @return the original text
     **/
    @Override
    public String getOriginalText() {
        return this.originalText;
    }
    
    /**
     * Retrieves the second alternate identifier
     * 
     * @return the second alternate identifier
     **/
    @Override
    public String getSecondAlternateIdentifier() {
        return this.secondAlternateIdentifier;
    }
    
    /**
     * Retrieves the second alternate text
     * 
     * @return the second alternate text
     **/
    @Override
    public String getSecondAlternateText() {
        return this.secondAlternateText;
    }
    
    /**
     * Retrieves the second name of alternate coding system
     * 
     * @return the second name of alternate coding system
     **/
    @Override
    public String getSecondNameOfAlternateCodingSystem() {
        return this.secondNameOfAlternateCodingSystem;
    }
    
    /**
     * Retrieves the second alternate coding system version ID
     * 
     * @return the second alternate coding system version ID
     **/
    @Override
    public String getSecondAlternateCodingSystemVersionID() {
        return this.secondAlternateCodingSystemVersionID;
    }
    
    /**
     * Retrieves the coding system OID
     * 
     * @return the coding system OID
     **/
    @Override
    public String getCodingSystemOID() {
        return this.codingSystemOID;
    }
    
    /**
     * Retrieves the value set OID
     * 
     * @return the value set OID
     **/
    @Override
    public String getValueSetOID() {
        return this.valueSetOID;
    }
    
    /**
     * Retrieves the value set version ID
     * 
     * @return the value set version ID
     **/
    @Override
    public DTM getValueSetVersionID() {
        return this.valueSetVersionID;
    }
    
    /**
     * Retrieves the alternate coding system OID
     * 
     * @return the alternate coding system OID
     **/
    @Override
    public String getAlternateCodingSystemOID() {
        return this.alternateCodingSystemOID;
    }
    
    /**
     * Retrieves the alternate value set OID
     * 
     * @return the alternate value set OID
     **/
    @Override
    public String getAlternateValueSetOID() {
        return this.alternateValueSetOID;
    }
    
    /**
     * Retrieves the alternate value set version ID
     * 
     * @return the alternate value set version ID
     **/
    @Override
    public DTM getAlternateValueSetVersionID() {
        return this.alternateValueSetVersionID;
    }
    
    /**
     * Retrieves the second alternate coding system OID
     * 
     * @return the second alternate coding system OID
     **/
    @Override
    public String getSecondAlternateCodingSystemOID() {
        return this.secondAlternateCodingSystemOID;
    }
    
    /**
     * Retrieves the second alternate value set OID
     * 
     * @return the second alternate value set OID
     **/
    @Override
    public String getSecondAlternateValueSetOID() {
        return this.secondAlternateValueSetOID;
    }
    
    /**
     * Retrieves the second alternate value set version ID
     * 
     * @return the second alternate value set version ID
     **/
    @Override
    public DTM getSecondAlternateValueSetVersionID() {
        return this.secondAlternateValueSetVersionID;
    }
    
    /**
     * Modifies the coding system version ID
     * 
     * @param codingSystemVersionID the new coding system version ID
     **/
    @Override
    public void setCodingSystemVersionID(final String codingSystemVersionID) {
        this.codingSystemVersionID = codingSystemVersionID;
    }
    
    /**
     * Modifies the alternate coding system version ID
     * 
     * @param alternateCodingSystemVersionID the new alternate coding system version ID
     **/
    @Override
    public void setAlternateCodingSystemVersionID(final String alternateCodingSystemVersionID) {
        this.alternateCodingSystemVersionID = alternateCodingSystemVersionID;
    }
    
    /**
     * Modifies the original text
     * 
     * @param originalText the new original text
     **/
    @Override
    public void setOriginalText(final String originalText) {
        this.originalText = originalText;
    }
    
    /**
     * Modifies the second alternate identifier
     * 
     * @param secondAlternateIdentifier the new second alternate identifier
     **/
    @Override
    public void setSecondAlternateIdentifier(final String secondAlternateIdentifier) {
        this.secondAlternateIdentifier = secondAlternateIdentifier;
    }
    
    /**
     * Modifies the second alternate text
     * 
     * @param secondAlternateText the new second alternate text
     **/
    @Override
    public void setSecondAlternateText(final String secondAlternateText) {
        this.secondAlternateText = secondAlternateText;
    }
    
    /**
     * Modifies the second name of alternate coding system
     * 
     * @param secondNameOfAlternateCodingSystem the new second name of alternate coding system
     **/
    @Override
    public void setSecondNameOfAlternateCodingSystem(final String secondNameOfAlternateCodingSystem) {
        this.secondNameOfAlternateCodingSystem = secondNameOfAlternateCodingSystem;
    }
    
    /**
     * Modifies the second alternate coding system version ID
     * 
     * @param secondAlternateCodingSystemVersionID the new second alternate coding system version ID
     **/
    @Override
    public void setSecondAlternateCodingSystemVersionID(final String secondAlternateCodingSystemVersionID) {
        this.secondAlternateCodingSystemVersionID = secondAlternateCodingSystemVersionID;
    }
    
    /**
     * Modifies the coding system OID
     * 
     * @param codingSystemOID the new coding system OID
     **/
    @Override
    public void setCodingSystemOID(final String codingSystemOID) {
        this.codingSystemOID = codingSystemOID;
    }
    
    /**
     * Modifies the value set OID
     * 
     * @param valueSetOID the new value set OID
     **/
    @Override
    public void setValueSetOID(final String valueSetOID) {
        this.valueSetOID = valueSetOID;
    }
    
    /**
     * Modifies the value set version ID
     * 
     * @param valueSetVersionID the new value set version ID
     **/
    @Override
    public void setValueSetVersionID(final DTM valueSetVersionID) {
        this.valueSetVersionID = valueSetVersionID;
    }
    
    /**
     * Modifies the alternate coding system OID
     * 
     * @param alternateCodingSystemOID the new alternate coding system OID
     **/
    @Override
    public void setAlternateCodingSystemOID(final String alternateCodingSystemOID) {
        this.alternateCodingSystemOID = alternateCodingSystemOID;
    }
    
    /**
     * Modifies the alternate value set OID
     * 
     * @param alternateValueSetOID the new alternate value set OID
     **/
    @Override
    public void setAlternateValueSetOID(final String alternateValueSetOID) {
        this.alternateValueSetOID = alternateValueSetOID;
    }
    
    /**
     * Modifies the alternate value set version ID
     * 
     * @param alternateValueSetVersionID the new alternate value set version ID
     **/
    @Override
    public void setAlternateValueSetVersionID(final DTM alternateValueSetVersionID) {
        this.alternateValueSetVersionID = alternateValueSetVersionID;
    }
    
    /**
     * Modifies the second alternate coding system OID
     * 
     * @param secondAlternateCodingSystemOID the new second alternate coding system OID
     **/
    @Override
    public void setSecondAlternateCodingSystemOID(final String secondAlternateCodingSystemOID) {
        this.secondAlternateCodingSystemOID = secondAlternateCodingSystemOID;
    }
    
    /**
     * Modifies the second alternate value set OID
     * 
     * @param secondAlternateValueSetOID the new second alternate value set OID
     **/
    @Override
    public void setSecondAlternateValueSetOID(final String secondAlternateValueSetOID) {
        this.secondAlternateValueSetOID = secondAlternateValueSetOID;
    }
    
    /**
     * Modifies the second alternate value set version ID
     * 
     * @param secondAlternateValueSetVersionID the new second alternate value set version ID
     **/
    @Override
    public void setSecondAlternateValueSetVersionID(final DTM secondAlternateValueSetVersionID) {
        this.secondAlternateValueSetVersionID = secondAlternateValueSetVersionID;
    }
    
    @Override
    public final void readPiped(final HL7Parser parser, final String line, int start, final char delim, final int stop) {
        int next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        final char c = parser.getSubcomponentSeparator();
        this.identifier = CodedIdentifier.parsePiped(parser, line, start, c, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.text = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.nameOfCodingSystem = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.alternateIdentifier = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.alternateText = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.nameOfAlternateCodingSystem = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.codingSystemVersionID = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.alternateCodingSystemVersionID = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.originalText = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.secondAlternateIdentifier = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.secondAlternateText = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.secondNameOfAlternateCodingSystem = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.secondAlternateCodingSystemVersionID = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.codingSystemOID = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.valueSetOID = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.valueSetVersionID = DTM.parsePiped(parser, line, start, c, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.alternateCodingSystemOID = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.alternateValueSetOID = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.alternateValueSetVersionID = DTM.parsePiped(parser, line, start, c, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.secondAlternateCodingSystemOID = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.secondAlternateValueSetOID = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.secondAlternateValueSetVersionID = DTM.parsePiped(parser, line, start, c, next);
        
        assertLast(line, next + 1, delim, stop);
    }
    
    @Override
    public final void toPiped(final Writer w, final int level) throws IOException {
        int last = 1;
        
        last = addComponent(w, this.identifier, last, 1, level);
        last = addComponent(w, this.text, last, 2, level);
        last = addComponent(w, this.nameOfCodingSystem, last, 3, level);
        last = addComponent(w, this.alternateIdentifier, last, 4, level);
        last = addComponent(w, this.alternateText, last, 5, level);
        last = addComponent(w, this.nameOfAlternateCodingSystem, last, 6, level);
        last = addComponent(w, this.codingSystemVersionID, last, 7, level);
        last = addComponent(w, this.alternateCodingSystemVersionID, last, 8, level);
        last = addComponent(w, this.originalText, last, 9, level);
        last = addComponent(w, this.secondAlternateIdentifier, last, 10, level);
        last = addComponent(w, this.secondAlternateText, last, 11, level);
        last = addComponent(w, this.secondNameOfAlternateCodingSystem, last, 12, level);
        last = addComponent(w, this.secondAlternateCodingSystemVersionID, last, 13, level);
        last = addComponent(w, this.codingSystemOID, last, 14, level);
        last = addComponent(w, this.valueSetOID, last, 15, level);
        last = addComponent(w, this.valueSetVersionID, last, 16, level);
        last = addComponent(w, this.alternateCodingSystemOID, last, 17, level);
        last = addComponent(w, this.alternateValueSetOID, last, 18, level);
        last = addComponent(w, this.alternateValueSetVersionID, last, 19, level);
        last = addComponent(w, this.secondAlternateCodingSystemOID, last, 20, level);
        last = addComponent(w, this.secondAlternateValueSetOID, last, 21, level);
        last = addComponent(w, this.secondAlternateValueSetVersionID, last, 22, level);
    }
    
    /**
     * Determines if this ConceptData equals the given Object
     * 
     * @param o the Object to compare to this
     * @return true if this equals o
     **/
    @Override
    public boolean equals(final Object o) {
        if ((o == null) || !(o instanceof ConceptData)
        //!o.getClass().equals(getClass()) // Should we require them to be instances of the same ConceptData subclass?
        ) {
            return false;
        }
        
        final ConceptData cd = (ConceptData) o;
        
        if (!Util.equals(cd.getIdentifierWithSuffix(), getIdentifierWithSuffix())) {
            return false;
        } else if (!Util.equals(cd.getNameOfCodingSystem(), this.nameOfCodingSystem)) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Retrieves the hash code
     * 
     * @return the hash code
     **/
    @Override
    public int hashCode() {
        return Util.hashCode(this.identifier) ^ Util.hashCode(this.nameOfCodingSystem);
    }
    
    /**
     * Retrieves the CE values of this
     * 
     * @return the CE values of this
     **/
    @Override
    public CE getCE() {
        final CE ce = new CE(this.prop);
        update(ce);
        return ce;
    }
    
    /**
     * Converts this into a CWE object
     * 
     * @return this as a CWE object
     **/
    @Override
    public CWE toCWE() {
        final CWE cwe = new CWE(this.prop);
        update(cwe);
        return cwe;
    }
    
    protected void update(final ConceptData cwe) {
        cwe.setIdentifierWithSuffix(this.identifier);
        cwe.setText(this.text);
        cwe.setNameOfCodingSystem(this.nameOfCodingSystem);
        cwe.setAlternateIdentifier(this.alternateIdentifier);
        cwe.setAlternateText(this.alternateText);
        cwe.setNameOfAlternateCodingSystem(this.nameOfAlternateCodingSystem);
        cwe.setCodingSystemVersionID(this.codingSystemVersionID);
        cwe.setAlternateCodingSystemVersionID(this.alternateCodingSystemVersionID);
        cwe.setOriginalText(this.originalText);
        cwe.setSecondAlternateIdentifier(this.secondAlternateIdentifier);
        cwe.setSecondAlternateText(this.secondAlternateText);
        cwe.setSecondNameOfAlternateCodingSystem(this.secondNameOfAlternateCodingSystem);
        cwe.setSecondAlternateCodingSystemVersionID(this.secondAlternateCodingSystemVersionID);
        cwe.setCodingSystemOID(this.codingSystemOID);
        cwe.setValueSetOID(this.valueSetOID);
        cwe.setValueSetVersionID(this.valueSetVersionID);
        cwe.setAlternateCodingSystemOID(this.alternateCodingSystemOID);
        cwe.setAlternateValueSetOID(this.alternateValueSetOID);
        cwe.setAlternateValueSetVersionID(this.alternateValueSetVersionID);
        cwe.setSecondAlternateCodingSystemOID(this.secondAlternateCodingSystemOID);
        cwe.setSecondAlternateValueSetOID(this.secondAlternateValueSetOID);
        cwe.setSecondAlternateValueSetVersionID(this.secondAlternateValueSetVersionID);
    }
    
    /**
     * Retrieves the alternate code as a new ConceptData
     * 
     * @return the alternate code ConceptData
     **/
    @Override
    public ConceptData getAlt() {
        if ((this.alternateIdentifier != null) || (this.alternateText != null) || (this.nameOfAlternateCodingSystem != null)) {
            final CWE alt = new CWE(this.prop);
            alt.setIdentifier(this.alternateIdentifier);
            alt.text = this.alternateText;
            alt.nameOfCodingSystem = this.nameOfAlternateCodingSystem;
            return alt;
        }
        
        return null;
    }
    
    @Override
    public ConceptData getAlt2() {
        if ((this.secondAlternateIdentifier != null) || (this.secondAlternateText != null) || (this.secondNameOfAlternateCodingSystem != null)) {
            final CWE alt = new CWE(this.prop);
            alt.setIdentifier(this.secondAlternateIdentifier);
            alt.text = this.secondAlternateText;
            alt.nameOfCodingSystem = this.secondNameOfAlternateCodingSystem;
            return alt;
        }
        
        return null;
    }
    
    @Override
    public ConceptData getSuffix() {
        return this.identifier == null ? null : getConceptData(this.identifier.getSuffix());
    }
    
    @Override
    public ConceptData getPrefix(final String obrCode) {
        /*
        HL7 2.6 manual, 7.2.3 Narrative Reports as Batteries with Many OBX
        ... the sender... may... allow the omission of the observation ID component of a result segment 
        when it is the same as the observation ID of the preceding OBR.
        In this case, only the ampersand and the suffix would have to be sent, e.g., &IMP or &REC...
        */
        return getConceptData(Util.nvls(this.identifier == null ? null : this.identifier.getIdentifier(), obrCode));
    }
    
    private ConceptData getConceptData(final String code) {
        if (Util.isEmpty(code)) {
            return null;
        }
        final CWE suffix = new CWE(this.prop);
        suffix.setIdentifier(code);
        suffix.setNameOfCodingSystem(this.nameOfCodingSystem);
        return suffix;
    }
    
    /**
     * Strips all fields besides free text from this ConceptData instance
     * 
     * @return the this ConceptData instance after stripping
     **/
    @Override
    public ConceptData toFreeText() {
        this.identifier = null;
        this.nameOfCodingSystem = null;
        this.alternateIdentifier = null;
        this.nameOfAlternateCodingSystem = null;
        this.codingSystemVersionID = null;
        this.alternateCodingSystemVersionID = null;
        this.secondAlternateIdentifier = null;
        this.secondNameOfAlternateCodingSystem = null;
        this.secondAlternateCodingSystemVersionID = null;
        this.codingSystemOID = null;
        this.valueSetOID = null;
        this.valueSetVersionID = null;
        this.alternateCodingSystemOID = null;
        this.alternateValueSetOID = null;
        this.alternateValueSetVersionID = null;
        this.secondAlternateCodingSystemOID = null;
        this.secondAlternateValueSetOID = null;
        this.secondAlternateValueSetVersionID = null;
        
        return this;
    }
    
    /**
     * Determines if the ConceptData represents free text
     * 
     * @return true if the ConceptData represents free text, false otherwise
     **/
    @Override
    public boolean isFreeText() {
        if (this.identifier != null) {
            return false;
        }
        /*
        From: Tull Glazener
        I think it's pretty common practice for lots of institutions to send something in CE.3 even when CE.1 is empty.
        Usually that's because they sometimes DO have a code in CE.1,
        and it's just easiest for them to always put the same coding system in CE.3 than to leave it out sometimes.
        Since this happens a lot, I think it probably makes sense to modify the ORU processor to just ignore CE.3 if there is nothing in CE.1.
        */
        /*if (nameOfCodingSystem != null)
        {	return false;
        }*/
        if (this.alternateIdentifier != null) {
            return false;
        }
        /*if (nameOfAlternateCodingSystem != null)
        {	return false;
        }*/
        // ExpandedConceptData instances also have version IDs, which are not free text.
        // However, those should be meaningless if populated without the coding system fields.
        return true;
    }
    
    /**
     * Retrieves the ConceptData's code
     * 
     * @return the ConceptData's code
     **/
    @Override
    public String toCode() {
        final String identifier = getIdentifier();
        if (Util.isValued(identifier)) {
            return identifier;
        }
        return Util.nvls(this.alternateIdentifier, this.secondAlternateIdentifier);
    }
    
    /**
     * Retrieves the ConceptData's text
     * 
     * @return the ConceptData's text
     **/
    @Override
    public String toText() {
        if (Util.isValued(this.text)) {
            return this.text;
        } else if (Util.isValued(this.alternateText)) {
            return this.alternateText;
        } else if (Util.isValued(this.secondAlternateText)) {
            return this.secondAlternateText;
        }
        return this.originalText;
    }
    
    /**
     * Retrieves the ConceptData's text or code
     * 
     * @return the ConceptData's text or code
     **/
    @Override
    public String toDisplay() {
        final String s = toText();
        
        return Util.isValued(s) ? s : toCode();
    }
    
    /**
     * Retrieves the code, or the text if no code is present
     * 
     * @return the code or text
     **/
    @Override
    public String getCodeOrText() {
        final String code = toCode();
        
        return Util.isValued(code) ? code : toText();
    }
    
    /**
     * Retrieves a String containing the identifier, text, and name of coding system
     * 
     * @return the String
     **/
    @Override
    public String toFullString() {
        return "[" + this.identifier + ", " + this.text + ", " + this.nameOfCodingSystem + "]";
    }
    
    /**
     * Clears the ConceptData
     **/
    @Override
    public void clear() {
        this.currTag = null;
        this.identifier = null;
        this.text = null;
        this.nameOfCodingSystem = null;
        this.alternateIdentifier = null;
        this.alternateText = null;
        this.nameOfAlternateCodingSystem = null;
        this.codingSystemVersionID = null;
        this.alternateCodingSystemVersionID = null;
        this.originalText = null;
        this.secondAlternateIdentifier = null;
        this.secondAlternateText = null;
        this.secondNameOfAlternateCodingSystem = null;
        this.secondAlternateCodingSystemVersionID = null;
        this.codingSystemOID = null;
        this.valueSetOID = null;
        this.valueSetVersionID = null;
        this.alternateCodingSystemOID = null;
        this.alternateValueSetOID = null;
        this.alternateValueSetVersionID = null;
        this.secondAlternateCodingSystemOID = null;
        this.secondAlternateValueSetOID = null;
        this.secondAlternateValueSetVersionID = null;
    }
    
    /**
     * Retrieves the code of a ConceptData
     * 
     * @param cd the ConceptData
     * @return the code
     **/
    public final static String toCode(final ConceptData cd) {
        return cd == null ? null : cd.toCode();
    }
    
    public final static List<String> toCodes(final List<? extends ConceptData> list) {
        if (list == null) {
            return null;
        }
        final List<String> r = new ArrayList<String>(list.size());
        for (final ConceptData cd : list) {
            r.add(toCode(cd));
        }
        return r;
    }
    
    /**
     * Retrieves the text of a ConceptData
     * 
     * @param cd the ConceptData
     * @return the text
     **/
    public final static String getText(final ConceptData cd) {
        return cd == null ? null : cd.getText();
    }
    
    /**
     * Retrieves the alternate text of a ConceptData
     * 
     * @param cd the ConceptData
     * @return the alternate text
     **/
    public final static String getAlternateText(final ConceptData cd) {
        return cd == null ? null : cd.getAlternateText();
    }
    
    /**
     * Retrieves the code, or the text if no code is present
     * 
     * @param cd the ConceptData
     * @return the code or text
     **/
    public final static String getCodeOrText(final ConceptData cd) {
        return cd == null ? null : cd.getCodeOrText();
    }
}
