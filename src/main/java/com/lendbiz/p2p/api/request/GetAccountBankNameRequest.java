package com.lendbiz.p2p.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetAccountBankNameRequest {
    private String bankCode;
    private String bankAccount;
    private Boolean fast;

}
