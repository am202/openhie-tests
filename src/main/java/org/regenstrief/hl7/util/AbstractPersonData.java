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

import java.util.List;

import org.regenstrief.hl7.HL7Properties;
import org.regenstrief.hl7.datatype.CE;
import org.regenstrief.hl7.datatype.CWE;
import org.regenstrief.hl7.datatype.TS;
import org.regenstrief.hl7.datatype.XAD;
import org.regenstrief.hl7.datatype.XPN;
import org.regenstrief.hl7.datatype.XTN;
import org.regenstrief.hl7.segment.HL7Segment;
import org.regenstrief.util.Util;

/**
 * <p>
 * Title: AbstractPersonData
 * </p>
 * <p>
 * Description: Interface for HL7 person data
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
public abstract class AbstractPersonData extends HL7Segment implements PersonData {
    
    protected List<XPN> name = null;
    
    protected List<XAD> address = null;
    
    protected List<XTN> phoneNumber = null;
    
    protected List<XTN> businessPhoneNumber = null;
    
    protected CWE administrativeSex = null;
    
    protected TS dateTimeOfBirth = null;
    
    protected CE maritalStatus = null;
    
    protected List<CWE> ambulatoryStatus = null;
    
    protected List<CE> citizenship = null;
    
    protected CE primaryLanguage = null;
    
    protected CWE livingArrangement = null;
    
    protected CE publicityCode = null;
    
    protected String protectionIndicator = null;
    
    protected CWE studentIndicator = null;
    
    protected CE religion = null;
    
    protected List<XPN> mothersMaidenName = null;
    
    protected CE nationality = null;
    
    protected List<CE> ethnicGroup = null;
    
    protected List<CE> race = null;
    
    protected CWE jobStatus = null;
    
    protected CWE handicap = null;
    
    protected String birthPlace = null;
    
    protected CWE vipIndicator = null;
    
    /**
     * Constructs an empty AbstractPersonData
     * 
     * @param prop the HL7Properties
     **/
    public AbstractPersonData(final HL7Properties prop) {
        super(prop);
    }
    
    /**
     * Retrieves the Name
     * 
     * @return the Name
     **/
    @Override
    public List<XPN> getName() {
        return this.name;
    }
    
    /**
     * Retrieves the Address
     * 
     * @return the Address
     **/
    @Override
    public List<XAD> getAddress() {
        return this.address;
    }
    
    /**
     * Retrieves the Phone Number
     * 
     * @return the Phone Number
     **/
    @Override
    public List<XTN> getPhoneNumber() {
        return this.phoneNumber;
    }
    
    /**
     * Retrieves the Business Phone Number
     * 
     * @return the Business Phone Number
     **/
    @Override
    public List<XTN> getBusinessPhoneNumber() {
        return this.businessPhoneNumber;
    }
    
    /**
     * Retrieves the Administrative Sex
     * 
     * @return the Administrative Sex
     **/
    @Override
    public CWE getAdministrativeSex() {
        return this.administrativeSex;
    }
    
    /**
     * Retrieves the Date/Time Of Birth
     * 
     * @return the Date/Time Of Birth
     **/
    @Override
    public TS getDateTimeOfBirth() {
        return this.dateTimeOfBirth;
    }
    
    /**
     * Retrieves the Marital Status
     * 
     * @return the Marital Status
     **/
    @Override
    public CE getMaritalStatus() {
        return this.maritalStatus;
    }
    
    /**
     * Retrieves the Ambulatory Status
     * 
     * @return the Ambulatory Status
     **/
    @Override
    public List<CWE> getAmbulatoryStatus() {
        return this.ambulatoryStatus;
    }
    
    /**
     * Retrieves the Citizenship
     * 
     * @return the Citizenship
     **/
    @Override
    public List<CE> getCitizenship() {
        return this.citizenship;
    }
    
    /**
     * Retrieves the Primary Language
     * 
     * @return the Primary Language
     **/
    @Override
    public CE getPrimaryLanguage() {
        return this.primaryLanguage;
    }
    
    /**
     * Retrieves the Living Arrangement
     * 
     * @return the Living Arrangement
     **/
    @Override
    public CWE getLivingArrangement() {
        return this.livingArrangement;
    }
    
    /**
     * Retrieves the Publicity Code
     * 
     * @return the Publicity Code
     **/
    @Override
    public CE getPublicityCode() {
        return this.publicityCode;
    }
    
    /**
     * Retrieves the Protection Indicator
     * 
     * @return the Protection Indicator
     **/
    @Override
    public String getProtectionIndicator() {
        return this.protectionIndicator;
    }
    
    /**
     * Retrieves the Student Indicator
     * 
     * @return the Student Indicator
     **/
    @Override
    public CWE getStudentIndicator() {
        return this.studentIndicator;
    }
    
    /**
     * Retrieves the Religion
     * 
     * @return the Religion
     **/
    @Override
    public CE getReligion() {
        return this.religion;
    }
    
    /**
     * Retrieves the Mother's Maiden Name
     * 
     * @return the Mother's Maiden Name
     **/
    @Override
    public List<XPN> getMothersMaidenName() {
        return this.mothersMaidenName;
    }
    
    /**
     * Retrieves the Nationality
     * 
     * @return the Nationality
     **/
    @Override
    public CE getNationality() {
        return this.nationality;
    }
    
    /**
     * Retrieves the Ethnic Group
     * 
     * @return the Ethnic Group
     **/
    @Override
    public List<CE> getEthnicGroup() {
        return this.ethnicGroup;
    }
    
    /**
     * Retrieves the race
     * 
     * @return the race
     **/
    @Override
    public List<CE> getRace() {
        return this.race;
    }
    
    /**
     * Retrieves the Job Status
     * 
     * @return the Job Status
     **/
    @Override
    public CWE getJobStatus() {
        return this.jobStatus;
    }
    
    /**
     * Retrieves the Handicap
     * 
     * @return the Handicap
     **/
    @Override
    public CWE getHandicap() {
        return this.handicap;
    }
    
    /**
     * Retrieves the Birth Place
     * 
     * @return the Birth Place
     **/
    @Override
    public String getBirthPlace() {
        return this.birthPlace;
    }
    
    /**
     * Retrieves the VIP Indicator
     * 
     * @return the VIP Indicator
     **/
    @Override
    public CWE getVipIndicator() {
        return this.vipIndicator;
    }
    
    /**
     * Modifies the Name
     * 
     * @param name the new Name
     **/
    @Override
    public void setName(final List<XPN> name) {
        this.name = name;
    }
    
    /**
     * Modifies the Name
     * 
     * @param name the new Name
     **/
    @Override
    public void addName(final XPN name) {
        this.name = Util.addIfNotNull(this.name, name);
    }
    
    /**
     * Modifies the Address
     * 
     * @param address the new Address
     **/
    @Override
    public void setAddress(final List<XAD> address) {
        this.address = address;
    }
    
    /**
     * Modifies the Address
     * 
     * @param address the new Address
     **/
    @Override
    public void addAddress(final XAD address) {
        this.address = Util.addIfNotNull(this.address, address);
    }
    
    /**
     * Modifies the Phone Number
     * 
     * @param phoneNumber the new Phone Number
     **/
    @Override
    public void setPhoneNumber(final List<XTN> phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    /**
     * Modifies the Phone Number
     * 
     * @param phoneNumber the new Phone Number
     **/
    @Override
    public void addPhoneNumber(final XTN phoneNumber) {
        this.phoneNumber = Util.addIfNotNull(this.phoneNumber, phoneNumber);
    }
    
    /**
     * Modifies the Business Phone Number
     * 
     * @param businessPhoneNumber the new Business Phone Number
     **/
    @Override
    public void setBusinessPhoneNumber(final List<XTN> businessPhoneNumber) {
        this.businessPhoneNumber = businessPhoneNumber;
    }
    
    /**
     * Modifies the Business Phone Number
     * 
     * @param businessPhoneNumber the new Business Phone Number
     **/
    @Override
    public void addBusinessPhoneNumber(final XTN businessPhoneNumber) {
        this.businessPhoneNumber = Util.addIfNotNull(this.businessPhoneNumber, businessPhoneNumber);
    }
    
    /**
     * Modifies the Administrative Sex
     * 
     * @param administrativeSex the new Administrative Sex
     **/
    @Override
    public void setAdministrativeSex(final CWE administrativeSex) {
        this.administrativeSex = administrativeSex;
    }
    
    /**
     * Modifies the Date/Time Of Birth
     * 
     * @param dateTimeOfBirth the new Date/Time Of Birth
     **/
    @Override
    public void setDateTimeOfBirth(final TS dateTimeOfBirth) {
        this.dateTimeOfBirth = dateTimeOfBirth;
    }
    
    /**
     * Modifies the Marital Status
     * 
     * @param maritalStatus the new Marital Status
     **/
    @Override
    public void setMaritalStatus(final CE maritalStatus) {
        this.maritalStatus = maritalStatus;
    }
    
    /**
     * Modifies the Ambulatory Status
     * 
     * @param ambulatoryStatus the new Ambulatory Status
     **/
    @Override
    public void setAmbulatoryStatus(final List<CWE> ambulatoryStatus) {
        this.ambulatoryStatus = ambulatoryStatus;
    }
    
    /**
     * Modifies the Ambulatory Status
     * 
     * @param ambulatoryStatus the new Ambulatory Status
     **/
    @Override
    public void addAmbulatoryStatus(final CWE ambulatoryStatus) {
        this.ambulatoryStatus = Util.addIfNotNull(this.ambulatoryStatus, ambulatoryStatus);
    }
    
    /**
     * Modifies the Citizenship
     * 
     * @param citizenship the new Citizenship
     **/
    @Override
    public void setCitizenship(final List<CE> citizenship) {
        this.citizenship = citizenship;
    }
    
    /**
     * Modifies the Citizenship
     * 
     * @param citizenship the new Citizenship
     **/
    @Override
    public void addCitizenship(final CE citizenship) {
        this.citizenship = Util.addIfNotNull(this.citizenship, citizenship);
    }
    
    /**
     * Modifies the Primary Language
     * 
     * @param primaryLanguage the new Primary Language
     **/
    @Override
    public void setPrimaryLanguage(final CE primaryLanguage) {
        this.primaryLanguage = primaryLanguage;
    }
    
    /**
     * Modifies the Living Arrangement
     * 
     * @param livingArrangement the new Living Arrangement
     **/
    @Override
    public void setLivingArrangement(final CWE livingArrangement) {
        this.livingArrangement = livingArrangement;
    }
    
    /**
     * Modifies the Publicity Code
     * 
     * @param publicityCode the new Publicity Code
     **/
    @Override
    public void setPublicityCode(final CE publicityCode) {
        this.publicityCode = publicityCode;
    }
    
    /**
     * Modifies the Protection Indicator
     * 
     * @param protectionIndicator the new Protection Indicator
     **/
    @Override
    public void setProtectionIndicator(final String protectionIndicator) {
        this.protectionIndicator = protectionIndicator;
    }
    
    /**
     * Modifies the Student Indicator
     * 
     * @param studentIndicator the new Student Indicator
     **/
    @Override
    public void setStudentIndicator(final CWE studentIndicator) {
        this.studentIndicator = studentIndicator;
    }
    
    /**
     * Modifies the Religion
     * 
     * @param religion the new Religion
     **/
    @Override
    public void setReligion(final CE religion) {
        this.religion = religion;
    }
    
    /**
     * Modifies the Mother's Maiden Name
     * 
     * @param mothersMaidenName the new Mother's Maiden Name
     **/
    @Override
    public void setMothersMaidenName(final List<XPN> mothersMaidenName) {
        this.mothersMaidenName = mothersMaidenName;
    }
    
    /**
     * Modifies the Mother's Maiden Name
     * 
     * @param mothersMaidenName the new Mother's Maiden Name
     **/
    @Override
    public void addMothersMaidenName(final XPN mothersMaidenName) {
        this.mothersMaidenName = Util.addIfNotNull(this.mothersMaidenName, mothersMaidenName);
    }
    
    /**
     * Modifies the Nationality
     * 
     * @param nationality the new Nationality
     **/
    @Override
    public void setNationality(final CE nationality) {
        this.nationality = nationality;
    }
    
    /**
     * Modifies the Ethnic Group
     * 
     * @param ethnicGroup the new Ethnic Group
     **/
    @Override
    public void setEthnicGroup(final List<CE> ethnicGroup) {
        this.ethnicGroup = ethnicGroup;
    }
    
    /**
     * Modifies the Ethnic Group
     * 
     * @param ethnicGroup the new Ethnic Group
     **/
    @Override
    public void addEthnicGroup(final CE ethnicGroup) {
        this.ethnicGroup = Util.addIfNotNull(this.ethnicGroup, ethnicGroup);
    }
    
    @Override
    public void setRace(final List<CE> race) {
        this.race = race;
    }
    
    /**
     * Modifies the race
     * 
     * @param race the new race
     **/
    @Override
    public void addRace(final CE race) {
        this.race = Util.add(this.race, race);
    }
    
    /**
     * Modifies the Job Status
     * 
     * @param jobStatus the new Job Status
     **/
    @Override
    public void setJobStatus(final CWE jobStatus) {
        this.jobStatus = jobStatus;
    }
    
    /**
     * Modifies the Handicap
     * 
     * @param handicap the new Handicap
     **/
    @Override
    public void setHandicap(final CWE handicap) {
        this.handicap = handicap;
    }
    
    /**
     * Modifies the Birth Place
     * 
     * @param birthPlace the new Birth Place
     **/
    @Override
    public void setBirthPlace(final String birthPlace) {
        this.birthPlace = birthPlace;
    }
    
    /**
     * Modifies the VIP Indicator
     * 
     * @param vipIndicator the new VIP Indicator
     **/
    @Override
    public void setVipIndicator(final CWE vipIndicator) {
        this.vipIndicator = vipIndicator;
    }
    
    /**
     * Clears the AbstractPersonData
     **/
    @Override
    public void clear() {
        super.clear();
        Util.clear(this.name);
        Util.clear(this.address);
        Util.clear(this.phoneNumber);
        Util.clear(this.businessPhoneNumber);
        this.administrativeSex = null;
        this.dateTimeOfBirth = null;
        this.maritalStatus = null;
        Util.clear(this.ambulatoryStatus);
        Util.clear(this.citizenship);
        this.primaryLanguage = null;
        this.livingArrangement = null;
        this.publicityCode = null;
        this.protectionIndicator = null;
        this.studentIndicator = null;
        this.religion = null;
        Util.clear(this.mothersMaidenName);
        this.nationality = null;
        Util.clear(this.ethnicGroup);
        this.race = null;
        this.jobStatus = null;
        this.handicap = null;
        this.birthPlace = null;
        this.vipIndicator = null;
    }
}
