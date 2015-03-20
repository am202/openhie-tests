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
import org.regenstrief.hl7.util.AbstractScopeData;

/**
 * <p>
 * Title: EI
 * </p>
 * <p>
 * Description: HL7 Entity Identifier
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
public class EI extends AbstractScopeData {
    
    public final static String EI_XML = "EI";
    
    public final static String ENTITY_IDENTIFIER_XML = "EI.1";
    
    public final static String NAMESPACE_ID_XML = "EI.2";
    
    public final static String UNIVERSAL_ID_XML = "EI.3";
    
    public final static String UNIVERSAL_ID_TYPE_XML = "EI.4";
    
    private String entityIdentifier = null;
    
    /**
     * Constructs an empty EI
     * 
     * @param prop the HL7Properties
     **/
    public EI(final HL7Properties prop) {
        super(prop);
    }
    
    /**
     * Constructs a new EI with the given identifier and HD
     * 
     * @param prop the HL7Properties
     * @param id the identifier
     * @param hd the HD
     **/
    public EI(final HL7Properties prop, final String id, final HD hd) {
        this(prop);
        
        this.entityIdentifier = id;
        if (hd != null) {
            this.namespaceID = hd.getNamespaceID();
            this.universalID = hd.getUniversalID();
            this.universalIDType = hd.getUniversalIDType();
        }
    }
    
    public final static EI create(final HL7Properties prop, final String id) {
        return id == null ? null : new EI(prop, id, null);
    }
    
    public static EI parsePiped(final HL7Parser parser, final String line, final int start, final char delim, final int stop) {
        if (stop <= start) {
            return null;
        }
        final EI ei = new EI(parser);
        ei.readPiped(parser, line, start, delim, stop);
        return ei;
    }
    
    @Override
    public void readPiped(final HL7Parser parser, final String line, int start, final char delim, final int stop) {
        int next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.entityIdentifier = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.namespaceID = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.universalID = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.universalIDType = getToken(line, start, next);
        
        assertLast(line, next + 1, delim, stop);
    }
    
    @Override
    public void toPiped(final Writer w, final int level) throws IOException {
        int last = 1;
        
        last = addComponent(w, this.entityIdentifier, last, 1, level);
        last = addComponent(w, this.namespaceID, last, 2, level);
        last = addComponent(w, this.universalID, last, 3, level);
        last = addComponent(w, this.universalIDType, last, 4, level);
    }
    
    /**
     * Retrieves the entity identifier
     * 
     * @return the entity identifier
     **/
    public String getEntityIdentifier() {
        return this.entityIdentifier;
    }
    
    /**
     * Modifies the entityIdentifier
     * 
     * @param entityIdentifier the new entity identifier
     **/
    public void setEntityIdentifier(final String entityIdentifier) {
        this.entityIdentifier = entityIdentifier;
    }
    
    /**
     * Clears the EI
     **/
    @Override
    public void clear() {
        this.currTag = null;
        this.entityIdentifier = null;
        this.namespaceID = null;
        this.universalID = null;
        this.universalIDType = null;
    }
    
    public final static String getEntityIdentifier(final EI ei) {
        return ei == null ? null : ei.getEntityIdentifier();
    }
}
