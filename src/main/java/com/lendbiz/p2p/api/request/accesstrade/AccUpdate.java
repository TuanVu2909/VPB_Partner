package com.lendbiz.p2p.api.request.accesstrade;

import lombok.Data;

@Data
public class AccUpdate {
    private String transaction_id;
    private String conversion_id;
    private String status;
    private String rejected_reason;
}
