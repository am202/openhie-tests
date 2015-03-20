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

/**
 * <p>
 * Title: HL7CodeMappingException
 * </p>
 * <p>
 * Description: Exception that occurs in HL7 processor
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
public class HL7Exception extends Exception {
    
    static final long serialVersionUID = 2006916360030458993L;
    
    private final static String hl7 = "org.regenstrief.hl7.util.HL7Exception.";
    
    public final static String NULL_OBSERVATION_VALUE = hl7 + "null_observation_value";
    
    public final static String UNKNOWN_VALUE_TYPE = hl7 + "unknown_value_type";
    
    public final static String NO_HL7_PRODUCER = hl7 + "no_hl7_producer";
    
    public final static String NO_TARGET_INSTITUTION = hl7 + "no_target_institution";
    
    public final static String NULL_OBSERVATION_IDENTIFIER = hl7 + "null_observation_identifier";
    
    public final static String READ_FAILED = hl7 + "read_failed";
    
    public final static String INSERT_FAILED = hl7 + "insert_failed";
    
    public final static String UPDATE_FAILED = hl7 + "update_failed";
    
    public final static String CANNOT_INSERT_PATIENT = hl7 + "cannot_insert_patient";
    
    public final static String NAME_VERIFICATION_FAILED = hl7 + "name_verification_failed";
    
    public final static String BIRTHDAY_VERIFICATION_FAILED = hl7 + "birthday_verification_failed";
    
    public final static String GENDER_VERIFICATION_FAILED = hl7 + "gender_verification_failed";
    
    public final static String NULL_ORDER_NUMBER = hl7 + "null_order_number";
    
    public final static String BAD_END_TIME = hl7 + "bad_end_time";
    
    public final static String INVALID_FOR_GENDER = hl7 + "invalid_for_gender";
    
    public final static String INVALID_FOR_AGE = hl7 + "invalid_for_age";
    
    public final static String INVALID_UNITS = hl7 + "invalid_units";
    
    public final static String NOT_PHYSIOLOGICALLY_POSSIBLE = hl7 + "not_physiologically_possible";
    
    public final static String MISMATCHED_ORDER_NUMBER = hl7 + "mismatched_order_number";
    
    public final static String PARENT_REJECTED = hl7 + "parent_rejected";
    
    public final static String PROCESSING_ERROR = hl7 + "processing_error";
    
    public final static String FACTORY_ERROR = hl7 + "factory_error";
    
    public final static String USER_VALIDATE_ERROR = hl7 + "user_validate_error";
    
    public final static String OUTPUT_CREATE_ERROR = hl7 + "output_create_error";
    
    public final static String COMMIT_ERROR = hl7 + "commit_error";
    
    public final static String HL7_PROCESS_ERROR = hl7 + "hl7_process_error";
    
    public final static String FILE_WRITE_ERROR = hl7 + "file_write_error";
    
    public final static String NAME_REJECTED = hl7 + "name_rejected";
    
    public final static String NUMBER_REJECTED = hl7 + "number_rejected";
    
    public final static String ADDRESS_REJECTED = hl7 + "address_rejected";
    
    public final static String SPECIMEN_REJECTED = hl7 + "specimen_rejected";
    
    public final static String REPORT_REJECTED = hl7 + "report_rejected";
    
    public final static String PARTICIPATION_REJECTED = hl7 + "participation_rejected";
    
    public final static String NOTES_REJECTED = hl7 + "notes_rejected";
    
    public final static String NAME_CHANGE_FAILED = hl7 + "name_change_failed";
    
    /**
     * Constructs an empty HL7Exception
     **/
    public HL7Exception() {
        super();
    }
    
    /**
     * Constructs a new HL7Exception with the given String
     * 
     * @param s the String
     **/
    public HL7Exception(final String s) {
        super(s);
    }
    
    /**
     * Constructs a new HL7Exception with the given cause
     * 
     * @param e the cause
     **/
    public HL7Exception(final Throwable e) {
        super(e);
    }
    
    /**
     * Constructs a new HL7Exception with the given cause
     * 
     * @param e the cause
     * @return the HL7Exception
     **/
    public static HL7Exception create(final Throwable e) {
        final HL7Exception h = getHL7Exception(e);
        
        return h == null ? new HL7Exception(e) : h;
    }
    
    /**
     * Retrieves a HL7Exception from the given Throwable
     * 
     * @param e the Throwable
     * @return the HL7Exception
     **/
    public static HL7Exception getHL7Exception(final Throwable e) {
        if (e == null) {
            return null;
        }
        
        return e instanceof HL7Exception ? (HL7Exception) e : getHL7Exception(e.getCause());
    }
}
