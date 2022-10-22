package com.lendbiz.p2p.api.request.baovietEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReceiverUser {
    private String name;
    private String address;
    private AddressDistrictData addressDistrictData;
    private String mobile;
    private String email;
    private String mobileHide;
    private String emailHide;
    private String ngaySinh;
}
