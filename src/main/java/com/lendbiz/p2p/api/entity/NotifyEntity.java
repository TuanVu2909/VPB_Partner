package com.lendbiz.p2p.api.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "NotifyEntity.changeCoin", procedureName = "pck_gm.changeCoin", resultClasses = NotifyEntity.class, parameters = { //
                @StoredProcedureParameter(name = "pv_refcursor", mode = ParameterMode.REF_CURSOR, type = Void.class),
                @StoredProcedureParameter(name = "pv_custId", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "pv_coinAmount", mode = ParameterMode.IN, type = String.class),
           }) ,
        @NamedStoredProcedureQuery(name = "NotifyEntity.updateReferenceId", procedureName = "pck_gm.updateReferenceId", resultClasses = NotifyEntity.class, parameters = { //
                @StoredProcedureParameter(name = "pv_refcursor", mode = ParameterMode.REF_CURSOR, type = Void.class),
                @StoredProcedureParameter(name = "pv_custId", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "pv_refId", mode = ParameterMode.IN, type = String.class),
        }),
        @NamedStoredProcedureQuery(name = "NotifyEntity.createBear", procedureName = "pck_gm.createBear", resultClasses = NotifyEntity.class, parameters = { //
                @StoredProcedureParameter(name = "pv_refcursor", mode = ParameterMode.REF_CURSOR, type = Void.class),
                @StoredProcedureParameter(name = "pv_custId", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "pv_pid", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "pv_rate", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "pv_term", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "pv_amt", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "pv_contractId", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "pv_payType", mode = ParameterMode.IN, type = String.class),
        }),
        @NamedStoredProcedureQuery(name = "NotifyEntity.insert_trans9pay", procedureName = "PKG_API.insert_trans9pay", resultClasses = NotifyEntity.class, parameters = { //
                @StoredProcedureParameter(name = "pv_refcursor", mode = ParameterMode.REF_CURSOR, type = Void.class),
                @StoredProcedureParameter(name = "cid", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "tid", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "pid", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "pri", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "pstatus", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "scode", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "ccode", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "c_amount", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "c_phone", mode = ParameterMode.IN, type = String.class),
        }),
        @NamedStoredProcedureQuery(name = "NotifyEntity.createNavDaily", procedureName = "pck_gm.createNavDaily", resultClasses = NotifyEntity.class, parameters = { //
                @StoredProcedureParameter(name = "pv_refcursor", mode = ParameterMode.REF_CURSOR, type = Void.class),
                @StoredProcedureParameter(name = "pv_fundId", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "pv_nav", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "pv_navDate", mode = ParameterMode.IN, type = String.class),

        }),
        @NamedStoredProcedureQuery(name = "NotifyEntity.saveNavDaily", procedureName = "pkg_api.saveNavDaily", resultClasses = NotifyEntity.class, parameters = { //
                @StoredProcedureParameter(name = "pv_refcursor", mode = ParameterMode.REF_CURSOR, type = Void.class),
                @StoredProcedureParameter(name = "pv_fundCode", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "pv_growth", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "pv_fundDate", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "pv_pid", mode = ParameterMode.IN, type = String.class),
        }),
        @NamedStoredProcedureQuery(name = "NotifyEntity.createFundInvest", procedureName = "pck_gm.createFundInvest", resultClasses = NotifyEntity.class, parameters = { //
                @StoredProcedureParameter(name = "pv_refcursor", mode = ParameterMode.REF_CURSOR, type = Void.class),
                @StoredProcedureParameter(name = "pv_custId", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "pv_amt", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "pv_packageId", mode = ParameterMode.IN, type = String.class),

        }),

        @NamedStoredProcedureQuery(name = "NotifyEntity.createFundInvestOptionally", procedureName = "pck_gm.createFundInvestOptionally", resultClasses = NotifyEntity.class, parameters = { //
                @StoredProcedureParameter(name = "pv_refcursor", mode = ParameterMode.REF_CURSOR, type = Void.class),
                @StoredProcedureParameter(name = "pv_custId", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "pv_amt1", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "pv_amt2", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "pv_amt3", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "pv_amt4", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "pv_amt5", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "pv_amt6", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "pv_amt7", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "pv_amt8", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "pv_amt9", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "pv_amt10", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "pv_amt11", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "pv_amt12", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "pv_amt13", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "pv_amt14", mode = ParameterMode.IN, type = String.class),
        }),
        @NamedStoredProcedureQuery(name = "NotifyEntity.createInsurance", procedureName = "pck_gm.createInsurance", resultClasses = NotifyEntity.class, parameters = { //
                @StoredProcedureParameter(name = "pv_refcursor", mode = ParameterMode.REF_CURSOR, type = Void.class),
                @StoredProcedureParameter(name = "pv_custId", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "pv_packageId", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "pv_startDate", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "pv_fee", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "pv_beneficiaryFullName", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "pv_beneficiaryBirthDate", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "pv_beneficiaryIdNumber", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "pv_RelationId", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "pv_isSick", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "pv_isTreatedIn3Years", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "pv_isTreatedNext12Months", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "pv_isTreatedSpecialIn3Years", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "pv_isRejectInsurance", mode = ParameterMode.IN, type = String.class),


                @StoredProcedureParameter(name = "pv_isNormal", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "pv_isConfirm", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "pv_insuredPersonFullName", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "pv_insuredPersonBirthDate", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "pv_insuredPersonGender", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "pv_insuredPersonIdNumber", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "pv_insuredPersonMobile", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "pv_insuredPersonEmail", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "pv_insuredPersonAddress", mode = ParameterMode.IN, type = String.class),

                @StoredProcedureParameter(name = "pv_insuredPersonNationality", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "pv_ParentInsuranceCode", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "pv_InsuredRelationId", mode = ParameterMode.IN, type = String.class),

        }),
})
@Getter
@Setter
@ToString
public class NotifyEntity {
    @Id
    @Column(name = "pv_status")
    private String pStatus;

    @Column(name = "pv_des")
    private String des;
}
