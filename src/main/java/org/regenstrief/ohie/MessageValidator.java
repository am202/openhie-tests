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

import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.regenstrief.hl7.HL7Data;
import org.regenstrief.hl7.HL7DataTree;
import org.regenstrief.hl7.datatype.HL7DataType;
import org.regenstrief.hl7.datatype.UCMP;
import org.regenstrief.hl7.datatype.UFLD;
import org.regenstrief.hl7.segment.HL7Segment;
import org.regenstrief.hl7.segment.USEG;
import org.regenstrief.io.IoUtil;
import org.regenstrief.util.Util;

/**
 * MessageValidator
 */
public class MessageValidator {
    
    private final static String SEGMENT_REM = "REM"; // Remark
    
    private final static String SEGMENT_ANY = "ANY";
    
    private final static String SEGMENT_SIZ = "SIZ"; // Size
    
    private final static String VALUE_VALUED = "VALUED";
    
    private final static String VALUE_EMPTY = "EMPTY";
    
    private final static String VALUE_PREFIX_ANY = "ANY(";
    
    private final static String VALUE_PREFIX_SIZ = "SIZ(";
    
    private int anyCount = 0;
    
    private boolean anyMet = false;
    
    public void validateMessage(final HL7DataTree exTree, final HL7DataTree acTree) throws Exception {
        this.anyCount = 0;
        this.anyMet = false;
        Reader in = null;
        try {
            for (final HL7Segment tst : exTree.getDescendantValues(HL7Segment.class)) {
                final String type = tst.getTagName();
                // REM|Free text remark
                if (SEGMENT_REM.equals(type)) {
                    continue;
                // ANY|2
                } else if (SEGMENT_ANY.equals(type)) {
                    if (this.anyCount > 0) {
                        throw new IllegalArgumentException("Found new ANY test while processing previous ANY test");
                    }
                    this.anyCount = Integer.parseInt(getField(tst, 1));
                    if (this.anyCount <= 1) {
                        throw new IllegalArgumentException("ANY test must have more than one condition");
                    }
                    this.anyCount++; // Add 1 to skip the current segment
                    this.anyMet = false;
                // SIZ|PID|>=|1
                } else if (SEGMENT_SIZ.equals(type)) {
                    final String segmentName = getField(tst, 1);
                    final int actualCount = Util.size(acTree.getDescendantValues(segmentName));
                    final String comparison = getField(tst, 2);
                    final char c0 = comparison.charAt(0);
                    final boolean orEquals = comparison.length() > 1;
                    final int expectedCount = Integer.parseInt(getField(tst, 3));
                    final boolean cmp;
                    boolean illegal = false;
                    if (orEquals) {
                        if (c0 == '<') {
                            cmp = actualCount <= expectedCount;
                        } else if (c0 == '>') {
                            cmp = actualCount >= expectedCount;
                        } else {
                            illegal = true;
                            cmp = false;
                        }
                    } else {
                        if (c0 == '=') {
                            cmp = expectedCount == actualCount;
                        } else if (c0 == '<') {
                            cmp = actualCount < expectedCount;
                        } else if (c0 == '>') {
                            cmp = actualCount > expectedCount;
                        } else {
                            illegal = true;
                            cmp = false;
                        }
                    }
                    if (illegal) {
                        throw new IllegalArgumentException("Unknown comparison " + comparison + " in " + tst.toPiped());
                    }
                    assertTrue("Expected " + segmentName + " size to be " + comparison + " " + expectedCount + " but was " + actualCount, cmp);
                // PID|||123^^^^MR
                } else {
                    final String segmentName = type;
                    final USEG exSeg = USEG.toUSEG(tst);
                    final int exSize = exSeg.size();
                    boolean found = false;
                    for (final HL7Data child : acTree.getDescendantValues(segmentName)) {
                        final USEG acSeg = USEG.toUSEG((HL7Segment) child);
                        boolean failed = false;
                        for (int i = 1; i <= exSize; i++) {
                            if (!validate(exSeg.get(i), acSeg.get(i))) {
                                failed = true;
                                break;
                            }
                        }
                        if (!failed) {
                            found = true;
                            break;
                        }
                    }
                    assertTrue("Could not find segment matching " + tst.toPiped(), found);
                }
                if (this.anyCount > 0) {
                    this.anyCount--;
                    if (this.anyCount <= 0) {
                        assertTrue("None of the ANY conditions were met", this.anyMet);
                    }
                }
            }
            if (this.anyCount > 0) {
                throw new IllegalArgumentException("ANY test specified more conditions than were found in test plan");
            }
        } finally {
            IoUtil.close(in);
        }
    }
    
