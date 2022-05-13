package com.lendbiz.p2p.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.ParameterMode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NamedStoredProcedureQueries({ //
        @NamedStoredProcedureQuery(name = "VerifyEmailEntity.verify", resultClasses = VerifyEmailEntity.class, procedureName = "PKG_API.PRC_VERIFY_EMAIL", parameters = { //
                @StoredProcedureParameter(name = "p_custid", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "p_email", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "p_otp", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "p_state", mode = ParameterMode.IN, type = int.class),
        }) //
})
public class VerifyEmailEntity {
    @Id
    @Column(name = "ID")
    private String id;

}
