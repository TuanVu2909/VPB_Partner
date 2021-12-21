package com.lendbiz.p2p.api.request;

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
public class PrepareCertificateRequest {

	private String agreementUUID;
	private String personalName;
	private String personalID;
	private String citizenID;
	private String location;
	private String stateProvince;
	private String country;
	private byte[] frontSideOfIDDocument;
	private byte[] backSideOfIDDocument;
	private String authorizationEmail;
	private String authorizationMobileNo;
}
