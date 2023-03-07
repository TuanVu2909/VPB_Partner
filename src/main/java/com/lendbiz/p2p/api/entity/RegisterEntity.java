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
                                @StoredProcedureParameter(name = "Pv_refcursor", mode = ParameterMode.REF_CURSOR, type = Void.class),
                                @StoredProcedureParameter(name = "Pv_mobile", mode = ParameterMode.IN, type = String.class),
                                @StoredProcedureParameter(name = "Pv_deviceId", mode = ParameterMode.IN, type = String.class),
                                @StoredProcedureParameter(name = "Pv_custId", mode = ParameterMode.IN, type = String.class),
                                @StoredProcedureParameter(name = "Pv_source", mode = ParameterMode.IN, type = String.class),
                                @StoredProcedureParameter(name = "Pv_medium", mode = ParameterMode.IN, type = String.class),
                                @StoredProcedureParameter(name = "Pv_platform", mode = ParameterMode.IN, type = String.class)
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
        @Column(name = "ACCOUNTSTATUS")
        private String accountStatus;
}
