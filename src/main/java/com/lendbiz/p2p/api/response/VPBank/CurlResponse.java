package com.lendbiz.p2p.api.response.VPBank;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CurlResponse {
    private String type;
    private String data;
}
