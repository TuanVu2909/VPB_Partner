package com.lendbiz.p2p.api.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class PkgFundInfoEntity {
    @Id
    @Column(name = "ID")
    private int id;
    @Column(name = "FUND_DATE")
    private String fund_date;
    @Column(name = "GROWTH")
    private String growth;
    @Column(name = "F_CODE")
    private String f_code;
    @Column(name = "PKG_ID")
    private String pkg_id;

}
