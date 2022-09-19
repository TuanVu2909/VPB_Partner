package com.lendbiz.p2p.api.request.amber;

import lombok.Data;

@Data
public class OTPMappingRequest extends IdentityCustomerRequest {
    private String OTP;
}
