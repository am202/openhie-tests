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
 * Title: AntiCriterion
 * </p>
 * <p>
 * Description: A Criterion performs the opposite test of a given Criterion
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
public class AntiCriterion implements Criterion {
    
    private final Criterion c;
    
    /**
     * Constructs a new AntiCriterion for the given Criterion
     * 
     * @param c the Criterion
     **/
    private AntiCriterion(final Criterion c) {
        this.c = c;
    }
    
    /**
     * Retrieves an AntiCriterion for the given Criterion
     * 
     * @param c the Criterion
     * @return the AntiCriterion
     **/
    public final static AntiCriterion getInstance(final Criterion c) {
        return new AntiCriterion(c); // Could cache these
    }
    
    /**
     * Retrieves whether the Criterion is met (the source Criterion is not met)
     * 
     * @param o the Object to test
     * @return whether the Criterion is met (the source Criterion is not met)
     **/
    @Override
    public boolean isMet(final Object o) {
        return !this.c.isMet(o);
    }
}
