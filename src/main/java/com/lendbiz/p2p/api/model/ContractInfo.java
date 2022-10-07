package com.lendbiz.p2p.api.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "CONTRACTS_INFO")
public class ContractInfo {

    @Id
    @Column(name = "ID")
    private String id;
    @Column(name = "CUSTID")
    private String custId;
    @Column(name = "URL")
    private String url;
    @Column(name = "PATH")
    private String path;
    @Column(name = "CONTRACT_TYPE")
    private String contractType;
    @Column(name = "CONTRACT_NAME")
    private String contractName;
    @Column(name = "CONTRACT_ID")
    private String contractId;
    @Column(name = "SIGNED_DATE")
    private LocalDate signedDate;
    @Column(name = "GENERATE_DATE")
    private LocalDate generateDate;
    @Column(name = "STATUS")
    private int status;

}
