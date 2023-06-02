package com.lendbiz.p2p.api.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@NamedStoredProcedureQueries({ //
                @NamedStoredProcedureQuery(name = "GameHistoryEntity.getGameHistory", resultClasses = GameHistoryEntity.class, procedureName = "PKG_API.get_gamehistory", parameters = { //
                                @StoredProcedureParameter(name = "pv_refcursor", mode = ParameterMode.REF_CURSOR, type = Void.class),
                                @StoredProcedureParameter(name = "pv_custid", mode = ParameterMode.IN, type = String.class),
                                @StoredProcedureParameter(name = "pv_gameid", mode = ParameterMode.IN, type = int.class),

                })

})
public class GameHistoryEntity {

        @Id
        @Column(name = "REQUESTID")
        private int requestId;

        @Column(name = "LOGDATE")
        private String logDate;

        @Column(name = "NAME")
        private String name;

}
