package com.lendbiz.p2p.api.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GmFundNavRequest {
    private String fundCode;
    private String nav;
    private String navDate;

}
