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

	private String identityId;
	private String birthday;
	private Date ngaysinh;
	private String address;
	private String dateIssued;
	private String fullName;
	private String domicile;
    private String type;
    private Date dateRange;
    private Date expirationDate;
	private String issuedBy;
	private String custId;

}
