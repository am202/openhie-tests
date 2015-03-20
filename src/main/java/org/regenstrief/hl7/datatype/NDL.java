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
import org.regenstrief.util.Util;

/**
 * <p>
 * Title: NDL
 * </p>
 * <p>
 * Description: HL7 Name With Date And Location
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
public class NDL extends HL7DataType {
    
    public final static String NDL_XML = "NDL";
    
    public final static String NAME_XML = "NDL.1";
    
    public final static String START_DATE_TIME_XML = "NDL.2";
    
    public final static String END_DATE_TIME_XML = "NDL.3";
    
    public final static String POINT_OF_CARE_XML = "NDL.4";
    
    public final static String ROOM_XML = "NDL.5";
    
    public final static String BED_XML = "NDL.6";
    
    public final static String FACILITY_XML = "NDL.7";
    
    public final static String LOCATION_STATUS_XML = "NDL.8";
    
    public final static String PATIENT_LOCATION_TYPE_XML = "NDL.9";
    
    public final static String BUILDING_XML = "NDL.10";
    
    public final static String FLOOR_XML = "NDL.11";
    
    private CN name = null;
    
    private TS startDateTime = null;
    
    private TS endDateTime = null;
    
    private String pointOfCare = null;
    
    private String room = null;
    
    private String bed = null;
    
    private HD facility = null;
    
    private String locationStatus = null;
    
    private String patientLocationType = null;
    
    private String building = null;
    
    private String floor = null;
    
    /**
     * Constructs an empty NDL
     * 
     * @param prop the HL7Properties
     **/
    public NDL(final HL7Properties prop) {
        super(prop);
    }
    
    /**
     * Determines if this is equal to the given Object
     * 
     * @param o the Object to compare to this
     * @return true if this equals o, false otherwise
     **/
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof NDL)) {
            return false;
        }
        
        return equals((NDL) o);
    }
    
    /**
     * Determines if this is equal to the given NDL
     * 
     * @param ndl the NDL to compare to this
     * @return true if this equals ndl, false otherwise
     **/
    public boolean equals(final NDL ndl) {
        if (ndl == null) {
            return false;
        } else if (!Util.equals(this.name, ndl.getName())) {
            return false;
        }
        
        return true;
    }
    
    public static NDL parsePiped(final HL7Parser parser, final String line, final int start, final char delim, final int stop) {
        if (stop <= start) {
            return null;
        }
        final NDL ndl = new NDL(parser);
        ndl.readPiped(parser, line, start, delim, stop);
        return ndl;
    }
    
    @Override
    public void readPiped(final HL7Parser parser, final String line, int start, final char delim, final int stop) {
        int next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        final char c = parser.getSubcomponentSeparator();
        this.name = CN.parsePiped(parser, line, start, c, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.startDateTime = TS.parsePiped(parser, line, start, c, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.endDateTime = TS.parsePiped(parser, line, start, c, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.pointOfCare = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.room = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.bed = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.facility = HD.parsePiped(parser, line, start, c, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.locationStatus = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.patientLocationType = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.building = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.floor = getToken(line, start, next);
        
        assertLast(line, next + 1, delim, stop);
    }
    
    @Override
    public void toPiped(final Writer w, final int level) throws IOException {
        int last = 1;
        
        last = addComponent(w, this.name, last, 1, level);
        last = addComponent(w, this.startDateTime, last, 2, level);
        last = addComponent(w, this.endDateTime, last, 3, level);
        last = addComponent(w, this.pointOfCare, last, 4, level);
        last = addComponent(w, this.room, last, 5, level);
        last = addComponent(w, this.bed, last, 6, level);
        last = addComponent(w, this.facility, last, 7, level);
        last = addComponent(w, this.locationStatus, last, 8, level);
        last = addComponent(w, this.patientLocationType, last, 9, level);
        last = addComponent(w, this.building, last, 10, level);
        last = addComponent(w, this.floor, last, 11, level);
    }
    
    /**
     * Retrieves the name
     * 
     * @return the name
     **/
    public CN getName() {
        return this.name;
    }
    
    /**
     * Retrieves the start date/time
     * 
     * @return the start date/time
     **/
    public TS getStartDateTime() {
        return this.startDateTime;
    }
    
    /**
     * Retrieves the end date/time
     * 
     * @return the end date/time
     **/
    public TS getEndDateTime() {
        return this.endDateTime;
    }
    
    /**
     * Retrieves the point of care
     * 
     * @return the point of care
     **/
    public String getPointOfCare() {
        return this.pointOfCare;
    }
    
    /**
     * Retrieves the room
     * 
     * @return the room
     **/
    public String getRoom() {
        return this.room;
    }
    
    /**
     * Retrieves the bed
     * 
     * @return the bed
     **/
    public String getBed() {
        return this.bed;
    }
    
    /**
     * Retrieves the facility
     * 
     * @return the facility
     **/
    public HD getFacility() {
        return this.facility;
    }
    
    /**
     * Retrieves the location status
     * 
     * @return the location status
     **/
    public String getLocationStatus() {
        return this.locationStatus;
    }
    
    /**
     * Retrieves the patient location type
     * 
     * @return the patient location type
     **/
    public String getPatientLocationType() {
        return this.patientLocationType;
    }
    
    /**
     * Retrieves the building
     * 
     * @return the building
     **/
    public String getBuilding() {
        return this.building;
    }
    
    /**
     * Retrieves the floor
     * 
     * @return the floor
     **/
    public String getFloor() {
        return this.floor;
    }
    
    /**
     * Modifies the name
     * 
     * @param name the new name
     **/
    public void setName(final CN name) {
        this.name = name;
    }
    
    /**
     * Modifies the start date/time
     * 
     * @param startDateTime the new start date/time
     **/
    public void setStartDateTime(final TS startDateTime) {
        this.startDateTime = startDateTime;
    }
    
    /**
     * Modifies the end date/time
     * 
     * @param endDateTime the new end date/time
     **/
    public void setEndDateTime(final TS endDateTime) {
        this.endDateTime = endDateTime;
    }
    
    /**
     * Modifies the point of care
     * 
     * @param pointOfCare the new point of care
     **/
    public void setPointOfCare(final String pointOfCare) {
        this.pointOfCare = pointOfCare;
    }
    
    /**
     * Modifies the room
     * 
     * @param room the new room
     **/
    public void setRoom(final String room) {
        this.room = room;
    }
    
    /**
     * Modifies the bed
     * 
     * @param bed the new bed
     **/
    public void setBed(final String bed) {
        this.bed = bed;
    }
    
    /**
     * Modifies the facility
     * 
     * @param facility the new facility
     **/
    public void setFacility(final HD facility) {
        this.facility = facility;
    }
    
    /**
     * Modifies the location status
     * 
     * @param locationStatus the new location status
     **/
    public void setLocationStatus(final String locationStatus) {
        this.locationStatus = locationStatus;
    }
    
    /**
     * Modifies the patient location type
     * 
     * @param patientLocationType the new patient location type
     **/
    public void setPatientLocationType(final String patientLocationType) {
        this.patientLocationType = patientLocationType;
    }
    
    /**
     * Modifies the building
     * 
     * @param building the new building
     **/
    public void setBuilding(final String building) {
        this.building = building;
    }
    
    /**
     * Modifies the floor
     * 
     * @param floor the new floor
     **/
    public void setFloor(final String floor) {
        this.bed = floor;
    }
}
