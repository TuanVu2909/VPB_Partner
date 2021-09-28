package com.lendbiz.p2p.api.request;

import lombok.Data;

/***********************************************************************  
 *  
 *   @package：com.lendbiz.p2p.api.entity，@class-name：UpdateUserRequest.java   
 *     
 *   @copyright       Copyright:   2021-2022     
 *   @creator         Hoang Thanh Tu <br/>   
 *   @create-time   Apr 9, 2021   10:57:01 AM   
 *
 ***********************************************************************/
@Data
public class UpdateUserRequest {

	private String tlId;
	private String tlName;
	private String tlFullName;
	private int tlLev;
	private String brId;
	private String tlTitle;
	private String tlPrn;
	private String tlGroup;
	private String pin;
	private String description;
	private String tlType;
	private String active;
	private String idCode;
	private String homeOrder;
	private String teleOrder;
	private String extTel;
}
