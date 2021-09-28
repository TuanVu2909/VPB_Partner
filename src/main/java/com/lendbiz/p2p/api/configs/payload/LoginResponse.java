package com.lendbiz.p2p.api.configs.payload;

import lombok.Data;

@Data
public class LoginResponse {
    private Object userData;
    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";

    public LoginResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}