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
public interface UserService {

	public ResponseEntity<?> login(LoginRequest loginRequest);

	// ReqJoin
	public ResponseEntity<?> register(ReqJoinRequest reqJoinRequest);


	public ResponseEntity<?> verifyAcc(VerifyAccountInput input);
	public ResponseEntity<?> createBear(AccountInput input);
	public ResponseEntity<?> getAccountAsset(String custId);
	public ResponseEntity<?> getAccountInvest(String custId);
	public ResponseEntity<?> getProduct();
	public ResponseEntity<?> getPayType();
	public ResponseEntity<?> getRate(String term,String productId,String amount);
	public ResponseEntity<?> getTerm(String productId);
	public ResponseEntity<?> getAccountInvestByProduct(AccountInput accountInput);
	public ResponseEntity<?> getProductInfo(BearRequest bearRequest);
	public ResponseEntity<?> getTransHistory(String customerId);

	String checkSession(String session);

}
