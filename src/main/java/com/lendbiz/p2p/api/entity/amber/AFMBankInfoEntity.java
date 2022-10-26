package com.lendbiz.p2p.api.entity.amber;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "AFM_BANK_INFO")
@Data
public class AFMBankInfoEntity {
    @Id
    @Column(name = "ID")
    private String id;
    @Column(name = "BANK_CODE")
    private String bankCode;
    @Column(name = "BANK_NAME")
    private String bankName;
    @Column(name = "BANK_BIN")
    private String bankBin;
}
