package com.lendbiz.p2p.api.model.SavisResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Xa {
    private long code;
    private String valueUnidecode;
    private String value;

    @JsonProperty("code")
    public long getCode() {
        return code;
    }

    @JsonProperty("code")
    public void setCode(long value) {
        this.code = value;
    }

    @JsonProperty("value_unidecode")
    public String getValueUnidecode() {
        return valueUnidecode;
    }

    @JsonProperty("value_unidecode")
    public void setValueUnidecode(String value) {
        this.valueUnidecode = value;
    }

    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    @JsonProperty("value")
    public void setValue(String value) {
        this.value = value;
    }
}
