package com.lendbiz.p2p.api.controller;

import javax.servlet.http.HttpServletRequest;

import com.lendbiz.p2p.api.exception.BusinessException;
import com.lendbiz.p2p.api.request.LoginRequest;
import com.lendbiz.p2p.api.request.ReqJoinRequest;
import com.lendbiz.p2p.api.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
public class UserController {

	@Autowired
	UserService userService;

	@PostMapping("/login")
	public ResponseEntity<?> login(HttpServletRequest httpServletRequest, @RequestHeader("requestId") String requestId, @RequestBody LoginRequest loginRequest) {
		log.info("[" + requestId + "] << login >>");

		return userService.login(loginRequest);
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(HttpServletRequest httpServletRequest,
			@RequestHeader("requestId") String requestId, @RequestBody ReqJoinRequest reqJoinRequest)
			throws BusinessException {
		log.info("[" + requestId + "] << register >>");
		return userService.register(reqJoinRequest);

	}

}
