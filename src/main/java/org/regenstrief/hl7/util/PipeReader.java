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
package org.regenstrief.hl7.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.regenstrief.hl7.convert.Escaper;
import org.regenstrief.hl7.segment.MSH;
import org.regenstrief.util.Util;

// Might want to repair names stuffed into last name field, addresses stuffed into street field, etc.

/**
 * Title: Piped HL7 Reader Description: Provides Reader access to a piped HL7 file, removing
 * incorrect line breaks. Copyright: Copyright (c) 2006 Company: Regenstrief Institute
 * 
 * @author Andrew Martin
 * @version 1.0
 **/
public class PipeReader extends BufferedReader {
    
    public final static String PROP_BREAK_REPLACEMENT = "org.regenstrief.hl7.util.PipeReader.breakReplacement";
    
    private String line;
    
    private char fieldSeparator = Escaper.DEFAULT_FIELD_SEPARATOR;
    
    private String breakReplacement = Util.getProperty(PROP_BREAK_REPLACEMENT, " ");
    
    /**
     * Constructs a new PipeReader
     * 
     * @param r the input Reader
     **/
    public PipeReader(final Reader r) {
        super(r);
        
        try {
            this.line = getLine();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Retrieves a line from the input Reader
     * 
     * @return the line
     * @throws IOException if a problem occurs
     **/
    private String getLine() throws IOException {
        final String line = super.readLine();
        
        if ((line != null) && line.startsWith(MSH.MSH_XML)) {
            this.fieldSeparator = line.charAt(3);
        }
        
        return line;
    }
    
    private final static boolean up(final String s, final int i) {
        return up(s.charAt(i));
    }
    
    private final static boolean up(final char c) {
        return (c >= 'A') && (c <= 'Z');
    }
    
    private final static boolean digit(final char c) {
        return Util.isDigit(c);
    }
    
    /**
     * Retrieves a segment, converting line breaks within the segment to spaces
     * 
     * @return the segment
     * @throws IOException if a problem occurs in the underlying Reader
     **/
    @Override
    public String readLine() throws IOException {
        String s = null;
        StringBuilder sb = null;
        final char f = this.fieldSeparator;
        
        while (true) {
            s = getLine();
            if ((s == null) || s.startsWith(MSH.MSH_XML)) {
                break;
            }
            final int n = s.length();
            if ((n >= 3) && up(s, 0) && up(s, 1) && (up(s, 2) || digit(s.charAt(2))) && ((n == 3) || (s.charAt(3) == f))) {
                break;
            } else {
                if (sb == null) {
                    sb = new StringBuilder(n + this.line.length());
                    sb.append(this.line);
                }
                appendLine(sb, s);
            }
        }
        
        if (sb == null) {
            final String t = this.line;
            this.line = s;
            return t;
        }
        this.line = s;
        
        return sb.toString();
    }
    
    /**
     * Repairs the line breaks of an HL7 message
     * 
     * @param hl7 the HL7 message
     * @return the repaired HL7 message
     **/
    public static String repair(final String hl7) {
        final PipeReader r = new PipeReader(new StringReader(hl7));
        String line;
        final StringBuffer sb = new StringBuffer(hl7.length());
        boolean isFirst = true;
        
        try {
            while ((line = r.readLine()) != null) {
                if (isFirst) {
                    isFirst = false;
                } else {
                    sb.append('\n');
                }
                sb.append(line);
            }
            r.close();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
        
        return sb.toString();
    }
    
    /**
     * Appends a line to the segment
     * 
     * @param sb the StringBuilder segment
     * @param line the line
     **/
    private void appendLine(final StringBuilder sb, final String line) {
        sb.append(this.breakReplacement);
        sb.append(line);
    }
    
    /**
     * Modifies the break replacement
     * 
     * @param breakReplacement the new break replacement
     */
    public void setBreakReplacement(final String breakReplacement) {
        this.breakReplacement = breakReplacement;
    }
    
    /**
     * Unsupported
     * 
     * @return never returns a value
     **/
    @Override
    public int read() {
        throw new UnsupportedOperationException();
    }
    
    /**
     * Unsupported
     * 
     * @param cbuf the character buffer
     * @return never returns a value
     **/
    @Override
    public int read(final char[] cbuf) {
        throw new UnsupportedOperationException();
    }
    
    /**
     * Unsupported
     * 
     * @param cbuf the character buffer
     * @param off the offset
     * @param len the length
     * @return never returns a value
     **/
    @Override
    public int read(final char[] cbuf, final int off, final int len) {
        throw new UnsupportedOperationException();
    }
}
