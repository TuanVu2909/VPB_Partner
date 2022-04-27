package com.lendbiz.p2p.api.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "SUM_GROWTH")
public class SumGrowthEntity {
    @Id
    @Column(name = "ID")
    private int id;
    @Column(name = "FUND_DATE")
    private String fund_date;
    @Column(name = "SUM")
    private String sum;
}