    private void assertTrue(final String failureMessage, final boolean flag) {
        if (flag) {
            if (this.anyCount > 0) {
                this.anyMet = true;
            }
            return;
        } else if (this.anyCount <= 0) {
            throw new IllegalStateException(failureMessage);
        }
    }
    
    private boolean validate(final List<UFLD> exFlds, List<UFLD> acFlds) {
        if (exFlds == null) {
            return true;
        }
        final Iterator<UFLD> iter = exFlds.iterator();
        while (iter.hasNext()) {
            final UFLD fld = iter.next();
            if (fld.size() == 1) {
                final String val = getField(fld);
                if (isFunc(val, VALUE_PREFIX_SIZ)) {
                    if (Integer.parseInt(getArg(val)) != Util.size(acFlds)) {
                        return false;
                    }
                    iter.remove();
                    break;
                }
            }
        }
        final int exFldsSize = Util.size(exFlds);
        int acFldsSize = Util.size(acFlds);
        if (exFldsSize == 0) {
            return true;
        } else if (acFldsSize == 0) {
            if (VALUE_EMPTY.equals(getField(exFlds))) {
                acFlds = new ArrayList<UFLD>(1);
                acFlds.add(newFld());
                acFldsSize = 1;
            } else {
                return false;
            }
        }
        if (acFldsSize < exFldsSize) {
            return false;
        }
        for (final UFLD exFld : exFlds) {
            boolean found = false;
            for (final UFLD acFld : acFlds) {
                if (validate(exFld, acFld)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }
    
    private final UFLD newFld() {
        final UFLD fld = new UFLD(null);
        fld.set(1, newCmp());
        return fld;
    }
    
    private boolean validate(final UFLD exFld, UFLD acFld) {
        final int exFldSize = UFLD.size(exFld);
        int acFldSize = UFLD.size(acFld);
        if (exFldSize == 0) {
            return true;
        } else if (acFldSize == 0) {
            if (VALUE_EMPTY.equals(getField(exFld))) {
                acFld = newFld();
                acFldSize = 1;
            } else {
                return false;
            }
        }
        if (acFldSize < exFldSize) {
            return false;
        }
        for (int i = 1; i <= exFldSize; i++) {
            if (!validate(exFld.get(i), acFld.get(i))) {
                return false;
            }
        }
        return true;
    }
    
    private final UCMP newCmp() {
        final UCMP cmp = new UCMP(null);
        cmp.set(1, "");
        return cmp;
    }
    
    private boolean validate(final UCMP exCmp, UCMP acCmp) {
        final int exCmpSize = UCMP.size(exCmp);
        int acCmpSize = UCMP.size(acCmp);
        if (exCmpSize == 0) {
            return true;
        } else if (acCmpSize == 0) {
            if (VALUE_EMPTY.equals(getField(exCmp))) {
                acCmp = newCmp();
                acCmpSize = 1;
            } else {
                return false;
            }
        }
        if (acCmpSize < exCmpSize) {
            return false;
        }
        for (int i = 1; i <= exCmpSize; i++) {
            if (!validate(exCmp.get(i), acCmp.get(i))) {
                return false;
            }
        }
        return true;
    }
    
    private boolean validate(final String exSub, final String acSub) {
        if (Util.isEmpty(exSub)) {
            return true;
        } else if (VALUE_EMPTY.equals(exSub)) {
            return Util.isEmpty(acSub);
        } else if (VALUE_VALUED.equals(exSub)) {
            return Util.isValued(acSub);
        } else if (isFunc(exSub, VALUE_PREFIX_ANY)) {
            final String list = getArg(exSub);
            for (final String token : Util.splitExactIntoList(list, ';')) {
                if (token.equals(acSub)) {
                    return true;
                }
            }
            return false;
        }
        return exSub.equals(acSub);
    }
    
    private final boolean isFunc(final String val, final String prefix) {
        return (val != null) && val.startsWith(prefix) && val.endsWith(")");
    }
    
    private final String getArg(final String func) {
        return func.substring(4, func.length() - 1);
    }
    
    private String getField(final HL7Segment tst, final int i) {
        return getField(tst.get(i));
    }
    
    private String getField(final Object o) {
        if (o == null) {
            return null;
        } else if (o instanceof List) {
            return getField(Util.get((List<?>) o, 0));
        } else if (o instanceof HL7DataType) {
            return getField(((HL7DataType) o).get(1));
        }
        return o.toString();
    }
}
