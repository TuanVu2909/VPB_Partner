package com.lendbiz.p2p.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lendbiz.p2p.api.exception.BusinessException;
import com.lendbiz.p2p.api.request.WheelConfigUpdateRequest;
import com.lendbiz.p2p.api.service.WheelService;

import lombok.extern.log4j.Log4j2;

/***********************************************************************
 *
 * @package：com.lendbiz.p2p.api.controller，@class-name：UserController.java
 *
 * @copyright Copyright: 2021-2022
 * @creator Hoang Thanh Tu <br/>
 * @create-time Apr 9, 2021 10:57:13 AM
 *
 ***********************************************************************/
@RestController
@RequestMapping("/lendbiz/wheel")
@CrossOrigin(origins = "*")
public class WheelController {

    @Autowired
    WheelService wheelService;

    @PostMapping("/update-wheel")
    @Transactional(readOnly = true)
    public ResponseEntity<?> updateWheel(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId, @RequestHeader("apiType") int apiType,
            @RequestBody WheelConfigUpdateRequest request)
            throws BusinessException {
        if (apiType == 1) {
            return wheelService.updateWheelConfig(request);
        }

        if (apiType == 2) {
            return wheelService.updateWheelGroupTime(request);
        }

        if (apiType == 3) {
            return wheelService.updateWheelPrize(request);
        }

        if (apiType == 4) {
            return wheelService.insertWheelHistory(request);
        }

        throw new BusinessException("Thiếu apiType trong header: 1 - update wheelConfig | 2 - updateWeelGroupTime | 3 - updateWheelPrize | 4 - insert play log");

    }

}