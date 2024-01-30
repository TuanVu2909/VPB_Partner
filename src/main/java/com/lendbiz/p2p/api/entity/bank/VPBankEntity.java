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
    }),
    @NamedStoredProcedureQuery(
        name = "VPBankEntity.selectNoti",
        procedureName = "PKG_VPB.selectNoti",
        resultClasses = VPBankEntity.class,
        parameters = {
            @StoredProcedureParameter(name = "pv_refcursor",        mode = ParameterMode.REF_CURSOR, type = Void.class),
            @StoredProcedureParameter(name = "pv_ft",               mode = ParameterMode.IN,         type = String.class)
        })
})
public class VPBankEntity {
    @Id
    @Column(name = "pv_status")
    private String status;

    @Column(name = "pv_des")
    private String des;
}
