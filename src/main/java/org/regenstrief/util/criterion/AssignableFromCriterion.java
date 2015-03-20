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
package org.regenstrief.util.criterion;

/**
 * <p>
 * Title: AssignableFromCriterion
 * </p>
 * <p>
 * Description: A Criterion that tests whether Objects are assignable from a Class
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
 */
public class AssignableFromCriterion implements Criterion {
    
    private final Class<?> c;
    
    /**
     * Constructs a new AssignableFromCriterion for the given Class
     * 
     * @param c the Class
     **/
    private AssignableFromCriterion(final Class<?> c) {
        this.c = c;
    }
    
    /**
     * Retrieves an AssignableFromCriterion for the given Class
     * 
     * @param c the Class
     * @return the AssignableFromCriterion
     **/
    public final static AssignableFromCriterion getInstance(final Class<?> c) {
        return new AssignableFromCriterion(c); // Could cache these
    }
    
    /**
     * Retrieves whether the Criterion is met (the Object is an instance of the Criterion's
     * specified Class)
     * 
     * @param o the Object to test
     * @return whether the Criterion is met
     **/
    @Override
    public boolean isMet(final Object o) {
        return o == null ? false : this.c.isAssignableFrom(o instanceof Class<?> ? (Class<?>) o : o.getClass());
    }
}
