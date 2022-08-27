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
        @NamedStoredProcedureQuery(name = "StatementsEntity.getStatements", procedureName = "pkg_api.GET_STATEMENTS", resultClasses = StatementsEntity.class, parameters = { //
                @StoredProcedureParameter(name = "pv_refcursor", mode = ParameterMode.REF_CURSOR, type = Void.class),
                @StoredProcedureParameter(name = "pv_custId", mode = ParameterMode.IN, type = String.class) }) //
})

public class StatementsEntity {
    @Id
    @Column(name = "AUTOID")
    private String autoId;
    @Column(name = "INVESTID")
    private int investId;
    @Column(name = "TXDATE")
    private String date;
    @Column(name = "NAMT")
    private String amount;
    @Column(name = "TLTXCD")
    private String code;
    @Column(name = "TXDESC")
    private String description;
    @Column(name = "TXTYPE")
    private String type;
    @Column(name = "TRANCODE")
    private int tranCode;
    @Column(name = "TITLE")
    private String title;

}
