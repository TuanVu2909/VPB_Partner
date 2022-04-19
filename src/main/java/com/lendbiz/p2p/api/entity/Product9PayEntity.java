package com.lendbiz.p2p.api.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PRODUCTS9PAY")
@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product9PayEntity {
    @Id
    @Column(name = "PID")
    private String id;
    @Column(name = "PRICE")
    private long price;
    @Column(name = "PRODUCT_NAME")
    private String name;
    @Column(name = "DESCRIPTION")
    private String des;
    @Column(name = "PROVIDER_ID")
    private int provider_id;
    @Column(name = "SERVICE_ID")
    private int service_id;

}
