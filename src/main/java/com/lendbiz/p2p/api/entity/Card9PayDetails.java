package com.lendbiz.p2p.api.entity;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Card9PayDetails {
    private String price;
    private String discount;
    private String amount;
    private String card_seri;
    private String card_code;
    private String expired_at;
}
