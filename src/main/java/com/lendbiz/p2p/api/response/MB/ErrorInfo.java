package com.lendbiz.p2p.api.response.MB;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorInfo {
    private String code;
    private String message;
    private Object target;
    private Object details;
}
