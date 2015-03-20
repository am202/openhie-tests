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
package org.regenstrief.util.collection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.regenstrief.util.Util;
import org.regenstrief.util.convert.CastConverter;
import org.regenstrief.util.convert.Converter;
import org.regenstrief.util.criterion.AssignableFromCriterion;
import org.regenstrief.util.criterion.Criterion;

/**
 * <p>
 * Title: AbstractTreeNode
 * </p>
 * <p>
 * Description: Structure for maintaining a tree of Objects
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: Regenstrief Institute
 * </p>
 * 
 * @author Andrew Martin
 * @version 1.0
 * @param <V> the value type
 * @param <T> the tree type
 */
public abstract class AbstractTreeNode<V, T extends AbstractTreeNode<V, T>> implements Serializable {
    
    private static final long serialVersionUID = 7269616397190723297L;

    protected V value = null;
    
    protected T parent = null;
    
    protected List<T> children = null;
    
    /**
     * Constructs a new AbstractTreeNode root
     * 
     * @param value the node value
     **/
    public AbstractTreeNode(final V value) {
        this.value = value;
    }
    
    protected abstract T newNode(V child);
    
    /**
     * Adds a child AbstractTreeNode
     * 
     * @param child the child
     * @return the new AbstractTreeNode
     **/
    public T addChild(final T child) {
        return addChild(Util.size(this.children), child);
    }
    
    /**
     * Adds a child value
     * 
     * @param value the child
     * @return the new AbstractTreeNode
     */
    public T addChild(final V value) {
        return addChild(newNode(value));
    }
    
    /**
     * Adds a child AbstractTreeNode
     * 
     * @param i the index at which to add the child
     * @param child the child
     * @return the new AbstractTreeNode
     **/
    public T addChild(final int i, final T child) {
        if (!child.isRoot()) {
            // Or should we just detach it?
            throw new RuntimeException("Cannot add tree node " + child.getClass().getSimpleName()
                    + " because it already has a parent; it must be detached first");
        } else if (child == this) {
            throw new IllegalArgumentException("Cannot add node " + child + " to itself");
        }
        child.parent = Util.cast(this);
        if (this.children == null) {
            this.children = new ArrayList<T>();
        }
        this.children.add(i, child);
        
        return child;
    }
    
    /**
     * Adds a child value
     * 
     * @param i the index at which to add the child
     * @param value the child
     * @return the new AbstractTreeNode
     */
    public T addChild(final int i, final V value) {
        return addChild(i, newNode(value));
    }
    
    /**
     * Adds a sibling AbstractTreeNode
     * 
     * @param sibling the sibling
     * @return the new AbstractTreeNode
     **/
    public T addNextSibling(final T sibling) {
        this.parent.addChild(this.parent.children.indexOf(this) + 1, sibling);
        return sibling;
    }
    
    /**
     * Adds a sibling value
     * 
     * @param value the sibling
     * @return the new AbstractTreeNode
     **/
    public T addNextSibling(final V value) {
        return addNextSibling(newNode(value));
    }
    
    /**
     * Modifies the node value
     * 
     * @param value the new node value
     */
    public void setValue(final V value) {
        this.value = value;
    }
    
    /**
     * Retrieves the node value
     * 
     * @return the node value
     **/
    public V getValue() {
        return this.value;
    }
    
    /**
     * Retrieves the parent AbstractTreeNode
     * 
     * @return the parent AbstractTreeNode
     **/
    public T getParent() {
        return this.parent;
    }
    
    // This should probably be getAncestorNodes();
    // could be an Iterable like getDescendants()
    /**
     * Retrieves the ancestors
     * 
     * @return the ancestors
     **/
    public List<T> getAncestors() {
        List<T> list = null;
        
        for (T t = this.parent; t != null; t = t.parent) {
            list = Util.add(list, t);
        }
        
        return list;
    }
    
    /**
     * Returns whether or not the node is a leaf (has no children)
     * 
     * @return true if the node is a leaf, false otherwise
     **/
    public boolean isLeaf() {
        return Util.isEmpty(this.children);
    }
    
    /**
     * Returns whether or not the node is the root (has no parent)
     * 
     * @return true if the node is a root, false otherwise
     **/
    public boolean isRoot() {
        return this.parent == null;
    }
    
