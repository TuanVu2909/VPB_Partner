package com.lendbiz.p2p.api.response.VPBank;

import lombok.AllArgsConstructor;
import lombok.Data;


import java.sql.Timestamp;
@Data
@AllArgsConstructor
public class VPBLogsDTO {
    private String   recordType = "0002";
    private String refNumber;
    private String transferType;
    private String ft;
    private String debitAccount;
    private String benAccount;
    private String benName;
    private String benBankName;
    private String bankCode;
    private long   amount;
    private String transDate;
    private String checkSum;

    public VPBLogsDTO() {

    }
}
