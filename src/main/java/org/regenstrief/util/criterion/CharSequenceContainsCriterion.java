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

import org.regenstrief.util.CharSequences;

/**
 * CharSequenceContainsCriterion
 */
public final class CharSequenceContainsCriterion implements Criterion {
    
    private final String sub;
    
    private final boolean ignCase;
    
    public CharSequenceContainsCriterion(final String sub, final boolean ignCase) {
        this.sub = sub;
        this.ignCase = ignCase;
    }

    /**
     * @see org.regenstrief.util.criterion.Criterion#isMet(java.lang.Object)
     */
    @Override
    public final boolean isMet(final Object o) {
        return CharSequences.indexOf(CharSequences.toCharSequence(o), this.sub, this.ignCase) >= 0;
    }
}
