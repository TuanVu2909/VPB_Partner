package com.lendbiz.p2p.api.request.hyperlead;

import lombok.Data;

@Data
public class HypPostBack {
    private String api_key;
    private String postback_type;
    private String click_id;
    private String transaction_id;
    private int status_code;
    private String status_message;
}
