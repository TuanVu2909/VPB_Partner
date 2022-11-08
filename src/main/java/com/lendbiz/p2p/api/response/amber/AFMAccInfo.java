package com.lendbiz.p2p.api.response.amber;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AFMAccInfo {
    private String custodycd;
    private String status;
    private String status_vsd;
}
