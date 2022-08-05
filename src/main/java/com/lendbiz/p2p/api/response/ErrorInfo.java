package com.lendbiz.p2p.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/***********************************************************************
 * 
 * @package：com.lendbiz.p2p.api.response，@class-name：ErrorInfo.java
 * 
 * @copyright Copyright: 2021-2022
 * @creator Hoang Thanh Tu <br/>
 * @create-time Apr 9, 2021 10:30:37 AM
 *
 ***********************************************************************/
@Getter
@Setter
@ToString
public class ErrorInfo {

	@JsonInclude(value = Include.NON_NULL)
	private String status;
	@JsonInclude(value = Include.NON_NULL)
	private String error;
	@JsonInclude(value = Include.NON_NULL)
	private String errorDesc;
	@JsonInclude(value = Include.NON_NULL)
	private String path;

	public ErrorInfo(String status, String error, String errorCode, String errorDesc, String path) {
		this.status = status;
		this.error = error;
		this.errorDesc = errorDesc;
		this.path = path;
	}

}
