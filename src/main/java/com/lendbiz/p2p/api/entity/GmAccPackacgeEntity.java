package com.lendbiz.p2p.api.entity;

import lombok.*;

import java.time.LocalDate;

import javax.persistence.*;

@Entity
@Table(name = "gmaccpackage")
@NamedQuery(name = "GmAccPackacgeEntity.findAll", query = "SELECT c FROM GmAccPackacgeEntity c")
@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GmAccPackacgeEntity {
    
    @Id
    @Column(name = "ID")
    private int id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "CREATEDDATE")
    private LocalDate createdDate;

    @Column(name = "STATUS")
    private int status;
}
