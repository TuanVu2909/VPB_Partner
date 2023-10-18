package com.lendbiz.p2p.api.request;

import lombok.Data;

@Data
public class VPBbankRequest {
    private String masterAccountNumber;
    private String virtualAccountNumber;
    private String virtualName;
    private String virtualAlkey;
    private String amount;
    private String bookingDate;
    private String transactionDate;
    private String transactionId;
    private String remark;
}
