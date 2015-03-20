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
import java.util.NoSuchElementException;

/**
 * <p>
 * Title: LookAheadIterator
 * </p>
 * <p>
 * Description: Iterator which must look ahead in order to determine if there are more elements
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
public abstract class LookAheadIterator<E> implements Iterator<E> {
    
    protected boolean needLook = true;
    
    protected boolean hasNext;
    
    protected E next;
    
    /**
     * Looks ahead for the next element in preparation for the next call to next() or hasNext()
     **/
    protected synchronized final void lookAhead() {
        if (this.needLook) {
            prepare();
            this.needLook = false;
        }
    }
    
    /**
     * Looks ahead for the next element in preparation for the next call to next() or hasNext()
     **/
    protected abstract void prepare();
    
    /**
     * Retrieves whether the source Iterator has more elements which meet the Criterion
     * 
     * @return whether the source Iterator has more elements which meet the Criterion
     **/
    @Override
    public boolean hasNext() {
        lookAhead();
        
        return this.hasNext;
    }
    
    /**
     * Retrieves the next element of the source Iterator which meets the Criterion
     * 
     * @return the next element of the source Iterator which meets the Criterion
     **/
    @Override
    public synchronized E next() {
        lookAhead();
        if (!this.hasNext) {
            throw new NoSuchElementException();
        }
        final E ret = this.next;
        this.needLook = true;
        
        return ret;
    }
    
    /**
     * Throws an UnsupportedOperationException unless overridden by a subclass
     **/
    @Override
    public void remove() {
        lookAhead();
        removePrevious();
    }
    
    protected void removePrevious() {
        // Should remove the last element returned by this CriterionIterator;
        // however, calling the source Iterator is always one place ahead of this CriterionIterator,
        // so we could not call iter.remove()
        throw new UnsupportedOperationException();
    }
}
