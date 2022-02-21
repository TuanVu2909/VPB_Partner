package com.lendbiz.p2p.api.response;

import com.lendbiz.p2p.api.entity.Card9PayCategory;
import com.lendbiz.p2p.api.entity.Card9PayService;
import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductResponse implements Serializable {
    private String id;
    private String name;
    private String description;
    private long price;
    private Card9PayService provider;
    private Card9PayCategory category;

    private Card9PayService service;

}
