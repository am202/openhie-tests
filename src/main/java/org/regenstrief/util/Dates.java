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

import java.text.DateFormatSymbols;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Dates
 */
public final class Dates {
    
    /**
     * The default TimeZone for the application
     */
    public final static TimeZone DEFAULT_TIME_ZONE = TimeZone.getDefault();
    
    private final static String ABBREV_JAN = "Jan";
    
    private final static String ABBREV_FEB = "Feb";
    
    private final static String ABBREV_MAR = "Mar";
    
    private final static String ABBREV_APR = "Apr";
    
    private final static String ABBREV_MAY = "May";
    
    private final static String ABBREV_JUN = "Jun";
    
    private final static String ABBREV_JUL = "Jul";
    
    private final static String ABBREV_AUG = "Aug";
    
    private final static String ABBREV_SEP = "Sep";
    
    private final static String ABBREV_OCT = "Oct";
    
    private final static String ABBREV_NOV = "Nov";
    
    private final static String ABBREV_DEC = "Dec";
    
    private final static int yearNow;
    
    private final static SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
    
    private final static SimpleDateFormat formatterTime = new SimpleDateFormat("dd-MMM-yyyy hh:mm aa");
    
    private final static String[] monthName; // Get official month names
    
    private final static String[] monthNameAbbr; // Get official 3-char month abbreviations
    
    static {
        final Calendar c = Calendar.getInstance();
        yearNow = getYear(c) % 100;
        final DateFormatSymbols formatSymbols = formatter.getDateFormatSymbols();
        monthName = formatSymbols.getMonths();
        monthNameAbbr = formatSymbols.getShortMonths();
    }
    
    private Dates() {
    }
    
    /**
     * Parses the given String into a Calendar
     * 
     * @param text the String
     * @return the Calendar
     */
    public final static Calendar parseCalendar(final String text) {
        if (Util.isEmpty(text)) {
            return null;
        }
        final Calendar c = Calendar.getInstance();
        if (!setDateTime(c, text)) {
            throw new IllegalArgumentException(text);
        }
        return c;
    }
    
    /**
     * Parses the given String into a Date
     * 
     * @param text the String
     * @return the Date
     */
    public final static Date parseDate(final String text) {
        return toDate(parseCalendar(text));
    }
    
    /**
     * Parses the given HL7 String into a Calendar
     * 
     * @param text the HL7 String
     * @return the Calendar
     */
    public final static Calendar parseHL7Calendar(final String text) {
        if (Util.isEmpty(text)) {
            return null;
        }
        final Calendar c = Calendar.getInstance(DEFAULT_TIME_ZONE);
        setHL7DateTime(c, text, false);
        return c;
    }
    
    /**
     * Parses the given HL7 String into a Date
     * 
     * @param text the HL7 String
     * @return the Date
     */
    public final static Date parseHL7Date(final String text) {
        return toDate(parseHL7Calendar(text));
    }
    
    /**
     * Populates the given Calendar's date/time fields based on the given HL7 String
     * 
     * @param c the Calendar
     * @param text the HL7 String
     * @param laxUnderstanding whether to throw an Exception for bad Strings
     * @return whether the String could be parsed
     */
    public final static boolean setHL7DateTime(final Calendar c, final String text, final boolean laxUnderstanding) {
        final int size = Util.length(text);
        if (!setHL7Date(c, text, laxUnderstanding)) {
            return false;
        }
        
        try {
            if (size >= 10) {
                c.set(Calendar.HOUR_OF_DAY, Util.parseInt(text, 8, 10));
                if (size >= 12) {
                    c.set(Calendar.MINUTE, Util.parseInt(text, 10, 12));
                    if (size >= 14) {
                        c.set(Calendar.SECOND, Util.parseInt(text, 12, 14));
                        if (size >= 16) {
                            int field, i;
                            field = Util.parseInt(text, 15, size);
                            for (i = size; i < 18; i++) {
                                field = field * 10;
                            }
                            for (i = size; i > 18; i--) {
                                field = field / 10;
                            }
                            c.set(Calendar.MILLISECOND, field);
                        }
                    }
                }
                c.getTimeInMillis(); // You have to do something to dt to to make the convert work.
                //c.setTimeZone(TimeZone.getTimeZone(DEFAULT_LOCAL_TIME_ZONE));
                c.setTimeZone(DEFAULT_TIME_ZONE);
                c.getTimeInMillis();
            }
        } catch (final Exception e) {}
        return true;
    }
    
