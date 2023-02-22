package com.lendbiz.p2p.api.request.accesstrade;

import lombok.Data;

import java.util.List;

@Data
public class AccUpdate {
    private String transaction_id;
    private int status;
    private String rejected_reason;
    private List<Object> items;
}
