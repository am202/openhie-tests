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

import org.regenstrief.hl7.HL7Properties;
import org.regenstrief.hl7.datatype.HL7DataType;
import org.regenstrief.hl7.datatype.XAD;
import org.regenstrief.util.Util;

/**
 * <p>
 * Title: AbstractAddressData
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
public abstract class AbstractAddressData extends HL7DataType implements AddressData {
    
    protected static boolean displayCountyCode = false;
    
    protected String otherDesignation = null;
    
    protected String city = null;
    
    protected String stateOrProvince = null;
    
    protected String zipOrPostalCode = null;
    
    protected String country = null;
    
    protected String addressType = null;
    
    protected String otherGeographicDesignation = null;
    
    /**
     * Constructs an empty AbstractAddressData
     * 
     * @param prop the HL7Properties
     **/
    public AbstractAddressData(final HL7Properties prop) {
        super(prop);
    }
    
    protected AbstractAddressData(final AddressData ad) {
        super(ad.getProp());
        this.otherDesignation = ad.getOtherDesignation();
        this.city = ad.getCity();
        this.stateOrProvince = ad.getStateOrProvince();
        this.zipOrPostalCode = ad.getZipOrPostalCode();
        this.country = ad.getCountry();
        this.addressType = ad.getAddressType();
        this.otherGeographicDesignation = ad.getOtherGeographicDesignation();
    }
    
    /**
     * Retrieves the other designation
     * 
     * @return the other designation
     **/
    @Override
    public String getOtherDesignation() {
        return this.otherDesignation;
    }
    
    /**
     * Retrieves the city
     * 
     * @return the city
     **/
    @Override
    public String getCity() {
        return this.city;
    }
    
    /**
     * Retrieves the state or province
     * 
     * @return the state or province
     **/
    @Override
    public String getStateOrProvince() {
        return this.stateOrProvince;
    }
    
    /**
     * Retrieves the zip or postal code
     * 
     * @return the zip or postal code
     **/
    @Override
    public String getZipOrPostalCode() {
        return this.zipOrPostalCode;
    }
    
    /**
     * Retrieves the zip code without an extension
     * 
     * @return the zip code without an extension
     **/
    @Override
    public String getZipWithoutExtension() {
        if (this.zipOrPostalCode == null) {
            return null;
        }
        
        int i = this.zipOrPostalCode.indexOf('-');
        if ((i < 0) && (this.zipOrPostalCode.length() > 5)) {
            i = 5;
        }
        
        return i < 0 ? this.zipOrPostalCode : this.zipOrPostalCode.substring(0, i);
    }
    
    /**
     * Retrieves the zip code extension
     * 
     * @return the zip code extension
     **/
    @Override
    public String getZipExtension() {
        if (this.zipOrPostalCode == null) {
            return null;
        }
        
        int i = this.zipOrPostalCode.indexOf('-');
        if ((i < 0) && (this.zipOrPostalCode.length() > 5)) {
            i = 4;
        }
        
        return (i < 0) || (i + 1 >= this.zipOrPostalCode.length()) ? null : this.zipOrPostalCode.substring(i + 1);
    }
    
    /**
     * Retrieves the country
     * 
     * @return the country
     **/
    @Override
    public String getCountry() {
        return this.country;
    }
    
    /**
     * Retrieves the address type
     * 
     * @return the address type
     **/
    @Override
    public String getAddressType() {
        return this.addressType;
    }
    
    /**
     * Retrieves the other geographic designation
     * 
     * @return the other geographic designation
     **/
    @Override
    public String getOtherGeographicDesignation() {
        return this.otherGeographicDesignation;
    }
    
    /**
     * Modifies the other designation
     * 
     * @param otherDesignation the new other designation
     **/
    @Override
    public void setOtherDesignation(final String otherDesignation) {
        this.otherDesignation = otherDesignation;
    }
    
    /**
     * Modifies the city
     * 
     * @param city the new city
     **/
    @Override
    public void setCity(final String city) {
        this.city = city;
    }
    
    /**
     * Modifies the state or province
     * 
     * @param stateOrProvince the new state or province
     **/
    @Override
    public void setStateOrProvince(final String stateOrProvince) {
        this.stateOrProvince = stateOrProvince;
    }
    
    /**
     * Modifies the zip or postal code
     * 
     * @param zipOrPostalCode the new zip or postal code
     **/
    @Override
    public void setZipOrPostalCode(final String zipOrPostalCode) {
        this.zipOrPostalCode = zipOrPostalCode;
    }
    
    /**
     * Modifies the country
     * 
     * @param country the new country
     **/
    @Override
    public void setCountry(final String country) {
        this.country = country;
    }
    
    /**
     * Modifies the address type
     * 
     * @param addressType the new address type
     **/
    @Override
    public void setAddressType(final String addressType) {
        this.addressType = addressType;
    }
    
    /**
     * Modifies the other geographic designation
     * 
     * @param otherGeographicDesignation the new other geographic designation
     **/
    @Override
    public void setOtherGeographicDesignation(final String otherGeographicDesignation) {
        this.otherGeographicDesignation = otherGeographicDesignation;
    }
    
    @Override
    public String toDisplay() {
        final StringBuilder b = new StringBuilder();
        append(b, getMailingAddress());
        append(b, this.otherDesignation);
        append(b, this.city);
        if (append(b, this.stateOrProvince)) {
            append(b, this.zipOrPostalCode, " ");
        } else {
            append(b, this.zipOrPostalCode);
        }
        append(b, this.country);
        if (displayCountyCode && (this instanceof XAD)) {
            final String county = toDisplay(((XAD) this).getCountyParishCode());
            if (Util.isValued(county)) {
                b.append(" (County: ").append(county).append(')');
            }
        }
        return b.toString();
    }
    
    private boolean append(final StringBuilder b, final String s) {
        return append(b, s, ", ");
    }
    
    private boolean append(final StringBuilder b, final String s, final String delim) {
        if (Util.isEmpty(s)) {
            return false;
        } else if (b.length() > 0) {
            b.append(delim);
        }
        b.append(s);
        return true;
    }
}
