package com.lendbiz.p2p.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@NamedStoredProcedureQueries({ //
        @NamedStoredProcedureQuery(name = "SavingProductsEntity.findViaProcedure", procedureName = "PKG_API.GET_SAVING_PRODUCTS", resultClasses = SavingProductsEntity.class, parameters = { //
                @StoredProcedureParameter(name = "p_cursor", mode = ParameterMode.REF_CURSOR, type = Void.class) }) //
})
@Getter
@Setter
@ToString
public class SavingProductsEntity {
    @Id
    @Column(name = "CURRENTINDEX")
    private int currIndex;
    @Column(name = "MONTH")
    private int month;
    @Column(name = "COLOR")
    private String color;
    @Column(name = "PRODUCTID")
    private int idProduct;
    @Column(name = "RATE")
    private double rate;

}