    /**
     * Populates the given Calendar's date fields based on the given HL7 String
     * 
     * @param c the Calendar
     * @param text the HL7 String
     * @param laxUnderstanding whether to throw an Exception for bad Strings
     * @return whether the String could be parsed
     */
    public final static boolean setHL7Date(final Calendar c, final String text, final boolean laxUnderstanding) {
        final int size = text.length();
        
        c.clear();
        try {
            c.set(Calendar.YEAR, Util.parseInt(text, 0, 4));
            if (size >= 6) {
                c.set(Calendar.MONTH, Util.parseInt(text, 4, 6) - 1);
                if (size >= 8) {
                    c.set(Calendar.DAY_OF_MONTH, Util.parseInt(text, 6, 8));
                }
            }
        } catch (final Exception e) {
            if (laxUnderstanding) {
                return false;
            }
            throw Util.toRuntimeException(e);
        }
        return true;
    }
    
    /**
     * Converts the given Calendar to a Date
     * 
     * @param c the Calendar
     * @return the Date
     */
    public final static Date toDate(final Calendar c) {
        if (c == null) {
            return null;
        }
        
        return c.getTime();
    }
    
    /**
     * Converts the given Date to a Calendar
     * 
     * @param d the Date
     * @return the Calendar
     */
    public final static Calendar toCalendar(final Date d) {
        if (d == null) {
            return null;
        }
        
        final Calendar c = Calendar.getInstance();
        setDate(c, d);
        
        return c;
    }
    
    private final static void setDate(final Calendar c, final Date d) {
        c.clear();
        c.setTime(d);
    }
    
    /**
     * Creates a Calendar from the given values
     * 
     * @param year the year
     * @param month the month
     * @param day the day
     * @return the Calendar
     */
    public final static Calendar createCalendar(final int year, final int month, final int day) {
        final Calendar c = Calendar.getInstance();
        setDate(c, year, month, day);
        return c;
    }
    
    /**
     * Creates a Date from the given values
     * 
     * @param year the year
     * @param month the month
     * @param day the day
     * @return the Date
     */
    public final static Date createDate(final int year, final int month, final int day) {
        return toDate(createCalendar(year, month, day));
    }
    
    public final static void clearTime(final Calendar c) {
        c.set(Calendar.HOUR_OF_DAY, 0); // Or could store day/tz, clear, and reset fields
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
    }
    
    /**
     * Truncates the given Calendar to the day, removing the time
     * 
     * @param c the Calendar
     * @return the truncated Calendar
     */
    public final static Calendar truncate(final Calendar c) {
        return c == null ? null : createCalendar(getYear(c), getMonth(c), getDay(c));
    }
    
    /**
     * Truncates the given Date to the day, removing the time
     * 
     * @param d the Date
     * @return the truncated Date
     */
    public final static Date truncate(final Date d) {
        return d == null ? null : toDate(truncate(toCalendar(d)));
    }
    
    public final static Calendar truncateDecade(final Calendar c) {
        return c == null ? null : createCalendar((getYear(c) / 10) * 10, 1, 1);
    }
    
    /**
     * Returns this DateItem truncated to the first day of the decade
     * 
     * @return the truncated DateItem
     **/
    public final static Date truncateDecade(final Date d) {
        return toDate(truncateDecade(toCalendar(d)));
    }
    
    public final static String toSimpleString(final Date d) {
        return toSimpleString(toCalendar(d));
    }
    
    public final static String toSimpleString(final Calendar c) {
        if (c == null) {
            return null;
        } else if ((c.get(Calendar.HOUR_OF_DAY) != 0) || (c.get(Calendar.MINUTE) != 0)) {
            return formatterTime.format(c.getTime());
        }
        
        final String s;
        final int thisday = getDay(c);
        if (thisday < 10) {
            s = "0" + thisday;
        } else {
            s = Integer.toString(thisday);
        }
        return s + "-" + getMonthAbbreviation(getMonth(c)) + "-" + getYear(c);
    }
    
    /**
     * Returns the date as a String in MM/DD/YYYY format
     * 
     * @return the date as a String in MM/DD/YYYY format
     **/
    public final static String toNewString(final Date d) {
        return toNewString(toCalendar(d));
    }
    
