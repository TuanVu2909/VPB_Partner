package com.lendbiz.p2p.api.request.amber;

import lombok.Data;

@Data
public class CCQInfoRequest {
    protected String fromdate;
    protected String todate;
    protected String symbol;
}
