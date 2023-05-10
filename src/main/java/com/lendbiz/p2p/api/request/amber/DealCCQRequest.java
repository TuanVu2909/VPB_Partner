package com.lendbiz.p2p.api.request.amber;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DealCCQRequest extends CCQInfoRequest{
    private String custodycd;
    private String orderid;
}
