package com.lendbiz.p2p.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.ToString;

@ToString
public class NinePayCardInfo {
    @JsonProperty("token")
    public String token;
    @JsonProperty("issuer")
    public String issuer;
    @JsonProperty("scheme")
    public String scheme;
    @JsonProperty("card_name")
    public String card_name;
    @JsonProperty("card_brand")
    public String card_brand;
    @JsonProperty("card_number")
    public String card_number;
    @JsonProperty("expire_date")
    public String expire_date;

}
