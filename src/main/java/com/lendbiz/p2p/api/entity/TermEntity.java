package com.lendbiz.p2p.api.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NamedStoredProcedureQueries({ //
        @NamedStoredProcedureQuery(name = "TermEntity.getTerm", resultClasses = TermEntity.class, procedureName = "pck_gm.getTerm", parameters = { //
                @StoredProcedureParameter(name = "pv_refcursor", mode = ParameterMode.REF_CURSOR, type = Void.class),
                @StoredProcedureParameter(name = "pv_pid", mode = ParameterMode.IN, type = String.class),
                }) //
})
public class TermEntity {
    @Id
    @Column(name = "TERMID")
    private  String termId;
    @Column(name = "TERMNAME")
    private String termName;

}
