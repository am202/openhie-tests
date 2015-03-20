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

/**
 * <p>
 * Title: AbstractNumericData
 * </p>
 * <p>
 * Description: Interface for HL7 data that represents a number
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: Regenstrief Institute
 * </p>
 * 
 * @author Andrew Martin
 * @version 1.0
 */
public abstract class AbstractNumericData extends HL7DataType implements NumericData {
    
    // If we expand to handle Integers for SI data types, we'll need to consider types like MO and CP too.
    
    /**
     * Constructs an empty AbstractNumericData
     * 
     * @param prop the HL7Properties
     **/
    public AbstractNumericData(final HL7Properties prop) {
        super(prop);
    }
    
    /**
     * Converts this into a double
     * 
     * @return this as a double
     **/
    @Override
    public double doubleValue() {
        return toDouble().doubleValue();
    }
    
    /**
     * Retrieves the display representation
     * 
     * @return the display representation
     **/
    @Override
    public String toDisplay() {
        return Util.doubleToString(doubleValue());
    }
    
    /**
     * Converts the NumericData to a Double
     * 
     * @param nd the NumericData
     * @return the NumericData as a Double
     **/
    public final static Double toDouble(final NumericData nd) {
        return nd == null ? null : nd.toDouble();
    }
}