    public final static String toNewString(final Calendar c) {
        if (c == null) {
            return null;
        }
        final int thisyear = getYear(c), thismonth = getMonth(c), thisday = getDay(c);
        final StringBuilder sb = new StringBuilder(10);
        
        sb.append(thismonth).append('/');
        sb.append(thisday).append('/').append(Util.to4DigitString(thisyear));
        final int minute = c.get(Calendar.MINUTE), hour24 = c.get(Calendar.HOUR_OF_DAY);
        if ((minute > 0) || (hour24 > 0)) { // Check existence of 24-based hour
            int hour = c.get(Calendar.HOUR); // Display 12-based hour
            if (hour <= 0) {
                hour = 12;
            }
            sb.append(' ').append(hour).append(':');
            append2DigitString(sb, minute);
            final int second = c.get(Calendar.SECOND);
            if (second > 0) {
                sb.append(':');
                append2DigitString(sb, second);
            }
            sb.append(' ').append((hour24 < 12) ? "AM" : "PM");
        }
        
        return sb.toString();
    }
    
    private final static StringBuilder append2DigitString(final StringBuilder sb, final int i) {
        if (i < 10) {
            sb.append('0');
        }
        sb.append(i);
        return sb;
    }
    
    public final static String toSimpleHL7String(final Date d) {
        return toSimpleHL7String(toCalendar(d));
    }
    
    public final static String toSimpleHL7String(final Calendar c) {
        if (c == null) {
            return null;
        }
        return Integer.toString(getYear(c) * 10000 + getMonth(c) * 100 + getDay(c));
    }
    
    /**
     * Converts the given Date to an XML date/time String
     * 
     * @param date the Date
     * @return the String
     */
    public final static String toXmlDateTimeString(final Date date) {
        return toXmlDateTimeString(toCalendar(date));
    }
    
    /**
     * Converts the given Calendar to an XML date/time String
     * 
     * @param cal the Calendar
     * @return the String
     */
    public final static String toXmlDateTimeString(final Calendar cal) {
        return toXmlDateTimeString(cal, Calendar.ZONE_OFFSET);
    }
    
    public final static String toXmlDateTimeString(final Date date, final int last) {
        return toXmlDateTimeString(toCalendar(date), last);
    }
    
    public final static String toXmlDateTimeString(final Calendar cal, final int last) {
        if (cal == null) {
            return null;
        }

        final StringBuilder val = new StringBuilder(29);
        val.append(getYear(cal)); // 4
        val.append('-'); // 5
        append2DigitString(val, getMonth(cal)); // 7
        val.append('-'); // 8
        append2DigitString(val, getDay(cal)); // 10
        val.append('T'); // 11
        append2DigitString(val, cal.get(Calendar.HOUR_OF_DAY)); // 13
        val.append(':'); // 14
        append2DigitString(val, cal.get(Calendar.MINUTE)); // 16
        if (last == Calendar.MINUTE) {
            return val.toString();
        }
        val.append(':'); // 17
        append2DigitString(val, cal.get(Calendar.SECOND)); // 19
        if (last == Calendar.SECOND) {
            return val.toString();
        }
        val.append('.'); // 20
        val.append(Util.to3DigitString(cal.get(Calendar.MILLISECOND))); // 23
        if (last == Calendar.MILLISECOND) {
            return val.toString();
        }
        //val.append('Z') // 24 // ?
        final int tz = (cal.get(Calendar.ZONE_OFFSET) + cal.get(Calendar.DST_OFFSET)) / (60 * 1000);
        val.append(tz < 0 ? '-' : '+'); // 24
        final int off = Math.abs(tz);
        append2DigitString(val, off / 60); // 26
        val.append(':'); // 27
        append2DigitString(val, off % 60); // 29
        if (last != Calendar.ZONE_OFFSET) {
            throw new IllegalArgumentException(Integer.toString(last));
        }

        return val.toString();
    }
    
    /**
     * Retrieves the Calendar's month
     * 
     * @param c the Calendar
     * @return the month
     **/
    public final static int getMonth(final Calendar c) {
        return c.get(Calendar.MONTH) + 1;
    }
    
    /**
     * Retrieves the Calendar's day
     * 
     * @param c the Calendar
     * @return the year
     **/
    public final static int getDay(final Calendar c) {
        return c.get(Calendar.DAY_OF_MONTH);
    }
    
    /**
     * Retrieves the Calendar's year
     * 
     * @param c the Calendar
     * @return the year
     **/
    public final static int getYear(final Calendar c) {
        return c.get(Calendar.YEAR);
    }
    
