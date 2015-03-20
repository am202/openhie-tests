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
package org.regenstrief.hl7.segment;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import org.regenstrief.hl7.HL7Parser;
import org.regenstrief.hl7.HL7Properties;
import org.regenstrief.hl7.datatype.CE;
import org.regenstrief.hl7.datatype.CWE;
import org.regenstrief.hl7.datatype.CX;
import org.regenstrief.hl7.datatype.DLN;
import org.regenstrief.hl7.datatype.HD;
import org.regenstrief.hl7.datatype.NM;
import org.regenstrief.hl7.datatype.SI;
import org.regenstrief.hl7.datatype.TS;
import org.regenstrief.hl7.datatype.XAD;
import org.regenstrief.hl7.datatype.XPN;
import org.regenstrief.hl7.datatype.XTN;
import org.regenstrief.util.Util;

/**
 * <p>
 * Title: PID
 * </p>
 * <p>
 * Description: HL7 Patient Identification
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
public class PID extends HL7Segment {
    
    public final static String PID_XML = "PID";
    
    public final static String SET_ID_PID_XML = "PID.1";
    
    public final static String PATIENT_ID_XML = "PID.2";
    
    public final static String PATIENT_IDENTIFIER_XML = "PID.3";
    
    public final static String ALTERNATE_PATIENT_ID_PID_XML = "PID.4";
    
    public final static String PATIENT_NAME_XML = "PID.5";
    
    public final static String MOTHERS_MAIDEN_NAME_XML = "PID.6";
    
    public final static String DATE_TIME_OF_BIRTH_XML = "PID.7";
    
    public final static String SEX_XML = "PID.8";
    
    public final static String PATIENT_ALIAS_XML = "PID.9";
    
    public final static String RACE_XML = "PID.10";
    
    public final static String PATIENT_ADDRESS_XML = "PID.11";
    
    public final static String COUNTY_CODE_XML = "PID.12";
    
    public final static String PHONE_NUMBER_HOME_XML = "PID.13";
    
    public final static String PHONE_NUMBER_BUSINESS_XML = "PID.14";
    
    public final static String PRIMARY_LANGUAGE_XML = "PID.15";
    
    public final static String MARITAL_STATUS_XML = "PID.16";
    
    public final static String RELIGION_XML = "PID.17";
    
    public final static String PATIENT_ACCOUNT_NUMBER_XML = "PID.18";
    
    public final static String SSN_NUMBER_PATIENT_XML = "PID.19";
    
    public final static String DRIVERS_LICENSE_NUMBER_XML = "PID.20";
    
    public final static String MOTHERS_IDENTIFIER_XML = "PID.21";
    
    public final static String ETHNIC_GROUP_XML = "PID.22";
    
    public final static String BIRTHPLACE_XML = "PID.23";
    
    public final static String MULTIPLE_BIRTH_INDICATOR_XML = "PID.24";
    
    public final static String BIRTH_ORDER_XML = "PID.25";
    
    public final static String CITIZENSHIP_XML = "PID.26";
    
    public final static String VETERAN_MILITARY_STATUS_XML = "PID.27";
    
    public final static String NATIONALITY_XML = "PID.28";
    
    public final static String PATIENT_DEATH_DATE_AND_TIME_XML = "PID.29";
    
    public final static String PATIENT_DEATH_INDICATOR_XML = "PID.30";
    
    public final static String IDENTITY_UNKNOWN_INDICATOR_XML = "PID.31";
    
    public final static String IDENTITY_RELIABILITY_CODE_XML = "PID.32";
    
    public final static String LAST_UPDATE_DATE_TIME_XML = "PID.33";
    
    public final static String LAST_UPDATE_FACILITY_XML = "PID.34";
    
    public final static String SPECIES_CODE_XML = "PID.35";
    
    public final static String BREED_CODE_XML = "PID.36";
    
    public final static String STRAIN_XML = "PID.37";
    
    public final static String PRODUCTION_CLASS_CODE_XML = "PID.38";
    
    public final static String TRIBAL_CITIZENSHIP_XML = "PID.39";
    
    public final static String PATIENT_TELECOMMUNICATION_INFORMATION_XML = "PID.40";
    
    private SI setIDPID = null;
    
    private CX patientID = null;
    
    private List<CX> patientIdentifier = null; //patient.medical_record_number*, patient_alias_number.*
    
    private List<CX> alternatePatientIDPID = null;
    
    private List<XPN> patientName = null; //patient.name*, patient_alias_name.*
    
    private List<XPN> mothersMaidenName = null; //patient.mothers_first_name, patient.mothers_maiden_name
    
    private TS dateTimeOfBirth = null; //patient.date_of_birth
    
    private CWE sex = null; //patient.gender*
    
    private List<XPN> patientAlias = null; //patient_alias_name.*
    
    private List<CE> race = null; //patient.race*
    
    private List<XAD> patientAddress = null; //patient.home_address*
    
    private String countyCode = null;
    
    private List<XTN> phoneNumberHome = null; //patient.night_phone_number
    
    private List<XTN> phoneNumberBusiness = null; //patient.day_phone_number
    
    private CE primaryLanguage = null;
    
    private CE maritalStatus = null; //patient.marital_status*
    
    private CE religion = null; //patient.religion*
    
    private CX patientAccountNumber = null;
    
    private String ssnNumberPatient = null; //patient.social_security_number
    
    private DLN driversLicenseNumber = null;
    
    private List<CX> mothersIdentifier = null;
    
    private List<CE> ethnicGroup = null;
    
    private String birthplace = null;
    
    private String multipleBirthIndicator = null;
    
    private NM birthOrder = null;
    
    private List<CE> citizenship = null;
    
    private CE veteranMilitaryStatus = null; //patient.veteran_yn
    
    private CE nationality = null;
    
    private TS patientDeathDateAndTime = null; //patient.date_of_death
    
    private String patientDeathIndicator = null;
    
    private String identityUnknownIndicator = null;
    
    private List<String> identityReliabilityCode = null;
    
    private TS lastUpdateDateTime = null;
    
    private HD lastUpdateFacility = null;
    
    private CE speciesCode = null; //person.human_yn
    
    private CE breedCode = null;
    
    private String strain = null;
    
    private List<CE> productionClassCode = null;
    
    private List<CWE> tribalCitizenship = null;
    
    private List<XTN> patientTelecommunicationInformation = null;
    
    /**
     * Constructs an empty PID
     * 
     * @param prop the HL7Properties
     **/
    public PID(final HL7Properties prop) {
        super(prop);
    }
    
    /**
     * Retrieves the registration information
     * 
     * @return the registration information
     **/
    public PID getRegistration() {
        final PID pid = new PID(this.prop);
        
        pid.setPatientIdentifier(getPatientIdentifier());
        
        return pid.removeSSN();
    }
    
    /**
     * Adds missing required fields
     * 
     * @return the HL7Data with all required fields
     **/
    @Override
    public PID addRequired() {
        if (Util.isEmpty(this.patientIdentifier)) {
            addPatientIdentifier(new CX(this.prop));
        }
        if (Util.isEmpty(this.patientName)) {
            addPatientName(new XPN(this.prop));
        }
        
        return this;
    }
    
    /**
     * Removes the SSN from the PID
     * 
     * @return the PID
     **/
    public PID removeSSN() {
        this.ssnNumberPatient = null;
        for (int i = 0; i < Util.size(this.patientIdentifier); i++) { // size changes and cannot be precomputed
            final CX cx = this.patientIdentifier.get(i);
            // We don't generally wanting HL7 objects using our database objects.
            // So we could store this constant in ConceptSystem and somewhere in the hl7 project.
            // Or we could put the constant in a more neutral place in the Core project.
            // We could create an HL7Constants class in the util package.
            if ("SS".equals(cx.getIdentifierTypeCode())) {
                this.patientIdentifier.remove(i);
                i--;
            }
        }
        
        return this;
    }
    
    public static PID parsePiped(final HL7Parser parser, final String line) {
        final PID pid = new PID(parser);
        pid.readPiped(parser, line);
        return pid;
    }
    
    @Override
    public void readPiped(final HL7Parser parser, final String line) {
        final char f = parser.getFieldSeparator();
        int start = line.indexOf(f) + 1;
        if (start <= 0) {
            return;
        }
        int stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        final char c = parser.getComponentSeparator();
        this.setIDPID = SI.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.patientID = CX.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        final char r = parser.getRepetitionSeparator();
        while (start < stop) {
            final int next = getNext(line, start, r, stop);
            addPatientIdentifier(CX.parsePiped(parser, line, start, c, next));
            start = next + 1;
        }
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        while (start < stop) {
            final int next = getNext(line, start, r, stop);
            addAlternatePatientIDPID(CX.parsePiped(parser, line, start, c, next));
            start = next + 1;
        }
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        while (start < stop) {
            final int next = getNext(line, start, r, stop);
            addPatientName(XPN.parsePiped(parser, line, start, c, next));
            start = next + 1;
        }
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        while (start < stop) {
            final int next = getNext(line, start, r, stop);
            addMothersMaidenName(XPN.parsePiped(parser, line, start, c, next));
            start = next + 1;
        }
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.dateTimeOfBirth = TS.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.sex = CWE.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        while (start < stop) {
            final int next = getNext(line, start, r, stop);
            addPatientAlias(XPN.parsePiped(parser, line, start, c, next));
            start = next + 1;
        }
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        while (start < stop) {
            final int next = getNext(line, start, r, stop);
            addRace(CE.parsePiped(parser, line, start, c, next));
            start = next + 1;
        }
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        while (start < stop) {
            final int next = getNext(line, start, r, stop);
            addPatientAddress(XAD.parsePiped(parser, line, start, c, next));
            start = next + 1;
        }
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.countyCode = getToken(line, start, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        while (start < stop) {
            final int next = getNext(line, start, r, stop);
            addPhoneNumberHome(XTN.parsePiped(parser, line, start, c, next));
            start = next + 1;
        }
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        while (start < stop) {
            final int next = getNext(line, start, r, stop);
            addPhoneNumberBusiness(XTN.parsePiped(parser, line, start, c, next));
            start = next + 1;
        }
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.primaryLanguage = CE.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.maritalStatus = CE.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.religion = CE.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.patientAccountNumber = CX.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.ssnNumberPatient = getToken(line, start, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.driversLicenseNumber = DLN.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        while (start < stop) {
            final int next = getNext(line, start, r, stop);
            addMothersIdentifier(CX.parsePiped(parser, line, start, c, next));
            start = next + 1;
        }
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        while (start < stop) {
            final int next = getNext(line, start, r, stop);
            addEthnicGroup(CE.parsePiped(parser, line, start, c, next));
            start = next + 1;
        }
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.birthplace = getToken(line, start, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.multipleBirthIndicator = getToken(line, start, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.birthOrder = NM.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        while (start < stop) {
            final int next = getNext(line, start, r, stop);
            addCitizenship(CE.parsePiped(parser, line, start, c, next));
            start = next + 1;
        }
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.veteranMilitaryStatus = CE.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.nationality = CE.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.patientDeathDateAndTime = TS.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.patientDeathIndicator = getToken(line, start, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.identityUnknownIndicator = getToken(line, start, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        while (start < stop) {
            final int next = getNext(line, start, r, stop);
            addIdentityReliabilityCode(getToken(line, start, next));
            start = next + 1;
        }
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.lastUpdateDateTime = TS.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.lastUpdateFacility = HD.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.speciesCode = CE.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.breedCode = CE.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.strain = getToken(line, start, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        while (start < stop) {
            final int next = getNext(line, start, r, stop);
            addProductionClassCode(CE.parsePiped(parser, line, start, c, next));
            start = next + 1;
        }
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        while (start < stop) {
            final int next = getNext(line, start, r, stop);
            addTribalCitizenship(CWE.parsePiped(parser, line, start, c, next));
            start = next + 1;
        }
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        while (start < stop) {
            final int next = getNext(line, start, r, stop);
            addPatientTelecommunicationInformation(XTN.parsePiped(parser, line, start, c, next));
            start = next + 1;
        }
        
        assertLast(line, stop + 1, f, line.length());
    }
    
    @Override
    public void toPiped(final Writer w) throws IOException {
        int last = startSegment(w, getTagName()); // Will be different than PID_XML for the ZID subclass
        
        last = addField(w, this.setIDPID, last, 1);
        last = addField(w, this.patientID, last, 2);
        last = addField(w, this.patientIdentifier, last, 3);
        last = addField(w, this.alternatePatientIDPID, last, 4);
        last = addField(w, this.patientName, last, 5);
        last = addField(w, this.mothersMaidenName, last, 6);
        last = addField(w, this.dateTimeOfBirth, last, 7);
        last = addField(w, this.sex, last, 8);
        last = addField(w, this.patientAlias, last, 9);
        last = addField(w, this.race, last, 10);
        last = addField(w, this.patientAddress, last, 11);
        last = addField(w, this.countyCode, last, 12);
        last = addField(w, this.phoneNumberHome, last, 13);
        last = addField(w, this.phoneNumberBusiness, last, 14);
        last = addField(w, this.primaryLanguage, last, 15);
        last = addField(w, this.maritalStatus, last, 16);
        last = addField(w, this.religion, last, 17);
        last = addField(w, this.patientAccountNumber, last, 18);
        last = addField(w, this.ssnNumberPatient, last, 19);
        last = addField(w, this.driversLicenseNumber, last, 20);
        last = addField(w, this.mothersIdentifier, last, 21);
        last = addField(w, this.ethnicGroup, last, 22);
        last = addField(w, this.birthplace, last, 23);
        last = addField(w, this.multipleBirthIndicator, last, 24);
        last = addField(w, this.birthOrder, last, 25);
        last = addField(w, this.citizenship, last, 26);
        last = addField(w, this.veteranMilitaryStatus, last, 27);
        last = addField(w, this.nationality, last, 28);
        last = addField(w, this.patientDeathDateAndTime, last, 29);
        last = addField(w, this.patientDeathIndicator, last, 30);
        last = addField(w, this.identityUnknownIndicator, last, 31);
        last = addField(w, this.identityReliabilityCode, last, 32);
        last = addField(w, this.lastUpdateDateTime, last, 33);
        last = addField(w, this.lastUpdateFacility, last, 34);
        last = addField(w, this.speciesCode, last, 35);
        last = addField(w, this.breedCode, last, 36);
        last = addField(w, this.strain, last, 37);
        last = addField(w, this.productionClassCode, last, 38);
        last = addField(w, this.tribalCitizenship, last, 39);
        last = addField(w, this.patientTelecommunicationInformation, last, 40);
        
        endSegment(w);
    }
    
    /**
     * Retrieves the alternate patient ID PID
     * 
     * @return the alternate patient ID PID
     **/
    public List<CX> getAlternatePatientIDPID() {
        return this.alternatePatientIDPID;
    }
    
    /**
     * Retrieves the veteran military status
     * 
     * @return the veteran military status
     **/
    public CE getVeteranMilitaryStatus() {
        return this.veteranMilitaryStatus;
    }
    
    /**
     * Retrieves the social security number
     * 
     * @return the social security number
     **/
    public String getSsnNumberPatient() {
        return this.ssnNumberPatient;
    }
    
    /**
     * Retrieves the sex
     * 
     * @return the sex
     **/
    public CWE getSex() {
        return this.sex;
    }
    
    /**
     * Retrieves the set ID PID
     * 
     * @return the set ID PID
     **/
    public SI getSetIDPID() {
        return this.setIDPID;
    }
    
    /**
     * Retrieves the religion
     * 
     * @return the religion
     **/
    public CE getReligion() {
        return this.religion;
    }
    
    /**
     * Retrieves the race
     * 
     * @return the race
     **/
    public List<CE> getRace() {
        return this.race;
    }
    
    /**
     * Retrieves the primary language
     * 
     * @return the primary language
     **/
    public CE getPrimaryLanguage() {
        return this.primaryLanguage;
    }
    
    /**
     * Retrieves the phone number home
     * 
     * @return the phone number home
     **/
    public List<XTN> getPhoneNumberHome() {
        return this.phoneNumberHome;
    }
    
    /**
     * Retrieves the phone number business
     * 
     * @return the phone number business
     **/
    public List<XTN> getPhoneNumberBusiness() {
        return this.phoneNumberBusiness;
    }
    
    /**
     * Retrieves the patient name
     * 
     * @return the patient name
     **/
    public List<XPN> getPatientName() {
        return this.patientName;
    }
    
    /**
     * Retrieves the patient identifier
     * 
     * @return the patient identifier
     **/
    public List<CX> getPatientIdentifier() {
        return this.patientIdentifier;
    }
    
    /**
     * Retrieves the patient ID
     * 
     * @return the patient ID
     **/
    public CX getPatientID() {
        return this.patientID;
    }
    
    /**
     * Retrieves the patient death indicator
     * 
     * @return the patient death indicator
     **/
    public String getPatientDeathIndicator() {
        return this.patientDeathIndicator;
    }
    
    /**
     * Retrieves the patient death date and time
     * 
     * @return the patient death date and time
     **/
    public TS getPatientDeathDateAndTime() {
        return this.patientDeathDateAndTime;
    }
    
    /**
     * Retrieves the patient alias
     * 
     * @return the patient alias
     **/
    public List<XPN> getPatientAlias() {
        return this.patientAlias;
    }
    
    /**
     * Retrieves the patient address
     * 
     * @return the patient address
     **/
    public List<XAD> getPatientAddress() {
        return this.patientAddress;
    }
    
    /**
     * Retrieves the patient account number
     * 
     * @return the patient account number
     **/
    public CX getPatientAccountNumber() {
        return this.patientAccountNumber;
    }
    
    /**
     * Retrieves the nationality
     * 
     * @return the nationality
     **/
    public CE getNationality() {
        return this.nationality;
    }
    
    /**
     * Retrieves the multiple birth indicator
     * 
     * @return the multiple birth indicator
     **/
    public String getMultipleBirthIndicator() {
        return this.multipleBirthIndicator;
    }
    
    /**
     * Retrieves the mother's maiden name
     * 
     * @return the mother's maiden name
     **/
    public List<XPN> getMothersMaidenName() {
        return this.mothersMaidenName;
    }
    
    /**
     * Retrieves the mother's identifier
     * 
     * @return the mother's identifier
     **/
    public List<CX> getMothersIdentifier() {
        return this.mothersIdentifier;
    }
    
    /**
     * Retrieves the marital status
     * 
     * @return the marital status
     **/
    public CE getMaritalStatus() {
        return this.maritalStatus;
    }
    
    /**
     * Retrieves the ethnic group
     * 
     * @return the ethnic group
     **/
    public List<CE> getEthnicGroup() {
        return this.ethnicGroup;
    }
    
    /**
     * Retrieves the driver's license number
     * 
     * @return the driver's license number
     **/
    public DLN getDriversLicenseNumber() {
        return this.driversLicenseNumber;
    }
    
    /**
     * Retrieves the date/time of birth
     * 
     * @return the date/time of birth
     **/
    public TS getDateTimeOfBirth() {
        return this.dateTimeOfBirth;
    }
    
    /**
     * Retrieves the country code
     * 
     * @return the country code
     **/
    public String getCountyCode() {
        return this.countyCode;
    }
    
    /**
     * Retrieves the citizenship
     * 
     * @return the citizenship
     **/
    public List<CE> getCitizenship() {
        return this.citizenship;
    }
    
    /**
     * Retrieves the birthplace
     * 
     * @return the birthplace
     **/
    public String getBirthplace() {
        return this.birthplace;
    }
    
    /**
     * Retrieves the birth order
     * 
     * @return the birth order
     **/
    public NM getBirthOrder() {
        return this.birthOrder;
    }
    
    /**
     * Retrieves the identity unknown indicator
     * 
     * @return the identity unknown indicator
     **/
    public String getIdentityUnknownIndicator() {
        return this.identityUnknownIndicator;
    }
    
    /**
     * Retrieves the identity reliability code list
     * 
     * @return the identity reliability code list
     **/
    public List<String> getIdentityReliabilityCode() {
        return this.identityReliabilityCode;
    }
    
    /**
     * Retrieves the last update date/time
     * 
     * @return the last update date/time
     **/
    public TS getLastUpdateDateTime() {
        return this.lastUpdateDateTime;
    }
    
    /**
     * Retrieves the last update facility
     * 
     * @return the last update facility
     **/
    public HD getLastUpdateFacility() {
        return this.lastUpdateFacility;
    }
    
    /**
     * Retrieves the species code
     * 
     * @return the species code
     **/
    public CE getSpeciesCode() {
        return this.speciesCode;
    }
    
    /**
     * Retrieves the breed code
     * 
     * @return the breed code
     **/
    public CE getBreedCode() {
        return this.breedCode;
    }
    
    /**
     * Retrieves the strain
     * 
     * @return the strain
     **/
    public String getStrain() {
        return this.strain;
    }
    
    /**
     * Retrieves the production class code list
     * 
     * @return the production class code list
     **/
    public List<CE> getProductionClassCode() {
        return this.productionClassCode;
    }
    
    /**
     * Retrieves the tribal citizenship list
     * 
     * @return the tribal citizenship list
     **/
    public List<CWE> getTribalCitizenship() {
        return this.tribalCitizenship;
    }
    
    /**
     * Retrieves the patient telecommunication information list
     * 
     * @return the patient telecommunication information list
     **/
    public List<XTN> getPatientTelecommunicationInformation() {
        return this.patientTelecommunicationInformation;
    }
    
    /**
     * Modifies the alternate patient ID PID list
     * 
     * @param alternatePatientIDPID the new alternate patient ID PID list
     **/
    public void setAlternatePatientIDPID(final List<CX> alternatePatientIDPID) {
        this.alternatePatientIDPID = alternatePatientIDPID;
    }
    
    /**
     * Modifies the alternate patient ID PID list
     * 
     * @param alternatePatientIDPID the new alternate patient ID PID
     **/
    public void addAlternatePatientIDPID(final CX alternatePatientIDPID) {
        this.alternatePatientIDPID = Util.add(this.alternatePatientIDPID, alternatePatientIDPID);
    }
    
    /**
     * Modifies the veteran military status
     * 
     * @param veteranMilitaryStatus the new veteran military status
     **/
    public void setVeteranMilitaryStatus(final CE veteranMilitaryStatus) {
        this.veteranMilitaryStatus = veteranMilitaryStatus;
    }
    
    /**
     * Modifies the social security number
     * 
     * @param ssnNumberPatient the new social security number
     **/
    public void setSsnNumberPatient(final String ssnNumberPatient) {
        this.ssnNumberPatient = ssnNumberPatient;
    }
    
    /**
     * Modifies the sex
     * 
     * @param sex the new sex
     **/
    public void setSex(final CWE sex) {
        this.sex = sex;
    }
    
    /**
     * Modifies the set ID PID
     * 
     * @param setIDPID the new set ID PID
     **/
    public void setSetIDPID(final SI setIDPID) {
        this.setIDPID = setIDPID;
    }
    
    /**
     * Modifies the religion
     * 
     * @param religion the new religion
     **/
    public void setReligion(final CE religion) {
        this.religion = religion;
    }
    
    /**
     * Modifies the race
     * 
     * @param race the new race
     **/
    public void setRace(final List<CE> race) {
        this.race = race;
    }
    
    /**
     * Modifies the race
     * 
     * @param race the new race
     **/
    public void addRace(final CE race) {
        this.race = Util.add(this.race, race);
    }
    
    /**
     * Modifies the primary language
     * 
     * @param primaryLanguage the new primary language
     **/
    public void setPrimaryLanguage(final CE primaryLanguage) {
        this.primaryLanguage = primaryLanguage;
    }
    
    /**
     * Modifies the phone number home list
     * 
     * @param phoneNumberHome the new phone number home list
     **/
    public void setPhoneNumberHome(final List<XTN> phoneNumberHome) {
        this.phoneNumberHome = phoneNumberHome;
    }
    
    /**
     * Modifies the phone number home list
     * 
     * @param phoneNumberHome the new phone number home
     **/
    public void addPhoneNumberHome(final XTN phoneNumberHome) {
        this.phoneNumberHome = Util.add(this.phoneNumberHome, phoneNumberHome);
    }
    
    /**
     * Modifies the phone number business list
     * 
     * @param phoneNumberBusiness the new phone number business list
     **/
    public void setPhoneNumberBusiness(final List<XTN> phoneNumberBusiness) {
        this.phoneNumberBusiness = phoneNumberBusiness;
    }
    
    /**
     * Modifies the phone number business list
     * 
     * @param phoneNumberBusiness the new phone number business
     **/
    public void addPhoneNumberBusiness(final XTN phoneNumberBusiness) {
        this.phoneNumberBusiness = Util.add(this.phoneNumberBusiness, phoneNumberBusiness);
    }
    
    /**
     * Modifies the patient name list
     * 
     * @param patientName the new patient name list
     **/
    public void setPatientName(final List<XPN> patientName) {
        this.patientName = patientName;
    }
    
    /**
     * Modifies the patient name list
     * 
     * @param patientName the new patient name
     **/
    public void addPatientName(final XPN patientName) {
        this.patientName = Util.add(this.patientName, patientName);
    }
    
    /**
     * Modifies the patient identifier list
     * 
     * @param patientIdentifier the new patient identifier list
     **/
    public void setPatientIdentifier(final List<CX> patientIdentifier) {
        this.patientIdentifier = patientIdentifier;
    }
    
    /**
     * Modifies the patient identifier list
     * 
     * @param patientIdentifier the new patient identifier
     **/
    public void addPatientIdentifier(final CX patientIdentifier) {
        this.patientIdentifier = Util.add(this.patientIdentifier, patientIdentifier);
    }
    
    /**
     * Modifies the patient ID
     * 
     * @param patientID the new patient ID
     **/
    public void setPatientID(final CX patientID) {
        this.patientID = patientID;
    }
    
    /**
     * Modifies the patient death indicator
     * 
     * @param patientDeathIndicator the new patient death indicator
     **/
    public void setPatientDeathIndicator(final String patientDeathIndicator) {
        this.patientDeathIndicator = patientDeathIndicator;
    }
    
    /**
     * Modifies the patient death date and time
     * 
     * @param patientDeathDateAndTime the new patient death date and time
     **/
    public void setPatientDeathDateAndTime(final TS patientDeathDateAndTime) {
        this.patientDeathDateAndTime = patientDeathDateAndTime;
    }
    
    /**
     * Modifies the patient alias list
     * 
     * @param patientAlias the new patient alias list
     **/
    public void setPatientAlias(final List<XPN> patientAlias) {
        this.patientAlias = patientAlias;
    }
    
    /**
     * Modifies the patient alias list
     * 
     * @param patientAlias the new patient alias
     **/
    public void addPatientAlias(final XPN patientAlias) {
        this.patientAlias = Util.add(this.patientAlias, patientAlias);
    }
    
    /**
     * Modifies the patient address list
     * 
     * @param patientAddress the new patient address list
     **/
    public void setPatientAddress(final List<XAD> patientAddress) {
        this.patientAddress = patientAddress;
    }
    
    /**
     * Modifies the patient address list
     * 
     * @param patientAddress the new patient address
     **/
    public void addPatientAddress(final XAD patientAddress) {
        this.patientAddress = Util.add(this.patientAddress, patientAddress);
    }
    
    /**
     * Modifies the patient account number
     * 
     * @param patientAccountNumber the new patient account number
     **/
    public void setPatientAccountNumber(final CX patientAccountNumber) {
        this.patientAccountNumber = patientAccountNumber;
    }
    
    /**
     * Modifies the nationality
     * 
     * @param nationality the new nationality
     **/
    public void setNationality(final CE nationality) {
        this.nationality = nationality;
    }
    
    /**
     * Modifies the multiple birth indicator
     * 
     * @param multipleBirthIndicator the new multiple birth indicator
     **/
    public void setMultipleBirthIndicator(final String multipleBirthIndicator) {
        this.multipleBirthIndicator = multipleBirthIndicator;
    }
    
    /**
     * Modifies the mother's maiden name list
     * 
     * @param mothersMaidenName the new mother's maiden name list
     **/
    public void setMothersMaidenName(final List<XPN> mothersMaidenName) {
        this.mothersMaidenName = mothersMaidenName;
    }
    
    /**
     * Modifies the mother's maiden name list
     * 
     * @param mothersMaidenName the new mother's maiden name
     **/
    public void addMothersMaidenName(final XPN mothersMaidenName) {
        this.mothersMaidenName = Util.add(this.mothersMaidenName, mothersMaidenName);
    }
    
    /**
     * Modifies the mother's identifier list
     * 
     * @param mothersIdentifier the new mother's identifier list
     **/
    public void setMothersIdentifier(final List<CX> mothersIdentifier) {
        this.mothersIdentifier = mothersIdentifier;
    }
    
    /**
     * Modifies the mother's identifier list
     * 
     * @param mothersIdentifier the new mother's identifier
     **/
    public void addMothersIdentifier(final CX mothersIdentifier) {
        this.mothersIdentifier = Util.add(this.mothersIdentifier, mothersIdentifier);
    }
    
    /**
     * Modifies the marital status
     * 
     * @param maritalStatus the new marital status
     **/
    public void setMaritalStatus(final CE maritalStatus) {
        this.maritalStatus = maritalStatus;
    }
    
    /**
     * Modifies the ethnic group list
     * 
     * @param ethnicGroup the new ethnic group list
     **/
    public void setEthnicGroup(final List<CE> ethnicGroup) {
        this.ethnicGroup = ethnicGroup;
    }
    
    /**
     * Modifies the ethnic group list
     * 
     * @param ethnicGroup the new ethnic group
     **/
    public void addEthnicGroup(final CE ethnicGroup) {
        this.ethnicGroup = Util.add(this.ethnicGroup, ethnicGroup);
    }
    
    /**
     * Modifies the driver's license number
     * 
     * @param driversLicenseNumber the new driver's license number
     **/
    public void setDriversLicenseNumber(final DLN driversLicenseNumber) {
        this.driversLicenseNumber = driversLicenseNumber;
    }
    
    /**
     * Modifies the date/time of birth
     * 
     * @param dateTimeOfBirth the new date/time of birth
     **/
    public void setDateTimeOfBirth(final TS dateTimeOfBirth) {
        this.dateTimeOfBirth = dateTimeOfBirth;
    }
    
    /**
     * Modifies the county code
     * 
     * @param countyCode the new county code
     **/
    public void setCountyCode(final String countyCode) {
        this.countyCode = countyCode;
    }
    
    /**
     * Modifies the citizenship list
     * 
     * @param citizenship the new citizenship list
     **/
    public void setCitizenship(final List<CE> citizenship) {
        this.citizenship = citizenship;
    }
    
    /**
     * Modifies the citizenship list
     * 
     * @param citizenship the new citizenship
     **/
    public void addCitizenship(final CE citizenship) {
        this.citizenship = Util.add(this.citizenship, citizenship);
    }
    
    /**
     * Modifies the birthplace
     * 
     * @param birthplace the new birthplace
     **/
    public void setBirthplace(final String birthplace) {
        this.birthplace = birthplace;
    }
    
    /**
     * Modifies the birth order
     * 
     * @param birthOrder the new birth order
     **/
    public void setBirthOrder(final NM birthOrder) {
        this.birthOrder = birthOrder;
    }
    
    /**
     * Modifies the identity unknown indicator
     * 
     * @param identityUnknownIndicator the new identity unknown indicator
     **/
    public void setIdentityUnknownIndicator(final String identityUnknownIndicator) {
        this.identityUnknownIndicator = identityUnknownIndicator;
    }
    
    /**
     * Modifies the identity reliability code
     * 
     * @param identityReliabilityCode the new identity reliability code
     **/
    public void setIdentityReliabilityCode(final List<String> identityReliabilityCode) {
        this.identityReliabilityCode = identityReliabilityCode;
    }
    
    /**
     * Modifies the identity reliability code
     * 
     * @param identityReliabilityCode the new identity reliability code
     **/
    public void addIdentityReliabilityCode(final String identityReliabilityCode) {
        this.identityReliabilityCode = Util.add(this.identityReliabilityCode, identityReliabilityCode);
    }
    
    /**
     * Modifies the last update date/time
     * 
     * @param lastUpdateDateTime the new last update date/time
     **/
    public void setLastUpdateDateTime(final TS lastUpdateDateTime) {
        this.lastUpdateDateTime = lastUpdateDateTime;
    }
    
    /**
     * Modifies the last update facility
     * 
     * @param lastUpdateFacility the new last update facility
     **/
    public void setLastUpdateFacility(final HD lastUpdateFacility) {
        this.lastUpdateFacility = lastUpdateFacility;
    }
    
    /**
     * Modifies the species code
     * 
     * @param speciesCode the new species code
     **/
    public void setSpeciesCode(final CE speciesCode) {
        this.speciesCode = speciesCode;
    }
    
    /**
     * Modifies the breed code
     * 
     * @param breedCode the new breed code
     **/
    public void setBreedCode(final CE breedCode) {
        this.breedCode = breedCode;
    }
    
    /**
     * Modifies the strain
     * 
     * @param strain the new strain
     **/
    public void setStrain(final String strain) {
        this.strain = strain;
    }
    
    /**
     * Modifies the production class code
     * 
     * @param productionClassCode the new production class code
     **/
    public void setProductionClassCode(final List<CE> productionClassCode) {
        this.productionClassCode = productionClassCode;
    }
    
    /**
     * Modifies the production class code
     * 
     * @param productionClassCode the new production class code
     **/
    public void addProductionClassCode(final CE productionClassCode) {
        this.productionClassCode = Util.add(this.productionClassCode, productionClassCode);
    }
    
    /**
     * Modifies the tribal citizenship
     * 
     * @param tribalCitizenship the new tribal citizenship
     **/
    public void setTribalCitizenship(final List<CWE> tribalCitizenship) {
        this.tribalCitizenship = tribalCitizenship;
    }
    
    /**
     * Modifies the tribal citizenship
     * 
     * @param tribalCitizenship the new tribal citizenship
     **/
    public void addTribalCitizenship(final CWE tribalCitizenship) {
        this.tribalCitizenship = Util.add(this.tribalCitizenship, tribalCitizenship);
    }
    
    /**
     * Modifies the patient telecommunication information
     * 
     * @param patientTelecommunicationInformation the new patient telecommunication information
     **/
    public void setPatientTelecommunicationInformation(final List<XTN> patientTelecommunicationInformation) {
        this.patientTelecommunicationInformation = patientTelecommunicationInformation;
    }
    
    /**
     * Modifies the patient telecommunication information
     * 
     * @param patientTelecommunicationInformation the new patient telecommunication information
     **/
    public void addPatientTelecommunicationInformation(final XTN patientTelecommunicationInformation) {
        this.patientTelecommunicationInformation = Util.add(this.patientTelecommunicationInformation, patientTelecommunicationInformation);
    }
}
