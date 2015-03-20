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
package org.regenstrief.hl7.util;

import org.regenstrief.hl7.HL7Properties;
import org.regenstrief.hl7.datatype.HD;
import org.regenstrief.hl7.datatype.HL7DataType;

/**
 * AbstractLocationData
 */
public abstract class AbstractLocationData extends HL7DataType implements LocationData {
    
    protected String pointOfCare = null;
    
    protected String room = null;
    
    protected String bed = null;
    
    protected HD facility = null;
    
    protected String locationStatus = null;
    
    protected String locationType = null;
    
    protected String building = null;
    
    protected String floor = null;
    
    /**
     * Constructs an empty AbstractLocationData
     * 
     * @param prop the HL7Properties
     **/
    public AbstractLocationData(final HL7Properties prop) {
        super(prop);
    }
    
    /**
     * Retrieves the point of care
     * 
     * @return the point of care
     **/
    @Override
    public String getPointOfCare() {
        return this.pointOfCare;
    }
    
    /**
     * Retrieves the room
     * 
     * @return the room
     **/
    @Override
    public String getRoom() {
        return this.room;
    }
    
    /**
     * Retrieves the bed
     * 
     * @return the bed
     **/
    @Override
    public String getBed() {
        return this.bed;
    }
    
    /**
     * Retrieves the facility
     * 
     * @return the facility
     **/
    @Override
    public HD getFacility() {
        return this.facility;
    }
    
    /**
     * Retrieves the location status
     * 
     * @return the location status
     **/
    @Override
    public String getLocationStatus() {
        return this.locationStatus;
    }
    
    /**
     * Retrieves the location type
     * 
     * @return the location type
     **/
    @Override
    public String getLocationType() {
        return this.locationType;
    }
    
    /**
     * Retrieves the building
     * 
     * @return the building
     **/
    @Override
    public String getBuilding() {
        return this.building;
    }
    
    /**
     * Retrieves the floor
     * 
     * @return the floor
     **/
    @Override
    public String getFloor() {
        return this.floor;
    }
    
    /**
     * Modifies the point of care
     * 
     * @param pointOfCare the new point of care
     **/
    @Override
    public void setPointOfCare(final String pointOfCare) {
        this.pointOfCare = pointOfCare;
    }
    
    /**
     * Modifies the room
     * 
     * @param room the new room
     **/
    @Override
    public void setRoom(final String room) {
        this.room = room;
    }
    
    /**
     * Modifies the bed
     * 
     * @param bed the new bed
     **/
    @Override
    public void setBed(final String bed) {
        this.bed = bed;
    }
    
    /**
     * Modifies the facility
     * 
     * @param facility the new facility
     **/
    @Override
    public void setFacility(final HD facility) {
        this.facility = facility;
    }
    
    /**
     * Modifies the location status
     * 
     * @param locationStatus the new location status
     **/
    @Override
    public void setLocationStatus(final String locationStatus) {
        this.locationStatus = locationStatus;
    }
    
    /**
     * Modifies the location type
     * 
     * @param locationType the new location type
     **/
    @Override
    public void setLocationType(final String locationType) {
        this.locationType = locationType;
    }
    
    /**
     * Modifies the building
     * 
     * @param building the new building
     **/
    @Override
    public void setBuilding(final String building) {
        this.building = building;
    }
    
    /**
     * Modifies the floor
     * 
     * @param floor the new floor
     **/
    @Override
    public void setFloor(final String floor) {
        this.floor = floor;
    }
}
