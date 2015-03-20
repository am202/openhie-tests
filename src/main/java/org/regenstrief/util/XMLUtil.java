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
package org.regenstrief.util;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.xml.namespace.NamespaceContext;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.regenstrief.io.IoUtil;
import org.regenstrief.io.LabeledInputStream;
import org.regenstrief.io.LabeledReader;
import org.regenstrief.xml.DOMIO;
import org.regenstrief.xml.NodeArrayList;
import org.regenstrief.xml.StandardDOMIO;
import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Entity;
import org.w3c.dom.EntityReference;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Notation;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;

/**
 * Title: XML Utilities Description: Set of static XML "utility" methods for parsing XML and
 * accessing data in a parsed XML data structure. Copyright: Copyright (c) 2001 Company: Regenstrief
 * Institute
 * 
 * @author Lonnie Blevins
 * @version 1.0
 */
public class XMLUtil {
    
    private static final Log log = LogFactory.getLog(XMLUtil.class);
    
    public final static String NAMESPACE_URI_XSI = "http://www.w3.org/2001/XMLSchema-instance";
    
    public final static String NAMESPACE_URI_XMLNS = "http://www.w3.org/2000/xmlns/";
    
    public final static String PROP_LINE_WIDTH = XMLUtil.class.getName() + ".lineWidth";
    
    public final static String OUTPUT_PROPERTY_YES = "yes";
    
    public final static String OUTPUT_PROPERTY_NO = "no";
    
    private final static TransformerFactory transformerFactory = TransformerFactory.newInstance();
    
    private static Transformer reportTransform = null;
    
    private final static XPath xp = getNewXPath();
    
    protected static DOMIO io;
    
    private final static Properties outputProperties;
    
    /**
     * XML escaped character entities
     **/
    private static final String[][] ENTITIES = { { "&", "amp" }, { "<", "lt" }, { ">", "gt" }, { "\"", "quot" },
            { "\'", "apos" },

            { "\u00A0", "nbsp" }, { "\u00A1", "iexcl" }, { "\u00A2", "cent" }, { "\u00A3", "pound" },
            { "\u00A4", "curren" }, { "\u00A5", "yen" }, { "\u00A6", "brvbar" }, { "\u00A7", "sect" }, { "\u00A8", "uml" },
            { "\u00A9", "copy" }, { "\u00AA", "ordf" }, { "\u00AB", "laquo" }, { "\u00AC", "not" }, { "\u00AD", "shy" },
            { "\u00AE", "reg" }, { "\u00AF", "macr" }, { "\u00B0", "deg" }, { "\u00B1", "plusmn" }, { "\u00B2", "sup2" },
            { "\u00B3", "sup3" },

            { "\u00B4", "acute" }, { "\u00B5", "micro" }, { "\u00B6", "para" }, { "\u00B7", "middot" },
            { "\u00B8", "cedil" }, { "\u00B9", "sup1" }, { "\u00BA", "ordm" }, { "\u00BB", "raquo" },
            { "\u00BC", "frac14" }, { "\u00BD", "frac12" }, { "\u00BE", "frac34" }, { "\u00BF", "iquest" },

            { "\u00C0", "Agrave" }, { "\u00C1", "Aacute" }, { "\u00C2", "Acirc" }, { "\u00C3", "Atilde" },
            { "\u00C4", "Auml" }, { "\u00C5", "Aring" }, { "\u00C6", "AElig" }, { "\u00C7", "Ccedil" },
            { "\u00C8", "Egrave" }, { "\u00C9", "Eacute" }, { "\u00CA", "Ecirc" }, { "\u00CB", "Euml" },
            { "\u00CC", "Igrave" }, { "\u00CD", "Iacute" }, { "\u00CE", "Icirc" }, { "\u00CF", "Iuml" },

            { "\u00D0", "ETH" }, { "\u00D1", "Ntilde" }, { "\u00D2", "Ograve" }, { "\u00D3", "Oacute" },
            { "\u00D4", "Ocirc" }, { "\u00D5", "Otilde" }, { "\u00D6", "Ouml" }, { "\u00D7", "times" },
            { "\u00D8", "Oslash" }, { "\u00D9", "Ugrave" }, { "\u00DA", "Uacute" }, { "\u00DB", "Ucirc" },
            { "\u00DC", "Uuml" }, { "\u00DD", "Yacute" }, { "\u00DE", "THORN" }, { "\u00DF", "szlig" },

            { "\u00E0", "agrave" }, { "\u00E1", "aacute" }, { "\u00E2", "acirc" }, { "\u00E3", "atilde" },
            { "\u00E4", "auml" }, { "\u00E5", "aring" }, { "\u00E6", "aelig" }, { "\u00E7", "ccedil" },
            { "\u00E8", "egrave" }, { "\u00E9", "eacute" }, { "\u00EA", "ecirc" }, { "\u00EB", "euml" },
            { "\u00EC", "igrave" }, { "\u00ED", "iacute" }, { "\u00EE", "icirc" }, { "\u00EF", "iuml" },

            { "\u00F0", "eth" }, { "\u00F1", "ntilde" }, { "\u00F2", "ograve" }, { "\u00F3", "oacute" },
            { "\u00F4", "ocirc" }, { "\u00F5", "otilde" }, { "\u00F6", "ouml" }, { "\u00F7", "divid" },
            { "\u00F8", "oslash" }, { "\u00F9", "ugrave" }, { "\u00FA", "uacute" }, { "\u00FB", "ucirc" },
            { "\u00FC", "uuml" }, { "\u00FD", "yacute" }, { "\u00FE", "thorn" }, { "\u00FF", "yuml" }, { "\u0080", "euro" } };
    
    private static String[] XML_ENTITIES = null;
    
    private static Map<String, String> XML_ENTITY_MAP = null;
    
    /**
     * load XML entity definitions into Map for quick lookups
     **/
    static {
        final int siz = ENTITIES.length;
        
        XML_ENTITIES = new String[siz];
        XML_ENTITY_MAP = new HashMap<String, String>(siz);
        for (int i = 0; i < siz; i++) {
            XML_ENTITIES[i] = "&" + ENTITIES[i][1] + ";";
            XML_ENTITY_MAP.put(ENTITIES[i][1], ENTITIES[i][0]);
        }
        
        io = new StandardDOMIO();
        
        outputProperties = new Properties();
        outputProperties.setProperty(OutputKeys.OMIT_XML_DECLARATION, OUTPUT_PROPERTY_YES);
    }
    
    /**
     * creates new XML Document
     * 
     * @return newly created Document
     **/
    public final static Document createDocument() {
        return io.createDocument();
    }
    
    /**
     * Creates a node of an XML Document
     * 
     * @param doc the XML Document
     * @param tagName the tag name of the XML element
     * @param attrs the Attributes of the XML element
     * @return the new Element
     **/
    public final static Element createNode(final Document doc, final String tagName, final Attributes attrs) {
        return createNode(doc, null, tagName, attrs);
    }
    
    /**
     * Creates a node of an XML Document
     * 
     * @param doc the XML Document
     * @param namespaceURI the namespace URI
     * @param tagName the tag name of the XML element
     * @param attrs the Attributes of the XML element
     * @return the new Element
     **/
    public final static Element createNode(final Document doc, final String namespaceURI, final String tagName,
                                           final Attributes attrs) {
        if ((doc == null) || (tagName == null)) {
            return null;
        }
        
        final Element e = doc.createElementNS(namespaceURI, tagName);
        
        for (int i = 0, len = attrs.getLength(); i < len; i++) {
            final String uri = attrs.getURI(i);
            if (Util.isEmpty(uri)) {
                e.setAttribute(attrs.getQName(i), attrs.getValue(i));
            } else {
                e.setAttributeNS(uri, attrs.getQName(i), attrs.getValue(i));
            }
        }
        
        return e;
    }
    
    /**
     * Returns a string of the Attributes
     * 
     * @param attrs the Attributes
     * @return the string of Attributes
     **/
    public final static String attrsToString(final Attributes attrs) {
        if (attrs == null) {
            return "";
        }
        
        final StringBuilder txt = new StringBuilder();
        for (int i = 0; i < attrs.getLength(); i++) {
            txt.append(' ');
            txt.append(attrs.getQName(i));
            txt.append("=\"");
            txt.append(attrs.getValue(i));
            txt.append('\"');
        }
        
        return txt.toString();
    }
    
    /**
     * Replaces line feeds, new lines and vertical tabs with one space
     * 
     * @param chars String original character string
     * @return String modified character string
     */
    public static final String replaceReturnsWithSpaces(String chars) {
        /**
         * NOTE: READ BEFORE MAKING ANY UPDATES This code has been optimized for speed when
         * escape'ing very long character strings. Please do not modify it unless you perform timing
         * tests on such long strings, such as long XML text sequences.
         **/
        boolean modified = false; // Set true if have to change chars to escape() it properly
        
        if (chars == null) {
            return "";
        }
        
        final int siz = chars.length(); // Curr size of chars
        final StringBuilder str = new StringBuilder(siz);
        
        for (int j = 0; j < siz; j++) {
            final char currChar = chars.charAt(j); // One special char to look for in chars
            switch (currChar) {
                case 10:
                case 11:
                case 13:
                    str.append(" ");
                    modified = true;
                    break;
                default:
                    str.append(currChar);
            }
        }
        if (modified || (str.length() != siz)) {
            chars = str.toString();
        }
        return chars; //*Return the escaped character string
    }
    
