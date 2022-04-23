package com.lendbiz.p2p.api.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NamedStoredProcedureQueries({ //
        @NamedStoredProcedureQuery(name = "FundInvestDetailEntity.getFundInvestDetail", resultClasses = FundInvestDetailEntity.class, procedureName = "pck_gm.getFundInvestDetail", parameters = { //
                @StoredProcedureParameter(name = "pv_refcursor", mode = ParameterMode.REF_CURSOR, type = Void.class),
                @StoredProcedureParameter(name = "pv_custId", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "pv_packageId", mode = ParameterMode.IN, type = String.class)
        }) //
})
@Getter
@Setter
public class FundInvestDetailEntity {

    @Id
    @Column(name = "PACKAGEID")
    private String pk_id;
    @Column(name = "PACKAGENAME")
    private String pkName;
    @Column(name = "AMT")
    private String amt;
    @Column(name = "FUNDID")
    private String fundID;
    @Column(name = "FUNDCODE")
    private String fundCode;
    @Column(name = "FUNDNAME")
    private String fundName;
    @Column(name = "STOCK")
    private String stock;
}
