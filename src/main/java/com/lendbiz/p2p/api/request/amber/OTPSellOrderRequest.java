package com.lendbiz.p2p.api.request.amber;

import lombok.Data;

@Data
public class OTPSellOrderRequest extends OTPIdentityRequest {
    private String OTP;
}
