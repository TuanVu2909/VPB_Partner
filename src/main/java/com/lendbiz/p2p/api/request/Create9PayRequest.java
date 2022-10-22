package com.lendbiz.p2p.api.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Create9PayRequest {

    private String amount;
    private String description;
    private boolean isCardToken;
    private String cardTokenValue;

}