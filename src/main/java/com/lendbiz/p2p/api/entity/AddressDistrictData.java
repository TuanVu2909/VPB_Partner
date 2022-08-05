package com.lendbiz.p2p.api.entity;

import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AddressDistrictData {
    private String pkPostcode;
    private String pkDistrict;
    private String pkProvince;

}
