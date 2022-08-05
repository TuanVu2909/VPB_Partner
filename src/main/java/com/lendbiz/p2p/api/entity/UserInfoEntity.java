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
public class UserInfoEntity {

    @Id
    @Column(name = "CUSTID")
    private String custid;

    @Column(name = "FULLNAME")
    private String fullName;

    @Column(name = "DATEOFBIRTH")
    private String dateOfBirth;

    @Column(name = "IDCODE")
    private String idCode;

    @Column(name = "MOBILESMS")
    private String mobileSms;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "STATUS")
    private String status;

}
