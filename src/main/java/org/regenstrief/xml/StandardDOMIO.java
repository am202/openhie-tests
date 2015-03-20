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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.regenstrief.util.Util;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

/**
 * <p>
 * Title: StandardDOMIO
 * </p>
 * <p>
 * Copyright: Copyright (c) 2010
 * </p>
 * <p>
 * Company: Regenstrief Institute
 * </p>
 * 
 * @version 1.0
 */
public class StandardDOMIO implements DOMIO {
    
    private final static DocumentBuilderFactory builderFactory;
    
    private final static DocumentBuilder builder;
    
    private final static TransformerFactory transformerFactory;
    
    static {
        try {
            builderFactory = DocumentBuilderFactory.newInstance();
            builderFactory.setNamespaceAware(true);
            builder = builderFactory.newDocumentBuilder();
            transformerFactory = TransformerFactory.newInstance();
        } catch (final Exception e) {
            throw Util.toRuntimeException(e);
        }
    }
    
    /**
     * Creates a document
     * 
     * @return the Document
     */
    @Override
    public Document createDocument() {
        return builder.newDocument();
    }
    
    /**
     * parse
     * 
     * @param r the input Reader
     * @return the Document
     */
    @Override
    public Document parse(final Reader r) {
        try {
            // DocumentBuilder.parse is not thread-safe
            //return builder.parse(new InputSource(r));
            return builderFactory.newDocumentBuilder().parse(new InputSource(r));
        } catch (final Exception e) {
            throw Util.toRuntimeException(e);
        }
    }
    
    /**
     * serialize
     * 
     * @param elem the element
     * @param out the output Writer
     * @param outputProperties the output properties
     */
    @Override
    public void serialize(final Element elem, final Writer out, final Properties outputProperties) {
        Transformer serializer;
        
        try {
            serializer = transformerFactory.newTransformer();
            // Should add Properties argument
            //serializer.getOutputProperties().putAll(outputProperties); // Doesn't work; a clone is returned
            if (outputProperties != null) {
                for (final String key : outputProperties.stringPropertyNames()) {
                    serializer.setOutputProperty(key, outputProperties.getProperty(key));
                }
            }
            serializer.transform(new DOMSource(elem), new StreamResult(out));
        } catch (final Exception e) {
            throw Util.toRuntimeException(e);
        }
    }
}
