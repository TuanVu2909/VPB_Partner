package com.lendbiz.p2p.api.entity;

import java.util.Date;

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
@Table(name = "USERONLINE")
@NamedQuery(name = "UserOnline.findAll", query = "SELECT u FROM UserOnline u")
@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserOnline {

	@Column(name = "CUSTID")
	private String custId;
	
	@Id
	private String username;
	
	@Column(name = "PWD")
	private String pwd;

	@Column(name = "SESSTIONID")
	private String session;
	
	@Column(name = "STARTLOGIN")
	private Date startLogin;

	@Column(name = "CFTYPE")
	private String cfType;

	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "VERSION")
	private String version;
	
	@Column(name = "LAST_CHANGE")
	private Date lastChange;
	
	@Column(name = "DEVICEID")
	private String deviceId;
	
	@Column(name = "NUMBEROFFAIL")
	private Integer numberOffail;
	
	@Column(name = "VERIFYCODE")
	private String verifyCode;
}
