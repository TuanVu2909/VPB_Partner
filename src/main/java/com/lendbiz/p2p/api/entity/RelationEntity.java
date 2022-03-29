package com.lendbiz.p2p.api.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NamedStoredProcedureQueries({ //
        @NamedStoredProcedureQuery(name = "RelationEntity.getRelation", resultClasses = RelationEntity.class, procedureName = "pck_gm.getRelation", parameters = { //
                @StoredProcedureParameter(name = "pv_refcursor", mode = ParameterMode.REF_CURSOR, type = Void.class),
        }) //
})
public class RelationEntity {
    @Id
    @Column(name = "RELATIONID")
    private String relationId;
    @Column(name = "RELATIONNAME")
    private  String relationName;
}
