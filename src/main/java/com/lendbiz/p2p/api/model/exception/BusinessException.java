package com.lendbiz.p2p.api.model.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    protected String        status;

    protected  Object      data;

    public BusinessException(String status) {
        super();
        this.status = status;
    }
    
    public BusinessException(String status, String message) {
        super(message);
        this.status = status;
    }

    public BusinessException(String status, String data, String message) {
        super(data + ": " + message);
        this.status = status;
    }
}
