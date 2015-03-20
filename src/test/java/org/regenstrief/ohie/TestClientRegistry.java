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

import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.regenstrief.hl7.HL7DataTree;
import org.regenstrief.hl7.HL7Parser;
import org.regenstrief.hl7.util.HL7IO;
import org.regenstrief.util.MultiException;
import org.regenstrief.util.Util;

import junit.framework.TestCase;

/**
 * TestClientRegistry
 */
public class TestClientRegistry extends TestCase {
    
    private static final Log log = LogFactory.getLog(TestClientRegistry.class);
    
    private final static String HOST = Util.getProperty("org.regenstrief.ohie.cr.host", "cr2.test.ohie.org");
    
    private final static int PORT_PIX = Util.getPropertyInt("org.regenstrief.ohie.cr.port.pix", 2100);
    
    private final static int PORT_PDQ = Util.getPropertyInt("org.regenstrief.ohie.cr.port.pdq", 2100);
    
    private final static String LOCATION_INPUT = "org/regenstrief/ohie/integration/input";
    
    private final static String LOCATION_EXPECT = "org/regenstrief/ohie/integration/expect";
    
    private final static List<String> DISABLED = Util.splitExactIntoList(Util.getProperty("org.regenstrief.ohie.cr.disabledTests"), ';');
    
    public void testRegistry() throws Exception {
        List<Exception> failures = null;
        final String[] files = Util.listFiles(LOCATION_INPUT);
        Arrays.sort(files);
        for (final String file : files) {
            boolean skip = false;
            for (final String disabled : Util.unNull(DISABLED)) {
                if (file.endsWith(disabled)) {
                    skip = true;
                    break;
                }
            }
            if (skip) {
                continue;
            }
            try {
                runMessage(file);
            } catch (final Exception e) {
                failures = Util.add(failures, new Exception(file + " failed validation", e));
            }
        }
        if (Util.isValued(failures)) {
            throw MultiException.createException(failures);
        }
    }
    
    private void runMessage(final String file) throws Exception {
        final String loc = LOCATION_INPUT + "/" + file, in = Util.readFile(loc);
        log.info("Sending " + file + ":\n" + in + "\n");
        final String out = send(in);
        log.info("Received:\n" + out + "\n");
        final HL7Parser parser = HL7Parser.createLaxParser();
        final String exLoc = LOCATION_EXPECT + "/" + Util.getProcessedFileName(file, "expected");
        parser.runFromLocation(exLoc);
        final HL7DataTree exTree = parser.getTree();
        parser.runFromString(out);
        final HL7DataTree acTree = parser.getTree();
        new MessageValidator().validateMessage(exTree, acTree);
    }
    
    private String send(final String in) throws Exception {
        return HL7IO.send_rcv_hl7_msg(HOST, getPort(in), 0, HL7IO.convert_lf_to_cr(in));
    }
    
    private int getPort(final String msg) {
        return Util.contains(msg, "QBP^Q22") ? PORT_PDQ : PORT_PIX;
    }
}
