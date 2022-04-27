package com.lendbiz.p2p.api.service;

import com.lendbiz.p2p.api.entity.AccountInput;
import com.lendbiz.p2p.api.entity.PkgFundInfoEntity;
import com.lendbiz.p2p.api.entity.VerifyAccountInput;
import com.lendbiz.p2p.api.request.*;
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

	public ResponseEntity<?> getUserInfo(String mobile);

	public ResponseEntity<?> setAccountPassword(SetAccountPasswordRequest setAccountPasswordRequest);

	public ResponseEntity<?> updateAccountInfo(UpdateAccountRequest updateRequest);

	public ResponseEntity<?> createBear(AccountInput input);

	public ResponseEntity<?> getAccountAsset(String custId);

	public ResponseEntity<?> getAccountInvest(String custId);

	public ResponseEntity<?> getPortfolioInvest(String custId);

	public ResponseEntity<?> getAccountNotifications(String custId);

	public ResponseEntity<?> getProduct();

	public ResponseEntity<?> getPayType();

	public ResponseEntity<?> getRate(String term, String productId, String amount);

	public ResponseEntity<?> getConfigRate();

	public ResponseEntity<?> getTerm(String productId);

	public ResponseEntity<?> getAccountInvestByProduct(AccountInput accountInput);

	public ResponseEntity<?> getProductInfo(BearRequest bearRequest);

	public ResponseEntity<?> getTransHistory(String customerId);

	public ResponseEntity<?> getCoin(String cif);

	public ResponseEntity<?> changeCoin(AccountInput input);

	public ResponseEntity<?> updateReferenceId(AccountInput input);

	public ResponseEntity<?> getBankInfo();

	public ResponseEntity<?> getInsurancePackage();

	public ResponseEntity<?> getRelation();

	public ResponseEntity<?> createInsurance(InsuranceRequest insuranceRequest);

	public ResponseEntity<?> createNavDaily(GmFundNavRequest request);

	public ResponseEntity<?> createFundInvest(GmFundNavRequest request);

	public ResponseEntity<?> getFundList();

	public ResponseEntity<?> getInvestPackage();

	public ResponseEntity<?> getInvestPackageDetail(String pkId);

	public ResponseEntity<?> getFundNAV();

	public ResponseEntity<?> getFundNAByFundID(String fid);

	public ResponseEntity<?> createFundInvestOptionally(GmFundNavRequest request);

	public ResponseEntity<?> getFundInvest(String cid);

	public ResponseEntity<?> getFundInvestDetail(String cid, String packageId);

	public ResponseEntity<?> savePkgFundInfo(PkgFundInfoEntity request);

	String checkSession(String session);

}
