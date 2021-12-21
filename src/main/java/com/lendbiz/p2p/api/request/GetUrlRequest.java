package com.lendbiz.p2p.api.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GetUrlRequest {
    private String typeContract;
    private String docId;
}