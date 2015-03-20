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
package org.regenstrief.io;

import java.io.FilterReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.regenstrief.util.Util;

/**
 * <p>
 * Title: LabeledReader
 * </p>
 * <p>
 * Copyright: Copyright (c) 2010
 * </p>
 * <p>
 * Company: Regenstrief Institute
 * </p>
 * 
 * @version 1.0
 */
public class LabeledReader extends FilterReader {
    
    protected String label;
    
    protected LabeledReader(final String label, final Reader in) {
        super(in);
        
        this.label = label;
    }
    
    /**
     * Creates a LabeledReader
     * 
     * @param in the InputStream
     */
    public LabeledReader(final LabeledInputStream in) {
        super(new InputStreamReader(in));
        
        this.label = in.getLabel();
    }
    
    /**
     * Creates a LabeledReader
     * 
     * @param in the InputStream
     * @param encoding the encoding
     */
    public LabeledReader(final LabeledInputStream in, final String encoding) {
        super(getReader(in, encoding));
        
        this.label = in.getLabel();
    }
    
    private static Reader getReader(final InputStream in, final String encoding) {
        try {
            return new BomReader(in, encoding);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Creates a LabeledReader
     * 
     * @param label the label
     * @param in the input Reader
     * @return the LabeledReader
     */
    public static LabeledReader create(final String label, final Reader in) {
        if (in instanceof LabeledReader) {
            final LabeledReader lin = (LabeledReader) in;
            if (Util.equals(lin.getLabel(), label)) {
                return lin;
            }
        }
        
        return new LabeledReader(label, in);
    }
    
    /**
     * Creates a LabeledReader
     * 
     * @param in the input Reader
     * @return the LabeledReader
     */
    public static LabeledReader create(final Reader in) {
        return in instanceof LabeledReader ? (LabeledReader) in : new LabeledReader(LabeledInputStream.createLabel(), in);
    }
    
    /**
     * Retrieves the label
     * 
     * @return the label
     */
    public String getLabel() {
        return this.label;
    }
    
    /**
     * Retrieves the source
     * 
     * @return the source
     */
    public Reader getSource() {
        return this.in;
    }
    
    /**
     * Creates a label
     * 
     * @param in the input Reader
     * @return the String
     */
    public static String createLabel(final Reader in) {
        return in instanceof LabeledReader ? ((LabeledReader) in).getLabel() : LabeledInputStream.createLabel();
    }
}