    private final static boolean setDateTime(final Calendar c, String dateText) {
        // Start parsing text at beginning of dateText
        String timeText = null; // Time portion of dateText value
        String hourText = null, minuteText = null, AmPmText = null;
        // Strings parsed from dateText
        dateText = dateText.trim(); // Get rid of leading and trailing spaces
        final int len = dateText.length();
        if (dateText.equals("")) { // If input is empty
            c.setTimeInMillis(0); // Then init date to null
            return true;
        }
        try { // Try to use standard JAVA date encode
            final ParsePosition pp = new ParsePosition(0);
            final Date d = formatterTime.parse(dateText, pp);
            c.setTime(d);
            formatYear(c);
            if (pp.getIndex() == len) {
                return true; // Exit if parsed entire value OK
            }
        } catch (final Exception e) {}
        try {
            setDateFormatXMLDateTime(c, dateText);
            return true;
        } catch (final Exception e) {}
        
        int charPos = dateText.indexOf(' '); // See if might have a time component after the date
        if (charPos == -1) {
            return setDate(c, dateText);
        } else { // If have a date and a time portion
            timeText = dateText.substring(charPos + 1).trim().toUpperCase();
            dateText = dateText.substring(0, charPos).trim();
        }
        if (!setDate(c, dateText)) {
            throw new IllegalArgumentException("Invalid date or date-time value '" + dateText + "'");
            // No valid date if reach here
        }
        
        if (Util.isValued(timeText)) { // If have time along with date
            charPos = timeText.indexOf("AM"); // Start by identifying any AM, PM, or M designation
            // Technically, 12:00 M is midnight
            if (charPos != -1) {
                AmPmText = "AM";
                timeText = timeText.substring(0, charPos);
            } else {
                charPos = timeText.indexOf("PM");
                if (charPos != -1) {
                    AmPmText = "PM";
                    timeText = timeText.substring(0, charPos);
                } else {
                    charPos = timeText.indexOf("M");
                    if (charPos != -1) {
                        AmPmText = "M";
                        timeText = timeText.substring(0, charPos);
                    }
                }
            }
            charPos = timeText.indexOf(':'); // Now try to separate time into hour and minute portions
            if (charPos != -1) {
                hourText = timeText.substring(0, charPos);
                minuteText = timeText.substring(charPos + 1);
                charPos = minuteText.indexOf('.');
                if (charPos != -1) {
                    minuteText = minuteText.substring(0, charPos);
                }
            } else {
                if (timeText.length() == 4) {
                    hourText = timeText.substring(0, 2);
                    minuteText = timeText.substring(2);
                } else {
                    throw new IllegalArgumentException("Invalid time value '" + timeText + "'");
                }
            }
        }
        return setDateTime(c, getYear(c), getMonth(c), getDay(c), hourText, minuteText, AmPmText);
    }
    
    private final static boolean setDateTime(final Calendar c, final int year, final int month, final int day, String hourText, final String minuteText,
                                  final String AmPmText) {
        int hour = 0;
        int minute = 0; //*Integer values for HH:MM values
        if (hourText != null) {
            hourText = hourText.trim();
            try {
                hour = Integer.parseInt(hourText);
            } catch (final Exception hourExcept) {
                throw new IllegalArgumentException("Hour value '" + hourText + "' is not valid.");
            }
            try {
                minute = Integer.parseInt(minuteText);
            } catch (final Exception minuteExcept) {
                throw new IllegalArgumentException("Minute value '" + minuteText + "' is not valid.");
            }
            switch (hour) {
                case 0:
                    if ((AmPmText != null) && (AmPmText.length() != 0)) {
                        throw new IllegalArgumentException("Invalid time '" + buildTimeText(hourText, minuteText, AmPmText)
                                + "'.  Zero is not a valid HOUR when AM or PM are specified.");
                    }
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                    if ("PM".equals(AmPmText)) {
                        hour += 12;
                    } else {
                        if ("M".equals(AmPmText)) {
                            throw new IllegalArgumentException("Invalid time '" + buildTimeText(hourText, minuteText, AmPmText)
                                    + "'.  M is only value with 12:00");
                        }
                    }
                    if ((minute < 0) || (minute > 59)) {
                        throw new IllegalArgumentException("Invalid minutes in time '" + buildTimeText(hourText, minuteText, AmPmText)
                                + "'");
                    }
                    break; //*Valid time if get to here, skip other CASE checks.
                case 12:
                    if ("AM".equals(AmPmText)) {
                        hour = 0; //* 12:xx AM is first hour of day
                    } else {
                        if ("M".equals(AmPmText)) {
                            if (minute == 0) {
                                hour = 24;
                            } else {
                                throw new IllegalArgumentException("Invalid time '" + buildTimeText(hourText, minuteText, AmPmText)
                                        + "'.  M is only value with 12:00");
                            }
                        }
                    }
                    if ((minute < 0) || (minute > 59)) {
                        throw new IllegalArgumentException("Invalid minutes in time '" + buildTimeText(hourText, minuteText, AmPmText)
                                + "'");
                    }
                    break; //*Valid time if get to here, skip other CASE checks.
                    
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                    if ((AmPmText != null) && (AmPmText.length() != 0)) {
                        throw new IllegalArgumentException("Invalid time '" + buildTimeText(hourText, minuteText, AmPmText)
                                + "'.  Zero is not a valid HOUR when AM or PM are specified.");
                    }
                    if ((minute < 0) || (minute > 59)) {
                        throw new IllegalArgumentException("Invalid minutes in time '" + buildTimeText(hourText, minuteText, AmPmText)
                                + "'");
                    }
                    break; //*Valid time if get to here, skip other CASE checks.
                    
                case 24:
                    if ((AmPmText != null) && (AmPmText.length() != 0)) {
                        throw new IllegalArgumentException("Invalid time '" + buildTimeText(hourText, minuteText, AmPmText)
                                + "'.  Zero is not a valid HOUR when AM or PM are specified.");
                    }
                    if (minute == 0) {
                        break;
                    }
                    //*Only value time with hour "24" is 24:00
                default:
                    throw new IllegalArgumentException("Invalid time '" + buildTimeText(hourText, minuteText, AmPmText) + "'");
            }
        }
        setDateTime(c, year, month, day, hour, minute);
        return true; //*Valid date if reach here
    }
    
