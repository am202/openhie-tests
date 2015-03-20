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
import org.regenstrief.hl7.datatype.HL7DataType;
import org.regenstrief.util.Util;

public abstract class AbstractDateData extends HL7DataType implements DateData {
    
    protected String value = null;
    
    /**
     * Constructs an empty AbstractDateData
     * 
     * @param prop the HL7Properties
     **/
    public AbstractDateData(final HL7Properties prop) {
        super(prop);
    }
    
    /**
     * Retrieves the value
     * 
     * @return the value
     **/
    @Override
    public String getValue() {
        return this.value;
    }
    
    /**
     * Modifies the value
     * 
     * @param value the new value
     **/
    @Override
    public void setValue(final String value) {
        this.value = value;
    }
    
    /**
     * Converts the DateData to a String
     * 
     * @return the DateData as a String
     **/
    @Override
    public String toDisplay() {
        return this.value;
    }
    
    /**
     * Retrieves the hash code
     * 
     * @return the hash code
     **/
    @Override
    public int hashCode() {
        return Util.hashCode(this.value);
    }
    
    /**
     * Retrieves whether the AbstractDateData equals the given object
     * 
     * @return whether the AbstractDateData equals the given object
     **/
    @Override
    public boolean equals(final Object o) {
        return o instanceof AbstractDateData ? this.value.equals(((AbstractDateData) o).value) : false;
    }
    
    /**
     * Retrieves a positive integer if this follows the given Object, 0 if they're equal, otherwise
     * a negative integer
     * 
     * @param o the Object to compare to this
     * @return a positive integer if this follows the given Object, 0 if they're equal, otherwise a
     *         negative integer
     **/
    @Override
    public int compareTo(final DateData o) {
        return this.value.compareTo(o.getValue());
    }
    
    /**
     * Clears the AbstractDateData
     **/
    @Override
    public void clear() {
        super.clear();
        this.value = null;
    }
    
    /**
     * Retrieves the time of a DateData
     * 
     * @param dd the DateData
     * @return the time
     **/
    public final static String getValue(final DateData dd) {
        return dd == null ? null : dd.getValue();
    }
    
    /**
     * Retrieves whether the DateData has a value
     * 
     * @param dd the DateData
     * @return whether it has a value
     */
    public final static boolean isValued(final DateData dd) {
        return Util.isValued(getValue(dd));
    }
}
