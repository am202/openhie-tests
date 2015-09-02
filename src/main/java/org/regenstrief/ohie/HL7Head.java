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

import java.io.BufferedReader;
import java.io.PrintStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.regenstrief.io.IoUtil;
import org.regenstrief.util.Util;

/**
 * HL7Head
 */
public class HL7Head {
    
    private static final Log log = LogFactory.getLog(HL7Head.class);
    
    public final static void main(final String[] args) throws Exception {
        run(args);
    }
    
    private final static void run(final String[] args) throws Exception {
        final String inName = args[0];
        final int n = Integer.parseInt(args[1]);
        final String label;
        if ((n % 1000000) == 0) {
            label = (n / 1000000) + "M";
        } else if ((n % 1000) == 0) {
            label = (n / 1000) + "K";
        } else {
            label = String.valueOf(n);
        }
        final String outName = inName.substring(0, inName.length() - 3) + label + ".hl7";
        BufferedReader in = null;
        PrintStream out = null;
        try {
            in = Util.getBufferedReader(inName);
            out = Util.getPrintStream(outName);
            String line;
            int count = 0;
            log.info("Starting");
            while ((line = in.readLine()) != null) {
                if (line.startsWith("MSH")) {
                    if ((count > 0) && ((count % 10000) == 0)) {
                        log.info("Copied " + count + " messages");
                    }
                    if (count >= n) {
                        break;
                    }
                    count++;
                }
                out.println(line);
            }
            log.info("Finished after " + count + " messages");
        } finally {
            IoUtil.close(out);
            IoUtil.close(in);
        }
    }
}
