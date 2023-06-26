package com.lendbiz.p2p.api.request;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@AllArgsConstructor
@Getter
@NoArgsConstructor
@ToString
public class GetRateByDayRequest {

    private String custId;
    private String date;

}
