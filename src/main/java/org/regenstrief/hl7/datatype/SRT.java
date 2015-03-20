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
 * Title: SRT
 * </p>
 * <p>
 * Description: HL7 Sort Order
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
public class SRT extends HL7DataType {
    
    public final static String SRT_XML = "SRT";
    
    public final static String SORT_BY_FIELD_XML = "SRT.1";
    
    public final static String SEQUENCING_XML = "SRT.2";
    
    private String sortByField = null;
    
    private String sequencing = null;
    
    /**
     * Constructs an empty SRT
     * 
     * @param prop the HL7Properties
     **/
    public SRT(final HL7Properties prop) {
        super(prop);
    }
    
    public static SRT parsePiped(final HL7Parser parser, final String line, final int start, final char delim, final int stop) {
        if (stop <= start) {
            return null;
        }
        final SRT srt = new SRT(parser);
        srt.readPiped(parser, line, start, delim, stop);
        return srt;
    }
    
    @Override
    public void readPiped(final HL7Parser parser, final String line, int start, final char delim, final int stop) {
        int next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.sortByField = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.sequencing = getToken(line, start, next);
        
        assertLast(line, next + 1, delim, stop);
    }
    
    @Override
    public void toPiped(final Writer w, final int level) throws IOException {
        int last = 1;
        
        last = addComponent(w, this.sortByField, last, 1, level);
        last = addComponent(w, this.sequencing, last, 2, level);
    }
    
    /**
     * Retrieves the sort-by field
     * 
     * @return the sort-by field
     **/
    public String getSortByField() {
        return this.sortByField;
    }
    
    /**
     * Retrieves the sequencing
     * 
     * @return the sequencing
     **/
    public String getSequencing() {
        return this.sequencing;
    }
    
    /**
     * Modifies the sort-by field
     * 
     * @param sortByField the new sort-by field
     **/
    public void setSortByField(final String sortByField) {
        this.sortByField = sortByField;
    }
    
    /**
     * Modifies the sequencing
     * 
     * @param sequencing the new sequencing
     **/
    public void setSequencing(final String sequencing) {
        this.sequencing = sequencing;
    }
}
