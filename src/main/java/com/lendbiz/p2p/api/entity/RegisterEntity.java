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
                @NamedStoredProcedureQuery(name = "RegisterEntity.register", resultClasses = RegisterEntity.class, procedureName = "PKG_API_AUTHENTICATION.reqjoin_v2", parameters = { //
                                @StoredProcedureParameter(name = "pv_refcursor", mode = ParameterMode.REF_CURSOR, type = Void.class),
                                @StoredProcedureParameter(name = "pv_mobile", mode = ParameterMode.IN, type = String.class),
                                @StoredProcedureParameter(name = "pv_deviceId", mode = ParameterMode.IN, type = String.class),
                }) //
})
public class RegisterEntity {
        @Id
        @Column(name = "CODE")
        private String code;
        @Column(name = "CUSTID")
        private String custId;
        @Column(name = "ERRORCODE")
        private int errorCode;
}
