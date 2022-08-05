package com.lendbiz.p2p.api.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionBuyCard {
    private String id;
    private String cif;
    private String price;
    private String payDate;
    private String transactionId;
    private String phone_received;
    private String product_name;
    private String product_des;
    private String service_name;
    private String category_id;
    private int used;
    Card9PayDetails[] card9PayDetailsList;
}
