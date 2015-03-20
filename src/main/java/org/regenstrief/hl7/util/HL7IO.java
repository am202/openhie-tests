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

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.regenstrief.util.Util;

public class HL7IO {
    
    private static final Log log = LogFactory.getLog(HL7IO.class);
    
    public final static String PROP_TIMEOUT_IN_MILLIS = HL7IO.class.getName() + ".timeoutInMillis";
    
    static final int BOM = 11;
    
    static final int EOM = 28;
    
    static final int CR = 13;
    
    public static int hl7_read_dbv = 0;
    
    /**
     * Defines an interface to be implemented by any type of HL7 message handlers
     */
    public static interface HL7Handler {
        
        /**
         * process an HL7 message received by the HL7 listener
         * 
         * @param hl7Message received message
         * @throws Exception unhandled exception
         */
        public void process(final String hl7Message) throws Exception;
    }
    
    // Used to throw IOException, but that looks like a low-level Exception thrown by a deeper Java API;
    // This class would clearly be thrown by us
    public final static class HL7IOException extends IOException {
        
        private static final long serialVersionUID = 1L;

        private HL7IOException(final String msg) {
            super(msg);
        }
        
        private HL7IOException(final String msg, final Throwable cause) {
            super(msg, cause);
        }
    }
    
    /* -------------------------------------------------------------
       Utilities from mqt.Utl and mqt.AWK
       ------------------------------------------------------------- */
    public static String fileToHeap(final String fname) throws FileNotFoundException, IOException {
        final File fn = new File(fname);
        //Utl.
        dp(">fileToHeap{" + fname + "} len:" + fn.length());
        final FileInputStream fis = new FileInputStream(fn);
        final byte[] a = new byte[(int) fn.length()];
        fis.read(a);
        fis.close();
        return new String(a);
    }
    
    public static String convert_lf_to_cr(final String s) {
        return Util.replaceAllExact(s, "\r\n", "\r").replace('\n', '\r');
    }
    
    public static void sleep(final long delay) {
        if (delay <= 0) {
            return;
        }
        try {
            Thread.sleep(delay * 1000);
        } catch (final Exception e) {
            log.error("Exception:", e);
        }
    }
    
    public static boolean isMscPrint(final int b) {
        switch (b) {
            case 'A':
            case 'B':
            case 'C':
            case 'D':
            case 'E':
            case 'F':
            case 'G':
            case 'H':
            case 'I':
            case 'J':
            case 'K':
            case 'L':
            case 'M':
            case 'N':
            case 'O':
            case 'P':
            case 'Q':
            case 'R':
            case 'S':
            case 'T':
            case 'U':
            case 'V':
            case 'W':
            case 'X':
            case 'Y':
            case 'Z':
            case 'a':
            case 'b':
            case 'c':
            case 'd':
            case 'e':
            case 'f':
            case 'g':
            case 'h':
            case 'i':
            case 'j':
            case 'k':
            case 'l':
            case 'm':
            case 'n':
            case 'o':
            case 'p':
            case 'q':
            case 'r':
            case 's':
            case 't':
            case 'u':
            case 'v':
            case 'w':
            case 'x':
            case 'y':
            case 'z':
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
            case '`':
            case '~':
            case '!':
            case '@':
            case '#':
            case '$':
            case '%':
            case '^':
            case '&':
            case '*':
            case '(':
            case ')':
            case '-':
            case '_':
            case '=':
            case '+':
            case '{':
            case '[':
            case '}':
            case ']':
            case '|':
            case '\\':
            case ';':
            case ':':
            case '"':
            case '\'':
            case '<':
            case ',':
            case '>':
            case '.':
            case '?':
            case '/':
                return true;
            default:
                return false;
        }
    }
    
    public static String hex2(final int ch) {
        final String rv = Integer.toHexString(ch);
        if (rv.length() < 2) {
            return "0" + rv;
        }
        return rv;
    }
    
