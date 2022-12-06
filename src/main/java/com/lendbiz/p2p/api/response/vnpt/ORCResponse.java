package com.lendbiz.p2p.api.response.vnpt;

import lombok.Data;

@Data
public class ORCResponse {
    private String idNo;
    private int typeId;
    private String cardType;
    private String name;
    private String birthDay;
    private String nationality;
    private String gender;
    private String originLocation;
    private String recentLocation;
    private String issueDate;
    private String validDate;
    private String issuePlace;
}
