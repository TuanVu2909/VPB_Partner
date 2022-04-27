package com.lendbiz.p2p.api.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Setter
@Getter
public class PkgFundResponse {
    private String fund_date;
    private String sum;
    private ArrayList<PkgFundDetail> pkgFundDetail;
}