    private final static void setDateFormatXMLDateTime(final Calendar c, final String dateText) {
        final int yearEnd = dateText.indexOf('-'); // Find delimiters for parsing individual fields
        final int monthEnd = dateText.indexOf('-', yearEnd + 1);
        final int dayEnd = dateText.indexOf('T', monthEnd + 1);
        final int hourEnd = dateText.indexOf(':', dayEnd + 1);
        int minuteEnd = dateText.indexOf(':', hourEnd + 1);
        if (minuteEnd < 0) { // Allow values without seconds
            minuteEnd = dateText.length();
        }
        
        final int year = Util.parseInt(dateText, 0, yearEnd); // Parse the individual fields
        final int month = Util.parseInt(dateText, yearEnd + 1, monthEnd);
        final int day = Util.parseInt(dateText, monthEnd + 1, dayEnd);
        final int hour = Util.parseInt(dateText, dayEnd + 1, hourEnd);
        final int minute = Util.parseInt(dateText, hourEnd + 1, minuteEnd);
        int second;
        try {
            second = Util.parseInt(dateText, minuteEnd + 1);
        } catch (final Exception e) {
            second = 0; // Allow values without seconds
        }
        
        c.clear(Calendar.MILLISECOND);
        c.set(year, month - 1, day, hour, minute, second); // Set the date
    }
    
    private final static void setDateFormatXMLDate(final Calendar c, final String dateText) {
        final int yearEnd = dateText.indexOf('-'); // Find delimiters for parsing individual fields
        final int monthEnd = dateText.indexOf('-', yearEnd + 1);
        
        final int year = Util.parseInt(dateText, 0, yearEnd); // Parse the individual fields
        final int month = Util.parseInt(dateText, yearEnd + 1, monthEnd);
        final int day = Util.parseInt(dateText, monthEnd + 1);
        
        c.clear(Calendar.MILLISECOND);
        c.set(year, month - 1, day, 0, 0, 0); // Set the date
    }
    
