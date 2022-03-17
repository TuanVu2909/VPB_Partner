package com.lendbiz.p2p.api.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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
        }),
})
@Getter
@Setter
public class NotifyEntity {
    @Id
    @Column(name = "pv_status")
    private String pStatus;

    @Column(name = "pv_des")
    private String des;
}
