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

import org.regenstrief.hl7.HL7DataTree;
import org.regenstrief.hl7.HL7Parser;
import org.regenstrief.hl7.group.UMSG_Z01;
import org.regenstrief.hl7.segment.HL7Segment;
import org.regenstrief.hl7.segment.PID;
import org.regenstrief.hl7.segment.USEG;

import junit.framework.TestCase;

/**
 * TestMessageValidator
 */
public class TestMessageValidator extends TestCase {
    
    private static HL7Parser parser = null;
    
    public void testValidate() throws Exception {
        parser = HL7Parser.createLaxParser();
        final PID pid1 = newPID("PID|||123^^^&1.2&ISO||DOE^JOHN");
        final PID pid2 = newPID("PID|||123^^^&1.2&ISO~456^^^^MR||DOE^JOHN");
        final HL7DataTree msg1 = newMsg(pid1);
        final HL7DataTree msg2 = newMsg(pid2);
        validate(msg1, msg1);
        validate(msg1, msg2);
        validate(msg2, msg2);
        runInvalid(msg2, msg1);
        final PID pid1Less = newPID("PID|||123^^^&1.2||DOE^JOHN");
        final HL7DataTree msg1Less = newMsg(pid1Less);
        validate(msg1Less, msg1);
        runInvalid(msg1, msg1Less);
        final PID pid2Less = newPID("PID|||123^^^&1.2&ISO~456||DOE^JOHN");
        final HL7DataTree msg2Less = newMsg(pid2Less);
        validate(msg2Less, msg2);
        runInvalid(msg2, msg2Less);
        final PID pid1More = newPID("PID|1||123^^^&1.2&ISO||DOE^JOHN");
        final HL7DataTree msg1More = newMsg(pid1More);
        validate(msg1, msg1More);
        runInvalid(msg1More, msg1);
        final USEG siz1 = newUSEG("SIZ|PID|=|1");
        final HL7DataTree msg12 = newMsg(pid1, pid2);
        final HL7DataTree msg1Siz1 = newMsg(pid1, siz1);
        validate(msg1, msg12);
        validate(msg2, msg12);
        validate(msg12, msg12);
        runInvalid(msg1Siz1, msg12);
        final USEG any = newUSEG("ANY|2");
        final HL7DataTree msgAny = newMsg(any, pid1, siz1);
        validate(msgAny, msg12);
        validate(msgAny, msg1Less);
        final HL7DataTree msg1Less2 = newMsg(pid1Less, pid1Less);
        runInvalid(msgAny, msg1Less2);
        final PID pidEmpty = newPID("PID|EMPTY");
        final HL7DataTree msgEmpty = newMsg(pidEmpty);
        validate(msgEmpty, msg1);
        runInvalid(msgEmpty, msg1More);
        final PID pidValued = newPID("PID|VALUED");
        final HL7DataTree msgValued = newMsg(pidValued);
        validate(msgValued, msg1More);
        runInvalid(msgValued, msg1);
        final PID pidAny12 = newPID("PID|ANY(1;2)");
        final HL7DataTree msgAny12 = newMsg(pidAny12);
        validate(msgAny12, msg1More);
        runInvalid(msgAny12, msg1);
        final PID pid1More3 = newPID("PID|3||123^^^&1.2&ISO||DOE^JOHN");
        final HL7DataTree msg1More3 = newMsg(pid1More3);
        runInvalid(msgAny12, msg1More3);
        final PID pidSiz1 = newPID("PID|||SIZ(1)");
        final HL7DataTree msgSiz1 = newMsg(pidSiz1);
        validate(msgSiz1, msg1);
        runInvalid(msgSiz1, msg2);
        final USEG rem = newUSEG("REM|Some free text");
        final HL7DataTree msgRem = newMsg(any, rem, pid1, siz1);
        validate(msgRem, msg12);
        validate(msgRem, msg1Less);
        runInvalid(msgRem, msg1Less2);
    }
    
    private void validate(final HL7DataTree ex, final HL7DataTree ac) throws Exception {
        new MessageValidator().validateMessage(ex, ac);
    }
    
    private void runInvalid(final HL7DataTree ex, final HL7DataTree ac) throws Exception {
        try {
            validate(ex, ac);
        } catch (final IllegalStateException e) {
            //println("Validation failed as expected: " + e.getMessage());
            return;
        }
        fail("Expected validation to fail, but it succeeded");
    }
    
    public PID newPID(final String s) {
        return PID.parsePiped(parser, s);
    }
    
    public USEG newUSEG(final String s) {
        return USEG.parsePiped(parser, s);
    }
    
    public HL7DataTree newMsg(final HL7Segment... a) {
        final HL7DataTree tree = new HL7DataTree(new UMSG_Z01(parser));
        for (final HL7Segment s : a) {
            tree.addChild(s);
        }
        return tree;
    }
}
