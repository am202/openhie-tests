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

/**
 * <p>
 * Title: MSG
 * </p>
 * <p>
 * Description: HL7 Message Type
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
public class MSG extends HL7DataType {
    
    public final static String MSG_XML = "MSG";
    
    public final static String MESSAGE_CODE_XML = "MSG.1";
    
    public final static String TRIGGER_EVENT_XML = "MSG.2";
    
    public final static String MESSAGE_STRUCTURE_XML = "MSG.3";
    
    public final static String EVENT_A11 = "A11"; // ADT/ACK - Cancel admit/visit notification
    
    public final static String EVENT_A13 = "A13"; // ADT/ACK - Cancel discharge/end visit
    
    public final static String EVENT_A18 = "A18"; // ADT/ACK - Merge patient information
    
    public final static String EVENT_A34 = "A34"; // ADT/ACK - Merge patient information - patient ID only
    
    public final static String EVENT_A35 = "A35"; // ADT/ACK - Merge patient information - account number only
    
    public final static String EVENT_A36 = "A36"; // ADT/ACK - Merge patient information - patient ID and account number
    
    public final static String EVENT_A37 = "A37"; // ADT/ACK - Unlink patient information A38 ADT/ACK - Cancel pre-admit
    
    public final static String EVENT_A40 = "A40"; // ADT/ACK - Merge patient - patient identifier list
    
    public final static String EVENT_A41 = "A41"; // ADT/ACK - Merge account - patient account number
    
    public final static String EVENT_A45 = "A45"; // ADT/ACK - Move visit information - visit number
    
    private String messageCode = null;
    
    private String triggerEvent = null;
    
    private String messageStructure = null;
    
    /**
     * Constructs an empty MSG
     * 
     * @param prop the HL7Properties
     **/
    public MSG(final HL7Properties prop) {
        super(prop);
    }
    
    /**
     * Constructs an MSG with the given message code and trigger event
     * 
     * @param prop the HL7Properties
     * @param messageCode the message code
     * @param triggerEvent the trigger event
     **/
    public MSG(final HL7Properties prop, final String messageCode, final String triggerEvent) {
        this(prop);
        this.messageCode = messageCode;
        this.triggerEvent = triggerEvent;
    }
    
    /**
     * Constructs an MSG with the given message code and trigger event
     * 
     * @param prop the HL7Properties
     * @param messageCode the message code
     * @param triggerEvent the trigger event
     * @param messageStructure the message structure
     **/
    public MSG(final HL7Properties prop, final String messageCode, final String triggerEvent, final String messageStructure) {
        this(prop, messageCode, triggerEvent);
        this.messageStructure = messageStructure;
    }
    
    public static MSG parsePiped(final HL7Parser parser, final String line, final int start, final char delim, final int stop) {
        if (stop <= start) {
            return null;
        }
        final MSG msg = new MSG(parser);
        msg.readPiped(parser, line, start, delim, stop);
        return msg;
    }
    
    @Override
    public void readPiped(final HL7Parser parser, final String line, int start, final char delim, final int stop) {
        int next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.messageCode = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.triggerEvent = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.messageStructure = getToken(line, start, next);
        
        assertLast(line, next + 1, delim, stop);
    }
    
    @Override
    public void toPiped(final Writer w, final int level) throws IOException {
        int last = 1;
        
        last = addComponent(w, this.messageCode, last, 1, level);
        last = addComponent(w, this.triggerEvent, last, 2, level);
        last = addComponent(w, this.messageStructure, last, 3, level);
    }
    
    /**
     * Retrieves the message code
     * 
     * @return the message code
     **/
    public String getMessageCode() {
        return this.messageCode;
    }
    
    /**
     * Retrieves the trigger event
     * 
     * @return the trigger event
     **/
    public String getTriggerEvent() {
        return this.triggerEvent;
    }
    
    /**
     * Retrieves the message structure
     * 
     * @return the message structure
     **/
    public String getMessageStructure() {
        return this.messageStructure;
    }
    
    /**
     * Modifies the message code
     * 
     * @param messageCode the new message code
     **/
    public void setMessageCode(final String messageCode) {
        this.messageCode = messageCode;
    }
    
    /**
     * Modifies the trigger event
     * 
     * @param triggerEvent the new trigger event
     **/
    public void setTriggerEvent(final String triggerEvent) {
        this.triggerEvent = triggerEvent;
    }
    
    /**
     * Modifies the message structure
     * 
     * @param messageStructure the new message structure
     **/
    public void setMessageStructure(final String messageStructure) {
        this.messageStructure = messageStructure;
    }
    
    public final static String getMessageCode(final MSG msg) {
        return msg == null ? null : msg.getMessageCode();
    }
    
    public final static String getTriggerEvent(final MSG msg) {
        return msg == null ? null : msg.getTriggerEvent();
    }
}
