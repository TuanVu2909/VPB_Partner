package com.lendbiz.p2p.api.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.lendbiz.p2p.api.entity.AccountInput;
import com.lendbiz.p2p.api.entity.GetEndRateRequest;
import com.lendbiz.p2p.api.entity.PkgFundInfoEntity;
import com.lendbiz.p2p.api.entity.VerifyAccountInput;
import com.lendbiz.p2p.api.entity.WithdrawBearRequest;
import com.lendbiz.p2p.api.request.*;
import com.lendbiz.p2p.api.request.hyperlead.HypPostBack;
import com.lendbiz.p2p.api.request.accesstrade.AccCreate;
import com.lendbiz.p2p.api.request.accesstrade.AccUpdate;
import com.lendbiz.p2p.api.response.BearResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

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

	ResponseEntity<?> checkVersion3GangOutdated(String version);

	ResponseEntity<?> checkExistedAccount(LoginRequest loginRequest);

	ResponseEntity<?> login(LoginRequest loginRequest);

	// ReqJoin
	ResponseEntity<?> register(ReqJoinRequest reqJoinRequest);

	ResponseEntity<?> resendOtp(ReqJoinRequest reqJoinRequest);

	ResponseEntity<?> verifyAcc(VerifyAccountInput input);

	ResponseEntity<?> updateBioState(UpdateBiometricRequest request);

	ResponseEntity<?> getUserInfo(String mobile);

	ResponseEntity<?> setAccountPassword(SetAccountPasswordRequest setAccountPasswordRequest);

	ResponseEntity<?> updateAccountInfo(UpdateAccountRequest updateRequest);

	ResponseEntity<?> updateBankAccountInfo(UpdateAccountRequest updateRequest);

	ResponseEntity<?> createBear(AccountInput input);

	ResponseEntity<?> withdrawBear(WithdrawBearRequest input);

	ResponseEntity<?> getAccountAsset(String custId);

	ResponseEntity<?> getAccountInvest(String custId);

	ResponseEntity<?> getPortfolioInvest(String custId);

	ResponseEntity<?> getAccountNotifications(String custId);

	ResponseEntity<?> updateNotifications(UpdateNotificationsRequest request);

	ResponseEntity<?> getStatements(String custId);

	ResponseEntity<?> getProduct();

	ResponseEntity<?> getPayType();

	ResponseEntity<?> getRate(String term, String productId, String amount);

	ResponseEntity<?> getConfigRate();

	ResponseEntity<?> getTerm(String productId);

	ResponseEntity<?> getAccountInvestByProduct(AccountInput accountInput);

	ResponseEntity<?> getProductInfo(BearRequest bearRequest);

	ResponseEntity<?> getTransHistory(String customerId);

	ResponseEntity<?> getCoin(String cif);

	ResponseEntity<?> getRefList(String cif);

	ResponseEntity<?> changeCoin(AccountInput input);

	ResponseEntity<?> updateReferenceId(AccountInput input);

	ResponseEntity<?> getBankInfo();

	ResponseEntity<?> getInsurancePackage();

	ResponseEntity<?> getRelation();

	ResponseEntity<?> createInsurance(InsuranceRequest insuranceRequest);

	ResponseEntity<?> createNavDaily(GmFundNavRequest request);

	ResponseEntity<?> createFundInvest(GmFundNavRequest request);

	ResponseEntity<?> getFundList();

	ResponseEntity<?> getInvestPackage();

	ResponseEntity<?> getInvestPackageDetail(String pkId);

	ResponseEntity<?> getFundNAV();

	ResponseEntity<?> getFundNAByFundID(String fid);

	ResponseEntity<?> createFundInvestOptionally(GmFundNavRequest request);

	ResponseEntity<?> getFundInvest(String cid);

	ResponseEntity<?> endBear(AccountInput accountInput);

	ResponseEntity<?> getEndRate(GetEndRateRequest request);

	ResponseEntity<?> getFundInvestDetail(String cid, String packageId);

	ResponseEntity<?> savePkgFundInfo(PkgSumFundRequest request);

	ResponseEntity<?> getPkgFundInfo();

	ResponseEntity<?> genTransferCode(String amount, String cif);

	ResponseEntity<?> withdraw(CashOutRequest request);

	String checkSession(String session);

	void autoSignContract();

	void jobHandleAffiliate0();

	void jobHandleAffiliate1();

	void jobHandleAffiliate2();

	void jobHandleAffiliate3();

	ResponseEntity<?> getSavingProducts();
}
