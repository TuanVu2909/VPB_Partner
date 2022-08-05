package com.lendbiz.p2p.api.request;

import java.time.LocalDate;

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
public class TranInfoRequest {

    private String requestId;
    private String amount;
    private LocalDate transactionDate;
    private LocalDate bankingDate;
    private String accountNumber;
    private String content;
    private String bankCode;
    private String channel;
    private String sourceName;
    private String SourceIdNo;
    private String receiveName;
    private String currency;
    private String ft;
    private String isRevert;
    private String tranType;

}
