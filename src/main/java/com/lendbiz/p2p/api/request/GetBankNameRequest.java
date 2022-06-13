package com.lendbiz.p2p.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetBankNameRequest {

    private String accountNumber;
    private String cardNumber;
    private String bankCode;
    private AddInfo[] addInfo;
    private String checksum;
}
