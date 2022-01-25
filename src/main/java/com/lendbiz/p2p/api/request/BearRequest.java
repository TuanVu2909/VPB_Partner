package com.lendbiz.p2p.api.request;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
public class BearRequest {
    private String term;
    private String pid;
    private String payType;
    private String amt;
}
