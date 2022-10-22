package com.lendbiz.p2p.api.entity;

import com.lendbiz.p2p.api.utils.Utils;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;

@Entity
@Table(name = "SERVICE9PAY")
@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Service9Pay {
    @Id
    @Column(name = "PID")
    private String id;
    @Column(name = "NAME")
    private long name;
    @Column(name = "CATEGORY_ID")
    private String c_id;
    @Column(name = "DESCRIPTION")
    private long des;

}
