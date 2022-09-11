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
                @NamedStoredProcedureQuery(name = "NinePayDepositEntity.insertApiTrans", resultClasses = NinePayDepositEntity.class, procedureName = "pkg_api.PRC_9PAY_SUCCESS", parameters = { //
                                @StoredProcedureParameter(name = "p_amount", mode = ParameterMode.IN, type = String.class),
                                @StoredProcedureParameter(name = "p_code", mode = ParameterMode.IN, type = String.class)
                }) //
})
public class NinePayDepositEntity {
        @Id
        @Column(name = "key")
        private String key;
}
