package com.lendbiz.p2p.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NamedStoredProcedureQueries({ //
        @NamedStoredProcedureQuery(name = "Card9PayEntity_v2.findViaProcedure", procedureName = "PKG_API.GET_NINE_PAY_TRANS", resultClasses = Card9PayEntity_v2.class, parameters = { //
                @StoredProcedureParameter(name = "p_cursor", mode = ParameterMode.REF_CURSOR, type = Void.class),
                @StoredProcedureParameter(name = "p_custId", mode = ParameterMode.IN, type = String.class), }) //
})
@Getter
@Setter
public class Card9PayEntity_v2 {
    @Id
    // @GeneratedValue
    private String id;
    @Column(name = "PRICE")
    private String price;
    @Column(name = "CUSTID")
    private String cif;
    @Column(name = "PAY_DATE")
    private String payDate;
    @Column(name = "SERI_CODE")
    private String card_seri;
    @Column(name = "CARD_CODE")
    private String card_code;
    @Column(name = "PAY_STATUS")
    private String status;
    @Column(name = "PRODUCT_ID")
    private String pid;
    @Column(name = "TRANS_ID")
    private String transId;
}
