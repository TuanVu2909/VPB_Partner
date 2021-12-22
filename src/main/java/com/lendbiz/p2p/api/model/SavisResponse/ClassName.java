package com.lendbiz.p2p.api.model.SavisResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClassName {
    private ClassNameNormalized normalized;
    private long confidence;
    private String value;

    @JsonProperty("normalized")
    public ClassNameNormalized getNormalized() {
        return normalized;
    }

    @JsonProperty("normalized")
    public void setNormalized(ClassNameNormalized value) {
        this.normalized = value;
    }

    @JsonProperty("confidence")
    public long getConfidence() {
        return confidence;
    }

    @JsonProperty("confidence")
    public void setConfidence(long value) {
        this.confidence = value;
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
