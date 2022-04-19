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
        @NamedStoredProcedureQuery(name = "RateEntity.getRatePro", resultClasses = RateEntity.class, procedureName = "pck_gm.getRate", parameters = { //
                @StoredProcedureParameter(name = "pv_refcursor", mode = ParameterMode.REF_CURSOR, type = Void.class),
                @StoredProcedureParameter(name = "pv_pid", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "pv_term", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "pv_amt", mode = ParameterMode.IN, type = String.class)}) //
})
public class RateEntity {
    @Id
    @Column(name = "term")
    private String term;
    @Column(name = "rate")
    private String rate;
    @Column(name = "paytype")
    private  String paytype;
}
