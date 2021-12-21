package com.lendbiz.p2p.api.response;

import org.springframework.stereotype.Controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Controller
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FptImgSelfieResponse {

	private String result;
	private String providerCode;
}
