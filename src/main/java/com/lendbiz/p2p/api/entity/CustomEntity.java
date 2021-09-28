package com.lendbiz.p2p.api.entity;

public class CustomEntity {

    private Object result;

    public CustomEntity() {
    }

    public CustomEntity(Object object) {
        super();
        this.result = object;
    }

    public Object getResult() {
        return this.result;
    }

    public void setResult(Object object) {
        this.result = object;
    }

    @Override
    public String toString() {
        return "{" + " result='" + getResult() + "'" + "}";
    }

}
