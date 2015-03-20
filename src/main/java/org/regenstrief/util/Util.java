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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.Security;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.RandomAccess;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.UUID;
import java.util.Vector;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipInputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.regenstrief.io.BomReader;
import org.regenstrief.io.IoUtil;
import org.regenstrief.io.LabeledInputStream;
import org.regenstrief.io.LabeledReader;
import org.regenstrief.util.criterion.AssignableFromCriterion;
import org.regenstrief.util.criterion.Criteria;
import org.regenstrief.util.criterion.Criterion;
import org.regenstrief.util.property.PropertyResolver;
import org.regenstrief.util.property.RegenPropertyResolver;
import org.regenstrief.util.reflect.ReflectUtil;

/**
 * Title: Utilities Description: List of arbitrary <code>static</code> methods not logically within
 * a specific object. Copyright: Copyright (c) 2001 Company: Regenstrief Institute
 * 
 * @author Lonnie Blevins
 * @version 1.0
 */
public final class Util {
    
    /** Nobody should create a <code>Util</code> object. It's just here for its static methods. **/
    private Util() {
    }
    
    /**
     * generate a unique id number, for lifetime of the applciation anyway
     * 
     * @return unique long integer (for lifetime of the application)
     **/
    public final synchronized static long generateUniqueID() {
        return ++uniqueID;
    }
    
    /**
     * generate a unique id number, for lifetime of the applciation anyway
     * 
     * @return unique long integer (for lifetime of the application)
     **/
    public final synchronized static String generateUniqueIDString() {
        return Long.toString(generateUniqueID());
    }
    
    private static PropertyResolver propertyResolver = new RegenPropertyResolver();
    
    private static long uniqueID = 0; //*Unique ID will start at one every time application begins
    
    private static Log log = LogFactory.getLog(Util.class);
    
    private static MyAuthenticator myAuthenticator = null;
    
    private final static ThreadLocal<String> authenticatorUser = new ThreadLocal<String>();
    
    private final static ThreadLocal<String> authenticatorPassword = new ThreadLocal<String>();
    
    private static Field threadTarget = null;
    
    private static String illegalFileNameCharacters = null;
    
    //private static boolean classpathValidated = false;
    public final static int BUFFER_SIZE = 4096;
    
    private final static int MAX_MARK_BUFFER = 10000000;
    
    public final static String DEFAULT_PROPERTIES = "rhits.properties";
    
    public final static String PROPERTIES = "org.regenstrief.database.Properties";
    
    public final static String PROP_APPLICATION = "org.regenstrief.database.APPLICATION";
    
    public final static String PROP_EMAIL_ADDRESS = "org.regenstrief.database.EMAIL.ADDRESS";
    
    public final static String PROP_EMAIL_ADDRESS_SENDER = "org.regenstrief.database.EMAIL.ADDRESS.SENDER";
    
    public final static String PROP_EMAIL_HOST = "org.regenstrief.database.EMAIL.HOST";
    
    public final static String PROP_EMAIL_PORT = "org.regenstrief.database.EMAIL.PORT";
    
    public final static String PROP_EMAIL_SERVER = "org.regenstrief.database.EMAIL.SERVER";
    
    public final static String PROP_PREFIX = Util.class.getName() + ".";
    
    public final static String PROP_DEBUG = PROP_PREFIX + "debug";
    
    public final static String PROP_LAZY_LOAD = PROP_PREFIX + "lazyLoad";
    
    public final static String PROP_INPUT_DELIMITER = PROP_PREFIX + "inputDelimiter";
    
    public final static String PROP_TRUST_STORE = "javax.net.ssl.trustStore";
    
    public final static String PROP_TRUST_STORE_PASSWORD = "javax.net.ssl.trustStorePassword";
    
    public final static String PROP_AUTHENTICATOR_USER = "org.regenstrief.util.AuthenticatorUser";
    
    public final static String PROP_AUTHENTICATOR_PASSWORD = "org.regenstrief.util.AuthenticatorPassword";
    
    public final static String PROP_USED_MEMORY_PERCENTAGE_CEILING = "org.regenstrief.util.UsedMemoryPercentageCeiling";
    
    public final static String PROP_LOG_OUTPUT = "org.regenstrief.util.Util.LOG_OUTPUT";
    
    public final static String PROP_LOG_ERROR = "org.regenstrief.util.Util.LOG_ERROR";
    
    public final static String PROP_EXCEPTION_REPORTING = "org.regenstrief.util.Util.EXCEPTION_REPORTING";
    
    public final static String PROP_HANDLER = "java.protocol.handler.pkgs";
    
    public final static String PROP_INFO = PROP_PREFIX + PROP_PREFIX + "info";
    
    public final static String HANDLER_SUN_SSL = "com.sun.net.ssl.internal.www.protocol";
    
    public final static String PARTIAL_KEY_JDBC_DRIVER = "JDBC.DRIVER_CLASS_NAME";
    
    public final static String PARTIAL_KEY_JDBC_ADAPTER = "JDBC.ADAPTER_CLASS_NAME";
    
    public final static String PARTIAL_KEY_JDBC_URL = "JDBC.URL";
    
    public final static String PARTIAL_KEY_JDBC_USERNAME = "JDBC.USERNAME";
    
    public final static String PARTIAL_KEY_JDBC_PASSWORD = "JDBC.PASSWORD";
    
    public final static int EXCEPTION_NONE = 0;
    
    public final static int EXCEPTION_GET_MESSAGE = 1;
    
    public final static int EXCEPTION_TO_STRING = 2;
    
    public final static int EXCEPTION_STACK_TRACE = 3;
    
    public final static int EXCEPTION_CAUSE_STRINGS = 4;
    
    public final static String DEFAULT_CONCAT_DELIM = " ";
    
    public final static String TRUE = Boolean.toString(true);
    
    public final static String FALSE = Boolean.toString(false);
    
    public final static String BR = getLineSeparator();
    
    public final static int[] EMPTY_ARRAY_INT = {};
    
    public final static String[] EMPTY_ARRAY_STRING = {};
    
    public final static Class<?>[] EMPTY_ARRAY_CLASS = {};
    
    public final static Object[] EMPTY_ARRAY_OBJECT = {};
    
    /**
     * Parses boolean value from text, such as input or control file.
     * 
     * @param val the boolean value
     * @return <code>true</code> is returned if value is "TRUE", "YES", "Y", "YE", "ON", or "1"
     *         (case-insensitive test) <br>
     *         <code>false</code> is returned otherwise.
     **/
    public final static boolean parseBoolean(final String val) {
        if (val == null) {
            return false;
        }
        final String x = val.trim(); //=Normalize all values
        if (x.equals("1") || x.equalsIgnoreCase(TRUE) || x.equalsIgnoreCase("YES") || x.equalsIgnoreCase("Y")
                || x.equalsIgnoreCase("YE") || x.equalsIgnoreCase("ON")) {
            return true;
        }
        return false; //=Default is FALSE
    }
    
    /**
     * Get the <code>boolean</code> value of the <code>Boolean</code>. This defaults to false if the
     * Boolean's value is <code>null</code>.
     * 
     * @param booleanValue the value of the Boolean
     * @return boolean value for the Boolean handling <code>null</code> case as false
     */
    public final static boolean booleanValue(final Boolean booleanValue) {
        return booleanValue == null ? false : booleanValue.booleanValue();
    }
    
    /**
     * parses String txt into a Vector of String objects by breaking it at every occurrence of a
     * delimiter character
     * 
     * @param txtValue text to be parsed
     * @param dlmChar delimiter character at which to split the <code>txtValue</code>
     * @return list of text values parsed out of <code>txtValue</code>; <br>
     *         <code>dlmChar</code> will not be included in any of the returned text values; <br>
     *         if <code>dlmChar</code> appears N times in <code>txtValue</code>, N+1 values will be
     *         returned
     **/
    public final static Vector<String> parseString(final String txtValue, final char dlmChar) {
        return parseString(txtValue, (new Character(dlmChar)).toString());
    }
    
    /**
     * parses String text into a Vector of String objects by breaking it at every occurrence of a
     * delimiter text string
     * 
     * @param txt text to be parsed
     * @param dlm delimiter String at which to split the <code>txtValue</code>
     * @return list of text values parsed out of <code>txtValue</code>; <br>
     *         <code>dlm</code> will not be included in any of the returned text values; <br>
     *         if <code>dlm</code> appears N times in <code>txtValue</code>, N+1 values will be
     *         returned
     **/
    public final static Vector<String> parseString(String txt, final String dlm) {
        if (isEmpty(txt)) {
            return null;
        }
        final Vector<String> lst = new Vector<String>(); //=Make guess at initial allocation
        final int bgnPos = 0; //=Character position parsing values.
        int endPos = -1, bgnPosNext = 0;
        String firstChar; //=First char of value
        int txtLen; //=Curr size of value
        final int dlmLen = dlm.length(); //=Size of delimiter String
        
        while ((txtLen = txt.length()) != 0) { //=Loop until parse entire value
            if (txt.startsWith("'") || txt.startsWith("\"")) {
            //=IF value begins with a double or single
            //quotation mark,
                firstChar = txt.substring(0, 1); //THEN find out which quotation mark it is.
                endPos = txt.indexOf(firstChar); //=Look for closing quotation mark followed by delimiter
                bgnPosNext = endPos + dlmLen; //=Calculate where following value will start.
                if (endPos != -1) { //=IF found matching quotation mark
                    if (endPos < txtLen) { //THEN IF is not at end of string
                        if (!txt.substring(endPos + 1, bgnPosNext).equals(dlm)) {
                            endPos = -1;
                        }
                        //     THEN IF delimiter does not follow quotation mark,
                        //          THEN revert to pure delimiter search.
                    }
                }
            } else {
                endPos = -1;
            }
            if (endPos == -1) { //=IF parsing by quotation mark did not work out
                endPos = txt.indexOf(dlm); //THEN revert to looking for delimiter by itself.
                if (endPos == -1) { //=IF no more delimiters
                    endPos = txtLen;
                    bgnPosNext = endPos;
                } else {
                    bgnPosNext = endPos + dlmLen; //=Calculate start of following value.
                }
            }
            lst.addElement(txt.substring(bgnPos, endPos)); //=Add single text element to value
            if (bgnPosNext >= txtLen) { //=IF exhausted all values
                break; //THEN exit loop
            } else {
                txt = txt.substring(bgnPosNext); //ELSE move parsing past the delimiter
            }
        }
        return lst; //=Return Vector to CALLer
    }
    
    /**
     * Parses an int from a substring of the given String; should yield the same results as
     * Integer.parseInt(s.substring(startIndex, endIndex), but should be more efficient
     * 
     * @param s the String
     * @param startIndex the start index of the substring
     * @param endIndex the end index of the substring
     * @return the int
     **/
    public final static int parseInt(final String s, final int startIndex, final int endIndex) {
        int n = 0, sign = 1;
        if (startIndex >= endIndex) {
            throw new StringIndexOutOfBoundsException(endIndex - startIndex);
        }
        for (int i = startIndex; i < endIndex; i++) {
            final char c = s.charAt(i);
            if (i == startIndex) {
                if (c == '-') {
                    sign = -1;
                    continue;
                } else if (c == '+') {
                    // Java 7 introduced support for leading + character
                    continue;
                }
            }
            if ((c < '0') || (c > '9')) {
                throw new NumberFormatException(s);
            }
            n = (n * 10) + (c - '0');
        }
        
        return sign * n;
    }
    
    public final static int parseInt(final String s, final int startIndex) {
        return parseInt(s, startIndex, s.length());
    }
    
    /*
    public final static <E extends Enum<E>> E parse(final String text, final E... vals) {
        for (final E val : vals) {
            if (val.name().equalsIgnoreCase(text)) {
                return val;
            }
        }
        return null;
    }
    */
    
    public final static <E> E parse(final String text, final E... vals) {
        for (final E val : vals) {
            if (val.toString().equalsIgnoreCase(text)) {
                return val;
            }
        }
        return null;
    }
    
    /**
     * Determines if a String and a char are equal
     * 
     * @param s the String
     * @param c the char
     * @return whether or not s equals c
     **/
    public final static boolean equals(final String s, final char c) {
        return (length(s) == 1) && (s.charAt(0) == c);
    }
    
    /**
     * Determines if two Strings are equal, ignoring case (true if both are null)
     * 
     * @param s1 String 1
     * @param s2 String 2
     * @return whether s1 equals s2, ignoring case
     **/
    public final static boolean equalsIgnoreCase(final String s1, final String s2) {
        return s1 == null ? s2 == null : s1.equalsIgnoreCase(s2);
    }
    
    /**
     * Retrieves a comparison of two Strings
     * 
     * @param c1 the first String
     * @param c2 the second String
     * @return -1 if c1 comes before c2, +1 if c2 comes first, otherwise 0
     **/
    public final static int compareToIgnoreCase(final String c1, final String c2) {
        if (c1 == null) {
            return c2 == null ? 0 : 1;
        }
        return c2 == null ? -1 : c1.compareToIgnoreCase(c2);
    }
    
    /**
     * Converts a string to upper case letters
     * 
     * @param s the String to convert
     * @return the upper case String
     **/
    public final static String toUpperCase(final String s) {
        return s == null ? null : s.toUpperCase();
    }
    
    /**
     * Converts a string to lower case letters
     * 
     * @param s the String to convert
     * @return the lower case String
     **/
    public final static String toLowerCase(final String s) {
        return s == null ? null : s.toLowerCase();
    }
    
    public final static String toProperName(final String s) {
        return isEmpty(s) ? s : Character.toUpperCase(s.charAt(0)) + s.substring(1).toLowerCase();
    }
    
    /**
     * Retrieves the String or an empty String if it is null
     * 
     * @param s the String
     * @return the String or an empty String if it is null
     **/
    public final static String unNull(final String s) {
        return nvl(s, "");
    }
    
    /**
     * Like Oracle's nvl, which is like coalesce with only two arguments
     * 
     * @param t the desired field
     * @param def the default field
     * @return t if not null, otherwise def
     */
    public final static <T> T nvl(final T t, final T def) {
        return t == null ? def : t;
    }
    
    /**
     * Like nvl, but treats empty Strings like null
     * 
     * @param s the desired String
     * @param def the default String
     * @return s if not empty, otherwise def
     */
    public final static String nvls(final String s, final String def) {
        return isEmpty(s) ? def : s;
    }
    
    /**
     * Retrieves the first non-null String in the given array
     * 
     * @param a the array
     * @return the first non-null String
     **/
    public final static String coalesce(final String... a) {
        for (final String s : unNull(a)) {
            if (s != null) {
                return s;
            }
        }
        return null;
    }
    
    /**
     * Converts an object to a string
     * 
     * @param o the Object to convert
     * @return the String
     **/
    public final static String toString(final Object o) {
        return o == null ? null : o.toString();
    }
    
    /**
     * Retrieves the same String for the given Object as would be returned by the default method
     * toString(), whether or not the given Object's class overrides toString(), the toString
     * equivalent of System.identityHashCode
     * 
     * @param o the Object
     * @return the String
     **/
    public final static String identityToString(final Object o) {
        return o == null ? null : o.getClass().getName() + '@' + Integer.toHexString(System.identityHashCode(o));
    }
    
    /**
     * Retrieves the hash code of the given Object
     * 
     * @param o the Object
     * @return the hash code
     **/
    public final static int hashCode(final Object o) {
        return o == null ? 0 : o.hashCode();
    }
    
    /**
     * Converts an array into a String
     * 
     * @param a the array
     * @return the String
     **/
    public final static String arrayToString(final Object[] a) {
        return length(a) == 0 ? null : listToString(Arrays.asList(a));
    }
    
    /**
     * Converts a Collection into a String
     * 
     * @param c the Collection
     * @return the String
     **/
    public final static String collectionToString(final Collection<?> c) {
        final Iterator<?> iter = c == null ? null : c.iterator();
        
        if (!hasNext(iter)) {
            return null;
        }
        
        final StringBuilder sb = new StringBuilder();
        for (; iter.hasNext();) {
            final String s = toString(iter.next());
            if (s != null) {
                sb.append(s);
            }
            if (iter.hasNext()) {
                sb.append('\n');
            }
        }
        
        return sb.toString();
    }
    
    /**
     * Converts a Map into a String
     * 
     * @param m the Map
     * @return the String
     **/
    public final static String mapToString(final Map<?, ?> m) {
        return m == null ? null : collectionToString(m.entrySet());
    }
    
