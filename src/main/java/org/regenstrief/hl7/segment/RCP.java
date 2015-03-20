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

import org.regenstrief.hl7.HL7Parser;
import org.regenstrief.hl7.HL7Properties;
import org.regenstrief.hl7.datatype.CE;
import org.regenstrief.hl7.datatype.CQ;
import org.regenstrief.hl7.datatype.SRT;
import org.regenstrief.hl7.datatype.TS;
import org.regenstrief.util.Util;

/**
 * <p>
 * Title: RCP
 * </p>
 * <p>
 * Description: HL7 Response Control Parameter Segment
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
public class RCP extends HL7Segment {
    
    public final static String RCP_XML = "RCP";
    
    public final static String QUERY_PRIORITY_XML = "RCP.1";
    
    public final static String QUANTITY_LIMITED_REQUEST_XML = "RCP.2";
    
    public final static String RESPONSE_MODALITY_XML = "RCP.3";
    
    public final static String EXECUTION_AND_DELIVERY_TIME_XML = "RCP.4";
    
    public final static String MODIFY_INDICATOR_XML = "RCP.5";
    
    public final static String SORT_BY_FIELD_XML = "RCP.6";
    
    public final static String SEGMENT_GROUP_INCLUSION_XML = "RCP.7";
    
    public final static String DEFERRED = "D";
    
    public final static String IMMEDIATE = "I";
    
    private String queryPriority = null;
    
    private CQ quantityLimitedRequest = null;
    
    private CE responseModality = null;
    
    private TS executionAndDeliveryTime = null;
    
    private String modifyIndicator = null;
    
    private List<SRT> sortByField = null;
    
    private List<String> segmentGroupInclusion = null;
    
    /**
     * Constructs an empty RCP
     * 
     * @param prop the HL7Properties
     **/
    public RCP(final HL7Properties prop) {
        super(prop);
    }
    
    /**
     * Constructs a RCP
     * 
     * @param prop the HL7Properties
     * @param quantity the quantity
     **/
    public RCP(final HL7Properties prop, final int quantity) {
        this(prop);
        this.quantityLimitedRequest = new CQ(prop, quantity);
    }
    
    public static RCP parsePiped(final HL7Parser parser, final String line) {
        final RCP rcp = new RCP(parser);
        rcp.readPiped(parser, line);
        return rcp;
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
        this.queryPriority = getToken(line, start, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        final char c = parser.getComponentSeparator();
        this.quantityLimitedRequest = CQ.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.responseModality = CE.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.executionAndDeliveryTime = TS.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.modifyIndicator = getToken(line, start, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        final char r = parser.getRepetitionSeparator();
        while (start < stop) {
            final int next = getNext(line, start, r, stop);
            addSortByField(SRT.parsePiped(parser, line, start, c, next));
            start = next + 1;
        }
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        while (start < stop) {
            final int next = getNext(line, start, r, stop);
            addSegmentGroupInclusion(getToken(line, start, next));
            start = next + 1;
        }
        
        assertLast(line, stop + 1, f, line.length());
    }
    
    @Override
    public void toPiped(final Writer w) throws IOException {
        int last = startSegment(w, RCP_XML);
        
        last = addField(w, this.queryPriority, last, 1);
        last = addField(w, this.quantityLimitedRequest, last, 2);
        last = addField(w, this.responseModality, last, 3);
        last = addField(w, this.executionAndDeliveryTime, last, 4);
        last = addField(w, this.modifyIndicator, last, 5);
        last = addField(w, this.sortByField, last, 6);
        last = addField(w, this.segmentGroupInclusion, last, 7);
        
        endSegment(w);
    }
    
    /**
     * Retrieves the query priority
     * 
     * @return the query priority
     **/
    public String getQueryPriority() {
        return this.queryPriority;
    }
    
    /**
     * Retrieves the modify indicator
     * 
     * @return the modify indicator
     **/
    public String getModifyIndicator() {
        return this.modifyIndicator;
    }
    
    /**
     * Retrieves the response modality
     * 
     * @return the response modality
     **/
    public CE getResponseModality() {
        return this.responseModality;
    }
    
    /**
     * Retrieves the quantity limited request
     * 
     * @return the quantity limited request
     **/
    public CQ getQuantityLimitedRequest() {
        return this.quantityLimitedRequest;
    }
    
    /**
     * Retrieves the execution and delivery time
     * 
     * @return the execution and delivery time
     **/
    public TS getExecutionAndDeliveryTime() {
        return this.executionAndDeliveryTime;
    }
    
    /**
     * Retrieves the sort-by field
     * 
     * @return the sort-by field
     **/
    public List<SRT> getSortByField() {
        return this.sortByField;
    }
    
    /**
     * Retrieves the segment group inclusion
     * 
     * @return the segment group inclusion
     **/
    public List<String> getSegmentGroupInclusion() {
        return this.segmentGroupInclusion;
    }
    
    /**
     * Modifies the query priority
     * 
     * @param queryPriority the new query priority
     **/
    public void setQueryPriority(final String queryPriority) {
        this.queryPriority = queryPriority;
    }
    
    /**
     * Modifies the modify indicator
     * 
     * @param modifyIndicator the new modify indicator
     **/
    public void setModifyIndicator(final String modifyIndicator) {
        this.modifyIndicator = modifyIndicator;
    }
    
    /**
     * Modifies the response modality
     * 
     * @param responseModality the new response modality
     **/
    public void setResponseModality(final CE responseModality) {
        this.responseModality = responseModality;
    }
    
    /**
     * Modifies the quantity limited request
     * 
     * @param quantityLimitedRequest the new quantity limited request
     **/
    public void setQuantityLimitedRequest(final CQ quantityLimitedRequest) {
        this.quantityLimitedRequest = quantityLimitedRequest;
    }
    
    /**
     * Modifies the execution and delivery time
     * 
     * @param executionAndDeliveryTime the new execution and delivery time
     **/
    public void setExecutionAndDeliveryTime(final TS executionAndDeliveryTime) {
        this.executionAndDeliveryTime = executionAndDeliveryTime;
    }
    
    /**
     * Modifies the sort-by field
     * 
     * @param sortByField the new sort-by field
     **/
    public void setSortByField(final List<SRT> sortByField) {
        this.sortByField = sortByField;
    }
    
    /**
     * Modifies the sort-by field
     * 
     * @param sortByField the new sort-by field
     **/
    public void addSortByField(final SRT sortByField) {
        this.sortByField = Util.add(this.sortByField, sortByField);
    }
    
    /**
     * Modifies the segment group inclusion
     * 
     * @param segmentGroupInclusion the new segment group inclusion
     **/
    public void setSegmentGroupInclusion(final List<String> segmentGroupInclusion) {
        this.segmentGroupInclusion = segmentGroupInclusion;
    }
    
    /**
     * Modifies the segment group inclusion
     * 
     * @param segmentGroupInclusion the new segment group inclusion
     **/
    public void addSegmentGroupInclusion(final String segmentGroupInclusion) {
        this.segmentGroupInclusion = Util.add(this.segmentGroupInclusion, segmentGroupInclusion);
    }
}
