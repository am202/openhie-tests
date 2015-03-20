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
import org.regenstrief.hl7.util.AbstractDateData;

/**
 * <p>
 * Title: DT
 * </p>
 * <p>
 * Description: HL7 Date
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
public class DT extends AbstractDateData {
    
    public final static String DT_XML = "DT"; //YYYY[MM[DD]]
    
    private DT(final HL7Properties prop) {
        super(prop);
    }
    
    /**
     * Constructs a new DT with from the given value
     * 
     * @param prop the HL7Properties
     * @param value the value
     **/
    public DT(final HL7Properties prop, final String value) {
        super(prop);
        this.value = value;
    }
    
    public static DT parsePiped(final HL7Parser parser, final String line, final int start, final char delim, final int stop) {
        if (stop <= start) {
            return null;
        }
        final DT dt = new DT(parser);
        dt.readPiped(parser, line, start, delim, stop);
        return dt;
    }
    
    @Override
    public void readPiped(final HL7Parser parser, final String line, final int start, final char delim, final int stop) {
        this.value = getToken(line, start, stop);
    }
    
    @Override
    public void toPiped(final Writer w, final int level) throws IOException {
        writePipedPrimitive(w);
    }
}
