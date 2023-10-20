package com.lendbiz.p2p.api.entity.bank;

import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "VPB_TRANSACTIONS")
public class VPBankEntity {

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "MASTER_ACC_NUM")
    private String master_acc_num;

    @Column(name = "VIRTUAL_ACC_NUM")
    private String virtual_acc_num;

    @Column(name = "VIRTUAL_NAME")
    private String virtual_name;

    @Column(name = "VIRTUAL_KEY")
    private String virtual_key;

    @Column(name = "AMOUNT")
    private String amount;

    @Column(name = "BOOKING_DATE")
    private String booking_date;

    @Column(name = "TRANSACTION_DATE")
    private String transaction_date;

    @Column(name = "TRANSACTION_ID")
    private String transaction_id;

    @Column(name = "REMARK")
    private String remark;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "NOTE")
    private String note;

    @Column(name = "SIGNATURE")
    private String signature;
}
