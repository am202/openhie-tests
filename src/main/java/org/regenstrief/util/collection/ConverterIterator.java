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

import org.regenstrief.util.convert.Converter;

/**
 * <p>
 * Title: ConverterIterator
 * </p>
 * <p>
 * Description: Wraps an Iterator, applyinging a given Converter to each of its elements
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
 * @param <S> the source type
 * @param <D> the destination type
 */
public class ConverterIterator<S, D> implements Iterator<D> {
    
    protected Iterator<S> iter;
    
    protected Converter<S, D> converter;
    
    /**
     * Constructs a new ConverterIterator
     * 
     * @param iter the Iterator to wrap
     * @param converter the Converter
     **/
    public ConverterIterator(final Iterator<S> iter, final Converter<S, D> converter) {
        this.iter = iter;
        this.converter = converter;
    }
    
    /**
     * Constructs a new ConverterIterator
     * 
     * @param iterable the Iterable to wrap
     * @param converter the Converter
     **/
    public ConverterIterator(final Iterable<S> iterable, final Converter<S, D> converter) {
        this(iterable.iterator(), converter);
    }
    
    /**
     * Retrieves whether the source Iterator has more elements
     * 
     * @return whether the source Iterator has more elements
     **/
    @Override
    public boolean hasNext() {
        return this.iter.hasNext();
    }
    
    /**
     * Retrieves the next element of the source Iterator after conversion
     * 
     * @return the next element of the source Iterator after conversion
     **/
    @Override
    public D next() {
        return this.converter.convert(this.iter.next());
    }
    
    /**
     * Removes the last element returned by the ConverterIterator from the source Iterator's
     * underlying Iterable
     **/
    @Override
    public void remove() {
        this.iter.remove();
    }
}
