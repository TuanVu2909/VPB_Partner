package com.lendbiz.p2p.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lendbiz.p2p.api.request.LoginRequest;
import com.lendbiz.p2p.api.service.UserService;

/***********************************************************************  
*  
*   @package：com.lendbiz.p2p.api.controller，@class-name：UserController.java   
*     
*   @copyright       Copyright:   2021-2022     
*   @creator         Hoang Thanh Tu <br/>   
*   @create-time   Apr 9, 2021   10:57:13 AM   
*
***********************************************************************/
@RestController
@RequestMapping("/lendbiz")
public class UserController {

	@Autowired
	UserService userService;

	@PostMapping("/login")
	public ResponseEntity<?> login(HttpServletRequest httpServletRequest, @RequestBody LoginRequest loginRequest) {
//		String requestId = httpServletRequest.getHeader("request_id");
		
		return userService.login(loginRequest);
	}
	
}
