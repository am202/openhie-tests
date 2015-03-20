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
import java.util.List;

import org.regenstrief.hl7.HL7Delimiters;
import org.regenstrief.hl7.HL7Parser;
import org.regenstrief.hl7.HL7Properties;
import org.regenstrief.hl7.convert.Escaper;
import org.regenstrief.hl7.datatype.CE;
import org.regenstrief.hl7.datatype.EI;
import org.regenstrief.hl7.datatype.HD;
import org.regenstrief.hl7.datatype.MSG;
import org.regenstrief.hl7.datatype.NM;
import org.regenstrief.hl7.datatype.PT;
import org.regenstrief.hl7.datatype.TS;
import org.regenstrief.hl7.datatype.VID;
import org.regenstrief.hl7.datatype.XON;
import org.regenstrief.hl7.util.UtilHL7;
import org.regenstrief.util.Util;

/**
 * <p>
 * Title: MSH
 * </p>
 * <p>
 * Description: HL7 Message Header
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
public class MSH extends HL7Segment {
    
    public final static String MSH_XML = "MSH";
    
    public final static String FIELD_SEPARATOR_XML = "MSH.1";
    
    public final static String ENCODING_CHARACTERS_XML = "MSH.2";
    
    public final static String SENDING_APPLICATION_XML = "MSH.3";
    
    public final static String SENDING_FACILITY_XML = "MSH.4";
    
    public final static String RECEIVING_APPLICATION_XML = "MSH.5";
    
    public final static String RECEIVING_FACILITY_XML = "MSH.6";
    
    public final static String DATE_TIME_OF_MESSAGE_XML = "MSH.7";
    
    public final static String SECURITY_XML = "MSH.8";
    
    public final static String MESSAGE_TYPE_XML = "MSH.9";
    
    public final static String MESSAGE_CONTROL_ID_XML = "MSH.10";
    
    public final static String PROCESSING_ID_XML = "MSH.11";
    
    public final static String VERSION_ID_XML = "MSH.12";
    
    public final static String SEQUENCE_NUMBER_XML = "MSH.13";
    
    public final static String CONTINUATION_POINTER_XML = "MSH.14";
    
    public final static String ACCEPT_ACKNOWLEDGEMENT_TYPE_XML = "MSH.15";
    
    public final static String APPLICATION_ACKNOWLEDGEMENT_TYPE_XML = "MSH.16";
    
    public final static String COUNTRY_CODE_XML = "MSH.17";
    
    public final static String CHARACTER_SET_XML = "MSH.18";
    
    public final static String PRINCIPAL_LANGUAGE_OF_MESSAGE_XML = "MSH.19";
    
    public final static String ALTERNATE_CHARACTER_SET_HANDLING_SCHEME_XML = "MSH.20";
    
    public final static String CONFORMANCE_STATEMENT_ID_XML = "MSH.21";
    
    public final static String SENDING_RESPONSIBLE_ORGANIZATION_XML = "MSH.22";
    
    public final static String RECEIVING_RESPONSIBLE_ORGANIZATION_XML = "MSH.23";
    
    public final static String SENDING_NETWORK_ADDRESS_XML = "MSH.24";
    
    public final static String RECEIVING_NETWORK_ADDRESS_XML = "MSH.25";
    
    private String fieldSeparator = null;
    
    private String encodingCharacters = null;
    
    private HD sendingApplication = null; //hl7_producer.hl7_sending_application_code if MSH.4 is valued, hl7_producer.authority_name otherwise
    
    private HD sendingFacility = null; //hl7_producer.hl7_sending_facility_code
    
    private HD receivingApplication = null;
    
    private HD receivingFacility = null;
    
    private TS dateTimeOfMessage = null;
    
    private String security = null;
    
    private MSG messageType = null;
    
    private String messageControlID = null;
    
    private PT processingID = null;
    
    private VID versionID = null;
    
    private NM sequenceNumber = null;
    
    private String continuationPointer = null;
    
    private String acceptAcknowledgementType = null;
    
    private String applicationAcknowledgementType = null;
    
    private String countryCode = null;
    
    private List<String> characterSet = null;
    
    private CE principalLanguageOfMessage = null;
    
    private String alternateCharacterSetHandlingScheme = null;
    
    private List<EI> conformanceStatementID = null;
    
    private XON sendingResponsibleOrganization = null;
    
    private XON receivingResponsibleOrganization = null;
    
    private HD sendingNetworkAddress = null;
    
    private HD receivingNetworkAddress = null;
    
    //private static int messageControlIDCounter = 0;
    
    /**
     * Constructs an empty MSH
     * 
     * @param prop the HL7Properties
     **/
    public MSH(final HL7Properties prop) {
        super(prop);
    }
    
    /**
     * Adds missing required fields
     * 
     * @return the HL7Data with all required fields
     **/
    @Override
    public MSH addRequired() {
        if (Util.isEmpty(this.fieldSeparator)) {
            this.fieldSeparator = "|";
        }
        if (Util.isEmpty(this.encodingCharacters)) {
            this.encodingCharacters = "^~\\&";
        }
        if (this.dateTimeOfMessage == null) {
            this.dateTimeOfMessage = new TS(this.prop);
        }
        if (this.messageType == null) {
            this.messageType = new MSG(this.prop);
        }
        if (this.messageControlID == null) {
            this.messageControlID = newControlID();
        }
        if (this.processingID == null) {
            this.processingID = new PT(this.prop);
        }
        if (this.versionID == null) {
            this.versionID = new VID(this.prop);
        }
        
        return this;
    }
    
    /**
     * Constructs an MSH for a new message with the current timestamp and a unique id
     * 
     * @param prop the HL7Properties
     * @param msg the MSG
     * @return the outgoing MSH
     **/
    public final static MSH createOutgoingMSH(final HL7Properties prop, final MSG msg) {
        final MSH outgoing = new MSH(prop);
        
        outgoing.buildOutgoing(msg);
        
        return outgoing;
    }
    
    /**
     * Constructs an MSH for a new message with the current timestamp and a unique id
     * 
     * @param prop the HL7Properties
     * @param messageType the message type
     * @param eventCode the event code
     * @return the outgoing MSH
     **/
    public final static MSH createOutgoingMSH(final HL7Properties prop, final String messageType, final String eventCode) {
        final MSH outgoing = new MSH(prop);
        
        outgoing.buildOutgoing(messageType, eventCode);
        
        return outgoing;
    }
    
    /**
     * Constructs an MSH for a reply to the given MSH
     * 
     * @param msh the MSH to which the reply will be sent
     * @param messageType the message type
     * @param eventCode the event code
     * @return the reply MSH
     **/
    public final static MSH createReplyMSH(final MSH msh, final String messageType, final String eventCode) {
        final MSH reply = new MSH(msh.prop);
        
        reply.buildReply(msh, messageType, eventCode);
        
        return reply;
    }
    
    /**
     * Builds an outgoing MSH
     * 
     * @param msg the MSG
     **/
    private final void buildOutgoing(final MSG msg) {
        this.messageType = msg;
        setDateTimeOfMessage(new TS(this.prop, new java.util.Date())); // Set current date
        setVersionID(new VID(this.prop, UtilHL7.getVersion(this.prop)));
        setMessageControlID(newControlID());
        setProcessingID(new PT(this.prop, "P"));
        addRequired();
    }
    
    /**
     * Builds an outgoing MSH
     * 
     * @param messageType the message type
     * @param eventCode the event code
     **/
    private final void buildOutgoing(final String messageType, final String eventCode) {
        buildOutgoing(new MSG(this.prop, messageType, eventCode));
    }
    
    /**
     * Builds a reply to the given MSH into this MSH
     * 
     * @param msh the MSH to which the reply will be sent
     * @param messageType the message type
     * @param eventCode the event code
     **/
    private final void buildReply(final MSH msh, final String messageType, final String eventCode) {
        buildOutgoing(messageType, eventCode);
        setSendingApplication(msh.getReceivingApplication());
        setSendingFacility(msh.getReceivingFacility());
        setReceivingApplication(msh.getSendingApplication());
        setReceivingFacility(msh.getSendingFacility());
    }
    
    /*package*/final static String newControlID() {
        return Util.guid();
    }
    
    ///**	Retrieves the next ID, incrementing the counter
    //@return		the next ID
    //**/
    //public synchronized static int nextControlID()
    //{	return ++messageControlIDCounter;
    //}
    
    public static MSH parsePiped(final HL7Parser parser, final String line) {
        final MSH msh = new MSH(parser);
        msh.readPiped(parser, line);
        return msh;
    }
    
    @Override
    public void readPiped(final HL7Parser parser, final String line) {
        final char f = parser.getFieldSeparator();
        int start = line.indexOf(f) + 1;
        if (start <= 0) {
            return;
        }
        this.fieldSeparator = Character.toString(f);
        
        int stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.encodingCharacters = getRawToken(line, start, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        final char c = parser.getComponentSeparator();
        this.sendingApplication = HD.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.sendingFacility = HD.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.receivingApplication = HD.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.receivingFacility = HD.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.dateTimeOfMessage = TS.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.security = getToken(line, start, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.messageType = MSG.parsePiped(parser, line, start, c, stop);
        
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
        this.processingID = PT.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.versionID = VID.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.sequenceNumber = NM.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.continuationPointer = getToken(line, start, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.acceptAcknowledgementType = getToken(line, start, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.applicationAcknowledgementType = getToken(line, start, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.countryCode = getToken(line, start, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        final char r = parser.getRepetitionSeparator();
        while (start < stop) {
            final int next = getNext(line, start, r, stop);
            addCharacterSet(getToken(line, start, next));
            start = next + 1;
        }
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.principalLanguageOfMessage = CE.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.alternateCharacterSetHandlingScheme = getToken(line, start, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        while (start < stop) {
            final int next = getNext(line, start, r, stop);
            addConformanceStatementID(EI.parsePiped(parser, line, start, c, next));
            start = next + 1;
        }
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.sendingResponsibleOrganization = XON.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.receivingResponsibleOrganization = XON.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.sendingNetworkAddress = HD.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.receivingNetworkAddress = HD.parsePiped(parser, line, start, c, stop);
        
        assertLast(line, stop + 1, f, line.length());
    }
    
    protected static int writeDelimiters(final Writer w, final String fieldSeparator, final String encodingCharacters, final HL7Delimiters d, int last) throws IOException {
        //last = addField(w, fieldSeparator, last, 1); // Don't want this escaped
        if (fieldSeparator == null) {
            w.write(Escaper.getFieldSeparator(d));
        } else {
            w.write(fieldSeparator);
        }
        //int last = 1;
        //last = addField(w, encodingCharacters, last, 2); // Don't want this escaped
        if (encodingCharacters != null) {
            w.write(encodingCharacters);
        }
        return 2;
    }
    
    @Override
    public void toPiped(final Writer w) throws IOException {
        int last = startSegment(w, MSH_XML);
        
        last = writeDelimiters(w, this.fieldSeparator, this.encodingCharacters, getDelimiters(), last);
        last = addField(w, this.sendingApplication, last, 3);
        last = addField(w, this.sendingFacility, last, 4);
        last = addField(w, this.receivingApplication, last, 5);
        last = addField(w, this.receivingFacility, last, 6);
        last = addField(w, this.dateTimeOfMessage, last, 7);
        last = addField(w, this.security, last, 8);
        last = addField(w, this.messageType, last, 9);
        last = addField(w, this.messageControlID, last, 10);
        last = addField(w, this.processingID, last, 11);
        last = addField(w, this.versionID, last, 12);
        last = addField(w, this.sequenceNumber, last, 13);
        last = addField(w, this.continuationPointer, last, 14);
        last = addField(w, this.acceptAcknowledgementType, last, 15);
        last = addField(w, this.applicationAcknowledgementType, last, 16);
        last = addField(w, this.countryCode, last, 17);
        last = addField(w, this.characterSet, last, 18);
        last = addField(w, this.principalLanguageOfMessage, last, 19);
        last = addField(w, this.alternateCharacterSetHandlingScheme, last, 20);
        last = addField(w, this.conformanceStatementID, last, 21);
        last = addField(w, this.sendingResponsibleOrganization, last, 22);
        last = addField(w, this.receivingResponsibleOrganization, last, 23);
        last = addField(w, this.sendingNetworkAddress, last, 24);
        last = addField(w, this.receivingNetworkAddress, last, 25);
        
        endSegment(w);
    }
    
    /**
     * Retrieves the accept acknowledgement type
     * 
     * @return the accept acknowledgement type
     **/
    public String getAcceptAcknowledgementType() {
        return this.acceptAcknowledgementType;
    }
    
    /**
     * Retrieves the version ID
     * 
     * @return the version ID
     **/
    public VID getVersionID() {
        return this.versionID;
    }
    
    /**
     * Retrieves the sequence number
     * 
     * @return the sequence number
     **/
    public NM getSequenceNumber() {
        return this.sequenceNumber;
    }
    
    /**
     * Retrieves the sending facility
     * 
     * @return the sending facility
     **/
    public HD getSendingFacility() {
        return this.sendingFacility;
    }
    
    /**
     * Retrieves the sending application
     * 
     * @return the sending application
     **/
    public HD getSendingApplication() {
        return this.sendingApplication;
    }
    
    /**
     * Retrieves the security
     * 
     * @return the security
     **/
    public String getSecurity() {
        return this.security;
    }
    
    /**
     * Retrieves the receiving facility
     * 
     * @return the receiving facility
     **/
    public HD getReceivingFacility() {
        return this.receivingFacility;
    }
    
    /**
     * Retrieves the receiving application
     * 
     * @return the receiving application
     **/
    public HD getReceivingApplication() {
        return this.receivingApplication;
    }
    
    /**
     * Retrieves the processing ID
     * 
     * @return the processing ID
     **/
    public PT getProcessingID() {
        return this.processingID;
    }
    
    /**
     * Retrieves the principal language of message
     * 
     * @return the principal language of message
     **/
    public CE getPrincipalLanguageOfMessage() {
        return this.principalLanguageOfMessage;
    }
    
    /**
     * Retrieves the message type
     * 
     * @return the message type
     **/
    public MSG getMessageType() {
        return this.messageType;
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
     * Retrieves the field separator
     * 
     * @return the field separator
     **/
    public String getFieldSeparator() {
        return this.fieldSeparator;
    }
    
    /**
     * Retrieves the encoding characters
     * 
     * @return the encoding characters
     **/
    public String getEncodingCharacters() {
        return this.encodingCharacters;
    }
    
    /**
     * Retrieves the date/time of message
     * 
     * @return the date/time of message
     **/
    public TS getDateTimeOfMessage() {
        return this.dateTimeOfMessage;
    }
    
    /**
     * Retrieves the country code
     * 
     * @return the country code
     **/
    public String getCountryCode() {
        return this.countryCode;
    }
    
    /**
     * Retrieves the continuation pointer
     * 
     * @return the continuation pointer
     **/
    public String getContinuationPointer() {
        return this.continuationPointer;
    }
    
    /**
     * Retrieves the character set
     * 
     * @return the character set
     **/
    public List<String> getCharacterSet() {
        return this.characterSet;
    }
    
    /**
     * Retrieves the application acknowledgement type
     * 
     * @return the application acknowledgement type
     **/
    public String getApplicationAcknowledgementType() {
        return this.applicationAcknowledgementType;
    }
    
    /**
     * Retrieves the alternate character set handling scheme
     * 
     * @return the alternate character set handling scheme
     **/
    public String getAlternateCharacterSetHandlingScheme() {
        return this.alternateCharacterSetHandlingScheme;
    }
    
    /**
     * Retrieves the conformance statement ID
     * 
     * @return the conformance statement ID
     **/
    public List<EI> getConformanceStatementID() {
        return this.conformanceStatementID;
    }
    
    /**
     * Retrieves the sending responsible organization
     * 
     * @return the sending responsible organization
     **/
    public XON getSendingResponsibleOrganization() {
        return this.sendingResponsibleOrganization;
    }
    
    /**
     * Retrieves the receiving responsible organization
     * 
     * @return the receiving responsible organization
     **/
    public XON getReceivingResponsibleOrganization() {
        return this.receivingResponsibleOrganization;
    }
    
    /**
     * Retrieves the sending network address
     * 
     * @return the sending network address
     **/
    public HD getSendingNetworkAddress() {
        return this.sendingNetworkAddress;
    }
    
    /**
     * Retrieves the receiving network address
     * 
     * @return the receiving network address
     **/
    public HD getReceivingNetworkAddress() {
        return this.receivingNetworkAddress;
    }
    
    /**
     * Modifies the accept acknowledgement type
     * 
     * @param acceptAcknowledgementType the new accept acknowledgement type
     **/
    public void setAcceptAcknowledgementType(final String acceptAcknowledgementType) {
        this.acceptAcknowledgementType = acceptAcknowledgementType;
    }
    
    /**
     * Modifies the version ID
     * 
     * @param versionID the new version ID
     **/
    public void setVersionID(final VID versionID) {
        this.versionID = versionID;
    }
    
    /**
     * Modifies the sequence number
     * 
     * @param sequenceNumber the new sequence number
     **/
    public void setSequenceNumber(final NM sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }
    
    /**
     * Modifies the sending facility
     * 
     * @param sendingFacility the new sending facility
     **/
    public void setSendingFacility(final HD sendingFacility) {
        this.sendingFacility = sendingFacility;
    }
    
    /**
     * Modifies the sending application
     * 
     * @param sendingApplication the new sending application
     **/
    public void setSendingApplication(final HD sendingApplication) {
        this.sendingApplication = sendingApplication;
    }
    
    /**
     * Modifies the security
     * 
     * @param security the new security
     **/
    public void setSecurity(final String security) {
        this.security = security;
    }
    
    /**
     * Modifies the receiving facility
     * 
     * @param receivingFacility the new receiving facility
     **/
    public void setReceivingFacility(final HD receivingFacility) {
        this.receivingFacility = receivingFacility;
    }
    
    /**
     * Modifies the receiving application
     * 
     * @param receivingApplication the new receiving application
     **/
    public void setReceivingApplication(final HD receivingApplication) {
        this.receivingApplication = receivingApplication;
    }
    
    /**
     * Modifies the processing ID
     * 
     * @param processingID the new processing ID
     **/
    public void setProcessingID(final PT processingID) {
        this.processingID = processingID;
    }
    
    /**
     * Modifies the principal language of message
     * 
     * @param principalLanguageOfMessage the new principal language of message
     **/
    public void setPrincipalLanguageOfMessage(final CE principalLanguageOfMessage) {
        this.principalLanguageOfMessage = principalLanguageOfMessage;
    }
    
    /**
     * Modifies the message type
     * 
     * @param messageType the new message type
     **/
    public void setMessageType(final MSG messageType) {
        this.messageType = messageType;
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
     * Modifies the field separator
     * 
     * @param fieldSeparator the new field separator
     **/
    public void setFieldSeparator(final String fieldSeparator) {
        this.fieldSeparator = fieldSeparator;
    }
    
    /**
     * Modifies the encoding characters
     * 
     * @param encodingCharacters the new encoding characters
     **/
    public void setEncodingCharacters(final String encodingCharacters) {
        this.encodingCharacters = encodingCharacters;
    }
    
    /**
     * Modifies the date/time of message
     * 
     * @param dateTimeOfMessage the new date/time of message
     **/
    public void setDateTimeOfMessage(final TS dateTimeOfMessage) {
        this.dateTimeOfMessage = dateTimeOfMessage;
    }
    
    /**
     * Modifies the country code
     * 
     * @param countryCode the new country code
     **/
    public void setCountryCode(final String countryCode) {
        this.countryCode = countryCode;
    }
    
    /**
     * Modifies the continuation pointer
     * 
     * @param continuationPointer the new continuation pointer
     **/
    public void setContinuationPointer(final String continuationPointer) {
        this.continuationPointer = continuationPointer;
    }
    
    /**
     * Modifies the character set list
     * 
     * @param characterSet the new character set list
     **/
    public void setCharacterSet(final List<String> characterSet) {
        this.characterSet = characterSet;
    }
    
    /**
     * Modifies the character set list
     * 
     * @param characterSet the new character set
     **/
    public void addCharacterSet(final String characterSet) {
        this.characterSet = Util.add(this.characterSet, characterSet);
    }
    
    /**
     * Modifies the conformance statement ID list
     * 
     * @param conformanceStatementID the new conformance statement ID list
     **/
    public void setConformanceStatementID(final List<EI> conformanceStatementID) {
        this.conformanceStatementID = conformanceStatementID;
    }
    
    /**
     * Modifies the conformance statement ID list
     * 
     * @param conformanceStatementID the new conformance statement ID
     **/
    public void addConformanceStatementID(final EI conformanceStatementID) {
        this.conformanceStatementID = Util.add(this.conformanceStatementID, conformanceStatementID);
    }
    
    /**
     * Modifies the application acknowledgement type
     * 
     * @param applicationAcknowledgementType the new application acknowledgement type
     **/
    public void setApplicationAcknowledgementType(final String applicationAcknowledgementType) {
        this.applicationAcknowledgementType = applicationAcknowledgementType;
    }
    
    /**
     * Modifies the alternate character set handling scheme
     * 
     * @param alternateCharacterSetHandlingScheme the new alternate character set handling scheme
     **/
    public void setAlternateCharacterSetHandlingScheme(final String alternateCharacterSetHandlingScheme) {
        this.alternateCharacterSetHandlingScheme = alternateCharacterSetHandlingScheme;
    }
    
    /**
     * Modifies the sending responsible organization
     * 
     * @param sendingResponsibleOrganization the new sending responsible organization
     **/
    public void setSendingResponsibleOrganization(final XON sendingResponsibleOrganization) {
        this.sendingResponsibleOrganization = sendingResponsibleOrganization;
    }
    
    /**
     * Modifies the receiving responsible organization
     * 
     * @param receivingResponsibleOrganization the new receiving responsible organization
     **/
    public void setReceivingResponsibleOrganization(final XON receivingResponsibleOrganization) {
        this.receivingResponsibleOrganization = receivingResponsibleOrganization;
    }
    
    /**
     * Modifies the sending network address
     * 
     * @param sendingNetworkAddress the new sending network address
     **/
    public void setSendingNetworkAddress(final HD sendingNetworkAddress) {
        this.sendingNetworkAddress = sendingNetworkAddress;
    }
    
    /**
     * Modifies the receiving network address
     * 
     * @param receivingNetworkAddress the new receiving network address
     **/
    public void setReceivingNetworkAddress(final HD receivingNetworkAddress) {
        this.receivingNetworkAddress = receivingNetworkAddress;
    }
    
    public final static String getMessageCode(final MSH msh) {
        return msh == null ? null : MSG.getMessageCode(msh.getMessageType());
    }
    
    public final static String getTriggerEvent(final MSH msh) {
        return msh == null ? null : MSG.getTriggerEvent(msh.getMessageType());
    }
}