    /**
     * Converts a List into a String
     * 
     * @param l the List
     * @return the String
     **/
    public final static String listToString(final List<?> l) {
        if (!(l instanceof RandomAccess)) {
            return collectionToString(l);
        }
        
        final int size = size(l);
        if (size == 0) {
            return null;
        }
        
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                sb.append('\n');
            }
            final String s = toString(l.get(i));
            if (s != null) {
                sb.append(s);
            }
        }
        
        return sb.toString();
    }
    
    /**
     * Retrieves the Iterable or an empty Iterable if it is null; designed to iterate through an
     * Iterable without an Exception if it's null: Iterable<E> iterable = null; for (E e :
     * Util.unNull(iterable)) { }
     * 
     * @param iterable the Iterable
     * @param <E> the element type
     * @return the Iterable or an empty Iterable if it is null
     **/
    public final static <E> Iterable<E> unNull(Iterable<E> iterable) {
        if (iterable == null) {
            iterable = Collections.emptyList();
        }
        
        return iterable;
    }
    
    public final static <E> List<E> unNullList(List<E> list) {
        if (list == null) {
            list = Collections.emptyList();
        }
        
        return list;
    }
    
    /**
     * Retrieves the int[] array or an empty int[] array if it is null; designed to iterate through
     * an int[] array without an Exception if it's null: int[] iterable = null; for (int i :
     * Util.unNull(iterable)) { }
     * 
     * @param a the int[] array
     * @return the int[] array or an empty int[] array if it is null
     **/
    public final static int[] unNull(final int[] a) {
        return nvl(a, EMPTY_ARRAY_INT);
    }
    
    /**
     * Retrieves the String[] array or an empty String[] array if it is null; designed to iterate
     * through a String[] array without an Exception if it's null: String[] iterable = null; for
     * (String i : Util.unNull(iterable)) { }
     * 
     * @param a the int[] array
     * @return the int[] array or an empty int[] array if it is null
     **/
    public final static String[] unNull(final String[] a) {
        return nvl(a, EMPTY_ARRAY_STRING);
    }
    
    /**
     * Makes a copy, starting over for ConcurrentModificationExceptions
     * 
     * @param iterable the Iterable
     * @param <E> the element type
     * @return the copy
     **/
    public final static <E> Iterable<E> threadSafe(final Iterable<E> iterable) {
        if (iterable == null) {
            return Collections.emptyList();
        }
        
        final List<E> copy = new ArrayList<E>();
        while (true) {
            try {
                addAll(copy, iterable);
                return copy;
            } catch (final ConcurrentModificationException e) {
                copy.clear(); // Try again
            }
        }
    }
    
    /**
     * Retrieves the first element of an Iterable
     * 
     * @param iterable the Iterable
     * @param <E> the element type
     * @return the first element
     **/
    public final static <E> E getFirst(final Iterable<E> iterable) {
        final Iterator<E> iter = iterator(iterable);
        return hasNext(iter) ? iter.next() : null;
    }
    
    public final static <E> Iterator<E> iterator(final Iterable<E> iterable) {
        return iterable == null ? null : iterable.iterator();
    }
    
    /**
     * Determines if two objects are equal (true if both are null)
     * 
     * @param o1 Object 1
     * @param o2 Object 2
     * @return whether or not o1 equals o2
     **/
    public final static boolean equals(final Object o1, final Object o2) {
        return o1 == null ? o2 == null : o2 == null ? false : o1.equals(o2) || o2.equals(o1);
        // .equals is not always symmetric;
        // for example, if a java.sql.Timestamp t contains the same date as a java.util.Date d,
        // t.equals(d) will be true but d.equals(t) will be false
    }
    
    /**
     * Determines if two arrays contain the same elements in the same order
     * 
     * @param a1 the first Object[] array
     * @param a2 the second Object[] array
     * @return whether or not a1 contains the same elements as a2
     **/
    public final static boolean equals(final Object[] a1, final Object[] a2) {
        if (a1 == a2) {
            return true;
        }
        
        if ((a1 == null) || (a2 == null)) { // If both are null, wouldn't get past a1 == a2
            return false;
        } else if (a1.length != a2.length) {
            return false;
        }
        
        for (int i = 0; i < a1.length; i++) {
            if (!equals(a1[i], a2[i])) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Determines if two Iterables contain the same elements in the same order
     * 
     * @param i1 the first Iterable
     * @param i2 the second Iterable
     * @return whether or not i1 contains the same elements as i2
     **/
    public final static boolean equals(final Iterable<?> i1, final Iterable<?> i2) {
        return equals(i1.iterator(), i2.iterator());
    }
    
    /**
     * Determines if two Iterators contain the same elements in the same order
     * 
     * @param iter1 the first Iterator
     * @param iter2 the second Iterator
     * @return whether or not iter1 contains the same elements as iter2
     **/
    public final static boolean equals(final Iterator<?> iter1, final Iterator<?> iter2) {
        while (iter1.hasNext()) {
            if (!iter2.hasNext()) {
                return false;
            } else if (!equals(iter1.next(), iter2.next())) {
                return false;
            }
        }
        
        return !iter2.hasNext();
    }
    
    /**
     * Retrieves a comparison of two Objects
     * 
     * @param c1 the first Comparable Object
     * @param c2 the second Comparable Object
     * @param <T> the type
     * @return -1 if c1 comes before c2, +1 if c2 comes first, otherwise 0
     **/
    public final static <T extends Comparable<? super T>> int compareTo(final T c1, final T c2) {
        return compareTo(c1, c2, true);
    }
    
    /**
     * Retrieves a comparison of two Objects
     * 
     * @param c1 the first Comparable Object
     * @param c2 the second Comparable Object
     * @param nullsLast whether nulls should be ordered last
     * @param <T> the type
     * @return -1 if c1 comes before c2, +1 if c2 comes first, otherwise 0
     **/
    public final static <T extends Comparable<? super T>> int compareTo(final T c1, final T c2, final boolean nullsLast) {
        final int nullCmp = nullsLast ? 1 : -1; // nullsLast name matches DatabaseAccess.orderCriteria
        if (c1 == null) {
            //return c2 == null ? 0 : -c2.compareTo(c1);
            return c2 == null ? 0 : nullCmp;
        }
        //return c1.compareTo(c2); // For some classes, passing a null argument throws a NullPointerException
        return c2 == null ? -nullCmp : c1.compareTo(c2);
    }
    
    /**
     * Retrieves a comparison of two ints
     * 
     * @param i1 the first int
     * @param i2 the second int
     * @return -1 if i1 comes before i2, +1 if i2 comes first, otherwise 0
     **/
    public final static int compareTo(final int i1, final int i2) {
        if (i1 < i2) {
            return -1;
        }
        return i1 > i2 ? 1 : 0;
    }
    
    /**
     * Retrieves a comparison of two longs
     * 
     * @param i1 the first long
     * @param i2 the second long
     * @return -1 if i1 comes before i2, +1 if i2 comes first, otherwise 0
     **/
    public final static int compareTo(final long i1, final long i2) {
        if (i1 < i2) {
            return -1;
        }
        return i1 > i2 ? 1 : 0;
    }
    
    /**
     * Returns the length of the given array
     * 
     * @param array the array for which to return the length
     * @return the length
     **/
    public final static int length(final Object array) {
        if (array == null) {
            return 0;
        } else if (array instanceof byte[]) {
            return ((byte[]) array).length;
        } else if (array instanceof short[]) {
            return ((short[]) array).length;
        } else if (array instanceof int[]) {
            return ((int[]) array).length;
        } else if (array instanceof long[]) {
            return ((long[]) array).length;
        } else if (array instanceof float[]) {
            return ((float[]) array).length;
        } else if (array instanceof double[]) {
            return ((double[]) array).length;
        } else if (array instanceof boolean[]) {
            return ((boolean[]) array).length;
        } else if (array instanceof char[]) {
            return ((char[]) array).length;
        }
        return ((Object[]) array).length;
    }
    
    /**
     * Returns the length of the given array
     * 
     * @param array the array for which to return the length
     * @return the length
     **/
    public final static int length(final Object[] array) {
        return array == null ? 0 : array.length;
    }
    
    /**
     * Returns the length of the given array
     * 
     * @param array the array for which to return the length
     * @return the length
     **/
    public final static int length(final byte[] array) {
        return array == null ? 0 : array.length;
    }
    
    /**
     * Returns the length of the given array
     * 
     * @param array the array for which to return the length
     * @return the length
     **/
    public final static int length(final int[] array) {
        return array == null ? 0 : array.length;
    }
    
    /**
     * Reverses a byte[] array
     * 
     * @param b the byte[] array
     * @param off the offset at which to
     * @param len int
     **/
    public final static void reverse(final byte[] b, final int off, final int len) {
        final int s = len / 2;
        for (int i = off, j = len - 1; i < s; i++, j--) {
            final byte t = b[i];
            b[i] = b[j];
            b[j] = t;
        }
    }
    
    /**
     * Sorts the given List
     * 
     * @param l the List to sort
     * @param <E> the element type
     * @param <L> the list type
     * @return the List after sorting it
     **/
    public final static <E extends Comparable<? super E>, L extends List<E>> L sort(final L l) {
        if (l == null) {
            return null;
        }
        Collections.sort(l);
        
        return l;
    }
    
    /**
     * Retrieves a List of elements beginIndex through the end of the given List
     * 
     * @param l the List
     * @param beginIndex the begin index
     * @param <E> the element type
     * @return the sub-List
     **/
    public final static <E> List<E> sublist(final List<E> l, final int beginIndex) {
        return sublist(l, beginIndex, l.size());
    }
    
    /**
     * Retrieves a List of elements beginIndex through endIndex - 1 of the given List
     * 
     * @param l the List
     * @param beginIndex the begin index
     * @param endIndex the end index
     * @param <E> the element type
     * @return the sub-List
     **/
    public final static <E> List<E> sublist(final List<E> l, final int beginIndex, final int endIndex) {
        final List<E> sub = new ArrayList<E>(endIndex - beginIndex);
        
        for (int i = beginIndex; i < endIndex; i++) {
            sub.add(l.get(i));
        }
        
        return sub;
    }
    
    public final static Object[] toArray(final Collection<?> c) {
        return c == null ? null : c.toArray();
    }
    
    /**
     * Returns the size of the given Collection (List, Set, etc.)
     * 
     * @param c the Collection for which to return the size
     * @return the size
     **/
    public final static int size(final Collection<?> c) {
        return c == null ? 0 : c.size();
    }
    
    /**
     * Retrieves whether the given Collection has any content
     * 
     * @param c the Collection
     * @return whether the given Collection has any content
     **/
    public final static boolean isValued(final Collection<?> c) {
        return size(c) > 0;
    }
    
    public final static boolean isEmpty(final Collection<?> c) {
        return size(c) == 0;
    }
    
    /**
     * Returns the size of the given Map
     * 
     * @param m the Map for which to return the size
     * @return the size
     **/
    public final static int size(final Map<?, ?> m) {
        return m == null ? 0 : m.size();
    }
    
    /**
     * Retrieves whether the given Map has any content
     * 
     * @param m the Map
     * @return whether the given Map has any content
     **/
    public final static boolean isValued(final Map<?, ?> m) {
        return size(m) > 0;
    }
    
    /**
     * Returns the size of the given Iterable
     * 
     * @param i the Iterable for which to return the size
     * @return the size
     **/
    public final static int size(final Iterable<?> i) {
        return i == null ? 0 : size(i.iterator());
    }
    
    /**
     * Retrieves whether the given Iterable has any content
     * 
     * @param i the Iterable
     * @return whether the given Iterable has any content
     **/
    public final static boolean isValued(final Iterable<?> i) {
        return i == null ? false : isValued(i.iterator());
    }
    
    /**
     * Returns the size of the given Iterator
     * 
     * @param i the Iterator for which to return the size
     * @return the size
     **/
    public final static int size(Iterator<?> i) {
        int size = 0;
        
        for (; hasNext(i); size++, i.next()) {
            ;
        }
        i = i instanceof ListIterator ? reset((ListIterator<?>) i) : i;
        
        return size;
    }
    
    /**
     * Retrieves whether the given Iterator has any content
     * 
     * @param i the Iterator
     * @return whether the given Iterator has any content
     **/
    public final static boolean isValued(final Iterator<?> i) {
        return hasNext(i);
    }
    
    public final static boolean hasNext(final Iterator<?> i) {
        return (i != null) && i.hasNext();
    }
    
    /**
     * Converts the given Iterable to a List if needed
     * 
     * @param i the Iterable
     * @param <E> the element type
     * @return the List
     **/
    public final static <E> List<E> toList(final Iterable<E> i) {
        return i instanceof List ? (List<E>) i : list(i);
    }
    
    /**
     * Retrieves an ArrayList containing the elements of the given Iterable
     * 
     * @param i the Iterable
     * @param <E> the element type
     * @return the ArrayList
     **/
    public final static <E> ArrayList<E> list(final Iterable<E> i) {
        return list(i.iterator());
    }
    
    /**
     * Retrieves an ArrayList containing the elements of the given Iterator
     * 
     * @param i the Iterator
     * @param <E> the element type
     * @return the ArrayList
     **/
    public final static <E> ArrayList<E> list(final Iterator<E> i) {
        return list(i, new ArrayList<E>());
    }
    
    /**
     * Retrieves a Collection containing the elements of the given Iterator
     * 
     * @param i the Iterator
     * @param c the Collection in which to store the Iterator contents
     * @param <C> the Collection type
     * @param <E> the element type
     * @return the List
     **/
    public final static <C extends Collection<E>, E> C list(Iterator<E> i, final C c) {
        for (; hasNext(i); c.add(i.next())) {
            ;
        }
        i = i instanceof ListIterator ? reset((ListIterator<E>) i) : i;
        
        return c;
    }
    
    public final static <E> Iterable<E> wrap(final Iterator<E> iter) {
        return new Iterable<E>() {
            @Override
            public Iterator<E> iterator() {
                return iter;
            }
        };
    }
    
    /**
     * Resets a ListIterator
     * 
     * @param i the ListIterator
     * @param <E> the element type
     * @return the ListIterator
     **/
    public final static <E> ListIterator<E> reset(final ListIterator<E> i) {
        for (; (i != null) && i.hasPrevious(); i.previous()) {
            ;
        }
        return i;
    }
    
    /**
     * Adds all elements of the source Iterable to the destination Collection
     * 
     * @param dst the destination Collection
     * @param src the source Iterable
     * @param <E> the element type
     **/
    public final static <E> void addAll(final Collection<E> dst, final Iterable<? extends E> src) {
        for (final E elem : unNull(src)) {
            dst.add(elem);
        }
    }
    
    /**
     * Adds all elements of the source Collection to the destination Collection
     * 
     * @param dst the destination Collection
     * @param src the source Collection
     * @param <E> the element type
     * @return whether the destination was changed as a result of the call
     **/
    public final static <E> boolean addAll(final Collection<E> dst, final Collection<E> src) {
        return (dst != null) && (src != null) ? dst.addAll(src) : false;
    }
    
    /**
     * Adds the given Object to the given List, allocating a new List if necessary
     * 
     * @param l the List
     * @param o the Object to add
     * @param <E> the element type
     * @return the List
     **/
    public final static <E> List<E> add(List<E> l, final E o) {
        (l = ((l == null) ? new ArrayList<E>() : l)).add(o);
        return l;
    }
    
    /**
     * Adds the given Object to the given List, allocating a new List if necessary
     * 
     * @param initialCapacity the initial capacity
     * @param l the List
     * @param o the Object to add
     * @param <E> the element type
     * @return the List
     **/
    public final static <E> List<E> add(final int initialCapacity, List<E> l, final E o) {
        (l = ((l == null) ? new ArrayList<E>(initialCapacity) : l)).add(o);
        return l;
    }
    
    /**
     * Places the given Object in the given List at the desired index, allocating if necessary
     * 
     * @param l the List
     * @param i the desired index
     * @param o the Object to add
     * @param <E> the element type
     **/
    public final static <E> List<E> set(List<E> l, final int i, final E o) {
        if (l == null) {
            l = new ArrayList<E>();
        }
        
        for (int j = l.size(); j <= i; j++) {
            l.add(null);
        }
        l.set(i, o);
        
        return l;
    }
    
    /**
     * Adds the given Object to the given List if the Object is not contained in it or null,
     * allocating a new List if necessary
     * 
     * @param l the List
     * @param o the Object to add
     * @param <E> the element type
     * @return the List
     **/
    public final static <E> List<E> addIfNotContainedOrNull(final List<E> l, final E o) {
        return (o != null) && !contains(l, o) ? add(l, o) : l;
    }
    
    /**
     * Adds the given Object to the given List if the Object is not contained in it, allocating
     * a new List if necessary
     * 
     * @param l the List
     * @param o the Object to add
     * @param <E> the element type
     * @return the List
     **/
    public final static <E> List<E> addIfNotContained(final List<E> l, final E o) {
        return !contains(l, o) ? add(l, o) : l;
    }
    
    /**
     * Adds the given Object to the given List if the Object is not null, allocating
     * a new List if necessary
     * 
     * @param l the List
     * @param o the Object to add
     * @param <E> the element type
     * @return the List
     **/
    public final static <E> List<E> addIfNotNull(final List<E> l, final E o) {
        return o != null ? add(l, o) : l;
    }
    
    /**
     * Adds the given Object to the given List if the Object is not null, allocating
     * a new List if necessary
     * 
     * @param initialCapacity the initial capacity
     * @param l the List
     * @param o the Object to add
     * @param <E> the element type
     * @return the List
     **/
    public final static <E> List<E> addIfNotNull(final int initialCapacity, final List<E> l, final E o) {
        return o != null ? add(initialCapacity, l, o) : l;
    }
    
    /**
     * Adds the given Object to the given Collection if the Object is not null
     * 
     * @param c the Collection
     * @param o the Object to add
     * @param <E> the element type
     **/
    public final static <E> void addIfNotNull(final Collection<E> c, final E o) {
        if (o != null) {
            c.add(o);
        }
    }
    
    /**
     * Adds the given Objects to the given List that are not contained in it or null,
     * allocating a new List if necessary
     * 
     * @param l the List
     * @param c the Collection of Objects to add
     * @param <E> the element type
     * @return the List
     **/
    public final static <E> List<E> addAllIfNotContainedOrNull(List<E> l, final Collection<E> c) {
        if (c != null) {
            final Iterator<E> iter = c.iterator();
            while (iter.hasNext()) {
                l = addIfNotContainedOrNull(l, iter.next());
            }
        }
        
        return l;
    }
    
    /**
     * Adds the given Objects to the given List that are not null, allocating a new List if
     * necessary
     * 
     * @param l the List
     * @param c the Collection of Objects to add
     * @param <E> the element type
     * @return the List
     **/
    public final static <E> List<E> addAllIfNotNull(List<E> l, final Collection<E> c) {
        if (c != null) {
            for (final E elem : c) {
                l = addIfNotNull(l, elem);
            }
        }
        
        return l;
    }
    
    /**
     * Given a List already sorted in accordance with a Comparator, adds a new element at the
     * appropriate index, allocating a new List if necessary
     * 
     * @param l the sorted List
     * @param o the new element
     * @param cmp the Comparator
     * @param <E> the element type
     **/
    public final static <E> List<E> add(List<E> l, final E o, final Comparator<E> cmp) {
        if (l == null) {
            l = new ArrayList<E>();
        }
        int i = l.size() - 1;
        // Search backwards; if added in right order, then only one comparison needed
        for (; i >= 0; i--) {
            if (cmp.compare(l.get(i), o) <= 0) {
                break;
            }
        }
        l.add(i + 1, o);
        return l;
    }
    
    public final static <E> void ensureCapacity(final Collection<E> col, final int minCapacity) {
        if (col instanceof ArrayList) {
            ((ArrayList<E>) col).ensureCapacity(minCapacity);
        }
    }
    
    /**
     * Retrieves an unmodifiable List backed by the given List, or null if the given List is null
     * 
     * @param l the List
     * @param <E> the element type
     * @return the unmodifiable List
     **/
    public final static <E> List<E> unmodifiableList(final List<E> l) {
        return l == null ? null : Collections.unmodifiableList(l);
    }
    
    /**
     * Removes nulls from the given List
     * 
     * @param list the List
     * @param <L> the List type
     * @return the List
     **/
    public final static <L extends List<?>> L trim(final L list) {
        int size = size(list);
        for (int i = 0; i < size; i++) {
            if (list.get(i) == null) {
                list.remove(i);
                i--;
                size--;
            }
        }
        
        return isEmpty(list) ? null : list;
    }
    
    /**
     * Adds the given Objects to the given List, allocating a new List if necessary
     * 
     * @param l the List
     * @param o1 the first Object to add
     * @param o2 the second Object to add
     * @param <E> the element type
     * @return the List
     **/
    public final static <E> List<E> add(final List<E> l, final E o1, final E o2) {
        return add(add(l, o1), o2);
    }
    
    /**
     * Removes and returns the last Object of the given List
     * 
     * @param l the List
     * @param <E> the element type
     * @return the last Object
     **/
    public final static <E> E removeLast(final List<E> l) {
        final int size = size(l);
        
        return size == 0 ? null : l.remove(size - 1);
    }
    
    /**
     * Retrieves the last Object of the given List
     * 
     * @param l the List
     * @param <E> the element type
     * @return the last Object
     **/
    public final static <E> E getLast(final List<E> l) {
        final int size = size(l);
        
        return size == 0 ? null : l.get(size - 1);
    }
    
    /**
     * Retrieves the String at the given index of the given String[] array
     * 
     * @param a the String[] array
     * @param i the index
     * @return the String
     **/
    public final static String get(final String[] a, final int i) {
        return length(a) <= i ? null : a[i];
    }
    
    public final static Map<String, String> toArgMap(final String[] args, final int firstKey) {
        final int argsSize = length(args);
        final Map<String, String> map = new LinkedHashMap<String, String>(Math.max(0, (argsSize - 2) / 2));
        for (int i = firstKey; i < argsSize; i++) {
            map.put(args[i].substring(1), args[++i]);
        }
        return map;
    }
    
    /**
     * Retrieves the first element of the given List that .equals(toFind)
     * 
     * @param list the List
     * @param toFind the Object to find
     * @param <E> the element type
     * @return the List element
     **/
    public final static <E> E get(final List<E> list, final Object toFind) {
        return list == null ? null : list.get(list.indexOf(toFind));
    }
    
    /**
     * Retrieves the Object at the given index of the given List
     * 
     * @param list the List
     * @param i the index
     * @param <E> the element type
     * @return the Object
     **/
    public final static <E> E get(final List<E> list, final int i) {
        return (i < 0) || (size(list) <= i) ? null : list.get(i);
    }
    
    /**
     * Retrieves the Object with the given key from the given Map
     * 
     * @param map the Map
     * @param key the key
     * @param <V> the value type
     * @return the Object
     **/
    public final static <V> V get(final Map<?, V> map, final Object key) {
        return map == null ? null : map.get(key);
    }
    
    /**
     * Retrieves the Object with the given key from the given Map, or null if it does not match the
     * expected type
     * 
     * @param map the Map
     * @param key the key
     * @param expectedValueType the Class expected value type
     * @param <V> the Map value type
     * @param <E> the expected value type
     * @return the Object
     **/
    public final static <V, E extends V> E get(final Map<?, V> map, final Object key, final Class<E> expectedValueType) {
        final V value = get(map, key);
        
        if ((value != null) && ReflectUtil.isAssignableFrom(expectedValueType, value.getClass())) {
            return cast(value);
        }
        
        return null;
    }
    
    public final static String getProperty(final Properties prop, final String key) {
        return prop == null ? null : prop.getProperty(key);
    }
    
    /**
     * Adds the given value to the List stored under the given key in the given Map, creating
     * a new List if needed
     * 
     * @param map the Map
     * @param key the key of the desired List
     * @param value the value
     * @param <K> the key type
     * @param <V> the value type
     **/
    public final static <K, V> void addToMapList(final Map<K, List<V>> map, final K key, final V value) {
        List<V> list = map.get(key);
        
        if (list == null) {
            list = new ArrayList<V>();
            map.put(key, list);
        }
        
        list.add(value);
    }
    
    /**
     * Determines if the given Iterable contains the given Object
     * 
     * @param c the Iterable
     * @param o the Object
     * @return true if c contains o, false otherwise
     **/
    public final static boolean contains(final Iterable<?> c, final Object o) {
        for (final Object i : c) {
            if (equals(i, o)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Determines if the given Collection contains the given Object
     * 
     * @param c the Collection
     * @param o the Object
     * @return true if c contains o, false otherwise
     **/
    public final static boolean contains(final Collection<?> c, final Object o) {
        return c == null ? false : c.contains(o);
    }
    
    /**
     * Determines if the given Map contains the given key
     * 
     * @param m the Map
     * @param o the key
     * @return true if m contains o, false otherwise
     **/
    public final static boolean containsKey(final Map<?, ?> m, final Object o) {
        return m == null ? false : m.containsKey(o);
    }
    
    public final static boolean containsAny(final Collection<?> c1, final Collection<?> c2) {
        for (final Object o : c2) {
            if (c1.contains(o)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Determines if the given Object is in the given array
     * 
     * @param toFind the Object to find
     * @param set the Object[] array
     * @return true the Object is found in the array
     **/
    public final static boolean in(final Object toFind, final Object... set) {
        for (final Object o : set) {
            if (equals(o, toFind)) {
                return true;
            }
        }
        
        return false;
    }
    
    public final static boolean inIgnoreCase(final String toFind, final String... set) {
        for (final String s : set) {
            if (equalsIgnoreCase(s, toFind)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Determines if the given int is in the given array
     * 
     * @param toFind the int to find
     * @param set the int[] array
     * @return true the int is found in the array
     **/
    public final static boolean in(final int toFind, final int... set) {
        for (final int o : set) {
            if (o == toFind) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Determines if the given array contains the given String, ignoring case
     * 
     * @param a the String[] array
     * @param s the String
     * @return true if a contains s, false otherwise
     **/
    public final static boolean containsIgnoreCase(final String[] a, final String s) {
        for (final String e : a) {
            if (equalsIgnoreCase(e, s)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Creates a Properties instance from the given array of 2-element key/value arrays
     * 
     * @param entries the array of 2-element key/value arrays
     * @return the Properties
     **/
    public final static Properties createProperties(final String[][] entries) {
        final Properties prop = new Properties();
        
        for (final String[] entry : entries) {
            if (entry.length != 2) {
                throw new RuntimeException("Invalid property entry: " + Arrays.asList(entry));
            }
            prop.setProperty(entry[0], entry[1]);
        }
        
        return prop;
    }
    
    /**
     * Loads the Properties
     * 
     * @param prop the Properties
     * @param location the Properties source location
     * @return the Properties
     **/
    public final static Properties loadProperties(Properties prop, final String location) {
        for (final String loc : splitExactIntoList(location, getPathSeparator())) {
            prop = loadProperties1(prop, loc);
        }
        return prop;
    }
    
    private final static Properties loadProperties1(Properties prop, final String location) {
        InputStream is = null;
        
        try {
            /*
            CORE-1439
            Properties in general aren't required.
            DataSource might be specified with a setter instead of a property.
            Could be injected by Spring.
            Most other properties have defaults or aren't required.
            */
            is = getStream(location);
            if (prop == null) {
                prop = new Properties();
            }
            if (is == null) {
                log.warn(getNotFoundMessage(location));
                return prop;
            }
            
            if (log.isDebugEnabled()) {
                final Properties sourceProperties = new Properties();
                sourceProperties.load(is);
                for (final String key : sourceProperties.stringPropertyNames()) {
                    if (prop.get(key) != null) {
                        log.debug("Overriding property with key: " + key);
                    }
                    prop.setProperty(key, sourceProperties.getProperty(key));
                }
            } else {
                prop.load(is);
            }
            
            return prop;
        } catch (final Exception e) {
            throw toRuntimeException(e);
        } finally {
            IoUtil.close(is);
        }
    }
    
    /**
     * Retrieves the Properties
     * 
     * @param location the Properties source location
     * @return the Properties
     **/
    public final static Properties getProperties(final String location) {
        return loadProperties(null, location);
    }
    
    public final static PropertyResolver registerPropertyResolver(final PropertyResolver propertyResolver) {
        if (propertyResolver == null) {
            throw new NullPointerException("Cannot register a null PropertyResolver");
        }
        final PropertyResolver old = Util.propertyResolver;
        Util.propertyResolver = propertyResolver;
        return old;
    }
    
    public final static PropertyResolver getPropertyResolver() {
        return propertyResolver;
    }
    
    public final static void setProperties(final Properties prop) {
        ((RegenPropertyResolver) propertyResolver).setProperties(prop); // Don't know what to do if we get a ClassCastException
    }
    
    /**
     * Retrieves the property value, searching the System properties defined as JVM arguments and
     * the global properties file
     * 
     * @param key the property key
     * @return the property value
     **/
    public final static String getProperty(final String key) {
        return propertyResolver.getProperty(key);
    }
    
    /**
     * Retrieves the property value or the given default
     * 
     * @param key the property key
     * @param defaultValue the default value
     * @return the property value
     **/
    public final static String getProperty(final String key, final String defaultValue) {
        return nvl(getProperty(key), defaultValue);
    }
    
    /**
     * Retrieves whether the given property is true
     * 
     * @param key the property's key
     * @param defaultValue the default if the property is unspecified or not a boolean value
     * @return whether the given property is true
     **/
    public final static boolean isProperty(final String key, final boolean defaultValue) {
        return toBoolean(getProperty(key), defaultValue);
    }
    
    /**
     * Retrieves whether the given value is true
     * 
     * @param booleanString the value
     * @param defaultValue the default if the value is unspecified or not a boolean value
     * @return whether the given value is true
     **/
    public final static boolean toBoolean(final String booleanString, final boolean defaultValue) {
        return toBoolean(booleanString, TRUE, FALSE, defaultValue);
    }
    
    public final static boolean toBoolean(final String in, final String tru, final String fls, final boolean defVal) {
        return defVal ? !fls.equalsIgnoreCase(in) : tru.equalsIgnoreCase(in);
    }
    
    /**
     * Retrieves the given property as an int
     * 
     * @param key the property's key
     * @param defaultValue the default if the property is unspecified or not an int value
     * @return the value
     **/
    public final static int getPropertyInt(final String key, final int defaultValue) {
        final String value = getProperty(key);
        //if (isAllDigits(value)) { // Too restrictive
        if (isValued(value)) {
            try {
                return Integer.parseInt(value);
            } catch (final Exception e) {
                // Just use defaultValue
            }
        }
        return defaultValue;
    }
    
    /**
     * Retrieves the given property as a long
     * 
     * @param key the property's key
     * @param defaultValue the default if the property is unspecified or not a long value
     * @return the value
     **/
    public final static long getPropertyLong(final String key, final long defaultValue) {
        final String value = getProperty(key);
        if (isValued(value)) {
            try {
                return Long.parseLong(value);
            } catch (final Exception e) {
                // Just use defaultValue
            }
        }
        return defaultValue;
    }
    
    /**
     * Retrieves the property value from the given Properties whose key contains the given partial
     * key
     * 
     * @param prop the Properties to search
     * @param partialKey the partial key
     * @return the property value
     **/
    public final static String getPropertyPartialKey(final Properties prop, final String partialKey) {
        if (prop == null) {
            return null;
        }
        
        final Iterator<?> iter = prop.keySet().iterator();
        while (iter.hasNext()) {
            final String key = (String) iter.next();
            if (key.startsWith("org.regenstrief.") && key.endsWith(partialKey)) {
                return prop.getProperty(key);
            }
        }
        
        return null;
    }
    
    /**
     * Retrieves the property value, searching the System properties defined as JVM arguments and
     * the global properties file
     * 
     * @param partialKey the partial key
     * @return the property value
     **/
    public final static String getPropertyPartialKey(final String partialKey) {
        for (final String s : new String[] { "database", "query", "queryposer" }) {
            final String key = "org.regenstrief." + s + "." + partialKey;
            if (propertyResolver.hasProperty(key)) {
                return propertyResolver.getProperty(key);
            }
        }
        return null;
    }
    
    /**
     * It might not be a good idea to call System.exit from our code. However, since we already do,
     * it seems good to funnel all of these through this method. That way we can add features like
     * sending an email notification to someone whenever a program exits if we want.
     * 
     * @param status the status
     **/
    public final static void exit(final int status) {
        // Might want to make sure that java.lang.SecurityManager.checkExit(int status) always throws a SecurityException on servers
        // and checkPermission with the RuntimePermission("exitVM") 
        log.info("Exiting", new Exception("Exiting"));
        System.exit(status);
    }
    
    /**
     * Remove null values from an array
     * 
     * @param originalArray Array
     * @param newArrayLength String
     * @return Array
     */
    public final static Object[] removeNullsFromArray(Object[] originalArray, final int newArrayLength) {
        final Object[] tempBreakdowns = new Object[newArrayLength];
        final int origLength = originalArray.length;
        int i = 0, j = 0;
        
        while ((i < newArrayLength) && (j < origLength)) {
            if (originalArray[j] != null) {
                tempBreakdowns[i] = originalArray[j];
                i++;
            }
            j++;
        }
        
        originalArray = new Object[newArrayLength];
        
        for (i = 0; i < newArrayLength; i++) {
            originalArray[i] = tempBreakdowns[i];
        }
        return originalArray;
    }
    
    /**
     * Returns i as a 2 digit string
     * 
     * @param i an integer
     * @return i as a 2 digit string
     **/
    public final static String to2DigitString(final int i) {
        return i < 10 ? "0" + i : Integer.toString(i);
    }
    
    /**
     * Creates a 3 digit string from an integer with leading zeros if needed
     * 
     * @param i the integer
     * @return the 3 digit String
     **/
    public final static String to3DigitString(final int i) {
        String val = Integer.toString(i);
        if (i < 100) { // IF need some leading zeroes
            val = ((i < 10) ? "00" : "0") + val;
        }
        return val;
    }
    
    /**
     * Creates a 4 digit string from an integer with leading zeros if needed
     * 
     * @param i the integer
     * @return the 4 digit String
     **/
    public final static String to4DigitString(final int i) {
        String num = Integer.toString(i); // 4-digit number within exchange
        if (i < 1000) { // IF need leading zeroes
            if (i < 100) { // THEN IF need more than one
                num = ((i < 10) ? "000" : "00") + num;
            } else {
                num = "0" + num;
            }
        }
        return num;
    }
    
    /**
     * Returns the double as a String, without a decimal point if the double actually stores an
     * integer
     * 
     * @param d the double
     * @return the double as a String
     **/
    public final static String doubleToString(final double d) {
        // If the double equals itself rounded to an integer, then it stores an integer
        final int i = (int) d;
        return i == d ? String.valueOf(i) : String.valueOf(d);
    }
    
    public final static String doubleToString(final Number d) {
        return d == null ? null : doubleToString(d.doubleValue());
    }
    
    public final static Integer toInteger(final Object o) {
        if (o == null) {
            return null;
        }
        return o instanceof Integer ? (Integer) o : Integer.valueOf(((Number) o).intValue());
    }
    
    public final static Long toLong(final Object o) {
        if (o == null) {
            return null;
        }
        return o instanceof Long ? (Long) o : Long.valueOf(((Number) o).longValue());
    }
    
    /**
     * Deletes the file with the given name
     * 
     * @param fileName the file name
     **/
    public final static void delete(final String fileName) {
        final File f = new File(fileName);
        final String sep = getFileSeparator();
        
        if (f.isDirectory()) {
            final String children[] = f.list(), t = fileName + sep;
            for (int i = 0; i < children.length; i++) {
                delete(t + children[i]);
            }
        }
        f.delete();
    }
    
    /**
     * Retrieves whether the file with the given name exists
     * 
     * @param fileName the file name
     * @return whether the file exists
     **/
    public final static boolean exists(final String fileName) {
        return fileName == null ? false : new File(fileName).exists();
    }
    
    /**
     * Renames the file with the given name
     * 
     * @param src the source file name
     * @param dst the destination file name
     **/
    public final static void rename(final String src, final String dst) {
        new File(src).renameTo(new File(dst));
    }
    
    /**
     * Reads the bytes of the InputStream
     * 
     * @param loc the location of the InputStream
     * @return the byte[] array
     * @throws Exception if an I/O problem occurs
     **/
    public final static byte[] readBytes(final String loc) throws Exception {
        final InputStream is = getStream(loc);
        
        try {
            return readBytes(is);
        } finally {
            is.close();
        }
    }
    
    /**
     * Reads the bytes of the InputStream
     * 
     * @param is the InputStream
     * @return the byte[] array
     * @throws IOException if an I/O problem occurs
     **/
    public final static byte[] readBytes(final InputStream is) throws IOException {
        byte[] buf = new byte[BUFFER_SIZE], back;
        int size = 0, amt;
        
        while ((amt = is.read(buf, size, BUFFER_SIZE)) != -1) {
            size += amt;
            if (buf.length - size < BUFFER_SIZE) {
                back = new byte[buf.length * 2];
                System.arraycopy(buf, 0, back, 0, size);
                buf = back;
            }
        }
        if (amt > 0) {
            size += amt;
        }
        
        if (size < buf.length) {
            back = new byte[size];
            System.arraycopy(buf, 0, back, 0, size);
            buf = back;
        }
        
        return buf;
    }
    
    /**
     * Reads the given File
     * 
     * @param f the File
     * @return the contents of the file
     * @throws IOException if an I/O error occurs while reading the file
     **/
    public final static String readFile(final File f) throws IOException {
        final FileInputStream in = new FileInputStream(f);
        try {
            return readStream(in);
        } finally {
            IoUtil.close(in);
        }
    }
    
    /**
     * Reads the file with the given name
     * 
     * @param name the file name
     * @return the contents of the file
     * @throws IOException if an I/O error occurs while reading the file
     **/
    public final static String readFile(final String name) throws IOException {
        final StringBuilder sb = new StringBuilder();
        final char[] buffer = new char[BUFFER_SIZE];
        FileReader f = null;
        int i = 0;
        InputStream is;
        
        try {
            f = new FileReader(name);
            while (i >= 0) {
                i = f.read(buffer, 0, BUFFER_SIZE);
                if (i > 0) {
                    sb.append(buffer, 0, i);
                }
            }
            
            f.close();
            return sb.toString();
        } catch (final Exception e) {}
        is = getStreamRequired(name);
        try {
            return readStream(is);
        } finally {
            is.close();
        }
    }
    
    /**
     * Reads an int from the file with the given name
     * 
     * @param name the file name
     * @return the int, or -1 if an error occurred
     **/
    public final static int readFileInt(final String name) {
        String s = null;
        
        try {
            s = trim(readFile(name));
        } catch (final Exception e) {}
        if (s != null) {
            try {
                return Integer.parseInt(s);
            } catch (final Exception e) {}
        }
        
        return -1;
    }
    
    /**
     * Reads the contents of the given URL
     * 
     * @param url the URL
     * @return the contents of the URL
     * @throws IOException if an I/O problem occurs
     **/
    public final static String readURL(final String url) throws IOException {
        final InputStream in = getStreamFromURL(url);
        try {
            return readStream(in);
        } finally {
            IoUtil.close(in);
        }
    }
    
    /**
     * Reads the number of items that an importer should skip
     * 
     * @param name the file name
     * @param label the label to print if any items are to be skipped
     * @return the number of items that an importer should skip
     **/
    public final static int readSkipAmount(final String name, final String label) {
        final int skip = readFileInt(name);
        
        if (skip > 0) {
            log.info("Skipping " + skip + " " + label + " according to " + name);
        }
        
        return skip;
    }
    
    /**
     * Retrieves the given file name's extension
     * 
     * @param fileName the file name
     * @return the extension
     */
    public final static String getExtension(final String fileName) {
        final int dot = fileName.lastIndexOf('.');
        return dot < 0 ? null : fileName.substring(dot + 1);
    }
    
    /**
     * Replaces name.ext with name.label.ext
     * 
     * @param inName the input file name
     * @param label the processed label
     * @return the processed name
     */
    public final static String getProcessedFileName(final String inName, final String label) {
        final int dot = inName.lastIndexOf('.');
        return inName.substring(0, dot + 1) + label + inName.substring(dot);
    }
    
    /**
     * Retrieves whether the given character is in the range from '0' to '9' (more restrictive than
     * Character.isDigit)
     * 
     * @param c the character
     * @return whether the given character is in the range from '0' to '9'
     **/
    public final static boolean isDigit(final char c) {
        return (c >= '0') && (c <= '9');
    }
    
    /**
     * Retrieves whether all characters in the given String are in the range from '0' to '9'
     * 
     * @param s the String
     * @return whether the characters are in the range from '0' to '9'
     **/
    public final static boolean isAllDigits(final String s) {
        return (isAllDigits(s, 0, length(s)));
    }
    
    public final static boolean isAllDigits(final String s, final int beginIndex, final int endIndex) {
        if (endIndex <= beginIndex) {
            return false; // if isAllDigits is true, Integer.parseInt shouldn't fail
        }
        
        for (int i = beginIndex; i < endIndex; i++) {
            if (!isDigit(s.charAt(i))) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Retrieves whether the given String contains numeric data anywhere in it
     * 
     * @param s the String
     * @return whether the given String contains numeric data anywhere in it
     **/
    public final static boolean hasNumeric(final String s) {
        final int len = length(s);
        
        for (int i = 0; i < len; i++) {
            if (isDigit(s.charAt(i))) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Retrieves whether the given character is a hexidecimal value (where 10-15 are represented as
     * a-f)
     * 
     * @param c the character
     * @return whether the character is a hexidecimal value
     **/
    public final static boolean isLowercaseHex(final char c) {
        return isDigit(c) || ((c >= 'a') && (c <= 'f'));
    }
    
    /**
     * Converts the given bytes into a hexidecimal String (where 10-15 are represented as a-f)
     * 
     * @param bytes the bytes
     * @return the hexidecimal String
     **/
    public final static String toLowercaseHex(final byte[] bytes) {
        final int len = length(bytes);
        
        if (len == 0) {
            return null;
        }
        
        final StringBuilder sb = new StringBuilder(len * 2);
        for (int i = 0; i < len; i++) {
            final byte b = bytes[i];
            appendLowercaseHex(sb, (b >> 4) & 0x0F);
            appendLowercaseHex(sb, b & 0x0F);
        }
        
        return isEmpty(sb) ? "0" : sb.toString();
    }
    
    /**
     * Appends the given half-byte (4 bits, 0-15) onto the given StringBuilder as a hexidecimal char
     * (where 10-15 are represented as a-f)
     * 
     * @param sb the StringBuilder
     * @param halfbyte the half-byte
     **/
    private final static void appendLowercaseHex(final StringBuilder sb, final int halfbyte) {
        final char c = toLowercaseHex(halfbyte);
        
        if ((c != '0') || (sb.length() > 0)) {
            sb.append(c);
        }
    }
    
    /**
     * Converts the given half-byte (4 bits, 0-15) into a hexidecimal char (where 10-15 are
     * represented as a-f)
     * 
     * @param halfbyte the half-byte
     * @return the hexidecimal char
     **/
    public final static char toLowercaseHex(final int halfbyte) {
        return toHex('a', halfbyte);
    }
    
    public final static char toUppercaseHex(final int halfbyte) {
        return toHex('A', halfbyte);
    }
    
    private final static char toHex(final char a, final int halfbyte) {
        return (char) (halfbyte < 10 ? '0' + halfbyte : a + halfbyte - 10);
    }
    
    public final static String toUppercaseHexString(final char c) {
        return toUppercaseHexString((int) c);
    }
    
    private final static String toUppercaseHexString(final int i) {
        return "" + toUppercaseHex(i / 16) + toUppercaseHex(i % 16);
    }
    
    /**
     * Retrieves whether the given character is a hexidecimal value
     * 
     * @param c the character
     * @return whether the character is a hexidecimal value
     **/
    public final static boolean isHex(final char c) {
        return isDigit(c) || ((c >= 'a') && (c <= 'f')) || ((c >= 'A') && (c <= 'F'));
    }
    
    public final static int fromHex(final char c) {
        if ((c >= '0') && (c <= '9')) {
            return c - '0';
        } else if (c >= 'a' && c <= 'f') {
            return c - 'a' + 10;
        } else if (c >= 'A' && c <= 'F') {
            return c - 'A' + 10;
        }
        throw new IllegalArgumentException("Invalid hex character " + c);
    }
    
    public final static int fromHex(final char c1, final char c2) {
        return fromHex(c1) * 16 + fromHex(c2);
    }
    
    public final static int fromHex(final CharSequence s) {
        return fromHex(s, 0, length(s));
    }
    
    public final static int fromHex(final CharSequence s, final int off, final int len) {
        int r = 0;
        for (int i = off; i < len; i++) {
            r = (r * 16) + fromHex(s.charAt(i));
        }
        return r;
    }
    
    /**
     * Retrieves whether all characters in the given String are whitespace
     * 
     * @param s the String
     * @return whether the characters are whitespace
     **/
    public final static boolean isAllWhitespace(final String s) {
        for (int i = length(s) - 1; i >= 0; i--) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Retrieves the SHA-1 hash of the given String
     * 
     * @param in the String
     * @return the SHA-1 hash
     **/
    public final static String sha1(final String in) {
        try {
            final MessageDigest md = MessageDigest.getInstance("SHA-1");
            
            md.update(in.getBytes());
            
            return toLowercaseHex(md.digest());
        } catch (final Exception e) {
            throw toRuntimeException(e);
        }
    }
    
    /**
     * <p>
     * Title: My Authenticator
     * </p>
     * <p>
     * Description: Creates a PasswordAuthentication.
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
    private static class MyAuthenticator extends Authenticator {
        
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            /*
             * If we access a URL that does not require authentication, this is not invoked.
             * If we access a URL that requires authentication twice, this is not invoked the second time.
             * It must use cookies or do something to keep track of different URLs,
             * because it doesn't always send the username/password after the first time.
             * If we access another URL after the first authentication,
             * and if we look at the HTTP headers sent,
             * there is no Authorization header.
             * If we then access another site requiring authentication, this is invoked again.
             */
            //getRequestingHost(); // Could use a different property for each host; also getRequestingSite()
            try {
                String user = authenticatorUser.get();
                final String password;
                if (user == null) {
                    user = System.getProperty(PROP_AUTHENTICATOR_USER);
                    password = System.getProperty(PROP_AUTHENTICATOR_PASSWORD);
                } else {
                    password = authenticatorPassword.get();
                }
                return new PasswordAuthentication(user, password.toCharArray());
            } finally {
                authenticatorUser.remove();
                authenticatorPassword.remove();
            }
        }
    }
    
    /**
     * Modifies the HTTP basic authentication username/password for the current thread. They will be
     * destroyed as soon as they are used.
     * 
     * @param username the username
     * @param password the password
     */
    public final static void setHttpBasicAuthentication(final String username, final String password) {
        authenticatorUser.set(username);
        authenticatorPassword.set(password);
    }
    
    /**
     * Stores a System property if none is currently stored for the key
     * 
     * @param key the property key
     * @param val the property value
     **/
    public final static void setPropertyIfNeeded(final String key, final String val) {
        if (System.getProperty(key) == null) {
            setProperty(key, val);
        }
    }
    
    /**
     * Stores a System property
     * 
     * @param key the property key
     * @param val the property value
     **/
    public final static void setProperty(final String key, final String val) {
        //set(System.getProperties(), key, val); // System.setProperty does more than System.getProperties().setProperty
        if (val != null) {
            System.setProperty(key, val);
        } else {
            System.getProperties().remove(key);
        }
    }
    
    public final static void set(final Properties prop, final String key, final String val) {
        if (val != null) {
            prop.setProperty(key, val);
        } else {
            prop.remove(key);
        }
    }
    
    /**
     * Retrieves the line separator for the current system ("\n" for Unix, "\r\n" for Windows)
     * 
     * @return the line separator
     **/
    public final static String getLineSeparator() {
        return System.getProperty("line.separator");
    }
    
    /**
     * Retrieves the file separator for the current system ("/" for Unix, "\" for Windows)
     * 
     * @return the file separator
     **/
    public final static String getFileSeparator() {
        return System.getProperty("file.separator");
    }
    
    /**
     * Retrieves the classpath separator for the current system (":" for Unix, ";" for Windows)
     * 
     * @return the classpath separator
     **/
    public final static String getPathSeparator() {
        return System.getProperty("path.separator");
    }
    
    /**
     * Appends the file separator to the end of the directory name if needed
     * 
     * @param dir the directory name
     * @return the formatted directory name
     **/
    public final static String formatDir(String dir) {
        if (dir == null) {
            return null;
        }
        
        final String sep = getFileSeparator();
        if (!dir.endsWith(sep)) {
            dir += sep;
        }
        
        return dir;
    }
    
    /**
     * Retrieves the temporary directory name
     * 
     * @return the temporary directory name
     **/
    public final static String getTempDir() {
        return formatDir(System.getProperty("java.io.tmpdir"));
    }
    
    /**
     * Retrieves the number of bytes remaining in the InputStream and closes it
     * 
     * @param is the InputStream
     * @return the number of bytes
     **/
    public final static long size(final InputStream is) {
        long size = 0, c;
        
        try {
            while (true) {
                while ((c = is.skip(BUFFER_SIZE)) > 0) { // Skip bytes
                    size += c;
                }
                if (is.read() == -1) { // EOF cannot be determined by skipping, so read
                    break;
                }
                size++; // Add a byte for the read
            }
            is.close();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
        
        return size;
    }
    
    /**
     * Skips the desired the number of bytes (or until EOF) in the InputStream
     * 
     * @param is the InputStream
     * @param skip the number of bytes to skip
     * @return the number of bytes
     **/
    public final static long skip(final InputStream is, final long skip) {
        long size = 0, c, remaining = skip;
        
        try {
            do {
                while ((remaining > 0) && ((c = is.skip(remaining)) > 0)) { // Skip bytes
                    size += c;
                    remaining = skip - size;
                }
                if ((remaining == 0) || (is.read() == -1)) { // EOF cannot be determined by skipping, so read
                    break;
                }
                size++; // Add a byte for the read
                remaining = skip - size;
            } while (remaining > 0);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
        
        return size;
    }
    
    /**
     * Retrieves the InputStream from the given location, attempting to open it by any means
     * 
     * @param location the location of the InputStream
     * @return the InputStream
     * @throws IOException if an I/O problem occurs
     **/
    public final static LabeledInputStream getStreamFromFile(final String location) throws IOException {
        return LabeledInputStream.create(location, wrapStream(new FileInputStream(location), location));
    }
    
    /**
     * Retrieves the InputStream from the given location, attempting to open it by any means
     * 
     * @param location the location of the InputStream
     * @return the InputStream
     * @throws IOException if an I/O problem occurs
     **/
    public final static LabeledInputStream getStreamFromURL(final String location) throws IOException {
        return LabeledInputStream.create(location, wrapStream(getRawStreamFromURL(location), location));
    }
    
    /**
     * Wraps the InputStream if needed for proper reading
     * 
     * @param stream the InputStream to wrap
     * @param location the location of the InputStream
     * @return the InputStream
     * @throws IOException if an I/O problem occurs
     **/
    public final static InputStream wrapStream(final InputStream stream, String location) throws IOException {
        if (stream == null) {
            return null;
        }
        location = location.toUpperCase();
        if (location.endsWith(".ZIP")) {
            final ZipInputStream zip = new ZipInputStream(stream);
            zip.getNextEntry(); // Prepares the InputStream for the first file in the .zip;
            return zip; // this method should not be used for .zip files containing more than one file
        } else if (location.endsWith(".GZ")) {
            return new GZIPInputStream(stream);
        }
        
        return stream;
    }
    
    /**
     * Retrieves the InputStream of the URLConnection, or the error InputStream if an Exception
     * occurs
     * 
     * @param con the URLConnection
     * @return the InputStream
     * @throws IOException if an I/O problem occurs
     **/
    public final static InputStream getRawStream(final URLConnection con) throws IOException {
        try {
            return con.getInputStream();
        } catch (final IOException e) {
            if (con instanceof HttpURLConnection) {
                final InputStream errStream = ((HttpURLConnection) con).getErrorStream();
                // Somtimes it's null, so throw the orignal Exception
                if (errStream != null) {
                    return errStream;
                }
            }
            
            throw e;
        }
    }
    
    /**
     * Retrieves the InputStream of the URL, or the error InputStream if an Exception occurs
     * 
     * @param url the URL
     * @return the InputStream
     * @throws IOException if an I/O problem occurs
     **/
    public final static InputStream getRawStream(final URL url) throws IOException {
        initializeSecurity();
        return getRawStream(url.openConnection());
    }
    
    /**
     * Initializes HTTP authentication and SSL
     **/
    public final synchronized static void initializeSecurity() {
        if (myAuthenticator == null) {
            RegenPropertyResolver.loadProperties(); // Copy from our properties file to System properties (including SSL properties)
            
            myAuthenticator = new MyAuthenticator(); // Enable HTTP authentication
            Authenticator.setDefault(myAuthenticator);
            
            //System.setProperty(PROP_HANDLER, HANDLER_SUN_SSL); // This can't be mixed with setHostnameVerifier
            //System.setProperty("javax.net.debug", "ssl:handshake"); // This can be put in our properties file
            
            Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        }
    }
    
    public final static String post(final String url, final String content) throws IOException {
        final URLConnection ucon = new URL(url).openConnection();
        
        ucon.setDoInput(true);
        if (content != null) {
            ucon.setDoOutput(true);
            ucon.getOutputStream().write(content.getBytes());
        }
        
        return readStream(getRawStream(ucon));
    }
    
    /**
     * Retrieves the InputStream from the given URL, or the error InputStream if an Exception occurs
     * 
     * @param location the URL
     * @return the InputStream
     * @throws IOException if an I/O problem occurs
     **/
    public final static InputStream getRawStreamFromURL(final String location) throws IOException {
        return getRawStream(new URL(location));
    }
    
    /**
     * Retrieves the InputStream from the given location, attempting to open it by any means,
     * without decompressing
     * 
     * @param location the location of the InputStream
     * @return the InputStream
     **/
    public final static InputStream getRawStream(final String location) {
        InputStream stream = null;
        String s = location;
        final String sep = getFileSeparator();
        String paths[] = null;
        int n = 1;
        
        for (int i = 0; i < n; i++) {
            if (i > 0) {
                if (contains(paths[i - 1], sep)) {
                    paths[i - 1] = paths[i - 1].substring(0, paths[i - 1].lastIndexOf(sep) + 1);
                }
                s = paths[i - 1] + location;
            }
            
            try {
                stream = getRawStreamFromURL(s);
            } catch (final Exception e) {}
            if (stream != null) {
                break;
            }
            try {
                stream = Util.class.getResourceAsStream(s);
            } catch (final Exception e) {}
            if (stream != null) {
                break;
            }
            try {
                stream = Util.class.getClassLoader().getResourceAsStream(s);
                //stream = getStream(Util.class.getClassLoader(), s);
            } catch (final Exception e) {}
            if (stream != null) {
                break;
            }
            try {
                stream = ClassLoader.getSystemResourceAsStream(s);
            } catch (final Exception e) {}
            if (stream != null) {
                break;
            }
            /*try
            {	stream = getStream(ClassLoader.getSystemClassLoader(), s);
            } catch (Exception e) {}
            if (stream != null)
            {	break;
            }*/
            try {
                stream = new FileInputStream(s);
            } catch (final Exception e) {}
            if (stream != null) {
                break;
            }
            
            if (i == 0) {
                paths = getClassPath();
                n = length(paths);
                n = n > 0 ? n + 1 : n;
            }
        }
        
        if ((stream == null) && (location.charAt(0) != '/')) {
            return getRawStream('/' + location);
        }
        
        return stream;
    }
    
    /*private final static InputStream getStream(ClassLoader loader, final String location)
    {
    	InputStream stream = null;

    	while (loader != null)
    	{
    		try
    		{	stream = loader.getResourceAsStream(location);
    		} catch (final Exception e) {}
    		if (stream != null)
    		{	return stream;
    		}
    		loader = loader.getParent();
    	}

    	return null;
    }*/

    public final static URI toURI(final String location) {
        if (exists(location)) {
            return new File(location).toURI();
        }
        final URL url = Util.class.getClassLoader().getResource(location);
        try {
            if (url != null) {
                return url.toURI();
            }
            return new URI(location);
        } catch (final URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
    
    public final static URL toURL(final String location) {
        try {
            if (exists(location)) {
                return new File(location).toURI().toURL();
            }
            final URL url = Util.class.getClassLoader().getResource(location);
            if (url != null) {
                return url;
            }
            return new URL(location);
        } catch (final MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Retrieves the class path String
     * 
     * @return the class path String
     **/
    public final static String getClassPathString() {
        // catalina.base=C:\Program Files\Apache Software Foundation\Tomcat 5.5
        // shared.loader=${catalina.base}/shared/classes,${catalina.base}/shared/lib/*.jar
        return System.getProperty("java.class.path");
        //return concatWithDelim(System.getProperty("java.class.path"), getLoaderProperty("shared.loader"), getPathSeparator());
    }
    
    //private final static String getFullClassPathString() { // See CompileUtil }
    
    /*private final static String getLoaderProperty(final String key) {
        // Should also expand wildcards
        final String evaluated = getEvaluatedProperty(key);
        return evaluated == null ? null : replaceAllExact(evaluated, ",", getPathSeparator());
    }*/

    private final static String getEvaluatedProperty(final String key) {
        return evaluatePropertyValue(System.getProperty(key));
    }
    
    protected final static String evaluatePropertyValue(final String value) {
        if (value == null) {
            return null;
        }
        int bgn = 0, i = 0, dlr, len = 0;
        StringBuilder sb = null;
        while ((dlr = value.indexOf('$', i)) >= 0) {
            int opn = dlr + 1;
            if (value.charAt(opn) != '{') {
                i = opn;
                continue;
            }
            opn++;
            final int cls = value.indexOf('}', opn);
            if (cls < opn) {
                continue;
            }
            if (sb == null) {
                len = value.length();
                sb = new StringBuilder(len);
            }
            sb.append(value, bgn, dlr);
            sb.append(getEvaluatedProperty(value.substring(opn, cls)));
            bgn = i = cls + 1;
        }
        if (sb == null) {
            return value;
        }
        sb.append(value, bgn, len);
        return sb.toString();
    }
    
    /**
     * Retrieves the tokens of the class path
     * 
     * @return the String[] array of tokens of the class path
     **/
    public final static String[] getClassPath() {
        return splitPath(getClassPathString());
    }
    
    private final static String[] splitPath(final String classpath) {
        final char delim = getPathSeparator().charAt(0); //contains(classpath, ';') ? ';' : ':';
        
        return splitExact(classpath, delim);
    }
    
    public final static String[] listFiles(final String location) throws IOException {
        try {
            final File f = new File(location);
            if (f.exists() && f.isDirectory()) {
                return f.list();
            }
        } catch (final Exception e) {
            // Try another way
        }
        BufferedReader in = null;
        try {
            // Works for directories in a ClassLoader.getResource, not sure about other URLs
            in = getBufferedReader(location);
            String line;
            final List<String> files = new ArrayList<String>();
            while ((line = in.readLine()) != null) {
                files.add(line);
            }
            return files.toArray(EMPTY_ARRAY_STRING);
        } finally {
            IoUtil.close(in);
        }
    }
    
    /**
     * Retrieves the InputStream from the given location, attempting to open it by any means,
     * decompressing if needed
     * 
     * @param location the location of the InputStream
     * @return the InputStream
     * @throws IOException if an I/O problem occurs
     **/
    public final static LabeledInputStream getStream(final String location) throws IOException {
        return LabeledInputStream.create(location, wrapStream(getRawStream(location), location));
    }
    
    /**
     * Retrieves the InputStream from the given location, throwing an Exception it it is not found
     * 
     * @param location the location of the InputStream
     * @return the InputStream
     * @throws IOException if an I/O problem occurs
     **/
    public final static LabeledInputStream getStreamRequired(final String location) throws IOException {
        final LabeledInputStream in = getStream(location);
        
        if (in == null) {
            throw new IOException(getNotFoundMessage(location));
        }
        
        return in;
    }
    
    private final static String getNotFoundMessage(final String location) {
        return "Could not find " + location;
    }
    
    /**
     * Retrieves whether InputStream from the given location is available
     * 
     * @param location the location of the InputStream
     * @return the whether the InputStream is available
     **/
    public final static boolean isStreamAvailable(final String location) {
        InputStream in;
        
        try {
            in = getRawStream(location);
        } catch (final Exception e) {
            in = null;
        }
        try {
            return in != null;
        } finally {
            IoUtil.close(in);
        }
    }
    
    /**
     * Retrieves the Reader from the given location, attempting to open it by any means,
     * decompressing if needed, using the correct character encoding
     * 
     * @param location the location of the Reader
     * @return the Reader
     * @throws IOException if an I/O problem occurs
     **/
    public final static LabeledReader getReader(final String location) throws IOException {
        final LabeledInputStream in = getStream(location);
        
        return in == null ? null : new LabeledReader(in, getEncoding(location));
    }
    
    /**
     * Retrieves the Reader from the given location, attempting to open it by any means,
     * decompressing if needed, using the correct character encoding
     * 
     * @param location the location of the Reader
     * @return the Reader
     * @throws IOException if an I/O problem occurs
     **/
    public final static LabeledReader getReaderRequired(final String location) throws IOException {
        return new LabeledReader(getStreamRequired(location), getEncoding(location));
    }
    
    /**
     * Retrieves a Reader for the given InputStream, using the correct character encoding
     * 
     * @param is the InputStream
     * @return the Reader
     * @throws Exception if an I/O problem occurs
     **/
    public static final Reader getReader(InputStream is) throws Exception {
        is = getMarkableInputStream(is, 11);
        final String enc = getEncoding(is, false);
        is.reset();
        
        if (is instanceof LabeledInputStream) {
            return new LabeledReader((LabeledInputStream) is, enc);
        }
        return new BomReader(is, enc);
    }
    
    /**
     * Retrieves the encoding of the InputStream at the given location
     * 
     * @param location the location of the InputStream
     * @return the encoding
     * @throws IOException if an I/O problem occurs
     **/
    public final static String getEncoding(final String location) throws IOException {
        return getEncoding(getStream(location));
    }
    
    /**
     * Retrieves the encoding of the InputStream and closes it
     * 
     * @param is the InputStream
     * @return the encoding
     * @throws IOException if an I/O problem occurs
     **/
    public final static String getEncoding(final InputStream is) throws IOException {
        return getEncoding(is, true);
    }
    
    /**
     * Retrieves the encoding of the InputStream and closes it if desired
     * 
     * @param is the InputStream
     * @param close whether to close the InputStream
     * @return the encoding
     * @throws IOException if an I/O problem occurs
     **/
    public final static String getEncoding(final InputStream is, final boolean close) throws IOException {
        try {
            final int b1 = is.read(), b2 = is.read();
            String enc = null;
            boolean utf16 = true;
            
            /*
            http://en.wikipedia.org/wiki/Byte_order_mark
            http://docs.oracle.com/javase/6/docs/api/java/nio/charset/Charset.html
            */
            if ((b1 == 239) && (b2 == 187) && (is.read() == 191)) {
                return BomReader.ENCODING_UTF_8_BOM;
            } else if (((b1 == 255) && (b2 == 254)) || ((b1 == 254) && (b2 == 255))) {
                enc = "UTF-16";
            } else if (b2 == 0) {
                is.read();
            }
            if ((b1 == 0) || (b2 == 0)) {
                for (int i = 0; i < 10; i++) {
                    final int j = is.read();
                    if (j < 0) {
                        break;
                    } else if (j != 0) {
                        utf16 = false;
                        break;
                    }
                    is.read();
                }
                if (utf16) {
                    enc = b2 == 0 ? "UTF-16LE" : "UTF-16BE";
                }
            }
            if (enc == null) {
                enc = getDefaultEncoding();
            }
            return enc;
        } finally {
            if (close) {
                is.close();
            }
        }
    }
    
    /**
     * Retrieves the system's default character encoding
     * 
     * @return the system's default character encoding
     **/
    public final static String getDefaultEncoding() {
        try {
            final InputStreamReader isr = new InputStreamReader(new ByteArrayInputStream(new byte[0]));
            final String enc = isr.getEncoding();
            
            isr.close();
            return enc;
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Retrieves the InputStream from the given location, caching first if the location is remote
     * 
     * @param location the location of the InputStream
     * @return the InputStream
     * @throws Exception if an I/O problem occurs
     **/
    public final static InputStream getCachedStream(final String location) throws Exception {
        if (location.startsWith("http")) {
            final String fileName = toFileName(location);
            downloadStream(fileName, location);
            return getStream(fileName);
        } else {
            return getStream(location);
        }
    }
    
    /**
     * Retrieves the Reader from the given location, caching first if the location is remote
     * 
     * @param location the location of the Reader
     * @return the Reader
     * @throws Exception if an I/O problem occurs
     **/
    public final static Reader getCachedReader(final String location) throws Exception {
        return new BomReader(getCachedStream(location), getEncoding(getCachedStream(location)));
    }
    
    /**
     * Downloads the contents of an InputStream
     * 
     * @param fileName the destination
     * @param location the source
     * @throws Exception if an I/O problem occurs
     **/
    public final static void downloadStream(final String fileName, final String location) throws Exception {
        InputStream is = null;
        final String partialFileName = fileName + ".par";
        int skip = 0;
        boolean needDownload = true;
        
        try {
            is = getStream(fileName);
        } catch (final Exception e) {}
        if (is != null) {
            is.close();
            return;
        }
        log.info("Downloading " + location + " to " + fileName);
        while (needDownload) {
            try {
                is = getStream(partialFileName);
            } catch (final Exception e) {}
            if (is != null) {
                skip = is.available();
                is.close();
            }
            is = getRawStream(location);
            try {
                if (skip > 0) {
                    skip(is, skip);
                }
                bufferedReadWrite(is, new FileOutputStream(partialFileName, true));
                needDownload = false; // Break loop if there's no Exception & this is reached
            } catch (final Exception e) {}
        }
        rename(partialFileName, fileName);
        log.info("Done downloading");
    }
    
    /**
     * Reads the given InputStream
     * 
     * @param in the InputStream
     * @return the contents of the InputStream
     **/
    public final static String readStream(final InputStream in) {
        final StringBuilder sb = new StringBuilder();
        final byte[] buffer = new byte[BUFFER_SIZE];
        int i = 0;
        
        try {
            while (i >= 0) {
                i = in.read(buffer, 0, BUFFER_SIZE);
                if (i > 0) {
                    sb.append(new String(buffer, 0, i));
                }
            }
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }
    
    /**
     * Reads the given Reader
     * 
     * @param in the Reader
     * @return the contents of the InputStream
     **/
    public final static String readReader(final Reader in) {
        final StringBuilder sb = new StringBuilder();
        final char[] buffer = new char[BUFFER_SIZE];
        int i = 0;
        
        try {
            while (i >= 0) {
                i = in.read(buffer, 0, BUFFER_SIZE);
                if (i > 0) {
                    sb.append(buffer, 0, i);
                }
            }
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }
    
    /**
     * Provides an easy way to write from a Reader to a Writer with a buffer
     * 
     * @param input Reader content to be written
     * @param output Writer place to write content
     * @param bufferSize int size of data buffer
     * @param closeOutput whether the output Writer should be closed when finished
     * @return the number of bytes that were copied
     * @throws IOException if an I/O problem occurs while reading or writing
     **/
    public static int bufferedReadWrite(final Reader input, final Writer output, final int bufferSize,
                                        final boolean closeOutput) throws IOException {
        int bytesRead, totalRead = 0;
        final char[] buff = new char[bufferSize];
        
        while (-1 != (bytesRead = input.read(buff, 0, buff.length))) {
            output.write(buff, 0, bytesRead);
            totalRead += bytesRead;
            output.flush();
        }
        
        input.close();
        if (closeOutput) {
            output.close();
        }
        return totalRead;
    }
    
    /**
     * Provides an easy way to write from a Reader to a Writer with a buffer
     * 
     * @param input Reader content to be written
     * @param output Writer place to write content
     * @param closeOutput whether the output Writer should be closed when finished
     * @return the number of bytes that were copied
     * @throws IOException if an I/O problem occurs while reading or writing
     **/
    public static int bufferedReadWrite(final Reader input, final Writer output, final boolean closeOutput)
                                                                                                           throws IOException {
        return bufferedReadWrite(input, output, BUFFER_SIZE, closeOutput);
    }
    
    /**
     * Provides an easy way to write from a Reader to a Writer with a buffer
     * 
     * @param input Reader content to be written
     * @param output Writer place to write content
     * @return the number of bytes that were copied
     * @throws IOException if an I/O problem occurs while reading or writing
     **/
    public static int bufferedReadWrite(final Reader input, final Writer output) throws IOException {
        return bufferedReadWrite(input, output, BUFFER_SIZE, true);
    }
    
    /**
     * Provides an easy way to write from a Reader to a Writer with a buffer
     * 
     * @param input Reader content to be written
     * @param output Writer place to write content
     * @param bufferSize int size of data buffer
     * @return the number of bytes that were copied
     * @throws IOException if an I/O problem occurs while reading or writing
     **/
    public static int bufferedReadWrite(final Reader input, final Writer output, final int bufferSize) throws IOException {
        return bufferedReadWrite(input, output, bufferSize, true);
    }
    
    /**
     * Provides an easy way to write from an InputStream to an OutputStream with a buffer
     * 
     * @param input InputSteram content to be written
     * @param output OutputStream place to write content
     * @param closeOutput whether the OutputStream should be closed
     * @return the number of bytes that were copied
     * @throws IOException if an I/O problem occurs while reading or writing
     **/
    public static int bufferedReadWrite(final InputStream input, final OutputStream output, final boolean closeOutput)
                                                                                                                      throws IOException {
        return bufferedReadWrite(input, output, BUFFER_SIZE, closeOutput);
    }
    
    /**
     * Provides an easy way to write from an InputStream to an OutputStream with a buffer
     * 
     * @param input InputSteram content to be written
     * @param output OutputStream place to write content
     * @return the number of bytes that were copied
     * @throws IOException if an I/O problem occurs while reading or writing
     **/
    public static int bufferedReadWrite(final InputStream input, final OutputStream output) throws IOException {
        return bufferedReadWrite(input, output, BUFFER_SIZE, true);
    }
    
    /**
     * Provides an easy way to write from an InputStream to an OutputStream with a buffer
     * 
     * @param input InputSteram content to be written
     * @param output OutputStream place to write content
     * @param bufferSize int size of data buffer
     * @return the number of bytes that were copied
     * @throws IOException if an I/O problem occurs while reading or writing
     **/
    public static int bufferedReadWrite(final InputStream input, final OutputStream output, final int bufferSize)
                                                                                                                 throws IOException {
        return bufferedReadWrite(input, output, bufferSize, true);
    }
    
    /**
     * Converts a String to an InputStream
     * 
     * @param s the String
     * @return the InputStream
     **/
    public final static InputStream toStream(final String s) {
        return new ByteArrayInputStream(s.getBytes());
    }
    
    /**
     * Reads the desired resource using the given Object's ClassLoader
     * 
     * @param o the Object whose ClassLoader should be used to read the resource
     * @param resourceName the name of the resource to read
     * @return the contents of the resource
     * @throws Exception if an I/O problem occurs
     **/
    public final static String readResource(final Object o, final String resourceName) throws Exception {
        return readStream(o.getClass().getResourceAsStream(resourceName));
    }
    
    /**
     * Reads the desired resource
     * 
     * @param resourceName the name of the resource to read
     * @return the contents of the resource
     * @throws Exception if an I/O problem occurs
     **/
    public final static String readResource(final String resourceName) throws Exception {
        return readStream(Class.class.getResourceAsStream(resourceName));
    }
    
    /**
     * Writes to the file with the given name
     * 
     * @param name the file name
     * @param s the contents of the file
     * @throws IOException if an I/O problem occurs
     **/
    public final static void writeFile(final String name, final String s) throws IOException {
        writeFile(name, s, false);
    }
    
    /**
     * Writes to the file with the given name
     * 
     * @param name the file name
     * @param s the contents of the file
     * @throws IOException if an I/O problem occurs
     **/
    public final static void appendFile(final String name, final String s) throws IOException {
        writeFile(name, s, true);
    }
    
    /**
     * Writes to the file with the given name
     * 
     * @param name the file name
     * @param s the contents of the file
     * @param append whether data should be appended to the end of the file instead of overwriting
     *            it
     * @throws IOException if an I/O problem occurs
     **/
    public final static void writeFile(final String name, final String s, final boolean append) throws IOException {
        //writer = new BufferedWriter(new FileWriter(name, append));
        final BufferedWriter writer = new BufferedWriter(getFileWriter(name, append));
        writer.write(s);
        writer.close();
    }
    
    private final static boolean DEF_APPEND = false;
    
    public final static Writer getFileWriter(final String absolutePath) throws IOException {
        return getFileWriter(absolutePath, DEF_APPEND);
    }
    
    public final static Writer getFileWriter(final String absolutePath, final boolean append) throws IOException {
        return new OutputStreamWriter(getFileOutputStream(absolutePath, append));
    }
    
    public final static Writer getFileWriter(final File f) throws IOException {
        return getFileWriter(f, DEF_APPEND);
    }
    
    public final static Writer getFileWriter(final File f, final boolean append) throws IOException {
        return new OutputStreamWriter(getFileOutputStream(f, append));
    }
    
    /**
     * Retrieves a OutputStream for the given absolute path, creating any necessary directories
     * 
     * @param absolutePath the absolute path
     * @return the OutputStream
     * @throws IOException if an I/O problem occurs
     **/
    public final static OutputStream getFileOutputStream(final String absolutePath) throws IOException {
        return getFileOutputStream(absolutePath, DEF_APPEND);
    }
    
    /**
     * Retrieves a OutputStream for the given absolute path, creating any necessary directories
     * 
     * @param absolutePath the absolute path
     * @param append whether the file should be opened in append mode
     * @return the OutputStream
     * @throws IOException if an I/O problem occurs
     **/
    public final static OutputStream getFileOutputStream(final String absolutePath, final boolean append) throws IOException {
        return getFileOutputStream(new File(absolutePath), append);
    }
    
    public final static OutputStream getFileOutputStream(final File f) throws IOException {
        return getFileOutputStream(f, DEF_APPEND);
    }
    
    public final static OutputStream getFileOutputStream(final File f, final boolean append) throws IOException {
        final File dir = f.getParentFile();
        
        if ((dir != null) && !dir.exists()) {
            dir.mkdirs();
        }
        
        return new FileOutputStream(f, append);
    }
    
    /**
     * Prints stack trace
     **/
    public final static void printStackTrace() {
        log.error("", new Exception());
    }
    
    /**
     * Retrieves the String value of the Throwable along with its stack trace
     * 
     * @param e the Throwable
     * @return the String value of the Throwable along with its stack trace
     **/
    public final static String getStackTraceString(final Throwable e) {
        if (e == null) {
            return null;
        }
        final ByteArrayOutputStream st = new ByteArrayOutputStream();
        e.printStackTrace(new PrintStream(st));
        return unNull(e.getMessage()) + getLineSeparator() + st.toString();
    }
    
    public final static String getStackTraceString(final Throwable e, final int maxLength) {
        final String s = getStackTraceString(e);
        if (length(s) <= maxLength) {
            return s;
        }
        return truncate(getAbbreviatedStackTraceString(e), maxLength);
    }
    
    public final static String getAbbreviatedStackTraceString(Throwable e) {
        /*
        Abbreviates package names and removes unnecessary content.
        Doesn't remove lines from the stack or causes.
        */
        final StringBuilder trace = new StringBuilder();
        final char br = '\n';
        StackTraceElement[] causedElems = null;
        while (e != null) {
            if (trace.length() > 0) {
                trace.append(br).append("By: ");
            }
            trace.append(replaceAllExact(abbreviateClass(e.toString()), "NullPointerException", "NPE"));
            final StackTraceElement[] elems = e.getStackTrace();
            int m = elems.length - 1, n = (causedElems == null) ? -1 : (causedElems.length - 1);
            while ((m >= 0) && (n >= 0) && elems[m].equals(causedElems[n])) {
                m--;
                n--;
            }
            for (int i = 0; i <= m; i++) {
                appendSeparator(trace, br).append(abbreviateClass(elems[i].toString()));
            }
            e = e.getCause();
            causedElems = elems;
        }
        return trace.toString();
    }
    
    public final static String abbreviateClass(String className) {
        // Abbreviate packages
        className = abbreviatePackage(className, "org", "regenstrief", "database", "util");
        className = abbreviatePackage(className, "org", "regenstrief", "database");
        className = abbreviatePackage(className, "org", "regenstrief", "util");
        className = abbreviatePackage(className, "org", "regenstrief");
        className = abbreviatePackage(className, "org");
        className = abbreviatePackage(className, "java", "lang");
        className = abbreviatePackage(className, "java", "util");
        className = abbreviatePackage(className, "java");
        className = abbreviatePackage(className, "sun");
        className = abbreviatePackage(className, "com");
        // Remove duplicate class name if method is on the instance's class instead of a superclass
        final int paren = className.indexOf('(');
        if (paren > 0) {
            final int dot = className.indexOf(".java:", paren + 1);
            if (dot > 0) {
                //className = className.substring(0, paren + 1) + className.substring(dot + 6); // method(line)
                className = className.substring(0, paren) + '.' + className.substring(dot + 6); // method.line)
                final int closed = className.indexOf(')', paren);
                if (closed > 0) {
                    className = className.substring(0, closed) + className.substring(closed + 1); // method.line
                }
            }
        }
        return className;
    }
    
    public final static String abbreviatePackage(final String className, final String... packagePath) {
        final StringBuilder path = new StringBuilder(), abbrev = new StringBuilder();
        for (final String elem : packagePath) {
            path.append(elem);
            abbrev.append(elem.charAt(0));
            // Add dot on end to make sure we don't accidentally replace javax with j, etc.
            path.append('.');
            abbrev.append('.');
        }
        return replaceAllExact(className, path.toString(), abbrev.toString());
    }
    
    /**
     * Retrieves text from the given Throwable
     * 
     * @param e the Throwable from which to extract text
     * @param mode determines the amount of text to retrieve
     * @return the text
     **/
    public final static String getExceptionText(final Throwable e, final int mode) {
        switch (mode) {
            case EXCEPTION_NONE:
                return null;
            case EXCEPTION_GET_MESSAGE:
                return e == null ? null : e.getMessage();
            case EXCEPTION_TO_STRING:
                return toString(e);
            case EXCEPTION_STACK_TRACE:
                return getStackTraceString(e);
            case EXCEPTION_CAUSE_STRINGS:
                return e == null ? null : concat(e.toString(), getExceptionText(e.getCause()));
        }
        
        return null;
    }
    
    /**
     * Retrieves text from the given Throwable
     * 
     * @param e the Throwable from which to extract text
     * @return the text
     **/
    public final static String getExceptionText(final Throwable e) {
        return getExceptionText(e, getPropertyInt(PROP_EXCEPTION_REPORTING, EXCEPTION_CAUSE_STRINGS));
    }
    
    /**
     * Creates an Exception from the Throwable if it isn't already an Exception
     * 
     * @param e the Throwable
     * @return the Exception
     **/
    public final static Exception toException(final Throwable e) {
        return e instanceof Exception ? (Exception) e : new Exception(e);
    }
    
    /**
     * Creates a RuntimeException from the Throwable if it isn't already a RuntimeException
     * 
     * @param e the Throwable
     * @return the RuntimeException
     **/
    public final static RuntimeException toRuntimeException(final Throwable e) {
        if (e == null) {
            // Without this check, toRuntimeException(e.getCause()) creates a confusing RuntimeException if cause is null
            return new NullPointerException("Problem without an Exception");
        }
        return e instanceof RuntimeException ? (RuntimeException) e : new RuntimeException(e);
    }
    
    /**
     * Creates an IOException from the Throwable if it isn't already an IOException
     * 
     * @param e the Throwable
     * @return the IOException
     **/
    public final static IOException toIOException(final Throwable e) {
        return (e instanceof IOException) ? (IOException) e : new IOException(e);
    }
    
    /**
     * Retrieves the index of the given char in the given String, ignoring their cases
     * 
     * @param s the String
     * @param c the char
     * @return the index
     **/
    public final static int indexOfIgnoreCase(final String s, final char c) {
        return CharSequences.indexOfIgnoreCase(s, c);
    }
    
    /**
     * Determines if the source contains the substring
     * 
     * @param src the source
     * @param sub the substring
     * @return true if the source contains the substring
     **/
    public final static boolean contains(final String src, final String sub) {
        return (src == null) || (sub == null) ? false : src.indexOf(sub) >= 0;
    }
    
    /**
     * Determines if the source starts with the substring
     * 
     * @param src the source
     * @param sub the substring
     * @return true if the source starts with the substring
     **/
    public final static boolean startsWith(final String src, final String sub) {
        return (src == null) || (sub == null) ? false : src.startsWith(sub);
    }
    
    /**
     * Retrieves the index of the nth occurrence of the substring in the source (0 being the first)
     * 
     * @param src the source
     * @param sub the substring
     * @param n the number of the desired occurrence
     * @return the index of the nth occurrence of the substring in the source
     **/
    public final static int nthIndexOf(final String src, final String sub, final int n) {
        int i = -1;
        
        for (int c = 0; c < n; c++) {
            i = src.indexOf(sub, i + 1);
            if (i == -1) {
                return i;
            }
        }
        
        return src.indexOf(sub, i + 1);
    }
    
    /**
     * Retrieves the number of occurrences of the given character
     * 
     * @param src the source CharSequence
     * @param c the character
     * @return the number of occurrences
     **/
    public final static int count(final CharSequence src, final char c) {
        final int size = length(src);
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (src.charAt(i) == c) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Determines if the source contains the character
     * 
     * @param src the source
     * @param c the character
     * @return true if the source contains the character
     **/
    public final static boolean contains(final String src, final char c) {
        return src == null ? false : src.indexOf(c) >= 0;
    }
    
    /**
     * Reverses the String
     * 
     * @param s the String
     * @return the reversed String
     **/
    public final static String reverse(final String s) {
        return s == null ? null : new StringBuilder(s).reverse().toString();
    }
    
    /**
     * Trims a String, returning null if it is empty
     * 
     * @param s the String to trim
     * @return the trimmed String
     **/
    public final static String trim(String s) {
        return isEmpty(s = s == null ? null : s.trim()) ? null : s;
    }
    
    /**
     * Removes leading zeros from a String
     * 
     * @param s the String
     * @return the String without leading zeros or spaces
     **/
    public final static String trimLeadingZeros(final String s) {
        return trimLeadingCharacters(s, '0');
    }
    
    /**
     * Removes leading instances of the given character from a String
     * 
     * @param s the String
     * @param c the character to remove
     * @return the String without leading instances of the given character or spaces
     **/
    public final static String trimLeadingCharacters(final String s, final char c) {
        if (s == null) {
            return null;
        }
        final int sLen = s.length(); // Find initial size of string
        for (int i = 0; i < sLen; i++) {
            final char oneChar = s.charAt(i);
            if ((oneChar > ' ') && (oneChar != c)) { // Discard spaces/control chars/0s at beginning of string
                if (i == 0) {
                    return s;
                }
                return s.substring(i); // ELSE return string starting with desired char
            }
        }
        return null; // It was all "0"s if get here
    }
    
    /**
     * Adds leading 0s to a String if needed to ensure its length is at least n
     * 
     * @param s the String
     * @param n the desired minimum length for the String
     * @return the zero-padded String
     **/
    public final static String fillLeadingZeros(final String s, final int n) {
        return fillLeadingCharacters(s, n, '0');
    }
    
    /**
     * Adds leading characters to a String if needed to ensure its length is at least n
     * 
     * @param s the String
     * @param n the desired minimum length for the String
     * @param c the character to use for padding
     * @return the zero-padded String
     **/
    public final static String fillLeadingCharacters(final String s, final int n, final char c) {
        if (s == null) {
            return null;
        }
        final int len = s.length();
        if (len >= n) {
            return s;
        }
        
        final StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n - len; i++) {
            sb.append(c);
        }
        sb.append(s);
        
        return sb.toString();
    }
    
    /**
     * Retrieves the length of the CharSequence
     * 
     * @param s the CharSequence
     * @return the length
     **/
    public final static int length(final CharSequence s) {
        return s == null ? 0 : s.length();
    }
    
    /**
     * Retrieves whether the given CharSequence has any content
     * 
     * @param s the CharSequence
     * @return whether the given CharSequence has any content
     **/
    public final static boolean isValued(final CharSequence s) {
        return length(s) > 0;
    }
    
    /**
     * Retrieves whether the given CharSequence is empty or null
     * 
     * @param s the CharSequence
     * @return whether the given CharSequence is empty or null
     */
    public final static boolean isEmpty(final CharSequence s) {
        return length(s) == 0;
    }
    
    /**
     * Retrieves the index of the first different character between two CharSequences
     * 
     * @param s1 the first CharSequence
     * @param s2 the second CharSequence
     * @return the index of the first different character (or -1 if they are identical)
     **/
    public final static int diff(final CharSequence s1, final CharSequence s2) {
        final int len1 = s1.length(), len2 = s2.length(), len = Math.min(len1, len2);
        
        for (int i = 0; i < len; i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                return i;
            }
        }
        
        return len1 == len2 ? -1 : len;
    }
    
    /**
     * Finds differences between two Readers
     * 
     * @param r1 the first Reader
     * @param r2 the second Reader
     * @param n the number of different characters to retrieve
     * @return the String[] array starting at the first different character
     **/
    public final static String[] diff(final Reader r1, final Reader r2, final int n) {
        final BufferedReader b1 = getBufferedReader(r1), b2 = getBufferedReader(r2);
        int d1, d2;
        boolean someMatch = false;
        
        try {
            while (true) {
                d1 = b1.read();
                d2 = b2.read();
                if ((d1 == -1) && (d2 == -1)) {
                    return null;
                }
                if (d1 != d2) {
                    break;
                }
                someMatch = true;
            }
            return new String[] { postDiff(d1, n, someMatch, b1), postDiff(d2, n, someMatch, b2) };
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Does postprocessing after a difference is found
     * 
     * @param d the first different character
     * @param n the number of characters to retrieve
     * @param someMatch whether some characters matched
     * @param b the BufferedReader from which to retrieve characters
     * @return the String of n characters starting with the first difference
     * @throws IOException if a problem occurs
     **/
    private final static String postDiff(int d, final int n, final boolean someMatch, final BufferedReader b)
                                                                                                             throws IOException {
        if (d >= 0) {
            final StringBuilder s = new StringBuilder(n);
            s.append('<');
            if (someMatch) {
                s.append("...");
            }
            for (int i = 0; (i < n) && (d != -1); i++) {
                if (d >= 0) {
                    s.append((char) d);
                }
                d = b.read();
            }
            if (d >= 0) {
                s.append("...");
            }
            s.append('>');
            return s.toString();
        }
        
        return null;
    }
    
    /**
     * Appends the given character to the given StringBuffer, allocating the StringBuffer if
     * necessary
     * 
     * @param sb the StringBuffer
     * @param c the char
     * @return the StringBuffer
     **/
    public final static StringBuffer append(final StringBuffer sb, final char c) {
        return (sb == null ? new StringBuffer() : sb).append(c);
    }
    
    /**
     * Appends the given String to the given StringBuffer, allocating the StringBuffer if necessary
     * 
     * @param sb the StringBuffer
     * @param s the String
     * @return the StringBuffer
     **/
    public final static StringBuffer append(final StringBuffer sb, final String s) {
        return s == null ? sb : (sb == null ? new StringBuffer() : sb).append(s);
    }
    
    /**
     * Appends part of the given CharSequence to the given StringBuffer, allocating the StringBuffer
     * if necessary
     * 
     * @param sb the StringBuffer
     * @param s the String
     * @param offset the offset of the CharSequence at which to start copying
     * @param len the number of characters to copy
     * @return the StringBuffer
     **/
    public final static StringBuffer append(StringBuffer sb, final CharSequence s, int offset, int len) {
        if (sb == null) {
            sb = new StringBuffer(len);
        }
        sb.ensureCapacity(sb.length() + len);
        for (len += offset; offset < len; offset++) {
            sb.append(s.charAt(offset));
        }
        
        return sb;
    }
    
    /**
     * Appends the given character to the given StringBuilder, allocating the StringBuilder if
     * necessary
     * 
     * @param sb the StringBuilder
     * @param c the char
     * @return the StringBuilder
     **/
    public final static StringBuilder append(final StringBuilder sb, final char c) {
        return ((sb == null) ? new StringBuilder() : sb).append(c);
    }
    
    /**
     * Appends the given String to the given StringBuilder, allocating the StringBuilder if necessary
     * 
     * @param sb the StringBuilder
     * @param s the String
     * @return the StringBuilder
     **/
    public final static StringBuilder append(final StringBuilder sb, final String s) {
        return (s == null) ? sb : ((sb == null) ? new StringBuilder() : sb).append(s);
    }
    
    public final static StringBuilder appendSeparator(final StringBuilder sb, final char c) {
        return (sb.length() > 0) ? sb.append(c) : sb;
    }
    
    /**
     * Retrieves an int from a Map
     * 
     * @param map the Map
     * @param key the key Object
     * @return the int
     **/
    public final static int getInt(final Map<?, ?> map, final Object key) {
        return ((Number) map.get(key)).intValue();
    }
    
    /**
     * Retrieves an long from a Map
     * 
     * @param map the Map
     * @param key the key Object
     * @return the long
     **/
    public final static long getLong(final Map<?, ?> map, final Object key) {
        return ((Number) map.get(key)).longValue();
    }
    
    /**
     * Retrieves a boolean from a Map
     * 
     * @param map the Map
     * @param key the key Object
     * @return the boolean
     **/
    public final static boolean getBoolean(final Map<?, ?> map, final Object key) {
        return ((Boolean) map.get(key)).booleanValue();
    }
    
    /**
     * Retrieves a String from a Map
     * 
     * @param map the Map
     * @param key the key Object
     * @return the String
     **/
    public final static String getString(final Map<?, ?> map, final Object key) {
        return toString(map == null ? null : map.get(key));
    }
    
    /**
     * Retrieves the ResourceBundle
     * 
     * @param location the ResourceBundle source location
     * @return the ResourceBundle
     **/
    public final static ResourceBundle getResourceBundle(final String location) {
        FileInputStream propin = null;
        
        try {
            propin = new FileInputStream(location);
            
            return new PropertyResourceBundle(propin);
        } catch (final Exception e) {
            IoUtil.close(propin);
            
            return ResourceBundle.getBundle(location);
        } finally {
            IoUtil.close(propin);
        }
    }
    
    /**
     * Creates a HashMap containing the same data as the given ResourceBundle; can be useful because
     * HashMap's get() method is faster than ResourceBundle's getString() method when the key is not
     * found, because HashMap returns null, while ResourceBundle throws an Exception
     * 
     * @param rb the ResourceBundle
     * @return the HashMap
     **/
    public final static HashMap<String, String> resourceBundleToHashMap(final ResourceBundle rb) {
        final Enumeration<?> e = rb.getKeys();
        final HashMap<String, String> hm = new HashMap<String, String>();
        
        while (e.hasMoreElements()) {
            final String key = e.nextElement().toString();
            hm.put(key, rb.getString(key));
        }
        
        return hm;
    }
    
    /**
     * Creates a Map<String, String> containing the same data as the given Map
     * 
     * @param m the Map
     * @return the Map<String, String>
     **/
    public final static Map<String, String> mapToStringMap(final Map<?, ?> m) {
        final Map<String, String> hm = new HashMap<String, String>(m.size());
        
        putAll(hm, m);
        
        return hm;
    }
    
    /**
     * Put all entries from the source Map into the destination Map, converting keys and values to
     * Strings if needed
     * 
     * @param dst the destination Map
     * @param src the source Map
     * @param <K> the source key type
     * @param <V> the source value type
     **/
    public final static <K, V> void putAll(final Map<String, String> dst, final Map<K, V> src) {
        final Iterator<Entry<K, V>> iter = src.entrySet().iterator();
        
        while (iter.hasNext()) {
            final Entry<K, V> entry = iter.next();
            dst.put(toString(entry.getKey()), toString(entry.getValue()));
        }
    }
    
    /**
     * Gets the first chunk of s of no more than maxSize characters, splitting at delim if possible
     * 
     * @param s the String for which to get the first chunk
     * @param maxSize the maximum size of a chunk
     * @param delim the delimiter at which to split
     * @return String
     **/
    private final static String getFirstChunk(final String s, final int maxSize, final String delim) {
        final int len = length(s);
        if ((len == 0) || (maxSize < 1) || (delim == null)) {
            return null;
        } else if (len <= maxSize) {
            return s;
        }
        
        final int delimSize;
        int splitPoint = s.lastIndexOf(delim, maxSize); // Search for the delimiter
        if (splitPoint >= 0) {
            delimSize = delim.length();
        } else {
            splitPoint = s.lastIndexOf('\n', maxSize); // line break
            if (splitPoint >= 0) {
                delimSize = 1;
            } else {
                splitPoint = s.lastIndexOf(' ', maxSize); // space
                if (splitPoint >= 0) {
                    delimSize = 1;
                } else {
                    splitPoint = maxSize; // Use max if no delimiter found
                    delimSize = 0;
                }
            }
        }
        
        if (splitPoint + delimSize <= maxSize) {
            splitPoint += delimSize;
        }
        
        return s.substring(0, splitPoint);
    }
    
    /**
     * Converts a List of Strings to a String[] array
     * 
     * @param tokens the List
     * @return the String[] array
     **/
    public final static String[] toStringArray(final List<String> tokens) {
        if (tokens == null) {
            return null;
        }
        final int size = tokens.size();
        final String[] ret = new String[size];
        
        for (int i = 0; i < size; i++) {
            ret[i] = tokens.get(i);
        }
        
        return ret;
    }
    
    /**
     * Splits s into chunks of no more than maxSize characters, splitting at delim if possible
     * 
     * @param s the String for which to split into chunks
     * @param maxSize the maximum size of a chunk
     * @param delim the delimiter at which to split
     * @return String
     **/
    public final static String[] split(String s, final int maxSize, final String delim) {
        final int delimSize = length(delim);
        
        if ((s == null) || (maxSize < 1) || (delimSize == 0)) {
            return null;
        }
        
        final List<String> tokens = new ArrayList<String>();
        String token = getFirstChunk(s, maxSize, delim);
        while (token != null) {
            tokens.add(token);
            s = s.substring(token.length());
            // Don't want to do this when splitting OBX or NTE text; would we ever?
            /*if (s.length() >= delimSize && delim.equals(s.substring(0, delimSize)))
            {	s = s.substring(delimSize);
            }
            else if (s.length() >= 1 && (s.charAt(0) == ' ' || s.charAt(0) == '\n'))
            {	s = s.substring(1);
            }*/
            token = getFirstChunk(s, maxSize, delim);
        }
        
        return toStringArray(tokens);
    }
    
    /**
     * Splits a fixed width line
     * 
     * @param s the line
     * @param widths the widths
     * @return the tokens
     **/
    public final static String[] splitFixedWidth(final String s, final int[] widths) {
        final List<String> list = new ArrayList<String>(widths.length);
        int curr = 0;
        final int len = s.length();
        
        for (int i = 0; i < widths.length; i++) {
            if (curr >= len) {
                break;
            }
            final int next = Math.min(curr + widths[i], len);
            list.add(s.substring(curr, next));
            curr = next;
        }
        
        return toStringArray(list);
    }
    
    /**
     * Splits a fixed width line
     * 
     * @param s the line
     * @param widths the widths
     * @return the tokens
     **/
    public final static String[] splitFixedWidth(final String s, final String widths) {
        final List<String> list = new ArrayList<String>();
        int i, curr = 0;
        final int len = widths.length();
        
        for (i = 0; i < len; i++) {
            final char c = widths.charAt(i);
            if ((c < '0') || (c > '9')) {
                if (i > curr) {
                    list.add(widths.substring(curr, i));
                }
                curr = i + 1;
            }
        }
        if (i > curr) {
            list.add(widths.substring(curr, i));
        }
        final int[] tokens = new int[list.size()];
        for (i = 0; i < tokens.length; i++) {
            tokens[i] = Integer.parseInt(list.get(i));
        }
        
        return splitFixedWidth(s, tokens);
    }
    
    /**
     * Retrieves the default input delimiter character
     * 
     * @return the delimiter character
     **/
    public final static char getInputDelim() {
        return getDelim(getProperty(PROP_INPUT_DELIMITER));
    }
    
    /**
     * Retrieves whether the default input delimiter character has been specified
     * 
     * @return whether the default input delimiter character has been specified
     **/
    public final static boolean isInputDelimSpecified() {
        return getProperty(PROP_INPUT_DELIMITER) != null;
    }
    
    /**
     * Parses the given delimiter String into a delimiter character (either the 1st character of the
     * String or '\t' if the String is "tab".
     * 
     * @param delimString the delimiter String
     * @return the delimiter character
     **/
    public final static char getDelim(final String delimString) {
        return "tab".equalsIgnoreCase(delimString) ? '\t' : delimString.charAt(0);
    }
    
    /**
     * Splits a delimited line, a comma delimiter indicating a CSV file requiring other escaping,
     * other delimiters handled simply
     * 
     * @param s the line
     * @param delim the delimiter char
     * @return the tokens
     **/
    public final static String[] splitParse(final String s, final char delim) {
        return delim == ',' ? splitCSV(s) : splitExact(s, delim);
    }
    
    /**
     * Escapes a String for use as a token within a line of a CSV file
     * 
     * @param s the String
     * @return the escaped String
     **/
    public final static String escapeCSV(String s) {
        final boolean needEscape;
        if (contains(s, '"')) {
            s = replaceAllExact(s, "\"", "\"\"");
            needEscape = true;
        } else {
            needEscape = contains(s, ',') || contains(s, '\n');
        }
        
        return needEscape ? '"' + s + '"' : s;
    }
    
    /**
     * Splits a comma-separated value line
     * 
     * @param s the line
     * @return the tokens
     **/
    public final static String[] splitCSV(final String s) {
        return splitCSVdelim(s, ',');
    }
    
    public final static String[] splitCSVdelim(final String s, final char delim) {
        int i = 0;
        final int len = s.length();
        final List<String> tokens = new ArrayList<String>();
        
        while (i < len) {
            final char c = s.charAt(i);
            if (c == delim) {
                i++;
                tokens.add("");
                continue;
            } else if (c == '"') {
                i++;
                int j = i;
                boolean needDoubleEscape = false, needBackslashEscape = false;
                while (true) {
                    j = s.indexOf('"', j);
                    if (j < 0) {
                        j = len;
                        break;
                    } else if (j == len - 1) {
                        break;
                    } else if ((j > 0) && (s.charAt(j - 1) == '\\')) {
                        needBackslashEscape = true;
                        j++;
                    } else if (s.charAt(j + 1) == '"') {
                        needDoubleEscape = true;
                        j += 2;
                    } else {
                        break;
                    }
                }
                String token = s.substring(i, j);
                if (needDoubleEscape) {
                    token = replaceAllExact(token, "\"\"", "\"");
                }
                if (needBackslashEscape) {
                    token = replaceAllExact(token, "\\\"", "\"");
                }
                if ((j + 1 < len) && (s.charAt(j + 1) != delim)) {
                    i = j + 1;
                    j = s.indexOf(delim, i);
                    if (j < 0) {
                        j = len;
                    }
                    token += s.substring(i, j);
                    i = j + 1;
                } else {
                    i = j + 2;
                }
                tokens.add(token);
            } else {
                int j = s.indexOf(delim, i);
                if (j < 0) {
                    j = len;
                }
                tokens.add(s.substring(i, j));
                i = j + 1;
            }
        }
        if (i == len) {
            tokens.add("");
        }
        
        return toStringArray(tokens);
    }
    
    public final static String[] splitRTF(String s) {
        if (s.endsWith("\\par")) {
            s = s.substring(0, s.length() - 4);
        }
        return splitExact(s, "\\tab ");
    }
    
    /**
     * Splits the source String into an array of substrings separated by a delimiter String; this
     * treats the delimiter String as the exact delimiter instead of a regular expression, making it
     * faster than String.split; should return the same results as src.split(delim) when delim has
     * no special characters, so trailing empty strings are removed
     * 
     * @param src the source String
     * @param delim the delimiter String
     * @return the array of substrings
     **/
    public final static String[] splitExact(final String src, final String delim) {
        return splitExact(src, delim, 0);
    }
    
    /**
     * Splits the source String into an array of substrings separated by a delimiter String; this
     * treats the delimiter String as the exact delimiter instead of a regular expression, making it
     * faster than String.split; should return the same results as src.split(delim, limit) when
     * delim has no special characters
     * 
     * @param src the source String
     * @param delim the delimiter String
     * @param limit the maximum number of Strings to return
     * @return the array of substrings
     **/
    public final static String[] splitExact(final String src, final String delim, final int limit) {
        return toStringArray(splitExactIntoList(src, delim, limit));
    }
    
    /**
     * Splits the source String into an List of substrings separated by a delimiter String;
     * this treats the delimiter String as the exact delimiter instead of a regular expression,
     * making it faster than String.split; should return results equivalent to src.split(delim) when
     * delim has no special characters, so trailing empty strings are removed
     * 
     * @param src the source String
     * @param delim the delimiter String
     * @return the List of substrings
     **/
    public final static List<String> splitExactIntoList(final String src, final String delim) {
        return splitExactIntoList(src, delim, 0);
    }
    
    /**
     * Splits the source String into an List of substrings separated by a delimiter String;
     * this treats the delimiter String as the exact delimiter instead of a regular expression,
     * making it faster than String.split; should return results equivalent to src.split(delim,
     * limit) when delim has no special characters
     * 
     * @param src the source String
     * @param delim the delimiter String
     * @param limit the maximum number of Strings to return
     * @return the List of substrings
     **/
    public final static List<String> splitExactIntoList(final String src, final String delim, final int limit) {
        final int len = src.length(), dlen = delim.length();
        int stop = -dlen, i = 0;
        final int limitm1 = limit - 1;
        
        if ((dlen == 0) || (len == 0)) {
            return arrayToArrayList(src.split(delim, limit));
        }
        final List<String> list = new ArrayList<String>();
        while (stop < len) {
            stop += dlen;
            final int start = stop;
            if (i == limitm1) {
                list.add(src.substring(start));
                return list;
            }
            stop = src.indexOf(delim, start);
            if (stop < 0) {
                stop = len;
            }
            list.add(src.substring(start, stop));
            i++;
        }
        if (limit == 0) {
            for (i = list.size() - 1; i >= 0; i--) {
                if (list.get(i).equals("")) {
                    list.remove(i);
                } else {
                    break;
                }
            }
        }
        
        return list;
    }
    
    /**
     * Splits the source String into an array of substrings separated by a delimiter char; this
     * treats the delimiter char as the exact delimiter instead of a regular expression, making it
     * faster than String.split; should return the same results as src.split(delim) when delim has
     * no special characters, so trailing empty strings are removed
     * 
     * @param src the source String
     * @param delim the delimiter char
     * @return the array of substrings
     **/
    public final static String[] splitExact(final String src, final char delim) {
        return splitExact(src, delim, 0);
    }
    
    /**
     * Splits the source String into an array of substrings separated by a delimiter char; this
     * treats the delimiter char as the exact delimiter instead of a regular expression, making it
     * faster than String.split; should return the same results as src.split(delim, limit) when
     * delim has no special characters
     * 
     * @param src the source String
     * @param delim the delimiter char
     * @param limit the maximum number of Strings to return
     * @return the array of substrings
     **/
    public final static String[] splitExact(final String src, final char delim, final int limit) {
        return toStringArray(splitExactIntoList(src, delim, limit));
    }
    
    /**
     * Splits the source String into an List of substrings separated by a delimiter char; this
     * treats the delimiter char as the exact delimiter instead of a regular expression, making it
     * faster than String.split; should return results equivalent to src.split(delim) when delim has
     * no special characters, so trailing empty strings are removed
     * 
     * @param src the source String
     * @param delim the delimiter char
     * @return the List of substrings
     **/
    public final static List<String> splitExactIntoList(final String src, final char delim) {
        return splitExactIntoList(src, delim, 0);
    }
    
    /**
     * Splits the source String into an List of substrings separated by a delimiter char; this
     * treats the delimiter char as the exact delimiter instead of a regular expression, making it
     * faster than String.split; should return results equivalent to src.split(delim, limit) when
     * delim has no special characters
     * 
     * @param src the source String
     * @param delim the delimiter char
     * @param limit the maximum number of Strings to return
     * @return the List of substrings
     **/
    public final static List<String> splitExactIntoList(final String src, final char delim, final int limit) {
        if (src == null) {
            return null;
        }
        final int len = src.length();
        int stop = -1, i = 0;
        final int limitm1 = limit - 1;
        
        if (len == 0) {
            return arrayToArrayList(src.split(Character.toString(delim), limit));
        }
        final List<String> list = new ArrayList<String>();
        while (stop < len) {
            stop += 1;
            final int start = stop;
            if (i == limitm1) {
                list.add(src.substring(start));
                return list;
            }
            stop = src.indexOf(delim, start);
            if (stop < 0) {
                stop = len;
            }
            list.add(src.substring(start, stop));
            i++;
        }
        if (limit == 0) {
            for (i = list.size() - 1; i >= 0; i--) {
                if (list.get(i).equals("")) {
                    list.remove(i);
                } else {
                    break;
                }
            }
        }
        
        return list;
    }
    
    /**
     * Removes all instances of the given character from the given String
     * 
     * @param s the String
     * @param toRemove the character to remove
     * @return the String
     **/
    public final static String remove(final String s, final char toRemove) {
        final int len = length(s);
        
        if (len == 0) {
            return null;
        }
        
        StringBuilder sb = null;
        char[] chars = null;
        int start = 0;
        for (int i = 0; i < len; i++) {
            final char c = s.charAt(i);
            if (c == toRemove) {
                if (chars == null) {
                    sb = new StringBuilder(len);
                    chars = s.toCharArray();
                }
                sb.append(chars, start, i - start);
                start = i + 1;
            }
        }
        
        if (sb == null) {
            return s;
        } else if (start < len) {
            sb.append(chars, start, len - start);
        }
        
        return sb.toString();
    }
    
    /**
     * Replaces occurrences of a source String with a new substring in the original String; this
     * treats the original substring as the exact value instead of a regular expression, making it
     * faster than String.replaceAll; should return the same result as src.replaceAll(o, n) when o
     * has no special characters
     * 
     * @param src the source String
     * @param o the original substring
     * @param n the new substring
     * @return the String after replacement
     **/
    public final static String replaceAllExact(final String src, final String o, final String n) {
        if (src == null) {
            return null;
        }
        
        int i = src.indexOf(o, 0);
        if (i == -1) {
            return src;
        }
        
        int start = 0;
        final int olen = o.length();
        final StringBuilder sb = new StringBuilder();
        
        do {
            sb.append(src.substring(start, i));
            sb.append(n);
            start = i + olen;
        } while ((i = src.indexOf(o, start)) != -1);
        sb.append(src.substring(start));
        
        return sb.toString();
    }
    
    /**
     * Replaces occurrences of a source String with a new substring in the original file
     * 
     * @param file the source file
     * @param o the original substring
     * @param n the new substring
     * @throws Exception if an I/O problem occurs
     **/
    public final static void replaceAllExactFile(final String file, final String o, final String n) throws Exception {
        writeFile(file, replaceAllExact(readFile(file), o, n));
    }
    
    /**
     * Replaces occurrencesof a source String with a new substring in the files in the path
     * 
     * @param path the source path
     * @param o the original substring
     * @param n the new substring
     * @throws Exception if an I/O problem occurs
     **/
    public final static void replaceAllExactPath(String path, final String o, final String n) throws Exception {
        final File f = new File(path);
        
        if (!f.exists()) {
            throw new Exception("Invalid path:  " + path);
        } else if (f.isFile()) {
            replaceAllExactFile(path, o, n);
        } else if (f.isDirectory()) {
            path = formatDir(path);
            final String[] list = f.list();
            final int len = length(list);
            for (int i = 0; i < len; i++) {
                replaceAllExactPath(path + list[i], o, n);
            }
        }
    }
    
    /**
     * Converts an array into an ArrayList
     * 
     * @param a the array
     * @param <E> the array type
     * @return the ArrayList
     **/
    public final static <E> ArrayList<E> arrayToArrayList(final E[] a) {
        final ArrayList<E> list = new ArrayList<E>(a.length);
        
        for (final E e : a) {
            list.add(e);
        }
        
        return list;
    }
    
    /**
     * Clears a StringBuffer so that it can be reused without allocating another StringBuffer
     * 
     * @param sb the StringBuffer
     **/
    public final static void clear(final StringBuffer sb) {
        if (sb != null) {
            sb.setLength(0); //sb.delete(0, sb.length()); is slower
        }
    }
    
    /**
     * Clears a StringBuilder so that it can be reused without allocating another StringBuilder
     * 
     * @param sb the StringBuilder
     **/
    public final static void clear(final StringBuilder sb) {
        if (sb != null) {
            sb.setLength(0);
        }
    }
    
    /**
     * Clears a Collection
     * 
     * @param c the Collection
     **/
    public final static void clear(final Collection<?> c) {
        if (c != null) {
            c.clear();
        }
    }
    
    /**
     * Clears a Map
     * 
     * @param m the Map
     **/
    public final static void clear(final Map<?, ?> m) {
        if (m != null) {
            m.clear();
        }
    }
    
    /**
     * Clears an Object[] array
     * 
     * @param a the Object[] array
     **/
    public final static void clear(final Object[] a) {
        for (int i = length(a); i >= 0; i--) {
            a[i] = null;
        }
    }
    
    public final static boolean remove(final Collection<?> c, final Object elem) {
        return (c != null) && c.remove(elem);
    }
    
    /**
     * Removes the given key from the given Map (if the Map is not null)
     * 
     * @param map the Map
     * @param key the key
     * @return the removed value
     */
    public final static <V> V remove(final Map<?, V> map, final Object key) {
        return map == null ? null : map.remove(key);
    }
    
    /**
     * Provides an easy way to write from an input stream to an output stream with a buffer
     * 
     * @param input InputStream content to be written
     * @param output OutputStream place to write content
     * @param bufferSize int size of data buffer
     * @param closeOutput boolean whether the OutputStream should be closed
     * @return the number of bytes that were copied
     * @throws IOException if an I/O problem occurs while reading or writing
     */
    public static int bufferedReadWrite(final InputStream input, final OutputStream output, final int bufferSize,
                                        final boolean closeOutput) throws IOException {
        int bytesRead, totalRead = 0;
        final byte[] buff = new byte[bufferSize];
        //BufferedInputStream inputStream = new BufferedInputStream(input);
        //BufferedOutputStream outputStream = new BufferedOutputStream(output);
        //from http://java.sun.com/j2se/1.4.2/docs/api/java/io/BufferedOutputStream.html:
        //an application can write bytes to the underlying output stream without necessarily causing a call to the underlying system for each byte written.
        // That doesn't help us since we're already reading/writing large chunks defined by the bufferSize parameter.
        
        try {
            while (-1 != (bytesRead = input.read(buff, 0, bufferSize))) {
                output.write(buff, 0, bytesRead);
                totalRead += bytesRead;
            }
            
            output.flush(); // Used to flush only if we didn't close it, but should flush then too, because some streams don't automatically flush on close.
            return totalRead;
        } finally {
            input.close(); // Don't need to check for null; if it was, we would've already gotten a NullPointerException.
            if (closeOutput) {
                output.close();
            }
        }
    }
    
    private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    
    /**
     * Reads a line from standard input
     * 
     * @return the line
     **/
    public static final String readLine() {
        try {
            return in.readLine();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Prompts the user for information and reads it
     * 
     * @param prompt the prompt
     * @return the information
     **/
    public static final String promptReadLine(final String prompt) {
        System.out.println(prompt); // User prompts written to System.out, not log
        return readLine();
    }
    
    /**
     * Prompts the user for information and reads it
     * 
     * @param prompt the prompt
     * @return the information
     **/
    public static final String promptRead(final String prompt) {
        System.out.print(prompt); // User prompts written to System.out, not log
        return readLine();
    }
    
    /**
     * Retrieves a String composed of n copies of s
     * 
     * @param s the String to replicate
     * @param n the desired number of copies of s
     * @return String
     **/
    public static final String replicate(final String s, final int n) {
        if (s == null) {
            return null;
        }
        
        final StringBuilder sb = new StringBuilder(n * s.length());
        
        for (int i = 0; i < n; i++) {
            sb.append(s);
        }
        
        return sb.toString();
    }
    
    /**
     * Retrieves the first n characters of s, or all of s if its length is less than n
     * 
     * @param s the String to truncate
     * @param n the desired number of characters
     * @return the truncated String
     **/
    public static final String truncate(final String s, final int n) {
        return length(s) <= n ? s : s.substring(0, n);
    }
    
    /**
     * Retrieves an InputStream that can be marked and marks it
     * 
     * @param in the InputStream
     * @return the markable InputStream
     **/
    public static final InputStream getMarkableInputStream(InputStream in) {
        in = in.markSupported() ? in : new BufferedInputStream(in);
        in.mark(MAX_MARK_BUFFER);
        return in;
    }
    
    /**
     * Retrieves an InputStream that can be marked and marks it
     * 
     * @param in the InputStream
     * @param buffer the buffer size
     * @return the markable InputStream
     **/
    public static final InputStream getMarkableInputStream(InputStream in, final int buffer) {
        in = in.markSupported() ? in : new BufferedInputStream(in);
        in.mark(buffer);
        return in;
    }
    
    /**
     * Retrieves a Reader that can be marked and marks it
     * 
     * @param in the Reader
     * @return the markable Reader
     **/
    public static final Reader getMarkableReader(Reader in) {
        in = in.markSupported() ? in : new BufferedReader(in);
        try {
            in.mark(MAX_MARK_BUFFER);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
        return in;
    }
    
    /**
     * Retrieves a BufferedInputStream for the given InputStream
     * 
     * @param in the InputStream
     * @return the BufferedInputStream
     **/
    public static final BufferedInputStream getBufferedInputStream(final InputStream in) {
        return in instanceof BufferedInputStream ? (BufferedInputStream) in : new BufferedInputStream(in);
    }
    
    /**
     * Retrieves a BufferedReader for the given Reader
     * 
     * @param in the Reader
     * @return the BufferedReader
     **/
    public static final BufferedReader getBufferedReader(final Reader in) {
        return in instanceof BufferedReader ? (BufferedReader) in : new BufferedReader(in);
    }
    
    /**
     * Retrieves a BufferedReader for the given location
     * 
     * @param location the location
     * @return the BufferedReader
     * @throws IOException if an I/O problem occurs
     **/
    public static final BufferedReader getBufferedReader(final String location) throws IOException {
        return getBufferedReader(getReader(location));
    }
    
    /**
     * Retrieves a BufferedOutputStream for the given OutputStream
     * 
     * @param out the OutputStream
     * @return the BufferedOutputStream
     **/
    public static final BufferedOutputStream getBufferedOutputStream(final OutputStream out) {
        return out instanceof BufferedOutputStream ? (BufferedOutputStream) out : new BufferedOutputStream(out);
    }
    
    /**
     * Retrieves a BufferedWriter for the given Writer
     * 
     * @param out the Writer
     * @return the BufferedWriter
     **/
    public static final BufferedWriter getBufferedWriter(final Writer out) {
        return out instanceof BufferedWriter ? (BufferedWriter) out : new BufferedWriter(out);
    }
    
    /**
     * Retrieves a PrintStream for the given OutputStream
     * 
     * @param out the OutputStream
     * @return the PrintStream
     **/
    public static final PrintStream getPrintStream(final OutputStream out) {
        return out instanceof PrintStream ? (PrintStream) out : new PrintStream(out);
    }
    
    public static final PrintStream getPrintStream(final String loc) {
        try {
            return getPrintStream(getFileOutputStream(loc));
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Retrieves a PrintWriter for the given Writer
     * 
     * @param out the Writer
     * @return the PrintWriter
     **/
    public static final PrintWriter getPrintWriter(final Writer out) {
        return out instanceof PrintWriter ? (PrintWriter) out : new PrintWriter(out);
    }
    
    private static Map<Class<?>, String> classMap = new java.util.IdentityHashMap<Class<?>, String>();
    
    private static Map<Class<?>, String> shortClassMap = new java.util.IdentityHashMap<Class<?>, String>();
    
    /**
     * Looks up a Class name in a cache, adding it if it is not present
     * 
     * @param c the Class
     * @return the name String
     **/
    public static final String lookupClassName(final Class<?> c) {
        String name = classMap.get(c);
        
        if (name == null) {
            name = c.getName();
            int i = name.lastIndexOf('$') + 1;
            final int size = name.length();
            
            for (; i < size; i++) {
                if (Character.isJavaIdentifierStart(name.charAt(i))) {
                    break;
                }
            }
            
            if (i > 0) {
                name = name.substring(i);
            }
            classMap.put(c, name);
        }
        
        return name;
    }
    
    /**
     * Returns the full class name for the given Class
     * 
     * @param c the Class for which to find the name
     * @return the full class name
     **/
    public static final String getFullClassName(final Class<?> c) {
        if (c.isArray()) {
            return getFullClassName(c.getComponentType()) + "[]";
        }
        final Class<?> declaringClass = c.getDeclaringClass();
        if (declaringClass != null) {
            return getFullClassName(declaringClass) + '.' + lookupClassName(c);
        }
        
        return lookupClassName(c);
    }
    
    /**
     * Returns the full class name for the given Object
     * 
     * @param o the Object for which to find the name
     * @return the full class name
     **/
    public static final String getFullClassName(final Object o) {
        return o == null ? null : getFullClassName(o.getClass());
    }
    
    /**
     * Returns the class name for the given Class
     * 
     * @param c the Class for which to find the name
     * @return the class name
     **/
    public static final String getClassName(final Class<?> c) {
        if (c == null) {
            return null;
        }
        String name = shortClassMap.get(c);
        if (name == null) {
            name = getFullClassName(c);
            final int i = name.lastIndexOf('.');
            name = i < 0 ? name : name.substring(i + 1);
            shortClassMap.put(c, name);
        }
        
        return name;
    }
    
    /**
     * Strips undisplayable characters from the String
     * 
     * @param s the String
     * @return the stripped String
     **/
    public static final String strip(final String s) {
        return strip(s, false, HANDLE_UNKNOWN_INCLUDE);
    }
    
    public final static int HANDLE_UNKNOWN_EXCEPTION = 0;
    
    public final static int HANDLE_UNKNOWN_INCLUDE = 1;
    
    public final static int HANDLE_UNKNOWN_SKIP = 2;
    
    /**
     * Strips undisplayable characters from the String
     * 
     * @param s the String
     * @param clean whether known characters should be replaced with clean characters instead of
     *            stripped
     * @param handleUnknownMode when cleaning, this indicates that an Exception should be thrown for
     *            unknown characters
     * @return the stripped String
     **/
    public static final String strip(final String s, final boolean clean, final int handleUnknownMode) {
        if (s == null) {
            return null;
        }
        
        StringBuilder sb = null;
        char[] chars = null;
        int start = 0;
        final int len = s.length();
        for (int i = 0; i < len; i++) {
            final char c = s.charAt(i);
            if (!isDisplayable(c)) {
                if (sb == null) {
                    sb = new StringBuilder(len);
                    chars = s.toCharArray();
                }
                sb.append(chars, start, i - start);
                if (clean) {
                    switch (c) {
                        // Used to put actual characters in comments, but that caused problems when Ubuntu users checked out and recommitted code
                        case 130:
                        case 8218:
                            sb.append(',');
                            break;
                        case 136:
                        case 710:
                            sb.append('^');
                            break;
                        case 139:
                        case 8249:
                            sb.append('<');
                            break;
                        case 145:
                        case 8216:
                        case 146:
                        case 8217:
                        case 180:
                            sb.append('\'');
                            break;
                        case 147: // Left "
                        case 8220:
                        case 148: // Right "
                        case 8221:
                            sb.append('"');
                            break;
                        case 150:
                        case 8211:
                        case 151: // Long -
                        case 8212:
                            sb.append('-');
                            break;
                        case 155:
                        case 8250:
                            sb.append('>');
                            break;
                        case 169: // Copyright
                            sb.append("(C)");
                            break;
                        case 171:
                            sb.append("<<");
                            break;
                        case 178: // 2 Exponent
                            sb.append("^2");
                            break;
                        case 179: // 3 Exponent
                            sb.append("^3");
                            break;
                        case 187:
                            sb.append(">>");
                            break;
                        case 188: // Fraction
                            sb.append("1/4");
                            break;
                        case 189: // Fraction
                            sb.append("1/2");
                            break;
                        case 190: // Fraction
                            sb.append("3/4");
                            break;
                        case 215: // Multiplication
                            sb.append('*');
                            break;
                        case 247: // Division
                            sb.append('/');
                            break;
                        default:
                            switch (handleUnknownMode) {
                                case HANDLE_UNKNOWN_EXCEPTION:
                                    throw new RuntimeException("Unrecognized character " + (int) c + ": " + c);
                                case HANDLE_UNKNOWN_INCLUDE:
                                    sb.append(c);
                                    break;
                            }
                    }
                }
                start = i + 1;
            }
        }
        
        if (sb == null) {
            return s;
        } else {
            if (start < len) {
                sb.append(chars, start, len - start);
            }
            return sb.toString();
        }
    }
    
    /**
     * Replaces invalid UTF-8 characters with valid characters
     * 
     * @param s the String
     * @return the cleaned String
     **/
    public static final String cleanUTF8(final String s) {
        return strip(s, true, HANDLE_UNKNOWN_EXCEPTION);
    }
    
    /**
     * Retrieves whether the character is displayable
     * 
     * @param c the character
     * @return whether the character is displayable
     **/
    public static final boolean isDisplayable(final char c) {
        return ((c > 31) && (c < 127)) || (c == 9) || (c == 10) || (c == 13);
    }
    
    /**
     * Returns the class name for the given Object
     * 
     * @param o the Object for which to find the name
     * @return the class name
     **/
    public static final String getClassName(final Object o) {
        return o == null ? null : getClassName(o.getClass());
    }
    
    /**
     * Retrieves whether two Objects are identical
     * 
     * @param o1 the first Object
     * @param o2 the second Object
     * @return whether the two Objects are identical
     **/
    public final static boolean identicalTo(final Object o1, final Object o2) {
        return diff(o1, o2) == null;
    }
    
    /**
     * Retrieves the difference between two Objects
     * 
     * @param o1 the first Object
     * @param o2 the second Object
     * @return the difference String
     **/
    public final static String diff(final Object o1, final Object o2) {
        StringBuilder b = null;
        
        if ((o1 == null) && (o2 == null)) {
            return null;
        } else if (o1 == null) {
            return "o1 is null";
        } else if (o2 == null) {
            return "o2 is null";
        } else if (!o1.getClass().equals(o2.getClass())) {
            return "Class mismatch: " + o1.getClass() + ", " + o2.getClass();
        }
        
        for (Class<?> c = o1.getClass(); c != null; c = c.getSuperclass()) {
            final Field[] fa = c.getDeclaredFields();
            for (int i = 0; i < fa.length; i++) {
                final Field f = fa[i];
                if (Modifier.isStatic(f.getModifiers())) {
                    continue;
                }
                f.setAccessible(true);
                try {
                    final Object f1 = f.get(o1), f2 = f.get(o2);
                    final boolean match;
                    if ((f1 instanceof String) && (f2 instanceof String)) {
                        match = equalsIgnoreCase((String) f1, (String) f2);
                    } else {
                        match = equals(f1, f2);
                    }
                    if (!match) {
                        b = append(b, "(").append(f.getName()).append(": ").append(f1).append(", ").append(f2).append(")");
                    }
                } catch (final IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        
        return toString(b);
    }
    
    /**
     * Returns whether or not a character is a vowel.
     * 
     * @param c the character to determine if it is a vowel
     * @return true if c is a vowel, false otherwise
     **/
    public final static boolean isVowel(final char c) {
        switch (c) {
            case 'a':
            case 'e':
            case 'i':
            case 'o':
            case 'u':
            case 'A':
            case 'E':
            case 'I':
            case 'O':
            case 'U':
                return true;
        }
        
        return false;
    }
    
    public final static boolean isLegalFileNameCharacter(final char c) {
        if (illegalFileNameCharacters == null) {
            final StringBuilder sb = new StringBuilder();
            final String tempDir = getTempDir();
            log.trace("Checking for legal file name characters, using temp directory " + tempDir);
            for (char t = 0; t < 256; t++) {
                final String tempName = tempDir + "good" + t + "good.txt";
                final File f = new File(tempName);
                try {
                    f.createNewFile();
                } catch (final Exception e) {
                    log.trace("Illegal file name character " + c + ", couldn't create " + tempName, e);
                    sb.append(t);
                }
                f.delete();
            }
            illegalFileNameCharacters = sb.toString();
            final int size = illegalFileNameCharacters.length();
            if (size == 0) {
                throw new RuntimeException("Found no illegal characters");
            } else if (size == 256) {
                throw new RuntimeException("Found no legal characters, couldn't create any temp files");
            }
        }
        return (c < 256) && !contains(illegalFileNameCharacters, c);
    }
    
    /**
     * Removes characters from the String that aren't valid for file names
     * 
     * @param s the String
     * @return the file name String
     **/
    public final static String toFileName(final String s) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0, len = s.length(); i < len; i++) {
            final char c = s.charAt(i);
            //if (Character.isLetter(c) || Character.isDigit(c) || c == '-' || c == '.' || c == '_')
            if (isLegalFileNameCharacter(c)) {
                sb.append(c);
            }
        }
        return sb.toString();
    }
    
    public final static List<File> getAllFiles(final File f) {
        final List<File> list = new ArrayList<File>();
        addAllFiles(list, f);
        return list;
    }
    
    private final static void addAllFiles(final List<File> list, final File f) {
        if (!f.isDirectory()) {
            list.add(f);
            return;
        }
        final File[] children = f.listFiles();
        final int size = length(children);
        if (size == 0) {
            return;
        }
        ensureCapacity(list, list.size() + size);
        for (final File child : children) {
            addAllFiles(list, child);
        }
    }
    
    public final static Class<?>[] getClasses() {
        return getClasses((String) null);
    }
    
    private final static class Resource {
        
        public final String containerPath;
        
        public final String resourcePath;
        
        public Resource(final String containerPath, final String resourcePath) {
            this.containerPath = containerPath;
            this.resourcePath = resourcePath;
        }
    }
    
    public final static class PackageCriterion implements Criterion {
        
        private final String prefix;
        
        private PackageCriterion(final String rootPackage) {
            this.prefix = rootPackage.endsWith(".") ? rootPackage : rootPackage + '.';
        }
        
        public final static PackageCriterion getInstance(final String rootPackage) {
            return rootPackage == null ? null : new PackageCriterion(rootPackage);
        }
        
        public final static PackageCriterion getInstance(final Class<?> siblingClass) {
            return siblingClass == null ? null : new PackageCriterion(siblingClass.getPackage().getName());
        }
        
        @Override
        public final boolean isMet(final Object o) {
            return ((String) o).startsWith(this.prefix);
        }
    }
    
    public final static Class<?>[] getClasses(final String rootPackage) {
        return getClasses(PackageCriterion.getInstance(rootPackage), null);
    }
    
    public final static Class<?>[] getClasses(final Criterion nameCriterion, final Criterion classCriterion) {
        return getClasses(nameCriterion, classCriterion, null);
    }
    
    private final static Class<?>[] getClasses(final Criterion nameCriterion, final Criterion classCriterion, final String ext) {
        final String[] _paths = getClassPath();
        List<String> paths = Arrays.asList(_paths);
        if (ext != null) {
            paths = new ArrayList<String>(paths);
            paths.add(ext);
        }
        final List<Resource> nameList = new ArrayList<Resource>();
        
        for (final String path : paths) {
            //log.info(path);
            final File f = new File(path);
            if (f.isDirectory()) {
                final int pathLength = f.getAbsolutePath().length() + 1;
                for (final File child : getAllFiles(f)) {
                    //log.info('\t' + child.getAbsolutePath().substring(pathLength));
                    nameList.add(new Resource(path, child.getAbsolutePath().substring(pathLength)));
                }
            } else if (path.endsWith(".jar")) {
                final JarFile jf;
                try {
                    jf = new JarFile(f);
                } catch (final IOException e) {
                    throw toRuntimeException(e);
                }
                final Enumeration<JarEntry> entries = jf.entries();
                while (entries.hasMoreElements()) {
                    nameList.add(new Resource(path, entries.nextElement().getName()));
                }
                try {
                    jf.close();
                } catch (final IOException e) {
                    // Can't close
                }
            }
        }
        
        final List<Class<?>> classList = new ArrayList<Class<?>>();
        final Set<String> nameSet = new HashSet<String>();
        for (final Resource resource : nameList) {
            final String childName = resource.resourcePath;
            if (childName.endsWith(".class")) {
                final String className = childName.substring(0, childName.length() - 6).replace('/', '.').replace('\\', '.');
                if (((nameCriterion == null) || nameCriterion.isMet(className)) && !nameSet.contains(className)) {
                    nameSet.add(className);
                    final Class<?> c;
                    try {
                        c = ReflectUtil.forName(className);
                        //} catch (final NoClassDefFoundError e)
                        //{	// Just keep looking
                        //} catch (final IncompatibleClassChangeError e)
                        //{	// Just keep looking
                    } catch (final Throwable e) {
                        throw new RuntimeException("Error loading class " + className + " from " + childName + " in "
                                + resource.containerPath, e);
                    }
                    if ((classCriterion == null) || classCriterion.isMet(c)) {
                        addIfNotContainedOrNull(classList, c);
                    }
                }
            }
        }
        
        return classList.toArray(EMPTY_ARRAY_CLASS);
    }
    
    public final static <T> Class<? extends T>[] getClasses(final Class<T> parent) {
        return getClasses(null, parent);
    }
    
    public final static <T> Class<? extends T>[] getClasses(final String rootPackage, final Class<T> parent) {
        return getClasses(rootPackage, null, parent, null);
    }
    
    public final static <T> Class<? extends T>[] getClasses(final String rootPackage, final Criterion nameCriterion,
                                                            final Class<T> parent, final Criterion classCriterion) {
        final Criterion nc = Criteria.getCriterion(PackageCriterion.getInstance(rootPackage), nameCriterion);
        final Criterion cc = Criteria.getCriterion(AssignableFromCriterion.getInstance(parent), classCriterion);
        return cast(getClasses(nc, cc));
    }
    
    private final static String getLocation(final Class<?> c) {
        final String className = c.getName();
        final int dot = className.lastIndexOf('.') + 1;
        final String packageName = className.substring(0, dot), packagePath = replaceAllExact(packageName, ".", "/");
        final String classLoc = packagePath + className.substring(dot) + ".class";
        URL url = Util.class.getClassLoader().getResource(classLoc);
        if (url == null) {
            final ClassLoader classLoader = c.getClassLoader();
            url = classLoader.getResource(classLoc);
            ReflectUtil.registerClassLoader(classLoader);
        }
        final String p = replaceAllExact(url.toString(), "%20", " "); // Replace with generic unescaping
        if (p.startsWith("jar:")) {
            return p.substring(p.startsWith("jar:file:") ? 9 : 4, p.indexOf('!'));
        } else {
            return p.substring(p.startsWith("file:") ? 5 : 0, p.lastIndexOf('/') + 1);
        }
    }
    
    /**
     * Retrieves the Classes of a package
     * 
     * @param c a Class in the desired package
     * @return the Classes of the desired package
     **/
    public final static Class<?>[] getClassesForPackage(final Class<?> c) {
        return getClasses(PackageCriterion.getInstance(c), null, getLocation(c));
    }
    
    public final static Class<?>[] getClassesForPackage(final Class<?> c, final Criterion nameCriterion) {
        return getClasses(Criteria.getCriterion(PackageCriterion.getInstance(c), nameCriterion), null, getLocation(c));
    }
    
    /**
     * Writes information about a .jar
     * 
     * @param path the path of the .jar
     * @param w the Writer
     * @throws Exception if an I/O problem occurs
     **/
    public final static void jarInfo(final String path, final Writer w) throws Exception {
        final JarFile jar = new JarFile(new File(path));
        final Enumeration<JarEntry> en = jar.entries();
        final PrintWriter pw = getPrintWriter(w);
        
        while (en.hasMoreElements()) {
            final String name = en.nextElement().getName();
            if (!name.endsWith(".class")) {
                continue;
            }
            try {
                Class<?> c = Class.forName(name.substring(0, name.length() - 6).replace('/', '.'));
                final Member[][] allMembers = new Member[][] { c.getFields(), c.getConstructors(), c.getMethods() };
                pw.print(c);
                for (c = c.getSuperclass(); c != null; c = c.getSuperclass()) {
                    pw.print(" extends ");
                    pw.print(c.getName());
                }
                pw.println();
                pw.println('{');
                for (int j = 0; j < allMembers.length; j++) {
                    final Member[] members = allMembers[j];
                    for (final Member member : members) {
                        if (!Modifier.isPublic(member.getModifiers())) {
                            continue;
                        }
                        pw.print('\t');
                        pw.print(member);
                        pw.println(';');
                    }
                    if ((members.length > 0) && (j < allMembers.length - 1)) {
                        pw.println();
                    }
                }
                pw.println('}');
            } catch (final Throwable e) {
                pw.println(e);
            }
            pw.println();
        }
        
        pw.flush();
        jar.close();
    }
    
    /**
     * Throws an Error if needed
     * 
     * @param error whether there was an error
     * @param errMsg the error message
     **/
    public final static void err(final boolean error, final String errMsg) {
        if (error) {
            throw new Error(errMsg);
        }
    }
    
    /**
     * Opposite of String.intern; retrieves a String instance not used elsewhere in the JVM which
     * has the same value as the given String
     * 
     * @param s the String to copy
     * @return the unique copy of the String
     **/
    public final static String unique(final String s) {
        // First intern it, so that if someone else does stumble on this string value and interns it, they'll get that instance.
        // Then copy it, so no one else will get our copied instance.
        return new String(s.intern());
    }
    
    /**
     * Retrieves a globally unique identifier (GUID)
     * 
     * @return the GUID
     **/
    public final static String guid() {
        // http://www.darrenhobbs.com/archives/2003/08/java_has_guids.html
        // unique over time within a JVM
        //return new java.rmi.server.UID().toString();
        
        // uniqueness across ALL JVM's
        return new java.rmi.dgc.VMID().toString();
    }
    
    /**
     * Retrieves a universally unique identifier (UUID)
     * 
     * @return the UUID
     **/
    public final static String uuid() {
        // The above method, guid(), should be more reliable for ensuring uniqueness.
        // Java does not have access to the MAC address when creating a UUID.
        // The advantage of this method is that it conforms to the UUID format.
        return UUID.randomUUID().toString();
    }
    
    /**
     * Retrieves whether the given String is an OID
     * 
     * @param s the String
     * @return whether the String is an OID
     **/
    public final static boolean isOid(final String s) {
        int size = length(s);
        boolean hasDot = false, prevDot;
        
        if (size == 0) {
            return false;
        } else if (!isDigit(s.charAt(0))) {
            return false;
        }
        prevDot = false;
        
        size--;
        if (!isDigit(s.charAt(size))) {
            return false;
        }
        
        for (int i = 1; i < size; i++) {
            final char c = s.charAt(i);
            if (c == '.') {
                if (prevDot) {
                    return false;
                }
                hasDot = true;
                prevDot = true;
            } else if (!isDigit(c)) {
                return false;
            } else {
                prevDot = false;
            }
        }
        
        return hasDot;
    }
    
    public final static void assertOid(final String s) {
        if ((s != null) && !isOid(s)) {
            throw new RuntimeException("Expected an OID but found " + s);
        }
    }
    
    /**
     * Retrieves whether the given String is a UUID http://en.wikipedia.org/wiki/UUID a UUID
     * consists of 32 hexadecimal digits, displayed in 5 groups separated by hyphens, in the form
     * 8-4-4-4-12 for a total of 36 characters. For example: 550e8400-e29b-41d4-a716-446655440000
     * 
     * @param s the String
     * @return whether the String is a UUID
     **/
    public final static boolean isUuid(final String s) {
        if (length(s) != 36) {
            return false;
        }
        
        for (int i = 0; i < 36; i++) {
            final char c = s.charAt(i);
            if ((i == 8) || (i == 13) || (i == 18) || (i == 23)) {
                if (c != '-') {
                    return false;
                }
            } else {
                //if (!isLowercaseHex(c)) {
                // https://tools.regenstrief.org/jira/browse/CORE-952
                if (!isHex(c)) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    /**
     * Retrieves the name of the class that launched the current application
     * 
     * @return the name
     **/
    public final static String getApplication() {
        String name = getProperty(PROP_APPLICATION);
        if (name == null) {
            name = generateApplication();
            System.setProperty(PROP_APPLICATION, name);
        }
        
        return name;
    }
    
    /**
     * Retrieves the name of the class that launched the current application
     * 
     * @return the name
     **/
    public final static String generateApplication() {
        final StackTraceElement elem = generateApplicationElement();
        
        return elem == null ? null : elem.getClassName();
    }
    
    /**
     * Retrieves the StackTraceElement of the class that launched the current application
     * 
     * @return the StackTraceElement
     **/
    public final static StackTraceElement generateApplicationElement() {
        final StackTraceElement[] trace = new Exception().getStackTrace();
        for (int i = trace.length - 1; i >= 0; i--) {
            final StackTraceElement elem = trace[i];
            if (elem.getClassName().startsWith("org.regenstrief.")) {
                return elem;
            }
        }
        
        return null;
    }
    
    /**
     * Looks at the second command line argument for the name of an outputfile
     * 
     * @param args String[] command line arguments
     * @return String
     */
    public final static String getOutputFileName(final String[] args) {
        if (length(args) > 1) {
            final String arg = args[1].trim();
            if (arg.length() > 0) {
                log.info(" Output file: " + arg);
                return arg; // If get here, have a command line file to use for input
            }
        }
        return null;
    }
    
    /**
     * Retrieves the union array of the two given arrays
     * 
     * @param a1 the first Object[] array
     * @param a2 the second Object[] array
     * @return the union Object[] array
     **/
    public final static Object[] union(final Object[] a1, final Object... a2) {
        final Object[] ret = new Object[a1.length + a2.length];
        
        System.arraycopy(a1, 0, ret, 0, a1.length);
        System.arraycopy(a2, 0, ret, a1.length, a2.length);
        
        return ret;
    }
    
    /**
     * Retrieves the union array of the two given Collections
     * 
     * @param c1 the first Collection
     * @param c2 the second Collection
     * @param <E> the element type
     * @return the union Collection
     **/
    public final static <E> Collection<E> union(final Collection<E> c1, final Collection<E> c2) {
        final int size = size(c1) + size(c2);
        
        if (size == 0) {
            return null;
        }
        
        final List<E> ret = new ArrayList<E>(size);
        addAll(ret, c1);
        addAll(ret, c2);
        
        return ret;
    }
    
    /**
     * Concatenates two Strings
     * 
     * @param s1 the first String
     * @param s2 the second String
     * @return String
     **/
    public final static String concat(final String s1, final String s2) {
        return concatWithDelim(s1, s2, DEFAULT_CONCAT_DELIM);
    }
    
    /**
     * Concatenates two Strings with the given delimiter
     * 
     * @param s1 the first String
     * @param s2 the second String
     * @param delim the delimiter
     * @return String
     **/
    public final static String concatWithDelim(final String s1, final String s2, final String delim) {
        return s1 == null ? s2 : s2 == null ? s1 : s1 + delim + s2;
    }
    
    /**
     * Concatenates three Strings
     * 
     * @param s1 the first String
     * @param s2 the second String
     * @param s3 the third String
     * @return String
     **/
    public final static String concat(final String s1, final String s2, final String s3) {
        return concatWithDelim(s1, s2, s3, DEFAULT_CONCAT_DELIM);
    }
    
    /**
     * Concatenates three Strings with the given delimiter
     * 
     * @param s1 the first String
     * @param s2 the second String
     * @param s3 the third String
     * @param delim the delimiter
     * @return String
     **/
    public final static String concatWithDelim(final String s1, final String s2, final String s3, final String delim) {
        return concatWithDelim(concatWithDelim(s1, s2, delim), s3, delim);
    }
    
    /**
     * Retrieves n!
     * 
     * @param n n
     * @return n!
     **/
    public final static long factorial(final int n) {
        long f = 1;
        
        for (int i = 2; i <= n; i++) {
            f *= i;
        }
        
        return f;
    }
    
    /**
     * Parses the tag with the given tag name from the given XML
     * 
     * @param xml the XML String
     * @param tagName the tag name
     * @return the tag
     **/
    public final static String parseTag(final String xml, final String tagName) {
        if (xml == null) {
            return null;
        }
        
        final int start = startIndex(xml, tagName);
        if (start < 0) {
            return null;
        }
        final int end = endIndex(xml, tagName);
        
        if (end < start) {
            return null;
        }
        
        return xml.substring(start, end);
    }
    
    /**
     * Parses the value for the given tag name from the given XML
     * 
     * @param xml the XML String
     * @param tagName the tag name
     * @return the value
     **/
    public final static String parseValue(final String xml, final String tagName) {
        final String tag = parseTag(xml, tagName);
        if (tag == null) {
            return null;
        }
        
        final int start = tag.indexOf('>') + 1, end = tag.lastIndexOf('<');
        
        return start >= end ? null : tag.substring(start, end);
    }
    
    /**
     * Retrieves the start index of a tag
     * 
     * @param xml the String containing the tag
     * @param tagName the tag name
     * @return the start index
     **/
    public final static int startIndex(final String xml, final String tagName) {
        int index = 0;
        final int len = tagName.length();
        int i = 0;
        
        while (index >= 0) {
            index = xml.indexOf(tagName, i);
            if (index < 0) {
                return index;
            } else if (index > 0) {
                // Confirm this tagName we found is within <tagName>
                if ((xml.charAt(index - 1) == '<') && (xml.charAt(index + len) == '>')) {
                    return index - 1;
                }
            }
            i = index + 1;
        }
        
        return -1;
    }
    
    /**
     * Retrieves the end index of a tag
     * 
     * @param xml the String containing the tag
     * @param tagName the tag name
     * @return the end index
     **/
    public final static int endIndex(final String xml, final String tagName) {
        int index = 0;
        final int len = tagName.length();
        int i = xml.length();
        
        while (index >= 0) {
            index = xml.lastIndexOf(tagName, i);
            if (index < 0) {
                return index;
            } else if (index > 1) {
                if ((xml.charAt(index - 1) == '/') && (xml.charAt(index - 2) == '<') && (xml.charAt(index + len) == '>')) {
                    return index + len + 1;
                }
            }
            i = index - 1;
        }
        
        return -1;
    }
    
    public final static boolean isIPAddress(final String ip) {
        final int ipSize = length(ip);
        if (ipSize == 0) {
            return false;
        }
        int fieldIndex = 0, fieldSize = 0;
        for (int i = 0; i < ipSize; i++) {
            final char c = ip.charAt(i);
            if (c == '.') {
                if (fieldSize == 0) {
                    return false;
                } else {
                    fieldIndex++;
                    fieldSize = 0;
                }
            } else if (isDigit(c)) {
                fieldSize++;
            } else {
                return false;
            }
        }
        return (fieldIndex == 3) && (fieldSize > 0);
    }
    
    /**
     * Retrieves a host's IP address; see SOAPClient.getLocalAddr() for local IP
     * 
     * @param hostName the host's name
     * @return the host's IP address
     **/
    public final static String getIPAddress(final String hostName) {
        try {
            //return new Socket(hostName, port).getInetAddress().getHostAddress();
            // See PropertyContext for dealing with ipv6 addresses
            return InetAddress.getByName(hostName).getHostAddress();
        } catch (final Exception e) {
            throw toRuntimeException(e);
        }
    }
    
    /**
     * Retrieves a host's name
     * 
     * @param ip the host's IP address
     * @return the host's name
     **/
    public final static String getHostName(final String ip) {
        //String[] tokens = splitExact(ip, '.');
        try {
            //return InetAddress.getByAddress(new byte[] {(byte) Integer.parseInt(tokens[0]), (byte) Integer.parseInt(tokens[1]), (byte) Integer.parseInt(tokens[2]), (byte) Integer.parseInt(tokens[3])}).getHostName();
            return InetAddress.getByName(ip).getHostName(); // The host name can either be a machine name, such as "java.sun.com", or a textual representation of its IP address
        } catch (final Exception e) {
            throw toRuntimeException(e);
        }
    }
    
    /**
     * Retrieves a new instance of the given class name
     * 
     * @param key the key of the property containing the desired class name
     * @param defaultClass the Class to instantiate if the given property is undefined
     * @return the instance Object
     **/
    public final static Object newInstance(final String key, final Class<?> defaultClass) {
        final String s = getProperty(key);
        
        return s == null ? ReflectUtil.newInstance(defaultClass) : ReflectUtil.newInstance(s);
    }
    
    /**
     * Retrieves the Thread's Runnable target
     * 
     * @param thread the Thread
     * @return the Runnable target
     **/
    public final static Runnable getTarget(final Thread thread) {
        try {
            if (threadTarget == null) {
                threadTarget = ReflectUtil.getField(Thread.class, "target");
            }
            
            final Runnable r = (Runnable) threadTarget.get(thread);
            
            return r != null ? r : thread;
        } catch (final Exception e) {
            throw toRuntimeException(e);
        }
    }
    
    /**
     * Retrieves the parent of the current Thread (not tested)
     * 
     * @return the parent Thread
     **/
    public final static Thread getParentThread() {
        return getParentThread(Thread.currentThread());
    }
    
    /**
     * Retrieves the parent of the given Thread (not tested)
     * 
     * @param t the child Thread
     * @return the parent Thread
     **/
    public final static Thread getParentThread(final Thread t) {
        final Thread[] g = new Thread[t.getThreadGroup().activeCount()];
        t.getThreadGroup().enumerate(g);
        
        return g[0];
    }
    
    /**
     * Displays and logs information
     * 
     * @param name the log file name
     * @param s the information String
     * @throws Exception if an I/O problem occurs
     **/
    public final static void log(final String name, final String s) throws Exception {
        appendFile(name, s + getLineSeparator());
        log.info(s);
    }
    
    /**
     * Retrieves the caller of the method that is invoking getCaller()
     * 
     * @return the caller
     **/
    public final static String getCaller() {
        final StackTraceElement stack[] = new Exception().getStackTrace();
        
        // stack[0] is this method; stack[1] called this method; stack[2] called stack[1] (stack[1] wants to know its own caller)
        if (length(stack) < 3) {
            return null;
        }
        
        return stack[2].toString();
    }
    
    /**
     * Retrieves the stack trace of the method that is invoking getStackTrace()
     * 
     * @return the stack trace
     **/
    public final static String getStackTrace() {
        final StackTraceElement stack[] = new Exception().getStackTrace();
        final int size = stack.length;
        final StringBuilder sb = new StringBuilder();
        final String sep = getLineSeparator();
        
        for (int i = 1; i < size; i++) {
            if (i > 1) {
                sb.append(sep);
            }
            sb.append(stack[i].toString());
        }
        
        return sb.toString();
    }
    
    /**
     * Converts the given Number to a Double
     * 
     * @param n the Number
     * @return the Double
     **/
    public final static Double toDouble(final Number n) {
        return n == null ? null : n instanceof Double ? (Double) n : new Double(n.doubleValue());
    }
    
    /**
     * Retrieves a Date relative to the current Date
     * 
     * @param numDays the offset number of days (positive to generate Dates after today, negative
     *            for Dates before today)
     * @return the Date
     **/
    public final static Date todayOffsetDate(final int numDays) {
        return new Date(todayOffsetTime(numDays));
    }
    
    public final static Date offsetDate(final Date date, final int numDays) {
        //return new Date(offsetTime(date.getTime(), numDays)); // Doesn't preserve the orignal Date's time zone
        final Calendar c = Dates.toCalendar(date);
        c.add(Calendar.DAY_OF_MONTH, numDays);
        return c.getTime();
    }
    
    public final static long todayOffsetTime(final int numDays) {
        return offsetTime(StopWatch.currentTime(), numDays);
    }
    
    public final static long offsetTime(final long time, final int numDays) {
        return time + (StopWatch.MILLIS_PER_DAY * numDays);
    }
    
    public final static boolean before(final Date date, final long compareTime) {
        return date.getTime() < compareTime;
    }
    
    public final static boolean after(final Date date, final long compareTime) {
        return date.getTime() > compareTime;
    }
    
    public final static boolean afterTodayOffset(final Date date, final int numDays) {
        //return date.after(todayOffsetDate(numDays));
        return after(date, todayOffsetTime(numDays));
    }
    
    public final static boolean beforeTodayOffset(final Date date, final int numDays) {
        //return date.before(todayOffsetDate(numDays));
        return before(date, todayOffsetTime(numDays));
    }
    
    public final static Date min(final Date a, final Date b) {
        if (a == null) {
            return b;
        } else if (b == null) {
            return a;
        }
        return a.before(b) ? a : b;
    }
    
    public final static Date max(final Date a, final Date b) {
        if (a == null) {
            return b;
        } else if (b == null) {
            return a;
        }
        return a.after(b) ? a : b;
    }
    
    /**
     * Retrieves whether the given Class is equal to or a subclass/implementation of the given class
     * name. Unlike className.equals(c.getName()), this will also check interfaces and super
     * classes. Unlike Class.isAssignableFrom(Class), this can be invoked even if the className is
     * not a loaded class.
     * 
     * @param c the Class
     * @param className the class name
     * @return whether objects of the Class are instances of the Class with the given name
     **/
    public final static boolean instanceOf(final Class<?> c, final String className) {
        if ((c == null) || (className == null)) {
            return false;
        } else if (className.equals(c.getName())) {
            return true;
        } else if (instanceOf(c.getSuperclass(), className)) {
            return true;
        }
        
        final Class<?>[] interfaces = c.getInterfaces();
        for (int i = 0, len = length(interfaces); i < len; i++) {
            if (instanceOf(interfaces[i], className)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Retrieves whether the given Object is an instance of the given class name. Unlike
     * className.equals(o.getClass().getName()), this will also check interfaces and super classes.
     * Unlike Object instanceof [class name], this can be invoked even if the className is not a
     * loaded class.
     * 
     * @param o the Object
     * @param className the class name
     * @return whether the Object is an instance of the Class with the given name
     **/
    public final static boolean instanceOf(final Object o, final String className) {
        if (o == null) {
            return false;
        }
        
        return instanceOf(o.getClass(), className);
    }
    
    /**
     * Performs an unchecked cast, so that we only need to deal with the warning in one place
     * 
     * @param o the Object to cast
     * @param <T> the type to which we want to cast the Object
     * @return the Object
     **/
    @SuppressWarnings("unchecked")
    public final static <T> T cast(final Object o) {
        return (T) o;
    }
    
    public final static <T> T assertValued(final T t) {
        if (t == null) {
            throw new NullPointerException();
        }
        return t;
    }
    
    /**
     * Retrieves the value of the base raised to the power of the exponent
     * 
     * @param b the base
     * @param e the exponent
     * @return the value
     **/
    public final static int pow(final int b, final int e) {
        if (e < 0) {
            throw new IllegalArgumentException("pow does not support negative exponent: " + e);
        }
        
        int v = 1;
        
        for (int i = 0; i < e; i++) {
            v *= b;
        }
        
        return v;
    }
    
    /**
     * Makes a deep clone of an object using serialization. The object, including all of its
     * components, must be serializable.
     * 
     * @param t the object to be cloned
     * @return a deep clone of the input object
     * @throws IOException if an I/O error occurs with the input stream or output stream
     * @throws ClassNotFoundException if the class of the input object cannot be found upon
     *             deserialization
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T deepCopy(final T t) throws IOException, ClassNotFoundException {
        ObjectOutputStream objectOutputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(t);
            objectOutputStream.flush();
            
            objectInputStream = new ObjectInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
            
            return (T) objectInputStream.readObject();
        } finally {
            IoUtil.close(objectOutputStream);
            IoUtil.close(objectInputStream);
        }
    }
    
    public final static boolean getBit(final int v, final int i) {
        return ((v & (1 << i)) >> i) == 1;
    }
    
    public final static void finalize(final Object o) {
        if (o == null) {
            return;
        }
        ReflectUtil.invoke(o, "finalize");
    }
    
    public final static <T> T call(final Callable<T> c) {
        try {
            return c == null ? null : c.call();
        } catch (final Exception e) {
            throw toRuntimeException(e);
        }
    }
}
