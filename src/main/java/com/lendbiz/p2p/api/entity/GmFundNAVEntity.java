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
public class GmFundNAVEntity {
    @Id
    @Column(name = "ID")
    String id;
    @Column(name = "FUNDID")
    String fundId;
    @Column(name = "NAV")
    String nav;
    @Column(name = "NAVDATE")
    String navDate;
}
