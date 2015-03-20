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
import org.regenstrief.util.Util;

/**
 * <p>
 * Title: DTM
 * </p>
 * <p>
 * Description: HL7 Date/time
 * </p>
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * <p>
 * Company: Regenstrief Institute
 * </p>
 * 
 * @author Andrew Martin
 * @version 1.0
 */
public class DTM extends AbstractDateData {
    
    // From manual:
    // The TS data type has been replaced by the DTM data type and the detail was withdrawn and removed from the standard as of v 2.6.
    // So maybe DTM should extend TS.
    // But TS has more components, so maybe TS should extend DTM.
    public final static String DTM_XML = "DTM"; //YYYY[MM[DD[HH[MM[SS[.S[S[S[S]]]]]]]]][+/-ZZZZ]
    
    private DTM(final HL7Properties prop) {
        super(prop);
    }
    
    /**
     * Constructs a new DTM with from the given value
     * 
     * @param prop the HL7Properties
     * @param value the value
     **/
    public DTM(final HL7Properties prop, final String value) {
        super(prop);
        this.value = value;
    }
    
    /**
     * Constructs a new DTM with from the given value
     * 
     * @param prop the HL7Properties
     * @param value the value
     * @return the DTM
     **/
    public final static DTM create(final HL7Properties prop, final String value) {
        return Util.isEmpty(value) ? null : new DTM(prop, value);
    }
    
    public static DTM parsePiped(final HL7Parser parser, final String line, final int start, final char delim, final int stop) {
        if (stop <= start) {
            return null;
        }
        final DTM dtm = new DTM(parser);
        dtm.readPiped(parser, line, start, delim, stop);
        return dtm;
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
