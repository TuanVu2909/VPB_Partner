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
        @NamedStoredProcedureQuery(name = "WithdrawEntity.subtractBalance", procedureName = "pck_gm.subtractBalance", resultClasses = WithdrawEntity.class, parameters = { //
                @StoredProcedureParameter(name = "pv_custId", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "pv_amt", mode = ParameterMode.IN, type = Double.class),
                @StoredProcedureParameter(name = "pv_type", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "pv_refcursor", mode = ParameterMode.REF_CURSOR, type = Void.class),
              

        }) //
})
public class WithdrawEntity {
        @Id
    @Column(name = "pv_status")
    private String pStatus;

    @Column(name = "pv_des")
    private String des;

}
