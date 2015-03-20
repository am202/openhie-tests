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
package org.regenstrief.hl7;

/**
 * HL7Delimiters
 */
public interface HL7Delimiters {
    
    /**
     * Retrieves the field separator
     * 
     * @return the char field separator
     **/
    public char getFieldSeparator();
    
    /**
     * Retrieves the component separator
     * 
     * @return the char component separator
     **/
    public char getComponentSeparator();
    
    /**
     * Retrieves the subcomponent separator
     * 
     * @return the char subcomponent separator
     **/
    public char getSubcomponentSeparator();
    
    /**
     * Retrieves the repetition separator
     * 
     * @return the char repetition separator
     **/
    public char getRepetitionSeparator();
    
    /**
     * Retrieves the escape character
     * 
     * @return the char escape character
     **/
    public char getEscapeCharacter();
    
    /**
     * Modifies the field separator
     * 
     * @param fieldSeparator the new char field separator
     **/
    public void setFieldSeparator(final char fieldSeparator);
    
    /**
     * Modifies the component separator
     * 
     * @param componentSeparator the new char component separator
     **/
    public void setComponentSeparator(final char componentSeparator);
    
    /**
     * Modifies the subcomponent separator
     * 
     * @param subcomponentSeparator the new char subcomponent separator
     **/
    public void setSubcomponentSeparator(final char subcomponentSeparator);
    
    /**
     * Modifies the repetition separator
     * 
     * @param repetitionSeparator the new char repetition separator
     **/
    public void setRepetitionSeparator(final char repetitionSeparator);
    
    /**
     * Modifies the escape character
     * 
     * @param escapeCharacter the new char escape character
     **/
    public void setEscapeCharacter(final char escapeCharacter);
}
