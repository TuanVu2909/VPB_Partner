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
public class OtpResponseNew {
    
	private DataOTP data;
	private int code;
	private String message;
	private String traceId;
}
