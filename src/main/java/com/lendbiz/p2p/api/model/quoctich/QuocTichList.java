package com.lendbiz.p2p.api.model.quoctich;

import com.fasterxml.jackson.annotation.*;

public class QuocTichList {
    private String quocTichCode;
    private String quocTichName;

    @JsonProperty("QUOC_TICH_CODE")
    public String getQuocTichCode() { return quocTichCode; }
    @JsonProperty("QUOC_TICH_CODE")
    public void setQuocTichCode(String value) { this.quocTichCode = value; }

    @JsonProperty("QUOC_TICH_NAME")
    public String getQuocTichName() { return quocTichName; }
    @JsonProperty("QUOC_TICH_NAME")
    public void setQuocTichName(String value) { this.quocTichName = value; }
}
