package com.lendbiz.p2p.api.response;

import lombok.*;

import javax.persistence.Column;
@AllArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
public class InvestAssetResponse {

    private String documentno;

    private String amount;

    private String term;

    private String rate;

    private String start_date;

    private String end_date;

    private String profit;


}
