package com.lendbiz.p2p.api.response;

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
public class UserRegisterResponse {

	private String userAttrId;
	private String userId;
	private String gender;
	private String age;
	private String lastUpdate;
	private String generalUrl;
	private String croppedGeneralUrl;
	private String faceAttrId;
	private String rawUserId;
	private String imageUrl;
	private String croppedUrl;
	private String requestId;
}
