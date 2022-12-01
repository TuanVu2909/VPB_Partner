package com.lendbiz.p2p.api.request;

import com.lendbiz.p2p.api.request.accesstrade.Item;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ATUpdateRequest {
    private String transactionID;
    private String status;
    private Item[] items;
}
