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
        @NamedStoredProcedureQuery(name = "ProductEntity.getproduct", resultClasses = ProductEntity.class, procedureName = "pck_gm.getproduct", parameters = { //
                @StoredProcedureParameter(name = "pv_refcursor", mode = ParameterMode.REF_CURSOR, type = Void.class), }) //
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
    @Column(name = "DESCRIPTION_2")
    private String des2;
    @Column(name = "MAX_VAL")
    private String maxVal;
    @Column(name = "CUSTTYPE")
    private String cType;
    @Column(name = "SHORTNAME")
    private String sName;
    @Column(name = "SUGGEST")
    private String suggest;
    @Column(name = "FEE")
    private String fee;
    @Column(name = "RATE")
    private String rate;
    @Column(name = "COLOR")
    private String color;

}