    /**
     * Completely escapes string then unescapes breaks
     * 
     * @param str1 String original string
     * @return String escaped string
     */
    public static final String escapePreserveBreaks(String str1) {
        str1 = escape(str1);
        return str1.replaceAll("&lt;br /&gt;", "<br />");
    }
    
    /**
     * escapes special XML characters so that they are valid XML data
     * 
     * @param chars incoming character string value intended to be saved as XML data
     * @return escaped character string suitable as XML equivalent for the incoming parameter
     **/
    public static final String escape(String chars) {
        /**
         * NOTE: READ BEFORE MAKING ANY UPDATES This code has been optimized for speed when
         * escape'ing very long character strings. Please do not modify it unless you perform timing
         * tests on such long strings, such as long XML text sequences.
         **/
        boolean modified = false; //*Set true if have to change chars to escape() it properly
        
        if (chars == null) {
            return "";
        }
        
        final int siz = chars.length(); // Curr size of chars
        final StringBuilder str = new StringBuilder(siz * 2);
        
        for (int j = 0; j < siz; j++) {
            final char currChar = chars.charAt(j); // One special char to look for in chars
            switch (currChar) {
                case 10:
                case 11:
                case 13:
                    str.append("&lt;br /&gt;");
                    modified = true;
                    break;
                case '&':
                    str.append("&amp;");
                    modified = true;
                    break; // Insert ampersand char sequence
                case '\"':
                    str.append("&quot;");
                    modified = true;
                    break; // Insert double quotation mark sequence
                case '<':
                    str.append("&lt;");
                    modified = true;
                    break; // Insert "less than" sequence
                case '>':
                    str.append("&gt;");
                    modified = true;
                    break; // Insert "greater than" sequence
                case '\'':
                    str.append("&apos;");
                    modified = true;
                    break; // Insert apostrophe
                default:
                    if (currChar < 32) { // Remove control characters
                        modified = true;
                        break;
                    }
                    if (currChar <= 127) {
                        str.append(currChar); // Insert char as it is
                    } else {
                        str.append("&#");
                        str.append(Integer.toString(currChar));
                        str.append(";"); // Insert Unicode char code for ones we don't know
                        modified = true;
                    }
            }
        }
        if (modified || (str.length() != siz)) {
            chars = str.toString();
        }
        return chars; // Return the escaped character string
    }
    
    /**
     * takes an escape'd XML string and converts it back to orginal text contents
     * 
     * @param chars XML contents
     * @return String translation of XML contents
     **/
    public static final String unescape(final String chars) {
        if (chars == null) {
            return "";
        }
        
        final StringBuilder res = new StringBuilder(chars);
        int i = res.indexOf("&");
        
        while (i != -1) {
            final int j = res.indexOf(";", i + 1);
            if (j == -1) {
                break;
            }
            final String entity = res.substring(i + 1, j);
            if (entity.startsWith("#")) {
                if ((entity.length() == 3) && (entity.charAt(1) == 'x')) {
                    // http://www.w3.org/TR/1999/WD-xml-c14n-19991109.html
                    // 5.2 Character Escaping
                    switch (entity.charAt(2)) {
                        case '9':
                            res.replace(i, j + 1, "\t");
                            break;
                        case 'D':
                            res.replace(i, j + 1, "\r");
                            break;
                        case 'A':
                            res.replace(i, j + 1, "\n");
                            break;
                    }
                } else {
                    try {
                        final int charCode = Integer.parseInt(entity.substring(1));
                        final char c = (char) charCode;
                        res.replace(i, j + 1, Character.toString(c));
                        i = i + 1;
                    } catch (final NumberFormatException e) {
                        res.delete(i, j + 1);
                    }
                }
            } else {
                final String textEntity = XML_ENTITY_MAP.get(entity);
                if (textEntity != null) {
                    res.replace(i, j + 1, textEntity);
                    i = i + textEntity.length();
                } else {
                    res.delete(i, j + 1);
                }
            }
            i = res.indexOf("&", i);
        }
        
        return res.toString();
    }
    
    /**
     * parses XML contents of a specified File
     * 
     * @param xmlFileName file name for file that contains the XML
     * @return parsed XML <code>Document</object>
     * @throws Exception if the XML cannot be parsed
     **/
    public static Document parseXMLFromFile(final String xmlFileName) throws Exception {
        return parseXMLFromFile(new File(xmlFileName));
    }
    
    /**
     * parses XML contents of a specified location
     * 
     * @param location the location
     * @return parsed XML <code>Document</object>
     * @throws Exception if the XML cannot be parsed
     **/
    public static Document parseXMLFromLocation(final String location) throws Exception {
        final InputStream in = Util.getStreamRequired(location);
        try {
            return parseXMLFromStream(in);
        } finally {
            IoUtil.close(in);
        }
    }
    
    /**
     * parses XML contents of a specified File
     * 
     * @param xmlFileName file name for file that contains the XML
     * @return parsed XML <code>Document</object>
     * @throws Exception if the XML cannot be parsed
     **/
    public static Document parseXMLFromFile(final File xmlFileName) throws Exception {
        final ByteArrayOutputStream output;
        final FileInputStream input = new FileInputStream(xmlFileName);
        try {
            output = new ByteArrayOutputStream();
            Util.bufferedReadWrite(input, output, 4000);
        } finally {
            IoUtil.close(input);
        }
        
        return parseXMLFromString(new String(output.toByteArray())); // Now parse the XML text
    }
    
    /**
     * parses XML from a text String value
     * 
     * @param xmlDefinition text representation of XML to be parsed
     * @return parsed XML <code>Document</object>
     * @throws Exception if the XML cannot be parsed
     **/
    public static Document parseXMLFromString(final String xmlDefinition) throws Exception {
        if (xmlDefinition == null) {
            return null;
        }
        return parseXMLFromReader(new StringReader(xmlDefinition));
    }
    
    /**
     * parses XML from a text Reader
     * 
     * @param xmlDefinition text representation of XML to be parsed
     * @return parsed XML <code>Document</object>
     * @throws Exception if the XML cannot be parsed
     **/
    public static Document parseXMLFromReader(final Reader xmlDefinition) throws Exception {
        return io.parse(xmlDefinition);
    }
    
    /**
     * parses XML from a text InputStream
     * 
     * @param xmlDefinition text representation of XML to be parsed
     * @return parsed XML <code>Document</object>
     * @throws Exception if the XML cannot be parsed
     **/
    public static Document parseXMLFromStream(final InputStream xmlDefinition) throws Exception {
        return parseXMLFromReader(Util.getReader(xmlDefinition));
    }
    
    /**
     * returns contents of first TextNode defined within an XML Node
     * 
     * @param xmlNode parsed XML Node in which to look for a TextNode
     * @return text character string value of text node
     **/
    public static String xmlFindTextNode(final Node xmlNode) {
        if (xmlNode == null) {
            return null;
        }
        
        final NodeList children = xmlNode.getChildNodes();
        final int childrenCnt = size(children);
        for (int j = 0; j < childrenCnt; j++) {
            final Node childNode = children.item(j);
            if ((childNode != null) && (childNode.getNodeType() == Node.TEXT_NODE)) {
                return childNode.getNodeValue();
            }
        }
        return null;
    }
    
    /**
     * Gets the String value of the Name
     * 
     * @param prefix the Name prefix
     * @param name the Name value
     * @return the String value of the Name
     **/
    public final static String getName(final String prefix, final String name) {
        if (Util.isEmpty(prefix)) {
            return name;
        } else if (Util.isEmpty(name)) {
            return prefix;
        }
        
        return prefix.equals(name) ? name : prefix + ':' + name;
    }
    
    private final static Node getParent(final Node n) {
        return n instanceof Attr ? ((Attr) n).getOwnerElement() : n.getParentNode();
    }
    
    public final static String getPath(Node n) {
        String path = "";
        
        while (n != null) {
            path = "/" + getPathElement(n) + path;
            n = getParent(n);
        }
        
        return path;
    }
    
    private final static String getPathElement(final Node n) {
        final String name = getLocalName(n);
        int i = 1;
        
        if (n instanceof Attr) {
            return "@" + name; // Still need to check namespaces; could have 2 with same local name
        }
        
        // Could use attributes/namespaces to distinguish between nodes with the same local name
        // instead of just an index
        Node sib = n;
        while ((sib = sib.getPreviousSibling()) != null) {
            if (name.equals(getLocalName(sib))) {
                i++;
            }
        }
        if (i > 1) {
            return name + "[" + i + "]";
        }
        
        sib = n;
        while ((sib = sib.getNextSibling()) != null) {
            if (name.equals(getLocalName(sib))) {
                return name + "[1]";
            }
        }
        
        return name;
    }
    
