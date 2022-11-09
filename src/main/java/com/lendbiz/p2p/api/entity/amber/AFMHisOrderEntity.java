package com.lendbiz.p2p.api.entity.amber;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "AFM_HIS_ORDER")
@Data
public class AFMHisOrderEntity {
    @Id
    @Column(name = "ID")
    private Long id;
    @Column(name = "CUSTODYCD")
    private String custodycd;
    @Column(name = "SYMBOL")
    private String symbol;
    @Column(name = "SRTYPE")
    private String srtype;
    @Column(name = "EXECTYPE")
    private String exectype;
    @Column(name = "TXDATE")
    private String txdate;
    @Column(name = "TRADINGDATE")
    private String tradingdate;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "ORDERAMT")
    private String orderamt;
    @Column(name = "ORDERQTTY")
    private String orderqtty;
    @Column(name = "ORDERID")
    private String orderid;
}
