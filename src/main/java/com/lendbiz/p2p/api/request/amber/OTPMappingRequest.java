package com.lendbiz.p2p.api.request.amber;

import lombok.Getter;

@Getter
public class OTPMappingRequest extends IdentityCustomerRequest {
    private String otp;
}
