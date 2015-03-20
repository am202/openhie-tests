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
package org.regenstrief.hl7.convert;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.regenstrief.hl7.HL7Delimiters;
import org.regenstrief.hl7.segment.MSH;
import org.regenstrief.util.Util;

/**
 * <p>
 * Title: Escaper
 * </p>
 * <p>
 * Description: Escape/unescape methods for converters
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
public final class Escaper implements HL7Delimiters {
    
    // Special character constants
    public final static char DEFAULT_FIELD_SEPARATOR = '|';
    
    public final static char DEFAULT_COMPONENT_SEPARATOR = '^';
    
    public final static char DEFAULT_REPETITION_SEPARATOR = '~';
    
    public final static char DEFAULT_ESCAPE_CHARACTER = '\\';
    
    public final static char DEFAULT_SUBCOMPONENT_SEPARATOR = '&';
    
    public final static String DEFAULT_ENCODING_CHARACTERS = "" + DEFAULT_COMPONENT_SEPARATOR + DEFAULT_REPETITION_SEPARATOR
            + DEFAULT_ESCAPE_CHARACTER + DEFAULT_SUBCOMPONENT_SEPARATOR;
    
    // Escape sequence constants
    /*
    From 2.3.1 manual
    \H\ start highlighting
    \N\ normal text (end highlighting)
    \F\ field separator
    \S\ component separator
    \T\ subcomponent separator
    \R\ repetition separator
    \E\ escape character
    \Xdddd...\ hexadecimal data
    \Zdddd...\ locally defined escape sequence
    
    Formatted text
    .sp <number> End current output line and skip <number> vertical spaces.
        <number> is a positive integer or absent.
        If <number> is absent, skip one space.
        The horizontal character position remains unchanged.
        Note that for purposes of compatibility with previous versions of HL7, "^\.sp\" is equivalent to "\.br\."
    .br Begin new output line.
        Set the horizontal position to the current left margin and increment the vertical position by 1.
    .fi Begin word wrap or fill mode.
        This is the default state.
        It can be changed to a nowrap mode using the .nf command.
    .nf Begin no-wrap mode.
    .in <number> Indent <number> of spaces, where <number> is a positive or negative integer.
        This command cannot appear after the first printable character of a line.
    .ti <number> Temporarily indent <number> of spaces where number is a positive or negative integer.
        This command cannot appear after the first printable character of a line.
    */
    public final static char ESCAPE_FIELD_SEPARATOR = 'F';
    
    public final static char ESCAPE_COMPONENT_SEPARATOR = 'S';
    
    public final static char ESCAPE_SUBCOMPONENT_SEPARATOR = 'T';
    
    public final static char ESCAPE_REPETITION_SEPARATOR = 'R';
    
    public final static char ESCAPE_ESCAPE_CHARACTER = 'E';
    
    public final static char ESCAPE_HEX_CHARACTER = 'X';
    
    public final static String ESCAPE_STR_FIELD_SEPARATOR = Character.toString(ESCAPE_FIELD_SEPARATOR);
    
    public final static String ESCAPE_STR_COMPONENT_SEPARATOR = Character.toString(ESCAPE_COMPONENT_SEPARATOR);
    
    public final static String ESCAPE_STR_SUBCOMPONENT_SEPARATOR = Character.toString(ESCAPE_SUBCOMPONENT_SEPARATOR);
    
    public final static String ESCAPE_STR_REPETITION_SEPARATOR = Character.toString(ESCAPE_REPETITION_SEPARATOR);
    
    public final static String ESCAPE_STR_ESCAPE_CHARACTER = Character.toString(ESCAPE_ESCAPE_CHARACTER);
    
    public final static String BREAK = "\n";
    
    // Mode constants
    public final static int MODE_TEXT = 0; // Sequences like \F\ can be converted to |, but sequences like \H\ are not supported
    
    public final static int MODE_XML = 1; // Sequences like \H\ can be converted to <escape V="H"/>
    
    protected char fieldSeparator = DEFAULT_FIELD_SEPARATOR;
    
    protected char componentSeparator = DEFAULT_COMPONENT_SEPARATOR;
    
    protected char repetitionSeparator = DEFAULT_REPETITION_SEPARATOR;
    
    protected char escapeCharacter = DEFAULT_ESCAPE_CHARACTER;
    
    protected char subcomponentSeparator = DEFAULT_SUBCOMPONENT_SEPARATOR;
    
    protected int mode = MODE_TEXT;
    
    /**
     * Unescapes HL7 escape sequences
     * 
     * @param s the HL7 String
     * @param fieldSeparator the field separator
     * @param componentSeparator the component separator
     * @param repetitionSeparator the repetition separator
     * @param escapeCharacter the escape character
     * @param subcomponentSeparator the subcomponent separator
     * @return the unescaped String
     **/
    public String unescape(final String s, final char fieldSeparator, final char componentSeparator,
                           final char repetitionSeparator, final char escapeCharacter, final char subcomponentSeparator) {
        if (s == null) {
            return null;
        }
        int start = 0;
        char ca[] = null;
        StringBuilder sb = null;
        for (int i = s.indexOf(escapeCharacter); i != -1; i = s.indexOf(escapeCharacter, start)) {
            final int j = s.indexOf(escapeCharacter, i + 1);
            if (j > 0) {
                ca = ca == null ? s.toCharArray() : ca;
                if (sb == null) {
                    sb = new StringBuilder();
                }
                sb.append(ca, start, i - start);
                boolean processed = false;
                final char x = s.charAt(i + 1);
                final int diff = j - i;
                if (diff == 2) {
                    final char c;
                    switch (x) {
                        case ESCAPE_FIELD_SEPARATOR:
                            c = fieldSeparator;
                            break;
                        case ESCAPE_COMPONENT_SEPARATOR:
                            c = componentSeparator;
                            break;
                        case ESCAPE_SUBCOMPONENT_SEPARATOR:
                            c = subcomponentSeparator;
                            break;
                        case ESCAPE_REPETITION_SEPARATOR:
                            c = repetitionSeparator;
                            break;
                        case ESCAPE_ESCAPE_CHARACTER:
                            c = escapeCharacter;
                            break;
                        default :
                            c = ' ';
                            break;
                    }
                    if (c != ' ') {
                        sb.append(c);
                        processed = true;
                    }
                } else if ((x == '.') && (diff == 4)) {
                    if ((s.charAt(i + 2) == 'b') && (s.charAt(i + 3) == 'r')) {
                        sb.append(BREAK);
                        processed = true;
                    }
                } else if ((x == ESCAPE_HEX_CHARACTER) && (diff == 4)) {
                    sb.append((char) Util.fromHex(s, i + 2, i + 4));
                }
                if (!processed && (this.mode == MODE_XML)) {
                    sb.append("<escape xmlns=\"\" V=\"");
                    sb.append(ca, i + 1, j - start - 1);
                    sb.append("\"/>");
                }
            }
            start = j < 0 ? start + 1 : j + 1;
        }
        if (sb == null) {
            return s;
        } else {
            sb.append(ca, start, s.length() - start);
            return sb.toString();
        }
    }
    
    /**
     * Unescapes HL7 escape sequences
     * 
     * @param s the HL7 String
     * @param fieldSeparator the field separator
     * @param encodingCharacters the other encoding characters
     * @return the unescaped String
     **/
    public String unescape(final String s, final char fieldSeparator, final String encodingCharacters) {
        final char componentSeparator = encodingCharacters.charAt(0);
        final char repetitionSeparator = charAt(encodingCharacters, 1, DEFAULT_REPETITION_SEPARATOR);
        final char escapeCharacter = charAt(encodingCharacters, 2, DEFAULT_ESCAPE_CHARACTER);
        final char subcomponentSeparator = charAt(encodingCharacters, 3, DEFAULT_SUBCOMPONENT_SEPARATOR);
        
        return unescape(s, fieldSeparator, componentSeparator, repetitionSeparator, escapeCharacter, subcomponentSeparator);
    }
    
    private final static char charAt(final String s, final int i, final char def) {
        return s.length() > i ? s.charAt(i) : def;
    }
    
    /**
     * Unescapes HL7 escape sequences
     * 
     * @param s the HL7 String
     * @return the unescaped String
     **/
    public String unescape(final String s) {
        return unescape(s, this.fieldSeparator, this.componentSeparator, this.repetitionSeparator, this.escapeCharacter,
            this.subcomponentSeparator);
    }
    
    private final static Pattern PAT_V_ATTR = Pattern.compile(".*\\s*V\\s*=\\s*(?:\"(.*)\").*");
    
    /**
     * Escapes special HL7 characters
     * 
     * @param s the HL7 String
     * @param fieldSeparator the field separator
     * @param componentSeparator the component separator
     * @param repetitionSeparator the repetition separator
     * @param escapeCharacter the escape character
     * @param subcomponentSeparator the subcomponent separator
     * @return the escaped String
     **/
    public String escape(final String s, final char fieldSeparator, final char componentSeparator, final char repetitionSeparator,
                         final char escapeCharacter, final char subcomponentSeparator) {
        try {
            return escapeInt(null, s, fieldSeparator, componentSeparator, repetitionSeparator, escapeCharacter,
                subcomponentSeparator);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void escape(final Writer w, final String s, final char fieldSeparator, final char componentSeparator,
                       final char repetitionSeparator, final char escapeCharacter, final char subcomponentSeparator)
                                                                                                                    throws IOException {
        escapeInt(w, s, fieldSeparator, componentSeparator, repetitionSeparator, escapeCharacter, subcomponentSeparator);
    }
    
    private String escapeInt(Writer w, final String s, final char fieldSeparator, final char componentSeparator,
                             final char repetitionSeparator, final char escapeCharacter, final char subcomponentSeparator)
                                                                                                                       throws IOException {
        if (s == null) {
            return null;
        }
        int start = 0;
        final int size = s.length();
        final boolean needStr = w == null;
        for (int i = 0; i < size; i++) {
            final char c = s.charAt(i);
            final String str;
            if (c == escapeCharacter) {
                str = ESCAPE_STR_ESCAPE_CHARACTER;
            } else if (c == fieldSeparator) {
                str = ESCAPE_STR_FIELD_SEPARATOR;
            } else if (c == componentSeparator) {
                str = ESCAPE_STR_COMPONENT_SEPARATOR;
            } else if (c == subcomponentSeparator) {
                str = ESCAPE_STR_SUBCOMPONENT_SEPARATOR;
            } else if (c == repetitionSeparator) {
                str = ESCAPE_STR_REPETITION_SEPARATOR;
            } else if ((c == '\r') || (c == '\n')) {
                // Don't need separate escape sequences for consecutive hex sequences,
                // but this will create them.
                str = ESCAPE_HEX_CHARACTER + Util.toUppercaseHexString(c);
            } else {
                str = null;
            }
            if (str != null) {
                if (w == null) {
                    w = new StringWriter(size + 2);
                }
                w.write(s, start, i - start);
                w.write(escapeCharacter);
                w.write(str);
                w.write(escapeCharacter);
                start = i + 1;
            } else if ((this.mode == MODE_XML) && (c == '<')) {
                final int j = s.indexOf('>', i + 1);
                String sub = s.substring(i, j);
                final Matcher m = PAT_V_ATTR.matcher(sub);
                if (m.find()) {
                    sub = m.group(1);
                }
                if (w == null) {
                    w = new StringWriter(size + 2);
                }
                w.write(s, start, i - start);
                w.write(escapeCharacter);
                w.write(sub);
                w.write(escapeCharacter);
                start = j + 1;
                i = j;
            }
        }
        if (w == null) { // Want a String and no escaping was required
            return s;
        } else if (start < size) {
            w.write(s, start, size - start);
        }
        return needStr ? w.toString() : null; // Don't call toString if streaming
    }
    
    /**
     * Escapes special HL7 characters
     * 
     * @param s the HL7 String
     * @param fieldSeparatorCharacter the field separator
     * @param encodingCharacters the other encoding characters
     * @return the escaped String
     **/
    public String escape(final String s, final char fieldSeparatorCharacter, final String encodingCharacters) {
        return escape(s, fieldSeparatorCharacter, encodingCharacters.charAt(0), encodingCharacters.charAt(1),
            encodingCharacters.charAt(2), encodingCharacters.charAt(3));
    }
    
    /**
     * Escapes special HL7 characters
     * 
     * @param s the HL7 String
     * @return the escaped String
     **/
    public String escape(final String s) {
        return escape(s, this.fieldSeparator, this.componentSeparator, this.repetitionSeparator, this.escapeCharacter,
            this.subcomponentSeparator);
    }
    
    public void escape(final Writer w, final String s) throws IOException {
        escape(w, s, this.fieldSeparator, this.componentSeparator, this.repetitionSeparator, this.escapeCharacter,
            this.subcomponentSeparator);
    }
    
    /**
     * Retrieves the field separator
     * 
     * @return the char field separator
     **/
    @Override
    public final char getFieldSeparator() {
        return this.fieldSeparator;
    }
    
    /**
     * Retrieves the component separator
     * 
     * @return the char component separator
     **/
    @Override
    public final char getComponentSeparator() {
        return this.componentSeparator;
    }
    
    /**
     * Retrieves the subcomponent separator
     * 
     * @return the char subcomponent separator
     **/
    @Override
    public final char getSubcomponentSeparator() {
        return this.subcomponentSeparator;
    }
    
    /**
     * Retrieves the repetition separator
     * 
     * @return the char repetition separator
     **/
    @Override
    public final char getRepetitionSeparator() {
        return this.repetitionSeparator;
    }
    
    /**
     * Retrieves the escape character
     * 
     * @return the char escape character
     **/
    @Override
    public final char getEscapeCharacter() {
        return this.escapeCharacter;
    }
    
    /**
     * Retrieves the encoding characters
     * 
     * @return the String of encoding characters
     **/
    public String getEncodingCharacters() {
        return "" + this.componentSeparator + this.repetitionSeparator + this.escapeCharacter + this.subcomponentSeparator;
    }
    
    /**
     * Retrieves the mode
     * 
     * @return the mode
     **/
    public int getMode() {
        return this.mode;
    }
    
    /**
     * Modifies the field separator
     * 
     * @param fieldSeparator the new char field separator
     **/
    @Override
    public final void setFieldSeparator(final char fieldSeparator) {
        this.fieldSeparator = fieldSeparator;
    }
    
    /**
     * Modifies the component separator
     * 
     * @param componentSeparator the new char component separator
     **/
    @Override
    public final void setComponentSeparator(final char componentSeparator) {
        this.componentSeparator = componentSeparator;
    }
    
    /**
     * Modifies the subcomponent separator
     * 
     * @param subcomponentSeparator the new char subcomponent separator
     **/
    @Override
    public final void setSubcomponentSeparator(final char subcomponentSeparator) {
        this.subcomponentSeparator = subcomponentSeparator;
    }
    
    /**
     * Modifies the repetition separator
     * 
     * @param repetitionSeparator the new char repetition separator
     **/
    @Override
    public final void setRepetitionSeparator(final char repetitionSeparator) {
        this.repetitionSeparator = repetitionSeparator;
    }
    
    /**
     * Modifies the escape character
     * 
     * @param escapeCharacter the new char escape character
     **/
    @Override
    public final void setEscapeCharacter(final char escapeCharacter) {
        this.escapeCharacter = escapeCharacter;
    }
    
    /**
     * Assigns the encoding characters to be used by the converter
     * 
     * @param encodingCharacters the String of encoding characters
     **/
    public void setEncodingCharacters(final String encodingCharacters) {
        setEncodingCharacters(this, encodingCharacters);
    }
    
    public final static void setEncodingCharacters(final HL7Delimiters delims, final String encodingCharacters) {
        setEncodingCharacters(delims, encodingCharacters, 0, Util.length(encodingCharacters));
    }
    
    public final static void setEncodingCharacters(final HL7Delimiters d, final String s, final int off, final int len) {
        final int size = len - off;
        if (size > 0) {
            d.setComponentSeparator(s.charAt(off));
            if (size > 1) {
                d.setRepetitionSeparator(s.charAt(off + 1));
                if (size > 2) {
                    d.setEscapeCharacter(s.charAt(off + 2));
                    if (size > 3) {
                        d.setSubcomponentSeparator(s.charAt(off + 3));
                    }
                }
            }
        }
    }
    
    /**
     * Modifies the mode
     * 
     * @param mode the new mode
     **/
    public void setMode(final int mode) {
        this.mode = mode;
    }
    
    public final static boolean extractDelimiters(final HL7Delimiters delims, final String seg) {
        final int size = Util.length(seg);
        if ((size < 4) || !seg.startsWith(MSH.MSH_XML)) {
            return false;
        }
        final char field = seg.charAt(3);
        delims.setFieldSeparator(field);
        final int sep = seg.indexOf(field, 4);
        setEncodingCharacters(delims, seg, 4, sep < 0 ? size : sep);
        return true;
    }
    
    public final static char getFieldSeparator(final HL7Delimiters d) {
        return d == null ? DEFAULT_FIELD_SEPARATOR : d.getFieldSeparator();
    }
    
    public final static char getComponentSeparator(final HL7Delimiters d) {
        return d == null ? DEFAULT_COMPONENT_SEPARATOR : d.getComponentSeparator();
    }
    
    public final static char getRepetitionSeparator(final HL7Delimiters d) {
        return d == null ? DEFAULT_REPETITION_SEPARATOR : d.getRepetitionSeparator();
    }
    
    public final static char getEscapeCharacter(final HL7Delimiters d) {
        return d == null ? DEFAULT_ESCAPE_CHARACTER : d.getEscapeCharacter();
    }
    
    public final static char getSubcomponentSeparator(final HL7Delimiters d) {
        return d == null ? DEFAULT_SUBCOMPONENT_SEPARATOR : d.getSubcomponentSeparator();
    }
}
