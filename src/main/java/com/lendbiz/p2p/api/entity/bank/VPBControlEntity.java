package com.lendbiz.p2p.api.entity.bank;

import com.google.api.client.util.DateTime;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name = "VPBControlEntity.selectLogs",
                procedureName = "PKG_VPB.selectLogs",
                resultClasses = VPBControlEntity.class,
                parameters = {
                        @StoredProcedureParameter(name = "pv_refcursor",        mode = ParameterMode.REF_CURSOR, type = Void.class)
                }
        ),

})
public class VPBControlEntity {
    @Id
    @Column(name = "REFERENCE_NUMBER")
    private String REFERENCE_NUMBER;

    @Column(name = "TRAN_TYPE")
    private String TRAN_TYPE;

    @Column(name = "FT")
    private String FT;

    @Column(name = "DEBIT_SOURCE_ACCOUNT")
    private String DEBIT_SOURCE_ACCOUNT;

    @Column(name = "BANK_ACCOUNT_NUMBER")
    private String BANK_ACCOUNT_NUMBER;

    @Column(name = "BANK_ACCOUNT_NAME")
    private String BANK_ACCOUNT_NAME;

    @Column(name = "BANK_NAME")
    private String BANK_NAME;

    @Column(name = "BANK_CODE")
    private String BANK_CODE;

    @Column(name = "AMOUNT")
    private int AMOUNT;

    @Column(name = "TRANS_DATE")
    private String TRANS_DATE;

    @Column(name = "CHECK_SUM")
    private String CHECK_SUM;

}

