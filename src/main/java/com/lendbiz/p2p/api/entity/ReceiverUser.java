package com.lendbiz.p2p.api.entity;

import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
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
