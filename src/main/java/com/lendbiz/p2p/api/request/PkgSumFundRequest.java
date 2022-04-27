package com.lendbiz.p2p.api.request;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PkgSumFundRequest {

    private int id;
    private String fund_date;
    private String growth;
    private String f_code;
    private String pkg_id;
    private String sum;
}
