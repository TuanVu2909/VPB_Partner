package com.lendbiz.p2p.api.response;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lendbiz.p2p.api.response.ninepay.CardInfo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class The9PayIPNResponse {
    private long amount;
    private Object amountForeign;
    private Object amountOriginal;
    private long amountRequest;
    private Object bank;
    private String cardBrand;
    private CardInfo cardInfo;
    private OffsetDateTime createdAt;
    private String currency;
    private String description;
    private String errorCode;
    private Object excRate;
    private Object failureReason;
    private Object foreignCurrency;
    private String invoiceNo;
    private String lang;
    private String method;
    private long paymentNo;
    private long status;
    private Object tenor;
}
