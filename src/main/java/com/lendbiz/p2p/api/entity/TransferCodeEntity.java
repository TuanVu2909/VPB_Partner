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
        @NamedStoredProcedureQuery(name = "TransferCodeEntity.genTransferCode", resultClasses = TransferCodeEntity.class, procedureName = "pck_gm.genTransferCode", parameters = { //
                @StoredProcedureParameter(name = "pv_refcursor", mode = ParameterMode.REF_CURSOR, type = Void.class),
                @StoredProcedureParameter(name = "pv_custId", mode = ParameterMode.IN, type = String.class)
        }) //
})
public class TransferCodeEntity {
    @Id
    @Column(name = "TRANSFERCODE")
    private String transferCode;
}
