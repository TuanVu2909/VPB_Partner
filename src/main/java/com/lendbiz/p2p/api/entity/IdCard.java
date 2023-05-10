package com.lendbiz.p2p.api.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "IDCARD_INFO")
public class IdCard {

    @Id
    @Column(name = "ID")
    private String id;
    @Column(name = "IDENTITY")
    private String Identity;
    @Column(name = "CUSTID")
    private String custID;
    @Column(name = "FULL_NAME")
    private String fullName;
    @Column(name = "BIRTHDAY")
    private Date birthDay;
    @Column(name = "ADDRESS")
    private String address;
    @Column(name = "DOMICILE")
    private String domicile;
    @Column(name = "IDCARD_TYPE")
    private String type;
    @Column(name = "DATE_ISSUED")
    private Date dateRange;
    @Column(name = "EXPIRATION_DATE")
    private Date expirationDate;
    @Column(name = "ISSUED_BY")
    private String issuedBy;

    public IdCard(String id,String custid , String identity, String fullName, Date birthDay, String address, String domicile, String type, Date dateRange, Date expirationDate, String issuedBy) {
        this.id = id;
        Identity = identity;
        this.fullName = fullName;
        this.birthDay = birthDay;
        this.address = address;
        this.domicile = domicile;
        this.type = type;
        this.dateRange = dateRange;
        this.expirationDate = expirationDate;
        this.issuedBy = issuedBy;
        this.custID = custid;
    }

    public IdCard() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdentity() {
        return Identity;
    }

    public String getCustID() {
        return custID;
    }

    public void setCustID(String custID) {
        this.custID = custID;
    }

    public void setIdentity(String identity) {
        Identity = identity;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDomicile() {
        return domicile;
    }

    public void setDomicile(String domicile) {
        this.domicile = domicile;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDateRange() {
        return dateRange;
    }

    public void setDateRange(Date dateRange) {
        this.dateRange = dateRange;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(String issuedBy) {
        this.issuedBy = issuedBy;
    }

    @Override
    public String toString() {
        return "IdCard{" +
                "id='" + id + '\'' +
                ", Identity='" + Identity + '\'' +
                ", custID='" + custID + '\'' +
                ", fullName='" + fullName + '\'' +
                ", birthDay=" + birthDay +
                ", address='" + address + '\'' +
                ", domicile='" + domicile + '\'' +
                ", type='" + type + '\'' +
                ", dateRange=" + dateRange +
                ", expirationDate=" + expirationDate +
                ", issuedBy='" + issuedBy + '\'' +
                '}';
    }
}
