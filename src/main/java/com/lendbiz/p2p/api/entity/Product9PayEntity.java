package com.lendbiz.p2p.api.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "PRODUCTS9PAY")
@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "CATEGORY_ID",referencedColumnName = "PID")
    private Service9Pay service9Pay;
}
