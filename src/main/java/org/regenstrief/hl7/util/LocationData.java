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

import org.regenstrief.hl7.datatype.HD;

/**
 * LocationData
 */
public interface LocationData extends BaseData {
    
    /**
     * Retrieves the point of care
     * 
     * @return the point of care
     **/
    public String getPointOfCare();
    
    /**
     * Retrieves the room
     * 
     * @return the room
     **/
    public String getRoom();
    
    /**
     * Retrieves the bed
     * 
     * @return the bed
     **/
    public String getBed();
    
    /**
     * Retrieves the facility
     * 
     * @return the facility
     **/
    public HD getFacility();
    
    /**
     * Retrieves the location status
     * 
     * @return the location status
     **/
    public String getLocationStatus();
    
    /**
     * Retrieves the location type
     * 
     * @return the location type
     **/
    public String getLocationType();
    
    /**
     * Retrieves the building
     * 
     * @return the building
     **/
    public String getBuilding();
    
    /**
     * Retrieves the floor
     * 
     * @return the floor
     **/
    public String getFloor();
    
    /**
     * Modifies the point of care
     * 
     * @param pointOfCare the new point of care
     **/
    public void setPointOfCare(final String pointOfCare);
    
    /**
     * Modifies the room
     * 
     * @param room the new room
     **/
    public void setRoom(final String room);
    
    /**
     * Modifies the bed
     * 
     * @param bed the new bed
     **/
    public void setBed(final String bed);
    
    /**
     * Modifies the facility
     * 
     * @param facility the new facility
     **/
    public void setFacility(final HD facility);
    
    /**
     * Modifies the location status
     * 
     * @param locationStatus the new location status
     **/
    public void setLocationStatus(final String locationStatus);
    
    /**
     * Modifies the location type
     * 
     * @param locationType the new location type
     **/
    public void setLocationType(final String locationType);
    
    /**
     * Modifies the building
     * 
     * @param building the new building
     **/
    public void setBuilding(final String building);
    
    /**
     * Modifies the floor
     * 
     * @param floor the new floor
     **/
    public void setFloor(final String floor);
}
