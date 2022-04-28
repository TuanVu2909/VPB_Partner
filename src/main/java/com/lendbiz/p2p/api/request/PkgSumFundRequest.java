package com.lendbiz.p2p.api.request;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.ArrayList;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PkgSumFundRequest {
    private String fund_date;
    private String pkg_id;
    private String sum;
    ArrayList<FunNavRequest> funNavRequests;
}
