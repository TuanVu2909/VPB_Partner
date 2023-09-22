package com.lendbiz.p2p.api.model.listbvaddbasevm;

import com.fasterxml.jackson.annotation.*;

public class InvoiceInfo {
    private String check;
    private Object name;
    private Object company;
    private Object taxNo;
    private Object address;
    private Object accountNo;

    @JsonProperty("check")
    public String getCheck() { return check; }
    @JsonProperty("check")
    public void setCheck(String value) { this.check = value; }

    @JsonProperty("name")
    public Object getName() { return name; }
    @JsonProperty("name")
    public void setName(Object value) { this.name = value; }

    @JsonProperty("company")
    public Object getCompany() { return company; }
    @JsonProperty("company")
    public void setCompany(Object value) { this.company = value; }

    @JsonProperty("taxNo")
    public Object getTaxNo() { return taxNo; }
    @JsonProperty("taxNo")
    public void setTaxNo(Object value) { this.taxNo = value; }

    @JsonProperty("address")
    public Object getAddress() { return address; }
    @JsonProperty("address")
    public void setAddress(Object value) { this.address = value; }

    @JsonProperty("accountNo")
    public Object getAccountNo() { return accountNo; }
    @JsonProperty("accountNo")
    public void setAccountNo(Object value) { this.accountNo = value; }
}
