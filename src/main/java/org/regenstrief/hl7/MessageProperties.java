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
package org.regenstrief.hl7;

import java.util.Date;

import org.regenstrief.hl7.convert.OutputUtil;
import org.regenstrief.hl7.datatype.TS;

/**
 * <p>
 * Title: MessageProperties
 * </p>
 * <p>
 * Description: Message Properties
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: Regenstrief Institute
 * </p>
 * 
 * @author Andrew Martin
 * @version 1.0
 */
public class MessageProperties {
    
    private TS observationDateTime = null;
    
    private Date physiologicTime = null;
    
    private String version = null;
    
    private boolean limitedOutput = false;
    
    /**
     * Modifies the version
     * 
     * @param version the new version
     **/
    public final void setVersion(final String version) {
        this.version = version;
    }
    
    /**
     * Retrieves the version
     * 
     * @return the version
     **/
    public final String getVersion() {
        if (this.version == null) {
            this.version = System.getProperty(HL7Parser.PROP_VERSION);
            if (this.version == null) {
                this.version = OutputUtil.V2_3_1;
            }
        }
        
        return this.version;
    }
    
    /**
     * Modifies the physiologic time
     * 
     * @param physiologicTime the new physiologic time
     **/
    public void setPhysiologicTime(final Date physiologicTime) {
        this.physiologicTime = physiologicTime;
    }
    
    /**
     * Modifies the observation date time
     * 
     * @param observationDateTime the new observation date time
     **/
    public void setObservationDateTime(final TS observationDateTime) {
        this.observationDateTime = observationDateTime;
    }
    
    /**
     * Retrieves the current physiologic time
     * 
     * @return the physiologic time Date
     **/
    public Date getPhysiologicTime() {
        return this.physiologicTime;
    }
    
    /**
     * Retrieves the observation date/time
     * 
     * @return the observation date/time String
     **/
    public TS getObservationDateTime() {
        return this.observationDateTime;
    }
    
    /**
     * Retrieves whether the output should be limited
     * 
     * @return whether the output should be limited
     **/
    public boolean isLimitedOutput() {
        return this.limitedOutput;
    }
    
    /**
     * Stores whether the output should be limited
     * 
     * @param limitedOutput whether the output should be limited
     **/
    public void setLimitedOutput(final boolean limitedOutput) {
        this.limitedOutput = limitedOutput;
    }
    
    /**
     * This temporary method is called in areas of the program that haven't been implemented yet.
     * This used to be in MessageReport, but that introduced a dependency on the DataImport project.
     * This dependency is unnecessary for some classes. MessageProperties doesn't have that
     * dependency. It also doesn't have subclasses, so it won't get in the way as an available
     * method like it would in a place like HL7Data.
     **/
    public static void todo() { //log.error("This feature needs to be done");
    }
}
