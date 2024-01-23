package com.lendbiz.p2p.api.response.VPBank;

import lombok.Data;

@Data
public class TranferDTO {
    private String referenceNumber;
    private String service;
    private String transactionType;
    private String sourceNumber;
    private String targetNumber;
    private long amount;
    private String currency;
    private String remark;
    private String signature;
}
