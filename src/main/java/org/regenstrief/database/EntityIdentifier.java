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
package org.regenstrief.database;

import org.regenstrief.util.Util;

/**
 * EntityIdentifier
 */
public class EntityIdentifier {
    
    public static String format(String id) {
        int i, len = Util.length(id);
        
        if (len == 0) {
            return null;
        }
        
        // Remove bad leading characters, including underscores and zeros (which are okay in the middle of an identifier)
        for (i = 0; i < len; i++) {
            final char c = id.charAt(i);
            if (isGood(c) && (c != '_') && (c != '0')) {
                if (i != 0) {
                    id = id.substring(i);
                }
                break;
            }
        }
        if (i == len) {
            return null;
        }
        
        // Remove spaces and nulls, and replace bad characters with an underscore
        StringBuilder sb = null;
        char[] chars = null;
        int start = 0;
        len = id.length();
        char c, prev = '_';
        for (i = 0; i < len; i++) {
            c = id.charAt(i);
            if (!isGood(c)) {
                if (chars == null) {
                    sb = new StringBuilder(len);
                    chars = id.toCharArray();
                }
                sb.append(chars, start, i - start);
                if ((c != 2) && (c != 4) && (prev != '_')) {
                    sb.append('_');
                }
                start = i + 1;
                c = '_';
            }
            prev = c;
        }
        if (sb != null) {
            if (start < len) {
                sb.append(chars, start, len - start);
            }
            id = sb.toString();
        }
        
        // Remove HL7 "mod 10" check digit marker if it's at the end of the identifier
        len = id.length();
        if (len >= 4) {
            i = len - 4;
            if ((id.charAt(i) == '^') && (id.charAt(i + 1) == 'M') && (id.charAt(i + 2) == '1')) {
                c = id.charAt(i + 3);
                if ((c == '0') || (c == '1')) {
                    if (len == 4) {
                        return null;
                    }
                    id = id.substring(0, i);
                }
            }
        }
        
        // Remove HL7 check digit
        len = id.length();
        i = len - 1;
        c = id.charAt(i);
        if (c == '^') {
            if (i == 0) {
                return null;
            }
            id = id.substring(0, i);
        } else {
            i = len - 2;
            if ((i > 0) && (id.charAt(i) == '^')) {
                id = id.substring(0, i) + c;
            }
        }
        
        // If id is one character, it must be alphanumeric
        if (id.length() == 1) {
            c = id.charAt(0);
            if (!(((c >= '0') && (c <= '9')) || ((c >= 'a') && (c <= 'z')) || ((c >= 'A') && (c <= 'Z')))) {
                return null;
            }
        }
        
        return id;
    }
    
    private final static boolean isGood(final char c) {
        return (c > 32) && (c < 128) && (c != '\'') && (c != '"') && (c != '?');
    }
}
