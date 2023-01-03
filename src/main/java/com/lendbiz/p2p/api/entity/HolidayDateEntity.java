package com.lendbiz.p2p.api.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class HolidayDateEntity {
    @Id
    @Column(name = "DATEVAL")
    private String dateVal;

    @Column(name = "GRPID")
    private int groupId;

}
