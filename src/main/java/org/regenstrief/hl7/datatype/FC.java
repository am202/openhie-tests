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

/**
 * <p>
 * Title: FC
 * </p>
 * <p>
 * Description: HL7 Financial Class
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
public class FC extends HL7DataType {
    
    public final static String FC_XML = "FC";
    
    public final static String FINANCIAL_CLASS_CODE_XML = "FC.1";
    
    public final static String EFFECTIVE_DATE_XML = "FC.2";
    
    private CWE financialClassCode = null;
    
    private TS effectiveDate = null;
    
    /**
     * Constructs an empty FC
     * 
     * @param prop the HL7Properties
     **/
    public FC(final HL7Properties prop) {
        super(prop);
    }
    
    public static FC parsePiped(final HL7Parser parser, final String line, final int start, final char delim, final int stop) {
        if (stop <= start) {
            return null;
        }
        final FC fc = new FC(parser);
        fc.readPiped(parser, line, start, delim, stop);
        return fc;
    }
    
    @Override
    public void readPiped(final HL7Parser parser, final String line, int start, final char delim, final int stop) {
        int next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        final char c = parser.getSubcomponentSeparator();
        this.financialClassCode = CWE.parsePiped(parser, line, start, c, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.effectiveDate = TS.parsePiped(parser, line, start, c, next);
        
        assertLast(line, next + 1, delim, stop);
    }
    
    @Override
    public void toPiped(final Writer w, final int level) throws IOException {
        int last = 1;
        
        last = addComponent(w, this.financialClassCode, last, 1, level);
        last = addComponent(w, this.effectiveDate, last, 2, level);
    }
    
    /**
     * Retrieves the financial class code
     * 
     * @return the financial class code
     **/
    public CWE getFinancialClassCode() {
        return this.financialClassCode;
    }
    
    /**
     * Retrieves the effective date
     * 
     * @return the effective date
     **/
    public TS getEffectiveDate() {
        return this.effectiveDate;
    }
    
    /**
     * Modifies the financial class code
     * 
     * @param financialClassCode the new financial class code
     **/
    public void setFinancialClassCode(final CWE financialClassCode) {
        this.financialClassCode = financialClassCode;
    }
    
    /**
     * Modifies the effective date
     * 
     * @param effectiveDate the new effective date
     **/
    public void setEffectiveDate(final TS effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
}
