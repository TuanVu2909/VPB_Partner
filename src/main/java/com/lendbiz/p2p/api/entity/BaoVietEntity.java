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
        @NamedStoredProcedureQuery(name = "BaoVietEntity.getInsurancePackage", resultClasses = BaoVietEntity.class, procedureName = "pck_gm.getInsurancePackage", parameters = { //
                @StoredProcedureParameter(name = "pv_refcursor", mode = ParameterMode.REF_CURSOR, type = Void.class),
        }) //
})
public class BaoVietEntity {
        @Id
        @Column(name = "ID")
        private String id;
        @Column(name = "PACKAGENAME")
        private String pkName;
        @Column(name = "DESCRIPTION")
        private String des;
        @Column(name = "TOTALLIMIT")
        private String totalLimit;
        @Column(name = "HOSPITALFEE")
        private String hospitalFee;
        @Column(name = "TREATEDFEE")
        private String trFee;
        @Column(name = "RETREATEDFEE")
        private String retFee;
}
