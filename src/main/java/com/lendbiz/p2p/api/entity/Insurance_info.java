package com.lendbiz.p2p.api.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "insurance_info")
@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Insurance_info {
    @Id
    @Column(name = "CUSTID")
    private String custid;

    @Column(name = "GYCBHNUMBER")
    private String gycNumber;

    @Column(name = "STATUSPOLICY")
    private String statusPolicy;
}
