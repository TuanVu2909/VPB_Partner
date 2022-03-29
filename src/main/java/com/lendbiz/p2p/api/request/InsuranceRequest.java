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
    private String pv_fee;
    private String pv_beneficiaryFullName;
    private String pv_beneficiaryBirthDate;


    private String pv_beneficiaryIdNumber;
    private String pv_RelationId;
    private String pv_isSick;
    private String pv_isTreatedIn3Years;
    private String pv_isTreatedNext12Months;
    private String pv_isTreatedSpecialIn3Years;


    private String pv_isRejectInsurance;
    private String pv_isNormal;
    private String pv_isConfirm ;
    private String pv_insuredPersonFullName;
    private String pv_insuredPersonBirthDate;
    private String pv_insuredPersonGender;


    private String pv_insuredPersonIdNumber;
    private String pv_insuredPersonMobile;
    private String pv_insuredPersonEmail;
    private String pv_insuredPersonAddress;
    private String pv_ParentInsuranceCode;
    private String pv_InsuredRelationId;


    private String pv_insuredPersonNationality;


}
