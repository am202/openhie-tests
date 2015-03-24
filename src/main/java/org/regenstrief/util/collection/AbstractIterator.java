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

/**
 * <p>
 * Title: AbstractIterator
 * </p>
 * <p>
 * Description: Provides a remove() implementation for subclasses which do not support removing
 * elements
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
public abstract class AbstractIterator<E> implements Iterator<E> {
    
    /**
     * Throws an UnsupportedOperationException
     **/
    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
