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
import org.regenstrief.hl7.datatype.HD;
import org.regenstrief.hl7.datatype.TS;
import org.regenstrief.hl7.datatype.XCN;
import org.regenstrief.util.Util;

/**
 * <p>
 * Title: EVN
 * </p>
 * <p>
 * Description: HL7 EVN segment
 * </p>
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * <p>
 * Company: Regenstrief Institute
 * </p>
 * 
 * @author Andrew Martin
 * @version 1.0
 */
public class EVN extends HL7Segment {
    
    public final static String EVN_XML = "EVN";
    
    public final static String EVENT_TYPE_CODE_XML = "EVN.1";
    
    public final static String RECORDED_DATE_TIME_XML = "EVN.2";
    
    public final static String DATE_TIME_PLANNED_EVENT_XML = "EVN.3";
    
    public final static String EVENT_REASON_CODE_XML = "EVN.4";
    
    public final static String OPERATOR_ID_XML = "EVN.5";
    
    public final static String EVENT_OCCURRED_XML = "EVN.6";
    
    public final static String EVENT_FACILITY_XML = "EVN.7";
    
    private String eventTypeCode = null;
    
    private TS recordedDateTime = null;
    
    private TS dateTimePlannedEvent = null;
    
    private String eventReasonCode = null;
    
    private List<XCN> operatorID = null;
    
    private TS eventOccurred = null;
    
    private HD eventFacility = null;
    
    /**
     * Constructs an empty EVN
     * 
     * @param prop the HL7Properties
     **/
    public EVN(final HL7Properties prop) {
        super(prop);
    }
    
    /**
     * Adds missing required fields
     * 
     * @return the HL7Data with all required fields
     **/
    @Override
    public EVN addRequired() {
        if (this.recordedDateTime == null) {
            this.recordedDateTime = new TS(this.prop);
        }
        
        return this;
    }
    
    public static EVN parsePiped(final HL7Parser parser, final String line) {
        final EVN evn = new EVN(parser);
        evn.readPiped(parser, line);
        return evn;
    }
    
    @Override
    public void readPiped(final HL7Parser parser, final String line) {
        final char f = parser.getFieldSeparator();
        int start = line.indexOf(f) + 1;
        if (start <= 0) {
            return;
        }
        int stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.eventTypeCode = getToken(line, start, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        final char c = parser.getComponentSeparator();
        this.recordedDateTime = TS.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.dateTimePlannedEvent = TS.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.eventReasonCode = getToken(line, start, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        final char r = parser.getRepetitionSeparator();
        while (start < stop) {
            final int next = getNext(line, start, r, stop);
            addOperatorID(XCN.parsePiped(parser, line, start, c, next));
            start = next + 1;
        }
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.eventOccurred = TS.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.eventFacility = HD.parsePiped(parser, line, start, c, stop);
        
        assertLast(line, stop + 1, f, line.length());
    }
    
    @Override
    public void toPiped(final Writer w) throws IOException {
        int last = startSegment(w, EVN_XML);
        
        last = addField(w, this.eventTypeCode, last, 1);
        last = addField(w, this.recordedDateTime, last, 2);
        last = addField(w, this.dateTimePlannedEvent, last, 3);
        last = addField(w, this.eventReasonCode, last, 4);
        last = addField(w, this.operatorID, last, 5);
        last = addField(w, this.eventOccurred, last, 6);
        last = addField(w, this.eventFacility, last, 7);
        
        endSegment(w);
    }
    
    /**
     * Retrieves the field at the given index
     * 
     * @param i the index
     * @return the field
     **/
    @Override
    public Object get(final int i) {
        switch (i) {
            case 1:
                return this.eventTypeCode;
            case 2:
                return this.recordedDateTime;
            case 3:
                return this.dateTimePlannedEvent;
            case 4:
                return this.eventReasonCode;
            case 5:
                return this.operatorID;
            case 6:
                return this.eventOccurred;
            case 7:
                return this.eventFacility;
        }
        throw new ArrayIndexOutOfBoundsException(i);
    }
    
    /**
     * Retrieves the Event Type Code
     * 
     * @return the Event Type Code
     **/
    public String getEventTypeCode() {
        return this.eventTypeCode;
    }
    
    /**
     * Retrieves the Recorded Date/Time
     * 
     * @return the Recorded Date/Time
     **/
    public TS getRecordedDateTime() {
        return this.recordedDateTime;
    }
    
    /**
     * Retrieves the Date/Time Planned Event
     * 
     * @return the Date/Time Planned Event
     **/
    public TS getDateTimePlannedEvent() {
        return this.dateTimePlannedEvent;
    }
    
    /**
     * Retrieves the Event Reason Code
     * 
     * @return the Event Reason Code
     **/
    public String getEventReasonCode() {
        return this.eventReasonCode;
    }
    
    /**
     * Retrieves the Operator ID
     * 
     * @return the Operator ID
     **/
    public List<XCN> getOperatorID() {
        return this.operatorID;
    }
    
    /**
     * Retrieves the Event Occurred
     * 
     * @return the Event Occurred
     **/
    public TS getEventOccurred() {
        return this.eventOccurred;
    }
    
    /**
     * Retrieves the Event Facility
     * 
     * @return the Event Facility
     **/
    public HD getEventFacility() {
        return this.eventFacility;
    }
    
    /**
     * Modifies the Event Type Code
     * 
     * @param eventTypeCode the new Event Type Code
     **/
    public void setEventTypeCode(final String eventTypeCode) {
        this.eventTypeCode = eventTypeCode;
    }
    
    /**
     * Modifies the Recorded Date/Time
     * 
     * @param recordedDateTime the new Recorded Date/Time
     **/
    public void setRecordedDateTime(final TS recordedDateTime) {
        this.recordedDateTime = recordedDateTime;
    }
    
    /**
     * Modifies the Date/Time Planned Event
     * 
     * @param dateTimePlannedEvent the new Date/Time Planned Event
     **/
    public void setDateTimePlannedEvent(final TS dateTimePlannedEvent) {
        this.dateTimePlannedEvent = dateTimePlannedEvent;
    }
    
    /**
     * Modifies the Event Reason Code
     * 
     * @param eventReasonCode the new Event Reason Code
     **/
    public void setEventReasonCode(final String eventReasonCode) {
        this.eventReasonCode = eventReasonCode;
    }
    
    /**
     * Modifies the Operator ID
     * 
     * @param operatorID the new Operator ID
     **/
    public void setOperatorID(final List<XCN> operatorID) {
        this.operatorID = operatorID;
    }
    
    /**
     * Modifies the Operator ID
     * 
     * @param operatorID the new Operator ID
     **/
    public void addOperatorID(final XCN operatorID) {
        this.operatorID = Util.addIfNotNull(this.operatorID, operatorID);
    }
    
    /**
     * Modifies the Event Occurred
     * 
     * @param eventOccurred the new Event Occurred
     **/
    public void setEventOccurred(final TS eventOccurred) {
        this.eventOccurred = eventOccurred;
    }
    
    /**
     * Modifies the Event Facility
     * 
     * @param eventFacility the new Event Facility
     **/
    public void setEventFacility(final HD eventFacility) {
        this.eventFacility = eventFacility;
    }
    
    /**
     * Clears the EVN
     **/
    @Override
    public void clear() {
        super.clear();
        this.eventTypeCode = null;
        this.recordedDateTime = null;
        this.dateTimePlannedEvent = null;
        this.eventReasonCode = null;
        Util.clear(this.operatorID);
        this.eventOccurred = null;
        this.eventFacility = null;
    }
}
