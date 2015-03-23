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

import junit.framework.TestCase;

/**
 * TestInfoMan
 */
public class TestInfoMan extends TestCase {
    
    public void testInfoMan() throws Exception {
        InfoMan.setDefaultMax(Integer.valueOf(1));
        final InfoMan infoMan = new InfoMan();
        FacilityArgs facArgs = null;
        ProviderArgs provArgs = null;
        
        // 2, Start a refresh
        //TODO
        
        // 3, Pick a facility that should exist by name
        facArgs = new FacilityArgs();
        facArgs.setPrimaryName("Dschubba Arkab Arneb Giedi");
        infoMan.getFacilities(facArgs);
        
        // 6, Pick an existing provider by name
        provArgs = new ProviderArgs();
        provArgs.setCommonName("Japhlet");
        infoMan.getProviders(provArgs);
        
        // 7, Wait for manual update
        //TODO
        
        // 8, Start a refresh
        //TODO
        
        // 9, Pick a facility after an update
        facArgs = new FacilityArgs();
        facArgs.setPrimaryName("Dschubba Arkab Arneb Giedi");
        infoMan.getFacilities(facArgs);
        
        // 10/11, Pick a provider by name after an update
        provArgs = new ProviderArgs();
        provArgs.setCommonName("Japhlet");
        infoMan.getProviders(provArgs);
        
        // 12, Test a facility name that doesn't exist
        facArgs = new FacilityArgs();
        facArgs.setPrimaryName("EIEIO Lab");
        infoMan.getFacilities(facArgs);
        
        // 13, Non-existant provider name
        provArgs = new ProviderArgs();
        provArgs.setCommonName("Ohieq");
        infoMan.getProviders(provArgs);
        
        // 14, Pick a provider by facility
        provArgs = new ProviderArgs();
        provArgs.setFacility("urn:uuid:103C8A4A-EA59-39F7-8DE8-CE232AF7A329");
        infoMan.getProviders(provArgs);
        
        // 16, Pick a provider by organization
        provArgs = new ProviderArgs();
        provArgs.setOrganization("urn:ihris.org:ihris-manage-fauxpays:organizations:health_workers");
        infoMan.getProviders(provArgs);
        
        // 17, Query for type of worker
        provArgs = new ProviderArgs();
        provArgs.setType(new CodedType("158", "2.25.5568431708365723170808281814005601643866817"));
        infoMan.getProviders(provArgs);
        
        //TODO assertions
    }
}
