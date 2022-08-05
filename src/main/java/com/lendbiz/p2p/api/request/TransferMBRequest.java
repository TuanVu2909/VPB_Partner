package com.lendbiz.p2p.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferMBRequest {
    private String bankCode;
    private String debitName;
    private String debitResourceNumber;
    private String debitType;
    private String creditName;
    private String creditResourceNumber;
    private String creditType;
    private String disCountCode;
    private String object;
    private String remark;
    private String serviceType;
    private String transferAmount;
    private String transferFee;
    private String transferType;
    private String customerType;
    private long customerLevel;
    private AddInfoList[] addInfoList;
}
