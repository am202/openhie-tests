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
import org.regenstrief.hl7.util.IdNameData;
import org.regenstrief.hl7.util.UtilHL7;
import org.regenstrief.util.Util;

/**
 * <p>
 * Title: CN
 * </p>
 * <p>
 * Description: HL7 Composite ID Number And Name
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
public class CN extends HL7DataType implements IdNameData {
    
    public final static String CN_XML = "CN";
    
    public final static String ID_NUMBER_XML = "CN.1";
    
    public final static String FAMILY_NAME_XML = "CN.2";
    
    public final static String GIVEN_NAME_XML = "CN.3";
    
    public final static String MIDDLE_INITIAL_OR_NAME_XML = "CN.4";
    
    public final static String SUFFIX_XML = "CN.5";
    
    public final static String PREFIX_XML = "CN.6";
    
    public final static String DEGREE_XML = "CN.7";
    
    public final static String SOURCE_TABLE_XML = "CN.8";
    
    public final static String ASSIGNING_AUTHORITY_XML = "CN.9";
    
    private String idNumber = null;
    
    private String familyName = null;
    
    private String givenName = null;
    
    private String middleInitialOrName = null;
    
    private String suffix = null;
    
    private String prefix = null;
    
    private String degree = null;
    
    private CWE sourceTable = null; // Changed to CWE to be consistent with XCN; CN was gone by then
    
    private HD assigningAuthority = null;
    
    /**
     * Constructs an empty CN
     * 
     * @param prop the HL7Properties
     **/
    public CN(final HL7Properties prop) {
        super(prop);
    }
    
    /**
     * Returns a CN created from the given XCN
     * 
     * @param xcn the XCN
     * @return the CN
     **/
    public static CN createCN(final XCN xcn) {
        return xcn == null ? null : xcn.getCN();
    }
    
    /**
     * Constructs an CN from the given first, last, and middle name and suffix/prefix
     * 
     * @param prop the HL7Properties
     * @param familyName the last name
     * @param givenName the first name
     * @param middleInitialOrName the middle initial or name
     * @param suffix the suffix
     * @param prefix the prefix
     **/
    public CN(final HL7Properties prop, final String familyName, final String givenName, final String middleInitialOrName,
        final String suffix, final String prefix) {
        this(prop);
        setName(familyName, givenName, middleInitialOrName, suffix, prefix);
    }
    
    /**
     * Retrieves the suffix and/or degree
     * 
     * @return the suffix and/or degree
     **/
    @Override
    public String getSuffixDegree() {
        return UtilHL7.concat(this.suffix, this.degree, " ");
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
    public void setName(final String familyName, final String givenName, final String middleInitialOrName,
                        final String suffix, final String prefix) {
        //this.familyName = new FN(familyName);
        this.familyName = familyName;
        this.givenName = givenName;
        this.middleInitialOrName = middleInitialOrName;
        this.suffix = suffix;
        this.prefix = prefix;
    }
    
    /**
     * Constructs an CN from the given first, last, and middle name
     * 
     * @param prop the HL7Properties
     * @param familyName the last name
     * @param givenName the first name
     * @param middleInitialOrName the middle initial or name
     **/
    public CN(final HL7Properties prop, final String familyName, final String givenName, final String middleInitialOrName) {
        this(prop, familyName, givenName, middleInitialOrName, null, null);
    }
    
    /**
     * Constructs an CN from the given first and last name
     * 
     * @param prop the HL7Properties
     * @param familyName the last name
     * @param givenName the first name
     **/
    public CN(final HL7Properties prop, final String familyName, final String givenName) {
        this(prop, familyName, givenName, null, null, null);
    }
    
    /**
     * Converts this into an XCN
     * 
     * @return this as an XCN
     **/
    public XCN toXCN() {
        final XCN xcn = new XCN(this.prop);
        
        xcn.setID(this.idNumber);
        xcn.setFamilyName(FN.create(this.prop, this.familyName));
        xcn.setGivenName(this.givenName);
        xcn.setMiddleInitialOrName(this.middleInitialOrName);
        xcn.setSuffix(this.suffix);
        xcn.setPrefix(this.prefix);
        xcn.setDegree(this.degree);
        xcn.setSourceTable(this.sourceTable);
        xcn.setAssigningAuthority(this.assigningAuthority);
        
        return xcn;
    }
    
    /**
     * Determines if this is equal to the given Object
     * 
     * @param o the Object to compare to this
     * @return true if this equals o, false otherwise
     **/
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof CN)) {
            return false;
        }
        
        return equals((CN) o);
    }
    
    /**
     * Determines if this is equal to the given CN
     * 
     * @param cn the CN to compare to this
     * @return true if this equals cn, false otherwise
     **/
    public boolean equals(final CN cn) {
        if (cn == null) {
            return false;
        }
        
        //if (!Util.equalsIgnoreCase(FN.getSurname(cn.getFamilyName()), FN.getSurname(familyName))) return false;
        if (!Util.equalsIgnoreCase(cn.getSurname(), this.familyName)) {
            return false;
        } else if (!Util.equalsIgnoreCase(cn.getGivenName(), this.givenName)) {
            return false;
        }
        
        return true;
    }
    
    public static CN parsePiped(final HL7Parser parser, final String line, final int start, final char delim, final int stop) {
        if (stop <= start) {
            return null;
        }
        final CN cn = new CN(parser);
        cn.readPiped(parser, line, start, delim, stop);
        return cn;
    }
    
    @Override
    public void readPiped(final HL7Parser parser, final String line, int start, final char delim, final int stop) {
        int next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.idNumber = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        final char c = parser.getSubcomponentSeparator();
        this.familyName = getToken(line, start, next);
        
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
        this.sourceTable = CWE.parsePiped(parser, line, start, c, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.assigningAuthority = HD.parsePiped(parser, line, start, c, next);
        
        assertLast(line, next + 1, delim, stop);
    }
    
    @Override
    public void toPiped(final Writer w, final int level) throws IOException {
        int last = 1;
        
        last = addComponent(w, this.idNumber, last, 1, level);
        last = addComponent(w, this.familyName, last, 2, level);
        last = addComponent(w, this.givenName, last, 3, level);
        last = addComponent(w, this.middleInitialOrName, last, 4, level);
        last = addComponent(w, this.suffix, last, 5, level);
        last = addComponent(w, this.prefix, last, 6, level);
        last = addComponent(w, this.degree, last, 7, level);
        last = addComponent(w, this.sourceTable, last, 8, level);
        last = addComponent(w, this.assigningAuthority, last, 9, level);
    }
    
    /**
     * Retrieves the ID number
     * 
     * @return the ID number
     **/
    @Override
    public String getID() {
        return this.idNumber;
    }
    
    /**
     * Retrieves the family name
     * 
     * @return the family name
     **/
    @Override
    public String getSurname() {
        return this.familyName;
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
    @Override
    public void setID(final String idNumber) {
        this.idNumber = idNumber;
    }
    
    /**
     * Modifies the family name
     * 
     * @param familyName the new family name
     **/
    @Override
    public void setSurname(final String familyName) {
        this.familyName = familyName;
    }
    
    /**
     * Modifies the family name
     * 
     * @param familyName the new family name
     **/
    public void setFamilyName(final FN familyName) {
        this.familyName = FN.getSurname(familyName);
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
     * Modifies the assigning authority
     * 
     * @param assigningAuthority the new assigning authority
     **/
    @Override
    public void setAssigningAuthority(final HD assigningAuthority) {
        this.assigningAuthority = assigningAuthority;
    }
    
    /**
     * Retrieves the ID number
     * 
     * @return the ID number
     **/
    @Override
    public String getFullIdentifier() {
        return this.idNumber;
    }
}
