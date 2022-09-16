package com.lendbiz.p2p.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@NamedStoredProcedureQueries({ //
                @NamedStoredProcedureQuery(name = "ResendOtpEntity.resendOtp", procedureName = "pkg_api_authentication.RESEND_OTP", resultClasses = ResendOtpEntity.class, parameters = { //
                                @StoredProcedureParameter(name = "p_cursor", mode = ParameterMode.REF_CURSOR, type = Void.class),
                                @StoredProcedureParameter(name = "p_mobile", mode = ParameterMode.IN, type = String.class),
                                @StoredProcedureParameter(name = "p_custId", mode = ParameterMode.IN, type = String.class), }) //
})
public class ResendOtpEntity {
        @Id
        @Column(name = "CUSTID")
        private String custId;
        @Column(name = "CODE")
        private String code;

}
