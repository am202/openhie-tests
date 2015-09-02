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
 * MrnModifier
 */
public class MrnModifier {
    
    private static final Log log = LogFactory.getLog(MrnModifier.class);
    
    public final static void main(final String[] args) throws Exception {
        run(args);
    }
    
    private final static void run(final String[] args) throws Exception {
        final String inName = args[0];
        final String outName = inName.substring(0, inName.length() - 3) + "mod.hl7";
        BufferedReader in = null;
        PrintStream out = null;
        try {
            in = Util.getBufferedReader(inName);
            out = Util.getPrintStream(outName);
            String line;
            int count = 0;
            log.info("Starting");
            while ((line = in.readLine()) != null) {
                if (line.startsWith("PID")) {
                    count++;
                    final int start = Util.nthIndexOf(line, "|", 2) + 1;
                    line = line.substring(0, start) + "MOD" + line.substring(start);
                    if ((count % 10000) == 0) {
                        log.info("Modified " + count + " messages");
                    }
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
