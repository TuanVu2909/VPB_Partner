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

@Entity
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "ATEntity.postBack", procedureName = "pkg_api.get_accesstrade_cashin", resultClasses = ATEntity.class, parameters = { //
                @StoredProcedureParameter(name = "pv_refcursor", mode = ParameterMode.REF_CURSOR, type = Void.class)
           }) ,
        @NamedStoredProcedureQuery(name = "ATEntity.accept", procedureName = "pkg_api.get_accesstrade_accept", resultClasses = ATEntity.class, parameters = { //
                @StoredProcedureParameter(name = "pv_refcursor", mode = ParameterMode.REF_CURSOR, type = Void.class)
        }),
        @NamedStoredProcedureQuery(name = "ATEntity.reject", procedureName = "pkg_api.get_accesstrade_reject", resultClasses = ATEntity.class, parameters = { //
                @StoredProcedureParameter(name = "pv_refcursor", mode = ParameterMode.REF_CURSOR, type = Void.class)
        }),
        @NamedStoredProcedureQuery(name = "ATEntity.status", procedureName = "pkg_api.update_accesstrade_status", resultClasses = ATEntity.class, parameters = { //
                @StoredProcedureParameter(name = "pv_refcursor", mode = ParameterMode.REF_CURSOR, type = Void.class),
                @StoredProcedureParameter(name = "pv_custid", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "pv_status", mode = ParameterMode.IN, type = Integer.class)
        }),
       
})
@Getter
@Setter
@ToString
public class ATEntity {
    @Id
    @Column(name = "CUSTID")
    private String custid;

    @Column(name = "PUBLICSHERID")
    private String pubid;
}
