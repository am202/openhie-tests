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
import org.regenstrief.hl7.datatype.NM;
import org.regenstrief.hl7.datatype.XTN;
import org.regenstrief.io.IoUtil;
import org.regenstrief.util.Dates;
import org.regenstrief.util.StopWatch;
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
    
    private final static String HL7_MOTHERS_MAIDEN_NAME = "^^^^L|";
    
    private final static String HL7_DOB ="|";
    
    private final static String HL7_GENDER ="|";
    
    private final static String HL7_STREET ="|||";
    
    private final static String HL7_CITY ="^^";
    
    private final static String HL7_STATE ="^";
    
    private final static String HL7_ZIP ="^";
    
    private final static String HL7_FULL_PHONE ="||";
    
    private final static String HL7_AREA_CODE ="^^^^^";
    
    private final static String HL7_PHONE ="^";
    
    private final static String HL7_SSN ="||||||";
    
    private final static String HL7_END = "\n" +
                    "PV1||I\n\n";
    
    private static int IND_MRN = -1;
    
    private static int IND_NAME_LAST = -1;
    
    private static int IND_NAME_FIRST = -1;
    
    private static int IND_NAME_MIDDLE = -1;
    
    private static int IND_MOTHERS_MAIDEN_NAME = -1;
    
    private static int IND_DOB = -1;
    
    private static int IND_GENDER = -1;
    
    private static int IND_STREET = -1;
    
    private static int IND_CITY = -1;
    
    private static int IND_STATE = -1;
    
    private static int IND_ZIP = -1;
    
    private static int IND_FULL_PHONE = -1;
    
    private static int IND_AREA_CODE = -1;
    
    private static int IND_PHONE = -1;
    
    private static int IND_SSN = -1;
    
    private static boolean skipFirst = false;
    
    private static BufferedReader in = null;
    
    private static BufferedWriter out = null;
    
    private static String[] tokens = null;
    
    private static Escaper escaper = null;
    
    public final static void main(final String[] args) throws Exception {
        run(args);
    }
    
    private final static void run(final String[] args) throws Exception {
        final String inName = args[0];
        final String format = args[1];
        if ("1".equals(format)) {
            initIndices1();
        } else if ("2".equals(format)) {
            initIndices2();
        } else {
            throw new IllegalArgumentException("Unrecognized format: " + format);
        }
        escaper = new Escaper();
        try {
            in = Util.getBufferedReader(inName);
            final String outName = inName + ".hl7";
            out = Util.getBufferedWriter(Util.getFileWriter(outName));
            if (skipFirst) {
                in.readLine();
            }
            String line;
            while ((line = in.readLine()) != null) {
                if (line.isEmpty()) {
                    continue;
                }
                tokens = Util.splitCSV(line);
                write(HL7_MRN, IND_MRN);
                write(HL7_NAME_LAST, IND_NAME_LAST);
                write(HL7_NAME_FIRST, IND_NAME_FIRST);
                write(HL7_NAME_MIDDLE, IND_NAME_MIDDLE);
                write(HL7_MOTHERS_MAIDEN_NAME, IND_MOTHERS_MAIDEN_NAME);
                write(HL7_DOB, formatDate(get(IND_DOB)));
                write(HL7_GENDER, formatGender(get(IND_GENDER)));
                write(HL7_STREET, IND_STREET);
                write(HL7_CITY, IND_CITY);
                write(HL7_STATE, IND_STATE);
                write(HL7_ZIP, IND_ZIP);
                final XTN xtn = getXtn();
                write(HL7_FULL_PHONE, xtn.getNumber());
                write(HL7_AREA_CODE, NM.getValue(xtn.getAreaCityCode()));
                write(HL7_PHONE, NM.getValue(xtn.getPhoneNumber()));
                write(HL7_SSN, IND_SSN);
                out.write(HL7_END);
                out.flush();
            }
        } finally {
            IoUtil.close(out);
            IoUtil.close(in);
        }
    }
    
    private final static String get(final int index) {
        return (index < 0) ? null : Util.get(tokens, index);
    }
    
    private final static void write(final String prefix, final int index) throws Exception {
        write(prefix, get(index));
    }
    
    private final static void write(final String prefix, final String value) throws Exception {
        out.write(prefix);
        final String escaped = escaper.escape(value);
        if (escaped != null) {
            out.write(escaped);
        }
    }
    
    private final static String formatGender(final String gender) {
        return Util.isValued(gender) ? gender.substring(0, 1).toUpperCase() : null;
    }
    
    private final static String formatDate(final String date) {
        if (Util.isAllDigits(date) && (date.startsWith("19") || date.startsWith("20"))) {
            return date;
        }
        return StopWatch.toHL7String(Dates.parseCalendar(date));
    }
    
    private final static XTN getXtn() {
        final XTN xtn = new XTN(null);
        String fullPhone = get(IND_FULL_PHONE), areaCode = get(IND_AREA_CODE), phone = get(IND_PHONE);
        final int fullSize = Util.length(fullPhone);
        if ((fullSize >= 7) && Util.isEmpty(phone)) {
            final String identifier = fullPhone.substring(fullSize - 4);
            final int end = fullSize - ((fullPhone.charAt(fullSize - 5) == '-') ? 5 : 4); 
            final String exchange = fullPhone.substring(end - 3, end);
            phone = identifier + exchange;
            if (fullSize >= 10) {
                final char char0 = fullPhone.charAt(0);
                final int start;
                if (char0 == '(') {
                    start = 1;
                } else if (fullPhone.charAt(1) == '-') {
                    start = 2;
                } else if (char0 == 1) {
                    start = 1;
                } else {
                    start = 0;
                }
                areaCode = fullPhone.substring(start, start + 3);
            }
        } else if (Util.isValued(phone) && Util.isEmpty(fullPhone)) {
            if (Util.isEmpty(areaCode) || areaCode.equals("000")) {
                fullPhone = phone;
            } else {
                fullPhone = "(" + areaCode + ")" + phone;
            }
        }
        phone = Util.remove(phone, '-');
        xtn.setNumber(fullPhone);
        xtn.setAreaCityCode(NM.create(null, areaCode));
        xtn.setPhoneNumber(NM.create(null, phone));
        return xtn;
    }
    
    private final static void initIndices1() {
        // person_id,last,first,middle,dob,gender,street,city,state,zip,area_code,phone_number,ssn,global_person_id
        IND_MRN = 0;
        IND_NAME_LAST = 1;
        IND_NAME_FIRST = 2;
        IND_NAME_MIDDLE = 3;
        IND_DOB = 4;
        IND_GENDER = 5;
        IND_STREET = 6;
        IND_CITY = 7;
        IND_STATE = 8;
        IND_ZIP = 9;
        IND_AREA_CODE = 10;
        IND_PHONE = 11;
        IND_SSN = 12;
        skipFirst = false;
    }
    
    private final static void initIndices2() {
        // Number,Gender,GivenName,MiddleInitial,Surname,StreetAddress,City,State,ZipCode,Country,EmailAddress,Username,Password,TelephoneNumber,MothersMaiden,Birthday,
        // CCType,CCNumber,CVV2,CCExpires,NationalID,UPS,Occupation,Company,Vehicle,Domain,BloodType,Pounds,Kilograms,FeetInches,Centimeters,GUID,Latitude,Longitude
        IND_MRN = 0;
        IND_GENDER = 1;
        IND_NAME_FIRST = 2;
        IND_NAME_MIDDLE = 3;
        IND_NAME_LAST = 4;
        IND_STREET = 5;
        IND_CITY = 6;
        IND_STATE = 7;
        IND_ZIP = 8;
        //IND_COUNTRY = 9;
        //IND_EMAIL = 10;
        //IND_USERNAME = 11;
        //IND_PASSWORD = 12;
        IND_FULL_PHONE = 13;
        IND_MOTHERS_MAIDEN_NAME = 14;
        IND_DOB = 15;
        skipFirst = true;
    }
}
