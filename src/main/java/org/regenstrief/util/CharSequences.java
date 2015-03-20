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

import org.regenstrief.util.reflect.ReflectUtil;

/**
 * CharSequences
 */
public final class CharSequences {
    
    /**
     * Returns a sub sequence of the same type as the given CharSequence
     * 
     * @param src the source CharSequence
     * @param start the start index
     * @param <T> the CharSequence type
     * @return the sub sequence
     */
    public final static <T extends CharSequence> T subSequence(final T src, final int start) {
        return subSequence(src, start, Util.length(src));
    }
    
    /**
     * Returns a sub sequence of the same type as the given CharSequence
     * 
     * @param src the source CharSequence
     * @param start the start index
     * @param end the end index
     * @param <T> the CharSequence type
     * @return the sub sequence
     */
    public final static <T extends CharSequence> T subSequence(final T src, final int start, final int end) {
        if (start == 0 && end == src.length()) {
            return src;
        }
        final Object r;
        if (src instanceof String) {
            r = ((String) src).substring(start, end);
        } else if (src instanceof StringBuffer) {
            final StringBuffer sb = (StringBuffer) src;
            sb.delete(0, start);
            sb.setLength(end - start);
            r = sb;
        } else if (src instanceof StringBuilder) {
            final StringBuilder sb = (StringBuilder) src;
            sb.delete(0, start);
            sb.setLength(end - start);
            r = sb;
        //} else if (src instanceof Segment) {
        //} else if (src instanceof CharBuffer) {
        } else {
            final Object s = src.subSequence(start, end);
            if (s != null && !src.getClass().isAssignableFrom(s.getClass())) {
                r = wrap(src.getClass(), s.toString());
            } else {
                r = s;
            }
        }
        return Util.cast(r);
    }
    
    /**
     * Trims a CharSequence, returning null if it is empty
     * 
     * @param src the CharSequence to trim
     * @param <T> the CharSequence type
     * @return the trimmed CharSequence
     **/
    public final static <T extends CharSequence> T trim(final T src) {
        final int size = Util.length(src);
        if (size == 0) {
            return null;
        }
        int start = 0, end = size;
        for (; (start < size) && Character.isWhitespace(src.charAt(start)); start++); // Just increment
        for (int i = size - 1; i > start; i--) {
            final char c = src.charAt(i);
            if (!Character.isWhitespace(c)) {
                break;
            }
            end = i;
        }
        if (start == end) {
            return null;
        }
        return subSequence(src, start, end); // subSequence is smart if start-end is whole range
    }
    
    /**
     * Retrieves the first n characters of a CharSequence, or all of it if its length is less than n
     * 
     * @param src the CharSequence to truncate
     * @param n the desired number of characters
     * @param <T> the CharSequence type
     * @return the truncated CharSequence
     **/
    public final static <T extends CharSequence> T truncate(final T src, final int n) {
        return Util.length(src) <= n ? src : subSequence(src, 0, n);
    }
    
    /**
     * Determines if two CharSequences are equal, ignoring case (true if both are null)
     * 
     * @param s1 CharSequence 1
     * @param s2 CharSequence 2
     * @return whether s1 equals s2, ignoring case
     **/
    public final static boolean equalsIgnoreCase(final CharSequence s1, final CharSequence s2) {
        return equals(s1, s2, 0, 0, -1, true);
    }
    
