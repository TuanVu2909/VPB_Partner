package com.lendbiz.p2p.api.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NamedStoredProcedureQueries({ //
        @NamedStoredProcedureQuery(name = "AccountInvest.getAccountInvest", procedureName = "pck_gm.getAccountInvest", resultClasses = AccountInvest.class, parameters = { //
                @StoredProcedureParameter(name = "pv_refcursor", mode = ParameterMode.REF_CURSOR, type = Void.class),
                @StoredProcedureParameter(name = "pv_custId", mode = ParameterMode.IN, type = String.class)}) //
})

public class AccountInvest {
    @Id
    @Column(name = "PID")
    private int id;
    @Column(name = "PRODUCTNAME")
    private String productName;
    @Column(name = "AMT")
    private long amt;

}
