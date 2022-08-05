package com.lendbiz.p2p.api.request.baovietEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AddressDistrictData {
    private String pkPostcode;
    private String pkDistrict;
    private String pkProvince;
}
