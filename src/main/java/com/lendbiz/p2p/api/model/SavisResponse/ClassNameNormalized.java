package com.lendbiz.p2p.api.model.SavisResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClassNameNormalized {
    private String code;
    private long value;

    @JsonProperty("code")
    public String getCode() {
        return code;
    }

    @JsonProperty("code")
    public void setCode(String value) {
        this.code = value;
    }

    @JsonProperty("value")
    public long getValue() {
        return value;
    }

    @JsonProperty("value")
    public void setValue(long value) {
        this.value = value;
    }
}
