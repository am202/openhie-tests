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
package org.regenstrief.util.convert;

import org.regenstrief.util.Util;

/**
 * <p>
 * Title: CastConverter
 * </p>
 * <p>
 * Description: Casts instances of a source class to instances of a destination class
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
public class CastConverter<S, D> implements Converter<S, D> {
    
    private static CastConverter<Object, Object> instance = new CastConverter<Object, Object>();
    
    /**
     * Constructs a new CastConverter (private, since we'll never need more than one)
     **/
    private CastConverter() {
    }
    
    /**
     * Retrieves the singleton CastConverter
     * 
     * @return the singleton CastConverter
     * @param <S> the source type
     * @param <D> the destination type
     **/
    public final static <S, D> CastConverter<S, D> getInstance() {
        return Util.cast(instance);
    }
    
    /**
     * Casts the source instance to a destination instance
     * 
     * @param src the source instance
     * @return the destination instance
     **/
    @Override
    public D convert(final S src) {
        return Util.cast(src);
    }
}
