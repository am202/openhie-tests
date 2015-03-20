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
package org.regenstrief.util;

import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>
 * Title: Stop Watch
 * </p>
 * <p>
 * Description: Stop watch for timing events
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
public class StopWatch {
    
    private static final Log log = LogFactory.getLog(StopWatch.class);
    
    /**
     * Pattern for formatting a time as a String
     */
    public static final String FORMAT_STRING = "hr:min:sec.ms";
    
    /**
     * The number of milliseconds per minute
     */
    public static final int MILLIS_PER_MINUTE = 1000 * 60;
    
    /**
     * The number of milliseconds per hour
     */
    public static final int MILLIS_PER_HOUR = MILLIS_PER_MINUTE * 60;
    
    /**
     * The number of milliseconds per day
     */
    public static final long MILLIS_PER_DAY = MILLIS_PER_HOUR * 24l;
    
    protected static final long BAD_TIME = -1;
    
    protected long startTime = BAD_TIME;
    
    protected long totalTime = 0;
    
    /**
     * Construct a stop watch
     **/
    public StopWatch() {
    }
    
    /**
     * Construct a stop watch, starting it if desired
     * 
     * @param start true if the watch should be started right away
     **/
    public StopWatch(final boolean start) {
        if (start) {
            start();
        }
    }

    /**
     * Resets the stop watch
     **/
    public void reset() {
        this.startTime = BAD_TIME;
        this.totalTime = 0;
    }
    
    /**
     * Starts the clock
     **/
    public void start() {
        this.startTime = currentTime();
    }
    
    /**
     * Resets the stop watch and starts the clock
     **/
    public void startOver() {
        this.totalTime = 0;
        start();
    }
    
    /**
     * Determines if the stop watch is running
     * 
     * @return true if the stop watch is running
     **/
    public boolean isRunning() {
        return this.startTime != BAD_TIME;
    }
    
    /**
     * Stops the stop watch, updating the total time
     **/
    public void stop() {
        if (this.startTime == BAD_TIME) {
            return;
        }
        this.totalTime += (currentTime() - this.startTime);
        this.startTime = BAD_TIME;
    }
    
    /**
     * Stops the stop watch and prints the total time
     **/
    public void stopAndPrint() {
        stop();
        log.info(new Long(this.totalTime));
    }
    
    /**
     * Stops the stop watch and prints the total time
     * 
     * @param message the message to print with the time
     **/
    public void stopAndPrint(final String message) {
        stop();
        log.info(message + ": " + this.totalTime);
    }
    
    /**
     * Returns the total time that the clock has been running, the sum of intervals between starting
     * and stopping the watch
     * 
     * @return the time that the clock has been running
     **/
    public long getTotal() {
        return this.totalTime;
    }
    
    /**
     * Returns the total time that the clock has been running in seconds
     * 
     * @return the total time that the clock has been running in seconds
     **/
    public double getTotalSeconds() {
        return this.totalTime / 1000.0;
    }
    
    /**
     * Returns the total time that the clock has been running in hr:min:sec.ms format
     * 
     * @return the total time that the clock has been running in hr:min:sec.ms format
     **/
    public String getTotalFormatted() {
        return getFormatted(this.totalTime);
    }
    
    /**
     * Stops the given StopWatch if it is valued
     * 
     * @param w the StopWatch
     */
    public final static void stop(final StopWatch w) {
        if (w != null) {
            w.stop();
        }
    }
    
    /**
     * Returns the given time in hr:min:sec.ms format
     * 
     * @param time the time to convert
     * @return the given time in hr:min:sec.ms format
     **/
    public static String getFormatted(final long time) {
        final int ms = (int) (time % 1000);
        final int ts = (int) (time / 1000);
        final int s = ts % 60;
        final int tm = ts / 60;
        final int m = tm % 60;
        final int h = tm / 60;
        return Util.to2DigitString(h) + ":" + Util.to2DigitString(m) + ":" + Util.to2DigitString(s) + "."
                + Util.to3DigitString(ms);
    }
    
    /**
     * Returns the current time of day in milliseconds
     * 
     * @return the current time of day in milliseconds
     **/
    public static long currentTime() {
        return System.currentTimeMillis();
    }
    
    /**
     * Returns the current time in a displayable String
     * 
     * @return the current time in a displayable String
     **/
    public static String currentTimeString() {
        return (new java.util.Date()).toString();
    }
    
    /**
     * Returns the current time in an HL7 String (YYYY[MM[DD[HHMM[SS[.S[S[S[S]]]]]]]]) This
     * functionality was once in the HL7 module, but it's a good format for use elsewhere, succinct
     * and good for sorting
     * 
     * @return the current time in an HL7 String
     **/
    public static String currentTimeHL7String() {
        return toHL7String(Calendar.getInstance());
    }
    
