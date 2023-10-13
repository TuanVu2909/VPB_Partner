package com.lendbiz.p2p.api.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@Data
@ToString
public class InsuranceRequest {
    private String pv_custId;
    private String pv_packageId;
    private String pv_startDate;
    private double pv_fee;
    private String pv_beneficiaryFullName;
    private String pv_beneficiaryBirthDate;

    private String pv_beneficiaryIdNumber;
    private String pv_RelationId;
    private String pv_isSick;
    private String pv_isTreatedIn3Years;
    private String pv_isTreatedNext12Months;
    private String pv_isTreatedSpecialIn3Years;

    private String pv_requireId;
    private String pv_isRejectInsurance;
    private String pv_isNormal;
    private String pv_isConfirm;
    private String pv_insuredPersonFullName;
    private String pv_insuredPersonBirthDate;
    private String pv_insuredPersonGender;

    private String pv_insuredPersonIdNumber;
    private String pv_insuredPersonMobile;
    private String pv_insuredPersonEmail;
    private String pv_insuredPersonAddress;
    private String pv_ParentInsuranceCode;
    private String pv_InsuredRelationId;
    private String pv_insuredPersonNationalityCode;
    private String pv_insuredPersonNationality;

    private String pv_isOutPatientFee;
    private String pv_isAccidentFee;
    private double pv_isAccidentFeeValue;
    private String pv_isLifeFee;
    private double pv_isLifeFeeValue;
    private String pv_isDentistryFee;
    private String pv_isPregnantFee;

    private double pv_isTotalFee;

    private String q1;
    private String q1Qestion;
    private String q1Desciption;
    private String q1QuestionId;
    private String q1QuestionNote;

    private String q2;
    private String q2Qestion;
    private String q2Desciption;
    private String q2QuestionId;
    private String q2QuestionNote;

    private String q3;
    private String q3Qestion;
    private String q3Desciption;
    private String q3QuestionId;
    private String q3QuestionNote;

    private String q4;
    private String q4Qestion;
    private String q4Desciption;
    private String q4QuestionId;
    private String q4QuestionNote;

    private String insuranceIdNumber;

    private String attachmentId;
    private String content;
    private String fileType;
    private String filename;
}
