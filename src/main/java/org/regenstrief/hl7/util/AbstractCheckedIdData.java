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
import org.regenstrief.hl7.datatype.HD;
import org.regenstrief.hl7.datatype.HL7DataType;
import org.regenstrief.hl7.datatype.NM;

/**
 * <p>
 * Title: AbstractCheckedIdData
 * </p>
 * <p>
 * Description: Interface for HL7 check digit data
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
public abstract class AbstractCheckedIdData extends HL7DataType implements CheckedIdData {
    
    protected NM idNumber = null;
    
    protected NM checkDigitNumber = null;
    
    protected String checkDigitScheme = null;
    
    protected HD assigningAuthority = null;
    
    /**
     * Constructs an empty AbstractCheckedIdData
     * 
     * @param prop the HL7Properties
     **/
    public AbstractCheckedIdData(final HL7Properties prop) {
        super(prop);
    }
    
    /**
     * Retrieves the ID number
     * 
     * @return the ID number
     **/
    public NM getIDNumber() {
        return this.idNumber;
    }
    
    /**
     * Retrieves the ID
     * 
     * @return the ID
     **/
    @Override
    public String getID() {
        return NM.getValue(this.idNumber);
    }
    
    /**
     * Retrieves the check digit number
     * 
     * @return the check digit number
     **/
    public NM getCheckDigitNumber() {
        return this.checkDigitNumber;
    }
    
    /**
     * Retrieves the check digit
     * 
     * @return the check digit
     **/
    @Override
    public String getCheckDigit() {
        return NM.getValue(this.checkDigitNumber);
    }
    
    /**
     * Retrieves the check digit scheme
     * 
     * @return the check digit scheme
     **/
    @Override
    public String getCheckDigitScheme() {
        return this.checkDigitScheme;
    }
    
    /**
     * Retrieves the assigning authority
     * 
     * @return the assigning authority
     **/
    @Override
    public HD getAssigningAuthority() {
        return this.assigningAuthority;
    }
    
    /**
     * Modifies the ID number
     * 
     * @param idNumber the new ID number
     **/
    public void setIDNumber(final NM idNumber) {
        this.idNumber = idNumber;
    }
    
    /**
     * Modifies the ID
     * 
     * @param id the new ID
     **/
    @Override
    public void setID(final String id) {
        this.idNumber = NM.setValue(this.prop, this.idNumber, id);
    }
    
    /**
     * Modifies the check digit number
     * 
     * @param checkDigitNumber the new check digit number
     **/
    public void setCheckDigitNumber(final NM checkDigitNumber) {
        this.checkDigitNumber = checkDigitNumber;
    }
    
    /**
     * Modifies the check digit
     * 
     * @param checkDigit the new check digit
     **/
    @Override
    public void setCheckDigit(final String checkDigit) {
        this.checkDigitNumber = NM.setValue(this.prop, this.checkDigitNumber, checkDigit);
    }
    
    /**
     * Modifies the the new check digit scheme
     * 
     * @param checkDigitScheme the new check digit scheme
     **/
    @Override
    public void setCheckDigitScheme(final String checkDigitScheme) {
        this.checkDigitScheme = checkDigitScheme;
    }
    
    /**
     * Modifies the assigning authority
     * 
     * @param assigningAuthority the new assigning authority
     **/
    @Override
    public void setAssigningAuthority(final HD assigningAuthority) {
        this.assigningAuthority = assigningAuthority;
    }
    
    /**
     * Retrieves the whole identifier (the check digit appended to the ID number)
     * 
     * @return the whole identifier
     **/
    @Override
    public String toDisplay() {
        return getFullIdentifier();
    }
    
    /**
     * Retrieves the id plus a check digit (if any)
     * 
     * @return the id plus a check digit (if any)
     */
    @Override
    public String getFullIdentifier() {
        return AbstractExpandedIdData.getFullIdentifier(NM.getValue(this.idNumber), NM.getValue(this.checkDigitNumber));
    }
}
