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

import com.lendbiz.p2p.api.constans.Constans;
import com.lendbiz.p2p.api.entity.AuthProfileEntity;
import com.lendbiz.p2p.api.entity.CustomEntity;
import com.lendbiz.p2p.api.repository.AuthRepository;
import com.lendbiz.p2p.api.repository.PackageFilterRepository;
import com.lendbiz.p2p.api.request.LoginRequest;
import com.lendbiz.p2p.api.response.BaseResponse;
import com.lendbiz.p2p.api.service.UserService;

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
@Service("loginService")
public class UserServiceImpl extends BaseResponse<UserService> implements UserService {

	@Autowired
	AuthRepository authRepository;

	@Autowired
	PackageFilterRepository pkgFilterRepo;

	@Override
	public ResponseEntity<?> login(LoginRequest loginRequest) {

		CustomEntity response = pkgFilterRepo.login(loginRequest.getUsername(), loginRequest.getPassword(), loginRequest.getDeviceId());

		// return response(toErrorResult(Constans.FAIL, Constans.MESSAGE_FAIL, null, null));

		return response(toResult(response));
	}

}
