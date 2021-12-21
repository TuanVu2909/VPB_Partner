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
public class ReGenerateAuthCodeRequest {

	private String agreementUUID;
	private String notificationTemplate;
	private String notificationSubject;
	private int authorizeMethod;
}
