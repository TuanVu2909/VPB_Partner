package com.lendbiz.p2p.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReqJoinRequest {

    private String mobile;
    private String deviceId;
    private String utmSource;
    private String utmMedium;

}
