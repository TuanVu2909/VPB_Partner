package com.lendbiz.p2p.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
public class UpdateAccountRequest {

    private String custId;
    private String fullName;
    private String sex;
    private String idCode;
    private String idDate;
    private String idPlace;
    private String idExp;
    private String dob;
    private String address;

}
