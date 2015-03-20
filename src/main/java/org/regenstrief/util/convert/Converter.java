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

/**
 * <p>
 * Title: Converter
 * </p>
 * <p>
 * Description: Converts instances of a source class to instances of a destination class
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
public interface Converter<S, D> {
    
    // Should have a 2-way converter subclass.
    // Should harmonize with StringConverter.
    // Or maybe there's no need to have both methods in a single class.
    // Maybe StringSerializer and StringDeserializer could both be Converters, and we could get rid of StringConverter.
    // Should rename this or org.regenstrief.util.Converter.  Maybe one could be Transformer.
    
    /**
     * Converts the source instance to a destination instance
     * 
     * @param src the source instance
     * @return the destination instance
     **/
    public D convert(S src);
}
