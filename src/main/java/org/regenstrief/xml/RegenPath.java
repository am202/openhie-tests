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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.regenstrief.util.Util;
import org.regenstrief.util.XMLUtil;
import org.regenstrief.util.collection.AbstractIterator;
import org.regenstrief.util.collection.CriterionIterator;
import org.regenstrief.util.criterion.Criteria;
import org.regenstrief.util.criterion.Criterion;
import org.regenstrief.util.criterion.LocalNameCriterion;
import org.regenstrief.util.criterion.NodeTypeCriterion;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * <p>
 * Title: RegenPath
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
public class RegenPath {
    
    private final static RegenPath instance = new RegenPath();
    
    private RegenPath() {
    }
    
    /**
     * Retrieves the instance
     * 
     * @return the instance
     */
    public final static RegenPath getInstance() {
        return instance;
    }
    
    /**
     * <p>
     * Title: RegenPathExpression
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
    public final static class RegenPathExpression {
        
        private final List<RegenPathToken> path;
        
        /*package*/RegenPathExpression(final List<RegenPathToken> path) {
            this.path = path;
        }
        
        /**
         * evaluateFirst
         * 
         * @param n the Node
         * @return the Node
         */
        public final Node evaluateFirst(final Node n) {
            return n == null ? null : getFirst(n, this.path, 0);
        }
        
        /**
         * evaluateAll
         * 
         * @param n the Node
         * @return the Iterable<Node>
         */
        public final Iterable<Node> evaluateAll(final Node n) {
            if (n == null) {
                return null;
            }
            final NodeArrayList targets = new NodeArrayList();
            getAll(n, this.path, 0, targets);
            return targets;
        }
        
        /**
         * evaluateText
         * 
         * @param n the Node
         * @return the String
         */
        public final String evaluateText(final Node n) {
            return XMLUtil.getTextContent(evaluateFirst(n));
        }
        
        public/*replace with package once out of XMLUtil*/final int size() {
            return this.path.size();
        }
        
