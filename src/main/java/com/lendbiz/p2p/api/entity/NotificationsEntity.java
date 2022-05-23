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
        @NamedStoredProcedureQuery(name = "NotificationsEntity.getNotifications", procedureName = "pkg_api.GET_NOTIFICATIONS", resultClasses = NotificationsEntity.class, parameters = { //
                @StoredProcedureParameter(name = "pv_refcursor", mode = ParameterMode.REF_CURSOR, type = Void.class),
                @StoredProcedureParameter(name = "pv_custId", mode = ParameterMode.IN, type = String.class) }) //
})

@Table(name = "BG_NOTIFICATIONS")
public class NotificationsEntity {
    @Id
    @Column(name = "ID")
    private String id;
    @Column(name = "TYPE")
    private int type;
    @Column(name = "TITLE")
    private String title;
    @Column(name = "MESSAGE")
    private String message;
    @Column(name = "TIME")
    private String time;
    @Column(name = "CUSTID")
    private String custId;
    @Column(name = "STATUS")
    private int status;
    @Column(name = "AMOUNT")
    private String amount;
    @Column(name = "INVESTID")
    private int investId;

}
