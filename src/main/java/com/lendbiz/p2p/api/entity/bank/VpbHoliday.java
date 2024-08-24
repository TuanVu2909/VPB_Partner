package com.lendbiz.p2p.api.entity.bank;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
@Getter
@Setter
@ToString
public class VpbHoliday {
    @Id
    @Column(name = "DATEVAL")
    private String dateVal;

    @Column(name = "GRPID")
    private int groupId;
}
