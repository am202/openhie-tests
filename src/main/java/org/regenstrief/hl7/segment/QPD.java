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

/**
 * <p>
 * Title: QPD
 * </p>
 * <p>
 * Description: HL7 Query Parameter Definition
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
public class QPD extends HL7Segment {
    
    public final static String QPD_XML = "QPD";
    
    public final static String MESSAGE_QUERY_NAME_XML = "QPD.1";
    
    public final static String QUERY_TAG_XML = "QPD.2";
    
    private CE messageQueryName = null;
    
    private String queryTag = null;
    
    /**
     * Constructs an empty QPD
     * 
     * @param prop the HL7Properties
     **/
    public QPD(final HL7Properties prop) {
        super(prop);
    }
    
    public static QPD parsePiped(final HL7Parser parser, final String line) {
        final QPD qpd = new QPD(parser);
        qpd.readPiped(parser, line);
        return qpd;
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
        final char c = parser.getComponentSeparator();
        this.messageQueryName = CE.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.queryTag = getToken(line, start, stop);
        
        //start = stop + 1;
        //stop = getNext(line, start, f);
        //if (stop < start)
        //{	return;
        //}
        //userParametersInSuccessiveFields = varies.parsePiped(parser, line, start, c, stop);
        
        assertLast(line, stop + 1, f, line.length());
    }
    
    @Override
    public void toPiped(final Writer w) throws IOException {
        int last = startSegment(w, QPD_XML);
        
        last = addField(w, this.messageQueryName, last, 1);
        last = addField(w, this.queryTag, last, 2);
        //last = addField(w, userParametersInSuccessiveFields, last, 3);
        
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
     * Retrieves the message query name
     * 
     * @return the message query name
     **/
    public CE getMessageQueryName() {
        return this.messageQueryName;
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
     * Modifies the message query name
     * 
     * @param messageQueryName the new message query name
     **/
    public void setMessageQueryName(final CE messageQueryName) {
        this.messageQueryName = messageQueryName;
    }
}
