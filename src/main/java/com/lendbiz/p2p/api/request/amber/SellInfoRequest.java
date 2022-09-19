package com.lendbiz.p2p.api.request.amber;

import lombok.Data;

@Data
public class SellInfoRequest {
    private String custodycd;
    private String symbol;
    private String qtty;
    private String srtype;
}
