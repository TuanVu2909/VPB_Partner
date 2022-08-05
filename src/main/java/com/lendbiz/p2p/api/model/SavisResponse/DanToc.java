package com.lendbiz.p2p.api.model.SavisResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DanToc {
    private double confidence;
    private String valueUnidecode;
    private String value;
    private DanTocNormalized normalized;
    private Validate validate;

    @JsonProperty("confidence")
    public double getConfidence() {
        return confidence;
    }

    @JsonProperty("confidence")
    public void setConfidence(double value) {
        this.confidence = value;
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

    @JsonProperty("normalized")
    public DanTocNormalized getNormalized() {
        return normalized;
    }

    @JsonProperty("normalized")
    public void setNormalized(DanTocNormalized value) {
        this.normalized = value;
    }

    @JsonProperty("validate")
    public Validate getValidate() {
        return validate;
    }

    @JsonProperty("validate")
    public void setValidate(Validate value) {
        this.validate = value;
    }
}
