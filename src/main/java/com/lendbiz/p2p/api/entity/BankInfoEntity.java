package com.lendbiz.p2p.api.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BANK_INFO")
@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BankInfoEntity {
    @Id
    @Column(name = "ID")
    private String id;
    @Column(name = "BANK_NAME")
    private String bankName;
    @Column(name = "BANK_ENG_NAME")
    private String engName;
    @Column(name = "BANK_SHORT_NAME")
    private String shortName;
    @Column(name = "BANK_CODE")
    private String bank_code;

}
