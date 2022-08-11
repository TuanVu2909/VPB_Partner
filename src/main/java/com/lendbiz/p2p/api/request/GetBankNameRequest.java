package com.lendbiz.p2p.api.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GetBankNameRequest {

    private String accountNumber;
    private String cardNumber;
    private String bankCode;
    private AddInfoList[] addInfo;
    private String checksum;
}
