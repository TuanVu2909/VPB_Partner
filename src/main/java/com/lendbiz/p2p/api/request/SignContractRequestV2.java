package com.lendbiz.p2p.api.request;

import java.util.ArrayList;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SignContractRequestV2 {

	private String page;
	private String llx;
	private String lly;
	private String urx;
	private String ury;
	private String detail;
	private String reason;
	private String location;
	private String contactInfo;
	private MultipartFile signature;
	private String docNo;
	private String type;
	private String signedBy;
	ArrayList<String> positions;
	private String isLBC;

	@Override
	public String toString() {
		return page + ":" + llx + "-" + lly + "-" + urx + "-" + ury;
	}

}
