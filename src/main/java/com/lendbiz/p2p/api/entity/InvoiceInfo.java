package com.lendbiz.p2p.api.entity;

import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InvoiceInfo {

    private String accountNo;
    private String address;
    private String check;
    private String company;
    private String name;
    private String taxNo;
}
