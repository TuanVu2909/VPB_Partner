package com.lendbiz.p2p.api.entity;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Input9Pay {
    private String productId;
    private String quantity;
    private String cif;
    private String phone;
    private double totalAmount;

}
