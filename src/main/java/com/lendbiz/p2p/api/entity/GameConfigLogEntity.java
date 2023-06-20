package com.lendbiz.p2p.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "GAMECONFIGLOG")
@NamedQuery(name = "GameConfigLogEntity.findAll", query = "SELECT c FROM GameConfigLogEntity c")
@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GameConfigLogEntity {

    @Id
    @Column(name = "VQID")
    private String vqId;
    @Column(name = "CUSTID")
    private String custId;
    @Column(name = "VM")
    private double l1;
    @Column(name = "VH")
    private double l2;
    @Column(name = "VB")
    private double l3;
    @Column(name = "VBB")
    private double l4;
    @Column(name = "VN")
    private double l5;
    @Column(name = "VS")
    private double l6;
    @Column(name = "VBBB")
    private double l7;
    @Column(name = "VT")
    private double l8;
    @Column(name = "VC")
    private double l9;
    @Column(name = "VMM")
    private double l10;
}