    /**
     * Retrieves the root
     * 
     * @return the root
     **/
    public T getRoot() {
        if (this.parent == null) {
            return Util.cast(this);
        }
        
        return this.parent.getRoot();
    }
    
    /**
     * Retrieves the ancestor with the given type
     * 
     * @param c the type
     * @return the ancestor
     **/
    public T getAncestorNode(final Class<? extends V> c) {
        return Util.cast(this.value.getClass().equals(c) ? this : this.parent == null ? null : this.parent
                .getAncestorNode(c));
    }
    
    /**
     * Retrieves the ancestor value with the given type
     * 
     * @param c the type
     * @return the ancestor
     **/
    public <D extends V> D getAncestorValue(final Class<D> c) {
        return Util.cast(getValue(getAncestorNode(c)));
    }
    
    /**
     * Retrieves this node's previous sibling
     * 
     * @return the node's previous sibling
     **/
    public T getPreviousSibling() {
        return getSibling(-1);
    }
    
    /**
     * Retrieves this node's next sibling
     * 
     * @return the node's next sibling
     **/
    public T getNextSibling() {
        return getSibling(1);
    }
    
    private final T getSibling(final int off) {
        if (this.parent == null) {
            return null;
        }
        final List<T> siblings = this.parent.getChildNodes();
        final int base = siblings.indexOf(this);
        if (base < 0) {
            throw new RuntimeException("Could not find node among its parent's children: " + this.value);
        }
        final int i = base + off;
        if ((i < 0) || (i >= siblings.size())) {
            return null;
        }
        
        return siblings.get(i);
    }
    
    /**
     * Detaches the node from its parent
     * 
     * @return this, the detached AbstractTreeNode
     **/
    public T detach() {
        if (this.parent != null) {
            this.parent.children.remove(this);
            this.parent = null;
        }
        
        return Util.cast(this);
    }
    
    /**
     * Detaches all children from the node
     * 
     * @return the AbstractTreeNode List of detached children
     **/
    public List<T> detachChildren() {
        for (final T child : Util.unNull(this.children)) {
            child.parent = null;
        }
        try {
            return this.children;
        } finally {
            this.children = null;
        }
    }
    
    /**
     * Detaches all children of a given Class from the node
     * 
     * @param c the Class
     **/
    public void detachChildren(final Class<? extends V> c) {
        for (int i = Util.size(this.children) - 1; i >= 0; i--) {
            final T child = this.children.get(i);
            if (c.isAssignableFrom(child.getValue().getClass())) {
                child.parent = null;
                this.children.remove(i);
            }
        }
    }
    
    /**
     * Retrieves the children of the node
     * 
     * @return the AbstractTreeNode List
     **/
    public List<T> getChildNodes() {
        // If we want this list to be modifiable,
        // we'd need a special List implementation
        // that clears a node's parent when one is removed
        // and assigns the parent when a node is added
        return Util.unmodifiableList(this.children);
    }
    
    /**
     * Retrieves the last child of this node
     * 
     * @return the last child
     **/
    public T getLastChild() {
        return Util.getLast(this.children);
    }
    
    /**
     * Retrieves the first child of this node
     * 
     * @return the first child
     **/
    public T getFirstChild() {
        return Util.get(this.children, 0);
    }
    
    /**
     * Sorts the child values with the given Comparator
     * 
     * @param cmp the Comparator
     */
    public void sortChildValues(final Comparator<V> cmp) {
        if (this.children == null) {
            return;
        }
        Collections.sort(this.children, new TreeNodeComparator(cmp));
    }
    
    private class TreeNodeComparator implements Comparator<T> {
        
        private final Comparator<V> cmp;
        
        private TreeNodeComparator(final Comparator<V> cmp) {
            this.cmp = cmp;
        }
        
        /**
         * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
         */
        @Override
        public final int compare(final T n1, final T n2) {
            if (n1 == null) {
                return n2 == null ? 0 : 1;
            } else if (n2 == null) {
                return -1;
            }
            return this.cmp.compare(n1.getValue(), n2.getValue());
        }
    }
    
    // Iterates through all nodes of the subtree starting at the given root in preorder (depth-first traversal)
    private final class DescendantNodeIterator extends LookAheadIterator<T> {
        
        private final T root;
        
        private T prev = null;
        
        public DescendantNodeIterator() {
            this.root = Util.cast(AbstractTreeNode.this);
            this.next = null;
        }
        
