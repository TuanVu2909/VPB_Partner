package com.lendbiz.p2p.api.request;

import lombok.Data;

@Data
public class SearchCmdRequest {

    private String searchCode;
    private String fieldName;
    private String operator;
    private String value;
}
