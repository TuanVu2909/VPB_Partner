package com.lendbiz.p2p.api.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "KEYS_MANAGE")
public class KeysManageEntity {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "PARTNER")
    private String partner;

    @Column(name = "NOTE")
    private String note;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "ALGORITHM")
    private String algorithm;

    @Column(name = "PRIVATE_KEY")
    private String private_key;

    @Column(name = "PUBLIC_KEY")
    private String public_key;

    @Column(name = "GEN_NUM")
    private String gen_num;

    @Column(name = "KEY_SIZE")
    private String key_size;
}








