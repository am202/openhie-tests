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
import org.regenstrief.hl7.util.AbstractCheckedIdData;

/**
 * <p>
 * Title: CK
 * </p>
 * <p>
 * Description: HL7 Composite ID With Check Digit
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
public class CK extends AbstractCheckedIdData {
    
    public final static String CK_XML = "CK";
    
    public final static String ID_NUMBER_XML = "CK.1";
    
    public final static String CHECK_DIGIT_XML = "CK.2";
    
    public final static String CODE_IDENTIFYING_THE_CHECK_DIGIT_SCHEME_EMPLOYED_XML = "CK.3";
    
    public final static String ASSIGNING_AUTHORITY_XML = "CK.4";
    
    /**
     * Constructs an empty CK
     * 
     * @param prop the HL7Properties
     **/
    public CK(final HL7Properties prop) {
        super(prop);
    }
    
    public static CK parsePiped(final HL7Parser parser, final String line, final int start, final char delim, final int stop) {
        if (stop <= start) {
            return null;
        }
        final CK ck = new CK(parser);
        ck.readPiped(parser, line, start, delim, stop);
        return ck;
    }
    
    @Override
    public void readPiped(final HL7Parser parser, final String line, int start, final char delim, final int stop) {
        int next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        final char c = parser.getSubcomponentSeparator();
        this.idNumber = NM.parsePiped(parser, line, start, c, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.checkDigitNumber = NM.parsePiped(parser, line, start, c, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.checkDigitScheme = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.assigningAuthority = HD.parsePiped(parser, line, start, c, next);
        
        assertLast(line, next + 1, delim, stop);
    }
    
    @Override
    public void toPiped(final Writer w, final int level) throws IOException {
        int last = 1;
        
        last = addComponent(w, this.idNumber, last, 1, level);
        last = addComponent(w, this.checkDigitNumber, last, 2, level);
        last = addComponent(w, this.checkDigitScheme, last, 3, level);
        last = addComponent(w, this.assigningAuthority, last, 4, level);
    }
}
