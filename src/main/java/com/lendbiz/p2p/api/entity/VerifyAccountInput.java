package com.lendbiz.p2p.api.entity;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VerifyAccountInput {
    private String custId;
    private String verifyCode;
}
