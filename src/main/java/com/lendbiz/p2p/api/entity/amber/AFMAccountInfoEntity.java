package com.lendbiz.p2p.api.entity.amber;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "AFM_ACCOUNT_INFO")
@Data
public class AFMAccountInfoEntity {
    @Id
    @Column(name = "MOBILE")
    private String mobile;
    @Column(name = "CUSID")
    private String cusId;
    @Column(name = "IDCODE")
    private String idCode;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "BANK_BIN")
    private String bankBin;
    @Column(name = "BANK_ACCOUNT")
    private String bankAccount;
    @Column(name = "CUSTODYCD")
    private String custodycd;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "STATUS_VSD")
    private String statusVsd;
    @Column(name = "CREATE_DATE")
    private String createDate;
    @Column(name = "STATUS_ID")
    private String status_id;
    @Column(name = "STATUS_VSD_ID")
    private String status_vsd_id;
}