    private final static boolean equals(final CharSequence s1, final CharSequence s2, final int o1, final int o2, final int len, final boolean ignCase) {
        if (s1 == null) {
            return (o1 == 0) && (o2 == 0) && (s2 == null);
        } else if (s2 == null) {
            return false;
        }
        final int size1 = Util.length(s1) - o1, size2 = Util.length(s2) - o2;
        final int lim;
        if (len == -1) {
            if (size1 != size2) {
                return false;
            }
            lim = size1;
        } else {
            if ((size1 < len) || (size2 < len)) {
                return false;
            }
            lim = len;
        }
        for (int i = 0; i < lim; i++) {
            if (!equalsIgnoreCase(s1.charAt(i + o1), s2.charAt(i + o2))) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Determines if two characters are equal, ignoring case
     * 
     * @param c1 char 1
     * @param c2 char 2
     * @return whether c1 equals c2, ignoring case
     **/
    public final static boolean equalsIgnoreCase(final char c1, final char c2) {
        return c1 == c2 ? true : Character.toUpperCase(c1) == Character.toUpperCase(c2);
    }
    
    private final static boolean equals(final char c1, final char c2, final boolean ignCase) {
        return ignCase ? equalsIgnoreCase(c1, c2) : c1 == c2;
    }
    
    /**
     * Retrieves the index within s of the 1st occurrence of c
     * 
     * @param s the CharSequence
     * @param c the char
     * @return the index (or -1 if the character was not found)
     */
    public final static int indexOf(final CharSequence s, final char c) {
        return indexOf(s, c, 0);
    }
    
    /**
     * Retrieves the index within s of the 1st occurrence of c, starting the search at fromIndex
     * 
     * @param s the CharSequence
     * @param c the char
     * @param fromIndex the index from which to start searching
     * @return the index (or -1 if the character was not found)
     */
    public final static int indexOf(final CharSequence s, final char c, final int fromIndex) {
        return indexOf(s, c, fromIndex, false);
    }
    
    /**
     * Retrieves the index within s of the 1st occurrence of c, ignoring their cases
     * 
     * @param s the CharSequence
     * @param c the char
     * @return the index
     **/
    public final static int indexOfIgnoreCase(final CharSequence s, final char c) {
        return indexOfIgnoreCase(s, c, 0);
    }
    
    /**
     * Retrieves the index within s of the 1st occurrence of c, ignoring their cases
     * 
     * @param s the CharSequence
     * @param c the char
     * @param fromIndex the index from which to start searching
     * @return the index
     **/
    public final static int indexOfIgnoreCase(final CharSequence s, final char c, final int fromIndex) {
        return indexOf(s, c, fromIndex, true);
    }
    
    private final static int indexOf(final CharSequence s, final char c, final int fromIndex, final boolean ignCase) {
        final int size = Util.length(s);
        for (int i = Math.max(0, fromIndex); i < size; i++) {
            final char ci = s.charAt(i);
            if (equals(ci, c, ignCase)) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Retrieves the index within s of the 1st occurrence of sub, ignoring their cases
     * 
     * @param s the CharSequence
     * @param sub the sub-CharSequence
     * @return the index
     **/
    public final static int indexOfIgnoreCase(final CharSequence s, final CharSequence sub) {
        return indexOfIgnoreCase(s, sub, 0);
    }
    
    /**
     * Retrieves the index within s of the 1st occurrence of sub, starting the search at fromIndex, ignoring their cases
     * 
     * @param s the CharSequence
     * @param sub the char
     * @param fromIndex the index from which to start searching
     * @return the index
     **/
    public final static int indexOfIgnoreCase(final CharSequence s, final CharSequence sub, final int fromIndex) {
        return indexOf(s, sub, fromIndex, true);
    }
    
    /**
     * Retrieves the index within s of the 1st occurrence of sub
     * 
     * @param s the CharSequence
     * @param sub the sub-CharSequence
     * @param ignCase whether to ignore cases
     * @return the index
     **/
    public final static int indexOf(final CharSequence s, final CharSequence sub, final boolean ignCase) {
        return indexOf(s, sub, 0, ignCase);
    }
    
    /**
     * Retrieves the index within s of the 1st occurrence of sub, starting the search at fromIndex
     * 
     * @param s the CharSequence
     * @param sub the sub-CharSequence
     * @param fromIndex the index from which to start searching
     * @param ignCase whether to ignore cases
     * @return the index
     **/
    public final static int indexOf(final CharSequence s, final CharSequence sub, final int fromIndex, final boolean ignCase) {
        final int size = Util.length(s), sizeSub = Util.length(sub);
        for (int i = Math.max(0, fromIndex); i < size; i++) {
            if (equals(s, sub, i, 0, sizeSub, ignCase)) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Retrieves the index within s of the last occurrence of c
     * 
     * @param s the CharSequence
     * @param c the char
     * @return the index (or -1 if the character was not found)
     */
    public final static int lastIndexOf(final CharSequence s, final char c) {
        return lastIndexOf(s, c, Util.length(s) - 1);
    }
    
    /**
     * Retrieves the index within s of the last occurrence of c, searching backward from fromIndex
     * 
     * @param s the CharSequence
     * @param c the char
     * @param fromIndex the index from which to start searching backward
     * @return the index (or -1 if the character was not found)
     */
    public final static int lastIndexOf(final CharSequence s, final char c, final int fromIndex) {
        for (int i = Math.min(fromIndex, Util.length(s) - 1); i >= 0; i--) {
            if (s.charAt(i) == c) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Retrieves whether the given sequence contains the sub-sequence, ignore their cases
     * 
     * @param s the CharSequence
     * @param sub the sub-CharSequence
     * @return whether it was contained
     */
    public final static boolean containsIgnoreCase(final CharSequence s, final CharSequence sub) {
        return indexOfIgnoreCase(s, sub) >= 0;
    }
    
    /**
     * Retrieves whether the given sequence contains the character, ignore their cases
     * 
     * @param s the CharSequence
     * @param c the char
     * @return whether it was contained
     */
    public final static boolean containsIgnoreCase(final CharSequence s, final char c) {
        return indexOfIgnoreCase(s, c) >= 0;
    }
    
    /**
     * Wraps the source String in the given CharSequence Class
     * 
     * @param targetClass the CharSequence Class
     * @param src the source String
     * @param <T> the CharSequence type
     * @return the CharSequence wrapper
     */
    public final static <T extends CharSequence> T wrap(final Class<T> targetClass, final String src) {
        if (src == null) {
            return null;
        } else if (targetClass.equals(String.class)) {
            return Util.cast(src);
        }
        return ReflectUtil.newInstance(ReflectUtil.getConstructor(targetClass, String.class), src);
    }
    
    /**
     * Converts the given Object to a CharSequence
     * 
     * @param o the Object
     * @return the CharSequence
     */
    public final static CharSequence toCharSequence(final Object o) {
        return o instanceof CharSequence ? ((CharSequence) o) : Util.toString(o);
    }
}
