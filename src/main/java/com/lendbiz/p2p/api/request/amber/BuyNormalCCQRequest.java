package com.lendbiz.p2p.api.request.amber;

import lombok.Data;

@Data
public class BuyNormalCCQRequest extends OrderCCQRequest{
    private String orderamt;
}
