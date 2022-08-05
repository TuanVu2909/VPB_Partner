package com.lendbiz.p2p.api.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Setter
@Getter
@Data
@Table(name = "products9pay")
public class Product9PayCardEntity {
    @Id
    @Column(name = "PID")
    private String id;
    @Column(name = "PRODUCT_NAME")
    private String name;
    @Column(name = "DESCRIPTION")
    private String des;
    @Column(name = "PRICE")
    private long price;
    @Column(name = "PROVIDER_ID")
    private int provider_id;
    @Column(name = "SERVICE_ID")
    private int service_id;
}
