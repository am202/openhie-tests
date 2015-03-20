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
package org.regenstrief.hl7.segment;

import java.io.IOException;
import java.io.Writer;

import org.regenstrief.hl7.HL7Parser;
import org.regenstrief.hl7.HL7Properties;
import org.regenstrief.hl7.datatype.CE;
import org.regenstrief.hl7.datatype.NM;
import org.regenstrief.util.Util;

/**
 * <p>
 * Title: MSA
 * </p>
 * <p>
 * Description: HL7 Message Acknowledgment Segment
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
public class MSA extends HL7Segment {
    
    public final static String MSA_XML = "MSA";
    
    public final static String ACKNOWLEDGMENT_CODE_XML = "MSA.1";
    
    public final static String MESSAGE_CONTROL_ID_XML = "MSA.2";
    
    public final static String TEXT_MESSAGE_XML = "MSA.3";
    
    public final static String EXPECTED_SEQUENCE_NUMBER_XML = "MSA.4";
    
    public final static String DELAYED_ACKNOWLEDGMENT_TYPE_XML = "MSA.5";
    
    public final static String ERROR_CONDITION_XML = "MSA.6";
    
    public final static String APPLICATION_ACCEPT = "AA";
    
    public final static String APPLICATION_ERROR = "AE";
    
    public final static String APPLICATION_REJECT = "AR";
    
    public final static String COMMIT_ACCEPT = "CA";
    
    public final static String COMMIT_ERROR = "CE";
    
    public final static String COMMIT_REJECT = "CR";
    
    private String acknowledgmentCode = null;
    
    private String messageControlID = null;
    
    private String textMessage = null;
    
    private NM expectedSequenceNumber = null;
    
    private String delayedAcknowledgmentType = null;
    
    private CE errorCondition = null;
    
    /**
     * Constructs an empty MSA
     * 
     * @param prop the HL7Properties
     **/
    public MSA(final HL7Properties prop) {
        super(prop);
    }
    
    /**
     * Adds missing required fields
     * 
     * @return the HL7Data with all required fields
     **/
    @Override
    public MSA addRequired() {
        if (Util.isEmpty(this.acknowledgmentCode)) {
            this.acknowledgmentCode = APPLICATION_ACCEPT;
        }
        if (this.messageControlID == null) {
            this.messageControlID = String.valueOf(MSH.newControlID());
        }
        
        return this;
    }
    
    /**
     * Constructs an MSA for a reply to the given MSH
     * 
     * @param msh the MSH to which the reply will be sent
     * @param acknowledgmentCode the acknowledgment code
     * @return the reply MSA
     **/
    public static MSA createReplyMSA(final MSH msh, final String acknowledgmentCode) {
        final MSA msa = new MSA(msh.getProp());
        
        msa.setMessageControlID(msh.getMessageControlID());
        msa.setAcknowledgmentCode(acknowledgmentCode);
        
        return msa;
    }
    
    public static MSA parsePiped(final HL7Parser parser, final String line) {
        final MSA msa = new MSA(parser);
        msa.readPiped(parser, line);
        return msa;
    }
    
    @Override
    public void readPiped(final HL7Parser parser, final String line) {
        final char f = parser.getFieldSeparator();
        int start = line.indexOf(f) + 1;
        if (start <= 0) {
            return;
        }
        int stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.acknowledgmentCode = getToken(line, start, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.messageControlID = getToken(line, start, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.textMessage = getToken(line, start, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        final char c = parser.getComponentSeparator();
        this.expectedSequenceNumber = NM.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.delayedAcknowledgmentType = getToken(line, start, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.errorCondition = CE.parsePiped(parser, line, start, c, stop);
        
        assertLast(line, stop + 1, f, line.length());
    }
    
    @Override
    public void toPiped(final Writer w) throws IOException {
        int last = startSegment(w, MSA_XML);
        
        last = addField(w, this.acknowledgmentCode, last, 1);
        last = addField(w, this.messageControlID, last, 2);
        last = addField(w, this.textMessage, last, 3);
        last = addField(w, this.expectedSequenceNumber, last, 4);
        last = addField(w, this.delayedAcknowledgmentType, last, 5);
        last = addField(w, this.errorCondition, last, 6);
        
        endSegment(w);
    }
    
    /**
     * Retrieves the acknowledgment code
     * 
     * @return the acknowledgment code
     **/
    public String getAcknowledgmentCode() {
        return this.acknowledgmentCode;
    }
    
    /**
     * Retrieves the message control ID
     * 
     * @return the message control ID
     **/
    public String getMessageControlID() {
        return this.messageControlID;
    }
    
    /**
     * Retrieves the text message
     * 
     * @return the text message
     **/
    public String getTextMessage() {
        return this.textMessage;
    }
    
    /**
     * Retrieves the expected sequence number
     * 
     * @return the expected sequence number
     **/
    public NM getExpectedSequenceNumber() {
        return this.expectedSequenceNumber;
    }
    
    /**
     * Retrieves the delayed acknowledgment type
     * 
     * @return the delayed acknowledgment type
     **/
    public String getDelayedAcknowledgmentType() {
        return this.delayedAcknowledgmentType;
    }
    
    /**
     * Retrieves the error condition
     * 
     * @return the error condition
     **/
    public CE getErrorCondition() {
        return this.errorCondition;
    }
    
    /**
     * Modifies the acknowledgment code
     * 
     * @param acknowledgmentCode the new acknowledgment code
     **/
    public void setAcknowledgmentCode(final String acknowledgmentCode) {
        this.acknowledgmentCode = acknowledgmentCode;
    }
    
    /**
     * Modifies the message control ID
     * 
     * @param messageControlID the new message control ID
     **/
    public void setMessageControlID(final String messageControlID) {
        this.messageControlID = messageControlID;
    }
    
    /**
     * Modifies the text message
     * 
     * @param textMessage the new text message
     **/
    public void setTextMessage(final String textMessage) {
        this.textMessage = textMessage;
    }
    
    /**
     * Modifies the expected sequence number
     * 
     * @param expectedSequenceNumber the new expected sequence number
     **/
    public void setExpectedSequenceNumber(final NM expectedSequenceNumber) {
        this.expectedSequenceNumber = expectedSequenceNumber;
    }
    
    /**
     * Modifies the delayed acknowledgment type
     * 
     * @param delayedAcknowledgmentType the new delayed acknowledgment type
     **/
    public void setDelayedAcknowledgmentType(final String delayedAcknowledgmentType) {
        this.delayedAcknowledgmentType = delayedAcknowledgmentType;
    }
    
    /**
     * Modifies the error condition
     * 
     * @param errorCondition the new error condition
     **/
    public void setErrorCondition(final CE errorCondition) {
        this.errorCondition = errorCondition;
    }
}
