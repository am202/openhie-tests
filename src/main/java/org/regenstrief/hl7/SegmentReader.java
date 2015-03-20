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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Set;

import org.regenstrief.hl7.convert.Escaper;
import org.regenstrief.hl7.segment.HL7Segment;
import org.regenstrief.hl7.segment.USEG;
import org.regenstrief.hl7.util.HL7ParseException;
import org.regenstrief.hl7.util.UtilHL7;
import org.regenstrief.util.Util;

/**
 * SegmentReader
 */
public class SegmentReader {
    
    private final HL7Parser parser;
    
    private final BufferedReader b;
    
    private String prevLine = null;
    
    private Set<String> limitTo = null;
    
    protected SegmentReader() {
        this.parser = null;
        this.b = null;
    }
    
    public SegmentReader(final HL7Parser parser, final Reader in) {
        this.parser = parser;
        this.b = Util.getBufferedReader(in);
    }
    
    public HL7Segment readSegment() throws IOException {
        String line = null, name = null;
        this.parser.stack.clear();
        while (true) {
            while (true) {
                if (this.prevLine == null) {
                    this.prevLine = this.b.readLine();
                    if (this.prevLine == null) {
                        break;
                    }
                }
                final int size = this.prevLine.length();
                if ((size == 0) || Util.isAllWhitespace(this.prevLine)) {
                    // Do nothing, just keep processing the loop
                } else if (line == null) {
                    line = this.prevLine;
                    Escaper.extractDelimiters(this.parser, line);
                } else if (this.prevLine.startsWith(UtilHL7.ADD_XML) && (size > 3)
                        && (this.parser.getFieldSeparator() == this.prevLine.charAt(3))) {
                    line = line + this.prevLine.substring(4); // Inefficient if there are many ADD segments
                } else {
                    break; // Keep prevLine for next time a segment is needed
                }
                this.prevLine = null;
            }
            if (line == null) {
                return null;
            }
            name = getName(this.parser, line);
            if ((this.limitTo != null) && !this.limitTo.contains(name)) {
                line = null;
                continue;
            }
            break;
        }
        HL7Data data = this.parser.getInstance(name);
        if (data instanceof HL7Segment) {
            try {
                ((HL7Segment) data).readPiped(this.parser, line);
            } catch (final Exception e) {
                //final USEG useg = new USEG(this, name);
                //useg.readPiped(this, line);
                //data = useg;
                // First attempt might have already been USEG; could check for that and skip second attempt
                data = USEG.parsePiped(this.parser, name, line, HL7ParseException.toHL7ParseException(e));
            }
        } else if (data == null) {
            throw new HL7ParseException("Null HL7Data for " + name);
        } else {
            throw new HL7ParseException("Unexpected HL7Data for " + name + ": " + data.getClass().getName());
        }
        return (HL7Segment) data;
    }
    
    public void setLimitTo(final Set<String> limitTo) {
        this.limitTo = limitTo;
    }
    
    public final static String getName(final HL7Parser parser, final String line) {
        final int i = line.indexOf(parser.getFieldSeparator());
        return i < 0 ? line : line.substring(0, i);
    }
}
