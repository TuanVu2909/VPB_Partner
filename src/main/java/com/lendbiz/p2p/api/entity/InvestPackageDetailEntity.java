package com.lendbiz.p2p.api.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@NamedStoredProcedureQueries({ //
        @NamedStoredProcedureQuery(name = "InvestPackageDetailEntity.getInvestPackageDetail", procedureName = "pck_gm.getInvestPackageDetail", resultClasses = InvestPackageDetailEntity.class, parameters = { //
                @StoredProcedureParameter(name = "pv_refcursor", mode = ParameterMode.REF_CURSOR, type = Void.class),
                @StoredProcedureParameter(name = "pv_packageId", mode = ParameterMode.IN, type = String.class),}) //
})
@Getter
@Setter
@ToString
public class InvestPackageDetailEntity {
    @Id
    // @GeneratedValue
    @Column(name = "ID")
    private String id;
    @Column(name = "FUNDCODE")
    private String fundCode;
    @Column(name = "PACKAGENAME")
    private String pkName;
    @Column(name = "RATE")
    private String rate;

}
