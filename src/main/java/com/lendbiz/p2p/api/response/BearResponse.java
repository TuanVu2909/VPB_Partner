package com.lendbiz.p2p.api.response;

import com.lendbiz.p2p.api.request.BearRequest;
import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@AllArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
public class BearResponse {
    private String term;
    private String pid;
    private String amt;
    private String rate;
    private String payType;
    private String day;
    private String profitPerDay;
    private String monthlyProfit;
    private String profit;
    private String total;
    private String startDate;
    private String endDate;




}
