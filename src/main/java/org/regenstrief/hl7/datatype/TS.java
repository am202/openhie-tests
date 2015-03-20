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
package org.regenstrief.hl7.datatype;

import java.io.IOException;
import java.io.Writer;
import java.util.Calendar;
import java.util.Date;

import org.regenstrief.hl7.HL7Parser;
import org.regenstrief.hl7.HL7Properties;
import org.regenstrief.hl7.MessageProperties;
import org.regenstrief.hl7.util.AbstractDateData;
import org.regenstrief.hl7.util.DateData;
import org.regenstrief.util.StopWatch;

/**
 * <p>
 * Title: TS
 * </p>
 * <p>
 * Description: HL7 Time Stamp
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Regenstrief Institute
 * </p>
 * 
 * @author Andrew Martin
 * @version 1.0
 */
public class TS extends AbstractDateData {
    
    // YYYY[MM[DD[HHMM[SS[.S[S[S[S]]]]]]]][+/-ZZZZ]
    // the HHMM part set to "0000" represents midnight of the
    // night extending from the previous day to the day given by the YYYYMMDD part
    public final static String TS_XML = "TS";
    
    public final static String TIME_XML = "TS.1";
    
    public final static String DEGREE_OF_PRECISION_XML = "TS.2";
    
    //private static Calendar cal = Calendar.getInstance();
    
    private String degreeOfPrecision = null; //<degree of precision>
    
    /**
     * Constructs an empty TS
     * 
     * @param prop the HL7Properties
     **/
    public TS(final HL7Properties prop) {
        super(prop);
    }
    
    /**
     * Constructs a new TS from the given timestamp String
     * 
     * @param prop the HL7Properties
     * @param time the timestamp String
     **/
    public TS(final HL7Properties prop, final String time) {
        this(prop);
        this.value = time;
    }
    
    /**
     * Constructs a new TS from the given Calendar
     * 
     * @param prop the HL7Properties
     * @param dt the Calendar
     **/
    public TS(final HL7Properties prop, final Calendar dt) {
        this(prop);
        setTime(dt);
    }
    
    /**
     * Assigns the TS value from the given Calendar
     * 
     * @param dt the Calendar
     **/
    private void setTime(final Calendar dt) {
        this.value = StopWatch.toHL7String(dt);
    }
    
    /**
     * Constructs a new TS from the given Date
     * 
     * @param prop the HL7Properties
     * @param date the Date
     **/
    public TS(final HL7Properties prop, final Date date) {
        this(prop);
        
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        setTime(cal);
    }
    
    /**
     * Constructs a new TS from the given Date
     * 
     * @param prop the HL7Properties
     * @param date the Date
     * @return the TS
     **/
    public static TS create(final HL7Properties prop, final Date date) {
        return date == null ? null : new TS(prop, date);
    }
    
    /**
     * Constructs a new TS from the given timestamp String
     * 
     * @param prop the HL7Properties
     * @param time the timestamp String
     * @return the TS
     **/
    public static TS create(final HL7Properties prop, final String time) {
        return time == null ? null : new TS(prop, time);
    }
    
    /**
     * Constructs a new TS from the given DateData
     * 
     * @param dd the DateData
     * @return the TS
     **/
    public static TS create(final DateData dd) {
        return dd == null ? null : create(dd.getProp(), dd.getValue());
    }
    
    /**
     * Retrieves a cached TS if the time is the same, or constructs a new one
     * 
     * @param prop the HL7Properties
     * @param date the Date
     * @return the TS
     **/
    public static TS createFromOrder(final HL7Properties prop, final Date date) {
        final long time = date.getTime();
        
        if (prop != null) {
            final MessageProperties mp = prop.getMessageProp();
            if ((mp != null) && (mp.getPhysiologicTime() != null) && (time == mp.getPhysiologicTime().getTime())) {
                return mp.getObservationDateTime();
            }
        }
        
        return new TS(prop, date);
    }
    
    public static TS parsePiped(final HL7Parser parser, final String line, final int start, final char delim, final int stop) {
        if (stop <= start) {
            return null;
        }
        final TS ts = new TS(parser);
        ts.readPiped(parser, line, start, delim, stop);
        return ts;
    }
    
    @Override
    public void readPiped(final HL7Parser parser, final String line, int start, final char delim, final int stop) {
        int next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.value = getToken(line, start, next);
        
        start = next + 1;
        next = getNext(line, start, delim, stop);
        if (stop < start) {
            return;
        }
        this.degreeOfPrecision = getToken(line, start, next);
        
        assertLast(line, next + 1, delim, stop);
    }
    
    @Override
    public void toPiped(final Writer w, final int level) throws IOException {
        int last = 1;
        
        last = addComponent(w, this.value, last, 1, level);
        last = addComponent(w, this.degreeOfPrecision, last, 2, level);
    }
    
    /**
     * Retrieves the degree of precision
     * 
     * @return the degree of precision
     **/
    public String getDegreeOfPrecision() {
        return this.degreeOfPrecision;
    }
    
    /**
     * Modifies the degree of precision
     * 
     * @param degreeOfPrecision the new degree of precision
     **/
    public void setDegreeOfPrecision(final String degreeOfPrecision) {
        this.degreeOfPrecision = degreeOfPrecision;
    }
    
    /**
     * Clears the TS
     **/
    @Override
    public void clear() {
        super.clear();
        this.degreeOfPrecision = null;
    }
}
