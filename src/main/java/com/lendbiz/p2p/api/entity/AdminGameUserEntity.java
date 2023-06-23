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

                @NamedStoredProcedureQuery(name = "AdminGameUserEntity.getAdminGameUser", resultClasses = AdminGameUserEntity.class, procedureName = "PKG_API.get_admingameuser", parameters = { //
                                @StoredProcedureParameter(name = "pv_refcursor", mode = ParameterMode.REF_CURSOR, type = Void.class),
                                @StoredProcedureParameter(name = "pv_custid", mode = ParameterMode.IN, type = String.class),
                                @StoredProcedureParameter(name = "pv_gameid", mode = ParameterMode.IN, type = int.class),

                })

})
public class AdminGameUserEntity {

        @Id
        @Column(name = "CUSTID")
        private String custId;

        @Column(name = "FULLNAME")
        private String fullName;

        @Column(name = "MOBILESMS")
        private String phone;

        @Column(name = "STARTDATE")
        private String logDate;

        @Column(name = "LASTDATE")
        private String lastDate;

        @Column(name = "RESTCOUNT")
        private int restCount;

        @Column(name = "TOTALTURN")
        private int totalTurn;

        @Column(name = "AMTVALID")
        private double amtValid;

        @Column(name = "AMTNOTVALID")
        private double amtNotValid;

        @Column(name = "CONGTHUONG")
        private double bonus;

        @Column(name = "TRUTHUONG")
        private double revertBonus;

}
