package com.lendbiz.p2p.api.controller;

import javax.servlet.http.HttpServletRequest;

import com.lendbiz.p2p.api.service.ConfigService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/lendbiz")
@Log4j2
public class ConfigController {

    @Autowired
    ConfigService configService;

    @Transactional(readOnly = true)
    @GetMapping("/get-product-field")
    public ResponseEntity<?> getProductField(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId) {

        return configService.getProductField();
    }

}
