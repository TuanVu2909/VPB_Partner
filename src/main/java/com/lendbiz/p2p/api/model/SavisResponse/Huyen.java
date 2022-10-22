package com.lendbiz.p2p.api.model.SavisResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Huyen {
    private Value code;
    private String valueUnidecode;
    private String value;

    @JsonProperty("code")
    public Value getCode() {
        return code;
    }

    @JsonProperty("code")
    public void setCode(Value value) {
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