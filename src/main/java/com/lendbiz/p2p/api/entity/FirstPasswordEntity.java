package com.lendbiz.p2p.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Entity
@Getter
@Setter
@NamedStoredProcedureQueries({ //
        @NamedStoredProcedureQuery(name = "FirstPasswordEntity.firstPassword", procedureName = "PKG_API_AUTHENTICATION.FIRST_PASSWORD", resultClasses = FirstPasswordEntity.class, parameters = { //
                @StoredProcedureParameter(name = "PV_REFCURSOR", mode = ParameterMode.REF_CURSOR, type = Void.class),
                @StoredProcedureParameter(name = "pv_CustId", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "pv_Password", mode = ParameterMode.IN, type = String.class) }) //
})
public class FirstPasswordEntity {

    @Id
    @Column(name = "KEY")
    private String key;

}
