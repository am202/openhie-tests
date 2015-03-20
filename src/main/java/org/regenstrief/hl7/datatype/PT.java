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
 * Title: PT
 * </p>
 * <p>
 * Description: HL7 Processing Type
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
public class PT extends HL7DataType {
    
    public final static String PT_XML = "PT";
    
    public final static String PROCESSING_ID_XML = "PT.1";
    
    public final static String PROCESSING_MODE_XML = "PT.2";
    
    private String processingID = null;
    
    private String processingMode = null;
    
    /**
     * Constructs an empty PT
     * 
     * @param prop the HL7Properties
     **/
    public PT(final HL7Properties prop) {
        super(prop);
    }
    
    /**
     * Constructs a PT with the given processing ID
     * 
     * @param prop the HL7Properties
     * @param processingID the processing ID
     **/
    public PT(final HL7Properties prop, final String processingID) {
        this(prop);
        this.setProcessingID(processingID);
    }
    
    public static PT parsePiped(final HL7Parser parser, final String line, final int start, final char delim, final int stop) {
        if (stop <= start) {
            return null;
        }
        final PT pt = new PT(parser);
        pt.readPiped(parser, line, start, delim, stop);
        return pt;
    }
    
    @Override
    public void readPiped(final HL7Parser parser, final String line, int start, final char delim, final int stop) {
        int next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.processingID = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.processingMode = getToken(line, start, next);
        
        assertLast(line, next + 1, delim, stop);
    }
    
    @Override
    public void toPiped(final Writer w, final int level) throws IOException {
        int last = 1;
        
        last = addComponent(w, this.processingID, last, 1, level);
        last = addComponent(w, this.processingMode, last, 2, level);
    }
    
    /**
     * Retrieves the processing ID
     * 
     * @return the processing ID
     **/
    public String getProcessingID() {
        return this.processingID;
    }
    
    /**
     * Retrieves the processing mode
     * 
     * @return the processing mode
     **/
    public String getProcessingMode() {
        return this.processingMode;
    }
    
    /**
     * Modifies the processing ID
     * 
     * @param processingID the new processing ID
     **/
    public void setProcessingID(final String processingID) {
        this.processingID = processingID;
    }
    
    /**
     * Modifies the processing mode
     * 
     * @param processingMode the new processing mode
     **/
    public void setProcessingMode(final String processingMode) {
        this.processingMode = processingMode;
    }
}
