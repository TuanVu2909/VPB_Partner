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
@NamedStoredProcedureQueries({ //
        @NamedStoredProcedureQuery(name = "InvestAssets.getAccountInvestByProduct", resultClasses = InvestAssets.class, procedureName = "pck_gm.getAccountInvestByProduct", parameters = { //
                @StoredProcedureParameter(name = "pv_refcursor", mode = ParameterMode.REF_CURSOR, type = Void.class),
                @StoredProcedureParameter(name = "pv_custId", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "pv_pid", mode = ParameterMode.IN, type = String.class)
        }) //
})
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
    @Column(name = "estint")
    private String estint;
    @Column(name = "START_DATE")
    private String start_date;
    @Column(name = "END_DATE")
    private String end_date;


}
