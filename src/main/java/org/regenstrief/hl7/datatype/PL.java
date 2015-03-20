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
import org.regenstrief.hl7.util.AbstractLocationData;

/**
 * <p>
 * Title: PL
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
public class PL extends AbstractLocationData {
    
    public final static String PL_XML = "PL";
    
    public final static String POINT_OF_CARE_XML = "PL.1";
    
    public final static String ROOM_XML = "PL.2";
    
    public final static String BED_XML = "PL.3";
    
    public final static String FACILITY_XML = "PL.4";
    
    public final static String LOCATION_STATUS_XML = "PL.5";
    
    public final static String PATIENT_LOCATION_TYPE_XML = "PL.6";
    
    public final static String BUILDING_XML = "PL.7";
    
    public final static String FLOOR_XML = "PL.8";
    
    public final static String LOCATION_DESCRIPTION_XML = "PL.9";
    
    public final static String COMPREHENSIVE_LOCATION_IDENTIFIER_XML = "PL.10";
    
    public final static String ASSIGNING_AUTHORITY_FOR_LOCATION_XML = "PL.11";
    
    private String locationDescription = null;
    
    private EI comprehensiveLocationIdentifier = null;
    
    private HD assigningAuthorityForLocation = null;
    
    /**
     * Constructs an empty PL
     * 
     * @param prop the HL7Properties
     **/
    public PL(final HL7Properties prop) {
        super(prop);
    }
    
    /**
     * Converts this into a String
     * 
     * @return this as a String
     **/
    @Override
    public String toDisplay() {
        if (this.pointOfCare != null) {
            return this.pointOfCare;
        }
        
        final StringBuilder s = new StringBuilder();
        append(s, this.building);
        append(s, this.floor);
        append(s, this.room);
        append(s, this.bed);
        return s.length() == 0 ? null : s.toString();
    }
    
    private void append(final StringBuilder s, final String val) {
        if (val != null) {
            if (s.length() > 0) {
                s.append(", ");
            }
            s.append(val);
        }
    }
    
    public static PL parsePiped(final HL7Parser parser, final String line, final int start, final char delim, final int stop) {
        if (stop <= start) {
            return null;
        }
        final PL pl = new PL(parser);
        pl.readPiped(parser, line, start, delim, stop);
        return pl;
    }
    
    @Override
    public void readPiped(final HL7Parser parser, final String line, int start, final char delim, final int stop) {
        int next = getNext(line, start, delim, stop);
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
        final char c = parser.getSubcomponentSeparator();
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
        this.locationType = getToken(line, start, next);
        
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
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.locationDescription = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.comprehensiveLocationIdentifier = EI.parsePiped(parser, line, start, c, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.assigningAuthorityForLocation = HD.parsePiped(parser, line, start, c, next);
        
        assertLast(line, next + 1, delim, stop);
    }
    
    @Override
    public void toPiped(final Writer w, final int level) throws IOException {
        int last = 1;
        
        last = addComponent(w, this.pointOfCare, last, 1, level);
        last = addComponent(w, this.room, last, 2, level);
        last = addComponent(w, this.bed, last, 3, level);
        last = addComponent(w, this.facility, last, 4, level);
        last = addComponent(w, this.locationStatus, last, 5, level);
        last = addComponent(w, this.locationType, last, 6, level);
        last = addComponent(w, this.building, last, 7, level);
        last = addComponent(w, this.floor, last, 8, level);
        last = addComponent(w, this.locationDescription, last, 9, level);
        last = addComponent(w, this.comprehensiveLocationIdentifier, last, 10, level);
        last = addComponent(w, this.assigningAuthorityForLocation, last, 11, level);
    }
    
    /**
     * Retrieves the component at the given index
     * 
     * @param i the index
     * @return the component
     **/
    @Override
    public Object get(final int i) {
        switch (i) {
            case 1:
                return this.pointOfCare;
            case 2:
                return this.room;
            case 3:
                return this.bed;
            case 4:
                return this.facility;
            case 5:
                return this.locationStatus;
            case 6:
                return this.locationType;
            case 7:
                return this.building;
            case 8:
                return this.floor;
            case 9:
                return this.locationDescription;
        }
        throw new ArrayIndexOutOfBoundsException(i);
    }
    
    /**
     * Retrieves the location description
     * 
     * @return the location description
     **/
    public String getLocationDescription() {
        return this.locationDescription;
    }
    
    /**
     * Retrieves the Comprehensive Location Identifier
     * 
     * @return the Comprehensive Location Identifier
     **/
    public EI getComprehensiveLocationIdentifier() {
        return this.comprehensiveLocationIdentifier;
    }
    
    /**
     * Retrieves the Assigning Authority For Location
     * 
     * @return the Assigning Authority For Location
     **/
    public HD getAssigningAuthorityForLocation() {
        return this.assigningAuthorityForLocation;
    }
    
    /**
     * Modifies the location description
     * 
     * @param locationDescription the new location description
     **/
    public void setLocationDescription(final String locationDescription) {
        this.locationDescription = locationDescription;
    }
    
    /**
     * Modifies the Comprehensive Location Identifier
     * 
     * @param comprehensiveLocationIdentifier the new Comprehensive Location Identifier
     **/
    public void setComprehensiveLocationIdentifier(final EI comprehensiveLocationIdentifier) {
        this.comprehensiveLocationIdentifier = comprehensiveLocationIdentifier;
    }
    
    /**
     * Modifies the Assigning Authority For Location
     * 
     * @param assigningAuthorityForLocation the new Assigning Authority For Location
     **/
    public void setAssigningAuthorityForLocation(final HD assigningAuthorityForLocation) {
        this.assigningAuthorityForLocation = assigningAuthorityForLocation;
    }
}
