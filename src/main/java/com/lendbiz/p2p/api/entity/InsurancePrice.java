package com.lendbiz.p2p.api.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NamedStoredProcedureQueries({ //
        @NamedStoredProcedureQuery(name = "InsurancePrice.getInsurancePackagePrice", resultClasses = InsurancePrice.class, procedureName = "pck_gm.getInsurancePackagePrice", parameters = { //
                @StoredProcedureParameter(name = "pv_refcursor", mode = ParameterMode.REF_CURSOR, type = Void.class),
                @StoredProcedureParameter(name = "pv_pkgId", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "pv_age", mode = ParameterMode.IN, type = String.class)}) //
})
public class InsurancePrice {
    @Id
    @Column(name = "ID")
    private String ID;
    @Column(name = "MAXAGE")
    private String maxAge;
    @Column(name = "MINAGE")
    private String minAge;
    @Column(name = "PKGID")
    private String pkgId;
    @Column(name = "PRICE")
    private String price;



}