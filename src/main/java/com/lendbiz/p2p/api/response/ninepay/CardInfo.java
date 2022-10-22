package com.lendbiz.p2p.api.response.ninepay;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardInfo {
    private String token;
    private String cardName;
    private String hashCard;
    private String cardBrand;
    private String cardNumber;
}
