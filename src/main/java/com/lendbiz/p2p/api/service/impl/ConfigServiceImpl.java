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

import com.lendbiz.p2p.api.constants.Constants;
import com.lendbiz.p2p.api.constants.ErrorCode;
import com.lendbiz.p2p.api.entity.BgConfigEntity;
import com.lendbiz.p2p.api.entity.HolidayDateEntity;
import com.lendbiz.p2p.api.entity.ProductConfigEntity;
import com.lendbiz.p2p.api.entity.bank.VpbHoliday;
import com.lendbiz.p2p.api.model.exception.BusinessException;
import com.lendbiz.p2p.api.repository.*;
import com.lendbiz.p2p.api.response.BaseResponse;
import com.lendbiz.p2p.api.service.ConfigService;

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

	@Autowired
	BgConfigRepository bgConfigRepository;

	@Autowired
	ProductConfigRepo prodConfigRepo;

	@Autowired
	CurrentDateRepo currentDateRepo;

	@Autowired
	HolidayDateRepo holidayDateRepo;

	@Override
	public ResponseEntity<?> getHolidayDate() {

		// return response(toResult(bgConfigRepository.findViaProcedure()));
		try {
			List<HolidayDateEntity> dateEntity = currentDateRepo.getHolidayDate();
			return response(toResult(dateEntity));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new BusinessException(Constants.FAIL, ErrorCode.NO_DATA_DESCRIPTION);
		}
	}

	@Override
	public ResponseEntity<?> getProductField(String mobile) {

		// return response(toResult(bgConfigRepository.findViaProcedure()));

		List<BgConfigEntity> list = bgConfigRepository.findViaProcedure(mobile);
		if (list.size() == 0)
			throw new BusinessException(Constants.FAIL, ErrorCode.NO_DATA_DESCRIPTION);

		return response(toResult(list));

	}

	@Override
	public ResponseEntity<?> getProductConfig() {

		List<ProductConfigEntity> list = prodConfigRepo.getProductConfig();
		if (list.size() == 0)
			throw new BusinessException(Constants.FAIL, ErrorCode.NO_DATA_DESCRIPTION);

		return response(toResult(list));

	}

	@Override
	public ResponseEntity<?> getVpbHolidayDate() {
		// return response(toResult(bgConfigRepository.findViaProcedure()));
		try {
			List<VpbHoliday> vpbHolidayDate = holidayDateRepo.getVpbHolidayDate();
			return response(toResult(vpbHolidayDate));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new BusinessException(Constants.FAIL, ErrorCode.NO_DATA_DESCRIPTION);
		}
	}


}
