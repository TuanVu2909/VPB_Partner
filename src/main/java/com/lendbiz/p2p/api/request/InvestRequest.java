package com.lendbiz.p2p.api.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Data
@ToString
public class InvestRequest {

    private String typeContract;
    private String contractInfo;
    private String amount;
    private String income;
    private String remainingMonth;
    private String cusId;

}
