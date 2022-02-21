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
import com.lendbiz.p2p.api.entity.AccountInput;
import com.lendbiz.p2p.api.entity.UserOnline;
import com.lendbiz.p2p.api.entity.VerifyAccountInput;
import com.lendbiz.p2p.api.exception.BusinessException;
import com.lendbiz.p2p.api.model.exception.InputInvalidExeption;
import com.lendbiz.p2p.api.repository.PackageFilterRepository;
import com.lendbiz.p2p.api.repository.UserOnlineRepository;
import com.lendbiz.p2p.api.request.BearRequest;
import com.lendbiz.p2p.api.request.LoginRequest;
import com.lendbiz.p2p.api.request.ReqJoinRequest;
import com.lendbiz.p2p.api.response.BaseResponse;
import com.lendbiz.p2p.api.service.ConfigService;
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
@Service("configService")
public class ConfigServiceImpl extends BaseResponse<ConfigService> implements ConfigService {

	@Autowired
	PackageFilterRepository pkgFilterRepo;

	@Autowired
	UserOnlineRepository userOnlineRepo;

	@Override
	public ResponseEntity<?> getProductField() {

		List<Object> response = (ArrayList) pkgFilterRepo.getProductField();

		return response(toResult(response));

	}

}
