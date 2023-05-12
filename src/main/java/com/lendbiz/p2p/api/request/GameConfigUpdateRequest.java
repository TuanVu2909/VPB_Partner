package com.lendbiz.p2p.api.request;

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
public class GameConfigUpdateRequest {

    private int id;
    private double rate;
    private int giftId;
    private int groupId;
    private int status;

    private String custId;
    private String name;
    private int prizeAmount;
    private String fromTime;
    private String toTime;
    private String toDate;
    private String fromDate;
    private int maxPrize;
    private int gameId;

}
