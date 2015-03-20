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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.regenstrief.util.Util;

/**
 * <p>
 * Title: Criterion
 * </p>
 * <p>
 * Description: Criterion implementation that evaluates multiple Criterion implementations
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
public class Criteria implements Criterion {
    
    private final static Criterion[] empty = {};
    
    private final Criterion c[];
    
    private final boolean all;
    
    /**
     * Constructs a new Criteria
     * 
     * @param all whether all must be met
     * @param c the Criterion implementations
     **/
    public Criteria(final boolean all, final Criterion... c) {
        this.c = c;
        this.all = all;
    }
    
    /**
     * Constructs a new Criteria
     * 
     * @param c the Criterion implementations
     **/
    public Criteria(final Criterion... c) {
        this(true, c);
    }
    
    public final static Criterion getCriterion(Criterion... c) {
        int size = Util.length(c);
        if (size == 0) {
            return null;
        }
        for (final Criterion x : c) {
            if (x == null) {
                c = toArray(Util.trim(new ArrayList<Criterion>(Arrays.asList(c))));
                break;
            }
        }
        size = Util.length(c);
        if (size == 0) {
            return null;
        } else if (size == 1) {
            return c[0];
        }
        return new Criteria(c);
    }
    
    public final static Criterion getCriterion(final Collection<Criterion> c) {
        return getCriterion(toArray(c)); // XMLUtil.parsePath depends on this copying the Collection
    }
    
    private final static Criterion[] toArray(final Collection<Criterion> c) {
        return c == null ? null : c.toArray(empty);
    }
    
    /**
     * Retrieves whether the Criterion implementations are met
     * 
     * @param o the Object to test
     * @return whether the Criterion implementations are met
     **/
    @Override
    public final boolean isMet(final Object o) {
        return this.all ? allMet(o) : anyMet(o);
    }
    
    /**
     * Retrieves whether all of the Criterion implementations are met
     * 
     * @param o the Object to test
     * @return whether the Criterion implementations are met
     **/
    private final boolean allMet(final Object o) {
        for (final Criterion c : this.c) {
            if (!c.isMet(o)) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Retrieves whether any of the Criterion implementations are met
     * 
     * @param o the Object to test
     * @return whether the Criterion implementations are met
     **/
    private final boolean anyMet(final Object o) {
        for (final Criterion c : this.c) {
            if (c.isMet(o)) {
                return true;
            }
        }
        
        return false;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        boolean first = true;
        sb.append('(');
        for (final Criterion criterion : this.c) {
            if (first) {
                first = false;
            } else {
                sb.append(' ');
                sb.append(this.all ? "&&" : "||");
                sb.append(' ');
            }
            sb.append(criterion.toString());
        }
        sb.append(')');
        return sb.toString();
    }
}
