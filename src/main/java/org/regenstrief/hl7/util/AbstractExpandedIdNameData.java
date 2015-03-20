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
import org.regenstrief.hl7.datatype.CE;
import org.regenstrief.hl7.datatype.CN;
import org.regenstrief.hl7.datatype.CWE;
import org.regenstrief.hl7.datatype.CX;
import org.regenstrief.hl7.datatype.DR;
import org.regenstrief.hl7.datatype.FN;
import org.regenstrief.hl7.datatype.NDL;
import org.regenstrief.hl7.datatype.TS;
import org.regenstrief.hl7.datatype.XPN;
import org.regenstrief.util.Util;

/**
 * AbstractExpandedIdNameData
 */
public abstract class AbstractExpandedIdNameData extends AbstractExpandedIdData implements ExpandedIdNameData {
    
    protected FN familyName = null;
    
    protected String givenName = null;
    
    protected String middleInitialOrName = null;
    
    protected String suffix = null;
    
    protected String prefix = null;
    
    protected String degree = null;
    
    protected CWE sourceTable = null;
    
    protected String nameTypeCode = null;
    
    protected String nameRepresentationCode = null;
    
    protected CE nameContext = null;
    
    protected DR nameValidityRange = null;
    
    protected String nameAssemblyOrder = null;
    
    protected TS effectiveDate = null;
    
    protected TS expirationDate = null;
    
    protected String professionalSuffix = null;
    
    protected AbstractExpandedIdNameData(final HL7Properties prop) {
        super(prop);
    }
    
    /**
     * Assigns the name from the given first, last, and middle name and suffix/prefix
     * 
     * @param familyName the last name
     * @param givenName the first name
     * @param middleInitialOrName the middle initial or name
     * @param suffix the suffix
     * @param prefix the prefix
     **/
    @Override
    public void setName(final String familyName, final String givenName, final String middleInitialOrName,
                        final String suffix, final String prefix) {
        this.familyName = FN.create(this.prop, familyName);
        this.givenName = givenName;
        this.middleInitialOrName = Util.length(middleInitialOrName) == 0 ? null : middleInitialOrName;
        this.suffix = suffix;
        this.prefix = prefix;
    }
    
    /**
     * Retrieves the suffix and/or degree
     * 
     * @return the suffix and/or degree
     **/
    @Override
    public String getSuffixDegree() {
        return AbstractNameData.getSuffixDegree(this.suffix, this.degree, this.professionalSuffix);
    }
    
    /**
     * Converts this into an XPN
     * 
     * @return this as an XPN
     **/
    public XPN getXPN() {
        final XPN xpn = new XPN(this.prop);
        
        xpn.setDegree(this.getDegree());
        xpn.setFamilyName(this.getFamilyName());
        xpn.setGivenName(this.getGivenName());
        xpn.setMiddleInitialOrName(this.getMiddleInitialOrName());
        xpn.setNameRepresentationCode(this.getNameRepresentationCode());
        xpn.setNameTypeCode(this.getNameTypeCode());
        xpn.setPrefix(this.getPrefix());
        xpn.setSuffix(this.getSuffix());
        xpn.setNameContext(this.getNameContext());
        xpn.setNameValidityRange(this.getNameValidityRange());
        xpn.setNameAssemblyOrder(this.getNameAssemblyOrder());
        xpn.setEffectiveDate(this.getEffectiveDate());
        xpn.setExpirationDate(this.getExpirationDate());
        xpn.setProfessionalSuffix(this.getProfessionalSuffix());
        
        return xpn;
    }
    
    /**
     * Retrieves the CN contained within this XCN
     * 
     * @return CN
     **/
    @Override
    public CN getCN() {
        final CN cn = new CN(this.prop);
        
        cn.setID(this.id);
        cn.setFamilyName(this.familyName);
        cn.setGivenName(this.givenName);
        cn.setMiddleInitialOrName(this.middleInitialOrName);
        cn.setSuffix(this.suffix);
        cn.setPrefix(this.prefix);
        cn.setDegree(this.degree);
        cn.setSourceTable(this.sourceTable);
        cn.setAssigningAuthority(this.assigningAuthority);
        
        return cn;
    }
    
    /**
     * Converts this into a CX
     * 
     * @return this as a CX
     **/
    @Override
    public CX getCX() {
        final CX cx = new CX(this.prop);
        
        cx.setID(this.id);
        cx.setCheckDigit(this.checkDigit);
        cx.setCheckDigitScheme(this.checkDigitScheme);
        cx.setAssigningAuthority(this.assigningAuthority);
        cx.setIdentifierTypeCode(this.identifierTypeCode);
        cx.setAssigningFacility(this.assigningFacility);
        
        return cx;
    }
    
    /**
     * Converts this into an NDL
     * 
     * @return this as an NDL
     */
    public NDL getNDL() {
        final NDL ndl = new NDL(this.prop);
        
        ndl.setName(getCN());
        
        return ndl;
    }
    