        @Override
        protected final void prepare() {
            this.prev = this.next;
            if (this.next == null) {
                this.next = this.root;
            } else if (this.next.isLeaf()) {
                prepareNonChild();
            } else {
                this.next = this.next.getFirstChild();
            }
            prepareHasNext();
        }
        
        private final void prepareHasNext() {
            this.hasNext = this.next != null;
        }
        
        private final void prepareNonChild() {
            T tree = this.next;
            this.next = null;
            while (this.next == null) {
                if (tree == this.root) {
                    break;
                }
                this.next = tree.getNextSibling();
                tree = tree.getParent();
            }
            prepareHasNext();
        }
        
        @Override
        public final void removePrevious() {
            if (this.prev == null) { /* || !this.root.contains(this.prev) not needed since we null out prev */
                throw new IllegalStateException();
            } else if (this.prev.contains(this.next)) {
                this.next = this.prev;
                prepareNonChild();
            }
            this.prev.detach();
            this.prev = null;
        }
    }
    
    private class DescendantNodeIterable implements Iterable<T> {
        
        @Override
        public Iterator<T> iterator() {
            return new DescendantNodeIterator();
        }
    }
    
    /**
     * Retrieves all descendant nodes
     * 
     * @return the descendant nodes
     */
    public Iterable<T> getDescendantNodes() {
        return new DescendantNodeIterable();
    }
    
    private Iterable<V> getValues(final Iterable<T> nodes) {
        return ConverterIterable.create(nodes, new Converter<T, V>() {
            
            @Override
            public V convert(final T t) {
                return t.getValue();
            }
        });
    }
    
    /**
     * Retrieves all child values
     * 
     * @return the child values
     */
    public Iterable<V> getChildValues() {
        // Instead of creating a new List with all of the values (could be a big List), we create one small Object backed by the actual child List
        return getValues(this.children);
    }
    
    /**
     * Retrieves all descendant values
     * 
     * @return the descendant values
     */
    public Iterable<V> getDescendantValues() {
        return getValues(getDescendantNodes());
    }
    
    /**
     * Retrieves the first child value of the given Class
     * 
     * @param c the Class
     * @return the child
     */
    public <D extends V> D getChildValue(final Class<D> c) {
        return Util.cast(getValue(getChildNode(c)));
    }
    
    /**
     * Retrieves the first child node of the given Class
     * 
     * @param c the Class
     * @return the child
     */
    public T getChildNode(final Class<? extends V> c) {
        for (final T node : Util.unNull(this.children)) {
            if (c.isAssignableFrom(node.value.getClass())) {
                return node;
            }
        }
        
        return null;
    }
    
    /**
     * Retrieves the first descendant value of the given Class
     * 
     * @param c the Class
     * @return the descendant
     */
    public <D extends V> D getDescendantValue(final Class<D> c) {
        return Util.cast(getValue(getDescendantNode(c)));
    }
    
    /**
     * Retrieves the first descendant node of the given Class
     * 
     * @param c the Class
     * @return the descendant
     */
    public T getDescendantNode(final Class<? extends V> c) {
        if (c.isAssignableFrom(this.value.getClass())) {
            return Util.cast(this);
        }
        
        for (T node : Util.unNull(this.children)) {
            node = node.getDescendantNode(c);
            if (node != null) {
                return node;
            }
        }
        
        return null;
    }
    
    private static class ValueAssignableFromCriterion implements Criterion {
        
        private final Class<?> c;
        
        /**
         * Constructs a new ValueAssignableFromCriterion for the given Class
         * 
         * @param c the Class
         **/
        private ValueAssignableFromCriterion(final Class<?> c) {
            this.c = c;
        }
        
        /**
         * Retrieves an ValueAssignableFromCriterion for the given Class
         * 
         * @param c the Class
         * @return the ValueAssignableFromCriterion
         **/
        public final static ValueAssignableFromCriterion getInstance(final Class<?> c) {
            return new ValueAssignableFromCriterion(c); // Could cache these
        }
        
        /**
         * Retrieves whether the Criterion is met (the Object is an instance of the Criterion's
         * desired Class)
         * 
         * @param o the Object to test
         * @return whether the Criterion is met
         **/
        @Override
        public boolean isMet(final Object o) {
            return o == null ? false : this.c.isAssignableFrom(((AbstractTreeNode<?, ?>) o).getValue().getClass());
        }
    }
    
