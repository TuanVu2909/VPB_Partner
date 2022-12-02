package com.lendbiz.p2p.api.entity;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WithdrawBearRequest {
    private String custId;
    private String amt;
    private String docNo;
    
}
