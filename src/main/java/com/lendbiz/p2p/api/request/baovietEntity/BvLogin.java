package com.lendbiz.p2p.api.request.baovietEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class BvLogin {
    private String username;
    private String password;
}
