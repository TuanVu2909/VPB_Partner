package com.lendbiz.p2p.api.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
public class ProductConfigEntity {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "prodName")
    private String prodName;
    @Column(name = "iconUrl")
    private String iconUrl;
    @Column(name = "tabId")
    private String tabId;
    @Column(name = "tabTitle")
    private String tabTitle;
    @Column(name = "routing")
    private String routing;
    @Column(name = "currentPage")
    private int currentPage;
    @Column(name = "sortOrder")
    private int sortOrder;

}
