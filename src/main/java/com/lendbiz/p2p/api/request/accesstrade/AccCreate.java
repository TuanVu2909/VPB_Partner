package com.lendbiz.p2p.api.request.accesstrade;

import lombok.Data;

import java.util.List;

@Data
public class AccCreate {
    private String transaction_id;
    private String conversion_id;
    private String tracking_id;
    private String transaction_time;
    private String conversion_result_id;
    private float transaction_value;
    private float transaction_discount;
    private List<Object> items;
}
