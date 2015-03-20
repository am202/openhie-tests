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

/**
 * <p>
 * Title: VID
 * </p>
 * <p>
 * Description: HL7 Version Identifier
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
public class VID extends HL7DataType {
    
    public final static String VID_XML = "VID";
    
    public final static String VERSION_ID_XML = "VID.1";
    
    public final static String INTERNATIONALIZATION_CODE_XML = "VID.2";
    
    public final static String INTERNATIONAL_VERSION_ID_XML = "VID.3";
    
    private String versionID = null;
    
    private CE internationalizationCode = null;
    
    private CE internationalVersionID = null;
    
    /**
     * Constructs an empty VID
     * 
     * @param prop the HL7Properties
     **/
    public VID(final HL7Properties prop) {
        super(prop);
    }
    
    /**
     * Constructs a VID with the given version ID
     * 
     * @param prop the HL7Properties
     * @param versionID the version ID
     **/
    public VID(final HL7Properties prop, final String versionID) {
        this(prop);
        this.setVersionID(versionID);
    }
    
    public static VID parsePiped(final HL7Parser parser, final String line, final int start, final char delim, final int stop) {
        if (stop <= start) {
            return null;
        }
        final VID vid = new VID(parser);
        vid.readPiped(parser, line, start, delim, stop);
        return vid;
    }
    
    @Override
    public void readPiped(final HL7Parser parser, final String line, int start, final char delim, final int stop) {
        int next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.versionID = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        final char c = parser.getSubcomponentSeparator();
        this.internationalizationCode = CE.parsePiped(parser, line, start, c, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.internationalVersionID = CE.parsePiped(parser, line, start, c, next);
        
        assertLast(line, next + 1, delim, stop);
    }
    
    @Override
    public void toPiped(final Writer w, final int level) throws IOException {
        int last = 1;
        
        last = addComponent(w, this.versionID, last, 1, level);
        last = addComponent(w, this.internationalizationCode, last, 2, level);
        last = addComponent(w, this.internationalVersionID, last, 3, level);
    }
    
    /**
     * Retrieves the international version ID
     * 
     * @return the international version ID
     **/
    public CE getInternationalVersionID() {
        return this.internationalVersionID;
    }
    
    /**
     * Retrieves the version ID
     * 
     * @return the version ID
     **/
    public String getVersionID() {
        return this.versionID;
    }
    
    /**
     * Retrieves the internationalization code
     * 
     * @return the internationalization code
     **/
    public CE getInternationalizationCode() {
        return this.internationalizationCode;
    }
    
    /**
     * Modifies the international version ID
     * 
     * @param internationalVersionID the new international version ID
     **/
    public void setInternationalVersionID(final CE internationalVersionID) {
        this.internationalVersionID = internationalVersionID;
    }
    
    /**
     * Modifies the version ID
     * 
     * @param versionID the new version ID
     **/
    public void setVersionID(final String versionID) {
        this.versionID = versionID;
    }
    
    /**
     * Modifies the internationalization code
     * 
     * @param internationalizationCode the new internationalization code
     **/
    public void setInternationalizationCode(final CE internationalizationCode) {
        this.internationalizationCode = internationalizationCode;
    }
}
