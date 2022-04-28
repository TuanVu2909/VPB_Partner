package com.lendbiz.p2p.api.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FunNavRequest {
    private String fund_date;
    private String growth;
    private String f_code;
    private String pkg_id;
}
