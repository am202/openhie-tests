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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.regenstrief.hl7.HL7Parser;
import org.regenstrief.hl7.SegmentReader;
import org.regenstrief.hl7.segment.HL7Segment;
import org.regenstrief.hl7.util.HL7IO;
import org.regenstrief.io.IoUtil;
import org.regenstrief.util.Util;

/**
 * MessageSender
 */
public class MessageSender {
    
    public final static String HOST = Util.getProperty("org.regenstrief.ohie.cr.host", "cr2.test.ohie.org");
    
    public final static int PORT_PIX = Util.getPropertyInt("org.regenstrief.ohie.cr.port.pix", 2100);
    
    public final static int PORT_PDQ = Util.getPropertyInt("org.regenstrief.ohie.cr.port.pdq", 2100);
    
    private final static int NUM_THREADS = Util.getPropertyInt("org.regenstrief.ohie.cr.threads", 10);
    
    private final static String STOP = "STOP";
    
    private final static String BR = Util.getLineSeparator();
    
    private final static Object infoLock = new Object();
    
    private final static Object countLock = new Object();
    
    private final static BlockingQueue<String> messageQueue = new ArrayBlockingQueue<String>(Math.round(NUM_THREADS * 1.5f));
    
    //private final static HL7Transform transform = null;
    
    private static volatile int messageCount = 0;
    
    private static PrintStream out = null;
    
    public final static void main(final String[] args) {
        try {
            run(args);
        } catch (final Throwable e) {
            e.printStackTrace();
        }
    }
    
    private final static void run(final String[] args) throws Exception {
        final String inName = args[0];
        BufferedReader bin = null;
        startSenders();
        try {
            out = Util.getPrintStream(inName + ".out.txt");
            info("Starting to send messages to " + HOST + ":" + PORT_PIX + BR);
            bin = Util.getBufferedReader(inName);
            final SegmentReader in = new SegmentReader(HL7Parser.createLaxParser(), bin);
            HL7Segment seg = null;
            List<HL7Segment> msg = new ArrayList<HL7Segment>();
            while ((seg = in.readSegment()) != null) {
                if ("MSH".equals(seg.getTagName())) {
                    send(msg);
                    msg.clear();
                }
                msg.add(seg);
            }
            send(msg);
            send(STOP);
        } finally {
            IoUtil.close(out);
            IoUtil.close(bin);
        }
        info("Finished after " + messageCount + " messages");
    }
    
    private final static void send(final List<HL7Segment> msg) throws Exception {
        if (Util.isEmpty(msg)) {
            return;
        }
        transform(msg);
        final StringBuilder b = new StringBuilder();
        for (final HL7Segment seg : msg) {
            b.append(seg.toPiped()).append('\n');
        }
        synchronized (countLock) {
            messageCount++;
        }
        final String req = b.toString();
        info("Sending " + messageCount + ":" + BR + req);
        send(req);
    }
    
    private final static void transform(final List<HL7Segment> msg) throws Exception {
        /*if (transform == null) {
            return;
        }
        final HL7DataTree tree = new HL7DataTree(new ADT_A01(msg.get(0).getProp()));
        for (final HL7Segment seg : msg) {
            tree.addChild(seg);
        }
        transform.transform(tree);
        msg.clear();
        for (final HL7Segment seg : tree.getDescendantValues(HL7Segment.class)) {
            msg.add(seg);
        }*/
    }
    
    private final static void startSenders() {
        for (int i = 0; i < NUM_THREADS; i++) {
            new Thread(new RawSender()).start();
        }
    }
    
    private final static void send(final String msg) throws Exception {
        messageQueue.put(msg);
    }
    
    private final static class RawSender implements Runnable {
        
        @Override
        public final void run() {
            try {
                while (true) {
                    final String msg = messageQueue.take();
                    if (STOP.equals(msg)) {
                        send(STOP); // Make sure next thread will see the STOP signal too
                        return;
                    }
                    final String rsp = HL7IO.send_rcv_hl7_msg(HOST, PORT_PIX, 0, HL7IO.convert_lf_to_cr(msg));
                    info("Received " + messageCount + ":" + BR + rsp + BR);
                }
            } catch (final Exception e) {
                info(Util.getStackTraceString(e));
            }
        }
    }
    
    private final static void info(final String s) {
        synchronized (infoLock) {
            System.out.println(s);
            out.println(s);
        }
    }
}
