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
    @Column(name = "BANKACC")
    private String bankAccount;

    @Column(name = "BANKNAME")
    private String bankName;

    @Column(name = "BANKACNAME")
    private String bankAcName;
}
