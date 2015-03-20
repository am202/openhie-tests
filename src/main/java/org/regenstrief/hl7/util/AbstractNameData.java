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
import org.regenstrief.hl7.datatype.FN;
import org.regenstrief.hl7.datatype.HL7DataType;
import org.regenstrief.util.Util;

/**
 * <p>
 * Title: AbstractNameData
 * </p>
 * <p>
 * Description: Interface for HL7 name data
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
public abstract class AbstractNameData extends HL7DataType implements NameData {
    
    protected FN familyName = null;
    
    protected String givenName = null;
    
    protected String middleInitialOrName = null;
    
    protected String suffix = null;
    
    protected String prefix = null;
    
    protected String degree = null;
    
    /**
     * Constructs an empty AbstractNameData
     * 
     * @param prop the HL7Properties
     **/
    public AbstractNameData(final HL7Properties prop) {
        super(prop);
    }
    
    /**
     * Retrieves the family name
     * 
     * @return the family name
     **/
    public FN getFamilyName() {
        return this.familyName;
    }
    
    /**
     * Retrieves the surname
     * 
     * @return the surname
     **/
    @Override
    public String getSurname() {
        return FN.getSurname(this.familyName);
    }
    
    /**
     * Retrieves the given name
     * 
     * @return the given name
     **/
    @Override
    public String getGivenName() {
        return this.givenName;
    }
    
    /**
     * Retrieves the middle initial or name
     * 
     * @return the middle initial or name
     **/
    @Override
    public String getMiddleInitialOrName() {
        return this.middleInitialOrName;
    }
    
    /**
     * Retrieves the suffix
     * 
     * @return the suffix
     **/
    @Override
    public String getSuffix() {
        return this.suffix;
    }
    
    /**
     * Retrieves the prefix
     * 
     * @return the prefix
     **/
    @Override
    public String getPrefix() {
        return this.prefix;
    }
    
    /**
     * Retrieves the degree
     * 
     * @return the degree
     **/
    @Override
    public String getDegree() {
        return this.degree;
    }
    
    /**
     * Retrieves the suffix and/or degree
     * 
     * @return the suffix and/or degree
     **/
    @Override
    public String getSuffixDegree() {
        return getSuffixDegree(this.suffix, this.degree, null);
    }
    
    /**
     * Retrieves the suffix and/or degree
     * 
     * @param suffix the suffix
     * @param degree the degree
     * @param professionalSuffix the professional suffix
     * @return the suffix and/or degree
     **/
    public final static String getSuffixDegree(final String suffix, final String degree, final String professionalSuffix) {
        return Util.concatWithDelim(suffix, degree, professionalSuffix, " ");
    }
    
    /**
     * Modifies the family name
     * 
     * @param familyName the new famliy name
     **/
    public void setFamilyName(final FN familyName) {
        this.familyName = familyName;
    }
    
    /**
     * Modifies the surname
     * 
     * @param surname the new surname
     **/
    @Override
    public void setSurname(final String surname) {
        this.familyName = FN.setSurname(this.prop, this.familyName, surname);
    }
    
    /**
     * Modifies the given name
     * 
     * @param givenName the new given name
     **/
    @Override
    public void setGivenName(final String givenName) {
        this.givenName = givenName;
    }
    
    /**
     * Modifies the middle initial or name
     * 
     * @param middleInitialOrName the new middle initial or name
     **/
    @Override
    public void setMiddleInitialOrName(final String middleInitialOrName) {
        this.middleInitialOrName = middleInitialOrName;
    }
    
    /**
     * Modifies the suffix
     * 
     * @param suffix the new suffix
     **/
    @Override
    public void setSuffix(final String suffix) {
        this.suffix = suffix;
    }
    
    /**
     * Modifies the prefix
     * 
     * @param prefix the new prefix
     **/
    @Override
    public void setPrefix(final String prefix) {
        this.prefix = prefix;
    }
    
    /**
     * Modifies the degree
     * 
     * @param degree the new degree
     **/
    @Override
    public void setDegree(final String degree) {
        this.degree = degree;
    }
}
