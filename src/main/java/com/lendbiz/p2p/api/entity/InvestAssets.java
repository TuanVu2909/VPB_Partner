package com.lendbiz.p2p.api.entity;

import lombok.*;
import org.hibernate.annotations.Formula;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class InvestAssets {
    @Id
    @Column(name = "DOCUMENTNO")
    private String documentno;
    @Column(name = "AMOUNT")
    private String amount;
    @Column(name = "TERM")
    private String term;
    @Column(name = "RATE")
    private String rate;
    @Column(name = "START_DATE")
    private String start_date;
    @Column(name = "END_DATE")
    private String end_date;


}