    public static String mscPrintableString(final String s) {
        final StringBuilder sb = new StringBuilder();
        final int slen = s.length();
        for (int i = 0; i < slen; i++) {
            final char ch = s.charAt(i);
            
            if (isMscPrint(ch)) {
                sb.append(ch);
            } else if (ch == 32) {
                sb.append("[32]");
            } else {
                sb.append("[" + hex2(ch) + "]");
            }
            
        }
        return sb.toString();
    }
    
    // You might get a compiler error here if you have an old compiler
    
    /*
    public static void dp( Object... args)
    {
    //dtabprint();
    log.info(args[0]);
    final StringBuilder sb = new StringBuilder();
    for ( int i  = 1 ; i < args.length ; i++)
    	{
    	sb.append("{"+args[i]+"}");
    	};
    log.info(sb);
    }
    */

    public static void dp(final String pmt, final int a1) {
        //dtabprint();
        log.info(pmt + "{" + a1 + "}");
    }
    
    public static void dp(final String pmt, final Object... a) {
        //dtabprint();
        final StringBuilder sb = new StringBuilder();
        sb.append(pmt);
        for (final Object an : a) {
            sb.append("{" + an + "}");
        }
        log.info(sb);
    }
    
    public static String asc_time_now() {
        final Date d = new Date();
        //return d.toGMTString();
        return d.toString();
    }
    
    public static String s(final int i) {
        return i + "";
    }
    
    static public String nthField(final String s, final char ch, final int n) {
        return nthField(s, ch + "", n);
    }
    
    static public String nthField(final String s, final String ch, final int n) {//0 based
        int idx;
        int prev;
        int cnt = (n > 0 ? n : 0);
        final int chlen = ch.length();
        for (prev = 0, idx = 0; (idx = s.indexOf(ch, prev)) > 0; prev = idx + chlen) {
            //Utl.di("idx:",idx);
            //Utl.dis("sofar",idx,s.substring(prev,idx));
            if (--cnt < 0) {
                break;
            }
        }
        if (idx < 0) {
            return s.substring(prev);
        }
        
        return s.substring(prev, idx);
    }
    
    public static int indexOf(final StringBuffer sb, final char ch, final int startat) {
        for (int i = startat; i < sb.length(); i++) {
            if (sb.charAt(i) == ch) {
                return i;
            }
        }
        
        return -1;
    }
    
    static public StringBuffer replaceNthField(final StringBuffer sb, final char ch, final int n, final String newval) {// zero based
        int idx = -1, cnt = (n > 0 ? n : 0);
        // Find the n'th delim
        //Utl.dp("replaceNthString:",n);
        if (n < 0) {
            return sb;
        }
        for (int prev = 0; cnt > 0; prev = idx + 1) {
            idx = indexOf(sb, ch, prev);
            //Utl.dp("cnt:",cnt,"idx:",idx);
            if (idx < 0) {
                break;
            }
            cnt--;
            //Utl.dis("sofar",idx,s.substring(prev,idx));
        }
        //Utl.dp("End search for nth idx:",idx," cnt = ",cnt);
        if (cnt > 0) {
            /* then we need to pad input */
            //Utl.dp("pad by cnt:",cnt);
            //Utl.dp("prev:",prev);
            while (cnt-- > 0) {
                sb.append(ch);
            }
            // The new field is just appended
            sb.append(newval);
            // We are done
            return sb;
        }
        //Utl.dp("cnt:",cnt,"idx:",idx);
        // Is there a next delimiter
        final int closer = indexOf(sb, ch, idx + 1);
        //  Utl.dp("closer = ",closer);
        if (closer > 0) {
            // Yup, replace the range
            sb.replace(idx + 1, closer, newval);
        } else {
            // No closer
            sb.replace(idx + 1, sb.length(), newval);
        }
        return sb;
    }
    
    static public String replaceNthField(final String s, final char ch, final int n, final String newval) {// zero based
        final StringBuffer sb = new StringBuffer(s);
        return replaceNthField(sb, ch, n, newval).toString();
    };
    
