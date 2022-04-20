package com.lendbiz.p2p.api.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Data
@NamedStoredProcedureQueries({ //
        @NamedStoredProcedureQuery(name = "Product9PayEntity.get_productcard9pay", resultClasses = Product9PayEntity.class, procedureName = "PKG_API.get_productcard9pay", parameters = { //
                @StoredProcedureParameter(name = "pv_refcursor", mode = ParameterMode.REF_CURSOR, type = Void.class),
                @StoredProcedureParameter(name = "s_id", mode = ParameterMode.IN, type = String.class),
        }) //
})
@NoArgsConstructor
@AllArgsConstructor
public class Product9PayEntity {
    @Id
    @Column(name = "PID")
    private String id;
    @Column(name = "PRICE")
    private long price;
    @Column(name = "PRODUCT_NAME")
    private String name;
    @Column(name = "DESCRIPTION")
    private String des;
    @Column(name = "PROVIDER_ID")
    private int provider_id;
    @Column(name = "SERVICE_ID")
    private int service_id;
    @Column(name = "PROVIDER_NAME")
    private String pv_name;
    @Column(name = "SERVICE_NAME")
    private String sv_name;
    @Column(name = "SERVICE_DES")
    private String sv_des;

}
