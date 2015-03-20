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
import java.util.Collection;
import java.util.Iterator;

import org.regenstrief.util.Util;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * <p>
 * Title: NodeArrayList
 * </p>
 * <p>
 * Description: NodeList implementation that extends ArrayList
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
// Maybe this should extend ArrayList<N extends Node>
// That would allow NodeArrayList<Element>, which might be useful in a lot of places
public class NodeArrayList extends ArrayList<Node> implements NodeList {
    
    static final long serialVersionUID = -3707261648319852910L;
    
    public final static NodeArrayList EMPTY = new EmptyNodeArrayList();
    
    /**
     * Constructs an empty NodeArrayList
     **/
    public NodeArrayList() {
        super();
    }
    
    /**
     * Constructs a NodeArrayList with the given initial capacity
     * 
     * @param initialCapacity the initial capacity int
     **/
    public NodeArrayList(final int initialCapacity) {
        super(initialCapacity);
    }
    
    /**
     * Constructs a NodeArrayList containing the elements of the given Collection
     * 
     * @param c the Collection
     **/
    public NodeArrayList(final Collection<Node> c) {
        super(c);
    }
    
    /**
     * Constructs a NodeArrayLIst containing the elements of the given NodeList
     * 
     * @param list the NodeList
     **/
    public NodeArrayList(final NodeList list) {
        this(list.getLength());
        
        int i = 0;
        final int size = list.getLength();
        
        for (i = 0; i < size; i++) {
            add(list.item(i));
        }
    }
    
    /**
     * Constructs a NodeArrayLIst containing the elements of the given Iterator
     * 
     * @param i the Iterator
     **/
    public NodeArrayList(final Iterator<Node> i) {
        this();
        Util.list(i, this);
    }
    
    /**
     * Retrieves the length
     * 
     * @return the length int
     **/
    @Override
    public int getLength() {
        return size();
    }
    
    /**
     * Retrieves the item at the desired index
     * 
     * @param i the index int
     * @return the item Node
     **/
    @Override
    public Node item(final int i) {
        return get(i);
    }
    
    private final static class EmptyNodeArrayList extends NodeArrayList {
        
        private static final long serialVersionUID = 8524912167151888975L;

        private EmptyNodeArrayList() {
            super(0);
        }
        
        @Override
        public Node set(int index, Node element) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean add(Node e) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void add(int index, Node element) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public Node remove(int index) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean remove(Object o) {
            throw new UnsupportedOperationException();
        }
        
        /*
        Iterator allows remove:
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
        ListIterator allows set/add
        */
        
        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean addAll(Collection<? extends Node> c) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean addAll(int index, Collection<? extends Node> c) {
            throw new UnsupportedOperationException();
        }
        
        /*
        @Override
        public List<Node> subList(int fromIndex, int toIndex) {
            // subList allows writes to underlying List, but will use underlying methods which already throw this
            throw new UnsupportedOperationException();
        }
        */
    }
}