    /**
     * Retrieves all child nodes of the given Class
     * 
     * @param c the Class
     * @return the children
     */
    public <D extends V> Iterable<T> getChildNodes(final Class<D> c) {
        return CriterionIterable.create(getChildNodes(), ValueAssignableFromCriterion.getInstance(c));
    }
    
    /**
     * Retrieves all child values of the given Class
     * 
     * @param c the Class
     * @return the children
     */
    public <D extends V> Iterable<D> getChildValues(final Class<D> c) {
        final Converter<V, D> converter = CastConverter.getInstance();
        
        return ConverterIterable.create(CriterionIterable.create(getChildValues(), AssignableFromCriterion.getInstance(c)),
            converter);
    }
    
    /**
     * Retrieves all descendant nodes of the given Class
     * 
     * @param c the Class
     * @return the descendants
     */
    public <D extends V> Iterable<T> getDescendantNodes(final Class<D> c) {
        return CriterionIterable.create(getDescendantNodes(), ValueAssignableFromCriterion.getInstance(c));
    }
    
    /**
     * Retrieves all descendant values of the given Class
     * 
     * @param c the Class
     * @return the descendants
     */
    public <D extends V> Iterable<D> getDescendantValues(final Class<D> c) {
        final Converter<V, D> converter = CastConverter.getInstance();
        final AssignableFromCriterion crit = AssignableFromCriterion.getInstance(c);
        return ConverterIterable.create(CriterionIterable.create(getDescendantValues(), crit), converter);
    }
    
    /**
     * Finds the node containing the given value
     * 
     * @param value the value
     * @return the node
     */
    public T find(final V value) {
        if (Util.equals(this.value, value)) {
            return Util.cast(this);
        }
        
        for (T child : Util.unNull(this.children)) {
            child = child.find(value);
            if (child != null) {
                return child;
            }
        }
        
        return null;
    }
    
    /**
     * Retrieves whether the tree contains the given value
     * 
     * @param value the value
     * @return whether the tree contains the given value
     */
    public boolean contains(final V value) {
        return find(value) != null;
    }
    
    /**
     * Retrieves whether the tree contains the given node
     * 
     * @param node the node
     * @return whether the tree contains the given node
     */
    public boolean contains(final T node) {
        T n = node;
        while (n != null) {
            if (this == n) {
                return true;
            }
            n = n.getParent();
        }
        /*
        if (this == node) {
            return true;
        }
        // Should be faster to search the given node's ancestors than this node's descendants
        for (final T child : Util.unNull(this.children)) {
            if (child.contains(node)) {
                return true;
            }
        }
        */
        return false;
    }
    
    /**
     * Retrieves the size of the tree
     * 
     * @return the size
     */
    public int size() {
        final int n = Util.size(this.children);
        int size = 1;
        
        for (int i = 0; i < n; i++) {
            size += this.children.get(i).size();
        }
        
        return size;
    }
    
    public List<V> toListDepthFirst() {
        return Util.toList(getDescendantValues());
    }
    
    public List<V> toListBreadthFirst() {
        final Queue<AbstractTreeNode<V, T>> q = new LinkedList<AbstractTreeNode<V, T>>();
        final List<V> ret = new ArrayList<V>();
        q.add(this);
        AbstractTreeNode<V, T> curr;
        while ((curr = q.poll()) != null) {
            ret.add(curr.getValue());
            for (final AbstractTreeNode<V, T> child : Util.unNull(curr.getChildNodes())) {
                q.add(child);
            }
        }
        return ret;
    }
    
    /**
     * Retrieves the String representation
     * 
     * @return the String representation
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        toString(sb);
        return sb.toString();
    }
    
    protected void toString(final StringBuilder sb) {
        // Format similar to AbstractCollection.toString()
        sb.append('[');
        sb.append(this.value);
        final int size = Util.size(this.children);
        if (size > 0) {
            sb.append(' ');
            for (int i = 0; i < size; i++) {
                if (i > 0) {
                    sb.append(", ");
                }
                this.children.get(i).toString(sb);
            }
        }
        sb.append(']');
    }
    
    public static <V, T extends AbstractTreeNode<V, T>> V getValue(final T node) {
        return node == null ? null : node.getValue();
    }
}
