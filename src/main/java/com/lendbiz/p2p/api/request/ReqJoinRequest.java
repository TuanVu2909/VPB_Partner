package com.lendbiz.p2p.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReqJoinRequest {

    private String type;
    private String fullName;
    private String sex;
    private String dob;
    private String place;
    private String idCode;
    private String idDate;
    private String idPlace;
    private String orgAddress;
    private String address;
    private String idWard;
    private String idDistrict;
    private String phone;
    private String mobile;
    private String email;
    private String bankAccount;
    private String bank;
    private String taxNo;
    private String online;
    private String matchOrdSms;
    private String productInfo;
    private String consultalInfo;
    private String officeName;
    private String position;
    private String job;
    private String exFullName;
    private String exPosition;
    private String exJob;
    private String exMobile;
    private String exEmail;
    private String exAddress;
    private String exBank;
    private String exStk;
    private String password;

}
