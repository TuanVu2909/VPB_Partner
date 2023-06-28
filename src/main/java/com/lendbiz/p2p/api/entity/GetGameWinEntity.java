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
                @NamedStoredProcedureQuery(name = "GetGameWinEntity.getGameWin", resultClasses = GetGameWinEntity.class, procedureName = "PKG_API.get_gamewinprize", parameters = { //
                                @StoredProcedureParameter(name = "pv_refcursor", mode = ParameterMode.REF_CURSOR, type = Void.class)

                })

})
public class GetGameWinEntity {

        @Column(name = "ID")
        private int id;

        @Id
        @Column(name = "TIMEWIN")
        private String time;

        @Column(name = "FULLNAME")
        private String fullName;

        @Column(name = "MOBILE")
        private String mobile;

        @Column(name = "PRIZENAME")
        private String prizeName;

}
