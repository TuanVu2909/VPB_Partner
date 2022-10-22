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

@Entity
@Getter
@Setter
@NamedStoredProcedureQueries({ //
        @NamedStoredProcedureQuery(name = "ReferenceIdentity.getReferenceId", resultClasses = ReferenceIdentity.class, procedureName = "pkg_api.get_ref_customers", parameters = { //
                @StoredProcedureParameter(name = "p_cursor", mode = ParameterMode.REF_CURSOR, type = Void.class), 
                @StoredProcedureParameter(name = "p_refCustId", mode = ParameterMode.IN, type = String.class), }) //
})
public class ReferenceIdentity {
    @Id
    @Column(name = "CUSTID")
    private String id;
    @Column(name = "REFCUSTID")
    private String refId;
    @Column(name = "ACCOUNTID")
    private String accId;
   

}
