package com.lendbiz.p2p.api.request.amber;

import lombok.Data;

@Data
public class OrderCCQRequest {
    protected String txdate;
    protected String symbol;
    protected String custodycd;
    protected String srtype;
    protected String exectype;
}
