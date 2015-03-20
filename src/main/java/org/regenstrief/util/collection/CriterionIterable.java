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

import java.util.Collections;
import java.util.Iterator;

import org.regenstrief.util.criterion.Criterion;

/**
 * <p>
 * Title: CriterionIterable
 * </p>
 * <p>
 * Description: Wraps an Iterable, only providing access to elements which meet a given Criterion
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
public class CriterionIterable<E> implements Iterable<E> {
    
    protected Iterable<E> iterable;
    
    protected Criterion criterion;
    
    /**
     * Constructs a new CriterionIterable
     * 
     * @param iterable the Iterable to wrap
     * @param criterion the Criterion
     **/
    public CriterionIterable(final Iterable<E> iterable, final Criterion criterion) {
        this.iterable = iterable;
        this.criterion = criterion;
    }
    
    /**
     * Constructs a new CriterionIterable
     * 
     * @param iterable the Iterable to wrap
     * @param criterion the Criterion
     * @param <E> the element type
     * @return the CriterionIterable
     **/
    public static <E> Iterable<E> create(final Iterable<E> iterable, final Criterion criterion) {
        if (iterable == null) {
            return Collections.emptyList();
        }
        
        return new CriterionIterable<E>(iterable, criterion);
    }
    
    /**
     * Retrieves a new Iterator
     * 
     * @return the Iterator
     **/
    @Override
    public Iterator<E> iterator() {
        return new CriterionIterator<E>(this.iterable, this.criterion);
    }
}
