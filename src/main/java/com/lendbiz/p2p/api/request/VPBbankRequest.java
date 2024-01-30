package com.lendbiz.p2p.api.request;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class VPBbankRequest {
    @NotBlank(message = "'masterAccountNumber' is mandatory")
    private String masterAccountNumber;

    private String virtualAccountNumber;
    private String virtualName;
    private String virtualAlkey;

    @NotBlank(message = "'amount' is mandatory")
    private String amount;

    @NotBlank(message = "'bookingDate' is mandatory")
    //@Pattern(regexp = "^$", message = "'bookingDate' wrong format !, format = dd/mm/yyyy, dd-mm-yyyy or dd.mm.yyyy")
    private String bookingDate;

    @NotBlank(message = "'transactionDate' is mandatory")
    //@Pattern(regexp = "^$", message = "'transactionDate' wrong format !, format = dd/mm/yyyy, dd-mm-yyyy or dd.mm.yyyy")
    private String transactionDate;

    @NotBlank(message = "'transactionId' is mandatory")
    private String transactionId;

    @NotBlank(message = "'remark' is mandatory")
    private String remark;
}
