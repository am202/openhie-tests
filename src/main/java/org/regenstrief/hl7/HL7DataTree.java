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

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;

import org.regenstrief.hl7.group.HL7Group;
import org.regenstrief.hl7.segment.HL7Segment;
import org.regenstrief.hl7.util.UtilHL7;
import org.regenstrief.util.Util;
import org.regenstrief.util.collection.AbstractTreeNode;
import org.regenstrief.util.collection.CriterionIterable;
import org.regenstrief.util.criterion.Criterion;

/**
 * <p>
 * Title: HL7DataTree
 * </p>
 * <p>
 * Description: A tree node storing a piece of data from an HL7 message
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
public class HL7DataTree extends AbstractTreeNode<HL7Data, HL7DataTree> {
    
    private static final long serialVersionUID = 1L;
    
    private final static String PROP_BR = "org.regenstrief.hl7.HL7DataTree.br";
    
    static private class SameTagNameCriterion implements Criterion {
        
        private String tagName;
        
        private String getTagName() {
            return this.tagName;
        }
        
        private void setTagName(final String tag) {
            this.tagName = tag;
        }
        
        private SameTagNameCriterion(final String tag) {
            setTagName(tag);
        }
        
        @Override
        public boolean isMet(final Object o) {
            if (o == null) {
                return false;
            }
            final HL7Data value;
            if (o instanceof HL7DataTree) {
                value = ((HL7DataTree) o).getValue();
            } else if (o instanceof HL7Data) {
                value = (HL7Data) o;
            } else {
                return false;
            }
            return value.getTagName().equals(getTagName());
        }
    }
    
    public Iterable<HL7DataTree> getChildNodes(final String tagName) {
        return CriterionIterable.create(getChildNodes(), new SameTagNameCriterion(tagName));
    }
    
    public HL7Data getChildValue(final String tagName) {
        final HL7DataTree childNode = getChildNode(tagName);
        return childNode == null ? null : childNode.getValue();
    }
    
    public HL7DataTree getChildNode(final String tagName) {
        return Util.getFirst(getChildNodes(tagName));
    }
    
    public Iterable<HL7DataTree> getDescendantNodes(final String tagName) {
        return CriterionIterable.create(getDescendantNodes(), new SameTagNameCriterion(tagName));
    }
    
    public Iterable<HL7Data> getDescendantValues(final String tagName) {
        return CriterionIterable.create(getDescendantValues(), new SameTagNameCriterion(tagName));
    }
    
    public HL7Data getDescendantValue(final String tagName) {
        final HL7DataTree childNode = getDescendantNode(tagName);
        return childNode == null ? null : childNode.getValue();
    }
    
    public HL7DataTree getDescendantNode(final String tagName) {
        return Util.getFirst(getDescendantNodes(tagName));
    }
    
    private final static String br = toBr(Util.getProperty(PROP_BR, Util.getLineSeparator()));
    
    private String segmentDelimiter = null;
    
    /**
     * Creates a tree node with the given data
     * 
     * @param data the data to store in this node
     **/
    public HL7DataTree(final HL7Data data) {
        super(data);
        
        if (data == null) {
            throw new NullPointerException();
        }
    }
    
    @Override
    protected HL7DataTree newNode(final HL7Data data) {
        return new HL7DataTree(data);
    }
    
    public HL7DataTree copy() {
        final HL7DataTree copy = new HL7DataTree(this.value);
        
        for (final HL7DataTree child : Util.unNull(getChildNodes())) {
            copy.addChild(child.copy());
        }
        
        return copy;
    }
    
    // Shouldn't override, but currently we count on the parent reference remaining after we've removed the node
    // We were getting NullPointerExceptions during parsing.
    // Those might be fixed by having HL7Parser store the parent before detaching.
    // We also get errors:
    /*
    org.w3c.dom.DOMException: HIERARCHY_REQUEST_ERR: An attempt was made to insert a node where it is not permitted. 
    at org.apache.xerces.dom.CoreDocumentImpl.insertBefore(Unknown Source)
    at org.apache.xerces.dom.NodeImpl.appendChild(Unknown Source)
    at org.regenstrief.hl7.HL7DataTree.accept(HL7DataTree.java:174)
    at org.regenstrief.hl7.HL7DataTree.process(HL7DataTree.java:195)
    at org.regenstrief.hl7.database.HL7ImportOutput.insertNodeAndAncestorsIntoDOM(HL7ImportOutput.java:115)
    at org.regenstrief.hl7.database.HL7ImportOutput.insertNodeIntoAcceptedDOM(HL7ImportOutput.java:89)
    at org.regenstrief.hl7.database.HL7ImportOutput.insertNodeIntoAcceptedDOM(HL7ImportOutput.java:99)
    at org.regenstrief.hl7.database.HL7Import.processStartElement(HL7Import.java:414)
    at org.regenstrief.hl7.HL7Parser.startElement(HL7Parser.java:219)
    at org.apache.xerces.parsers.AbstractSAXParser.startElement(Unknown Source)
    at org.apache.xerces.impl.XMLNSDocumentScannerImpl.scanStartElement(Unknown Source)
    at org.apache.xerces.impl.XMLDocumentFragmentScannerImpl$FragmentContentDispatcher.dispatch(Unknown Source)
    at org.apache.xerces.impl.XMLDocumentFragmentScannerImpl.scanDocument(Unknown Source)
    at org.apache.xerces.parsers.XML11Configuration.parse(Unknown Source)
    at org.apache.xerces.parsers.DTDConfiguration.parse(Unknown Source)
    at org.apache.xerces.parsers.XMLParser.parse(Unknown Source)
    at org.apache.xerces.parsers.AbstractSAXParser.parse(Unknown Source)
    at org.regenstrief.xml.XMLParser.run(XMLParser.java:157)
    at org.regenstrief.hl7.HL7Parser.run(HL7Parser.java:72)
    at org.regenstrief.xml.XMLParser.runFromLocation(XMLParser.java:108)
    at org.regenstrief.xml.XMLParser.runFromFile(XMLParser.java:73)
    at org.regenstrief.hl7_tests.database.TestHL7Import.run(TestHL7Import.java:1132)
    at org.regenstrief.hl7_tests.database.TestHL7Export.bigTest(TestHL7Export.java:105)
    at org.regenstrief.hl7_tests.database.TestHL7Export.testExport(TestHL7Export.java:57)
    at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
    at sun.reflect.NativeMethodAccessorImpl.invoke(Unknown Source)
    at sun.reflect.DelegatingMethodAccessorImpl.invoke(Unknown Source)
    at java.lang.reflect.Method.invoke(Unknown Source)
    at junit.framework.TestCase.runTest(TestCase.java:154)
    at junit.framework.TestCase.runBare(TestCase.java:127)
    at junit.framework.TestResult$1.protect(TestResult.java:106)
    at junit.framework.TestResult.runProtected(TestResult.java:124)
    at junit.framework.TestResult.run(TestResult.java:109)
    at junit.framework.TestCase.run(TestCase.java:118)
    at junit.framework.TestSuite.runTest(TestSuite.java:208)
    at junit.framework.TestSuite.run(TestSuite.java:203)
    at org.eclipse.jdt.internal.junit.runner.junit3.JUnit3TestReference.run(JUnit3TestReference.java:130)
    at org.eclipse.jdt.internal.junit.runner.TestExecution.run(TestExecution.java:38)
    at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:460)
    at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:673)
    at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.run(RemoteTestRunner.java:386)
    at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.main(RemoteTestRunner.java:196)
    */
    // Writing output XML
    @Override
    public HL7DataTree detach() {
        if (this.parent != null) {
            this.parent.children.remove(this);
        }
        
        return this;
    }
    
    // super impl throws an exception if child already has a parent;
    // even if we call detach first, the child will still have a parent (see above)
    // so we override here,e ven though it's ugly
    @Override
    public HL7DataTree addChild(final int i, final HL7DataTree child) {
        child.parent = this;
        if (this.children == null) {
            this.children = new ArrayList<HL7DataTree>();
        }
        this.children.add(i, child);
        return child;
    }
    
    /**
     * Retrieve's this node's children
     * 
     * @return the node's children
     **/
    public HL7DataTreeIterator getChildrenIterator() {
        return new HL7DataTreeIterator(this.children);
    }
    
    // addChild(HL7Data) used to return null without adding anything if the argument was null.
    // AbstractTreeNode doesn't do that.
    @Override
    public HL7DataTree addChild(final HL7Data value) {
        return value == null ? null : addChild(new HL7DataTree(value));
    }
    
    @Override
    public HL7DataTree addChild(final int i, final HL7Data value) {
        return value == null ? null : addChild(i, new HL7DataTree(value));
    }
    
    /**
     * Removes and returns the desired child of this node
     * 
     * @param i the index to remove
     * @return the removed child
     **/
    public HL7DataTree remove(final int i) {
        return this.children.remove(i);
    }
    
    /**
     * Removes and returns the last child of this node
     * 
     * @return the last child
     **/
    public HL7DataTree removeLastChild() {
        return this.children.remove(this.children.size() - 1);
    }
    
    /**
     * Removes all but the last child
     **/
    public void removeAllButLastChild() {
        final HL7DataTree last = getLastChild();
        
        this.children.clear();
        this.children.add(last);
    }
    
    /**
     * Removes all but the first child
     **/
    public void removeAllButFirstChild() {
        final HL7DataTree first = getFirstChild();
        
        this.children.clear();
        this.children.add(first);
    }
    
    /**
     * Finds a node in the tree starting at this node with the given data
     * 
     * @param data the data of the desired node
     * @return the node
     **/
    public HL7DataTree findNode(final HL7Data data) {
        if (this.value == data) {
            return this;
        }
        
        for (int i = 0, size = Util.size(this.children); i < size; i++) {
            final HL7DataTree node = this.children.get(i).findNode(data);
            if (node != null) {
                return node;
            }
        }
        
        return null;
    }
    
    ///**	Finds HL7DataTree nodes in the tree starting at this node with the given type
    //@param c	the class of the desired node
    //@return		the nodes
    //**/
    //public List<HL7DataTree> getNodeTrees(Class<? extends HL7Data> c)
    //{	return getNodeTrees(c, null);
    //}
    
    ///**	Finds HL7DataTree nodes in the tree starting at this node with the given type
    //@param c	the class of the desired node
    //@param delimiter	the class at which to stop looking for the desired node
    //@return		the nodes
    //**/
    //public List<HL7DataTree> getNodeTrees(Class<? extends HL7Data> c, Class<? extends HL7Data> delimiter)
    //{	return getNodes(c, new ArrayList<HL7DataTree>(), true, delimiter);
    //}
    
    ///**	Finds HL7Data nodes in the tree starting at this node with the given type
    //@param c	the class of the desired node
    //@return		the nodes
    //**/
    //public List<HL7Data> getNodes(Class<? extends HL7Data> c)
    //{	return getNodes(c, new ArrayList<HL7Data>(), false, null);
    //}
    
    ///**	Finds descendants of the given Class, checking descendants of ancestors if this node has none
    //@param c	the class of the desired node
    //@return		the nodes
    //**/
    //public List<HL7DataTree> getDescendantTrees(Class<? extends HL7Data> c)
    //{	return getDescendantTrees(c, null);
    //}
    
    ///**	Finds descendants of the given Class, checking descendants of ancestors if this node has none
    //@param c	the class of the desired node
    //@param delimiter	the class at which to stop looking for the desired node
    //@return		the nodes
    //**/
    //public List<HL7DataTree> getDescendantTrees(Class<? extends HL7Data> c, Class<? extends HL7Data> delimiter)
    //{	return getDescendantTrees(this, c, delimiter);
    //}
    
    ///**	Finds descendants of the given Class in the given tree
    //@param tree	the HL7DataTree to search
    //@param c	the class of the desired node
    //@param delimiter	the class at which to stop looking for the desired node
    //@return		the nodes
    //**/
    //private List<HL7DataTree> getDescendantTrees(HL7DataTree tree, Class<? extends HL7Data> c, Class<? extends HL7Data> delimiter)
    //{
    //List<HL7DataTree> list = getNodeTrees(tree.getValue().getClass());
    
    //if (Util.size(list) != 1 || list.get(0) != tree)
    //{	return null;
    //}
    //list = getNodeTrees(c, delimiter);
    
    //return Util.size(list) != 0 ? list : parent == null ? null : parent.getDescendantTrees(tree, c, delimiter);
    //}
    
    ///**	Finds the descendant of the given Class, checking descendants of ancestors if this node has none
    //@param c	the class of the desired node
    //@return		the node
    //**/
    //public HL7DataTree getDescendantTree(Class<? extends HL7Data> c)
    //{
    //List<HL7DataTree> list = getDescendantTrees(c);
    
    //return Util.size(list) != 0 ? list.get(0) : null;
    //}
    
    ///**	Finds the descendant of the given Class, checking descendants of ancestors if this node has none
    //@param c	the class of the desired node
    //@return		the node
    //**/
    //public List<HL7Data> getDescendants(Class<? extends HL7Data> c)
    //{	return getDescendants(c, null);
    //}
    
    ///**	Finds the descendant of the given Class, checking descendants of ancestors if this node has none
    //@param c	the class of the desired node
    //@param delimiter	the class at which to stop looking for the desired node
    //@return		the node
    //**/
    //public List<HL7Data> getDescendants(Class<? extends HL7Data> c, Class<? extends HL7Data> delimiter)
    //{
    //List<HL7DataTree> trees = getDescendantTrees(c, delimiter);
    //int i, size = Util.size(trees);
    //List<HL7Data> descendants = new ArrayList<HL7Data>(size);
    
    //for (i = 0; i < size; i++)
    //{	descendants.add(trees.get(i).getValue());
    //}
    
    //return descendants;
    //}
    
    ///**	Finds the descendant of the given Class, checking descendants of ancestors if this node has none
    //@param c	the class of the desired node
    //@return		the node
    //**/
    //public HL7Data getDescendant(Class<? extends HL7Data> c)
    //{
    //HL7DataTree tree = getDescendantTree(c);
    
    //return tree == null ? null : tree.getValue();
    //}
    
    ///**	Finds nodes in the tree starting at this node with the given type
    //@param c	the class of the desired node
    //@param list	the nodes
    //@param getTrees	whether the HL7DataTrees should be retrieved instead of their data
    //@param delimiter	the class at which to stop looking for the desired node
    //@return		the nodes
    //**/
    //private <E> List<E> getNodes(Class<? extends HL7Data> c, List<E> list, boolean getTrees, Class<? extends HL7Data> delimiter)
    //{
    //int i = 0, size = 0;
    //HL7DataTree child;
    //E e;
    
    //if (this.value.getClass().equals(c))
    //{
    //e = Util.cast(getTrees ? this : value);
    //list.add(e);
    //}
    
    //for (i = 0, size = Util.size(children); i < size; i++)
    //{
    //child = children.get(i);
    //if (delimiter != null && delimiter.equals(child.getValue().getClass()))
    //{	break;
    //}
    //child.getNodes(c, list, getTrees, delimiter);
    //}
    
    //return list;
    //}
    
    /**
     * Counts the nodes in the tree starting at this node with the given type
     * 
     * @param c the class of the desired node
     * @return the count
     **/
    public int getCount(final Class<? extends HL7Data> c) {
        int count = 0;
        
        if (this.value.getClass().equals(c)) {
            count++;
        }
        
        for (int i = 0, size = Util.size(this.children); i < size; i++) {
            count += this.children.get(i).getCount(c);
        }
        
        return count;
    }
    
    /**
     * Determines if the data at the desired position of the children List is of the desired type
     * 
     * @param i the desired position
     * @param c the desired type
     * @return true if the data is of the desired type
     **/
    public boolean isChildDesiredType(final int i, final Class<? extends HL7Data> c) {
        if (Util.size(this.children) <= i) {
            return false;
        }
        
        return this.children.get(i).getValue().getClass().equals(c);
    }
    
    /**
     * Determines if the children List contains data of the desired type
     * 
     * @param c the desired type
     * @return true if the data is of the desired type
     **/
    public boolean containsChild(final Class<? extends HL7Data> c) {
        for (final HL7DataTree child : Util.unNull(this.children)) {
            if (child.getValue().getClass().equals(c)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Determines if the HL7DataTree contains data of the desired type
     * 
     * @param c the desired type
     * @return true if the HL7DataTree contains data of the desired type
     **/
    public boolean contains(final Class<? extends HL7Data> c) {
        return getDescendantValue(c) != null;
    }
    
    /**
     * Retrieves the HL7DataTree as a piped String
     * 
     * @return the piped String
     **/
    public String toPiped() {
        final StringWriter w = new StringWriter();
        try {
            toPiped(w);
        } catch (final IOException e) {
            throw new RuntimeException(e); // StringWriter shouldn't throw IOException
        }
        return w.toString();
        //return HL7Group.toPiped(toHL7XML()); // If root is a segment, need to call HL7Segment.toPiped... if data type...
    }
    
    public final void toPiped(final Writer w) throws IOException {
        int i = 0;
        final String br = getBr();
        for (final HL7Data data : getDescendantValues()) {
            if (data instanceof HL7Segment) { // Only other option should be HL7Group
                if (i > 0) {
                    w.write(br);
                }
                ((HL7Segment) data).toPiped(w);
                i++;
            }
        }
    }
    
    private final static String toBr(final String s) {
        return Util.replaceAllExact(Util.replaceAllExact(s, "r", "\r"), "n", "\n");
    }
    
    public final static String getDefaultSegmentDelimiter() {
        return br;
    }
    
    private final String getBr() {
        return this.segmentDelimiter == null ? getDefaultSegmentDelimiter() : this.segmentDelimiter;
    }
    
    public void setSegmentDelimiter(final String segmentDelimiter) {
        this.segmentDelimiter = segmentDelimiter; // CORE-860
    }
    
    /**
     * Retrieves the HL7DataTree as an XML String
     * 
     * @return the XML String
     **/
    @Override
    public String toString() {
        //return toXml();
        return toPiped();
    }
    
    /**
     * Adds an instance at the given position if it is not present there
     * 
     * @param prop the HL7Properties
     * @param i the desired position
     * @param c the Class of the desired type
     * @return the new HL7DataTree
     **/
    public HL7DataTree addIfNotAtPosition(final HL7Properties prop, final int i, final Class<? extends HL7Data> c) {
        if (!isChildDesiredType(i, c)) {
            return addChild(i, UtilHL7.getInstance(prop, c));
        }
        
        return null;
    }
    
    /**
     * Adds an instance if it is not present
     * 
     * @param prop the HL7Properties
     * @param c the Class of the desired type
     * @return the new HL7DataTree
     **/
    public HL7DataTree addIfNotPresent(final HL7Properties prop, final Class<? extends HL7Data> c) {
        if (!containsChild(c)) {
            return addChild(UtilHL7.getInstance(prop, c));
        }
        
        return null;
    }
    
    /**
     * Adds required fields and children to the HL7DataTree
     * 
     * @return the HL7DataTree with required data added
     **/
    public HL7DataTree addRequired() {
        if (this.value instanceof HL7Group) {
            ((HL7Group) this.value).addRequiredChildren(this);
        }
        this.value.addRequired();
        
        for (int i = 0, size = Util.size(this.children); i < size; i++) {
            this.children.get(i).addRequired();
        }
        
        return this;
    }
    
    public HL7DataTree toFlat() {
        final HL7DataTree flat = new HL7DataTree(this.value);
        
        fillFlat(flat);
        
        return flat;
    }
    
    private void fillFlat(final HL7DataTree flat) {
        HL7Data childValue;
        
        for (final HL7DataTree childNode : Util.unNull(this.children)) {
            childValue = childNode.getValue();
            if (childValue instanceof HL7Segment) {
                flat.addChild(childValue);
            }
            childNode.fillFlat(flat);
        }
    }
    
    @Override
    public void setValue(final HL7Data value) {
        /*
        This could be in AbstractTreeNode.
        Why wasn't already?
        I think we originally wanted a TreeNode's value to be immutable.
        Why?
        This is added here for use cases like:
        An OBR segment had non-standard data and was parsed into a USEG.
        We write a preprocessor to standardize the data and create an instance of the OBR class.
        We want to replace the message's USEG with the true OBR.
        We can now use setValue.
        If we decide that the value really should be immutable,
        then we should add something like a replace(HL7DataTree replacement) method.
        */
        this.value = value;
    }
}
