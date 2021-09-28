package com.lendbiz.p2p.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name ="SEARCH")
@Getter
@Setter
public class SearchEntity {

    @Id
    @Column(name ="SEARCHCODE")
    private String searchCode;
    @Column(name ="SEARCHTITLE")
    private String searchTitle;
    @Column(name ="EN_SEARCHTITLE")
    private String en_searchTitle;
    @Column(name ="SEARCHCMDSQL")
    private String searchCmdSql;
    @Column(name ="OBJNAME")
    private String objName;
    @Column(name ="FRMNAME")
    private String frmName;
    @Column(name ="ORDERBYCMDSQL")
    private String orderByCmdSql;
    @Column(name ="TLTXCD")
    private String tltxCd;
    @Column(name ="CNTRECORD")
    private String cntRecord;
    @Column(name ="ROWPERPAGE")
    private String rowPerPage;
    @Column(name ="AUTOSEARCH")
    private String autoSearch;
    @Column(name ="INTERVAL")
    private String interval;
    @Column(name ="AUTHCODE")
    private String authCode;
    @Column(name ="ROWLIMIT")
    private String rowLimit;
    @Column(name ="CMDTYPE")
    private String cmdType;
    @Column(name ="CONDDEFFLD")
    private String condDefFld;
    @Column(name ="BANKINQ")
    private String bankInq;
    @Column(name ="BANKACCT")
    private String bankAcct;

}
