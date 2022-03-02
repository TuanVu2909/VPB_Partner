package com.lendbiz.p2p.api.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NamedStoredProcedureQueries({ //
        @NamedStoredProcedureQuery(name = "ProductEntity.getproduct", resultClasses = ProductEntity.class, procedureName = "pck_gm.getproduct", parameters = { //
                @StoredProcedureParameter(name = "pv_refcursor", mode = ParameterMode.REF_CURSOR, type = Void.class),}) //
})
public class ProductEntity {
    @Id
    private String id;
    @Column(name = "PCODE")
    private String pCode;
    @Column(name = "PNAME")
    private String name;
    @Column(name = "DESCRIPTION")
    private String des;
    @Column(name = "MAX_VAL")
    private String maxVal;
    @Column(name = "CUSTTYPE")
    private String cType;
    @Column(name = "SHORTNAME")
    private String sName;

}