    private final static boolean setDate(final Calendar c, final String dateText) {
        if (dateText.equals("")) { // If input is empty
            return false;
        } else if (parseToday(c, dateText)) {
            return true;
        }
        try { // Try to use standard JAVA date encode
            final Date d = formatter.parse(dateText);
            setDate(c, d);
            formatYear(c);
            return true; // Exit if parsed it OK
        } catch (final Exception e) {}
        char dlm = '-'; // Parsing delimiter
        int p1 = dateText.indexOf(dlm); // Do we find the "-" delim?
        int p2; // Second delim char position
        String yearText, monthText, dayText; // Strings parsed from dateText
        if ((p1 == 1) || (p1 == 2)) { // If found "-" where could delimit day of month (1-2 digits)
            p2 = dateText.indexOf(dlm, p1 + 1);
            if (p2 != -1) {
                dayText = dateText.substring(0, p1);
                monthText = dateText.substring(p1 + 1, p2);
                yearText = dateText.substring(p2 + 1);
                // Then parse it as "DD-MMM-YYYY"
                return setDate(c, yearText, monthText, dayText);
            }
        } else {
            dlm = '/';
            p1 = dateText.indexOf(dlm); // Else look for "/" delimiter
            if (p1 != -1) {
                p2 = dateText.indexOf(dlm, p1 + 1);
                if (p2 != -1) {
                    monthText = dateText.substring(0, p1);
                    dayText = dateText.substring(p1 + 1, p2);
                    yearText = dateText.substring(p2 + 1);
                    return setDate(c, yearText, monthText, dayText);
                }
            }
        }
        dlm = ','; // Will now check for "January 1,2000" format
        p1 = dateText.indexOf(dlm); // Is the "," there
        if (p1 > 0) {
            p2 = dateText.indexOf(' '); // Find space between month and day
            if ((p2 > -1) && (p2 < p1)) { // If space is there and is before comma
                monthText = dateText.substring(0, p2);
                dayText = dateText.substring(p2 + 1, p1);
                yearText = dateText.substring(p1 + 1);
                // Then year is everything after the comma
                return setDate(c, yearText, monthText, dayText);
                // Create DateItem from parsed components
            } else {
                char onechar;
                for (int i = 0; i < p1; i++) {
                    onechar = dateText.charAt(i);
                    if ((onechar >= '0') && (onechar <= '9')) { // If find a numeric char
                        monthText = dateText.substring(0, i - 1);
                        dayText = dateText.substring(i, p1);
                        yearText = dateText.substring(p1 + 1);
                        return setDate(c, yearText, monthText, dayText);
                    }
                }
            }
        }
        if (dateText.length() == 8) { // If might be YYYYMMDD format
            yearText = dateText.substring(0, 4);
            monthText = dateText.substring(4, 6);
            dayText = dateText.substring(6, 8);
            return setDate(c, yearText, monthText, dayText);
        }
        
        // See if 31 DEC 2003 format
        char[] dateTextChars = new char[dateText.length()];
        dateTextChars = dateText.toCharArray();
        final int length = dateTextChars.length;
        dayText = "";
        yearText = "";
        monthText = "";
        
        int i = 0;
        // Try to set the dayText
        while ((i < length) && (dateTextChars[i] != ' ')) {
            dayText += dateTextChars[i];
            i++;
        }
        while ((i < length) && (dateTextChars[i] == ' ')) {
            i++;
        }
        // Try to set the month text
        while ((i < length) && (dateTextChars[i] != ' ')) {
            monthText += dateTextChars[i];
            i++;
        }
        final int m = wordMonthToNum(monthText, dateText);
        if (m != -1) {
            monthText = String.valueOf(m);
            while ((i < length) && (dateTextChars[i] == ' ')) {
                i++;
            }
            // Try to set the year text
            while ((i < length) && (dateTextChars[i] != ' ')) {
                yearText += dateTextChars[i];
                i++;
            }
            if ((yearText.length() > 0) && (monthText.length() > 0) && (dayText.length() > 0)) {
                return setDate(c, yearText, monthText, dayText);
            }
        }
        
        try {
            setDateFormatXMLDate(c, dateText);
            return true;
        } catch (final Exception e) {
            return false;
        }
    }
    
    private final static boolean setDate(final Calendar c, final String yearText, final String monthText, final String dayText) {
        final int year = parseYear(yearText);
        final int month = parseMonth(monthText);
        final int day = parseDay(dayText); // Integer values for year, month day
        if ((day == 0) || (month == 0) || (year == 0)) {
            return false;
        }
        setDate(c, year, month, day);
        return true; // Valid date if reach here
    }
    
    private final static int parseDay(final String dayText) {
        final int day;
        try {
            day = Integer.parseInt(dayText.trim()); // Remove surounding white space
        } catch (final Exception e) {
            return 0; // Must have numeric day for valid date
        }
        return (day < 1) || (day > 31) ? 0 : day; // Check for valid day
        //throw new IllegalArgumentException("Invalid day portion of date was '" + dayText + "'");
    }
    
    private final static int parseMonth(String monthText) {
        monthText = monthText.trim();
        final int month;
        try {
            month = Integer.parseInt(monthText);
        } catch (final Exception e) {
            for (int i = 0; i < 12; i++) {
                if ((monthText.compareToIgnoreCase(monthName[i]) == 0)
                        || (monthText.compareToIgnoreCase(monthNameAbbr[i]) == 0)) {
                    return i + 1;
                }
            }
            return 0;
        }
        return (month < 1) || (month > 12) ? 0 : month; // Check for valid month
        //throw new IllegalArgumentException("Invalid month specified '" + monthText + "'");
    }
    
