package com.lendbiz.p2p.api.model.listbvaddbasevm;

import com.fasterxml.jackson.annotation.*;

public class QuocTich {
    private String quocTichCode;
    private String quocTichName;

    @JsonProperty("quocTichCode")
    public String getQuocTichCode() { return quocTichCode; }
    @JsonProperty("quocTichCode")
    public void setQuocTichCode(String value) { this.quocTichCode = value; }

    @JsonProperty("quocTichName")
    public String getQuocTichName() { return quocTichName; }
    @JsonProperty("quocTichName")
    public void setQuocTichName(String value) { this.quocTichName = value; }
}
