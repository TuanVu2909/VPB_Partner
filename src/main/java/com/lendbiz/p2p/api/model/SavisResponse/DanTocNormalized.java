package com.lendbiz.p2p.api.model.SavisResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DanTocNormalized {
    private Value value;
    private Huyen huyen;
    private String valueUnidecode;
    private Huyen tinh;
    private Xa xa;
    private Day month;
    private Day year;
    private Day day;

    @JsonProperty("value")
    public Value getValue() {
        return value;
    }

    @JsonProperty("value")
    public void setValue(Value value) {
        this.value = value;
    }

    @JsonProperty("huyen")
    public Huyen getHuyen() {
        return huyen;
    }

    @JsonProperty("huyen")
    public void setHuyen(Huyen value) {
        this.huyen = value;
    }

    @JsonProperty("value_unidecode")
    public String getValueUnidecode() {
        return valueUnidecode;
    }

    @JsonProperty("value_unidecode")
    public void setValueUnidecode(String value) {
        this.valueUnidecode = value;
    }

    @JsonProperty("tinh")
    public Huyen getTinh() {
        return tinh;
    }

    @JsonProperty("tinh")
    public void setTinh(Huyen value) {
        this.tinh = value;
    }

    @JsonProperty("xa")
    public Xa getXa() {
        return xa;
    }

    @JsonProperty("xa")
    public void setXa(Xa value) {
        this.xa = value;
    }

    @JsonProperty("month")
    public Day getMonth() {
        return month;
    }

    @JsonProperty("month")
    public void setMonth(Day value) {
        this.month = value;
    }

    @JsonProperty("year")
    public Day getYear() {
        return year;
    }

    @JsonProperty("year")
    public void setYear(Day value) {
        this.year = value;
    }

    @JsonProperty("day")
    public Day getDay() {
        return day;
    }

    @JsonProperty("day")
    public void setDay(Day value) {
        this.day = value;
    }
}