    private final static int parseYear(final String yearText) {
        final int year;
        try {
            year = formatYear(Integer.parseInt(yearText.trim()));
        } catch (final Exception e) {
            return 0;
        }
        return (year < 1800) || (year > 2100) ? 0 : year; // Check year for reasonable
        //throw new IllegalArgumentException("Invalid year specified '" + yearText + "'");
    }
    
    private final static int formatYear(final int year) {
        if ((year >= 0) && (year < 100)) { // If was a 2-digit year (00 - 09 are 2-digit years)
            // Then get current year (relative to 2000)
            if (year <= yearNow + 20) { // If is not more than 20 years in future
                return year + 2000; // Then make it a 20xx date
            } else {
                return year + 1900; // Else make it 19xx date way in past
            }
        }
        return year;
    }
    
    private final static void formatYear(final Calendar c) {
        final int year = getYear(c);
        final int formattedYear = formatYear(year);
        if (year != formattedYear) {
            c.set(Calendar.YEAR, formattedYear);
        }
    }
    
    private final static boolean parseToday(final Calendar c, final String dateText) {
        if (dateText.equals("//") || dateText.equals("T") || dateText.equals("TODAY") || dateText.equals("--")) {
            // If input was one of the shortcuts for today's date, then load today's date
            setDate(c, new Date());
            return true;
        }
        return false;
    }
    
    /**
     * builds total time value from its components when signalling an exception
     * 
     * @param hourText text for hour of day
     * @param minuteText text for minute
     * @param AmPmText text indicating if time is AM or PM
     * @return complete formatting time
     **/
    private final static String buildTimeText(final String hourText, final String minuteText, final String AmPmText) {
        String txt = Util.unNull(hourText);
        txt += (minuteText == null ? ":" : (":" + minuteText));
        if (AmPmText != null) {
            txt += AmPmText;
        }
        return txt;
    }
    
    private final static void setDateTime(final Calendar c, final int year, final int month, final int day, final int hour, final int minute) {
        c.clear();
        c.set(year, month - 1, day, hour, minute);
    }
    
    protected final static void setDate(final Calendar c, final int year, final int month, final int day) {
        c.clear();
        c.set(year, month - 1, day);
    }
    
    /**
     * Retrieves the number of the given month
     * 
     * @param monthText the month as text
     * @param dateText the whole date (used in error message; it was confusing seeing only the month
     *            in the error, especially when it was blank)
     * @return the number of the month
     **/
    private final static int wordMonthToNum(final String monthText, final String dateText) {
        if (monthText.equalsIgnoreCase(ABBREV_JAN)) {
            return 1;
        } else if (monthText.equalsIgnoreCase(ABBREV_FEB)) {
            return 2;
        } else if (monthText.equalsIgnoreCase(ABBREV_MAR)) {
            return 3;
        } else if (monthText.equalsIgnoreCase(ABBREV_APR)) {
            return 4;
        } else if (monthText.equalsIgnoreCase(ABBREV_MAY)) {
            return 5;
        } else if (monthText.equalsIgnoreCase(ABBREV_JUN)) {
            return 6;
        } else if (monthText.equalsIgnoreCase(ABBREV_JUL)) {
            return 7;
        } else if (monthText.equalsIgnoreCase(ABBREV_AUG)) {
            return 8;
        } else if (monthText.equalsIgnoreCase(ABBREV_SEP)) {
            return 9;
        } else if (monthText.equalsIgnoreCase(ABBREV_OCT)) {
            return 10;
        } else if (monthText.equalsIgnoreCase(ABBREV_NOV)) {
            return 11;
        } else if (monthText.equalsIgnoreCase(ABBREV_DEC)) {
            return 12;
        }
        
        return -1;
    }
    
    /**
     * Retrieves a month abbreviation String
     * 
     * @param m the month
     * @return month abbreviation String
     **/
    public final static String monthString(final int m) {
        switch (m) {
            case 1:
                return ABBREV_JAN;
            case 2:
                return ABBREV_FEB;
            case 3:
                return ABBREV_MAR;
            case 4:
                return ABBREV_APR;
            case 5:
                return ABBREV_MAY;
            case 6:
                return ABBREV_JUN;
            case 7:
                return ABBREV_JUL;
            case 8:
                return ABBREV_AUG;
            case 9:
                return ABBREV_SEP;
            case 10:
                return ABBREV_OCT;
            case 11:
                return ABBREV_NOV;
            case 12:
                return ABBREV_DEC;
            default:
                return null;
        }
    }
    
