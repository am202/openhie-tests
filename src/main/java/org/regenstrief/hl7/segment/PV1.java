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
package org.regenstrief.hl7.segment;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import org.regenstrief.hl7.HL7Parser;
import org.regenstrief.hl7.HL7Properties;
import org.regenstrief.hl7.datatype.CE;
import org.regenstrief.hl7.datatype.CWE;
import org.regenstrief.hl7.datatype.CX;
import org.regenstrief.hl7.datatype.DLD;
import org.regenstrief.hl7.datatype.DT;
import org.regenstrief.hl7.datatype.FC;
import org.regenstrief.hl7.datatype.NM;
import org.regenstrief.hl7.datatype.PL;
import org.regenstrief.hl7.datatype.SI;
import org.regenstrief.hl7.datatype.TS;
import org.regenstrief.hl7.datatype.XCN;
import org.regenstrief.util.Util;

/**
 * <p>
 * Title: PV1
 * </p>
 * <p>
 * Description: HL7 Patient Visit
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Regenstrief Institute
 * </p>
 * 
 * @author Andrew Martin
 * @version 1.0
 */
public class PV1 extends HL7Segment {
    
    public final static String PV1_XML = "PV1";
    
    public final static String SET_ID_PV1_XML = "PV1.1";
    
    public final static String PATIENT_CLASS_XML = "PV1.2";
    
    public final static String ASSIGNED_PATIENT_LOCATION_XML = "PV1.3";
    
    public final static String ADMISSION_TYPE_XML = "PV1.4";
    
    public final static String PREADMIT_NUMBER_XML = "PV1.5";
    
    public final static String PRIOR_PATIENT_LOCATION_XML = "PV1.6";
    
    public final static String ATTENDING_DOCTOR_XML = "PV1.7";
    
    public final static String REFERRING_DOCTOR_XML = "PV1.8";
    
    public final static String CONSULTING_DOCTOR_XML = "PV1.9";
    
    public final static String HOSPITAL_SERVICE_XML = "PV1.10";
    
    public final static String TEMPORARY_LOCATION_XML = "PV1.11";
    
    public final static String PREADMIT_TEST_INDICATOR_XML = "PV1.12";
    
    public final static String RE_ADMISSION_INDICATOR_XML = "PV1.13";
    
    public final static String ADMIT_SOURCE_XML = "PV1.14";
    
    public final static String AMBULATORY_STATUS_XML = "PV1.15";
    
    public final static String VIP_INDICATOR_XML = "PV1.16";
    
    public final static String ADMITTING_DOCTOR_XML = "PV1.17";
    
    public final static String PATIENT_TYPE_XML = "PV1.18";
    
    public final static String VISIT_NUMBER_XML = "PV1.19";
    
    public final static String FINANCIAL_CLASS_XML = "PV1.20";
    
    public final static String CHARGE_PRICE_INDICATOR_XML = "PV1.21";
    
    public final static String COURTESY_CODE_XML = "PV1.22";
    
    public final static String CREDIT_RATING_XML = "PV1.23";
    
    public final static String CONTRACT_CODE_XML = "PV1.24";
    
    public final static String CONTRACT_EFFECTIVE_DATE_XML = "PV1.25";
    
    public final static String CONTRACT_AMOUNT_XML = "PV1.26";
    
    public final static String CONTRACT_PERIOD_XML = "PV1.27";
    
    public final static String INTEREST_CODE_XML = "PV1.28";
    
    public final static String TRANSFER_TO_BAD_DEBT_CODE_XML = "PV1.29";
    
    public final static String TRANSFER_TO_BAD_DEBT_DATE_XML = "PV1.30";
    
    public final static String BAD_DEBT_AGENCY_CODE_XML = "PV1.31";
    
    public final static String BAD_DEBT_TRANSFER_AMOUNT_XML = "PV1.32";
    
    public final static String BAD_DEBT_RECOVERY_AMOUNT_XML = "PV1.33";
    
    public final static String DELETE_ACCOUNT_INDICATOR_XML = "PV1.34";
    
    public final static String DELETE_ACCOUNT_DATE_XML = "PV1.35";
    
    public final static String DISCHARGE_DISPOSITION_XML = "PV1.36";
    
    public final static String DISCHARGED_TO_LOCATION_XML = "PV1.37";
    
    public final static String DIET_TYPE_XML = "PV1.38";
    
    public final static String SERVICING_FACILITY_XML = "PV1.39";
    
    public final static String BED_STATUS_XML = "PV1.40";
    
    public final static String ACCOUNT_STATUS_XML = "PV1.41";
    
    public final static String PENDING_LOCATION_XML = "PV1.42";
    
    public final static String PRIOR_TEMPORARY_LOCATION_XML = "PV1.43";
    
    public final static String ADMIT_DATE_TIME_XML = "PV1.44";
    
    public final static String DISCHARGE_DATE_TIME_XML = "PV1.45";
    
    public final static String CURRENT_PATIENT_BALANCE_XML = "PV1.46";
    
    public final static String TOTAL_CHARGES_XML = "PV1.47";
    
    public final static String TOTAL_ADJUSTMENTS_XML = "PV1.48";
    
    public final static String TOTAL_PAYMENTS_XML = "PV1.49";
    
    public final static String ALTERNATE_VISIT_ID_XML = "PV1.50";
    
    public final static String VISIT_INDICATOR_XML = "PV1.51";
    
    public final static String OTHER_HEALTHCARE_PROVIDER_XML = "PV1.52";
    
    public final static String SERVICE_EPISODE_DESCRIPTION_XML = "PV1.53";
    
    public final static String SERVICE_EPISODE_IDENTIFIER_XML = "PV1.54";
    
    private SI setIDPV1 = null;
    
    private CWE patientClass = null;
    
    private PL assignedPatientLocation = null;
    
    private CWE admissionType = null;
    
    private CX preadmitNumber = null;
    
    private PL priorPatientLocation = null;
    
    private List<XCN> attendingDoctor = null;
    
    private List<XCN> referringDoctor = null;
    
