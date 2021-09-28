package com.lendbiz.p2p.api.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "CMDMENU")
@Data
public class CmdMenuEntity {

    @Id
    @Column(name = "CMDID")
    private String cmdId;
    @Column(name = "PRID")
    private String prId;
    @Column(name = "LEV")
    private int lev;
    @Column(name = "LAST")
    private String last;
    @Column(name = "MENUTYPE")
    private String menutype;
    @Column(name = "MENUCODE")
    private String menucode;
    @Column(name = "MODCODE")
    private String modCode;
    @Column(name = "OBJNAME")
    private String objName;
    @Column(name = "CMDNAME")
    private String cmdName;
    @Column(name = "EN_CMDNAME")
    private String en_cmdName;
    @Column(name = "AUTHCODE")
    private String authCode;
    @Column(name = "TLTXCD")
    private String tltxCd;
}
