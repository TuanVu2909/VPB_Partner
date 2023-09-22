package com.lendbiz.p2p.api.model.listbvaddbasevm;

import com.fasterxml.jackson.annotation.*;

public class ReceiverUser {
    private String name;
    private String address;
    private String email;
    private String mobile;

    @JsonProperty("name")
    public String getName() { return name; }
    @JsonProperty("name")
    public void setName(String value) { this.name = value; }

    @JsonProperty("address")
    public String getAddress() { return address; }
    @JsonProperty("address")
    public void setAddress(String value) { this.address = value; }

    @JsonProperty("email")
    public String getEmail() { return email; }
    @JsonProperty("email")
    public void setEmail(String value) { this.email = value; }

    @JsonProperty("mobile")
    public String getMobile() { return mobile; }
    @JsonProperty("mobile")
    public void setMobile(String value) { this.mobile = value; }
}