    private List<XCN> consultingDoctor = null;
    
    private CWE hospitalService = null;
    
    private PL temporaryLocation = null;
    
    private CWE preadmitTestIndicator = null;
    
    private CWE reAdmissionIndicator = null;
    
    private CWE admitSource = null;
    
    private List<CWE> ambulatoryStatus = null;
    
    private CWE vipIndicator = null;
    
    private List<XCN> admittingDoctor = null;
    
    private CWE patientType = null;
    
    private CX visitNumber = null;
    
    private List<FC> financialClass = null;
    
    private CWE chargePriceIndicator = null;
    
    private CWE courtesyCode = null;
    
    private CWE creditRating = null;
    
    private List<CWE> contractCode = null;
    
    private List<DT> contractEffectiveDate = null;
    
    private List<NM> contractAmount = null;
    
    private List<NM> contractPeriod = null;
    
    private CWE interestCode = null;
    
    private CWE transferToBadDebtCode = null;
    
    private DT transferToBadDebtDate = null;
    
    private CWE badDebtAgencyCode = null;
    
    private NM badDebtTransferAmount = null;
    
    private NM badDebtRecoveryAmount = null;
    
    private CWE deleteAccountIndicator = null;
    
    private DT deleteAccountDate = null;
    
    private CWE dischargeDisposition = null;
    
    private DLD dischargedToLocation = null;
    
    private CE dietType = null;
    
    private CWE servicingFacility = null;
    
    private String bedStatus = null;
    
    private CWE accountStatus = null;
    
    private PL pendingLocation = null;
    
    private PL priorTemporaryLocation = null;
    
    private TS admitDateTime = null;
    
    private List<TS> dischargeDateTime = null;
    
    private NM currentPatientBalance = null;
    
    private NM totalCharges = null;
    
    private NM totalAdjustments = null;
    
    private NM totalPayments = null;
    
    private CX alternateVisitID = null;
    
    private CWE visitIndicator = null;
    
    private List<XCN> otherHealthcareProvider = null;
    
    private String serviceEpisodeDescription = null;
    
    private CX serviceEpisodeIdentifier = null;
    
    /**
     * Constructs an empty PV1
     * 
     * @param prop the HL7Properties
     **/
    public PV1(final HL7Properties prop) {
        super(prop);
    }
    
    /**
     * Adds missing required fields
     * 
     * @return the HL7Data with all required fields
     **/
    @Override
    public PV1 addRequired() {
        if (this.patientClass == null) {
            this.patientClass = new CWE(this.prop);
        }
        
        return this;
    }
    
    public static PV1 parsePiped(final HL7Parser parser, final String line) {
        final PV1 pv1 = new PV1(parser);
        pv1.readPiped(parser, line);
        return pv1;
    }
    
