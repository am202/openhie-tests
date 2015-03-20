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

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.regenstrief.hl7.HL7Data;
import org.regenstrief.hl7.HL7Parser;
import org.regenstrief.hl7.HL7Properties;
import org.regenstrief.hl7.MessageProperties;
import org.regenstrief.hl7.convert.OutputUtil;
import org.regenstrief.hl7.datatype.CE;
import org.regenstrief.hl7.datatype.CWE;
import org.regenstrief.hl7.datatype.NM;
import org.regenstrief.hl7.group.UMSG_Z01;
import org.regenstrief.hl7.segment.PID;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.regenstrief.util.Util;
import org.regenstrief.util.criterion.AntiCriterion;
import org.regenstrief.util.criterion.CharSequenceContainsCriterion;
import org.regenstrief.util.reflect.ReflectUtil;

/**
 * <p>
 * Title: Utility (HL7)
 * </p>
 * <p>
 * Description: HL7 Utility Class
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
public class UtilHL7 {
    
    private static final Log log = LogFactory.getLog(UtilHL7.class);
    
    public final static String NS_URI_V2 = "urn:hl7-org:v2xml";
    
    public final static String NS_URI_V3 = "urn:hl7-org:v3";
    
    // Primitive data types
    public final static String FT_XML = "FT";
    
    public final static String NM_XML = "NM";
    
    public final static String ST_XML = "ST";
    
    public final static String TX_XML = "TX";
    
    public final static String TM_XML = "TM";
    
    // Pseudo-segment, appended to previous segment in message
    public final static String ADD_XML = "ADD";
    
    public final static String PACKAGE = "org.regenstrief.hl7.";
    
    public final static String DATATYPE = PACKAGE + "datatype.";
    
    public final static String SEGMENT = PACKAGE + "segment.";
    
    public final static String GROUP = PACKAGE + "group.";
    
    public final static String PACKAGE_EXT = PACKAGE + "ext.";
    
    //public final static String DATATYPE_EXT = PACKAGE_EXT + "datatype.";
    public final static String SEGMENT_EXT = PACKAGE_EXT + "segment.";
    
    public final static String GROUP_EXT = PACKAGE_EXT + "group.";
    
    public final static Package PACKAGE_GROUP = UMSG_Z01.class.getPackage();
    
    private final static int NULL_NUMERIC = -1;
    
    private final static Map<String, Constructor<? extends HL7Data>> nameCache = new HashMap<String, Constructor<? extends HL7Data>>();
    
    private final static Map<Class<? extends HL7Data>, Constructor<? extends HL7Data>> classCache = new HashMap<Class<? extends HL7Data>, Constructor<? extends HL7Data>>();
    
    private final static Pattern PAT_BR = Pattern.compile("\\n|\\r\\n");
    
    static {
        final AntiCriterion tc = AntiCriterion.getInstance(new CharSequenceContainsCriterion("Test", false));
        final Class<?> hierarchy[] = new Class<?>[] { UMSG_Z01.class, PID.class, CE.class };
        final HL7Parser parser = new HL7Parser();
        final String versions[] = new String[] { OutputUtil.V2_3_1, OutputUtil.V2_4 };
        final MessageProperties mp = parser.getMessageProp();
        
        for (final Class<?> hc : hierarchy) {
            final Class<?>[] a = Util.getClassesForPackage(hc, tc);
            for (final String version : versions) { // Groups have different names in different versions
                mp.setVersion(version);
                for (final Class<?> ac : a) {
                    final Class<? extends HL7Data> c = Util.cast(ac);
                    if (Modifier.isAbstract(c.getModifiers())) {
                        continue;
                    }
                    final HL7Data data = getInstance(parser, c);
                    if (data != null) { // These packages also contain test classes which can be ignored
                        final Constructor<? extends HL7Data> cons = ReflectUtil.getConstructor(c, HL7Properties.class);
                        nameCache.put(data.getTagName(), cons);
                        classCache.put(cons.getDeclaringClass(), cons);
                    }
                }
            }
        }
        if (!nameCache.containsKey(PID.PID_XML)) {
            final StringBuilder b = new StringBuilder();
            for (final String n : nameCache.keySet()) {
                if (b.length() > 0) {
                    b.append(", ");
                }
                b.append(n);
            }
            log.error("Failed to load PID class; available HL7 types/segments/groups:");
            log.error(b);
        }
    }
    
    public final static String getNamespaceURI() {
        return NS_URI_V2;
    }
    
    public final static String getQualifiedName(final String localName) {
        return localName;
    }
    
    /**
     * Converts a String into a List
     * 
     * @param s the String
     * @return the List
     **/
    public final static List<String> stringToStringList(final String s) {
        return Util.length(s) == 0 ? null : Util.splitExactIntoList(s, '\n');
    }
    
    /**
     * Converts a String into a List
     * 
     * @param prop the HL7Properties
     * @param s the String
     * @return the List
     **/
    public final static List<CE> stringToCEList(final HL7Properties prop, final String s) {
        List<CE> list = null;
        String[] tokens = null;
        int i = 0;
        
        if ((s == null) || (s.length() == 0)) {
            return null;
        }
        
        tokens = Util.splitExact(s, '\n');
        list = new ArrayList<CE>(tokens.length);
        
        for (i = 0; i < tokens.length; i++) {
            list.add(new CE(prop, tokens[i]));
        }
        
        return list;
    }
    
    /**
     * Converts a String into an List
     * 
     * @param prop the HL7Properties
     * @param s the String
     * @return the List
     **/
    public final static List<CWE> stringToCWEList(final HL7Properties prop, final String s) {
        List<CWE> list = null;
        String[] tokens = null;
        int i = 0;
        
        if ((s == null) || (s.length() == 0)) {
            return null;
        }
        
        tokens = Util.splitExact(s, '\n');
        list = new ArrayList<CWE>(tokens.length);
        
        for (i = 0; i < tokens.length; i++) {
            list.add(CWE.createCWE(prop, tokens[i]));
        }
        
        return list;
    }
    
    // Might be a good place to use a ConverterIterable
    /**
     * Converts the given List of CEs to a List of CWEs
     * 
     * @param inList the List of CEs
     * @return the List of CWEs
     **/
    public final static List<CWE> CEListToCWEList(final List<CE> inList) {
        List<CWE> outList = null;
        int i = 0;
        final int size = Util.size(inList);
        
        if (size == 0) {
            return null;
        }
        
        outList = new ArrayList<CWE>(size);
        for (i = 0; i < size; i++) {
            outList.add(inList.get(i).toCWE());
        }
        
        return outList;
    }
    
    /**
     * Converts the given List of CWEs to a List of CWs
     * 
     * @param inList the List of CWEs
     * @return the List of CEs
     **/
    public final static List<CE> CWEListToCEList(final List<CWE> inList) {
        List<CE> outList = null;
        int i = 0;
        final int size = Util.size(inList);
        
        if (size == 0) {
            return null;
        }
        
        outList = new ArrayList<CE>(size);
        for (i = 0; i < size; i++) {
            outList.add(inList.get(i).getCE());
        }
        
        return outList;
    }
    
    /**
     * Retrieves the double value of a Double
     * 
     * @param d the Double
     * @return the double
     **/
    public final static double getDouble(final Number d) {
        return d == null ? NULL_NUMERIC : d.doubleValue();
    }
    
    /**
     * Retrieves the double value of an NM
     * 
     * @param nm the NM
     * @return the double
     **/
    public final static double getDouble(final NumericData nm) {
        return getDouble(AbstractNumericData.toDouble(nm)); // nm == null ? NULL_NUMERIC : nm.doubleValue(); // nm.doubleValue() can throw NullPointerException
    }
    
    /**
     * Retrieves the int value of a Number
     * 
     * @param d the Number
     * @return the int
     **/
    public final static int getInt(final Number d) {
        return d == null ? NULL_NUMERIC : d.intValue();
    }
    
    /**
     * Retrieves the Integer value of a Double
     * 
     * @param d the Double
     * @return the Integer
     **/
    public final static Integer getInteger(final Number d) {
        return d == null ? null : new Integer(d.intValue());
    }
    
    /**
     * Retrieves the Integer value of a NumericData
     * 
     * @param nd the NumericData
     * @return the Integer
     **/
    public final static Integer getInteger(final NumericData nd) {
        return nd == null ? null : getInteger(nd.toDouble());
    }
    
    /**
     * Wraps a double in a Double
     * 
     * @param d the double
     * @return the Double
     **/
    public final static Double getDouble(final double d) {
        return d < 0 ? null : new Double(d);
    }
    
    /**
     * Wraps a double in an NM
     * 
     * @param prop the HL7Properties
     * @param d the double
     * @return the NM
     **/
    public final static NM getNM(final HL7Properties prop, final double d) {
        return d < 0 ? null : new NM(prop, d);
    }
    
    /**
     * Retrieves the Double value of an Integer
     * 
     * @param i the Integer
     * @return the Double
     **/
    public final static Double getDouble(final Integer i) {
        return i == null ? null : new Double(i.intValue());
    }
    
    /**
     * Wraps an Integer in an NM
     * 
     * @param prop the HL7Properties
     * @param i the Integer
     * @return the NM
     **/
    public final static NM getNM(final HL7Properties prop, final Integer i) {
        return i == null ? null : new NM(prop, i.doubleValue());
    }
    
    /**
     * Parses the String into an int
     * 
     * @param s the String
     * @return the int
     **/
    public final static int parseInt(final String s) {
        return s == null ? NULL_NUMERIC : Integer.parseInt(s);
    }
    
    /**
     * Concatenates two Objects
     * 
     * @param o1 the first Object
     * @param o2 the second Object
     * @return the concatenated String
     **/
    public final static String concat(final Object o1, final Object o2) {
        return concat(o1, o2, null);
    }
    
    /**
     * Concatenates two Objects
     * 
     * @param o1 the first Object
     * @param o2 the second Object
     * @param delim the delimiter to put between the Objects
     * @return the concatenated String
     **/
    public final static String concat(final Object o1, final Object o2, final String delim) {
        return Util.concatWithDelim(toString(o1), toString(o2), Util.unNull(delim));
    }
    
    /**
     * Converts an Object to a display String
     * 
     * @param o the Object
     * @return the String
     **/
    public final static String toString(final Object o) {
        return o instanceof Double ? Util.doubleToString((Double) o) : o instanceof HL7Data ? ((HL7Data) o)
                .toDisplay() : Util.toString(o);
    }
    
    /**
     * Converts an Object to a piped String
     * 
     * @param o the Object
     * @return the String
     **/
    public final static String toPiped(final Object o) {
        return o instanceof HL7Data ? ((HL7Data) o).toPiped() : toString(o);
    }
    
    /**
     * Converts a List to a piped String
     * 
     * @param list the List
     * @return the String
     **/
    public final static String toPiped(final List<?> list) {
        if (Util.isEmpty(list)) {
            return null;
        }
        
        final StringBuilder sb = new StringBuilder();
        for (final Object o : list) {
            Util.appendSeparator(sb, '~').append(toPiped(o));
        }
        
        return sb.toString();
    }
    
    /**
     * Retrieves the HL7 version
     * 
     * @param prop the HL7Properties
     * @return the HL7 version
     **/
    public final static String getVersion(final HL7Properties prop) {
        if (prop == null) {
            return null;
        }
        final MessageProperties mp = prop.getMessageProp();
        return mp == null ? null : mp.getVersion();
    }
    
    /**
     * Retrieves whether the version is 2.4 or greater
     * 
     * @param prop the HL7Properties
     * @return whether the version is 2.4 or greater
     **/
    public final static boolean isVersion24Compatible(final HL7Properties prop) {
        return isVersion2xCompatible(prop, '4');
    }
    
    private final static boolean isVersion2xCompatible(final HL7Properties prop, final char x) {
        //return getVersion(prop).compareTo(OutputUtil.V2_4) >= 0;
        final String v = getVersion(prop);
        return v == null ? false : v.charAt(2) >= x; // Works for values like 2.1, 2.2, 2.3.1, 2.4, 2.5, 2.6
    }
    
    public final static boolean isVersion27Compatible(final HL7Properties prop) {
        return isVersion2xCompatible(prop, '7');
    }
    
    /**
     * Retrieves a Class of the desired type
     * 
     * @param valueType the desired type
     * @return the Class
     **/
    public final static Class<? extends HL7Data> getClass(final String valueType) {
        final Constructor<? extends HL7Data> cons = nameCache.get(valueType);
        return cons == null ? null : cons.getDeclaringClass();
    }
    
    /**
     * Retrieves the names of known types/segments/etc.
     * 
     * @return the names
     **/
    public final static Set<String> getTypes() {
        return nameCache.keySet();
    }
    
    /**
     * Retrieves an instance Object of the desired class
     * 
     * @param prop the HL7Properties
     * @param c the desired class
     * @param <T> the desired type
     * @return the instance Object
     **/
    @SuppressWarnings("unchecked")
    public final static <T extends HL7Data> T getInstance(final HL7Properties prop, final Class<T> c) {
        if (c == null) {
            return null;
        }
        try {
            final Constructor<?> cache = classCache.get(c);
            final Constructor<T> cons = cache == null ? ReflectUtil.getConstructor(c, HL7Properties.class) : (Constructor<T>) cache;
            return cons == null ? null : cons.newInstance(prop);
        } catch (final Exception e) {
            throw new RuntimeException("Could not instantiate " + c.getName(), e);
        }
    }
    
    /**
     * Retrieves an instance Object of the desired class
     * 
     * @param prop the HL7Properties
     * @param valueType the desired class
     * @return the instance Object
     **/
    public final static HL7Data getInstance(final HL7Properties prop, final String valueType) {
        final Constructor<? extends HL7Data> cons = nameCache.get(valueType);
        final HL7Data data;
        try {
            data = cons == null ? null : cons.newInstance(prop);
        } catch (final Exception e) {
            throw new RuntimeException("Could not instantiate " + valueType, e);
        }
        return data == null ? null : data.getTagName().equals(valueType) ? data : null;
    }
    
    /**
     * Determines if a String contains valid NM data: A number represented as a series of ASCII
     * numeric characters consisting of an optional leading sign (+ or -), the digits and an
     * optional decimal point. In the absence of a sign, the number is assumed to be positive. If
     * there is no decimal point the number is assumed to be an integer.
     * 
     * @param s the String
     * @return whether the String contains valid NM data
     **/
    public final static boolean isNumeric(final String s) {
        return indexOfNonNumeric(s) == -1;
    }
    
    /**
     * Retrieves first index of a character in the given String that violates the NM definition: A
     * number represented as a series of ASCII numeric characters consisting of an optional leading
     * sign (+ or -), the digits and an optional decimal point. In the absence of a sign, the number
     * is assumed to be positive. If there is no decimal point the number is assumed to be an
     * integer.
     * 
     * @param s the String
     * @return whether the String contains valid NM data
     **/
    public final static int indexOfNonNumeric(final String s) {
        int i = 0;
        final int len = Util.length(s);
        boolean hasDecimal = false, hasDigit = false;
        
        if (len == 0) {
            return -1;
        }
        
        char c = s.charAt(0);
        if ((c == '+') || (c == '-')) {
            i++;
        }
        
        for (; i < len; i++) {
            c = s.charAt(i);
            if (Util.isDigit(c)) {
                hasDigit = true;
                continue;
            }
            if (c == '.') {
                if (hasDecimal) {
                    return i;
                }
                hasDecimal = true;
                continue;
            }
            return i;
        }
        
        return hasDigit ? -1 : len - 1;
    }
    
    public final static String getNumeric(final String s) {
        if (Util.isEmpty(s)) {
            return null;
        }
        final int i = indexOfNonNumeric(s);
        return i == -1 ? s : s.substring(0, i);
    }
    
    public final static boolean isTimestamp(final String s) {
        final int size = Util.length(s);
        //        8  1214
        // YYYYMMDDHHMMSS
        if ((size != 8) && (size != 12) && (size != 14)) {
            return false;
        }
        if (!Util.isAllDigits(s)) {
            return false;
        }
        final int year = Util.parseInt(s, 0, 4);
        if ((year < 1980) || (year > 2040)) {
            return false;
        }
        final int month = Util.parseInt(s, 4, 6);
        if ((month < 1) || (month > 12)) {
            return false;
        }
        final int day = Util.parseInt(s, 6, 8);
        if ((day < 1) || (day > 31)) {
            return false;
        }
        if (size == 8) {
            return true;
        }
        final int hour = Util.parseInt(s, 8, 10);
        if ((hour < 0) || (hour > 24)) {
            return false;
        }
        final int minute = Util.parseInt(s, 10, 12);
        if ((minute < 0) || (minute > 59)) {
            return false;
        }
        if (size == 12) {
            return true;
        }
        final int second = Util.parseInt(s, 12, 14);
        if ((second < 0) || (second > 59)) {
            return false;
        }
        return true;
    }
    
    /**
     * Clears the HL7Data
     * 
     * @param data the HL7Data
     **/
    public final static void clear(final HL7Data data) {
        if (data != null) {
            data.clear();
        }
    }
    
    /**
     * Adds the tokens of the given String to the given List
     * 
     * @param list the List
     * @param s the String
     * @return the List
     **/
    public final static List<String> addTokens(List<String> list, final String s) {
        final String[] tokens = Util.splitExact(s, '\n', -1);
        final int size = Util.length(tokens);
        
        if (size == 0) {
            /*
            This should be right for NTE parsing; empty repetitions should be added to the list.
            It's probably right for anything else invoking this, if there is anything.
            */
            list = Util.add(list, "");
        } else {
            for (int i = 0; i < size; i++) {
                list = Util.addIfNotNull(list, tokens[i]);
            }
        }
        
        return list;
    }
    
    /**
     * Adds the tokens of the given String to the given List
     * 
     * @param list the List
     * @param s the String
     * @return the List
     **/
    public final static List<Object> addTokensToObjectList(List<Object> list, final String s) {
        //final String[] tokens = Util.splitExact(s, '\n', -1); // If s has \r\n breaks, this keeps \r in the Strings
        final String[] tokens = PAT_BR.split(s, -1);
        
        for (int i = 0; i < tokens.length; i++) {
            list = Util.addIfNotNull(list, tokens[i]);
        }
        
        return list;
    }
    
    public final static HL7Parser getParser(final HL7Properties prop) {
        return prop instanceof HL7Parser ? (HL7Parser) prop : null;
    }
}
