package com.lendbiz.p2p.api.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class SavisVerifyOtpRequest {
    private String userName;
    private String otp;
}
