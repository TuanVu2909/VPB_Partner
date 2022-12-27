package com.lendbiz.p2p.api.entity.vnpt;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "BG_EKYC")
public class BgEkycEntity {
    @Id
    @Column(name = "MOBILESMS")
    private String mobileSms;
    @Column(name = "ID_NO")
    private String idNo;
    @Column(name = "TYPE_ID")
    private int typeId;
    @Column(name = "CARD_TYPE")
    private String cardType;
    @Column(name = "NAME")
    private String name;
    @Column(name = "BIRTH_DAY")
    private String birthDay;
    @Column(name = "NATIONALITY")
    private String nationality;
    @Column(name = "GENDER")
    private String gender;
    @Column(name = "ORIGIN_LOCATION")
    private String originLocation;
    @Column(name = "RECENT_LOCATION")
    private String recentLocation;
    @Column(name = "ISSUE_DATE")
    private String issueDate;
    @Column(name = "VALID_DATE")
    private String validDate;
    @Column(name = "ISSUE_PLACE")
    private String issuePlace;
    @Column(name = "API_COMPARE")
    private int apiCompare;
    @Column(name = "API_ORC")
    private int apiOrc;
    @Column(name = "COMPARE_SUCCESS")
    private String compareSuccess;
    @Column(name = "ORC_SUCCESS")
    private String orcSuccess;
    @Column(name = "EKYC_DATE")
    private String ekycDate;
}
