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

import java.io.IOException;
import java.io.Writer;

import org.regenstrief.hl7.HL7Parser;
import org.regenstrief.hl7.HL7Properties;
import org.regenstrief.util.Util;

/**
 * <p>
 * Title: FN
 * </p>
 * <p>
 * Description: HL7 Family Name
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
public class FN extends HL7DataType {
    
    public final static String FN_XML = "FN";
    
    public final static String SURNAME_XML = "FN.1";
    
    public final static String OWN_SURNAME_PREFIX_XML = "FN.2";
    
    public final static String OWN_SURNAME_XML = "FN.3";
    
    public final static String SURNAME_PREFIX_FROM_PARTNER_SPOUSE_XML = "FN.4";
    
    public final static String SURNAME_FROM_PARTNER_SPOUSE_XML = "FN.5";
    
    private String surname = null;
    
    private String ownSurnamePrefix = null;
    
    private String ownSurname = null;
    
    private String surnamePrefixFromPartnerSpouse = null;
    
    private String surnameFromPartnerSpouse = null;
    
    /**
     * Constructs an empty FN
     * 
     * @param prop the HL7Properties
     **/
    public FN(final HL7Properties prop) {
        super(prop);
    }
    
    /**
     * Constructs a new FN with the given surname
     * 
     * @param prop the HL7Properties
     * @param surname the surname
     * @return the FN
     **/
    public static FN create(final HL7Properties prop, final String surname) {
        if (surname == null) {
            return null;
        }
        
        final FN fn = new FN(prop);
        fn.setSurname(surname);
        
        return fn;
    }
    
    public static FN parsePiped(final HL7Parser parser, final String line, final int start, final char delim, final int stop) {
        if (stop <= start) {
            return null;
        }
        final FN fn = new FN(parser);
        fn.readPiped(parser, line, start, delim, stop);
        return fn;
    }
    
    @Override
    public void readPiped(final HL7Parser parser, final String line, int start, final char delim, final int stop) {
        int next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.surname = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.ownSurnamePrefix = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.ownSurname = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.surnamePrefixFromPartnerSpouse = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.surnameFromPartnerSpouse = getToken(line, start, next);
        
        assertLast(line, next + 1, delim, stop);
    }
    
    @Override
    public void toPiped(final Writer w, final int level) throws IOException {
        int last = 1;
        
        last = addComponent(w, this.surname, last, 1, level);
        last = addComponent(w, this.ownSurnamePrefix, last, 2, level);
        last = addComponent(w, this.ownSurname, last, 3, level);
        last = addComponent(w, this.surnamePrefixFromPartnerSpouse, last, 4, level);
        last = addComponent(w, this.surnameFromPartnerSpouse, last, 5, level);
    }
    
    /**
     * Compares to the given Object
     * 
     * @param o the Object to compare to this
     * @return true if this equals the given Object
     **/
    @Override
    public boolean equals(final Object o) {
        if ((o == null) || !(o instanceof FN)) {
            return false;
        }
        
        return Util.equals(this.surname, ((FN) o).surname);
    }
    
    /**
     * Retrieves the surname
     * 
     * @return the surname
     **/
    public String getSurname() {
        return this.surname;
    }
    
    /**
     * Retrieves the own surname prefix
     * 
     * @return the own surname prefix
     **/
    public String getOwnSurnamePrefix() {
        return this.ownSurnamePrefix;
    }
    
    /**
     * Retrieves the own surname
     * 
     * @return the own surname
     **/
    public String getOwnSurname() {
        return this.ownSurname;
    }
    
    /**
     * Retrieves the surname prefix from partner/spouse
     * 
     * @return the surname prefix from partner/spouse
     **/
    public String getSurnamePrefixFromPartnerSpouse() {
        return this.surnamePrefixFromPartnerSpouse;
    }
    
    /**
     * Retrieves the surname from partner/spouse
     * 
     * @return the surname from partner/spouse
     **/
    public String getSurnameFromPartnerSpouse() {
        return this.surnameFromPartnerSpouse;
    }
    
    /**
     * Modifies the surname
     * 
     * @param surname the new surname
     **/
    public void setSurname(final String surname) {
        this.surname = surname;
    }
    
    /**
     * Modifies the own surname prefix
     * 
     * @param ownSurnamePrefix the new own surname prefix
     **/
    public void setOwnSurnamePrefix(final String ownSurnamePrefix) {
        this.ownSurnamePrefix = ownSurnamePrefix;
    }
    
    /**
     * Modifies the own surname
     * 
     * @param ownSurname the new own surname
     **/
    public void setOwnSurname(final String ownSurname) {
        this.ownSurname = ownSurname;
    }
    
    /**
     * Modifies the surname prefix from partner/spouse
     * 
     * @param surnamePrefixFromPartnerSpouse the new surname prefix from partner/spouse
     **/
    public void setSurnamePrefixFromPartnerSpouse(final String surnamePrefixFromPartnerSpouse) {
        this.surnamePrefixFromPartnerSpouse = surnamePrefixFromPartnerSpouse;
    }
    
    /**
     * Modifies the surname from partner/spouse
     * 
     * @param surnameFromPartnerSpouse the new surname from partner/spouse
     **/
    public void setSurnameFromPartnerSpouse(final String surnameFromPartnerSpouse) {
        this.surnameFromPartnerSpouse = surnameFromPartnerSpouse;
    }
    
    /**
     * Retrieves the surname of the FN
     * 
     * @return the surname
     **/
    @Override
    public String toDisplay() {
        return this.surname != null ? this.surname : Util.concatWithDelim(Util.concatWithDelim(this.ownSurnamePrefix,
            this.ownSurname, " "), Util.concatWithDelim(this.surnamePrefixFromPartnerSpouse, this.surnameFromPartnerSpouse,
            " "), "-");
    }
    
    /**
     * Retrieves the surname of the given FN, or null if the FN is null
     * 
     * @param familyName the FN
     * @return the surname
     **/
    public static String getSurname(final FN familyName) {
        return toDisplay(familyName);
    }
    
    /**
     * Modifies the surname of the given FN, instantiating or nulling it if needed
     * 
     * @param prop the HL7Properties
     * @param familyName the FN
     * @param surname the surname
     * @return the FN
     **/
    public static FN setSurname(final HL7Properties prop, FN familyName, final String surname) {
        if (surname == null) {
            familyName = null;
        } else {
            if (familyName == null) {
                familyName = new FN(prop);
            } else {
                familyName.setOwnSurnamePrefix(null);
                familyName.setOwnSurname(null);
                familyName.setSurnamePrefixFromPartnerSpouse(null);
                familyName.setSurnameFromPartnerSpouse(null);
            }
            familyName.setSurname(surname);
        }
        
        return familyName;
    }
}
