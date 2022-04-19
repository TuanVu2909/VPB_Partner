package com.lendbiz.p2p.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "SEARCHFLD")
@Getter
@Setter
@ToString
public class SearchFldEntity {

    @Column(name ="POSITION")
    private int position;
    @Id
    @Column(name ="FIELDCODE")
    private String fieldCode;
    @Column(name ="FIELDNAME")
    private String fieldName;
    @Column(name ="FIELDTYPE")
    private String fieldType;
    @Column(name ="SEARCHCODE")
    private String searchCode;
    @Column(name ="FIELDSIZE")
    private int fieldSize;
    @Column(name ="MASK")
    private String mask;
    @Column(name ="OPERATOR")
    private String operator;
    @Column(name ="FORMAT")
    private String format;
    @Column(name ="DISPLAY")
    private String display;
    @Column(name ="SRCH")
    private String srch;
    @Column(name ="KEY")
    private String key;
    @Column(name ="WIDTH")
    private int width;
    @Column(name ="LOOKUPCMDSQL")
    private String lookupCmdSql;
    @Column(name ="EN_FIELDNAME")
    private String en_Fieldname;
    @Column(name ="REFVALUE")
    private String refValue;
    @Column(name ="FLDCD")
    private String fldCd;
    @Column(name ="DEFVALUE")
    private String defValue;
    @Column(name ="MULTILANG")
    private String multiLang;
    @Column(name ="ACDTYPE")
    private String acdType;
    @Column(name ="ACDNAME")
    private String acdName;
    @Column(name ="FIELDCMP")
    private String fieldCmp;
    @Column(name ="FIELDCMPKEY")
    private String fieldCmpKey;

}
