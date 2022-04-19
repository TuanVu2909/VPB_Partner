package com.lendbiz.p2p.api.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@NamedStoredProcedureQueries({ //
        @NamedStoredProcedureQuery(name = "InvestPackageEntity.getInvestPackage", procedureName = "pck_gm.getInvestPackage", resultClasses = InvestPackageEntity.class, parameters = { //
                @StoredProcedureParameter(name = "pv_refcursor", mode = ParameterMode.REF_CURSOR, type = Void.class),}) //
})
@Getter
@Setter
@ToString
public class InvestPackageEntity {
    @Id
    // @GeneratedValue
    @Column(name = "ID")
    private String id;
    @Column(name = "FULLDES")
    private String fullDes;
    @Column(name = "PACKAGENAME")
    private String pkName;
    @Column(name = "RISK")
    private String risk;
    @Column(name = "SHORTDES")
    private String shortDes;
;
}
