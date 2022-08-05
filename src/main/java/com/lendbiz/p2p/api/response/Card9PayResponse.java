package com.lendbiz.p2p.api.response;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Card9PayResponse {
    private String request_id;
    private String partner_id;
    private String transaction_id;
    private String product_id;

    private String quantity;
    private String price;
    private String discount;
    private String amount;
    private String cards;
    private String status;
    private String created_at;
    private String signature;


}
