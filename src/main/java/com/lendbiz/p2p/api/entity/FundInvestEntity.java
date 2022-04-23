package com.lendbiz.p2p.api.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NamedStoredProcedureQueries({ //
        @NamedStoredProcedureQuery(name = "FundInvestEntity.getFundInvest", resultClasses = FundInvestEntity.class, procedureName = "pck_gm.getFundInvest", parameters = { //
                @StoredProcedureParameter(name = "pv_refcursor", mode = ParameterMode.REF_CURSOR, type = Void.class),
                @StoredProcedureParameter(name = "pv_custId", mode = ParameterMode.IN, type = String.class)
        }) //
})
@Getter
@Setter
public class FundInvestEntity {
        @Id
        @Column(name = "PACKAGEID")
        private String pk_id;
        @Column(name = "PACKAGENAME")
        private String pkName;
        @Column(name = "AMT")
        private String amt;

}
