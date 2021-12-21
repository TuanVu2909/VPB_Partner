package com.lendbiz.p2p.api.request;

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
public class MsgResponse {

	private String from;
	private String subject;
	private Date sendTime;
	private String custId;
	private long amount;
	private long befBalance;
	private long avlBalance;
	private String content;
	private String txDate;
	
}
