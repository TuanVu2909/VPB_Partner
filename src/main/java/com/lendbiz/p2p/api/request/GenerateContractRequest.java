package com.lendbiz.p2p.api.request;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class GenerateContractRequest {

    private String typeContract;
    private String contractInfo;
    private String amount;
    private String income;
    private String remainingMonth;
    private String name;
    private String startDate;
    private String interest;
    private String minValueInterest;
    private String maxValueInterest; 
    private String differenceProfit; 
    private String trueValueInterest; 
    private String profitCus; 
    private String profitCusLate;
    private String dayInterset;
    private String moneyGiven;
    private String realMoneyGiven;
    private String rate;
    private String term;
    private String rateTrue;
    private String endDate;
    private String docNo;
    // Phương thức thanh toán theo ky
    private String pttt;
    private String ttncn;
    private String isLBC;
    private String isLBC2;
    private String fee;




}
