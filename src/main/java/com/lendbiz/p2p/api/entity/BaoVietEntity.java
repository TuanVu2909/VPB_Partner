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
        private String pkgName;
        @Column(name = "DESCRIPTION")
        private String des;
        @Column(name = "TOTALLIMIT")
        private String totalLimit;
        @Column(name = "HOSPITALFEE")
        private String hospitalFee;
        @Column(name = "SURGERYFEE")
        private String surgeryFee;
        @Column(name = "TREATEDFEE")
        private String treatedFee;
        @Column(name = "RETREATEDFEE")
        private String retreatedFee;
        @Column(name = "AMBULANCEFEE")
        private String ambulanceFee;
        @Column(name = "REHABILITATIONFEE")
        private String rehabilitationFee;
        @Column(name = "PUBLICHOSPITALFEE")
        private String publicHospitalFee;
        @Column(name = "BURIALFEE")
        private String burialFee;
        @Column(name = "OUTPATIENTFEE")
        private String outPatientFee;
        @Column(name = "ACCIDENTFEE")
        private String accidentFee;
        @Column(name = "LIFEFEE")
        private String lifeFee;
        @Column(name = "DENTISTRYFEE")
        private String dentistryFee;
        @Column(name = "PREGNANTFEE")
        private String pregnantFee;
}
