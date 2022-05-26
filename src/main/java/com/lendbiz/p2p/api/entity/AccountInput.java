package com.lendbiz.p2p.api.entity;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AccountInput {
    private String custId;
    private String productId;
    private String term;
    private String amt;
    private String rate;
    private String contractId;
    private String payType;
    private String pv_refId;
    private String coin;
    private String doc_no;
    
}
