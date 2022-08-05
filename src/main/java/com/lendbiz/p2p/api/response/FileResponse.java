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
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FileResponse {

	private String name;
	private String url;
	private String type;
	private long size;
}
