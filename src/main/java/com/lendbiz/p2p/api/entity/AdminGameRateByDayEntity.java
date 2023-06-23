package com.lendbiz.p2p.api.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigInteger;

import javax.persistence.*;

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
        @Column(name = "NAME")
        private String name;

        @Column(name = "TOTAL")
        private BigInteger total;

        @Column(name = "RATE")
        private double rate;

}