    /* ***********************************************************
       HL7 Routines
     *********************************************************** */
    // --------------------------------------
    // read_hl7_msg
    // --------------------------------------
    public static String read_hl7_msg(final Reader br) throws IOException {
        final int ldbv = hl7_read_dbv;
        //int ccnt = 0;
        int ach = br.read();
        if (ldbv > 0) {
            dp("ach.1 = ", ach);
        }
        
        if (ach == -1) {
            dp("Bad socket in first read [read_hl7_msg]");
            throw new HL7IOException("EndOfSocket");
        } else if (ach == CR) {
            // skip the CR after EOM
            ach = br.read();
            if (ldbv > 0) {
                dp("skip cr, leaving ach = ", ach);
            }
        }
        while (ach != BOM) {
            if (ach == -1) {
                dp("Bad socket in looking for BOM read [read_hl7_msg]");
                throw new HL7IOException("EndOfSocket");
            } else if (ldbv > 0) {
                // sloughing ach
                dp("slough.ach = ", ach);
            }
            ach = br.read();
        }
        
        final StringBuffer sb = new StringBuffer();
        if (ldbv > 0) {
            dp("After bom=", ach);
        }
        ach = br.read();
        
        if (ldbv > 0) {
            dp("Start collecting:", ach);
        }
        while (ach != EOM) {
            if (ach == -1) {
                dp("Bad socket in looking for EOM read [read_hl7_msg]");
                throw new HL7IOException("EndOfSocket");
            }
            sb.append((char) ach);
            if (ldbv > 3) {
                dp("Found:" + sb.length() + " ch:=" + ach);
            }
            ach = br.read();
        }
        if (ldbv > 0) {
            dp("Done collecting ach=" + ach + " sb.length=" + sb.length());
        }
        return sb.toString();
    }
    
    static String make_ack(final String msg) throws Exception {
        final int ldbv = hl7_read_dbv;
        if (!msg.startsWith("MSH")) {
            dp("Does not start with MSH:", msg);
            throw new Exception("Bad Message");
        }
        final char fld = msg.charAt(3);
        final String msh = nthField(msg, "\r", 0);
        if (ldbv > 0) {
            dp("msh:", msh);
        }
        final String snd_app = nthField(msg, fld, 2), snd_fac = nthField(msg, fld, 3);
        if (ldbv > 0) {
            dp("snd_app:" + snd_app + "snd_fac:" + snd_fac);
        }
        final StringBuffer sb = new StringBuffer(msh);
        replaceNthField(sb, fld, 2, nthField(msg, fld, 4));
        replaceNthField(sb, fld, 3, nthField(msg, fld, 5));
        replaceNthField(sb, fld, 4, nthField(msg, fld, 2));
        replaceNthField(sb, fld, 5, nthField(msg, fld, 3));
        final String ack = sb.toString() + "\rMSA" + fld + "AA" + fld + nthField(msg, fld, 9) + "\r";
        return ack;
    }
    
    public static void send_hl7_msg(final Writer os, final String msg) throws IOException {
        send_hl7_msg(os, msg, null);
    }
    
    public static void send_hl7_msg(final Writer os, final String msg, final String header) throws IOException {
        /*
        \x0b17:MSH|^~\&|XXXX|YYYY\x0d\x1c\x0b
        HL7 MESSAGE
        \x1c

        \x0b = [0][11] = 11
        \x0d = [0][13] = 13
        \x1c = [1][12] = 1 * 16 + 12 = 28
        */

        if (header != null) {
            os.write(11);
            os.write("17:");
            os.write(header);
            os.write(13);
            os.write(28);
        }
        os.write(11);
        os.write(msg);
        os.write(28);
        os.write(13);
        os.flush();
    }
    
    public static String send_rcv_hl7_msg(final String host, final int port, final Writer dsd_w, final Reader dsd_r,
                                          final String msg) throws IOException {
        return send_rcv_hl7_msg(host, port, dsd_w, dsd_r, msg, null);
    }
    
    public static String send_rcv_hl7_msg(final String host, final int port, final Writer dsd_w, final Reader dsd_r,
                                          final String msg, final String header) throws IOException {
        try {
            send_hl7_msg(dsd_w, msg, header);
        } catch (final Exception e) {
            throw new HL7IOException("Could not send HL7 to " + host + ":" + port, e);
        }
        try {
            return read_hl7_msg(dsd_r);
        } catch (final Exception e) {
            throw new HL7IOException("Could not receive HL7 from " + host + ":" + port, e);
        }
    }
    
