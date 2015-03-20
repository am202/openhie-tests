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
import org.regenstrief.util.Util;

/**
 * CodedIdentifier
 */
public class CodedIdentifier extends HL7DataType {
    
    /*
    This class is not an official data type.
    However ch. 7 of the manual mentions that OBX.3.1 can use the subcomponent delimiter to add a suffix.
    
    The observation ID for a chest X-ray impression... would be the chest X-ray observation ID (... 71020),
    a subcomponent delimiter, and the suffix, IMP, i.e., 71020&IMP.
    ... if a local code for EKG was E793, and the locally agreed upon designation for that local code was EKG,
    the impression would be identified as E793&IMP^^99EKG.
    
    So we need a way to parse that.
    */
    private String identifier = null;
    
    private String suffix = null;
    
    /**
     * Constructs an empty DDI
     * 
     * @param prop the HL7Properties
     **/
    public CodedIdentifier(final HL7Properties prop) {
        super(prop);
    }
    
    public static CodedIdentifier parsePiped(final HL7Parser parser, final String line, final int start, final char delim, final int stop) {
        if (stop <= start) {
            return null;
        }
        final CodedIdentifier ci = new CodedIdentifier(parser);
        ci.readPiped(parser, line, start, delim, stop);
        return ci;
    }
    
    @Override
    public void readPiped(final HL7Parser parser, final String line, int start, final char delim, final int stop) {
        int next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.identifier = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.suffix = getToken(line, start, next);
        
        assertLast(line, next + 1, delim, stop);
    }
    
    @Override
    public void toPiped(final Writer w, final int level) throws IOException {
        int last = 1;
        
        last = addComponent(w, this.identifier, last, 1, level);
        last = addComponent(w, this.suffix, last, 2, level);
    }
    
    public String getIdentifier() {
        return this.identifier;
    }
    
    public String getSuffix() {
        return this.suffix;
    }
    
    public void setIdentifier(final String identifier) {
        this.identifier = identifier;
    }
    
    public void setSuffix(final String suffix) {
        this.suffix = suffix;
    }
    
    @Override
    public String toDisplay() {
        return toPiped();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof CodedIdentifier)) {
            return false;
        }
        final CodedIdentifier ci = (CodedIdentifier) o;
        return Util.equals(this.identifier, ci.identifier) && Util.equals(this.suffix, ci.suffix);
    }
    
    @Override
    public int hashCode() {
        return Util.hashCode(this.identifier) ^ Util.hashCode(this.suffix);
    }
    
    public final static String getIdentifier(final CodedIdentifier ci) {
        return ci == null ? null : ci.identifier;
    }
    
    public static CodedIdentifier setIdentifier(final HL7Properties prop, CodedIdentifier ci, final String identifier) {
        if (identifier == null) {
            ci = null;
        } else {
            if (ci == null) {
                ci = new CodedIdentifier(prop);
            } else {
                ci.setSuffix(null);
            }
            ci.setIdentifier(identifier);
        }
        
        return ci;
    }
}
