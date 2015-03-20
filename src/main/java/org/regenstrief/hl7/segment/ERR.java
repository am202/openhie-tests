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
package org.regenstrief.hl7.segment;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import org.regenstrief.hl7.HL7Parser;
import org.regenstrief.hl7.HL7Properties;
import org.regenstrief.hl7.datatype.ELD;
import org.regenstrief.util.Util;

/**
 * <p>
 * Title: ERR
 * </p>
 * <p>
 * Description: HL7 Error Segment
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
public class ERR extends HL7Segment {
    
    public final static String ERR_XML = "ERR";
    
    public final static String ERROR_CODE_AND_LOCATION_XML = "ERR.1";
    
    private List<ELD> errorCodeAndLocation = null;
    
    /**
     * Constructs an empty ERR
     * 
     * @param prop the HL7Properties
     **/
    public ERR(final HL7Properties prop) {
        super(prop);
    }
    
    /**
     * Constructs and ERR with the given error message
     * 
     * @param prop the HL7Properties
     * @param errText the error text String
     **/
    public ERR(final HL7Properties prop, final String errText) {
        this(prop);
        this.addErrorCodeAndLocation(new ELD(prop, errText));
    }
    
    /**
     * Constructs and ERR with the given error message
     * 
     * @param prop the HL7Properties
     * @param errCode the error code String
     * @param errText the error text String
     **/
    public ERR(final HL7Properties prop, final String errCode, final String errText) {
        this(prop);
        this.addErrorCodeAndLocation(new ELD(prop, errCode, errText));
    }
    
    /**
     * Adds missing required fields
     * 
     * @return the HL7Data with all required fields
     **/
    @Override
    public ERR addRequired() {
        if (Util.isEmpty(this.errorCodeAndLocation)) {
            addErrorCodeAndLocation(new ELD(this.prop));
        }
        
        return this;
    }
    
    public static ERR parsePiped(final HL7Parser parser, final String line) {
        final ERR err = new ERR(parser);
        err.readPiped(parser, line);
        return err;
    }
    
    @Override
    public void readPiped(final HL7Parser parser, final String line) {
        final char f = parser.getFieldSeparator();
        int start = line.indexOf(f) + 1;
        if (start <= 0) {
            return;
        }
        final int stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        final char c = parser.getComponentSeparator();
        final char r = parser.getRepetitionSeparator();
        while (start < stop) {
            final int next = getNext(line, start, r, stop);
            addErrorCodeAndLocation(ELD.parsePiped(parser, line, start, c, next));
            start = next + 1;
        }
        
        assertLast(line, stop + 1, f, line.length());
    }
    
    @Override
    public void toPiped(final Writer w) throws IOException {
        int last = startSegment(w, ERR_XML);
        
        last = addField(w, this.errorCodeAndLocation, last, 1);
        
        endSegment(w);
    }
    
    /**
     * Retrieves the error code and location
     * 
     * @return the error code and location
     **/
    public List<ELD> getErrorCodeAndLocation() {
        return this.errorCodeAndLocation;
    }
    
    /**
     * Modifies the error code and location
     * 
     * @param errorCodeAndLocation the new error code and location
     **/
    public void setErrorCodeAndLocation(final List<ELD> errorCodeAndLocation) {
        this.errorCodeAndLocation = errorCodeAndLocation;
    }
    
    /**
     * Modifies the error code and location
     * 
     * @param errorCodeAndLocation the new error code and location
     **/
    public void addErrorCodeAndLocation(final ELD errorCodeAndLocation) {
        this.errorCodeAndLocation = Util.add(this.errorCodeAndLocation, errorCodeAndLocation);
    }
}
