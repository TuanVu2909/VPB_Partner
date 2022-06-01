package com.lendbiz.p2p.api.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NamedStoredProcedureQueries({ //
        @NamedStoredProcedureQuery(name = "InsuranceAdditionPrice.getInsuranceAdditionPrice", resultClasses = InsuranceAdditionPrice.class, procedureName = "pck_gm.getInsuranceAdditionPrice", parameters = { //
                @StoredProcedureParameter(name = "pv_refcursor", mode = ParameterMode.REF_CURSOR, type = Void.class),
                @StoredProcedureParameter(name = "pv_pkgId", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "pv_age", mode = ParameterMode.IN, type = String.class)}) //
})
public class InsuranceAdditionPrice {

    @Id
    @Column(name = "ID")
    private String ID;
    @Column(name = "additionid")
    private String additionid;
    @Column(name = "additionname")
    private String additionname;
    @Column(name = "PKGID")
    private String pkgId;
    @Column(name = "PRICE")
    private String price;
}
