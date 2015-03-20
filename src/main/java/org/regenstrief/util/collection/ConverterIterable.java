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

import org.regenstrief.util.convert.Converter;

/**
 * <p>
 * Title: ConverterIterable
 * </p>
 * <p>
 * Description: Wraps an Iterable, applyinging a given Converter to each of its elements
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
public class ConverterIterable<S, D> implements Iterable<D> {
    
    protected Iterable<S> iterable;
    
    protected Converter<S, D> converter;
    
    /**
     * Constructs a new ConverterIterable
     * 
     * @param iterable the Iterable to wrap
     * @param converter the Converter
     **/
    public ConverterIterable(final Iterable<S> iterable, final Converter<S, D> converter) {
        this.iterable = iterable;
        this.converter = converter;
    }
    
    /**
     * Constructs a new ConverterIterable
     * 
     * @param iterable the Iterable to wrap
     * @param converter the Converter
     * @param <S> the source type
     * @param <D> the destination type
     * @return the ConverterIterable
     **/
    public static <S, D> Iterable<D> create(final Iterable<S> iterable, final Converter<S, D> converter) {
        if (iterable == null) {
            return Collections.emptyList();
        }
        
        return new ConverterIterable<S, D>(iterable, converter);
    }
    
    /**
     * Retrieves a new Iterator
     * 
     * @return the Iterator
     **/
    @Override
    public Iterator<D> iterator() {
        return new ConverterIterator<S, D>(this.iterable, this.converter);
    }
}
