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
        @NamedStoredProcedureQuery(name = "RateEntity.getRateConfig", resultClasses = RateEntity.class, procedureName = "pkg_api.get_rate", parameters = { //
                @StoredProcedureParameter(name = "pv_refcursor", mode = ParameterMode.REF_CURSOR, type = Void.class) }) //
})
public class RateConfigEntity {
    @Id
    @Column(name = "ID")
    private String id;
    @Column(name = "pid")
    private String pid;
    @Column(name = "term")
    private String term;
    @Column(name = "termtype")
    private String termType;
    @Column(name = "minamt")
    private String minAmount;
    @Column(name = "rate")
    private String rate;
    @Column(name = "profitsharing")
    private String profitSharing;
    @Column(name = "paytype")
    private String payType;
    @Column(name = "maxamt")
    private String maxAmount;
    @Column(name = "fee")
    private String fee;
}
