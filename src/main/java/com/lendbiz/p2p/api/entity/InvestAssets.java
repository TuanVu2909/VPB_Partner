package com.lendbiz.p2p.api.entity;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InvestAssets {
    private String documentno;
    private String amount;
    private String profit;
    private String term;
    private String rate;
    private String start_date;
    private String end_date;


}
