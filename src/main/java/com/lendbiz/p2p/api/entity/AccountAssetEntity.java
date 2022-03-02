package com.lendbiz.p2p.api.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NamedStoredProcedureQueries({ //
        @NamedStoredProcedureQuery(name = "AccountAssetEntity.getAccountAsset", procedureName = "pck_gm.getAccountAsset", resultClasses = AccountAssetEntity.class, parameters = { //
                @StoredProcedureParameter(name = "pv_refcursor", mode = ParameterMode.REF_CURSOR, type = Void.class),
                @StoredProcedureParameter(name = "pv_custId", mode = ParameterMode.IN, type = String.class)}) //
})
public class AccountAssetEntity {
    @Id
    private String id;
    @Column(name = "BALANCE")
    private long balance;
    @Column(name = "TOTALAMOUNT")
    private long totalAmount;


}