    /**
     * Converts the given Date to an HL7 String (YYYY[MM[DD[HHMM[SS[.S[S[S[S]]]]]]]])
     * 
     * @param d the Date
     * @return the Date an an HL7 String
     **/
    public static String toHL7String(final Date d) {
        return toHL7String(Dates.toCalendar(d));
    }
    
    /**
     * Converts the given Date to an HL7 String (YYYY[MM[DD[HHMM[SS[.S[S[S[S]]]]]]]])
     * 
     * @param d the Date
     * @param timeZone whether to include the time zone
     * @return the Date an an HL7 String
     **/
    public static String toHL7String(final Date d, final boolean timeZone) {
        return toHL7String(Dates.toCalendar(d), timeZone);
    }
    
    /**
     * Converts the given Calendar to an HL7 String (YYYY[MM[DD[HHMM[SS[.S[S[S[S]]]]]]]])
     * 
     * @param cal the Calendar
     * @return the Calendar an an HL7 String
     **/
    public static String toHL7String(final Calendar cal) {
        return toHL7String(cal, false);
    }
    
    /**
     * Converts the given Calendar to an HL7 String (YYYY[MM[DD[HHMM[SS[.S[S[S[S]]]]]]]])
     * 
     * @param cal the Calendar
     * @param timeZone whether to include the time zone
     * @return the Calendar an an HL7 String
     **/
    public static String toHL7String(final Calendar cal, final boolean timeZone) {
        long time;
        String value;
        
        if (cal == null) {
            return null;
        }
        
        final int day = cal.get(Calendar.DAY_OF_MONTH);
        final int hour = cal.get(Calendar.HOUR_OF_DAY);
        final int minute = cal.get(Calendar.MINUTE);
        final int second = cal.get(Calendar.SECOND);
        final int milli = cal.get(Calendar.MILLISECOND);
        time = cal.get(Calendar.YEAR);
        time = time * 100 + cal.get(Calendar.MONTH) + 1;
        final boolean isMilli = (milli > 0) || timeZone;
        final boolean isSecond = (second > 0) || isMilli;
        final boolean isMinute = (hour > 0) || (minute > 0) || isSecond;
        final boolean isDay = (day > 0) || isMinute;
        if (isDay) {
            time = time * 100 + day;
            if (isMinute) {
                time = time * 100 + hour;
                time = time * 100 + minute;
                if (isSecond) {
                    time = time * 100 + second;
                }
            }
        }
        
        value = String.valueOf(time);
        
        if (isMilli) {
            value = value + '.' + Util.to3DigitString(milli);
        }
        
        if (timeZone) {
            //cal.getTimeZone().getOffset(date)
            //cal.getTimeZone().getRawOffset()
            final int off = cal.get(Calendar.ZONE_OFFSET); // Used by Java SIG
            final int tz = Math.abs(off);
            final int hours = tz / MILLIS_PER_HOUR;
            value = value + (off < 0 ? '-' : '+') + Util.to2DigitString(hours) + Util.to2DigitString((tz - (hours * MILLIS_PER_HOUR)) / MILLIS_PER_MINUTE);
        }
        
        return value;
    }
    
    /**
     * Sleeps for the given number of milliseconds, continuing to wait if interrupted
     * 
     * @param millis the number of milliseconds to sleep
     **/
    public static void sleep(long millis) {
        final long stop = currentTime() + millis;
        while (true) {
            try {
                Thread.sleep(millis);
            } catch (final Exception e) {
            }
            final long curr = currentTime();
            if (curr >= stop) {
                break;
            }
            millis = stop - curr;
        }
    }
    
    /**
     * Sleeps for the given number of milliseconds, returning without an Exception if interrupted
     * 
     * @param millis the number of milliseconds to sleep
     **/
    public static void sleepQuiet(final long millis) {
        try {
            Thread.sleep(millis);
        } catch (final Exception e) {}
    }
    
    /**
     * Causes the given Runnable to run every midnight, useful for servers that run constantly.
     * 
     * @param task the Runnable
     * @return the Timer running the task
     **/
    public final static java.util.Timer scheduleNightlyTask(final Runnable task) {
        final Runnable r = task;
        
        return scheduleNightlyTask(new TimerTask() {
            
            @Override
            public void run() {
                r.run();
            }
        });
    }
    
    /**
     * Causes the given TimerTask to run every midnight, useful for servers that run constantly.
     * 
     * @param task the TimerTask
     * @return the Timer running the task
     **/
    public final static java.util.Timer scheduleNightlyTask(final TimerTask task) {
        final java.util.Timer timer = new java.util.Timer(true);
        final Calendar start = Calendar.getInstance();
        
        start.setTimeInMillis(start.getTimeInMillis() + MILLIS_PER_DAY);
        start.set(Calendar.HOUR_OF_DAY, 0);
        start.set(Calendar.MINUTE, 0);
        // Might want to parameterize start time and frequency
        timer.scheduleAtFixedRate(task, start.getTime(), MILLIS_PER_DAY);
        
        return timer;
    }
}
