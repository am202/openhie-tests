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

/**
 * <p>
 * Title: AddressData
 * </p>
 * <p>
 * Description: Interface for HL7 address data
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
public interface AddressData extends BaseData {
    
    /**
     * Retrieves the mailing address
     * 
     * @return the mailing address
     **/
    public String getMailingAddress();
    
    /**
     * Retrieves the other designation
     * 
     * @return the other designation
     **/
    public String getOtherDesignation();
    
    /**
     * Retrieves the city
     * 
     * @return the city
     **/
    public String getCity();
    
    /**
     * Retrieves the state or province
     * 
     * @return the state or province
     **/
    public String getStateOrProvince();
    
    /**
     * Retrieves the zip or postal code
     * 
     * @return the zip or postal code
     **/
    public String getZipOrPostalCode();
    
    /**
     * Retrieves the zip code without an extension
     * 
     * @return the zip code without an extension
     **/
    public String getZipWithoutExtension();
    
    /**
     * Retrieves the zip code extension
     * 
     * @return the zip code extension
     **/
    public String getZipExtension();
    
    /**
     * Retrieves the country
     * 
     * @return the country
     **/
    public String getCountry();
    
    /**
     * Retrieves the address type
     * 
     * @return the address type
     **/
    public String getAddressType();
    
    /**
     * Retrieves the other geographic designation
     * 
     * @return the other geographic designation
     **/
    public String getOtherGeographicDesignation();
    
    /**
     * Modifies the mailing address
     * 
     * @param mailingAddress the new mailing address
     **/
    public void setMailingAddress(String mailingAddress);
    
    /**
     * Modifies the other designation
     * 
     * @param otherDesignation the new other designation
     **/
    public void setOtherDesignation(String otherDesignation);
    
    /**
     * Modifies the city
     * 
     * @param city the new city
     **/
    public void setCity(String city);
    
    /**
     * Modifies the state or province
     * 
     * @param stateOrProvince the new state or province
     **/
    public void setStateOrProvince(String stateOrProvince);
    
    /**
     * Modifies the zip or postal code
     * 
     * @param zipOrPostalCode the new zip or postal code
     **/
    public void setZipOrPostalCode(String zipOrPostalCode);
    
    /**
     * Modifies the country
     * 
     * @param country the new country
     **/
    public void setCountry(String country);
    
    /**
     * Modifies the address type
     * 
     * @param addressType the new address type
     **/
    public void setAddressType(String addressType);
    
    /**
     * Modifies the other geographic designation
     * 
     * @param otherGeographicDesignation the new other geographic designation
     **/
    public void setOtherGeographicDesignation(String otherGeographicDesignation);
}
