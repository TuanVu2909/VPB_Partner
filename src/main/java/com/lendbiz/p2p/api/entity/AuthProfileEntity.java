package com.lendbiz.p2p.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/***********************************************************************  
 *  
 *   @package：com.lendbiz.p2p.api.entity，@class-name：TLProfile.java   
 *     
 *   @copyright       Copyright:   2021-2022     
 *   @creator         Hoang Thanh Tu <br/>   
 *   @create-time   Apr 9, 2021   10:57:01 AM   
 *
 ***********************************************************************/
@Entity
@Table(name = "TLPROFILES")
@Data
public class AuthProfileEntity {

	@Id
    @Column(name = "TLID")
    private String tlId;
	@Column(name = "TLNAME")
	private String tlName;
	@Column(name = "TLFULLNAME")
	private String tlFullName;
	@Column(name = "TLLEV")
	private int tlLev;
	@Column(name = "BRID")
	private String brId;
	@Column(name = "TLTITLE")
	private String tlTitle;
	@Column(name = "TLPRN")
	private String tlPrn;
	@Column(name = "TLGROUP")
	private String tlGroup;
	@Column(name = "PIN")
	private String pin;
	@Column(name = "DESCRIPTION")
	private String description;
	@Column(name = "TLTYPE")
	private String tlType;
	@Column(name = "ACTIVE")
	private String active;
	@Column(name = "IDCODE")
	private String idCode;
	@Column(name = "HOMEORDER")
	private String homeOrder;
	@Column(name = "TELEORDER")
	private String teleOrder;
	@Column(name = "EXTTEL")
	private String extTel;
}
