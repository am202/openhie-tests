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
package org.regenstrief.hl7;

public class ParseProperties {
    
    public final static String ERR_UNRECOGNIZED_CONTENT = "Unrecognized content: ";
    
    public final static String ERR_IMPROPER_COMPLEX_DATA = "Unexpected complex data: ";
    
    public final static String ERR_IMPROPER_REPETITION = "Unexpected repetition: ";
    
    protected boolean flatXML;
    
    protected boolean strict;
    
    protected boolean silent;
    
    public ParseProperties() {
        this.strict = true;
        this.flatXML = false;
        this.silent = true;
    }
    
    public void load(final ParseProperties parseProperties) {
        this.strict = parseProperties.isStrict();
        this.flatXML = parseProperties.isFlatXML();
        this.silent = parseProperties.isSilent();
    }
    
    /**
     * Retrieves the flat XML flag
     * 
     * @return the flat XML flag
     **/
    public boolean isFlatXML() {
        return this.flatXML;
    }
    
    /**
     * Retrieves the strict flag
     * 
     * @return the strict flag
     **/
    public boolean isStrict() {
        return this.strict;
    }
    
    /**
     * Retrieves the silent flag
     * 
     * @return the silent flag
     **/
    public boolean isSilent() {
        return this.silent;
    }
    
    /**
     * Modifies the flat XML flag
     * 
     * @param flatXML the new flat XML flag
     **/
    public void setFlatXML(final boolean flatXML) {
        this.flatXML = flatXML;
    }
    
    /**
     * Modifies the strict flag
     * 
     * @param strict the new strict flag
     **/
    public void setStrict(final boolean strict) {
        this.strict = strict;
    }
    
    /**
     * Modifies the silent flag
     * 
     * @param silent the new silent flag
     **/
    public void setSilent(final boolean silent) {
        this.silent = silent;
    }
    
    protected final void err(final String msg) {
        if (!isSilent()) {
            throw new RuntimeException(msg);
        }
    }
    
    public final void errUnrecognizedContent(final String content) {
        err(ERR_UNRECOGNIZED_CONTENT + content);
    }
    
    public final void errImproperComplexData(final String content) {
        err(ERR_IMPROPER_COMPLEX_DATA + content);
    }
    
    public final void errImproperRepetition(final String content) {
        err(ERR_IMPROPER_REPETITION + content);
    }
}
