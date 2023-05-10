package com.lendbiz.p2p.api.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomEntity {

    private String errorCode;
    private String errorMessage;
    // private String data;
    private Object result;

    public CustomEntity() {
    }

    public CustomEntity(Object object) {
        super();
        this.result = object;
    }

    @Override
    public String toString() {
        return "{" + " result='" + getResult() + "'" + "}";
    }

}
