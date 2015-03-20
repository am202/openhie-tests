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

/**
 * <p>
 * Title: QAK
 * </p>
 * <p>
 * Description: HL7 Query Acknowledgment Segment
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
public class QAK extends HL7Segment {
    
    public final static String QAK_XML = "QAK";
    
    public final static String QUERY_TAG_XML = "QAK.1";
    
    public final static String QUERY_RESPONSE_STATUS_XML = "QAK.2";
    
    public final static String MESSAGE_QUERY_NAME_XML = "QAK.3";
    
    public final static String HIT_COUNT_XML = "QAK.4";
    
    public final static String THIS_PAYLOAD_XML = "QAK.5";
    
    public final static String HITS_REMAINING_XML = "QAK.6";
    
    private String queryTag = null;
    
    private String queryResponseStatus = null;
    
    private CE messageQueryName = null;
    
    private NM hitCount = null;
    
    private NM thisPayload = null;
    
    private NM hitsRemaining = null;
    
    /**
     * Constructs an empty QAK
     * 
     * @param prop the HL7Properties
     **/
    public QAK(final HL7Properties prop) {
        super(prop);
    }
    
    /**
     * Constructs a QAK for a reply to the given QPD
     * 
     * @param qpd the QPD to which the reply will be sent
     * @return the reply QAK
     **/
    public static QAK createReplyQAK(final QPD qpd) {
        if (qpd == null) {
            return null;
        }
        
        final QAK qak = new QAK(qpd.getProp());
        qak.setQueryTag(qpd.getQueryTag());
        
        return qak;
    }
    
    public static QAK parsePiped(final HL7Parser parser, final String line) {
        final QAK qak = new QAK(parser);
        qak.readPiped(parser, line);
        return qak;
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
        this.queryTag = getToken(line, start, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.queryResponseStatus = getToken(line, start, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        final char c = parser.getComponentSeparator();
        this.messageQueryName = CE.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.hitCount = NM.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.thisPayload = NM.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.hitsRemaining = NM.parsePiped(parser, line, start, c, stop);
        
        assertLast(line, stop + 1, f, line.length());
    }
    
    @Override
    public void toPiped(final Writer w) throws IOException {
        int last = startSegment(w, QAK_XML);
        
        last = addField(w, this.queryTag, last, 1);
        last = addField(w, this.queryResponseStatus, last, 2);
        last = addField(w, this.messageQueryName, last, 3);
        last = addField(w, this.hitCount, last, 4);
        last = addField(w, this.thisPayload, last, 5);
        last = addField(w, this.hitsRemaining, last, 6);
        
        endSegment(w);
    }
    
    /**
     * Retrieves the query tag
     * 
     * @return the query tag
     **/
    public String getQueryTag() {
        return this.queryTag;
    }
    
    /**
     * Retrieves the query response status
     * 
     * @return the query response status
     **/
    public String getQueryResponseStatus() {
        return this.queryResponseStatus;
    }
    
    /**
     * Retrieves the message query name
     * 
     * @return the message query name
     **/
    public CE getMessageQueryName() {
        return this.messageQueryName;
    }
    
    /**
     * Retrieves the hit count
     * 
     * @return the hit count
     **/
    public NM getHitCount() {
        return this.hitCount;
    }
    
    /**
     * Retrieves the this payload
     * 
     * @return the this payload
     **/
    public NM getThisPayload() {
        return this.thisPayload;
    }
    
    /**
     * Retrieves the hits remaining
     * 
     * @return the hits remaining
     **/
    public NM getHitsRemaining() {
        return this.hitsRemaining;
    }
    
    /**
     * Modifies the query tag
     * 
     * @param queryTag the new query tag
     **/
    public void setQueryTag(final String queryTag) {
        this.queryTag = queryTag;
    }
    
    /**
     * Modifies the query response status
     * 
     * @param queryResponseStatus the new query response status
     **/
    public void setQueryResponseStatus(final String queryResponseStatus) {
        this.queryResponseStatus = queryResponseStatus;
    }
    
    /**
     * Modifies the message query name
     * 
     * @param messageQueryName the new message query name
     **/
    public void setMessageQueryName(final CE messageQueryName) {
        this.messageQueryName = messageQueryName;
    }
    
    /**
     * Modifies the hit count
     * 
     * @param hitCount the new hit count
     **/
    public void setHitCount(final NM hitCount) {
        this.hitCount = hitCount;
    }
    
    /**
     * Modifies the this payload
     * 
     * @param thisPayload the new this payload
     **/
    public void setThisPayload(final NM thisPayload) {
        this.thisPayload = thisPayload;
    }
    
    /**
     * Modifies the hits remaining
     * 
     * @param hitsRemaining the new hits remaining
     **/
    public void setHitsRemaining(final NM hitsRemaining) {
        this.hitsRemaining = hitsRemaining;
    }
}
