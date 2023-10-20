package com.lendbiz.p2p.api.response.VPBank;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VPBResDTO {
    private String status;
    private String errorCode;
    private String errorMessage;
    private String transactionId;
}
