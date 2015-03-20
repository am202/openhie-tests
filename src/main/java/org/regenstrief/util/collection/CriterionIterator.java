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

import java.util.Iterator;

import org.regenstrief.util.criterion.Criterion;

/**
 * <p>
 * Title: CriterionIterator
 * </p>
 * <p>
 * Description: Wraps an Iterator, only providing access to elements which meet a given Criterion
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
 * @param <E> the element type
 */
public class CriterionIterator<E> extends LookAheadIterator<E> {
    
    protected Iterator<E> iter;
    
    protected Criterion criterion;
    
    /**
     * Constructs a new CriterionIterator
     * 
     * @param iter the Iterator to wrap
     * @param criterion the Criterion
     **/
    public CriterionIterator(final Iterator<E> iter, final Criterion criterion) {
        this.iter = iter;
        this.criterion = criterion;
    }
    
    /**
     * Constructs a new CriterionIterator
     * 
     * @param iterable the Iterable to wrap
     * @param criterion the Criterion
     **/
    public CriterionIterator(final Iterable<E> iterable, final Criterion criterion) {
        this(iterable.iterator(), criterion);
    }
    
    /**
     * Traverses the source Iterator, looking for the next element which meets the Criterion, in
     * preparation for the next call to next() or hasNext()
     **/
    @Override
    protected void prepare() {
        this.hasNext = false;
        this.next = null;
        
        while (this.iter.hasNext()) {
            final E e = this.iter.next();
            if (this.criterion.isMet(e)) {
                this.hasNext = true;
                this.next = e;
                break;
            }
        }
    }
    
    @Override
    public final void remove() {
        if (!this.needLook) {
            throw new IllegalStateException("Must call remove right after next(), not after hasNext()");
        }
        // If we haven't looked ahead yet, then last element from CriterionIterator was last one from underlying Iterator
        this.iter.remove();
    }
}