    public final static String getTextContent(final Node xmlNode) {
        return xmlNode == null ? null : xmlNode.getTextContent();
    }
    
    /**
     * Retrieves contents of first Text Node defined within an XML Node
     * 
     * @param xmlNode parsed XML Node in which to look for a Text Node
     * @return text character string value of text node
     **/
    public final static String getText(final Node xmlNode) {
        return xmlNode == null ? null : xmlFindTextNode(xmlNode);
    }
    
    /**
     * Retrieves text contents of the specified Node
     * 
     * @param xmlNode the Node
     * @param tag the tag name of the specified Node
     * @return the text contents
     **/
    public final static String searchText(final Node xmlNode, final String tag) {
        return getText(getDescendant(xmlNode, tag));
    }
    
    /**
     * Retrieves text contents of the specified Nodes
     * 
     * @param xmlNode the Node
     * @param tag the tag name of the specified Nodes
     * @return the text contents
     **/
    public final static List<String> searchTextAll(final Node xmlNode, final String tag) {
        final NodeArrayList nodes = getDescendants(xmlNode, tag);
        
        if (Util.isEmpty(nodes)) {
            return null;
        }
        
        final List<String> list = new ArrayList<String>();
        
        for (final Node node : nodes) {
            list.add(getText(node));
        }
        
        return list;
    }
    
    /**
     * Determines if the first name equals the second name or the qualified version of the second
     * name
     * 
     * @param name1 the first name String
     * @param name2 the second name String
     * @return whether they are equal
     **/
    public final static boolean equalsNameOrQualifiedName(final String name1, final String name2) {
        if (Util.equalsIgnoreCase(name1, name2)) {
            return true;
        } else if ((name1 == null) || (name2 == null)) {
            return false;
        }
        final int pos = name1.length() - name2.length() - 1;
        
        return pos < 0 ? false : name1.endsWith(name2) && (name1.charAt(pos) == ':');
    }
    
    public final static XPath getNewXPath() {
        return XPathFactory.newInstance().newXPath();
    }
    
    public final static NodeList xpath(final XPathExpression xpe, final Node n) {
        try {
            return (NodeList) xpe.evaluate(n, XPathConstants.NODESET);
        } catch (final Exception e) {
            throw Util.toRuntimeException(e);
        }
    }
    
    public final static NodeList xpath(final XPath xp, final Node n, final String path) {
        try {
            /*
            Should we have a cache of compiled XPathExpressions?  No.
            From Java documentation:
            An XPath expression is not thread-safe and not reentrant.
            In other words, it is the application's responsibility to make sure that one XPathExpression object
            is not used from more than one thread at any given time.
            */
            return xpath(xp.compile(path), n);
        } catch (final Exception e) {
            throw Util.toRuntimeException(e);
        }
    }
    
    /**
     * Evaluates an XPath expression
     * 
     * @param n the Node
     * @param path the XPath expression
     * @return the NodeList of results
     **/
    public final static NodeList xpath(final Node n, final String path) {
        return xpath(xp, n, path);
    }
    
    public final static NodeList xpath(final Node n, final String path, final NamespaceContext nc) {
        final XPath xp = getNewXPath();
        
        xp.setNamespaceContext(nc);
        
        return xpath(xp, n, path);
    }
    
    /**
     * Retrieves the closest ancestor Node with the specified name above the given Node
     * 
     * @param node the Node for which to seek an ancestor
     * @param nodename the ancestor Node name
     * @return the ancestor Node
     **/
    public final static Node getAncestor(Node node, final String nodename) {
        while ((node != null) && !equalsNameOrQualifiedName(node.getNodeName(), nodename)) {
            node = node.getParentNode();
        }
        
        return node;
    }
    
    /**
     * Retrieves the first Node with the specified name immediately under the given Node
     * 
     * @param node the parent Node to search
     * @param nodename the name of node for which to search
     * @return the XML Node found with the specified name
     **/
    public final static Node getChild(final Node node, final String nodename) {
        return (Node) getChildren(node, nodename, false);
    }
    
    /**
     * Retrieves the Nodes with the specified name immediately under the given Node
     * 
     * @param node the parent Node to search
     * @param nodename the name of node for which to search
     * @return the XML Nodes found with the specified name
     **/
    public final static NodeArrayList getChildren(final Node node, final String nodename) {
        return (NodeArrayList) getChildren(node, nodename, true);
    }
    
    private final static Object getChildren(final Node node, final String nodename, final boolean all) {
        if (node == null) {
            return null;
        }
        
        final NodeList children = node.getChildNodes();
        final int size = size(children);
        final NodeArrayList list = all ? new NodeArrayList() : null;
        for (int i = 0; i < size; i++) {
            final Node child = children.item(i);
            if (equalsNameOrQualifiedName(child.getNodeName(), nodename)) {
                if (all) {
                    list.add(child);
                } else {
                    return child;
                }
            }
        }
        
        return list;
    }
    
    /**
     * Retrieves the first Node with the specified name under the given Node
     * 
     * @param node the parent Node to search
     * @param nodename the name of node for which to search
     * @return the XML Node found with the specified name
     **/
    public final static Node getDescendant(final Node node, final String nodename) {
        if (Util.isEmpty(nodename)) {
            return null;
        }
        
        /*
        final String[] path = Util.splitExact(nodename, '/'); // Could have expression like elem[@attr="some/value"] where wouldn't want to split on /
        final int size = path.length;
        if (size > 1)
        {
        	for (int i = 0; i < size; i++)
        	{	node = getDescendant(node, path[i]); // Would miss b/d in <a><b><c/></b><b><d/></b></a>
        	}
        	return node;
        }
        */
        
        final NodeArrayList list = getDescendants(node, nodename, new NodeArrayList(1), 1);
        
        return Util.get(list, 0);
    }
    
    /**
     * static method for finding nodes with <nodename> in parsed XML node tree
     * 
     * @param node parent node to search under
     * @param nodename name of node to search for
     * @return XML nodes found with specified <code>nodename</code>
     **/
    public final static NodeArrayList getDescendants(final Node node, final String nodename) {
        return getDescendants(node, nodename, new NodeArrayList(), -1);
    }
    
    /**
     * static method for finding nodes with <nodename> in parsed XML node tree
     * 
     * @param node parent node to search under
     * @param nodename name of node to search for
     * @param list the NodeArrayList in which to store the nodes
     * @param limit the maximum number of nodes to store
     * @return XML nodes found with specified <code>nodename</code>
     **/
    public final static NodeArrayList getDescendants(final Node node, String nodename, final NodeArrayList list,
                                                     final int limit) {
        final String path = nodename;
        String attrName = null, attrValue = null;
        
        if ((node == null) || Util.isEmpty(nodename)) {
            return list;
        } else if (nodename.charAt(0) == '@') {
            final NamedNodeMap attrs = node.getAttributes();
            if (attrs != null) {
                Util.addIfNotNull(list, attrs.getNamedItem(nodename.substring(1)));
            }
            //getAttribute(node, nodename.substring(1));
            return list;
        }
        
        final int nameLen = nodename.length();
        if (nodename.charAt(nameLen - 1) == ']') {
            // XMLTokenReader might have good code for parsing key='value' that allows whitespace around the '='.
            // Might also have something elsewhere in XMLUtil.
            final int size = nodename.indexOf('[');
            final int i = nodename.indexOf('=');
            attrName = nodename.substring(size + 2, i); // "+ 2" to skip [ and @ in expressions like name[@key='value']
            attrValue = nodename.substring(i + 2, nameLen - 2); // "+ 2" to skip = and ', "- 2" to skip ' and ]
            nodename = nodename.substring(0, size);
        }
        
        final String nodeFoundName = node.getNodeName(); // First check if incoming node is the one we are searching for.
        if (equalsNameOrQualifiedName(nodeFoundName, nodename)
                && ((attrName == null) || attrValue.equals(getAttribute(node, attrName)))) {
            list.add(node);
            if ((limit >= 0) && (limit <= list.size())) {
                return list;
            }
        }
        
        final NodeList children = node.getChildNodes(); // Now check children of incoming node
        if (children != null) {
            final int size = children.getLength();
            for (int i = 0; i < size; i++) {
                getDescendants(children.item(i), path, list, limit);
                if ((limit >= 0) && (limit <= list.size())) {
                    return list;
                }
            }
        }
        
        return list;
    }
    
    /**
     * static method for finding first node with <nodename> in parsed XML node tree
     * 
     * @param nodes nodes to search under
     * @param nodename name of node to search for
     * @return XML node found with specified <code>nodename</code>
     **/
    public static Node getDescendant(final NodeList nodes, final String nodename) {
        final int size = nodes.getLength();
        
        for (int i = 0; i < size; i++) {
            final Node node = getDescendant(nodes.item(i), nodename);
            if (node != null) {
                return node;
            }
        }
        
        return null;
    }
    
