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

import org.regenstrief.hl7.datatype.CN;
import org.regenstrief.hl7.datatype.CX;
import org.regenstrief.hl7.datatype.FN;

/**
 * ExpandedIdNameData
 */
public interface ExpandedIdNameData extends ExpandedIdData, IdNameData, ExpandedNameData {
    
    /**
     * Assigns the name from the given first, last, and middle name and suffix/prefix
     * 
     * @param familyName the last name
     * @param givenName the first name
     * @param middleInitialOrName the middle initial or name
     * @param suffix the suffix
     * @param prefix the prefix
     **/
    public void setName(final String familyName, final String givenName, final String middleInitialOrName,
                        final String suffix, final String prefix);
    
    /**
     * Retrieves the CN contained within this XCN
     * 
     * @return CN
     **/
    public CN getCN();
    
    /**
     * Converts this into a CX
     * 
     * @return this as a CX
     **/
    public CX getCX();
    
    /**
     * Determines if this is equal to the given ExpandedIdNameData
     * 
     * @param xcn the ExpandedIdNameData to compare to this
     * @return true if this equals xcn, false otherwise
     **/
    public boolean equals(final ExpandedIdNameData xcn);
    
    /**
     * Retrieves the family name
     * 
     * @return the family name
     **/
    public FN getFamilyName();
    
    /**
     * Modifies the family name
     * 
     * @param familyName the new famliy name
     **/
    public void setFamilyName(final FN familyName);
}
