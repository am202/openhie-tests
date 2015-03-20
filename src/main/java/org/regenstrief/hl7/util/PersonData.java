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

import org.regenstrief.hl7.datatype.CE;
import org.regenstrief.hl7.datatype.CWE;
import org.regenstrief.hl7.datatype.TS;
import org.regenstrief.hl7.datatype.XAD;
import org.regenstrief.hl7.datatype.XPN;
import org.regenstrief.hl7.datatype.XTN;

/**
 * <p>
 * Title: PersonData
 * </p>
 * <p>
 * Description: Interface for HL7 preson data
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
public interface PersonData extends BaseData {
    
    /**
     * Retrieves the Name
     * 
     * @return the Name
     **/
    public List<XPN> getName();
    
    /**
     * Retrieves the Address
     * 
     * @return the Address
     **/
    public List<XAD> getAddress();
    
    /**
     * Retrieves the Phone Number
     * 
     * @return the Phone Number
     **/
    public List<XTN> getPhoneNumber();
    
    /**
     * Retrieves the Business Phone Number
     * 
     * @return the Business Phone Number
     **/
    public List<XTN> getBusinessPhoneNumber();
    
    /**
     * Retrieves the Administrative Sex
     * 
     * @return the Administrative Sex
     **/
    public CWE getAdministrativeSex();
    
    /**
     * Retrieves the Date/Time Of Birth
     * 
     * @return the Date/Time Of Birth
     **/
    public TS getDateTimeOfBirth();
    
    /**
     * Retrieves the Marital Status
     * 
     * @return the Marital Status
     **/
    public CE getMaritalStatus();
    
    /**
     * Retrieves the Ambulatory Status
     * 
     * @return the Ambulatory Status
     **/
    public List<CWE> getAmbulatoryStatus();
    
    /**
     * Retrieves the Citizenship
     * 
     * @return the Citizenship
     **/
    public List<CE> getCitizenship();
    
    /**
     * Retrieves the Primary Language
     * 
     * @return the Primary Language
     **/
    public CE getPrimaryLanguage();
    
    /**
     * Retrieves the Living Arrangement
     * 
     * @return the Living Arrangement
     **/
    public CWE getLivingArrangement();
    
    /**
     * Retrieves the Publicity Code
     * 
     * @return the Publicity Code
     **/
    public CE getPublicityCode();
    
    /**
     * Retrieves the Protection Indicator
     * 
     * @return the Protection Indicator
     **/
    public String getProtectionIndicator();
    
    /**
     * Retrieves the Student Indicator
     * 
     * @return the Student Indicator
     **/
    public CWE getStudentIndicator();
    
    /**
     * Retrieves the Religion
     * 
     * @return the Religion
     **/
    public CE getReligion();
    
    /**
     * Retrieves the Mother's Maiden Name
     * 
     * @return the Mother's Maiden Name
     **/
    public List<XPN> getMothersMaidenName();
    
    /**
     * Retrieves the Nationality
     * 
     * @return the Nationality
     **/
    public CE getNationality();
    
    /**
     * Retrieves the Ethnic Group
     * 
     * @return the Ethnic Group
     **/
    public List<CE> getEthnicGroup();
    
    /**
     * Retrieves the race
     * 
     * @return the race
     **/
    public List<CE> getRace();
    
    /**
     * Retrieves the Job Status
     * 
     * @return the Job Status
     **/
    public CWE getJobStatus();
    
    /**
     * Retrieves the Handicap
     * 
     * @return the Handicap
     **/
    public CWE getHandicap();
    
    /**
     * Retrieves the Birth Place
     * 
     * @return the Birth Place
     **/
    public String getBirthPlace();
    
    /**
     * Retrieves the VIP Indicator
     * 
     * @return the VIP Indicator
     **/
    public CWE getVipIndicator();
    
    /**
     * Modifies the Name
     * 
     * @param name the new Name
     **/
    public void setName(List<XPN> name);
    
    /**
     * Modifies the Name
     * 
     * @param name the new Name
     **/
    public void addName(XPN name);
    
    /**
     * Modifies the Address
     * 
     * @param address the new Address
     **/
    public void setAddress(List<XAD> address);
    
    /**
     * Modifies the Address
     * 
     * @param address the new Address
     **/
    public void addAddress(XAD address);
    
    /**
     * Modifies the Phone Number
     * 
     * @param phoneNumber the new Phone Number
     **/
    public void setPhoneNumber(List<XTN> phoneNumber);
    
    /**
     * Modifies the Phone Number
     * 
     * @param phoneNumber the new Phone Number
     **/
    public void addPhoneNumber(XTN phoneNumber);
    
    /**
     * Modifies the Business Phone Number
     * 
     * @param businessPhoneNumber the new Business Phone Number
     **/
    public void setBusinessPhoneNumber(List<XTN> businessPhoneNumber);
    
    /**
     * Modifies the Business Phone Number
     * 
     * @param businessPhoneNumber the new Business Phone Number
     **/
    public void addBusinessPhoneNumber(XTN businessPhoneNumber);
    
    /**
     * Modifies the Administrative Sex
     * 
     * @param administrativeSex the new Administrative Sex
     **/
    public void setAdministrativeSex(CWE administrativeSex);
    
    /**
     * Modifies the Date/Time Of Birth
     * 
     * @param dateTimeOfBirth the new Date/Time Of Birth
     **/
    public void setDateTimeOfBirth(TS dateTimeOfBirth);
    
    /**
     * Modifies the Marital Status
     * 
     * @param maritalStatus the new Marital Status
     **/
    public void setMaritalStatus(CE maritalStatus);
    
    /**
     * Modifies the Ambulatory Status
     * 
     * @param ambulatoryStatus the new Ambulatory Status
     **/
    public void setAmbulatoryStatus(List<CWE> ambulatoryStatus);
    
    /**
     * Modifies the Ambulatory Status
     * 
     * @param ambulatoryStatus the new Ambulatory Status
     **/
    public void addAmbulatoryStatus(CWE ambulatoryStatus);
    
    /**
     * Modifies the Citizenship
     * 
     * @param citizenship the new Citizenship
     **/
    public void setCitizenship(List<CE> citizenship);
    
    /**
     * Modifies the Citizenship
     * 
     * @param citizenship the new Citizenship
     **/
    public void addCitizenship(CE citizenship);
    
    /**
     * Modifies the Primary Language
     * 
     * @param primaryLanguage the new Primary Language
     **/
    public void setPrimaryLanguage(CE primaryLanguage);
    
    /**
     * Modifies the Living Arrangement
     * 
     * @param livingArrangement the new Living Arrangement
     **/
    public void setLivingArrangement(CWE livingArrangement);
    
    /**
     * Modifies the Publicity Code
     * 
     * @param publicityCode the new Publicity Code
     **/
    public void setPublicityCode(CE publicityCode);
    
    /**
     * Modifies the Protection Indicator
     * 
     * @param protectionIndicator the new Protection Indicator
     **/
    public void setProtectionIndicator(String protectionIndicator);
    
    /**
     * Modifies the Student Indicator
     * 
     * @param studentIndicator the new Student Indicator
     **/
    public void setStudentIndicator(CWE studentIndicator);
    
    /**
     * Modifies the Religion
     * 
     * @param religion the new Religion
     **/
    public void setReligion(CE religion);
    
    /**
     * Modifies the Mother's Maiden Name
     * 
     * @param mothersMaidenName the new Mother's Maiden Name
     **/
    public void setMothersMaidenName(List<XPN> mothersMaidenName);
    
    /**
     * Modifies the Mother's Maiden Name
     * 
     * @param mothersMaidenName the new Mother's Maiden Name
     **/
    public void addMothersMaidenName(XPN mothersMaidenName);
    
    /**
     * Modifies the Nationality
     * 
     * @param nationality the new Nationality
     **/
    public void setNationality(CE nationality);
    
    /**
     * Modifies the Ethnic Group
     * 
     * @param ethnicGroup the new Ethnic Group
     **/
    public void setEthnicGroup(List<CE> ethnicGroup);
    
    /**
     * Modifies the Ethnic Group
     * 
     * @param ethnicGroup the new Ethnic Group
     **/
    public void addEthnicGroup(CE ethnicGroup);
    
    /**
     * Modifies the Race
     * 
     * @param race the new Race
     **/
    public void setRace(List<CE> race);
    
    /**
     * Modifies the race
     * 
     * @param race the new race
     **/
    public void addRace(CE race);
    
    /**
     * Modifies the Job Status
     * 
     * @param jobStatus the new Job Status
     **/
    public void setJobStatus(CWE jobStatus);
    
    /**
     * Modifies the Handicap
     * 
     * @param handicap the new Handicap
     **/
    public void setHandicap(CWE handicap);
    
    /**
     * Modifies the Birth Place
     * 
     * @param birthPlace the new Birth Place
     **/
    public void setBirthPlace(String birthPlace);
    
    /**
     * Modifies the VIP Indicator
     * 
     * @param vipIndicator the new VIP Indicator
     **/
    public void setVipIndicator(CWE vipIndicator);
}
