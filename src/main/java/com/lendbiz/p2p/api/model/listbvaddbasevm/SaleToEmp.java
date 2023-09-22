package com.lendbiz.p2p.api.model.listbvaddbasevm;

import com.fasterxml.jackson.annotation.*;

public class SaleToEmp {
    private Object empID;
    private String empName;

    @JsonProperty("empId")
    public Object getEmpID() { return empID; }
    @JsonProperty("empId")
    public void setEmpID(Object value) { this.empID = value; }

    @JsonProperty("empName")
    public String getEmpName() { return empName; }
    @JsonProperty("empName")
    public void setEmpName(String value) { this.empName = value; }
}
