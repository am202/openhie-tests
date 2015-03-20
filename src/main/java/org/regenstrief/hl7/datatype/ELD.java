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
 * Title: ELD
 * </p>
 * <p>
 * Description: HL7 Error Location And Description
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
public class ELD extends HL7DataType {
    
    public final static String ELD_XML = "ELD";
    
    public final static String SEGMENT_ID_XML = "ELD.1";
    
    public final static String SEGMENT_SEQUENCE_XML = "ELD.2";
    
    public final static String FIELD_POSITION_XML = "ELD.3";
    
    public final static String CODE_IDENTIFYING_ERROR_XML = "ELD.4";
    
    public final static String MESSAGE_ERROR_CONDITION_CODES_TABLE = "0357";
    
    public final static String CODE_APPLICATION_INTERNAL_ERROR = "207";
    
    private String segmentID = null;
    
    private NM segmentSequence = null;
    
    private NM fieldPosition = null;
    
    private CE codeIdentifyingError = null;
    
    /**
     * Constructs an empty ELD
     * 
     * @param prop the HL7Properties
     **/
    public ELD(final HL7Properties prop) {
        super(prop);
    }
    
    /**
     * Constructs and ELD with the given error message
     * 
     * @param prop the HL7Properties
     * @param errText the error text String
     **/
    public ELD(final HL7Properties prop, final String errText) {
        this(prop, CODE_APPLICATION_INTERNAL_ERROR, errText);
    }
    
    /**
     * Constructs and ELD with the given error message
     * 
     * @param prop the HL7Properties
     * @param errCode the error code String
     * @param errText the error text String
     **/
    public ELD(final HL7Properties prop, final String errCode, final String errText) {
        this(prop);
        this.setCodeIdentifyingError(new CE(prop, errCode, errText, MESSAGE_ERROR_CONDITION_CODES_TABLE));
    }
    
    public static ELD parsePiped(final HL7Parser parser, final String line, final int start, final char delim, final int stop) {
        if (stop <= start) {
            return null;
        }
        final ELD eld = new ELD(parser);
        eld.readPiped(parser, line, start, delim, stop);
        return eld;
    }
    
    @Override
    public void readPiped(final HL7Parser parser, final String line, int start, final char delim, final int stop) {
        int next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.segmentID = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        final char c = parser.getSubcomponentSeparator();
        this.segmentSequence = NM.parsePiped(parser, line, start, c, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.fieldPosition = NM.parsePiped(parser, line, start, c, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.codeIdentifyingError = CE.parsePiped(parser, line, start, c, next);
        
        assertLast(line, next + 1, delim, stop);
    }
    
    @Override
    public void toPiped(final Writer w, final int level) throws IOException {
        int last = 1;
        
        last = addComponent(w, this.segmentID, last, 1, level);
        last = addComponent(w, this.segmentSequence, last, 2, level);
        last = addComponent(w, this.fieldPosition, last, 3, level);
        last = addComponent(w, this.codeIdentifyingError, last, 4, level);
    }
    
    /**
     * Retrieves the segment ID
     * 
     * @return the segment ID
     **/
    public String getSegmentID() {
        return this.segmentID;
    }
    
    /**
     * Retrieves the segment sequence
     * 
     * @return the segment sequence
     **/
    public NM getSegmentSequence() {
        return this.segmentSequence;
    }
    
    /**
     * Retrieves the field position
     * 
     * @return the field position
     **/
    public NM getFieldPosition() {
        return this.fieldPosition;
    }
    
    /**
     * Retrieves the code identifying error
     * 
     * @return the code identifying error
     **/
    public CE getCodeIdentifyingError() {
        return this.codeIdentifyingError;
    }
    
    /**
     * Modifies the segment ID
     * 
     * @param segmentID the new segment ID
     **/
    public void setSegmentID(final String segmentID) {
        this.segmentID = segmentID;
    }
    
    /**
     * Modifies the segment sequence
     * 
     * @param segmentSequence the new segment sequence
     **/
    public void setSegmentSequence(final NM segmentSequence) {
        this.segmentSequence = segmentSequence;
    }
    
    /**
     * Modifies the field position
     * 
     * @param fieldPosition the new field position
     **/
    public void setFieldPosition(final NM fieldPosition) {
        this.fieldPosition = fieldPosition;
    }
    
    /**
     * Modifies the code identifying error
     * 
     * @param codeIdentifyingError the new code identifying error
     **/
    public void setCodeIdentifyingError(final CE codeIdentifyingError) {
        this.codeIdentifyingError = codeIdentifyingError;
    }
}
