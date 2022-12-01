package com.lendbiz.p2p.api.response.amber;

import lombok.Data;

@Data
public class AFMToken {
    private String access_token;
    private String refresh_token;
    private int expires_in;
    private String token_type;
}
