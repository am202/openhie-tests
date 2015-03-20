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

public class HL7ParseException extends RuntimeException {
    
    private static final long serialVersionUID = 7154271601278477690L;
    
    public HL7ParseException(final String message) {
        super(message);
    }
    
    private HL7ParseException(final Throwable cause) {
        super(cause);
    }
    
    public final static HL7ParseException toHL7ParseException(final Throwable cause) {
        return cause instanceof HL7ParseException ? (HL7ParseException) cause : new HL7ParseException(cause);
    }
}
