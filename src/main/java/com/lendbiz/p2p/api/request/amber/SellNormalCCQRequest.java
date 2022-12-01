package com.lendbiz.p2p.api.request.amber;

import lombok.Data;

@Data
public class SellNormalCCQRequest extends OrderCCQRequest {
    private String orderqtty;
}
