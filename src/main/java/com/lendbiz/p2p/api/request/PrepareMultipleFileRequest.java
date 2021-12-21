package com.lendbiz.p2p.api.request;

import java.util.List;

import com.lendbiz.api.model.MultipleSigningFileData;

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
public class PrepareMultipleFileRequest {

	private String agreementUUID;
	private int authorizeMethod;
	private String authorizeCode;
	private String notificationTemplate;
	private String notificationSubject;
	private List<MultipleSigningFileData> listOfSigningFileData;
	
}
