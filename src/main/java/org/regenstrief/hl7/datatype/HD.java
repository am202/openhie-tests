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
import org.regenstrief.hl7.util.AbstractScopeData;
import org.regenstrief.util.Util;

/**
 * <p>
 * Title: HD
 * </p>
 * <p>
 * Description: HL7 Hierarchical Descriptor
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
public class HD extends AbstractScopeData {
    
    public final static String HD_XML = "HD";
    
    public final static String NAMESPACE_ID_XML = "HD.1";
    
    public final static String UNIVERSAL_ID_XML = "HD.2";
    
    public final static String UNIVERSAL_ID_TYPE_XML = "HD.3";
    
    /**
     * An Internet dotted name. Either in ASCII or as integers
     */
    public final static String TYPE_DNS = "DNS";
    
    // GUID // Same as UUID.
    
    /**
     * The CEN Healthcare Coding Scheme Designator. (Identifiers used in DICOM follow this assignment scheme.)
     */
    public final static String TYPE_HCD = "HCD";
    
    // HL7 // Reserved for future HL7 registration schemes
    
    /**
     * An International Standards Organization Object Identifier
     */
    public final static String TYPE_ISO = "ISO";
    
    // L,M,N // These are reserved for locally defined coding schemes.
    
    // Random
    // Usually a base64 encoded string of random bits.
    // The uniqueness depends on the length of the bits.
    // Mail systems often generate ASCII string "unique names," from a combination of random bits and system names.
    // Obviously, such identifiers will not be constrained to the base64 character set.
    
    /**
     * Uniform Resource Identifier
     */
    public final static String TYPE_URI = "URI";
    
    /**
     * The DCE Universal Unique Identifier
     */
    public final static String TYPE_UUID = "UUID";
    
    // x400 // An X.400 MHS format identifier
    
    // x500 // An X.500 directory name
    
    /**
     * Constructs an empty HD
     * 
     * @param prop the HL7Properties
     **/
    public HD(final HL7Properties prop) {
        super(prop);
    }
    
    /**
     * Constructs a new HD from the given namespace ID
     * 
     * @param prop the HL7Properties
     * @param namespaceID the namespace ID
     **/
    private HD(final HL7Properties prop, final String namespaceID) {
        this(prop);
        setNamespaceID(namespaceID);
    }
    
    /**
     * Constructs a new HD from the given namespace ID
     * 
     * @param prop the HL7Properties
     * @param namespaceID the namespace ID
     * @param universalID the universal ID
     * @param universalIDType the universal ID type
     **/
    private HD(final HL7Properties prop, final String namespaceID, final String universalID, final String universalIDType) {
        this(prop, namespaceID);
        setUniversalID(universalID);
        setUniversalIDType(universalIDType);
    }
    
    /**
     * Constructs a new HD from the given namespace ID
     * 
     * @param prop the HL7Properties
     * @param namespaceID the namespace ID
     * @return the HD
     **/
    public static HD createHD(final HL7Properties prop, final String namespaceID) {
        return namespaceID == null ? null : new HD(prop, namespaceID);
    }
    
    /**
     * Constructs a new HD from the given namespace ID
     * 
     * @param prop the HL7Properties
     * @param namespaceID the namespace ID
     * @param universalID the universal ID
     * @param universalIDType the universal ID type
     * @return the HD
     **/
    public static HD createHD(final HL7Properties prop, final String namespaceID, final String universalID,
                              final String universalIDType) {
        return (namespaceID == null) && (universalID == null) && (universalIDType == null) ? null : new HD(prop,
                namespaceID, universalID, universalIDType);
    }
    
    /**
     * Retrieves the hash code
     * 
     * @return the hash code
     **/
    @Override
    public int hashCode() {
        return Util.hashCode(this.namespaceID) ^ Util.hashCode(this.universalID) ^ Util.hashCode(this.universalIDType);
    }
    
    /**
     * Retrieves whether the given Object is equal to this
     * 
     * @param o the Object
     * @return whether the giveb Object is equal to this
     **/
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof HD)) {
            return false;
        }
        final HD hd = (HD) o;
        
        return Util.equals(this.namespaceID, hd.namespaceID) && Util.equals(this.universalID, hd.universalID)
                && Util.equals(this.universalIDType, hd.universalIDType);
    }
    
    public static HD parsePiped(final HL7Parser parser, final String line, final int start, final char delim, final int stop) {
        if (stop <= start) {
            return null;
        }
        final HD hd = new HD(parser);
        hd.readPiped(parser, line, start, delim, stop);
        return hd;
    }
    
    @Override
    public void readPiped(final HL7Parser parser, final String line, int start, final char delim, final int stop) {
        int next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.namespaceID = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.universalID = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.universalIDType = getToken(line, start, next);
        
        assertLast(line, next + 1, delim, stop);
    }
    
    @Override
    public void toPiped(final Writer w, final int level) throws IOException {
        int last = 1;
        
        last = addComponent(w, this.namespaceID, last, 1, level);
        last = addComponent(w, this.universalID, last, 2, level);
        last = addComponent(w, this.universalIDType, last, 3, level);
    }
}