    public static String send_rcv_hl7_msg(final String host, final int port, final int retries, final String msg)
                                                                                                                 throws IOException {
        return send_rcv_hl7_msg(host, port, retries, msg, null);
    }
    
    public static String send_rcv_hl7_msg(final String host, final int port, final int retries, final String msg,
                                          final String header) throws IOException {
        final Socket sd = connect(host, port, retries);
        Writer w;
        BufferedReader r;
        
        try {
            w = new PrintWriter(new BufferedOutputStream(getOutputStream(host, port, sd)));
            r = new BufferedReader(new InputStreamReader(getInputStream(host, port, sd)));
            return send_rcv_hl7_msg(host, port, w, r, msg, header);
        } finally {
            sd.close();
        }
    }
    
    private static OutputStream getOutputStream(final String host, final int port, final Socket sd) throws IOException {
        try {
            return sd.getOutputStream();
        } catch (final Exception e) {
            throw new HL7IOException("Could not open HL7 OutputStream for " + host + ":" + port, e);
        }
    }
    
    private static InputStream getInputStream(final String host, final int port, final Socket sd) throws IOException {
        try {
            return sd.getInputStream();
        } catch (final Exception e) {
            throw new HL7IOException("Could not open HL7 InputStream for " + host + ":" + port, e);
        }
    }
    
    public static String invoke(final String propHost, final String propPort, final String displayName, final String msg)
                                                                                                                         throws IOException {
        return invoke(propHost, propPort, displayName, msg, null);
    }
    
    private static String getProp(final String displayName, final String key) {
        return assertProp(displayName, key, Util.getProperty(key));
    }
    
    private static String assertProp(final String displayName, final String key, final String value) {
        if (Util.isEmpty(value)) {
            throw new RuntimeException("Can't invoke " + displayName + " unless property " + key + " is valued");
        }
        
        return value;
    }
    
    public static String invoke(final String propHost, final String propPort, final String displayName, final String msg,
                                final String header) throws IOException {
        return invokeLiteral(getProp(displayName, propHost), getProp(displayName, propPort), displayName, msg, header);
    }
    
    private static String invokeLiteral(final String host, final String port, final String displayName, String msg,
                                        final String header) throws IOException {
        msg = convert_lf_to_cr(msg);
        log.info("Sending " + displayName + " request to " + host + ":" + port);
        
        return send_rcv_hl7_msg(host, Integer.parseInt(port), 20, msg, header);
    }
    
    public static void setTimeoutInMillis(final int millis) {
        Util.setProperty(PROP_TIMEOUT_IN_MILLIS, Integer.toString(millis));
    }
    
    public static Socket connect(final String host, final int port, int nrRetries) throws UnknownHostException, IOException {
        final String prop = Util.getProperty(PROP_TIMEOUT_IN_MILLIS);
        final int timeout = prop == null ? -1 : Integer.parseInt(prop);
        
        while (nrRetries-- >= 0) {
            try {
                final Socket rv = new Socket(host, port);
                if (timeout >= 0) {
                    rv.setSoTimeout(timeout);
                }
                
                return rv;
            } catch (final UnknownHostException e) {
                e.fillInStackTrace();
                //Utl.dp("connect failed: unknownHost:", host, port);
                throw e;
            } catch (final IOException e) {
                if (nrRetries < 0) {
                    e.fillInStackTrace();
                    //Utl.dp("connect failed: connection refused:", host, port);
                    throw e;
                }
                sleep(10);
            }
        }
        throw new HL7IOException("connect nr retries exceeded:" + host + "|" + port + "|" + nrRetries);
    }
}

/* Local Variables: */
/* *special-compile*: "javac -Xemacs HL7IO.java" */
/* *xspecial-compile*: "vaxput HL7IO.java ds:HL7IO.java" */
/* *vms-disable-local-xfer*: nil */
/* *vms-directory-x*: "op nlmput HL7IO.java /a/tmp/." */
/* End: */
