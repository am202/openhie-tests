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
import java.util.Date;

import org.regenstrief.hl7.HL7Parser;
import org.regenstrief.hl7.HL7Properties;

/**
 * <p>
 * Title: DR
 * </p>
 * <p>
 * Description: HL7 Date/Time Range
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
public class DR extends HL7DataType {
    
    public final static String DR_XML = "DR";
    
    public final static String RANGE_START_DATE_TIME_XML = "DR.1";
    
    public final static String RANGE_END_DATE_TIME_XML = "DR.2";
    
    private TS rangeStartDateTime = null;
    
    private TS rangeEndDateTime = null;
    
    /**
     * Constructs an empty DR
     * 
     * @param prop the HL7Properties
     **/
    public DR(final HL7Properties prop) {
        super(prop);
    }
    
    /**
     * Constructs a DR from the given start Date
     * 
     * @param prop the HL7Properties
     * @param d the start Date
     **/
    public DR(final HL7Properties prop, final Date d) {
        this(prop);
        this.rangeStartDateTime = new TS(prop, d);
    }
    
    public final static DR create(final HL7Properties prop, final TS ts) {
        if (ts == null) {
            return null;
        }
        final DR dr = new DR(prop);
        dr.rangeStartDateTime = ts;
        return dr;
    }
    
    public static DR parsePiped(final HL7Parser parser, final String line, final int start, final char delim, final int stop) {
        if (stop <= start) {
            return null;
        }
        final DR dr = new DR(parser);
        dr.readPiped(parser, line, start, delim, stop);
        return dr;
    }
    
    @Override
    public void readPiped(final HL7Parser parser, final String line, int start, final char delim, final int stop) {
        int next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        final char c = parser.getSubcomponentSeparator();
        this.rangeStartDateTime = TS.parsePiped(parser, line, start, c, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.rangeEndDateTime = TS.parsePiped(parser, line, start, c, next);
        
        assertLast(line, next + 1, delim, stop);
    }
    
    @Override
    public void toPiped(final Writer w, final int level) throws IOException {
        int last = 1;
        
        last = addComponent(w, this.rangeStartDateTime, last, 1, level);
        last = addComponent(w, this.rangeEndDateTime, last, 2, level);
    }
    
    /**
     * Retrieves the range start date/time
     * 
     * @return the range start date/time
     **/
    public TS getRangeStartDateTime() {
        return this.rangeStartDateTime;
    }
    
    /**
     * Retrieves the range end date/time
     * 
     * @return the range end date/time
     **/
    public TS getRangeEndDateTime() {
        return this.rangeEndDateTime;
    }
    
    /**
     * Modifies the range start date/time
     * 
     * @param rangeStartDateTime the new range start date/time
     **/
    public void setRangeStartDateTime(final TS rangeStartDateTime) {
        this.rangeStartDateTime = rangeStartDateTime;
    }
    
    /**
     * Modifies the range end date/time
     * 
     * @param rangeEndDateTime the new range end date/time
     **/
    public void setRangeEndDateTime(final TS rangeEndDateTime) {
        this.rangeEndDateTime = rangeEndDateTime;
    }
    
    public final static TS getRangeStartDateTime(final DR dr) {
        return dr == null ? null : dr.rangeStartDateTime;
    }
}
