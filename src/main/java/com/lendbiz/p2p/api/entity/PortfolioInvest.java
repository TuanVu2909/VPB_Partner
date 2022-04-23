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
        @NamedStoredProcedureQuery(name = "PortfolioInvest.getPortfolio", procedureName = "pkg_api.GET_PORTFOLIO", resultClasses = PortfolioInvest.class, parameters = { //
                @StoredProcedureParameter(name = "pv_refcursor", mode = ParameterMode.REF_CURSOR, type = Void.class),
                @StoredProcedureParameter(name = "pv_custId", mode = ParameterMode.IN, type = String.class) }) //
})

public class PortfolioInvest {
    @Id
    @Column(name = "INVEST_ID")
    private int investId;
    @Column(name = "INVEST_TYPE")
    private int investType;
    @Column(name = "DOCUMENTNO")
    private String documentNo;
    @Column(name = "AMOUNT")
    private long amount;
    @Column(name = "RATE")
    private double rate;
    @Column(name = "TERM")
    private int term;
    @Column(name = "START_DATE")
    private String startDate;
    @Column(name = "END_DATE")
    private String enDate;
    @Column(name = "PAY_TYPE")
    private int payType;
    @Column(name = "CREATE_DATE")
    private String createDate;
    @Column(name = "PNAME")
    private String pName;
    @Column(name = "COLOR")
    private String color;

}
