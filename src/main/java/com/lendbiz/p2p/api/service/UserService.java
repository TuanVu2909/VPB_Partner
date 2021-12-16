package com.lendbiz.p2p.api.service;

import com.lendbiz.p2p.api.entity.VerifyAccountInput;
import com.lendbiz.p2p.api.request.LoginRequest;
import com.lendbiz.p2p.api.request.ReqJoinRequest;

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
public interface UserService {

	public ResponseEntity<?> login(LoginRequest loginRequest);

	// ReqJoin
	public ResponseEntity<?> register(ReqJoinRequest reqJoinRequest);


	public ResponseEntity<?> verifyAcc(VerifyAccountInput input);

}
