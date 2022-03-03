package com.lendbiz.p2p.api.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NamedStoredProcedureQueries({ //
        @NamedStoredProcedureQuery(name = "PayTypeEntity.getPay", resultClasses = PayTypeEntity.class, procedureName = "pck_gm.getPayType", parameters = { //
                @StoredProcedureParameter(name = "pv_refcursor", mode = ParameterMode.REF_CURSOR, type = Void.class),
               }) //
})
public class PayTypeEntity {
    @Id
    @Column(name = "PAYTYPEID")
    private String payTypeId;
    @Column(name = "PAYTYPENAME")
    private String payTypeName;
}
