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
                @NamedStoredProcedureQuery(name = "GameEntity.updateGameConfig", resultClasses = GameEntity.class, procedureName = "PKG_API.update_gameconfig", parameters = { //
                                @StoredProcedureParameter(name = "pv_refcursor", mode = ParameterMode.REF_CURSOR, type = Void.class),
                                @StoredProcedureParameter(name = "pv_id", mode = ParameterMode.IN, type = Integer.class),
                                @StoredProcedureParameter(name = "pv_rate", mode = ParameterMode.IN, type = Double.class),
                                @StoredProcedureParameter(name = "pv_giftid", mode = ParameterMode.IN, type = Integer.class),
                                @StoredProcedureParameter(name = "pv_groupid", mode = ParameterMode.IN, type = Integer.class),
                                @StoredProcedureParameter(name = "pv_status", mode = ParameterMode.IN, type = Integer.class),
                }),
                @NamedStoredProcedureQuery(name = "GameEntity.updateGameGroupTime", resultClasses = GameEntity.class, procedureName = "PKG_API.update_gamegrouptime", parameters = { //
                                @StoredProcedureParameter(name = "pv_refcursor", mode = ParameterMode.REF_CURSOR, type = Void.class),
                                @StoredProcedureParameter(name = "pv_groupid", mode = ParameterMode.IN, type = Integer.class),
                                @StoredProcedureParameter(name = "pv_fromtime", mode = ParameterMode.IN, type = String.class),
                                @StoredProcedureParameter(name = "pv_totime", mode = ParameterMode.IN, type = String.class),
                                @StoredProcedureParameter(name = "pv_fromdate", mode = ParameterMode.IN, type = String.class),
                                @StoredProcedureParameter(name = "pv_todate", mode = ParameterMode.IN, type = String.class),
                                @StoredProcedureParameter(name = "pv_status", mode = ParameterMode.IN, type = Integer.class),
                                @StoredProcedureParameter(name = "pv_maxprize", mode = ParameterMode.IN, type = Integer.class),
                }),

                @NamedStoredProcedureQuery(name = "GameEntity.updateGamePrize", resultClasses = GameEntity.class, procedureName = "PKG_API.update_gameprize", parameters = { //
                                @StoredProcedureParameter(name = "pv_refcursor", mode = ParameterMode.REF_CURSOR, type = Void.class),
                                @StoredProcedureParameter(name = "pv_id", mode = ParameterMode.IN, type = Integer.class),
                                @StoredProcedureParameter(name = "pv_name", mode = ParameterMode.IN, type = String.class),
                                @StoredProcedureParameter(name = "pv_status", mode = ParameterMode.IN, type = Integer.class),
                                @StoredProcedureParameter(name = "pv_ramount", mode = ParameterMode.IN, type = Integer.class),

                }),

                @NamedStoredProcedureQuery(name = "GameEntity.insertGameHistory", resultClasses = GameEntity.class, procedureName = "PKG_API.insert_gamehistory", parameters = { //
                                @StoredProcedureParameter(name = "pv_refcursor", mode = ParameterMode.REF_CURSOR, type = Void.class),
                                @StoredProcedureParameter(name = "pv_custid", mode = ParameterMode.IN, type = String.class),
                                @StoredProcedureParameter(name = "pv_status", mode = ParameterMode.IN, type = Integer.class),
                                @StoredProcedureParameter(name = "pv_giftid", mode = ParameterMode.IN, type = Integer.class),
                                @StoredProcedureParameter(name = "pv_rate", mode = ParameterMode.IN, type = Double.class),
                                @StoredProcedureParameter(name = "pv_configid", mode = ParameterMode.IN, type = Integer.class),
                                @StoredProcedureParameter(name = "pv_logid", mode = ParameterMode.IN, type = String.class),

                })
})
public class GameEntity {
        @Id
        @Column(name = "ERRCODE")
        private int errCode;

        @Column(name = "ERRMESSAGE")
        private int errMessage;
}
