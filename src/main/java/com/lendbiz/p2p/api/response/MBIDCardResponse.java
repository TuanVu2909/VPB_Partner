package com.lendbiz.p2p.api.response;

import com.lendbiz.p2p.api.response.MB.ErrorInfo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MBIDCardResponse {
    private String responseID;
    private ErrorInfo errorInfo;
    private String cardID;
    private String cardNumber;
}
