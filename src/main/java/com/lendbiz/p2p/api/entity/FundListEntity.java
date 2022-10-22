package com.lendbiz.p2p.api.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@NamedStoredProcedureQueries({ //
        @NamedStoredProcedureQuery(name = "FundListEntity.getFundList", procedureName = "pck_gm.getFundList", resultClasses = FundListEntity.class, parameters = { //
                @StoredProcedureParameter(name = "pv_refcursor", mode = ParameterMode.REF_CURSOR, type = Void.class),}) //
})
@Getter
@Setter
@ToString
public class FundListEntity {
    @Id
    // @GeneratedValue
    @Column(name = "ID")
    private String id;
    @Column(name = "FUNDCODE")
    private String fCode;
    @Column(name = "FUNDNAME")
    private String fName;
    @Column(name = "FUNDTYPE")
    private String fType;
    @Column(name = "MANAGEMENTNAME")
    private String managementName;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "FEE")
    private String fee;
    @Column(name = "INTRODUCTION")
    private String introduction;
    @Column(name = "STRATEGY")
    private String strategy;

}
