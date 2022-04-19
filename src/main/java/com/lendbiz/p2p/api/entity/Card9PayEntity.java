package com.lendbiz.p2p.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
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
import lombok.ToString;

@Entity
@Table(name = "CARD9PAY")
@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Card9PayEntity {
    @Id
    private String id;
    @Column(name = "PRICE")
    private String price;
    @Column(name = "CUSTID")
    private String custid;
    @Column(name = "PAY_DATE")
    private String pay_Date;
    @Column(name = "SERI_CODE")
    private String seri_code;
    @Column(name = "CARD_CODE")
    private String card_code;
    @Column(name = "PAY_STATUS")
    private String pay_status;
    @Column(name = "PRODUCT_ID")
    private String product_id;
    @Column(name = "TRANS_ID")
    private String trans_Id;
    @Column(name = "AMOUNT")
    private String amount;
}
