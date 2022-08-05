package com.lendbiz.p2p.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.ToString;

@ToString
public class NinePayResult {
    @JsonProperty("amount")
    public String amount;
    @JsonProperty("amount_foreign")
    public String amount_foreign;
    @JsonProperty("amount_request")
    public String amount_request;
    @JsonProperty("card_brand")
    public String card_brand;
    @JsonProperty("card_info")
    public NinePayCardInfo card_info;
    @JsonProperty("created_at")
    public String created_at;
    @JsonProperty("currency")
    public String currency;
    @JsonProperty("description")
    public String description;
    @JsonProperty("exc_rate")
    public String exc_rate;
    @JsonProperty("failure_reason")
    public String failure_reason;
    @JsonProperty("foreign_currency")
    public String foreign_currency;
    @JsonProperty("invoice_no")
    public String invoice_no;
    @JsonProperty("lang")
    public String lang;
    @JsonProperty("method")
    public String method;
    @JsonProperty("payment_no")
    public String payment_no;
    @JsonProperty("status")
    public String status;

}
