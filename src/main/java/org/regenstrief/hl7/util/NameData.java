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
 * Title: NameData
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
public interface NameData extends BaseData {
    
    ///**	Retrieves the family name
    //@return		the family name
    //**/
    //public FN getFamilyName();
    
    /**
     * Retrieves the surname
     * 
     * @return the surname
     **/
    public String getSurname();
    
    /**
     * Retrieves the given name
     * 
     * @return the given name
     **/
    public String getGivenName();
    
    /**
     * Retrieves the middle initial or name
     * 
     * @return the middle initial or name
     **/
    public String getMiddleInitialOrName();
    
    /**
     * Retrieves the suffix
     * 
     * @return the suffix
     **/
    public String getSuffix();
    
    /**
     * Retrieves the prefix
     * 
     * @return the prefix
     **/
    public String getPrefix();
    
    /**
     * Retrieves the degree
     * 
     * @return the degree
     **/
    public String getDegree();
    
    /**
     * Retrieves the suffix and/or degree
     * 
     * @return the suffix and/or degree
     **/
    public String getSuffixDegree();
    
    ///**	Modifies the family name
    //@param familyName	the new famliy name
    //**/
    //public void setFamilyName(FN familyName);
    
    /**
     * Modifies the surname
     * 
     * @param surname the new surname
     **/
    public void setSurname(String surname);
    
    /**
     * Modifies the given name
     * 
     * @param givenName the new given name
     **/
    public void setGivenName(String givenName);
    
    /**
     * Modifies the middle initial or name
     * 
     * @param middleInitialOrName the new middle initial or name
     **/
    public void setMiddleInitialOrName(String middleInitialOrName);
    
    /**
     * Modifies the suffix
     * 
     * @param suffix the new suffix
     **/
    public void setSuffix(String suffix);
    
    /**
     * Modifies the prefix
     * 
     * @param prefix the new prefix
     **/
    public void setPrefix(String prefix);
    
    /**
     * Modifies the degree
     * 
     * @param degree the new degree
     **/
    public void setDegree(String degree);
}
