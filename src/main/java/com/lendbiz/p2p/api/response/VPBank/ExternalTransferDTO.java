package com.lendbiz.p2p.api.response.VPBank;

import lombok.Data;

@Data
public class ExternalTransferDTO extends TranferDTO {
    private String bankCode;
}
