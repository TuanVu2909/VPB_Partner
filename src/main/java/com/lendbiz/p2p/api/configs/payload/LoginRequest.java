package com.lendbiz.p2p.api.configs.payload;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}