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
package org.regenstrief.hl7.util;

import org.regenstrief.hl7.datatype.CE;
import org.regenstrief.hl7.datatype.CQ;
import org.regenstrief.util.Util;

/**
 * <p>
 * Title: Duration
 * </p>
 * <p>
 * Description: A parsed duration (such as TQ.3 or RXE.22)
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: Regenstrief Institute
 * </p>
 * 
 * @author Andrew Martin
 * @version 1.0
 */
public class Duration {
    
    public final static String INDEF = "INDEF";
    
    public final static char SECONDS = 'S';
    
    public final static char MINUTES = 'M';
    
    public final static char HOURS = 'H';
    
    public final static char DAYS = 'D';
    
    public final static char WEEKS = 'W';
    
    public final static char MONTHS = 'L';
    
    public final static char TIMES = 'X'; // <integer> times at interval specified in the order
    
    public final static char TOTAL = 'T'; // At the interval and amount stated until a total of <integer> "DOSAGE" is accumulated
    
    public final static int DEFAULT_AMOUNT = -1;
    
    public final static char DEFAULT_UNITS = ' ';
    
    private int amount = DEFAULT_AMOUNT;
    
    private char units = DEFAULT_UNITS;
    
    public Duration() {
    }
    
    public Duration(final char units, final int amount) {
        this.units = units;
        this.amount = amount;
    }
    
    /**
     * Creates a new Duration
     * 
     * @param duration the duration value
     * @return the Duration
     **/
    public final static Duration create(final String duration) {
        if (Util.isEmpty(duration)) {
            return null;
        }
        
        final Duration d = new Duration();
        d.setDuration(duration);
        
        return d;
    }
    
    /**
     * Creates a new Duration
     * 
     * @param cq the duration CQ
     * @return the Duration
     **/
    public final static Duration create(final CQ cq) {
        if (cq == null) {
            return null;
        }
        
        final Duration d = new Duration();
        final Double dub = cq.toDouble();
        final CE ce = cq.getUnits();
        final String unit = CE.toCode(ce);
        if ((dub == null) || Util.isEmpty(unit)) {
            return null;
        }
        final double value = dub.doubleValue();
        d.amount = (int) value;
        final double tmp = d.amount;
        if (tmp != value) {
            return null; // If double didn't contain an int, don't truncate it
        }
        if ("S".equals(unit)) {
            d.units = SECONDS;
        } else if ("MIN".equals(unit)) {
            d.units = MINUTES;
        } else if ("HR".equals(unit)) {
            d.units = HOURS;
        } else if ("D".equals(unit)) {
            d.units = DAYS;
        } else {
            return null;
        }
        
        return d;
    }
    
    /**
     * Retrieves the duration as a String
     * 
     * @return the duration as a String
     **/
    @Override
    public String toString() {
        return getDuration();
    }
    
    /**
     * Retrieves the duration
     * 
     * @return the duration
     **/
    public String getDuration() {
        if (isIndefinite()) {
            return INDEF;
        }
        
        return ("" + this.units) + this.amount;
    }
    
    /**
     * Modifies the duration
     * 
     * @param duration the new duration
     **/
    public void setDuration(final String duration) {
        if (duration.equals(INDEF)) {
            this.amount = DEFAULT_AMOUNT;
            this.units = DEFAULT_UNITS;
        }
        
        this.units = duration.charAt(0);
        this.amount = Integer.parseInt(duration.substring(1));
    }
    
    /**
     * Retrieves whether the duration is indefinite
     * 
     * @return whether the duration is indefinite
     **/
    public boolean isIndefinite() {
        return this.amount < 0;
    }
    
    /**
     * Retrieves the amount
     * 
     * @return the amount
     **/
    public int getAmount() {
        return this.amount;
    }
    
    /**
     * Modifies the amount
     * 
     * @param amount the new amount
     **/
    public void setAmount(final int amount) {
        this.amount = amount;
    }
    
    /**
     * Retrieves the units
     * 
     * @return the units
     **/
    public char getUnits() {
        return this.units;
    }
    
    /**
     * Modifies the units
     * 
     * @param units the new units
     **/
    public void setUnits(final char units) {
        this.units = units;
    }
    
    public final static Integer getNumberOfDaysSupply(final String dur) {
        // https://tools.regenstrief.org/jira/browse/CORE-607
        /*
         * Health Level Seven, Version 2.6 Copyright (c) 2007. Page 2-179
         * S<integer> <integer> seconds
         * M<integer> <integer> minutes
         * H<integer> <integer> hours
         * D<integer> <integer> days
         * W<integer> <integer> weeks
         * L<integer> <integer> months
         */
        /*
         * From: Anne Belsito 
         * Sent: Monday, November 08, 2010 3:25 PM
         * MONTHS 30
         * Days 1
         * Weeks 7
         */
        final int size = Util.length(dur);
        if (size > 1) {
            final int mult;
            switch (dur.charAt(0)) {
                case DAYS :
                    mult = 1;
                    break;
                case WEEKS :
                    mult = 7;
                    break;
                case MONTHS :
                    mult = 30;
                    break;
                default :
                    mult = 0;
            }
            if ((mult > 0) && (Util.isAllDigits(dur, 1, size))) {
                return new Integer(mult * Util.parseInt(dur, 1, size));
            }
        }
        return null;
    }
}
