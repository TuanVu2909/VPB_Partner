package com.lendbiz.p2p.api.entity;

import java.sql.Date;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "CFRELATION")
@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CfRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "CUSTID")
    private String cusId;

    @Column(name = "RETYPE")
    private String reType;

    @Column(name = "RECUSTID")
    private String reCode;

    @Column(name = "DESCRIPTION")
    private Date description;

    @Column(name = "FULLNAME")
    private String fullName;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "TELEPHONE")
    private String phone;

    @Column(name = "LICENSENO")
    private String licenseNo;

    @Column(name = "LNIDDATE")
    private String dateOfBirth;

    @Column(name = "LNPLACE")
    private String lnPlace;

    @Column(name = "ACTIVES")
    private String active;

    @Column(name = "ACDATE")
    private String activeDate;

    @Column(name = "SIGNATURE")
    private String signature;

    @Column(name = "SUMDEBT")
    private int sumDeBT;

    @Column(name = "MARRIED")
    private String married;

    @Column(name = "SMERATE")
    private int smeRate;

    @Column(name = "SEX")
    private String sex;

    @Column(name = "PASSPORTNO")
    private String idCode;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "WARD")
    private Integer ward;

    @Column(name = "DISTRICT")
    private Integer district;

    @Column(name = "PROVINCE")
    private String province;
    
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    @Column(name = "PASSPORTDATE")
    private Date idDate;

    @Column(name = "PASSPORTPLACE")
    private String idPlace;

    @Column(name = "CITIZENID")
    private String citizenId;

    @Column(name = "CITIZENIDDATE")
    private String citizenIdDate;


    @Column(name = "CITIZENIDPLACE")
    private String citizenIdPlace;

    @Column(name = "FACEBOOK")
    private String facebook;

    @Column(name = "YOUTUBE")
    private String youtube;

    @Column(name = "COUNTRY")
    private String country;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "PSTATUS")
    private String pStatus;


}
