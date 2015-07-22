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
import java.io.BufferedWriter;

import org.regenstrief.hl7.convert.Escaper;
import org.regenstrief.io.IoUtil;
import org.regenstrief.util.Util;

/**
 * CsvConverter
 */
public class CsvConverter {
    
    private final static String HL7_MRN =
            "MSH|^~\\&|TEST_HARNESS^^|TEST^^|OpenEMPI|OpenHIE|20141009103551||ADT^A01^ADT_A01|TEST-CR-05-10|P|2.3.1|1234||AL|NE\n" +
                    "EVN||20141009103551|||||OpenEMPI\n" +
                    "PID|||";
    
    private final static String HL7_NAME_LAST = "^^^TEST||";
    
    private final static String HL7_NAME_FIRST ="^";
    
    private final static String HL7_NAME_MIDDLE ="^";
    
    private final static String HL7_DOB ="^^^^L||";
    
    private final static String HL7_GENDER ="|";
    
    private final static String HL7_STREET ="|||";
    
    private final static String HL7_CITY ="^^";
    
    private final static String HL7_STATE ="^";
    
    private final static String HL7_ZIP ="^";
    
    private final static String HL7_AREA_CODE ="||^^^^^";
    
    private final static String HL7_PHONE ="^";
    
    private final static String HL7_SSN ="||||||";
    
    private final static String HL7_END = "\n" +
                    "PV1||I\n\n";
    
    // person_id,last,first,middle,dob,gender,street,city,state,zip,area_code,phone_number,ssn,global_person_id
    private static BufferedReader in = null;
    
    private static BufferedWriter out = null;
    
    private static String[] tokens = null;
    
    private static Escaper escaper = null;
    
    public final static void main(final String[] args) throws Exception {
        run(args[0]);
    }
    
    private final static void run(final String inName) throws Exception {
        escaper = new Escaper();
        try {
            in = Util.getBufferedReader(inName);
            final String outName = inName + ".hl7";
            out = Util.getBufferedWriter(Util.getFileWriter(outName));
            String line;
            while ((line = in.readLine()) != null) {
                if (line.isEmpty()) {
                    continue;
                }
                tokens = Util.splitCSV(line);
                write(HL7_MRN, 0);
                write(HL7_NAME_LAST, 1);
                write(HL7_NAME_FIRST, 2);
                write(HL7_NAME_MIDDLE, 3);
                write(HL7_DOB, 4);
                write(HL7_GENDER, 5);
                write(HL7_STREET, 6);
                write(HL7_CITY, 7);
                write(HL7_STATE, 8);
                write(HL7_ZIP, 9);
                write(HL7_AREA_CODE, 10);
                write(HL7_PHONE, 11);
                write(HL7_SSN, 12);
                out.write(HL7_END);
                out.flush();
            }
        } finally {
            IoUtil.close(out);
            IoUtil.close(in);
        }
    }
    
    private final static void write(final String prefix, final int index) throws Exception {
        out.write(prefix);
        out.write(escaper.escape(tokens[index]));
    }
}