    @Override
    public void readPiped(final HL7Parser parser, final String line) {
        final char f = parser.getFieldSeparator();
        int start = line.indexOf(f) + 1;
        if (start <= 0) {
            return;
        }
        int stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        final char c = parser.getComponentSeparator();
        this.setIDPV1 = SI.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.patientClass = CWE.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.assignedPatientLocation = PL.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.admissionType = CWE.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.preadmitNumber = CX.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.priorPatientLocation = PL.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        final char r = parser.getRepetitionSeparator();
        while (start < stop) {
            final int next = getNext(line, start, r, stop);
            addAttendingDoctor(XCN.parsePiped(parser, line, start, c, next));
            start = next + 1;
        }
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        while (start < stop) {
            final int next = getNext(line, start, r, stop);
            addReferringDoctor(XCN.parsePiped(parser, line, start, c, next));
            start = next + 1;
        }
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        while (start < stop) {
            final int next = getNext(line, start, r, stop);
            addConsultingDoctor(XCN.parsePiped(parser, line, start, c, next));
            start = next + 1;
        }
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.hospitalService = CWE.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.temporaryLocation = PL.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.preadmitTestIndicator = CWE.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.reAdmissionIndicator = CWE.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.admitSource = CWE.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        while (start < stop) {
            final int next = getNext(line, start, r, stop);
            addAmbulatoryStatus(CWE.parsePiped(parser, line, start, c, next));
            start = next + 1;
        }
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.vipIndicator = CWE.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        while (start < stop) {
            final int next = getNext(line, start, r, stop);
            addAdmittingDoctor(XCN.parsePiped(parser, line, start, c, next));
            start = next + 1;
        }
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.patientType = CWE.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.visitNumber = CX.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        while (start < stop) {
            final int next = getNext(line, start, r, stop);
            addFinancialClass(FC.parsePiped(parser, line, start, c, next));
            start = next + 1;
        }
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.chargePriceIndicator = CWE.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.courtesyCode = CWE.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.creditRating = CWE.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        while (start < stop) {
            final int next = getNext(line, start, r, stop);
            addContractCode(CWE.parsePiped(parser, line, start, c, next));
            start = next + 1;
        }
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        while (start < stop) {
            final int next = getNext(line, start, r, stop);
            addContractEffectiveDate(DT.parsePiped(parser, line, start, c, next));
            start = next + 1;
        }
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        while (start < stop) {
            final int next = getNext(line, start, r, stop);
            addContractAmount(NM.parsePiped(parser, line, start, c, next));
            start = next + 1;
        }
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        while (start < stop) {
            final int next = getNext(line, start, r, stop);
            addContractPeriod(NM.parsePiped(parser, line, start, c, next));
            start = next + 1;
        }
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.interestCode = CWE.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.transferToBadDebtCode = CWE.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.transferToBadDebtDate = DT.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.badDebtAgencyCode = CWE.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.badDebtTransferAmount = NM.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.badDebtRecoveryAmount = NM.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.deleteAccountIndicator = CWE.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.deleteAccountDate = DT.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.dischargeDisposition = CWE.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.dischargedToLocation = DLD.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.dietType = CE.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.servicingFacility = CWE.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.bedStatus = getToken(line, start, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.accountStatus = CWE.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.pendingLocation = PL.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.priorTemporaryLocation = PL.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.admitDateTime = TS.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        while (start < stop) {
            final int next = getNext(line, start, r, stop);
            addDischargeDateTime(TS.parsePiped(parser, line, start, c, next));
            start = next + 1;
        }
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.currentPatientBalance = NM.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.totalCharges = NM.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.totalAdjustments = NM.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.totalPayments = NM.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.alternateVisitID = CX.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.visitIndicator = CWE.parsePiped(parser, line, start, c, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        while (start < stop) {
            final int next = getNext(line, start, r, stop);
            addOtherHealthcareProvider(XCN.parsePiped(parser, line, start, c, next));
            start = next + 1;
        }
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.serviceEpisodeDescription = getToken(line, start, stop);
        
        start = stop + 1;
        stop = getNext(line, start, f);
        if (stop < start) {
            return;
        }
        this.serviceEpisodeIdentifier = CX.parsePiped(parser, line, start, c, stop);
        
        assertLast(line, stop + 1, f, line.length());
    }
    
    @Override
    public void toPiped(final Writer w) throws IOException {
        int last = startSegment(w, PV1_XML);
        
        last = addField(w, this.setIDPV1, last, 1);
        last = addField(w, this.patientClass, last, 2);
        last = addField(w, this.assignedPatientLocation, last, 3);
        last = addField(w, this.admissionType, last, 4);
        last = addField(w, this.preadmitNumber, last, 5);
        last = addField(w, this.priorPatientLocation, last, 6);
        last = addField(w, this.attendingDoctor, last, 7);
        last = addField(w, this.referringDoctor, last, 8);
        last = addField(w, this.consultingDoctor, last, 9);
        last = addField(w, this.hospitalService, last, 10);
        last = addField(w, this.temporaryLocation, last, 11);
        last = addField(w, this.preadmitTestIndicator, last, 12);
        last = addField(w, this.reAdmissionIndicator, last, 13);
        last = addField(w, this.admitSource, last, 14);
        last = addField(w, this.ambulatoryStatus, last, 15);
        last = addField(w, this.vipIndicator, last, 16);
        last = addField(w, this.admittingDoctor, last, 17);
        last = addField(w, this.patientType, last, 18);
        last = addField(w, this.visitNumber, last, 19);
        last = addField(w, this.financialClass, last, 20);
        last = addField(w, this.chargePriceIndicator, last, 21);
        last = addField(w, this.courtesyCode, last, 22);
        last = addField(w, this.creditRating, last, 23);
        last = addField(w, this.contractCode, last, 24);
        last = addField(w, this.contractEffectiveDate, last, 25);
        last = addField(w, this.contractAmount, last, 26);
        last = addField(w, this.contractPeriod, last, 27);
        last = addField(w, this.interestCode, last, 28);
        last = addField(w, this.transferToBadDebtCode, last, 29);
        last = addField(w, this.transferToBadDebtDate, last, 30);
        last = addField(w, this.badDebtAgencyCode, last, 31);
        last = addField(w, this.badDebtTransferAmount, last, 32);
        last = addField(w, this.badDebtRecoveryAmount, last, 33);
        last = addField(w, this.deleteAccountIndicator, last, 34);
        last = addField(w, this.deleteAccountDate, last, 35);
        last = addField(w, this.dischargeDisposition, last, 36);
        last = addField(w, this.dischargedToLocation, last, 37);
        last = addField(w, this.dietType, last, 38);
        last = addField(w, this.servicingFacility, last, 39);
        last = addField(w, this.bedStatus, last, 40);
        last = addField(w, this.accountStatus, last, 41);
        last = addField(w, this.pendingLocation, last, 42);
        last = addField(w, this.priorTemporaryLocation, last, 43);
        last = addField(w, this.admitDateTime, last, 44);
        last = addField(w, this.dischargeDateTime, last, 45);
        last = addField(w, this.currentPatientBalance, last, 46);
        last = addField(w, this.totalCharges, last, 47);
        last = addField(w, this.totalAdjustments, last, 48);
        last = addField(w, this.totalPayments, last, 49);
        last = addField(w, this.alternateVisitID, last, 50);
        last = addField(w, this.visitIndicator, last, 51);
        last = addField(w, this.otherHealthcareProvider, last, 52);
        last = addField(w, this.serviceEpisodeDescription, last, 53);
        last = addField(w, this.serviceEpisodeIdentifier, last, 54);
        
        endSegment(w);
    }
    
    /**
     * Retrieves the field at the given index
     * 
     * @param i the index
     * @return the field
     **/
    @Override
    public Object get(final int i) {
        switch (i) {
            case 1:
                return this.setIDPV1;
            case 2:
                return this.patientClass;
            case 3:
                return this.assignedPatientLocation;
            case 4:
                return this.admissionType;
            case 5:
                return this.preadmitNumber;
            case 6:
                return this.priorPatientLocation;
            case 7:
                return this.attendingDoctor;
            case 8:
                return this.referringDoctor;
            case 9:
                return this.consultingDoctor;
            case 10:
                return this.hospitalService;
            case 11:
                return this.temporaryLocation;
            case 12:
                return this.preadmitTestIndicator;
            case 13:
                return this.reAdmissionIndicator;
            case 14:
                return this.admitSource;
            case 15:
                return this.ambulatoryStatus;
            case 16:
                return this.vipIndicator;
            case 17:
                return this.admittingDoctor;
            case 18:
                return this.patientType;
            case 19:
                return this.visitNumber;
            case 20:
                return this.financialClass;
            case 21:
                return this.chargePriceIndicator;
            case 22:
                return this.courtesyCode;
            case 23:
                return this.creditRating;
            case 24:
                return this.contractCode;
            case 25:
                return this.contractEffectiveDate;
            case 26:
                return this.contractAmount;
            case 27:
                return this.contractPeriod;
            case 28:
                return this.interestCode;
            case 29:
                return this.transferToBadDebtCode;
            case 30:
                return this.transferToBadDebtDate;
            case 31:
                return this.badDebtAgencyCode;
            case 32:
                return this.badDebtTransferAmount;
            case 33:
                return this.badDebtRecoveryAmount;
            case 34:
                return this.deleteAccountIndicator;
            case 35:
                return this.deleteAccountDate;
            case 36:
                return this.dischargeDisposition;
            case 37:
                return this.dischargedToLocation;
            case 38:
                return this.dietType;
            case 39:
                return this.servicingFacility;
            case 40:
                return this.bedStatus;
            case 41:
                return this.accountStatus;
            case 42:
                return this.pendingLocation;
            case 43:
                return this.priorTemporaryLocation;
            case 44:
                return this.admitDateTime;
            case 45:
                return this.dischargeDateTime;
            case 46:
                return this.currentPatientBalance;
            case 47:
                return this.totalCharges;
            case 48:
                return this.totalAdjustments;
            case 49:
                return this.totalPayments;
            case 50:
                return this.alternateVisitID;
            case 51:
                return this.visitIndicator;
            case 52:
                return this.otherHealthcareProvider;
            case 53:
                return this.serviceEpisodeDescription;
            case 54:
                return this.serviceEpisodeIdentifier;
        }
        throw new ArrayIndexOutOfBoundsException(i);
    }
    
    /**
     * Retrieves the account status
     * 
     * @return the account status
     **/
    public CWE getAccountStatus() {
        return this.accountStatus;
    }
    
    /**
     * Retrieves the visit number
     * 
     * @return the visit number
     **/
    public CX getVisitNumber() {
        return this.visitNumber;
    }
    
    /**
     * Retrieves the visit indicator
     * 
     * @return the visit indicator
     **/
    public CWE getVisitIndicator() {
        return this.visitIndicator;
    }
    
    /**
     * Retrieves the VIP indicator
     * 
     * @return the VIP indicator
     **/
    public CWE getVipIndicator() {
        return this.vipIndicator;
    }
    
    /**
     * Retrieves the transfer to bad debt date
     * 
     * @return the transfer to bad debt date
     **/
    public DT getTransferToBadDebtDate() {
        return this.transferToBadDebtDate;
    }
    
    /**
     * Retrieves the transfer to bad debt code
     * 
     * @return the transfer to bad debt code
     **/
    public CWE getTransferToBadDebtCode() {
        return this.transferToBadDebtCode;
    }
    
    /**
     * Retrieves the total payments
     * 
     * @return the total payments
     **/
    public NM getTotalPayments() {
        return this.totalPayments;
    }
    
    /**
     * Retrieves the total charges
     * 
     * @return the total charges
     **/
    public NM getTotalCharges() {
        return this.totalCharges;
    }
    
    /**
     * Retrieves the total adjustments
     * 
     * @return the total adjustments
     **/
    public NM getTotalAdjustments() {
        return this.totalAdjustments;
    }
    
    /**
     * Retrieves the temporary location
     * 
     * @return the temporary location
     **/
    public PL getTemporaryLocation() {
        return this.temporaryLocation;
    }
    
    /**
     * Retrieves the set ID PV1
     * 
     * @return the set ID PV1
     **/
    public SI getSetIDPV1() {
        return this.setIDPV1;
    }
    
    /**
     * Retrieves the servicing facility
     * 
     * @return the servicing facility
     **/
    public CWE getServicingFacility() {
        return this.servicingFacility;
    }
    
    /**
     * Retrieves the referring doctor
     * 
     * @return the referring doctor
     **/
    public List<XCN> getReferringDoctor() {
        return this.referringDoctor;
    }
    
    /**
     * Retrieves the re-admission indicator
     * 
     * @return the re-admission indicator
     **/
    public CWE getReAdmissionIndicator() {
        return this.reAdmissionIndicator;
    }
    
    /**
     * Retrieves the prior temporary location
     * 
     * @return the prior temporary location
     **/
    public PL getPriorTemporaryLocation() {
        return this.priorTemporaryLocation;
    }
    
    /**
     * Retrieves the prior patient location
     * 
     * @return the prior patient location
     **/
    public PL getPriorPatientLocation() {
        return this.priorPatientLocation;
    }
    
    /**
     * Retrieves the preadmit test indicator
     * 
     * @return the preadmit test indicator
     **/
    public CWE getPreadmitTestIndicator() {
        return this.preadmitTestIndicator;
    }
    
    /**
     * Retrieves the preadmit number
     * 
     * @return the preadmit number
     **/
    public CX getPreadmitNumber() {
        return this.preadmitNumber;
    }
    
    /**
     * Retrieves the pending location
     * 
     * @return the pending location
     **/
    public PL getPendingLocation() {
        return this.pendingLocation;
    }
    
    /**
     * Retrieves the patient type
     * 
     * @return the patient type
     **/
    public CWE getPatientType() {
        return this.patientType;
    }
    
    /**
     * Retrieves the patient class
     * 
     * @return the patient class
     **/
    public CWE getPatientClass() {
        return this.patientClass;
    }
    
    /**
     * Retrieves the other healthcare provider
     * 
     * @return the other healthcare provider
     **/
    public List<XCN> getOtherHealthcareProvider() {
        return this.otherHealthcareProvider;
    }
    
    /**
     * Retrieves the interest code
     * 
     * @return the interest code
     **/
    public CWE getInterestCode() {
        return this.interestCode;
    }
    
    /**
     * Retrieves the hospital service
     * 
     * @return the hospital service
     **/
    public CWE getHospitalService() {
        return this.hospitalService;
    }
    
    /**
     * Retrieves the financial class
     * 
     * @return the financial class
     **/
    public List<FC> getFinancialClass() {
        return this.financialClass;
    }
    
    /**
     * Retrieves the discharged to location
     * 
     * @return the discharged to location
     **/
    public DLD getDischargedToLocation() {
        return this.dischargedToLocation;
    }
    
    /**
     * Retrieves the discharge disposition
     * 
     * @return the discharge disposition
     **/
    public CWE getDischargeDisposition() {
        return this.dischargeDisposition;
    }
    
    /**
     * Retrieves the discharge date/time
     * 
     * @return the discharge date/time
     **/
    public List<TS> getDischargeDateTime() {
        return this.dischargeDateTime;
    }
    
    /**
     * Retrieves the diet type
     * 
     * @return the diet type
     **/
    public CE getDietType() {
        return this.dietType;
    }
    
    /**
     * Retrieves the delete account indicator
     * 
     * @return the delete account indicator
     **/
    public CWE getDeleteAccountIndicator() {
        return this.deleteAccountIndicator;
    }
    
    /**
     * Retrieves the delete account date
     * 
     * @return the delete account date
     **/
    public DT getDeleteAccountDate() {
        return this.deleteAccountDate;
    }
    
    /**
     * Retrieves the current patient balance
     * 
     * @return the current patient balance
     **/
    public NM getCurrentPatientBalance() {
        return this.currentPatientBalance;
    }
    
    /**
     * Retrieves the credit rating
     * 
     * @return the credit rating
     **/
    public CWE getCreditRating() {
        return this.creditRating;
    }
    
    /**
     * Retrieves the courtesy code
     * 
     * @return the courtesy code
     **/
    public CWE getCourtesyCode() {
        return this.courtesyCode;
    }
    
    /**
     * Retrieves the contract period
     * 
     * @return the contract period
     **/
    public List<NM> getContractPeriod() {
        return this.contractPeriod;
    }
    
    /**
     * Retrieves the contract effective date
     * 
     * @return the contract effective date
     **/
    public List<DT> getContractEffectiveDate() {
        return this.contractEffectiveDate;
    }
    
    /**
     * Retrieves the contract code
     * 
     * @return the contract code
     **/
    public List<CWE> getContractCode() {
        return this.contractCode;
    }
    
    /**
     * Retrieves the contract amount
     * 
     * @return the contract amount
     **/
    public List<NM> getContractAmount() {
        return this.contractAmount;
    }
    
    /**
     * Retrieves the consulting doctor
     * 
     * @return the consulting doctor
     **/
    public List<XCN> getConsultingDoctor() {
        return this.consultingDoctor;
    }
    
    /**
     * Retrieves the charge price indicator
     * 
     * @return the charge price indicator
     **/
    public CWE getChargePriceIndicator() {
        return this.chargePriceIndicator;
    }
    
    /**
     * Retrieves the bed status
     * 
     * @return the bed status
     **/
    public String getBedStatus() {
        return this.bedStatus;
    }
    
    /**
     * Retrieves the bad debt transfer amount
     * 
     * @return the bad debt transfer amount
     **/
    public NM getBadDebtTransferAmount() {
        return this.badDebtTransferAmount;
    }
    
    /**
     * Retrieves the bad debt recovery amount
     * 
     * @return the bad debt recovery amount
     **/
    public NM getBadDebtRecoveryAmount() {
        return this.badDebtRecoveryAmount;
    }
    
    /**
     * Retrieves the bad debt agency code
     * 
     * @return the bad debt agency code
     **/
    public CWE getBadDebtAgencyCode() {
        return this.badDebtAgencyCode;
    }
    
    /**
     * Retrieves the attending doctor
     * 
     * @return the attending doctor
     **/
    public List<XCN> getAttendingDoctor() {
        return this.attendingDoctor;
    }
    
    /**
     * Retrieves the assigned patient location
     * 
     * @return the assigned patient location
     **/
    public PL getAssignedPatientLocation() {
        return this.assignedPatientLocation;
    }
    
    /**
     * Retrieves the ambulatory status
     * 
     * @return the ambulatory status
     **/
    public List<CWE> getAmbulatoryStatus() {
        return this.ambulatoryStatus;
    }
    
    /**
     * Retrieves the alternate visit ID
     * 
     * @return the alternate visit ID
     **/
    public CX getAlternateVisitID() {
        return this.alternateVisitID;
    }
    
    /**
     * Retrieves the admitting doctor
     * 
     * @return the admitting doctor
     **/
    public List<XCN> getAdmittingDoctor() {
        return this.admittingDoctor;
    }
    
    /**
     * Retrieves the admit source
     * 
     * @return the admit source
     **/
    public CWE getAdmitSource() {
        return this.admitSource;
    }
    
    /**
     * Retrieves the admit date/time
     * 
     * @return the admit date/time
     **/
    public TS getAdmitDateTime() {
        return this.admitDateTime;
    }
    
    /**
     * Retrieves the admission type
     * 
     * @return the admission type
     **/
    public CWE getAdmissionType() {
        return this.admissionType;
    }
    
    /**
     * Retrieves the service episode description
     * 
     * @return the service episode description
     **/
    public String getServiceEpisodeDescription() {
        return this.serviceEpisodeDescription;
    }
    
    /**
     * Retrieves the service episode identifier
     * 
     * @return the service episode identifier
     **/
    public CX getServiceEpisodeIdentifier() {
        return this.serviceEpisodeIdentifier;
    }
    
    /**
     * Modifies the account status
     * 
     * @param accountStatus the new account status
     **/
    public void setAccountStatus(final CWE accountStatus) {
        this.accountStatus = accountStatus;
    }
    
    /**
     * Modifies the visit number
     * 
     * @param visitNumber the new visit number
     **/
    public void setVisitNumber(final CX visitNumber) {
        this.visitNumber = visitNumber;
    }
    
    /**
     * Modifies the visit indicator
     * 
     * @param visitIndicator the new visit indicator
     **/
    public void setVisitIndicator(final CWE visitIndicator) {
        this.visitIndicator = visitIndicator;
    }
    
    /**
     * Modifies the VIP indicator
     * 
     * @param vipIndicator the new VIP indicator
     **/
    public void setVipIndicator(final CWE vipIndicator) {
        this.vipIndicator = vipIndicator;
    }
    
    /**
     * Modifies the transfer to bad debt date
     * 
     * @param transferToBadDebtDate the new transfer to bad debt date
     **/
    public void setTransferToBadDebtDate(final DT transferToBadDebtDate) {
        this.transferToBadDebtDate = transferToBadDebtDate;
    }
    
    /**
     * Modifies the transfer to bad debt code
     * 
     * @param transferToBadDebtCode the new transfer to bad debt code
     **/
    public void setTransferToBadDebtCode(final CWE transferToBadDebtCode) {
        this.transferToBadDebtCode = transferToBadDebtCode;
    }
    
    /**
     * Modifies the total payments
     * 
     * @param totalPayments the new total payments
     **/
    public void setTotalPayments(final NM totalPayments) {
        this.totalPayments = totalPayments;
    }
    
    /**
     * Modifies the total charges
     * 
     * @param totalCharges the new total charges
     **/
    public void setTotalCharges(final NM totalCharges) {
        this.totalCharges = totalCharges;
    }
    
    /**
     * Modifies the total adjustments
     * 
     * @param totalAdjustments the new total adjustments
     **/
    public void setTotalAdjustments(final NM totalAdjustments) {
        this.totalAdjustments = totalAdjustments;
    }
    
    /**
     * Modifies the temporary location
     * 
     * @param temporaryLocation the new temporary location
     **/
    public void setTemporaryLocation(final PL temporaryLocation) {
        this.temporaryLocation = temporaryLocation;
    }
    
    /**
     * Modifies the set ID PV1
     * 
     * @param setIDPV1 the new set ID PV1
     **/
    public void setSetIDPV1(final SI setIDPV1) {
        this.setIDPV1 = setIDPV1;
    }
    
    /**
     * Modifies the servicing facility
     * 
     * @param servicingFacility the new servicing facility
     **/
    public void setServicingFacility(final CWE servicingFacility) {
        this.servicingFacility = servicingFacility;
    }
    
    /**
     * Modifies the referring doctor
     * 
     * @param referringDoctor the new referring doctor
     **/
    public void setReferringDoctor(final List<XCN> referringDoctor) {
        this.referringDoctor = referringDoctor;
    }
    
    /**
     * Modifies the referring doctor
     * 
     * @param referringDoctor the new referring doctor
     * @return the new referring doctor
     **/
    public XCN addReferringDoctor(final XCN referringDoctor) {
        this.referringDoctor = Util.add(this.referringDoctor, referringDoctor);
        return referringDoctor;
    }
    
    /**
     * Modifies the re-admission indicator
     * 
     * @param reAdmissionIndicator the new re-admission indicator
     **/
    public void setReAdmissionIndicator(final CWE reAdmissionIndicator) {
        this.reAdmissionIndicator = reAdmissionIndicator;
    }
    
    /**
     * Modifies the prior temporary location
     * 
     * @param priorTemporaryLocation the new prior temporary location
     **/
    public void setPriorTemporaryLocation(final PL priorTemporaryLocation) {
        this.priorTemporaryLocation = priorTemporaryLocation;
    }
    
    /**
     * Modifies the prior patient location
     * 
     * @param priorPatientLocation the new prior patient location
     **/
    public void setPriorPatientLocation(final PL priorPatientLocation) {
        this.priorPatientLocation = priorPatientLocation;
    }
    
    /**
     * Modifies the preadmit test indicator
     * 
     * @param preadmitTestIndicator the new preadmit test indicator
     **/
    public void setPreadmitTestIndicator(final CWE preadmitTestIndicator) {
        this.preadmitTestIndicator = preadmitTestIndicator;
    }
    
    /**
     * Modifies the preadmit number
     * 
     * @param preadmitNumber the new preadmit number
     **/
    public void setPreadmitNumber(final CX preadmitNumber) {
        this.preadmitNumber = preadmitNumber;
    }
    
    /**
     * Modifies the pending location
     * 
     * @param pendingLocation the new pending location
     **/
    public void setPendingLocation(final PL pendingLocation) {
        this.pendingLocation = pendingLocation;
    }
    
    /**
     * Modifies the patient type
     * 
     * @param patientType the new patient type
     **/
    public void setPatientType(final CWE patientType) {
        this.patientType = patientType;
    }
    
    /**
     * Modifies the patient class
     * 
     * @param patientClass the new patient class
     **/
    public void setPatientClass(final CWE patientClass) {
        this.patientClass = patientClass;
    }
    
    /**
     * Modifies the other healthcare provider
     * 
     * @param otherHealthcareProvider the new other healthcare provider
     **/
    public void setOtherHealthcareProvider(final List<XCN> otherHealthcareProvider) {
        this.otherHealthcareProvider = otherHealthcareProvider;
    }
    
    /**
     * Modifies the other healthcare provider
     * 
     * @param otherHealthcareProvider the new other healthcare provider
     * @return the new other healthcare provider
     **/
    public XCN addOtherHealthcareProvider(final XCN otherHealthcareProvider) {
        this.otherHealthcareProvider = Util.add(this.otherHealthcareProvider, otherHealthcareProvider);
        return otherHealthcareProvider;
    }
    
    /**
     * Modifies the interest code
     * 
     * @param interestCode the new interest code
     **/
    public void setInterestCode(final CWE interestCode) {
        this.interestCode = interestCode;
    }
    
    /**
     * Modifies the hospital service
     * 
     * @param hospitalService the new hospital service
     **/
    public void setHospitalService(final CWE hospitalService) {
        this.hospitalService = hospitalService;
    }
    
    /**
     * Modifies the financial class
     * 
     * @param financialClass the new financial class
     **/
    public void setFinancialClass(final List<FC> financialClass) {
        this.financialClass = financialClass;
    }
    
    /**
     * Modifies the financial class
     * 
     * @param financialClass the new financial class
     * @return the new financial class
     **/
    public FC addFinancialClass(final FC financialClass) {
        this.financialClass = Util.add(this.financialClass, financialClass);
        return financialClass;
    }
    
    /**
     * Modifies the discharged to location
     * 
     * @param dischargedToLocation the new discharged to location
     **/
    public void setDischargedToLocation(final DLD dischargedToLocation) {
        this.dischargedToLocation = dischargedToLocation;
    }
    
    /**
     * Modifies the discharge disposition
     * 
     * @param dischargeDisposition the new discharge disposition
     **/
    public void setDischargeDisposition(final CWE dischargeDisposition) {
        this.dischargeDisposition = dischargeDisposition;
    }
    
    /**
     * Modifies the discharge date/time
     * 
     * @param dischargeDateTime the new discharge date/time
     **/
    public void setDischargeDateTime(final List<TS> dischargeDateTime) {
        this.dischargeDateTime = dischargeDateTime;
    }
    
    /**
     * Modifies the discharge date/time
     * 
     * @param dischargeDateTime the new discharge date/time
     * @return the new discharge date/time
     **/
    public TS addDischargeDateTime(final TS dischargeDateTime) {
        this.dischargeDateTime = Util.add(this.dischargeDateTime, dischargeDateTime);
        return dischargeDateTime;
    }
    
    /**
     * Modifies the diet type
     * 
     * @param dietType the new diet type
     **/
    public void setDietType(final CE dietType) {
        this.dietType = dietType;
    }
    
    /**
     * Modifies the delete account indicator
     * 
     * @param deleteAccountIndicator the new delete account indicator
     **/
    public void setDeleteAccountIndicator(final CWE deleteAccountIndicator) {
        this.deleteAccountIndicator = deleteAccountIndicator;
    }
    
    /**
     * Modifies the delete account date
     * 
     * @param deleteAccountDate the new delete account date
     **/
    public void setDeleteAccountDate(final DT deleteAccountDate) {
        this.deleteAccountDate = deleteAccountDate;
    }
    
    /**
     * Modifies the current patient balance
     * 
     * @param currentPatientBalance the new current patient balance
     **/
    public void setCurrentPatientBalance(final NM currentPatientBalance) {
        this.currentPatientBalance = currentPatientBalance;
    }
    
    /**
     * Modifies the credit rating
     * 
     * @param creditRating the new credit rating
     **/
    public void setCreditRating(final CWE creditRating) {
        this.creditRating = creditRating;
    }
    
    /**
     * Modifies the courtesy code
     * 
     * @param courtesyCode the new courtesy code
     **/
    public void setCourtesyCode(final CWE courtesyCode) {
        this.courtesyCode = courtesyCode;
    }
    
    /**
     * Modifies the contract period
     * 
     * @param contractPeriod the new contract period
     **/
    public void setContractPeriod(final List<NM> contractPeriod) {
        this.contractPeriod = contractPeriod;
    }
    
    /**
     * Modifies the contract period
     * 
     * @param contractPeriod the new contract period
     * @return the new contract period
     **/
    public NM addContractPeriod(final NM contractPeriod) {
        this.contractPeriod = Util.add(this.contractPeriod, contractPeriod);
        return contractPeriod;
    }
    
    /**
     * Modifies the contract effective date
     * 
     * @param contractEffectiveDate the new contract effective date
     **/
    public void setContractEffectiveDate(final List<DT> contractEffectiveDate) {
        this.contractEffectiveDate = contractEffectiveDate;
    }
    
    /**
     * Modifies the contract effective date
     * 
     * @param contractEffectiveDate the new contract effective date
     * @return the new contract effective date
     **/
    public DT addContractEffectiveDate(final DT contractEffectiveDate) {
        this.contractEffectiveDate = Util.add(this.contractEffectiveDate, contractEffectiveDate);
        return contractEffectiveDate;
    }
    
    /**
     * Modifies the contract code
     * 
     * @param contractCode the new contract code
     **/
    public void setContractCodeCE(final List<CWE> contractCode) {
        this.contractCode = contractCode;
    }
    
    /**
     * Modifies the contract code
     * 
     * @param contractCode the new contract code
     * @return the new contract code
     **/
    public CWE addContractCode(final CWE contractCode) {
        this.contractCode = Util.add(this.contractCode, contractCode);
        return contractCode;
    }
    
    /**
     * Modifies the contract amount
     * 
     * @param contractAmount the new contract amount
     **/
    public void setContractAmount(final List<NM> contractAmount) {
        this.contractAmount = contractAmount;
    }
    
    /**
     * Modifies the contract amount
     * 
     * @param contractAmount the new contract amount
     * @return the new contract amount
     **/
    public NM addContractAmount(final NM contractAmount) {
        this.contractAmount = Util.add(this.contractAmount, contractAmount);
        return contractAmount;
    }
    
    /**
     * Modifies the consulting doctor
     * 
     * @param consultingDoctor the new consulting doctor
     **/
    public void setConsultingDoctor(final List<XCN> consultingDoctor) {
        this.consultingDoctor = consultingDoctor;
    }
    
    /**
     * Modifies the consulting doctor
     * 
     * @param consultingDoctor the new consulting doctor
     * @return the new consulting doctor
     **/
    public XCN addConsultingDoctor(final XCN consultingDoctor) {
        this.consultingDoctor = Util.add(this.consultingDoctor, consultingDoctor);
        return consultingDoctor;
    }
    
    /**
     * Modifies the charge price indicator
     * 
     * @param chargePriceIndicator the new charge price indicator
     **/
    public void setChargePriceIndicator(final CWE chargePriceIndicator) {
        this.chargePriceIndicator = chargePriceIndicator;
    }
    
    /**
     * Modifies the bed status
     * 
     * @param bedStatus the new bed status
     **/
    public void setBedStatus(final String bedStatus) {
        this.bedStatus = bedStatus;
    }
    
    /**
     * Modifies the bad debt transfer amount
     * 
     * @param badDebtTransferAmount the new bad debt transfer amount
     **/
    public void setBadDebtTransferAmount(final NM badDebtTransferAmount) {
        this.badDebtTransferAmount = badDebtTransferAmount;
    }
    
    /**
     * Modifies the bad debt recovery amount
     * 
     * @param badDebtRecoveryAmount the new bad debt recovery amount
     **/
    public void setBadDebtRecoveryAmount(final NM badDebtRecoveryAmount) {
        this.badDebtRecoveryAmount = badDebtRecoveryAmount;
    }
    
    /**
     * Modifies the bad debt agency code
     * 
     * @param badDebtAgencyCode the new bad debt agency code
     **/
    public void setBadDebtAgencyCode(final CWE badDebtAgencyCode) {
        this.badDebtAgencyCode = badDebtAgencyCode;
    }
    
    /**
     * Modifies the attending doctor
     * 
     * @param attendingDoctor the new attending doctor
     **/
    public void setAttendingDoctor(final List<XCN> attendingDoctor) {
        this.attendingDoctor = attendingDoctor;
    }
    
    /**
     * Modifies the attending doctor
     * 
     * @param attendingDoctor the new attending doctor
     * @return the new attending doctor
     **/
    public XCN addAttendingDoctor(final XCN attendingDoctor) {
        this.attendingDoctor = Util.add(this.attendingDoctor, attendingDoctor);
        return attendingDoctor;
    }
    
    /**
     * Modifies the assigned patient location
     * 
     * @param assignedPatientLocation the new assigned patient location
     **/
    public void setAssignedPatientLocation(final PL assignedPatientLocation) {
        this.assignedPatientLocation = assignedPatientLocation;
    }
    
    /**
     * Modifies the ambulatory status
     * 
     * @param ambulatoryStatus the new ambulatory status
     **/
    public void setAmbulatoryStatusCE(final List<CWE> ambulatoryStatus) {
        this.ambulatoryStatus = ambulatoryStatus;
    }
    
    /**
     * Modifies the ambulatory status
     * 
     * @param ambulatoryStatus the new ambulatory status
     * @return the new ambulatory status
     **/
    public CWE addAmbulatoryStatus(final CWE ambulatoryStatus) {
        this.ambulatoryStatus = Util.add(this.ambulatoryStatus, ambulatoryStatus);
        return ambulatoryStatus;
    }
    
    /**
     * Modifies the alternate visit ID
     * 
     * @param alternateVisitID the new alternate visit ID
     **/
    public void setAlternateVisitID(final CX alternateVisitID) {
        this.alternateVisitID = alternateVisitID;
    }
    
    /**
     * Modifies the admitting doctor
     * 
     * @param admittingDoctor the new admitting doctor
     **/
    public void setAdmittingDoctor(final List<XCN> admittingDoctor) {
        this.admittingDoctor = admittingDoctor;
    }
    
    /**
     * Modifies the admitting doctor
     * 
     * @param admittingDoctor the new admitting doctor
     * @return the new admitting doctor
     **/
    public XCN addAdmittingDoctor(final XCN admittingDoctor) {
        this.admittingDoctor = Util.add(this.admittingDoctor, admittingDoctor);
        return admittingDoctor;
    }
    
    /**
     * Modifies the admit source
     * 
     * @param admitSource the new admit source
     **/
    public void setAdmitSource(final CWE admitSource) {
        this.admitSource = admitSource;
    }
    
    /**
     * Modifies the admit date/time
     * 
     * @param admitDateTime the new admit date/time
     **/
    public void setAdmitDateTime(final TS admitDateTime) {
        this.admitDateTime = admitDateTime;
    }
    
    /**
     * Modifies the admission type
     * 
     * @param admissionType the new admission type
     **/
    public void setAdmissionType(final CWE admissionType) {
        this.admissionType = admissionType;
    }
    
    /**
     * Modifies the service episode description
     * 
     * @param serviceEpisodeDescription the new service episode description
     **/
    public void setServiceEpisodeDescription(final String serviceEpisodeDescription) {
        this.serviceEpisodeDescription = serviceEpisodeDescription;
    }
    
    /**
     * Modifies the service episode identifier
     * 
     * @param serviceEpisodeIdentifier the new service episode identifier
     **/
    public void setServiceEpisodeIdentifier(final CX serviceEpisodeIdentifier) {
        this.serviceEpisodeIdentifier = serviceEpisodeIdentifier;
    }
    
    /**
     * Clears the PV1
     **/
    @Override
    public void clear() {
        super.clear();
        this.setIDPV1 = null;
        this.patientClass = null;
        this.assignedPatientLocation = null;
        this.admissionType = null;
        this.preadmitNumber = null;
        this.priorPatientLocation = null;
        Util.clear(this.attendingDoctor);
        Util.clear(this.referringDoctor);
        Util.clear(this.consultingDoctor);
        this.hospitalService = null;
        this.temporaryLocation = null;
        this.preadmitTestIndicator = null;
        this.reAdmissionIndicator = null;
        this.admitSource = null;
        Util.clear(this.ambulatoryStatus);
        this.vipIndicator = null;
        Util.clear(this.admittingDoctor);
        this.patientType = null;
        this.visitNumber = null;
        Util.clear(this.financialClass);
        this.chargePriceIndicator = null;
        this.courtesyCode = null;
        this.creditRating = null;
        Util.clear(this.contractCode);
        Util.clear(this.contractEffectiveDate);
        Util.clear(this.contractAmount);
        Util.clear(this.contractPeriod);
        this.interestCode = null;
        this.transferToBadDebtCode = null;
        this.transferToBadDebtDate = null;
        this.badDebtAgencyCode = null;
        this.badDebtTransferAmount = null;
        this.badDebtRecoveryAmount = null;
        this.deleteAccountIndicator = null;
        this.deleteAccountDate = null;
        this.dischargeDisposition = null;
        this.dischargedToLocation = null;
        this.dietType = null;
        this.servicingFacility = null;
        this.bedStatus = null;
        this.accountStatus = null;
        this.pendingLocation = null;
        this.priorTemporaryLocation = null;
        this.admitDateTime = null;
        Util.clear(this.dischargeDateTime);
        this.currentPatientBalance = null;
        this.totalCharges = null;
        this.totalAdjustments = null;
        this.totalPayments = null;
        this.alternateVisitID = null;
        this.visitIndicator = null;
        Util.clear(this.otherHealthcareProvider);
        this.serviceEpisodeDescription = null;
        this.serviceEpisodeIdentifier = null;
    }
}
