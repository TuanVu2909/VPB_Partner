package com.lendbiz.p2p.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MbTransHistoryRequest {
    private String requestId;
    private String tranType;
    private String bankTransId;
    private String ft;
    private String tranDate;
    private String amount;
    private String currency;
    private String tranDetail;
    private int status;
    private String debitSourceNumber;
    private String debitSourceName;
    private String creditSourceNumber;
    private String creditSourceName;
    private String creditIdCard;
    private String creditFastName;
    private String creditFastBankName;
    private String channel;
    private String channelName;
    private int reconcileStatus;
    private int requestType;
    private String addInfo;

}
