package com.lendbiz.p2p.api.controller;

import javax.servlet.http.HttpServletRequest;

import com.lendbiz.p2p.api.entity.AccountInput;
import com.lendbiz.p2p.api.entity.VerifyAccountInput;
import com.lendbiz.p2p.api.exception.BusinessException;
import com.lendbiz.p2p.api.request.LoginRequest;
import com.lendbiz.p2p.api.request.ReqJoinRequest;
import com.lendbiz.p2p.api.service.AccountService;
import com.lendbiz.p2p.api.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/verify-account")
    public ResponseEntity<?> verifyAccount(HttpServletRequest httpServletRequest,
                                           @RequestHeader("requestId") String requestId, @RequestBody VerifyAccountInput verifyAccountInput)
            throws BusinessException {
        log.info("[" + requestId + "] << verify account >>");
        return userService.verifyAcc(verifyAccountInput);
    }
    @GetMapping("/get-account-asset")
    public ResponseEntity<?> getAccountAsset(HttpServletRequest httpServletRequest,
                                           @RequestHeader("requestId") String requestId, @RequestParam String cif)
            throws BusinessException {
        log.info("[" + requestId + "] << getAccountAsset >>");
        return userService.getAccountAsset(cif);
    }
    @GetMapping("/get-account-invest")
    public ResponseEntity<?> getAccountInvest(HttpServletRequest httpServletRequest,
                                             @RequestHeader("requestId") String requestId, @RequestParam String cif)
            throws BusinessException {
        log.info("[" + requestId + "] << getAccountInvest >>");
        return userService.getAccountInvest(cif);
    }
    @GetMapping("/get-product")
    public ResponseEntity<?> getProduct(HttpServletRequest httpServletRequest,
                                             @RequestHeader("requestId") String requestId)
            throws BusinessException {
        log.info("[" + requestId + "] << getProduct >>");
        return userService.getProduct();
    }
    @GetMapping("/get-account-invest-by-product")
    public ResponseEntity<?> getAccountInvestByProduct(HttpServletRequest httpServletRequest,
                                             @RequestHeader("requestId") String requestId, @RequestParam("cif") String cif, @RequestParam("pid") String pId)
            throws BusinessException {
        AccountInput accountInput = new AccountInput(cif,pId);
        log.info("[" + requestId + "] << getAccountInvestByProduct >>");
        return userService.getAccountInvestByProduct(accountInput);
    }



}
