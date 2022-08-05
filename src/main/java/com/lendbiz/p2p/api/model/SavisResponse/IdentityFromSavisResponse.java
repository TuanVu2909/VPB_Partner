package com.lendbiz.p2p.api.model.SavisResponse;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IdentityFromSavisResponse {
    private SavisOutput[] output;
    private double time;
    private String apiVersion;
    private String mlchainVersion;
    private UUID requestID;

    @JsonProperty("output")
    public SavisOutput[] getOutput() {
        return output;
    }

    @JsonProperty("output")
    public void setOutput(SavisOutput[] value) {
        this.output = value;
    }

    @JsonProperty("time")
    public double getTime() {
        return time;
    }

    @JsonProperty("time")
    public void setTime(double value) {
        this.time = value;
    }

    @JsonProperty("api_version")
    public String getAPIVersion() {
        return apiVersion;
    }

    @JsonProperty("api_version")
    public void setAPIVersion(String value) {
        this.apiVersion = value;
    }

    @JsonProperty("mlchain_version")
    public String getMlchainVersion() {
        return mlchainVersion;
    }

    @JsonProperty("mlchain_version")
    public void setMlchainVersion(String value) {
        this.mlchainVersion = value;
    }

    @JsonProperty("request_id")
    public UUID getRequestID() {
        return requestID;
    }

    @JsonProperty("request_id")
    public void setRequestID(UUID value) {
        this.requestID = value;
    }
}
