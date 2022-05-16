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

@ToString
@Entity
@NamedStoredProcedureQueries({ //
        @NamedStoredProcedureQuery(name = "Card9PayEntity_v2.findViaProcedure", procedureName = "PKG_API.FIND_TRANS_BY_DATE", resultClasses = Card9PayEntity_v2.class, parameters = { //
                @StoredProcedureParameter(name = "p_cursor", mode = ParameterMode.REF_CURSOR, type = Void.class),
                @StoredProcedureParameter(name = "p_custId", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "p_sdate", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "p_edate", mode = ParameterMode.IN, type = String.class), }) //
})
@Getter
@Setter
public class Card9PayEntity_v2 {
    @Id
    // @GeneratedValue
    private String id;
    @Column(name = "PRICE")
    private String price;
    @Column(name = "AMOUNT")
    private String amount;
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
    @Column(name = "PRODUCT_NAME")
    private String product_name;
    @Column(name = "P_DES")
    private String product_des;
    @Column(name = "S_NAME")
    private String service_name;
    @Column(name = "CATEGORY_ID")
    private String category_id;
    @Column(name = "PHONE_RECEIVED")
    private String phone;

}
