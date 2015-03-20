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

import org.regenstrief.hl7.HL7Parser;
import org.regenstrief.hl7.HL7Properties;
import org.regenstrief.hl7.util.AbstractConceptData;

/**
 * <p>
 * Title: CF
 * </p>
 * <p>
 * Description: HL7 Coded Element With Formatted Values
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
public class CF extends AbstractConceptData {
    
    public final static String CF_XML = "CF";
    
    public final static String IDENTIFIER_XML = "CF.1";
    
    public final static String FORMATTED_TEXT_XML = "CF.2";
    
    public final static String NAME_OF_CODING_SYSTEM_XML = "CF.3";
    
    public final static String ALTERNATE_IDENTIFIER_XML = "CF.4";
    
    public final static String ALTERNATE_FORMATTED_TEXT_XML = "CF.5";
    
    public final static String NAME_OF_ALTERNATIVE_CODING_SYSTEM_XML = "CF.6";
    
    public final static String CODING_SYSTEM_VERSION_ID_XML = "CF.7";
    
    public final static String ALTERNATE_CODING_SYSTEM_VERSION_ID_XML = "CF.8";
    
    public final static String ORIGINAL_TEXT_XML = "CF.9";
    
    public final static String SECOND_ALTERNATE_IDENTIFIER_XML = "CF.10";
    
    public final static String SECOND_ALTERNATE_TEXT_XML = "CF.11";
    
    public final static String SECOND_NAME_OF_ALTERNATE_CODING_SYSTEM_XML = "CF.12";
    
    public final static String SECOND_ALTERNATE_CODING_SYSTEM_VERSION_ID_XML = "CF.13";
    
    public final static String CODING_SYSTEM_OID_XML = "CF.14";
    
    public final static String VALUE_SET_OID_XML = "CF.15";
    
    public final static String VALUE_SET_VERSION_ID_XML = "CF.16";
    
    public final static String ALTERNATE_CODING_SYSTEM_OID_XML = "CF.17";
    
    public final static String ALTERNATE_VALUE_SET_OID_XML = "CF.18";
    
    public final static String ALTERNATE_VALUE_SET_VERSION_ID_XML = "CF.19";
    
    public final static String SECOND_ALTERNATE_CODING_SYSTEM_OID_XML = "CF.20";
    
    public final static String SECOND_ALTERNATE_VALUE_SET_OID_XML = "CF.21";
    
    public final static String SECOND_ALTERNATE_VALUE_SET_VERSION_ID_XML = "CF.22";
    
    /**
     * Constructs an empty CF
     * 
     * @param prop the HL7Properties
     **/
    public CF(final HL7Properties prop) {
        super(prop);
    }
    
    public static CF parsePiped(final HL7Parser parser, final String line, final int start, final char delim, final int stop) {
        if (stop <= start) {
            return null;
        }
        final CF cf = new CF(parser);
        cf.readPiped(parser, line, start, delim, stop);
        return cf;
    }
}
