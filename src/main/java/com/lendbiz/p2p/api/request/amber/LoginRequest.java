package com.lendbiz.p2p.api.request.amber;

import lombok.Data;

@Data
public class LoginRequest {
    private String grant_type;
    private String client_id;
    private String client_secret;
    private String password;
    private String username;
}
