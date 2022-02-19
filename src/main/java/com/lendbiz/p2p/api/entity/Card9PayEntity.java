package com.lendbiz.p2p.api.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "CARD9PAY")
@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Card9PayEntity {
    @Id
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
