package com.lendbiz.p2p.api.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CreateNotificationOneSignalRequest {
    
    private String appId;
    private String includePlayerIds;
    private String contents;
    private String data;
}