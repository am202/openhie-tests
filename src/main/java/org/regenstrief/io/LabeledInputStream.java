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

import java.io.FileNotFoundException;
import java.io.FilterInputStream;
import java.io.InputStream;

import org.regenstrief.util.Util;

/**
 * <p>
 * Title: LabeledInputStream
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
public class LabeledInputStream extends FilterInputStream {
    
    protected String label;
    
    protected LabeledInputStream(final String label, final InputStream in) {
        super(in);
        
        if (in == null) {
            final NullPointerException e = new NullPointerException();
            e.initCause(new FileNotFoundException(label));
            throw e;
        }
        
        this.label = label;
    }
    
    /**
     * Creates a LabeledInputStream
     * 
     * @param label the label
     * @param in the InputStream
     * @return the LabeledInputStream
     */
    public static LabeledInputStream create(final String label, final InputStream in) {
        if (in == null) {
            return null;
        }
        
        if (in instanceof LabeledInputStream) {
            final LabeledInputStream lin = (LabeledInputStream) in;
            if (Util.equals(lin.getLabel(), label)) {
                return lin;
            }
        }
        
        return new LabeledInputStream(label, in);
    }
    
    /**
     * Creates a LabeledInputStream
     * 
     * @param in the InputStream
     * @return the LabeledInputStream
     */
    public static LabeledInputStream create(final InputStream in) {
        return in instanceof LabeledInputStream ? (LabeledInputStream) in : new LabeledInputStream(createLabel(), in);
    }
    
    /*package*/static String createLabel() {
        return Util.getStackTrace();
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
    public InputStream getSource() {
        return this.in;
    }
    
    /**
     * Creates a label
     * 
     * @param in the InputStream
     * @return the String
     */
    public static String createLabel(final InputStream in) {
        return in instanceof LabeledInputStream ? ((LabeledInputStream) in).getLabel() : createLabel();
    }
}
