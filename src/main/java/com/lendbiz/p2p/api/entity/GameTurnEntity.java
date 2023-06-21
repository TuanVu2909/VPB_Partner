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
                @NamedStoredProcedureQuery(name = "GameTurnEntity.getGameTurn", resultClasses = GameTurnEntity.class, procedureName = "PKG_API.get_gameturn", parameters = { //
                                @StoredProcedureParameter(name = "pv_refcursor", mode = ParameterMode.REF_CURSOR, type = Void.class),
                                @StoredProcedureParameter(name = "pv_custid", mode = ParameterMode.IN, type = String.class),
                                @StoredProcedureParameter(name = "pv_gameid", mode = ParameterMode.IN, type = int.class),

                })

})
public class GameTurnEntity {

        @Id
        @Column(name = "RESTCOUNT")
        private int restCount;

}
