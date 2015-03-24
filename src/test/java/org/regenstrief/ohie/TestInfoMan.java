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
package org.regenstrief.ohie;

import org.regenstrief.ohie.InfoMan.CodedType;
import org.regenstrief.ohie.InfoMan.FacilityArgs;
import org.regenstrief.ohie.InfoMan.ProviderArgs;
import org.regenstrief.util.Util;
import org.regenstrief.util.XMLUtil;
import org.w3c.dom.NodeList;

import junit.framework.TestCase;

/**
 * TestInfoMan
 */
public class TestInfoMan extends TestCase {
    
    private final static String EXPECTED_FACILITY_NAME = "Dschubba Arkab Arneb Giedi";
    
    private final static String EXPECTED_PROVIDER_NAME = "Japhlet";
    
    private final static String MISSING_FACILITY_NAME = "EIEIO Lab";
    
    private final static String MISSING_PROVIDER_NAME = "Ohieq";
    
    private final static String EXPECTED_FACILITY_ID = "urn:uuid:103C8A4A-EA59-39F7-8DE8-CE232AF7A329";
    
    private final static String EXPECTED_ORGANIZATION_ID = "urn:ihris.org:ihris-manage-fauxpays:organizations:health_workers";
    
    private final static String EXPECTED_TYPE = "158";
    
    private static InfoMan infoMan = new InfoMan();
    
    private static FacilityArgs facArgs = null;
    
    private static ProviderArgs provArgs = null;
    
    public void testInfoMan() throws Exception {
        InfoMan.setDefaultMax(Integer.valueOf(1));
        
        // 2, Start a refresh
        //TODO
        
        // 3, Pick a facility that should exist by name
        facArgs = new FacilityArgs();
        facArgs.setPrimaryName(EXPECTED_FACILITY_NAME);
        assertFacility(EXPECTED_FACILITY_NAME, "primaryName");
        
        // 6, Pick an existing provider by name
        provArgs = new ProviderArgs();
        provArgs.setCommonName(EXPECTED_PROVIDER_NAME);
        assertProvider(EXPECTED_PROVIDER_NAME, "commonName");
        
        // 7, Wait for manual update
        //TODO
        
        // 8, Start a refresh
        //TODO
        
        // 9, Pick a facility after an update
        facArgs = new FacilityArgs();
        facArgs.setPrimaryName(EXPECTED_FACILITY_NAME);
        assertFacility(EXPECTED_FACILITY_NAME, "primaryName");
        
        // 10/11, Pick a provider by name after an update
        provArgs = new ProviderArgs();
        provArgs.setCommonName(EXPECTED_PROVIDER_NAME);
        assertProvider(EXPECTED_PROVIDER_NAME, "commonName");
        
        // 12, Test a facility name that doesn't exist
        facArgs = new FacilityArgs();
        facArgs.setPrimaryName(MISSING_FACILITY_NAME);
        assertFacility(null, null);
        
        // 13, Non-existant provider name
        provArgs = new ProviderArgs();
        provArgs.setCommonName(MISSING_PROVIDER_NAME);
        assertProvider(null, null);
        
        // 14, Pick a provider by facility
        provArgs = new ProviderArgs();
        provArgs.setFacility(EXPECTED_FACILITY_ID);
        assertProvider(EXPECTED_FACILITY_ID, "facility/@entityID");
        
        // 16, Pick a provider by organization
        provArgs = new ProviderArgs();
        provArgs.setOrganization(EXPECTED_ORGANIZATION_ID);
        assertProvider(EXPECTED_ORGANIZATION_ID, "organization/@entityID");
        
        // 17, Query for type of worker
        provArgs = new ProviderArgs();
        provArgs.setType(new CodedType(EXPECTED_TYPE, "2.25.5568431708365723170808281814005601643866817"));
        assertProvider(EXPECTED_TYPE, "codedType/@code");
    }
    
    private static void assertFacility(final String ex, final String tag) throws Exception {
        assertTag(ex, infoMan.getFacilities(facArgs), tag);
    }
    
    private static void assertProvider(final String ex, final String tag) throws Exception {
        assertTag(ex, infoMan.getProviders(provArgs), tag);
    }
    
    private static void assertTag(final String ex, final NodeList list, final String tag) {
        if (tag == null) {
            assertEquals(0, XMLUtil.size(list));
            return;
        }
        final String ac = XMLUtil.searchText(list.item(0), tag);
        assertTrue("Could not find " + ex + " in " + ac, Util.contains(Util.toUpperCase(ac), Util.toUpperCase(ex)));
    }
}
