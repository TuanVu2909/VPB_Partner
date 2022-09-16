package com.lendbiz.p2p.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class BankAccountEntity {

    @Id
    @Column(name = "BANK_ACCOUNT_NUMBER")
    private String bankAccount;

    @Column(name = "BANK_NAME")
    private String bankName;

    @Column(name = "BANK_ACCOUNT_NAME")
    private String bankAcName;

    @Column(name = "BANK_CODE")
    private String bankCode;
}