    /**
     * translates month number (1-12) into month abbreviation text
     * 
     * @param monthNumber month as integer 1 through 12
     * @return official three-letter abbreviation for month
     **/
    private final static String getMonthAbbreviation(final int monthNumber) {
        //TODO different than monthString above?
        return (monthNumber >= 1) && (monthNumber <= 12) ? monthNameAbbr[monthNumber - 1] : "Unk*" + monthNumber + "*";
    }
    
    /**
     * Determines if two dates match (have 2/3 matching fields or swapped day/month (year
     * comparisons only use last two digits)
     * 
     * @param c1 the first date
     * @param c2 the second date
     * @return number of days since base/offset date
     **/
    public final static boolean matches(final Calendar c1, final Calendar c2) {
        if (c1 == null) {
            return c2 == null;
        } else if (c2 == null) {
            return false;
        }
        final int m1 = getMonth(c1), d1 = getDay(c1), m2 = getMonth(c2), d2 = getDay(c2);
        int dateFieldsMatched = 0;
        
        final boolean yearsMatch = yearEquals(c1, c2);
        
        // Count matching fields
        if (m1 == m2) {
            dateFieldsMatched++;
        }
        if (d1 == d2) {
            dateFieldsMatched++;
        }
        if (yearsMatch) {
            dateFieldsMatched++;
        }
        
        // There's a match if at least two fields match
        if (dateFieldsMatched >= 2) {
            return true;
        }
        
        // There's a match if the years match and the months/days are swapped
        if (yearsMatch && (m1 == d2) && (d1 == m2)) {
            return true;
        }
        
        return false;
    }
    
    // Determines if the last two digits of the years of the dates are equal 
    private final static boolean yearEquals(final Calendar c1, final Calendar c2) {
        // Extract the last two digits of this year and cmpdate's year
        try {
            String yearLastDigits = String.valueOf(getYear(c1));
            yearLastDigits = yearLastDigits.substring(yearLastDigits.length() - 2, yearLastDigits.length());
            String cmpYearLastDigits = String.valueOf(getYear(c2));
            cmpYearLastDigits = cmpYearLastDigits.substring(cmpYearLastDigits.length() - 2, cmpYearLastDigits.length());
            if (!yearLastDigits.equals(cmpYearLastDigits)) { // If the years aren't equal, the dates aren't equal
                return false;
            }
        } catch (final Exception e) {
            if (getYear(c1) != getYear(c2)) { // If comparison of last two digits of years failed,
                return false; // just compare the whole years
            }
        }
        
        return true;
    }
    
    public final static boolean matches(final Date d1, final Date d2) {
        return matches(toCalendar(d1), toCalendar(d2));
    }
    
    public final static boolean dayEquals(final Calendar c1, final Calendar c2) {
        if (c1 == null) {
            return c2 == null;
        } else if (c2 == null) {
            return false;
        }
        return (getYear(c1) == getYear(c2)) && (getMonth(c1) == getMonth(c2)) && (getDay(c1) == getDay(c2));
    }
    
    public final static boolean dayEquals(final Date d1, final Date d2) {
        return dayEquals(toCalendar(d1), toCalendar(d2));
    }
    
    /**
     * Treat 1/1/1900 and 1/1/1901 as fake
     * 
     * @param d the Date
     * @return whether the Date is fake
     **/
    public final static boolean isFake(final Date d) {
        if (d == null) {
            return true;
        }
        final Calendar c = toCalendar(d);
        final int year = getYear(c);
        return ((year == 1900) || (year == 1901)) && (getMonth(c) == 1) && (getDay(c) == 1);
    }
    
    /**
     * Retrieves the Date or null if it is fake
     * 
     * @param d the Date
     * @return the Date or null if it is fake
     **/
    public final static Date nullFake(final Date d) {
        return isFake(d) ? null : d;
    }
    
    public final static int daysBetween(final Date d1, final Date d2) {
        if ((d2 == null) || (d1 == null)) {
            return 0; // "d2 == null" consistent with old DateItem class
        }
        return Math.round((truncate(d2).getTime() - truncate(d1).getTime()) / ((float) StopWatch.MILLIS_PER_DAY));
    }
    
    public final static void main(final String[] args) {
        System.out.println("Available:");
        for (final String id : TimeZone.getAvailableIDs()) {
            if (id.startsWith("America/") || id.startsWith("US/")) {
                System.out.println(id);
            }
        }
        System.out.println("This machine: " + TimeZone.getDefault().getID());
    }
}
