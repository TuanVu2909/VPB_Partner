package com.lendbiz.p2p.api.model.SavisResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Liveness {
    private String liveness;

    @JsonProperty("liveness")
    public String getLiveness() {
        return liveness;
    }

    @JsonProperty("liveness")
    public void setLiveness(String value) {
        this.liveness = value;
    }
}