        /**
         * Converts to a String
         * 
         * @return the String
         */
        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            for (final RegenPathToken token : this.path) {
                sb.append(token);
            }
            return sb.toString();
        }
    }
    
    // This might be useful too:
    //public final static Node evaluateFirst(final Node n, final String path)
    //{	return instance.compile(path).evaluateFirst(n); // Cache me
    //}
    
    private final static Node getFirst(final Node node, final List<RegenPathToken> path, final int pathIndex) {
        final RegenPathToken token = path.get(pathIndex);
        final Iterator<Node> iter = new CriterionIterator<Node>(token.iter(node), token.criterion);
        if (pathIndex == path.size() - 1) {
            return iter.hasNext() ? iter.next() : null;
        }
        while (iter.hasNext()) {
            final Node child = iter.next();
            final Node desc = getFirst(child, path, pathIndex + 1);
            if (desc != null) {
                return desc;
            }
        }
        return null;
    }
    
    private final static void getAll(final Node node, final List<RegenPathToken> path, final int pathIndex,
                                     final NodeArrayList targets) {
        final RegenPathToken token = path.get(pathIndex);
        final Iterator<Node> iter = new CriterionIterator<Node>(token.iter(node), token.criterion);
        final boolean leaf = pathIndex == path.size() - 1;
        while (iter.hasNext()) {
            final Node child = iter.next();
            if (leaf) {
                targets.add(child);
            } else {
                getAll(child, path, pathIndex + 1, targets);
            }
        }
    }
    
    private static abstract class RegenPathToken {
        
        public final Criterion criterion;
        
        /*package*/RegenPathToken(final Criterion criterion) {
            this.criterion = criterion;
        }
        
        /*package*/abstract Iterator<Node> iter(final Node n);
    }
    
    private final static class ChildToken extends RegenPathToken {
        
        /**
         * Creates a ChildToken
         * 
         * @param criterion the criterion
         */
        public ChildToken(final Criterion criterion) {
            super(criterion);
        }
        
        /**
         * iter
         * 
         * @param n the Node
         * @return the Iterator<Node>
         */
        @Override
        public Iterator<Node> iter(final Node n) {
            return new ChildIterator(n);
        }
        
        /**
         * Converts to a String
         * 
         * @return the String
         */
        @Override
        public String toString() {
            return '/' + this.criterion.toString();
        }
    }
    
    private final static class DescendantToken extends RegenPathToken {
        
        /**
         * Creates a DescendantToken
         * 
         * @param criterion the criterion
         */
        public DescendantToken(final Criterion criterion) {
            super(criterion);
        }
        
        /**
         * iter
         * 
         * @param n the Node
         * @return the Iterator<Node>
         */
        @Override
        public Iterator<Node> iter(final Node n) {
            return new DescendantIterator(n);
        }
        
        /**
         * Converts to a String
         * 
         * @return the String
         */
        @Override
        public String toString() {
            return "//" + this.criterion.toString();
        }
    }
    
    /**
     * compile
     * 
     * @param path the path
     * @return the RegenPathExpression
     */
    public final RegenPathExpression compile(final String path) {
        return compile(path, false);
    }
    
    // searchAllDescendants - Search all descendants for path tokens rather than just children
    //TODO Remove this argument and use "/" vs. "//" within the path String to distinguish
    public final RegenPathExpression compile(final String path, final boolean searchAllDescendants) {
        final int size = Util.length(path);
        if (size == 0) {
            return null;
        }
        
        final List<RegenPathToken> tokens = new ArrayList<RegenPathToken>();
        final List<Criterion> tokenCriterions = new ArrayList<Criterion>();
        int nodeStart = 0;
        int bracketStartIndex = -1;
        boolean outOfBracket = true;
        int equalIndex = -1;
        for (int i = 0; i <= size; i++) {
            final char c = i == size ? '/' : path.charAt(i);
            if (c == '[') {
                bracketStartIndex = i;
                outOfBracket = false;
            } else if (c == '=') {
                equalIndex = i;
            } else if (c == ']') {
                if (outOfBracket) {
                    throw new RuntimeException("Unexpected ] at + " + i + " in " + path);
                }
                final String value;
                if (equalIndex == -1) {
                    // Should this be an Exception, or should this mean to check for the existence of the node?
                    //throw new RuntimeException("[condition] without = at " + bracketStartIndex + " in " + path);
                    value = null;
                    equalIndex = i;
                } else {
                    final int quoteStart = equalIndex + 1;
                    final char quote = path.charAt(quoteStart);
                    if ((quote != '\'') && (quote != '"')) {
                        // Should we allow whitespace before quote?
                        // Are quotes needed of the value has no space?
                        throw new RuntimeException("Expected ' or \" at " + quoteStart + " in " + path);
                    }
                    final int quoteEnd = i - 1;
                    if (path.charAt(quoteEnd) != quote) {
                        throw new RuntimeException("Expected " + quote + " at " + quoteEnd + " in " + path);
                    }
                    value = path.substring(quoteStart + 1, quoteEnd);
                }
                tokenCriterions.add(new DescendantValueCriterion(path.substring(bracketStartIndex + 1, equalIndex), value,
                        searchAllDescendants));
                outOfBracket = true;
                equalIndex = -1;
            } else if ((c == '/') && outOfBracket) {
                final short nodeType;
                if (path.charAt(nodeStart) == '@') {
                    /*
                    Could probably skip the NodeTypeCriterion
                    and use '@' to choose an AttrIterator instead of Child/DescendantIterator.
                    Then we could simplify Child/DescendantIterator to only look for elements.
                    */
                    nodeType = Node.ATTRIBUTE_NODE;
                    nodeStart++;
                } else {
                    nodeType = Node.ELEMENT_NODE;
                }
                tokenCriterions.add(NodeTypeCriterion.getInstance(nodeType));
                tokenCriterions.add(new LocalNameCriterion(path.substring(nodeStart, bracketStartIndex == -1 ? i
                        : bracketStartIndex), false));
                final Criterion criterion = Criteria.getCriterion(tokenCriterions);
                tokens.add(searchAllDescendants ? new DescendantToken(criterion) : new ChildToken(criterion));
                tokenCriterions.clear();
                nodeStart = i + 1;
                bracketStartIndex = -1;
            }
        }
        if (!outOfBracket) {
            throw new RuntimeException("Unmatched [ at " + bracketStartIndex + " in " + path);
        }
        
        return new RegenPathExpression(tokens);
    }
    
    /**
     * <p>
     * Title: DescendantIterator
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
    public final static class DescendantIterator extends AbstractIterator<Node> {
        
        private final Node root;
        
        private Node curr;
        
        /**
         * Creates a DescendantIterator
         * 
         * @param root the root
         */
        public DescendantIterator(final Node root) {
            this.root = root;
            this.curr = root;
        }
        
        /**
         * hasNext
         * 
         * @return the boolean
         */
        @Override
        public boolean hasNext() {
            return this.curr != null;
        }
        
        /**
         * next
         * 
         * @return the Node
         */
        @Override
        public Node next() {
            if (this.curr == null) {
                throw new NoSuchElementException();
            }
            final Node ret = this.curr;
            Node next = getFirstChild(this.curr); // Depth first
            if (next == null) {
                Node parent = this.curr;
                while ((parent != this.root) && (next == null)) {
                    next = getNextSibling(parent);
                    parent = getParentNode(parent);
                }
            }
            this.curr = next;
            return ret;
        }
    }
    
    /**
     * <p>
     * Title: ChildIterator
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
    public final static class ChildIterator extends AbstractIterator<Node> {
        
        private Node curr;
        
        /**
         * Creates a ChildIterator
         * 
         * @param parent the parent
         */
        public ChildIterator(final Node parent) {
            this.curr = getFirstChild(parent);
        }
        
        /**
         * hasNext
         * 
         * @return the boolean
         */
        @Override
        public boolean hasNext() {
            return this.curr != null;
        }
        
        /**
         * next
         * 
         * @return the Node
         */
        @Override
        public Node next() {
            if (this.curr == null) {
                throw new NoSuchElementException();
            }
            final Node ret = this.curr;
            this.curr = getNextSibling(this.curr);
            return ret;
        }
    }
    
    private final static Node getFirstChild(final Node n) {
        final NamedNodeMap attrs = n.getAttributes();
        final Node child = XMLUtil.size(attrs) == 0 ? n.getFirstChild() : attrs.item(0);
        if (child != null) {
            final Node parent = getParentNode(child);
            if (parent != n) {
                throw new IllegalStateException("Node " + n + " had child " + child + " with different parent " + parent);
            }
        }
        return child;
    }
    
    private final static Node getNextSibling(final Node n) {
        final Node sibling;
        if (n.getNodeType() == Node.ATTRIBUTE_NODE) {
            final Element parent = ((Attr) n).getOwnerElement();
            if (parent == null) {
                return null;
            }
            final NamedNodeMap attrs = parent.getAttributes();
            int i;
            for (i = 0; attrs.item(i) != n; i++) {
                ;
            }
            sibling = i == attrs.getLength() - 1 ? parent.getFirstChild() : attrs.item(i + 1);
        } else {
            sibling = n.getNextSibling();
        }
        if (sibling != null) {
            final Node p1 = getParentNode(n), p2 = getParentNode(sibling);
            if (p1 != p2) {
                final String msg;
                msg = "Node " + n + " had parent " + p1 + " but sibling " + sibling + " had different parent " + p2;
                throw new IllegalStateException(msg);
            }
        }
        return sibling;
    }
    
    private final static Node getParentNode(final Node n) {
        return n.getNodeType() == Node.ATTRIBUTE_NODE ? ((Attr) n).getOwnerElement() : n.getParentNode();
    }
    
    private final static class DescendantValueCriterion implements Criterion {
        
        //private final String path;
        
        private final String value;
        
        private final RegenPathExpression expr;
        
        /**
         * Creates a DescendantValueCriterion
         * 
         * @param path the path
         * @param value the value
         * @param searchAllDescendants whether to search all descendants for path tokens rather than just children
         */
        public DescendantValueCriterion(final String path, final String value, final boolean searchAllDescendants) {
            //this.path = path;
            this.value = value;
            this.expr = getInstance().compile(path, searchAllDescendants);
        }
        
        /**
         * isMet
         * 
         * @param o the Object
         * @return the boolean
         */
        @Override
        public final boolean isMet(final Object o) {
            if (!(o instanceof Node)) {
                return false;
            }
            //for (final Node desc : XMLUtil.getDescendants(((Node) o), this.path)) {
            for (final Node desc : this.expr.evaluateAll((Node) o)) {
                if (this.value == null) {
                    // If there's no comparison value, then we just check for Node existence
                    return true;
                } else if (this.value.equals(XMLUtil.getText(desc))) {
                    return true;
                }
            }
            return false;
        }
        
        /**
         * Converts to a String
         * 
         * @return the String
         */
        @Override
        public String toString() {
            //return '[' + this.path + "=\"" + this.value + "\"]";
            return '[' + this.expr.toString() + "=\"" + this.value + "\"]";
        }
    }
}