    /**
     * Determines if this is equal to the given Object
     * 
     * @param o the Object to compare to this
     * @return true if this equals o, false otherwise
     **/
    @Override
    public boolean equals(final Object o) {
        return (o instanceof ExpandedIdNameData) && equals((ExpandedIdNameData) o);
    }
    
    /**
     * Determines if this is equal to the given ExpandedIdNameData
     * 
     * @param xcn the ExpandedIdNameData to compare to this
     * @return true if this equals xcn, false otherwise
     **/
    @Override
    public boolean equals(final ExpandedIdNameData xcn) {
        if (xcn == null) {
            return false;
        } else if (!Util.equalsIgnoreCase(FN.getSurname(xcn.getFamilyName()), FN.getSurname(this.familyName))) {
            return false;
        } else if (!Util.equalsIgnoreCase(xcn.getGivenName(), this.givenName)) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Retrieves the family name
     * 
     * @return the family name
     **/
    @Override
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
     * Retrieves the source table
     * 
     * @return the source table
     **/
    @Override
    public CWE getSourceTable() {
        return this.sourceTable;
    }
    
    /**
     * Retrieves the name type code
     * 
     * @return the name type code
     **/
    @Override
    public String getNameTypeCode() {
        return this.nameTypeCode;
    }
    
    /**
     * Retrieves the name representation code
     * 
     * @return the name representation code
     **/
    @Override
    public String getNameRepresentationCode() {
        return this.nameRepresentationCode;
    }
    
    /**
     * Retrieves the name context
     * 
     * @return the name context
     **/
    @Override
    public CE getNameContext() {
        return this.nameContext;
    }
    
    /**
     * Retrieves the name validity range
     * 
     * @return the name validity range
     **/
    @Override
    public DR getNameValidityRange() {
        return this.nameValidityRange;
    }
    
    /**
     * Retrieves the name assembly order
     * 
     * @return the name assembly order
     **/
    @Override
    public String getNameAssemblyOrder() {
        return this.nameAssemblyOrder;
    }
    
    /**
     * Retrieves the effective date
     * 
     * @return the effective date
     **/
    @Override
    public TS getEffectiveDate() {
        return this.effectiveDate;
    }
    
    /**
     * Retrieves the expiration date
     * 
     * @return the expiration date
     **/
    @Override
    public TS getExpirationDate() {
        return this.expirationDate;
    }
    
    /**
     * Retrieves the professional suffix
     * 
     * @return the professional suffix
     **/
    @Override
    public String getProfessionalSuffix() {
        return this.professionalSuffix;
    }
    
    /**
     * Modifies the family name
     * 
     * @param familyName the new famliy name
     **/
    @Override
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
    
    /**
     * Modifies the source table
     * 
     * @param sourceTable the new source table
     **/
    @Override
    public void setSourceTable(final CWE sourceTable) {
        this.sourceTable = sourceTable;
    }
    
    /**
     * Modifies the name type code
     * 
     * @param nameTypeCode the new name type code
     **/
    @Override
    public void setNameTypeCode(final String nameTypeCode) {
        this.nameTypeCode = nameTypeCode;
    }
    
    /**
     * Modifies the name representation code
     * 
     * @param nameRepresentationCode the new name representation code
     **/
    @Override
    public void setNameRepresentationCode(final String nameRepresentationCode) {
        this.nameRepresentationCode = nameRepresentationCode;
    }
    
    /**
     * Modifies the name context
     * 
     * @param nameContext the new name context
     **/
    @Override
    public void setNameContext(final CE nameContext) {
        this.nameContext = nameContext;
    }
    
    /**
     * Modifies the name validity range
     * 
     * @param nameValidityRange the new name validity range
     **/
    @Override
    public void setNameValidityRange(final DR nameValidityRange) {
        this.nameValidityRange = nameValidityRange;
    }
    
    /**
     * Modifies the name assembly order
     * 
     * @param nameAssemblyOrder the new name assembly order
     **/
    @Override
    public void setNameAssemblyOrder(final String nameAssemblyOrder) {
        this.nameAssemblyOrder = nameAssemblyOrder;
    }
    
    /**
     * Modifies the effective date
     * 
     * @param effectiveDate the new effective date
     **/
    @Override
    public void setEffectiveDate(final TS effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
    
    /**
     * Modifies the expiration date
     * 
     * @param expirationDate the new expiration date
     **/
    @Override
    public void setExpirationDate(final TS expirationDate) {
        this.expirationDate = expirationDate;
    }
    
    /**
     * Modifies the professional suffix
     * 
     * @param professionalSuffix the new professional suffix
     **/
    @Override
    public void setProfessionalSuffix(final String professionalSuffix) {
        this.professionalSuffix = professionalSuffix;
    }
}
