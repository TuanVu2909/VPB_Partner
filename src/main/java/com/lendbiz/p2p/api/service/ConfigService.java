package com.lendbiz.p2p.api.service;

import com.lendbiz.p2p.api.entity.AccountInput;
import com.lendbiz.p2p.api.entity.VerifyAccountInput;
import com.lendbiz.p2p.api.request.BearRequest;
import com.lendbiz.p2p.api.request.LoginRequest;
import com.lendbiz.p2p.api.request.ReqJoinRequest;

import com.lendbiz.p2p.api.response.BearResponse;
import org.springframework.http.ResponseEntity;

/***********************************************************************
 * 
 * @package：com.lendbiz.p2p.api.service，@class-name：UserService.java
 * 
 * @copyright Copyright: 2021-2022
 * @creator Hoang Thanh Tu <br/>
 * @create-time Apr 9, 2021 10:56:05 AM
 *
 ***********************************************************************/
public interface ConfigService {

	public ResponseEntity<?> getHolidayDate();

	public ResponseEntity<?> getProductField(String mobile);

	public ResponseEntity<?> getProductConfig();

	public ResponseEntity<?> getVpbHolidayDate();
}
