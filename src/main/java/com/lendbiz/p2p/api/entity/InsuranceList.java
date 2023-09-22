package com.lendbiz.p2p.api.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NamedStoredProcedureQueries({ //
        @NamedStoredProcedureQuery(name = "InsuranceList.getInsuranceList", resultClasses = InsuranceList.class, procedureName = "pck_gm.getInsuranceList", parameters = { //
                @StoredProcedureParameter(name = "pv_refcursor", mode = ParameterMode.REF_CURSOR, type = Void.class),
                @StoredProcedureParameter(name = "pv_custId", mode = ParameterMode.IN, type = String.class) }) //
})
public class InsuranceList {
    @Id
    @Column(name = "ID")
    private String ID;

    @Column(name = "CUSTID")
    private String custId;

    @Column(name = "PACKAGENAME")
    private String packageName;

    @Column(name = "STARTDATE")
    private String startDate;

    @Column(name = "FEE")
    private String fee;

    @Column(name = "ISSICK")
    private String isSick;

    @Column(name = "BENEFICIARYNAME")
    private String beneficiaryName;

    @Column(name = "BENEFICARYRELATION")
    private String beneficiaryRelation;

    @Column(name = "INSUREDPERSONNAME")
    private String insuredPersonName;

    @Column(name = "INSUREDPERSONRELATION")
    private String insuredPersonRelation;

    @Column(name = "ISTREATEDIN3YEARS")
    private String isTreatedIn3Years;

    @Column(name = "ISTREATEDNEXT12MONTHS")
    private String isTreatedNext12Months;

    @Column(name = "ISTREATEDSPECIALIN3YEARS")
    private String isTreatedSpecialIn3Years;

    @Column(name = "ISREJECTINSURANCE")
    private String isRejectInsurance;

    @Column(name = "ISNORMAL")
    private String isNormal;

    @Column(name = "ISCONFIRM")
    private String isConfirm;

    @Column(name = "CREATEDDATE")
    private String createdDate;

    @Column(name = "REQUIREID")
    private String requireId;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "INSUREDPERSONID")
    private String insuredPersonId;

    @Column(name = "ISOUTPATIENTFEE")
    private String isOutPatientFee;

    @Column(name = "ISACCIDENTFEE")
    private String isAccidentFee;

    @Column(name = "ISLIFEFEE")
    private String isLifeFee;

    @Column(name = "ISDENTISTRYFEE")
    private String isDentistryFee;

    @Column(name = "ISPREGNANTFEE")
    private String isPregnantFee;

    @Column(name = "TOTALLIMIT")
    private String totalLimit;

    @Column(name = "ISOUTPATIENTTOTAL")
    private String isOutPatientTotal;

    @Column(name = "ISACCIDENTTOTAL")
    private String isAccidentTotal;

    @Column(name = "ISLIFETOTAL")
    private String isLifeTotal;

    @Column(name = "ISDENTISTRYTOTAL")
    private String isDentistryTotal;

    @Column(name = "ISPREGNANTTOTAL")
    private String isPregnantTotal;

}
