package com.lendbiz.p2p.api.entity.affiliate;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "GMAFFILIATE")
@Data
public class GMAffiliateEntity {

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "CUSTID")
    private String custId;

    @Column(name = "PUBLICSHERID")
    private String publicSherId;

    @Column(name = "START_DATE")
    private Date startDate;

    @Column(name = "DEVICEID")
    private String deviceId;

    @Column(name = "SOURCE")
    private String source;

    @Column(name = "ISEKYC")
    private int isEkyc;

    @Column(name = "ISSAVING")
    private int isSaving;

    @Column(name = "STATUS")
    private int status;
}
