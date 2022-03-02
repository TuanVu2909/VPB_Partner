/*
 * Apache License, Version 2.0
 *
 * Copyright (c) 2021 TU HOANG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lendbiz.p2p.api.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.lendbiz.p2p.api.constants.Constants;
import com.lendbiz.p2p.api.constants.ErrorCode;
import com.lendbiz.p2p.api.entity.AccountInput;
import com.lendbiz.p2p.api.entity.UserOnline;
import com.lendbiz.p2p.api.entity.VerifyAccountInput;
import com.lendbiz.p2p.api.exception.BusinessException;
import com.lendbiz.p2p.api.model.exception.InputInvalidExeption;
import com.lendbiz.p2p.api.repository.AccountAssetRepository;
import com.lendbiz.p2p.api.repository.PackageFilterRepository;
import com.lendbiz.p2p.api.repository.ProductGMRepository;
import com.lendbiz.p2p.api.repository.UserOnlineRepository;
import com.lendbiz.p2p.api.request.BearRequest;
import com.lendbiz.p2p.api.request.LoginRequest;
import com.lendbiz.p2p.api.request.ReqJoinRequest;
import com.lendbiz.p2p.api.response.BaseResponse;
import com.lendbiz.p2p.api.service.UserService;
import com.lendbiz.p2p.api.utils.StringUtil;

import com.lendbiz.p2p.api.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/***********************************************************************
 * 
 * @package：com.lendbiz.p2p.api.service.impl，@class-name：UserServiceImpl.java
 * 
 * @copyright Copyright: 2021-2022
 * @creator Hoang Thanh Tu <br/>
 * @create-time Apr 9, 2021 10:43:45 AM
 *
 ***********************************************************************/
@Service("userService")
public class UserServiceImpl extends BaseResponse<UserService> implements UserService {

	@Autowired
	PackageFilterRepository pkgFilterRepo;

	@Autowired
	UserOnlineRepository userOnlineRepo;
	@Autowired
	ProductGMRepository productGMRepository;
	@Autowired
	AccountAssetRepository accountAssetRepository;
	@Override
	public ResponseEntity<?> login(LoginRequest loginRequest) {

		List<Object> response = (ArrayList) pkgFilterRepo.login(loginRequest.getUsername(), loginRequest.getPassword(),
				loginRequest.getDeviceId());

		return response(toResult(response.get(0)));

	}

	@Override
	public ResponseEntity<?> register(ReqJoinRequest reqJoinRequest) {

		List<Object> response = (ArrayList) pkgFilterRepo.reqJoin(reqJoinRequest);
		return response(toResult(response.get(0)));
	}

	@Override
	public ResponseEntity<?> verifyAcc(VerifyAccountInput input) {
		try {
			return response(toResult(pkgFilterRepo.verifyAcc(input)));
		} catch (Exception e) {
			throw new BusinessException(Constants.FAIL, e.getMessage());
		}
	}

	@Override
	public ResponseEntity<?> createBear(AccountInput input) {
		try {
			return response(toResult(pkgFilterRepo.crateBear(input)));
		} catch (Exception e) {
			throw new BusinessException(Constants.FAIL, e.getMessage());
		}
	}

	@Override
	public ResponseEntity<?> getAccountAsset(String custId) {

		return response(toResult(accountAssetRepository.getAccountAsset(custId)));

	}

	@Override
	public ResponseEntity<?> getAccountInvest(String custId) {

		return response(toResult(pkgFilterRepo.getAccountInvest(custId)));

	}

	@Override
	public ResponseEntity<?> getProduct() {

		return response(toResult(productGMRepository.getproduct()));

	}

	@Override
	public ResponseEntity<?> getPayType() {
		return response(toResult(pkgFilterRepo.getPayType()));
	}

	@Override
	public ResponseEntity<?> getRate(String term, String productId, String amount) {
		return response(toResult(pkgFilterRepo.getRate(productId, term, amount)));
	}

	@Override
	public ResponseEntity<?> getTerm(String productId) {
		return response(toResult(pkgFilterRepo.getTerm(productId)));
	}

	@Override
	public ResponseEntity<?> getAccountInvestByProduct(AccountInput accountInput) {

		return response(toResult(pkgFilterRepo.getAccountInvestByProduct(accountInput)));

	}

	@Override
	public ResponseEntity<?> getProductInfo(BearRequest bearRequest) {
		try {
			return response(toResult(Utils.getProductInfo(bearRequest)));
		} catch (Exception e) {
			throw new BusinessException(Constants.FAIL, e.getMessage());
		}

	}

	@Override
	public ResponseEntity<?> getCoin(String cif) {
		return response(toResult(pkgFilterRepo.getCoin(cif)));
	}

	@Override
	public ResponseEntity<?> changeCoin(AccountInput input) {
		return response(toResult(pkgFilterRepo.changeCoin(input)));
	}

	@Override
	public ResponseEntity<?> updateReferenceId(AccountInput input) {
		return response(toResult(pkgFilterRepo.updateReferenceId(input)));
	}


	@Override
	public String checkSession(String session) {
		logger.info("[" + session + "] << checkSession >>");
		if (StringUtil.isEmty(session))
			throw new BusinessException("user or pass invalid");

		Optional<UserOnline> userOnline = userOnlineRepo.findBySession(session);
		if (!userOnline.isPresent())

			throw new BusinessException(ErrorCode.SESSION_TIMEOUT, ErrorCode.SESSION_TIMEOUT_DESCRIPTION);

		return userOnline.get().getCustId();
	}

	@Override
	public ResponseEntity<?> getTransHistory(String customerId) {

		return response(toResult(pkgFilterRepo.getTransHistory(customerId)));

	}

}
