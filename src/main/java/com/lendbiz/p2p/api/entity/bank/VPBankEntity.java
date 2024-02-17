package com.lendbiz.p2p.api.entity.bank;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@NamedStoredProcedureQueries({
    @NamedStoredProcedureQuery(
        name = "VPBankEntity.insertNotify",
        procedureName = "PKG_VPB.insertNotify",
        resultClasses = VPBankEntity.class,
        parameters = {
            @StoredProcedureParameter(name = "pv_refcursor",        mode = ParameterMode.REF_CURSOR, type = Void.class),
            @StoredProcedureParameter(name = "pv_source_num",       mode = ParameterMode.IN,         type = String.class),
            @StoredProcedureParameter(name = "pv_amount",           mode = ParameterMode.IN,         type = String.class),
            @StoredProcedureParameter(name = "pv_transaction_date", mode = ParameterMode.IN,         type = String.class),
            @StoredProcedureParameter(name = "pv_transaction_id",   mode = ParameterMode.IN,         type = String.class),
            @StoredProcedureParameter(name = "pv_remark",           mode = ParameterMode.IN,         type = String.class),
            @StoredProcedureParameter(name = "pv_signature",        mode = ParameterMode.IN,         type = String.class)
        }
    ),
    @NamedStoredProcedureQuery(
        name = "VPBankEntity.selectNoti",
        procedureName = "PKG_VPB.selectNoti",
        resultClasses = VPBankEntity.class,
        parameters = {
            @StoredProcedureParameter(name = "pv_refcursor",        mode = ParameterMode.REF_CURSOR, type = Void.class),
            @StoredProcedureParameter(name = "pv_ft",               mode = ParameterMode.IN,         type = String.class)
        }
    ),
    @NamedStoredProcedureQuery(
        name = "VPBankEntity.insertLogs",
        procedureName = "PKG_VPB.insertLogs",
        resultClasses = VPBankEntity.class,
        parameters = {
            @StoredProcedureParameter(name = "pv_refcursor",        mode = ParameterMode.REF_CURSOR, type = Void.class),
            @StoredProcedureParameter(name = "pv_msg",              mode = ParameterMode.IN,         type = String.class),
            @StoredProcedureParameter(name = "pv_status",           mode = ParameterMode.IN,         type = String.class),
            @StoredProcedureParameter(name = "pv_note",             mode = ParameterMode.IN,         type = String.class),
        }
    ),
    @NamedStoredProcedureQuery(
        name = "VPBankEntity.insertChiHo",
        procedureName = "PKG_VPB.insertChiHo",
        resultClasses = VPBankEntity.class,
        parameters = {
            @StoredProcedureParameter(name = "pv_refcursor",        mode = ParameterMode.REF_CURSOR, type = Void.class),
            @StoredProcedureParameter(name = "pv_referenceNumber",  mode = ParameterMode.IN,         type = String.class),
            @StoredProcedureParameter(name = "pv_transactionId",    mode = ParameterMode.IN,         type = String.class),
            @StoredProcedureParameter(name = "pv_transferResult",   mode = ParameterMode.IN,         type = String.class),
            @StoredProcedureParameter(name = "pv_signature",        mode = ParameterMode.IN,         type = String.class),
            @StoredProcedureParameter(name = "pv_transactionDate",  mode = ParameterMode.IN,         type = String.class),
            @StoredProcedureParameter(name = "pv_type",             mode = ParameterMode.IN,         type = String.class),
            @StoredProcedureParameter(name = "pv_amount",           mode = ParameterMode.IN,         type = String.class),
            @StoredProcedureParameter(name = "pv_remark",           mode = ParameterMode.IN,         type = String.class),
            @StoredProcedureParameter(name = "pv_debitAccount",     mode = ParameterMode.IN,         type = String.class),
            @StoredProcedureParameter(name = "pv_creditAccount",    mode = ParameterMode.IN,         type = String.class),
            @StoredProcedureParameter(name = "pv_creditName",       mode = ParameterMode.IN,         type = String.class),
            @StoredProcedureParameter(name = "pv_add_info",         mode = ParameterMode.IN,         type = String.class),
            @StoredProcedureParameter(name = "pv_custid",           mode = ParameterMode.IN,         type = String.class),
        }
    )
})
public class VPBankEntity {
    @Id
    @Column(name = "pv_status")
    private String status;

    @Column(name = "pv_des")
    private String des;
}
