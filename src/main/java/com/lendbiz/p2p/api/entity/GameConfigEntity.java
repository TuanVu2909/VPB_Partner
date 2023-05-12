package com.lendbiz.p2p.api.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigInteger;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NamedStoredProcedureQueries({ //
                @NamedStoredProcedureQuery(name = "GameConfigEntity.getGameConfig", resultClasses = GameConfigEntity.class, procedureName = "PKG_API.get_gameconfig", parameters = { //
                                @StoredProcedureParameter(name = "pv_refcursor", mode = ParameterMode.REF_CURSOR, type = Void.class),
                                @StoredProcedureParameter(name = "pv_groupid", mode = ParameterMode.IN, type = Integer.class),
                                @StoredProcedureParameter(name = "pv_fromtime", mode = ParameterMode.IN, type = String.class),
                                @StoredProcedureParameter(name = "pv_totime", mode = ParameterMode.IN, type = String.class),
                                @StoredProcedureParameter(name = "pv_fromdate", mode = ParameterMode.IN, type = String.class),
                                @StoredProcedureParameter(name = "pv_todate", mode = ParameterMode.IN, type = String.class),

                })
})
public class GameConfigEntity {
        @Id
        @Column(name = "ID")
        private int id;

        @Column(name = "RATE")
        private double rate;

        @Column(name = "NAME")
        private String name;

        @Column(name = "RAMOUNT")
        private BigInteger rAmount;

        @Column(name = "FROMTIME")
        private String fromTime;

        @Column(name = "TOTIME")
        private String toTime;

        @Column(name = "FROMDATE")
        private String fromDate;

        @Column(name = "TODATE")
        private String toDate;

        @Column(name = "MAXPRIZE")
        private Integer maxPrize;
}
