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

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.regenstrief.util.Util;

/**
 * BomReader
 */
public class BomReader extends InputStreamReader {
    
    public final static String ENCODING_UTF_8 = "UTF-8";
    
    public final static String ENCODING_UTF_8_BOM = "UTF-8-BOM";
    
    /**
     * Creates a BomReader for the given InputStream and character set
     * 
     * @param in the InputStream
     * @param charsetName the name of the character set
     * @throws UnsupportedEncodingException if the encoding is unknown
     */
    public BomReader(final InputStream in, final String charsetName) throws UnsupportedEncodingException {
        super(in, standardize(charsetName));
        if (ENCODING_UTF_8_BOM.equals(charsetName)) {
            try {
                read(); // Read the non-standard UTF-8 byte order mark, leaving a standard UTF-8 stream which Java supports
            } catch (final Exception e) {
                throw Util.toRuntimeException(e);
            }
        }
    }
    
    private final static String standardize(final String encoding) {
        if (ENCODING_UTF_8_BOM.equals(encoding)) {
            return ENCODING_UTF_8;
        }
        return encoding;
    }
}
