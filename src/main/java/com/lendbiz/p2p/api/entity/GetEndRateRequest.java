package com.lendbiz.p2p.api.entity;

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
public class GetEndRateRequest {
    private int calType; // 1 no term else other
    private float amount;
    private String payType;
    private String startDate;
    private int investId;

}
