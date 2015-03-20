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
package org.regenstrief.xml;

import java.io.Reader;
import java.io.Writer;
import java.util.Properties;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public interface DOMIO {
    
    /**
     * Creates a document
     * 
     * @return the Document
     */
    public Document createDocument();
    
    /**
     * serialize
     * 
     * @param elem the element
     * @param out the output Writer
     * @param outputProperties the output properties
     */
    public void serialize(Element elem, Writer out, Properties outputProperties);
    
    /**
     * parse
     * 
     * @param r the input Reader
     * @return the Document
     */
    public Document parse(Reader r);
}
