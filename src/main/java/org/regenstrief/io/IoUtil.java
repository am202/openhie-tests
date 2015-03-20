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

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

import org.regenstrief.util.Util;

/**
 * <p>
 * Title: IoUtil
 * </p>
 * <p>
 * Description: Utility IO methods
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: Regenstrief Institute
 * </p>
 * 
 * @author Andrew Martin
 * @version 1.0
 */
public class IoUtil {
    
    /**
     * Closes the given stream
     * 
     * @param s the stream
     **/
    public final static void close(final Closeable s) {
        if (s != null) {
            try {
                s.close();
            } catch (final Exception e) { // If it couldn't be closed, then it's already as closed as it ever will be
            }
        }
    }
    
    /**
     * Retrieves whether two InputStreams have the same content
     * 
     * @param in1 the first InputStream
     * @param in2 the second InputStream
     * @return whether the two InputStreams have the same content
     **/
    public final static boolean equals(final InputStream in1, final InputStream in2) {
        final BufferedInputStream bin1 = Util.getBufferedInputStream(in1), bin2 = Util.getBufferedInputStream(in2);
        int b1;
        
        try {
            while ((b1 = bin1.read()) != -1) {
                if (bin2.read() != b1) {
                    return false;
                }
            }
            
            return bin2.read() == -1;
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
        /*byte buf1[] = new byte[Util.BUFFER_SIZE], buf2[] = new byte[Util.BUFFER_SIZE];

        while (in1.read(buf1) != -1)
        {
            if (in2.read(buf2) == -1)
            {   return false;
            }
        }*/
    }
    
    /**
     * Retrieves whether two files have the same content
     * 
     * @param f1 the name of the first file
     * @param f2 the name of the second file
     * @return whether the two files have the same content
     **/
    public final static boolean equalsFile(final String f1, final String f2) {
        InputStream in1 = null, in2 = null;
        
        try {
            in1 = Util.getRawStream(f1);
            if (in1 == null) {
                throw new RuntimeException("Could not open: " + f1);
            }
            in2 = Util.getRawStream(f2);
            if (in2 == null) {
                throw new RuntimeException("Could not open: " + f1);
            }
            return equals(in1, in2);
        } catch (final Exception e) {
            throw Util.toRuntimeException(e);
        } finally {
            close(in1);
            close(in2);
        }
    }
}
