package com.lendbiz.p2p.api.entity;

import java.math.BigInteger;

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

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@NamedStoredProcedureQueries({ //

                @NamedStoredProcedureQuery(name = "AdminGameRateByDayEntity.getAdminRateByDay", resultClasses = AdminGameRateByDayEntity.class, procedureName = "PKG_API.get_adminratebyday", parameters = { //
                                @StoredProcedureParameter(name = "pv_refcursor", mode = ParameterMode.REF_CURSOR, type = Void.class),
                                @StoredProcedureParameter(name = "pv_custid", mode = ParameterMode.IN, type = String.class),
                                @StoredProcedureParameter(name = "pv_date", mode = ParameterMode.IN, type = String.class),

                })

})
public class AdminGameRateByDayEntity {

        @Id
        @Column(name = "ROWNUM")
        private int rowNum;

        @Column(name = "NAME")
        private String name;

        @Column(name = "TOTAL")
        private BigInteger total;

        @Column(name = "RATE")
        private double rate;

}
