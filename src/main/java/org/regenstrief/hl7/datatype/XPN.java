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
import org.regenstrief.hl7.util.AbstractNameData;
import org.regenstrief.hl7.util.ExpandedNameData;
import org.regenstrief.util.Util;

/**
 * <p>
 * Title: XPN
 * </p>
 * <p>
 * Description: HL7 Extended Person Name
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
public class XPN extends AbstractNameData implements ExpandedNameData {
    
    public final static String XPN_XML = "XPN";
    
    public final static String FAMILY_NAME_XML = "XPN.1";
    
    public final static String GIVEN_NAME_XML = "XPN.2";
    
    public final static String MIDDLE_INITIAL_OR_NAME_XML = "XPN.3";
    
    public final static String SUFFIX_XML = "XPN.4";
    
    public final static String PREFIX_XML = "XPN.5";
    
    public final static String DEGREE_XML = "XPN.6";
    
    public final static String NAME_TYPE_CODE_XML = "XPN.7";
    
    public final static String NAME_REPRESENTATION_CODE_XML = "XPN.8";
    
    public final static String NAME_CONTEXT_XML = "XPN.9";
    
    public final static String NAME_VALIDITY_RANGE_XML = "XPN.10";
    
    public final static String NAME_ASSEMBLY_ORDER_XML = "XPN.11";
    
    public final static String EFFECTIVE_DATE_XML = "XPN.12";
    
    public final static String EXPIRATION_DATE_XML = "XPN.13";
    
    public final static String PROFESSIONAL_SUFFIX_XML = "XPN.14";
    
    public final static String CALLED_BY_XML = "XPN.15";
    
    private String nameTypeCode = null;
    
    private String nameRepresentationCode = null;
    
    private CE nameContext = null;
    
    private DR nameValidityRange = null;
    
    private String nameAssemblyOrder = null;
    
    private TS effectiveDate = null;
    
    private TS expirationDate = null;
    
    private String professionalSuffix = null;
    
    private String calledBy = null;
    
    /**
     * Constructs an empty XPN
     * 
     * @param prop the HL7Properties
     **/
    public XPN(final HL7Properties prop) {
        super(prop);
    }
    
    /**
     * Constructs an XPN from the given first, last, and middle name and suffix/prefix
     * 
     * @param prop the HL7Properties
     * @param familyName the last name
     * @param givenName the first name
     * @param middleInitialOrName the middle initial or name
     * @param suffix the suffix
     * @param prefix the prefix
     **/
    public XPN(final HL7Properties prop, final String familyName, final String givenName, final String middleInitialOrName,
        final String suffix, final String prefix) {
        this(prop);
        this.familyName = FN.create(prop, familyName);
        this.givenName = givenName;
        this.middleInitialOrName = middleInitialOrName;
        this.suffix = suffix;
        this.prefix = prefix;
    }
    
    /**
     * Constructs an XPN from the given first, last, and middle name
     * 
     * @param prop the HL7Properties
     * @param familyName the last name
     * @param givenName the first name
     * @param middleInitialOrName the middle initial or name
     **/
    public XPN(final HL7Properties prop, final String familyName, final String givenName, final String middleInitialOrName) {
        this(prop, familyName, givenName, middleInitialOrName, null, null);
    }
    
    /**
     * Constructs an XPN from the given first and last name
     * 
     * @param prop the HL7Properties
     * @param familyName the last name
     * @param givenName the first name
     **/
    public XPN(final HL7Properties prop, final String familyName, final String givenName) {
        this(prop, familyName, givenName, null, null, null);
    }
    
    /**
     * Retrieves the suffix and/or degree
     * 
     * @return the suffix and/or degree
     **/
    @Override
    public String getSuffixDegree() {
        return getSuffixDegree(this.suffix, this.degree, this.professionalSuffix);
    }
    
    /**
     * Converts this into an XCN
     * 
     * @return this as an XCN
     **/
    public XCN toXCN() {
        final XCN xcn = new XCN(this.prop);
        
        xcn.setDegree(this.getDegree());
        xcn.setFamilyName(this.getFamilyName());
        xcn.setGivenName(this.getGivenName());
        xcn.setMiddleInitialOrName(this.getMiddleInitialOrName());
        xcn.setNameRepresentationCode(this.getNameRepresentationCode());
        xcn.setNameTypeCode(this.getNameTypeCode());
        xcn.setPrefix(this.getPrefix());
        xcn.setSuffix(this.getSuffix());
        xcn.setNameContext(this.getNameContext());
        xcn.setNameValidityRange(this.getNameValidityRange());
        xcn.setNameAssemblyOrder(this.getNameAssemblyOrder());
        xcn.setEffectiveDate(this.getEffectiveDate());
        xcn.setExpirationDate(this.getExpirationDate());
        xcn.setProfessionalSuffix(this.getProfessionalSuffix());
        
        return xcn;
    }
    
    public static XPN parsePiped(final HL7Parser parser, final String line, final int start, final char delim, final int stop) {
        if (stop <= start) {
            return null;
        }
        final XPN xpn = new XPN(parser);
        xpn.readPiped(parser, line, start, delim, stop);
        return xpn;
    }
    
    @Override
    public void readPiped(final HL7Parser parser, final String line, int start, final char delim, final int stop) {
        int next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        final char c = parser.getSubcomponentSeparator();
        this.familyName = FN.parsePiped(parser, line, start, c, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.givenName = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.middleInitialOrName = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.suffix = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.prefix = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.degree = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.nameTypeCode = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.nameRepresentationCode = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.nameContext = CE.parsePiped(parser, line, start, c, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.nameValidityRange = DR.parsePiped(parser, line, start, c, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.nameAssemblyOrder = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.effectiveDate = TS.parsePiped(parser, line, start, c, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.expirationDate = TS.parsePiped(parser, line, start, c, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.professionalSuffix = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.calledBy = getToken(line, start, next);
        
        assertLast(line, next + 1, delim, stop);
    }
    
    @Override
    public void toPiped(final Writer w, final int level) throws IOException {
        int last = 1;
        
        last = addComponent(w, this.familyName, last, 1, level);
        last = addComponent(w, this.givenName, last, 2, level);
        last = addComponent(w, this.middleInitialOrName, last, 3, level);
        last = addComponent(w, this.suffix, last, 4, level);
        last = addComponent(w, this.prefix, last, 5, level);
        last = addComponent(w, this.degree, last, 6, level);
        last = addComponent(w, this.nameTypeCode, last, 7, level);
        last = addComponent(w, this.nameRepresentationCode, last, 8, level);
        last = addComponent(w, this.nameContext, last, 9, level);
        last = addComponent(w, this.nameValidityRange, last, 10, level);
        last = addComponent(w, this.nameAssemblyOrder, last, 11, level);
        last = addComponent(w, this.effectiveDate, last, 12, level);
        last = addComponent(w, this.expirationDate, last, 13, level);
        last = addComponent(w, this.professionalSuffix, last, 14, level);
        last = addComponent(w, this.calledBy, last, 15, level);
    }
    
    /**
     * Compares to the given Object
     * 
     * @param o the Object to compare to this
     * @return true if this equals the given Object
     **/
    @Override
    public boolean equals(final Object o) {
        if ((o == null) || !(o instanceof XPN)) {
            return false;
        }
        
        final XPN xpn = (XPN) o;
        if (!Util.equals(this.familyName, xpn.familyName)) {
            return false;
        } else if (!Util.equals(this.givenName, xpn.givenName)) {
            return false;
        } else if (!Util.equals(this.middleInitialOrName, xpn.middleInitialOrName)) {
            return false;
        } else if (!Util.equals(this.suffix, xpn.suffix)) {
            return false;
        } else if (!Util.equals(this.prefix, xpn.prefix)) {
            return false;
        }
        
        return true;
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
     * Retrieves the called by
     * 
     * @return the called by
     **/
    public String getCalledBy() {
        return this.calledBy;
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
    
    /**
     * Modifies the called by
     * 
     * @param calledBy the new called by
     **/
    public void setCalledBy(final String calledBy) {
        this.calledBy = calledBy;
    }
}
