package com.lendbiz.p2p.api.model.bvpremium;

import com.fasterxml.jackson.annotation.*;

public class BvErrorResponse {
    private String timestamp;
    private String message;
    private String stackTrace;
    private String errorCode;
    private String fieldName;

    @JsonProperty("timestamp")
    public String getTimestamp() { return timestamp; }
    @JsonProperty("timestamp")
    public void setTimestamp(String value) { this.timestamp = value; }

    @JsonProperty("message")
    public String getMessage() { return message; }
    @JsonProperty("message")
    public void setMessage(String value) { this.message = value; }

    @JsonProperty("stackTrace")
    public String getStackTrace() { return stackTrace; }
    @JsonProperty("stackTrace")
    public void setStackTrace(String value) { this.stackTrace = value; }

    @JsonProperty("errorCode")
    public String getErrorCode() { return errorCode; }
    @JsonProperty("errorCode")
    public void setErrorCode(String value) { this.errorCode = value; }

    @JsonProperty("fieldName")
    public String getFieldName() { return fieldName; }
    @JsonProperty("fieldName")
    public void setFieldName(String value) { this.fieldName = value; }
}
