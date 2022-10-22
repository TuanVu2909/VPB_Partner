package com.lendbiz.p2p.api.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NamedStoredProcedureQueries({ //
        @NamedStoredProcedureQuery(name = "NotificationsPushEntity.getOsNotifications", procedureName = "pkg_api.GET_OS_NOTIFICATIONS", resultClasses = NotificationsPushEntity.class, parameters = { //
                @StoredProcedureParameter(name = "p_cursor", mode = ParameterMode.REF_CURSOR, type = Void.class) }) //
})

@Table(name = "BG_NOTIFICATIONS")
public class NotificationsPushEntity {
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
    private String investId;
    @Column(name = "OS_STATUS")
    private int osStatus;
    @Column(name = "DEVICEID")
    private String deviceId;

}
