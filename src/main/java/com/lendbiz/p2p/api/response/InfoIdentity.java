package com.lendbiz.p2p.api.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InfoIdentity {

	private String idNo;
	private String dateIssued;
	private String issuedBy;
	private String fullName;
	private String birthDay;
	private String sex;
	private String nation;
	private String address;
	private String expirationDate;
	private int type;

}
