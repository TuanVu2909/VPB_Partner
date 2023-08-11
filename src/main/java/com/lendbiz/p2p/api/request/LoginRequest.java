package com.lendbiz.p2p.api.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/***********************************************************************  
 *  
 *   @package：com.lendbiz.p2p.api.request，@class-name：LoginRequest.java   
 *     
 *   @copyright       Copyright:   2021-2022     
 *   @creator         Hoang Thanh Tu <br/>   
 *   @create-time   Apr 9, 2021   10:55:38 AM   
 *
 ***********************************************************************/
@Data
@ToString
@Getter
@Setter
public class LoginRequest {

	private String username;
	private String password;
	private String deviceId;
	private String version;

}