    /**
     * returns list of child Element Nodes for a given, parsed XML node
     * 
     * @param xmlNode parsed XML Node for which child Nodes are to be returned
     * @return List of child Element Nodes of xmlNode
     **/
    public static final List<Element> getChildElementNodes(final Node xmlNode) {
        final NodeList children = xmlNode.getChildNodes();
        if (children == null) {
            return null;
        }
        final int childrenCnt = children.getLength();
        final List<Element> list = new ArrayList<Element>(childrenCnt);
        
        for (int i = 0; i < childrenCnt; i++) { // Then loop through them looking for search criteria node
            final Node childNode = children.item(i);
            if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                list.add((Element) childNode);
            }
        }
        
        return list;
    }
    
    /**
     * returns array of child Element Nodes for a given, parsed XML node
     * 
     * @param xmlNode parsed XML Node for which child Nodes are to be returned
     * @return array of child Element Nodes of xmlNode
     **/
    public static final Element[] getChildElementArray(final Node xmlNode) {
        return getChildElementNodes(xmlNode).toArray(new Element[0]);
    }
    
    /**
     * Retrieves a NodeArrayList of the Node's children
     * 
     * @param node the Node
     * @return the NodeArrayList of children
     **/
    public final static NodeArrayList getChildren(final Node node) {
        return new NodeArrayList(node.getChildNodes());
    }
    
    /**
     * does "pretty" format on XML to produce XML as text
     * 
     * @param elems XML Elements to be formated into text
     * @return returns formated XML as text, without any leading <?xml...?> declaration
     **/
    public static final String xmlToString(final Element[] elems) {
        final StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < elems.length; i++) {
            sb.append(xmlToString(elems[i]));
        }
        
        return sb.toString();
    }
    
    /**
     * does "pretty" format on XML to produce XML as text
     * 
     * @param elem XML Element to be formated into text
     * @return returns formated XML as text, without any leading <?xml...?> declaration
     **/
    public static final String xmlToString(final Element elem) {
        return xmlToString(elem, true);
    }
    
    /**
     * Converts a Document to a String
     * 
     * @param doc the Document
     * @return the String
     **/
    public final static String xmlToString(final Document doc) {
        final StringWriter buffer = new StringWriter();
        
        try {
            transformerFactory.newTransformer().transform(new DOMSource(doc), new StreamResult(buffer));
            
            return buffer.toString();
        } catch (final TransformerException e) {
            throw Util.toRuntimeException(e);
        } finally {
            close(buffer);
        }
    }
    
    /**
     * does "pretty" format on XML to produce XML as text
     * 
     * @param elem XML Element to be formated into text
     * @param omitXMLDeclaration set to <code>true</code> to prevent <?xml...?> declaration line
     *            from being included in output
     * @return returns formated XML as text
     **/
    public static final String xmlToString(final Element elem, final boolean omitXMLDeclaration) {
        return xmlToString(elem, omitXMLDeclaration, false, -1);
    }
    
    /**
     * does "pretty" format on XML to produce XML as text
     * 
     * @param elem XML Element to be formated into text
     * @param omitXMLDeclaration set to <code>true</code> to prevent <?xml...?> declaration line
     *            from being included in output
     * @param indent set to <code>true</code> to indent output
     * @param lineWidth the XML line width
     * @return returns formated XML as text
     **/
    public static final String xmlToString(final Element elem, final boolean omitXMLDeclaration, final boolean indent,
                                           final int lineWidth) {
        final StringWriter w = new StringWriter();
        
        //io.serialize(elem, w, omitXMLDeclaration);
        final Properties prop = new Properties();
        prop.setProperty(OutputKeys.OMIT_XML_DECLARATION, toOutputPropertyValue(omitXMLDeclaration));
        prop.setProperty(OutputKeys.INDENT, toOutputPropertyValue(indent));
        io.serialize(elem, w, prop);
        
        return w.toString();
        //return XercesUtil.xmlToString(elem, omitXMLDeclaration, indent, lineWidth);
    }
    
    public final static String toOutputPropertyValue(final boolean b) {
        return b ? OUTPUT_PROPERTY_YES : OUTPUT_PROPERTY_NO;
    }
    
    public final static void setOutputProperty(final Properties prop, final String key, final boolean b) {
        prop.setProperty(key, toOutputPropertyValue(b));
    }
    
    public final static void setOutputProperty(final Transformer t, final String key, final boolean b) {
        t.setOutputProperty(key, toOutputPropertyValue(b));
    }
    
    public final static boolean outputPropertyToBoolean(final String val, final boolean defVal) {
        return Util.toBoolean(val, OUTPUT_PROPERTY_YES, OUTPUT_PROPERTY_NO, defVal);
    }
    
    public final static boolean isOutputProperty(final Properties prop, final String key, final boolean defVal) {
        return outputPropertyToBoolean(Util.getProperty(prop, key), defVal);
    }
    
    /**
     * does "pretty" format on XML to produce XML as indented text
     * 
     * @param elem XML Element to be formated into text
     * @param omitXMLDeclaration set to <code>true</code> to prevent <?xml...?> declaration line
     *            from being included in output
     * @return returns formated XML as text
     **/
    public static final String xmlToStringIndented(final Element elem, final boolean omitXMLDeclaration) {
        return xmlToString(elem, omitXMLDeclaration, true, -1);
    }
    
    /**
     * does "pretty" format on XML to produce XML as indented text
     * 
     * @param elem XML Element to be formated into text
     * @param omitXMLDeclaration set to <code>true</code> to prevent <?xml...?> declaration line
     *            from being included in output
     * @return returns formated XML as text
     **/
    public static final String xmlToStringNotIndented(final Element elem, final boolean omitXMLDeclaration) {
        return xmlToString(elem, omitXMLDeclaration, false, 0);
    }
    
    /**
     * Converts the given Element to a StringBuffer
     * 
     * @param elem the Element
     * @return the StringBuffer
     **/
    public static final StringBuffer xmlToStringBuffer(final Element elem) {
        final StringWriter xmlbuff = new StringWriter();
        
        xmlToWriter(elem, xmlbuff);
        IoUtil.close(xmlbuff);
        
        return xmlbuff.getBuffer();
    }
    
    /**
     * Writes the given Element to an OutputStream
     * 
     * @param elem the Element
     * @param out the OutputStream
     **/
    public static final void xmlToOutputStream(final Element elem, final OutputStream out) {
        xmlToWriter(elem, new OutputStreamWriter(out));
    }
    
    public static final void xmlToWriter(final Element elem, final Writer w) {
        io.serialize(elem, w, outputProperties);
        try {
            w.flush();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Closes a Writer
     * 
     * @param w the Writer
     **/
    public static final void close(final Writer w) {
        IoUtil.close(w);
    }
    
    /**
     * returns XML representation of an integer, with specified XML node name
     * 
     * @param doc XML Document in which to build the XML element
     * @param nodename top level XML node name in XML representation
     * @param i the integer
     * @return XML representation of the integer, with specified XML node name
     **/
    public final static Element intToXML(final Document doc, final String nodename, final int i) {
        return strToXML(doc, nodename, String.valueOf(i));
    }
    
    /**
     * returns XML representation of a long, with specified XML node name
     * 
     * @param doc XML Document in which to build the XML element
     * @param nodename top level XML node name in XML representation
     * @param l the long
     * @return XML representation of the integer, with specified XML node name
     **/
    public final static Element longToXML(final Document doc, final String nodename, final long l) {
        return strToXML(doc, nodename, String.valueOf(l));
    }
    
    /**
     * returns XML representation of a double, with specified XML node name
     * 
     * @param doc XML Document in which to build the XML element
     * @param nodename top level XML node name in XML representation
     * @param d the double
     * @return XML representation of the integer, with specified XML node name
     **/
    public final static Element doubleToXML(final Document doc, final String nodename, final double d) {
        return doubleToXML(doc, null, nodename, d);
    }
    
    /**
     * returns XML representation of a double, with specified XML node name
     * 
     * @param doc XML Document in which to build the XML element
     * @param namespaceURI the namespace URI
     * @param nodename top level XML node name in XML representation
     * @param d the double
     * @return XML representation of the integer, with specified XML node name
     **/
    public final static Element doubleToXML(final Document doc, final String namespaceURI, final String nodename,
                                            final double d) {
        return strToXML(doc, nodename, Util.doubleToString(d));
    }
    
    /**
     * returns XML representation of a string, with specified XML node name
     * 
     * @param doc XML Document in which to build the XML element
     * @param nodename top level XML node name in XML representation
     * @param str the string
     * @return XML representation of the string, with specified XML node name
     **/
    public final static Element strToXML(final Document doc, final String nodename, final String str) {
        return strToXML(doc, null, nodename, str);
    }
    
    /**
     * returns XML representation of a string, with specified XML node name
     * 
     * @param doc XML Document in which to build the XML element
     * @param namespaceURI the namespace URI
     * @param qname top level qualified XML node name in XML representation
     * @param str the string
     * @return XML representation of the string, with specified XML node name
     **/
    public final static Element strToXML(final Document doc, final String namespaceURI, final String qname, final String str) {
        if (str == null /*|| str.length() == 0*/) {
            return null;
        }
        final Element xml = doc.createElementNS(namespaceURI, qname);
        xml.appendChild(doc.createTextNode(str));
        
        return xml;
    }
    
    /**
     * returnx an nbsp Node
     * 
     * @param doc XML Document in which to build the XML element
     * @return a EntityReference, the nbsp Node
     **/
    public final static EntityReference nbspXML(final Document doc) {
        return doc.createEntityReference("nbsp");
    }
    
    ///**     returns XML representation of an XMLable object, with specified XML node name
    //@param		doc		XML Document in which to build the XML element
    //@param		nodename	top level XML node name in XML representation
    //@param		x		the XMLable object
    //@return         XML representation of the XMLable object, with specified XML node name
    //**/
    //public final static Element XMLableToXML(Document doc, String nodename, XMLable x)
    //{	return x == null ? null : x instanceof SmallXMLable ? ((SmallXMLable) x).toXMLSmall(doc, nodename) : x.toXML(doc, nodename);
    //}
    
    ///**     returns XML representation of an XMLable object, with default XML node name
    //@param		doc		XML Document in which to build the XML element
    //@param		x		the XMLable object
    //@return         XML representation of the XMLable object, with default XML node name
    //**/
    //public final static Element XMLableToXML(Document doc, XMLable x)
    //{	return x == null ? null : x.toXML(doc);
    //}
    
    /**
     * returns XML representation of an List, with specified XML node names
     * 
     * @param doc XML Document in which to build the XML element
     * @param nodename top level XML node name in XML representation
     * @param childname child XML node name in XML representation
     * @param list the List
     * @return XML representation of the ArrayList, with specified XML node names
     **/
    public final static Element listToXML(final Document doc, final String nodename, final String childname,
                                          final List<?> list) {
        final int size = Util.size(list);
        if (size == 0) {
            return null;
        }
        final Element xml = doc.createElementNS(null, nodename);
        
        for (int i = 0; i < size; i++) {
            final Element child = doc.createElementNS(null, childname);
            child.appendChild(doc.createTextNode(list.get(i).toString()));
            xml.appendChild(child);
        }
        return xml;
    }
    
    /**
     * returns XML representation of a date value, with specified XML node name
     * 
     * @param doc XML Document in which to build the XML element
     * @param nodename top level XML node name in XML representation
     * @param date the date value
     * @return XML representation of the date value, with specified XML node name
     **/
    public final static Element dateToXML(final Document doc, final String nodename, final Date date) {
        final Element xml = doc.createElementNS(null, nodename);
        final StringBuilder val = new StringBuilder(10);
        if (date == null) {
            return null;
        }
        final Calendar cal = Dates.toCalendar(date);
        val.append(Util.to4DigitString(cal.get(Calendar.YEAR)));
        val.append('-');
        val.append(Util.to2DigitString(cal.get(Calendar.MONTH) + 1));
        val.append('-');
        val.append(Util.to2DigitString(cal.get(Calendar.DAY_OF_MONTH)));
        xml.appendChild(doc.createTextNode(val.toString()));
        return xml;
    }
    
    /**
     * returns XML representation of a date-time value, with specified XML node name
     * 
     * @param doc XML Document in which to build the XML element
     * @param nodename top level XML node name in XML representation
     * @param date the date-time value
     * @return XML representation of the date-time value, with specified XML node name
     **/
    public final static Element dateTimeToXML(final Document doc, final String nodename, final Date date) {
        return dateTimeToXML(doc, null, nodename, date);
    }
    
    /**
     * returns XML representation of a date-time value, with specified XML node name
     * 
     * @param doc XML Document in which to build the XML element
     * @param namespaceURI the namespace URI
     * @param nodename top level XML node name in XML representation
     * @param date the date-time value
     * @return XML representation of the date-time value, with specified XML node name
     **/
    public final static Element dateTimeToXML(final Document doc, final String namespaceURI, final String nodename,
                                              final Date date) {
        if (date == null) {
            return null;
        }
        
        final Element xml = doc.createElementNS(namespaceURI, nodename);
        final StringBuilder val = new StringBuilder(16);
        final Calendar cal = Dates.toCalendar(date);
        val.append(cal.get(Calendar.YEAR));
        val.append('-');
        val.append(Util.to2DigitString(cal.get(Calendar.MONTH) + 1));
        val.append('-');
        val.append(Util.to2DigitString(cal.get(Calendar.DAY_OF_MONTH)));
        val.append('T');
        val.append(Util.to2DigitString(cal.get(Calendar.HOUR_OF_DAY)));
        val.append(':');
        val.append(Util.to2DigitString(cal.get(Calendar.MINUTE)));
        xml.appendChild(doc.createTextNode(val.toString()));
        
        return xml;
    }
    
    /**
     * returns XML representation of a non negative integer, with specified XML node name
     * 
     * @param doc XML Document in which to build the XML element
     * @param nodename top level XML node name in XML representation
     * @param i the integer
     * @return XML representation of the object, with specified XML node name
     **/
    public final static Element intNonNegToXML(final Document doc, final String nodename, final int i) {
        return i >= 0 ? intToXML(doc, nodename, i) : null;
    }
    
    /**
     * returns XML representation of a positive integer, with specified XML node name
     * 
     * @param doc XML Document in which to build the XML element
     * @param nodename top level XML node name in XML representation
     * @param i the integer
     * @return XML representation of the object, with specified XML node name
     **/
    public final static Element intPosToXML(final Document doc, final String nodename, final int i) {
        return i > 0 ? intToXML(doc, nodename, i) : null;
    }
    
    /**
     * returns XML representation of a non negative long, with specified XML node name
     * 
     * @param doc XML Document in which to build the XML element
     * @param nodename top level XML node name in XML representation
     * @param l the long
     * @return XML representation of the object, with specified XML node name
     **/
    public final static Element longNonNegToXML(final Document doc, final String nodename, final long l) {
        return l >= 0 ? longToXML(doc, nodename, l) : null;
    }
    
    /**
     * returns XML representation of a positive long, with specified XML node name
     * 
     * @param doc XML Document in which to build the XML element
     * @param nodename top level XML node name in XML representation
     * @param l the long
     * @return XML representation of the object, with specified XML node name
     **/
    public final static Element longPosToXML(final Document doc, final String nodename, final long l) {
        return l > 0 ? longToXML(doc, nodename, l) : null;
    }
    
    /**
     * returns XML representation of a non negative double, with specified XML node name
     * 
     * @param doc XML Document in which to build the XML element
     * @param nodename top level XML node name in XML representation
     * @param d the double
     * @return XML representation of the object, with specified XML node name
     **/
    public final static Element doubleNonNegToXML(final Document doc, final String nodename, final double d) {
        return d >= 0 ? doubleToXML(doc, nodename, d) : null;
    }
    
    /**
     * returns XML representation of a positive double, with specified XML node name
     * 
     * @param doc XML Document in which to build the XML element
     * @param nodename top level XML node name in XML representation
     * @param d the double
     * @return XML representation of the object, with specified XML node name
     **/
    public final static Element doublePosToXML(final Document doc, final String nodename, final double d) {
        return d > 0 ? doubleToXML(doc, nodename, d) : null;
    }
    
    /**
     * formats a string into a valid xml tagname
     * 
     * @param tagname String
     * @return String
     */
    public final static String stringToXMLTag(final String tagname) {
        final int length = tagname.length();
        final StringBuilder formattedTagname = new StringBuilder(length);
        boolean previousSpace = false;
        
        for (int i = 0; i < length; i++) {
            char currentChar = tagname.charAt(i);
            if ((currentChar >= 'A') && (currentChar <= 'Z')) {
                if (previousSpace == false) {
                    currentChar += 32;
                }
                formattedTagname.append(currentChar);
                previousSpace = false;
                continue;
            }
            if ((currentChar >= 'a') && (currentChar <= 'z')) {
                if (previousSpace) {
                    currentChar -= 32;
                }
                formattedTagname.append(currentChar);
                previousSpace = false;
                continue;
            }
            if (formattedTagname.length() > 0) {
                switch (currentChar) {
                    case ' ':
                    case '_':
                    case '-':
                        previousSpace = true;
                        break;
                    case '0':
                    case '1':
                    case '2':
                    case '3':
                    case '4':
                    case '5':
                    case '6':
                    case '7':
                    case '8':
                    case '9':
                        formattedTagname.append(currentChar);
                        previousSpace = false;
                }
            }
        }
        return formattedTagname.toString();
    }
    
    /**
     * Appends a child to the given xml Node
     * 
     * @param xml the parent Node
     * @param child the child Node to append to parent
     **/
    public final static void appendChild(final Node xml, final Node child) {
        if ((xml == null) || (child == null)) {
            return;
        }
        xml.appendChild(child);
    }
    
    /**
     * Sets an attribute for an XML element
     * 
     * @param xml the Element for which to set an attribute
     * @param name the name of the attribute to set
     * @param attr the value of the attribute
     **/
    public final static void setAttribute(final Element xml, final String name, final String attr) {
        if ((xml == null) || (name == null) || Util.isEmpty(attr)) {
            return;
        }
        xml.setAttribute(name, attr);
    }
    
    /**
     * Retrieves the desired attribute from the given Node
     * 
     * @param xml the Node
     * @param attr the attribute name String
     * @return the attribute value String
     **/
    public final static String getAttribute(final Node xml, final String attr) {
        if (xml == null) {
            return null;
        }
        final NamedNodeMap attrs = xml.getAttributes();
        return attrs == null ? null : xmlFindTextNode(attrs.getNamedItem(attr));
    }
    
    /**
     * Retrieves the desired attribute from the given Node
     * 
     * @param xml the Node
     * @param namespaceURI the attribute namespace URI String
     * @param localName the attribute local name String
     * @return the attribute value String
     **/
    public final static String getAttribute(final Node xml, final String namespaceURI, final String localName) {
        return xml == null ? null : xmlFindTextNode(xml.getAttributes().getNamedItemNS(namespaceURI, localName));
    }
    
    /**
     * Gets the owner Document of a Node, possibly the node itself
     * 
     * @param xml the Node for which to find the owner Document
     * @return the owner Document
     **/
    public final static Document getOwnerDocument(final Node xml) {
        if (xml == null) {
            return null;
        }
        return xml.getOwnerDocument() == null ? (Document) xml : xml.getOwnerDocument();
    }
    
    /**
     * Writes a Node to a BufferedWriter
     * 
     * @param out the output BufferedWriter
     * @param node the Node to print
     * @param omitXMLDeclaration indicates whether or not the XML declaration should be omitted
     * @param indent indicates whether or not to indent the output
     * @throws IOException if an I/O problem occurs while writing the Node
     **/
    public final static void writeNode(final BufferedWriter out, final Node node, final boolean omitXMLDeclaration,
                                       final int indent) throws IOException {
        final String br = Util.getLineSeparator();
        final StringBuilder sb = new StringBuilder(indent);
        
        for (int i = 0; i < indent; i++) {
            sb.append(' ');
        }
        final String indentString = sb.toString();
        final String tokens[] = Util.splitExact((xmlToStringIndented((Element) node, omitXMLDeclaration)), '\n');
        for (int i = 0; i < tokens.length; i++) {
            out.write(indentString + tokens[i] + br);
        }
    }
    
    /**
     * Writes a Node to a file with the XML declaration
     * 
     * @param fileString the name of the output file
     * @param node the Node to print
     * @param indent indicates whether or not to indent the output
     * @throws IOException if an I/O problem occurs while writing the Node
     **/
    public final static void writeNode(final String fileString, final Node node, final int indent) throws IOException {
        final BufferedWriter out = new BufferedWriter(new FileWriter(fileString));
        //out.write(xmlToString((Element)node, false));
        writeNode(out, node, false, indent);
        out.close();
    }
    
    /**
     * Writes a Node to a BufferedWriter without indenting
     * 
     * @param out the output BufferedWriter
     * @param node the Node to print
     * @param omitXMLDeclaration indicates whether or not the XML declaration should be omitted
     * @throws IOException if an I/O problem occurs while writing the Node
     **/
    public final static void writeNodeNotIndented(final BufferedWriter out, final Node node, final boolean omitXMLDeclaration)
                                                                                                                              throws IOException {
        out.write(xmlToStringNotIndented((Element) node, omitXMLDeclaration));
    }
    
    /**
     * Writes a Node to a BufferedWriter with the XML declaration, without indenting
     * 
     * @param out the output BufferedWriter
     * @param node the Node to print
     * @throws IOException if an I/O problem occurs while writing the Node
     **/
    public final static void writeNodeNotIndented(final BufferedWriter out, final Node node) throws IOException {
        out.write(xmlToStringNotIndented((Element) node, false));
    }
    
    private static boolean minXML = false;
    
    /**
     * Retrieves whether XML output should be minimized (omit names from <ccode> nodes, use sys_id
     * instead of xml_code)
     * 
     * @return whether XML output should be minimized
     **/
    public final static boolean isMinXML() {
        return minXML;
    }
    
    /**
     * Modifies whether XML output should be minimized
     * 
     * @param minXML true if XML output should be minimized
     **/
    public static void setMinXML(final boolean minXML) {
        XMLUtil.minXML = minXML;
    }
    
    /**
     * copies a node and all its children from one xml document to another provides a way to attach
     * xml as a string to xml as a dom object
     * 
     * @param doc Document xml document
     * @param childNode Node node that will be copied to xml document
     * @return Node new node created from document
     * @throws DOMException if the Document cannot be changed
     */
    public static Node changeNodeDocument(final Document doc, final Node childNode) throws DOMException {
        return changeNodeDocument(doc, childNode, true);
    }
    
    /**
     * copies a node and optionally all its children from one xml document to another provides a way
     * to attach xml as a string to xml as a dom object
     * 
     * @param doc Document xml document
     * @param childNode Node node that will be copied to xml document
     * @param deep boolean whether children of the Node should be changed
     * @return Node new node created from document
     * @throws DOMException if the Document cannot be changed
     */
    public static Node changeNodeDocument(final Document doc, final Node childNode, final boolean deep) throws DOMException {
        return doc.importNode(childNode, deep);
    }
    
    /**
     * Retrieves a new Document
     * 
     * @return the new Document
     **/
    public final static Document getNewDocument() {
        try {
            return createDocument();
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Retrieves the number of Nodes in the NodeList
     * 
     * @param nodeList the NodeList
     * @return the number of Nodes
     **/
    public final static int size(final NodeList nodeList) {
        return nodeList == null ? 0 : nodeList.getLength();
    }
    
    /**
     * Retrieves the desired Node of the given NodeList
     * 
     * @param nodeList the NodeList
     * @param i the desired index
     * @return the desired Node
     **/
    public final static Node get(final NodeList nodeList, final int i) {
        return size(nodeList) <= i ? null : nodeList.item(i);
    }
    
    /**
     * Retrieves the number of Nodes in the NamedNodeMap
     * 
     * @param nodeMap the NamedNodeMap
     * @return the number of Nodes
     **/
    public final static int size(final NamedNodeMap nodeMap) {
        return nodeMap == null ? 0 : nodeMap.getLength();
    }
    
    public static Node getNamedItem(final NamedNodeMap nodeMap, final String name) {
        return nodeMap == null ? null : nodeMap.getNamedItem(name);
    }
    
    public static Node getNamedItemNS(final NamedNodeMap nodeMap, final String namespaceURI, final String localName) {
        return nodeMap == null ? null : nodeMap.getNamedItemNS(namespaceURI, localName);
    }
    
    public static Node removeNamedItem(final NamedNodeMap nodeMap, final String name) {
        return nodeMap == null ? null : nodeMap.removeNamedItem(name);
    }
    
    public static Node removeNamedItemNS(final NamedNodeMap nodeMap, final String namespaceURI, final String localName) {
        return nodeMap == null ? null : nodeMap.removeNamedItemNS(namespaceURI, localName);
    }
    
    /**
     * Retrieves the number of Nodes in the tree rooted at the given Node
     * 
     * @param node the root Node
     * @return the number of Nodes
     **/
    public final static int size(final Node node) {
        if (node == null) {
            return 0;
        }
        
        final NodeList children = node.getChildNodes();
        int size = 1;
        for (int i = 0, n = size(children); i < n; i++) {
            size += size(children.item(i));
        }
        
        return size;
    }
    
    /**
     * Puts all Nodes of the source NamedNodeMap into the destination NamedNodeMap
     * 
     * @param dst the destination NamedNodeMap
     * @param src the source NamedNodeMap
     **/
    public final static void putAll(final NamedNodeMap dst, final NamedNodeMap src) {
        final int size = size(src);
        
        for (int i = 0; i < size; i++) {
            dst.setNamedItemNS(src.item(i));
        }
    }
    
    /**
     * Adds all Nodes of the source NodeList to the destination Node
     * 
     * @param dst the destination NodeList
     * @param src the source Node
     **/
    public final static void addAll(final Node dst, final NodeList src) {
        final int size = size(src);
        
        for (int i = 0; i < size; i++) {
            dst.appendChild(src.item(i));
        }
    }
    
    private final static Pattern PREFIX_PATTERN = Pattern.compile("[^<\"'/\\s]+:");
    
    private final static char[] ATTRIBUTE_DELIMITERS = { '"', '\'' };
    
    /**
     * Removes prefixes from an XML String
     * 
     * @param xml an XML String
     * @return the XML String without prefixes
     **/
    public final static String removePrefixes(final String xml) {
        return removePrefixes(xml, true);
    }
    
    /**
     * Removes prefixes from an XML String
     * 
     * @param xml an XML String
     * @param ignoreAttrValues whether the values of attributes should be ignored when removing
     *            prefixes
     * @return the XML String without prefixes
     **/
    public final static String removePrefixes(final String xml, final boolean ignoreAttrValues) {
        final int len = xml.length();
        int start = -2, stop = -1, stop2;
        final StringBuilder sb = new StringBuilder(len);
        final char[] c = xml.toCharArray();
        
        while (start != -1) {
            start = xml.indexOf('<', start + 1);
            // Append text outside of tags as is
            sb.append(c, stop + 1, (start < 0 ? len : start) - stop - 1);
            if (start >= 0) {
                stop = xml.indexOf('>', start + 1);
                if (ignoreAttrValues) {
                    for (int i = 0; i < ATTRIBUTE_DELIMITERS.length; i++) {
                        stop2 = xml.indexOf(ATTRIBUTE_DELIMITERS[i], start + 1);
                        if ((stop2 >= 0) && (stop2 < stop)) {
                            stop = stop2;
                        }
                    }
                }
                // Append text with tags after removing prefixes
                sb.append(PREFIX_PATTERN.matcher(xml.substring(start, stop + 1)).replaceAll(""));
            }
        }
        return sb.toString();
    }
    
    /**
     * Imports a Node into a Document without creating new prefixes like Document.importNode
     * 
     * @param doc the Document
     * @param toImport the Node to import
     * @return the imported Node
     **/
    public final static Node importNode(final Document doc, final Node toImport) {
        Node imported;
        NodeList list;
        NamedNodeMap map;
        int i, size;
        
        if (toImport instanceof Element) {
            //imported = doc.createElement(((Element) toImport).getTagName()); // Ever want this?
            // This should copy toImport's prefix; I don't think that's what we want, but we don't have a namespace context for doc
            imported = doc.createElementNS(toImport.getNamespaceURI(), toImport.getNodeName());
            for (map = toImport.getAttributes(), size = size(map), i = 0; i < size; i++) {
                final Node n = map.item(i);
                if (n != null) {
                    ((Element) imported).setAttributeNode((Attr) importNode(doc, n));
                }
            }
        } else if (toImport instanceof Attr) {
            final String uri = toImport.getNamespaceURI();
            if (Util.isEmpty(uri)) {
                imported = doc.createAttribute("xmlns".equals(getPrefix(toImport)) ? toImport.getNodeName()
                        : getLocalName(toImport));
            } else {
                imported = doc.createAttributeNS(uri, toImport.getNodeName());
            }
            //imported.setNodeValue(toImport.getNodeValue()); // The value will be copied when we import children below
        } else {
            imported = doc.importNode(toImport, false);
        }
        for (list = toImport.getChildNodes(), size = size(list), i = 0; i < size; i++) {
            final Node n = list.item(i);
            if (n != null) {
                imported.appendChild(importNode(doc, n));
            }
        }
        
        return imported;
    }
    
    /**
     * Retrieves the default namespace for the given Node
     * 
     * @param n the Node
     * @return the default namespace String
     **/
    public final static String getDefaultNamespaceURI(final Node n) {
        return getNamespaceURI(n, null);
    }
    
    /**
     * Retrieves the namespace for the given Node
     * 
     * @param n the the Node
     * @return the namespace String
     **/
    public final static String getNamespaceURI(final Node n) {
        final String prefix = getPrefix(n);
        if (((n instanceof Attr) && "xmlns".equals(prefix)) || "xmlns".equals(n.getNodeName())) {
            return NAMESPACE_URI_XMLNS;
        }
        return getNamespaceURI(n, prefix);
    }
    
    /**
     * Retrieves the namespace for the given Node and prefix
     * 
     * @param n the the Node
     * @param xmlnsPlusPrefix the xmlns[:<prefix>] String
     * @return the namespace String
     **/
    public final static String getNamespaceURI(final Node n, String xmlnsPlusPrefix) {
        if (xmlnsPlusPrefix == null) {
            xmlnsPlusPrefix = "xmlns";
        } else if (!Util.contains(xmlnsPlusPrefix, ':')) {
            xmlnsPlusPrefix = "xmlns:" + xmlnsPlusPrefix;
        }
        return getNamespaceURIIntern(n, xmlnsPlusPrefix);
    }
    
    /**
     * Retrieves the namespace for the given Node and prefix
     * 
     * @param n the the Node
     * @param xmlnsPlusPrefix the xmlns[:<prefix>] String
     * @return the namespace String
     **/
    private final static String getNamespaceURIIntern(final Node n, final String xmlnsPlusPrefix) {
        if (n == null) {
            return null;
        }
        
        final String prefix = getPrefix(n);
        if (prefix == null ? (xmlnsPlusPrefix.length() == 5) : (xmlnsPlusPrefix.endsWith(prefix) && (xmlnsPlusPrefix
                .charAt(5) == ':'))) {
            final String namespaceURI = n.getNamespaceURI();
            if (namespaceURI != null) {
                return namespaceURI;
            }
            //if (n instanceof Attr)
            //{	return NAMESPACE_URI_XMLNS;
            //}
        }
        
        if (!(n instanceof Element)
                || ((n instanceof Element) && Util.isEmpty(((Element) n).getAttribute(xmlnsPlusPrefix)))) {
            return getNamespaceURIIntern(n.getParentNode(), xmlnsPlusPrefix);
        }
        
        return ((Element) n).getAttribute(xmlnsPlusPrefix);
    }
    
    /**
     * Retrieves the local name of the given Node
     * 
     * @param n the Node
     * @return the local name String
     **/
    public final static String getLocalName(final Node n) {
        if (n == null) {
            return null;
        }
        
        final String name = n.getLocalName();
        
        return name == null ? getLocalName(n.getNodeName()) : name;
    }
    
    /**
     * Retrieves the prefix of the given Node
     * 
     * @param n the Node
     * @return the prefix String
     **/
    public final static String getPrefix(final Node n) {
        if (n == null) {
            return null;
        }
        
        final String prefix = n.getPrefix();
        if (prefix != null) {
            return prefix;
        }
        
        return getPrefix(n.getNodeName());
    }
    
    public final static String getPrefix(Node context, final String namespaceURI) {
        while (context != null) {
            if (Util.equals(namespaceURI, getNamespaceURI(context))) {
                return getPrefix(context);
            }
            context = context.getParentNode();
        }
        return null;
    }
    
    /**
     * Normalizes an XML String for comparison that disregards differences in optional escaping
     * 
     * @param s the XML String
     * @return the normalized XML String
     **/
    private final static String normalize(String s) {
        if (s == null) {
            return null;
        } else if (s.startsWith("<?xml")) {
            s = s.substring(s.indexOf("?>") + 2);
        }
        try {
            return xmlToString((Element) stripNamespaces(parseXMLFromString("<X>" + s + "</X>").getDocumentElement()));
        } catch (final Exception e) {
            throw Util.toRuntimeException(e);
        }
    }
    
    /**
     * Compares two XML Strings, disregarding differences in namespaces and escaping
     * 
     * @param s1 the first XML String
     * @param s2 the second XML String
     * @return whether the two Strings are equal
     **/
    public final static boolean xmlMatches(final String s1, final String s2) {
        return Util.equals(s1, s2) || Util.equals(normalize(s1), normalize(s2));
    }
    
    /**
     * Retrieves the name of a Node
     * 
     * @param n the Node
     * @return the name
     **/
    public final static String getName(final Node n) {
        if (n == null) {
            return null;
        }
        
        final String name = n.getLocalName();
        if ((name == null) && (n instanceof Element)) {
            return ((Element) n).getTagName();
        }
        
        return name;
    }
    
    /**
     * Retrieves the local name from the given qualified name
     * 
     * @param s the qualified name
     * @return the local name
     **/
    public final static String getLocalName(final String s) {
        if (s == null) {
            return null;
        }
        
        final int i = s.indexOf(':');
        
        return i < 0 ? s : s.substring(i + 1);
    }
    
    /**
     * Retrieves the prefix from the given qualified name
     * 
     * @param s the qualified name
     * @return the prefix
     **/
    public final static String getPrefix(final String s) {
        if (s == null) {
            return null;
        }
        
        final int i = s.indexOf(':');
        
        return i < 0 ? null : s.substring(0, i);
    }
    
    /**
     * Retrieves the String of a Node
     * 
     * @param n the Node
     * @return the String
     **/
    public final static String toString(final Node n) {
        if (n == null) {
            return null;
        }
        
        return n instanceof Text ? n.getNodeValue() : n.getNodeName();
    }
    
    /**
     * Strips whitespace from a Node tree
     * 
     * @param n the root Node
     **/
    public final static void stripWhitespace(final Node n) {
        if (n == null) {
            return;
        } else if (n.getNodeType() == Node.TEXT_NODE) {
            final Node p = canStripTextWhitespace(n);
            if (p != null) {
                p.removeChild(n);
            }
        } else {
            final NodeList list = n.getChildNodes();
            for (int i = size(list) - 1; i >= 0; i--) {
                stripWhitespace(list.item(i));
            }
        }
    }
    
    /**
     * Retrieves the parent Node if the given Text Node is whitespace that can be stripped
     * 
     * @param text the Node (must be of nodeType == Node.TEXT_NODE)
     * @return the parent or null
     */
    public final static Node canStripTextWhitespace(final Node text) {
        if (Util.isAllWhitespace(text.getNodeValue())) {
            final Node p = text.getParentNode();
            //if ((p != null) && (text.getPreviousSibling() != null) || (text.getNextSibling() != null)) {
            if ((p != null) && (p.getChildNodes().getLength() > 1)) {
                return p;
            }
        }
        return null;
    }
    
    /**
     * Strips namespaces and prefixes from a Node tree
     * 
     * @param n the root Node
     * @return the stripped Node
     **/
    public final static Node stripNamespaces(final Node n) {
        if (n == null) {
            return null;
        }
        
        final Document doc = n.getOwnerDocument();
        if (n instanceof Element) {
            final Element eOld = (Element) n;
            final Element eNew = doc.createElement(getLocalName(eOld));
            final NamedNodeMap map = eOld.getAttributes();
            for (int i = 0, size = size(map); i < size; i++) {
                final Attr attr = (Attr) map.item(i);
                String name = attr.getName();
                if (name == null) {
                    name = attr.getLocalName();
                }
                if (!("xmlns".equals(name) || ((name != null) && name.startsWith("xmlns:")) || "xmlns".equals(attr
                        .getPrefix()))) {
                    final int j = name.indexOf(':');
                    eNew.setAttribute(j < 0 ? name : name.substring(j + 1), attr.getValue());
                }
            }
            final NodeList list = n.getChildNodes();
            for (int i = 0, size = size(list); i < size; i++) {
                appendChild(eNew, stripNamespaces(list.item(i)));
            }
            return eNew;
        } else if (n instanceof Attr) {
            return null;
        }
        return n.cloneNode(false);
    }
    
    /**
     * Reads in a string of report xml and returns the plain text version of the report with no xml
     * tags
     * 
     * @param reportXML String report xml
     * @return String plain text version of report
     * @throws Exception if the report cannot be converted
     */
    public final static String convertReportXMLToPlainText(final String reportXML) throws Exception {
        final StringWriter out = new StringWriter();
        convertReportXMLToPlainText(reportXML, out);
        return out.toString();
    }
    
    public final static void convertReportXMLToPlainText(final String reportXML, final Writer out) throws Exception {
        if (reportTransform == null) {
            reportTransform = getTransformer("strip.xsl");
            reportTransform.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, OUTPUT_PROPERTY_YES);
            reportTransform.setOutputProperty(OutputKeys.INDENT, OUTPUT_PROPERTY_NO);
        }
        
        reportTransform.transform(new StreamSource(new StringReader(reportXML)), new StreamResult(out));
    }
    
    public final static String getAttributeIgnoreNamespace(final Element xml, final String attr, final String namespaceURI) {
        String attrValue = xml.getAttribute(attr);
        if (Util.isEmpty(attrValue)) {
            attrValue = xml.getAttributeNS(namespaceURI, attr);
        }
        return attrValue;
    }
    
    public final static void applyXSLTTransform(final String xslt, final Reader input, final Writer output)
                                                                                                           throws TransformerException {
        applyXSLTTransform(new StringReader(xslt), input, output);
    }
    
    public final static void applyXSLTTransform(final Reader xslt, final Reader input, final Writer output)
                                                                                                           throws TransformerException {
        final Transformer transformer = transformerFactory.newTransformer(new StreamSource(xslt));
        
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, OUTPUT_PROPERTY_YES);
        transformer.setOutputProperty(OutputKeys.INDENT, OUTPUT_PROPERTY_NO);
        
        transformer.transform(new StreamSource(input), new StreamResult(output));
    }
    
    public final static String toURL(final String location) {
        try {
            return Util.toURL(location).toString();
        } catch (final Exception e) {} // URL is not required, just leave it as null
        return null;
    }
    
    public final static Transformer getTransformer(final String location) {
        return getTransformer(transformerFactory, location);
    }
    
    public final static Transformer getTransformer(final TransformerFactory tf, final String loc) {
        Reader in = null;
        try {
            in = Util.getReader(loc);
            return getTransformer(tf, in, loc);
        } catch (final Exception e) {
            throw Util.toRuntimeException(e);
        } finally {
            IoUtil.close(in);
        }
    }
    
    public final static Transformer getTransformer(final TransformerFactory tf, final Reader in, final String loc) {
        try {
            return tf.newTransformer(getSource(in, toURL(loc)));
        } catch (final Exception e) {
            throw Util.toRuntimeException(e);
        }
    }
    
    public final static Reader toReader(final InputSource source) {
        try {
            final InputStream in = source.getByteStream();
            if (in != null) {
                return Util.getReader(in);
            } else {
                final Reader r = source.getCharacterStream();
                if (r != null) {
                    return r;
                } else {
                    final String systemId = source.getSystemId();
                    if (systemId != null) {
                        return Util.getReader(systemId);
                    }
                }
            }
        } catch (final Exception e) {
            throw Util.toRuntimeException(e);
        }
        
        throw new RuntimeException("Unsupported InputSource: " + source);
    }
    
    public final static <N extends Node> N trim(final N n) {
        return trimIntern(expandFragments(n));
    }
    
    private final static <N extends Node> N trimIntern(final N n) {
        if (n instanceof Comment) {
            return null;
        } else if (n instanceof Text) {
            return Util.trim(n.getNodeValue()) == null ? null : n;
        } else {
            for (final Node child : new NodeArrayList(n.getChildNodes())) { // Copy since we'll be modifying it
                if (trimIntern(child) == null) {
                    n.removeChild(child);
                }
            }
        }
        
        return n;
    }
    
    public final static <N extends Node> N expandFragments(final N n) {
        for (final Node child : new NodeArrayList(n.getChildNodes())) { // Copy since we'll be modifying it
            expandFragments(n, child, child);
            expandFragments(child);
        }
        
        return n;
    }
    
    private final static boolean expandFragments(final Node parent, final Node ref, final Node frag) {
        if (frag instanceof DocumentFragment) {
            for (final Node child : new NodeArrayList(frag.getChildNodes())) { // Copy since we'll be modifying it
                if (!expandFragments(parent, ref, child)) {
                    parent.insertBefore(child, ref);
                }
            }
            parent.removeChild(frag);
            return true;
        }
        
        return false;
    }
    
    public final static StreamSource getSource(final InputStream in) {
        return in instanceof LabeledInputStream ? new StreamSource(in, ((LabeledInputStream) in).getLabel())
                : new StreamSource(in);
    }
    
    public final static StreamSource getSource(final Reader in) {
        return in instanceof LabeledReader ? new StreamSource(in, ((LabeledReader) in).getLabel()) : new StreamSource(in);
    }
    
    public final static StreamSource getSource(final String url) {
        try {
            return getSource(Util.getReaderRequired(url), url);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public final static StreamSource getSource(final Reader in, final String url) {
        return url == null ? new StreamSource(in) : new StreamSource(in, url);
    }
    
    public final static Class<? extends Node> toClass(final short nodeType) {
        switch (nodeType) {
            case Node.ATTRIBUTE_NODE:
                return Attr.class;
            case Node.CDATA_SECTION_NODE:
                return CDATASection.class;
            case Node.COMMENT_NODE:
                return Comment.class;
            case Node.DOCUMENT_FRAGMENT_NODE:
                return DocumentFragment.class;
            case Node.DOCUMENT_NODE:
                return Document.class;
            case Node.DOCUMENT_TYPE_NODE:
                return DocumentType.class;
            case Node.ELEMENT_NODE:
                return Element.class;
            case Node.ENTITY_NODE:
                return Entity.class;
            case Node.ENTITY_REFERENCE_NODE:
                return EntityReference.class;
            case Node.NOTATION_NODE:
                return Notation.class;
            case Node.PROCESSING_INSTRUCTION_NODE:
                return ProcessingInstruction.class;
            case Node.TEXT_NODE:
                return Text.class;
        }
        throw new RuntimeException("Unrecognized node type " + nodeType);
    }
    
    public final static String toString(final short nodeType) {
        return toClass(nodeType).getSimpleName();
    }
    
    /**
     * make constructor private. All real methods of this class are <code>static</code> now.
     **/
    protected XMLUtil() {
    }
    
    public static void main(final String[] args) {
        final String origText = " abc def 01*&_aXYZ";
        final String abc = stringToXMLTag(origText);
        log.info("Orig value = '" + origText + "'");
        log.info("toXMLTag = '" + abc + "'");
    }
}
