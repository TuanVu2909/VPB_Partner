package com.lendbiz.p2p.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MbAccessToken {
    private String access_token;
    private String expires_in;
    private String scope;
    private String issued_at;
    private String token_type;

}