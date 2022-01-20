package com.lendbiz.p2p.api.controller;

import javax.servlet.http.HttpServletRequest;

import com.lendbiz.p2p.api.entity.AccountInput;
import com.lendbiz.p2p.api.entity.VerifyAccountInput;
import com.lendbiz.p2p.api.exception.BusinessException;
import com.lendbiz.p2p.api.request.LoginRequest;
import com.lendbiz.p2p.api.request.ReqJoinRequest;
import com.lendbiz.p2p.api.response.InfoIdentity;
import com.lendbiz.p2p.api.service.SavisService;
import com.lendbiz.p2p.api.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    SavisService savisService;

    @PostMapping("/login")
    public ResponseEntity<?> login(HttpServletRequest httpServletRequest, @RequestHeader("requestId") String requestId,
            @RequestBody LoginRequest loginRequest) {
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
    @PostMapping("/create-bear")
    public ResponseEntity<?> createBear(HttpServletRequest httpServletRequest,
                                           @RequestHeader("requestId") String requestId, @RequestBody AccountInput accountInput)
            throws BusinessException {
        log.info("[" + requestId + "] << verify account >>");
        return userService.createBear(accountInput);
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

    @GetMapping("/get-paytype")
    public ResponseEntity<?> getPayType(HttpServletRequest httpServletRequest,
                                        @RequestHeader("requestId") String requestId)
            throws BusinessException {
        log.info("[" + requestId + "] << getProduct >>");
        return userService.getPayType();
    }

    @GetMapping("/get-term")
    public ResponseEntity<?> getTerm(HttpServletRequest httpServletRequest,
                                        @RequestHeader("requestId") String requestId,@RequestParam("pid") String pId)
            throws BusinessException {
        log.info("[" + requestId + "] << getProduct >>");
        return userService.getTerm(pId);
    }

    @GetMapping("/get-rate")
    public ResponseEntity<?> getRate(HttpServletRequest httpServletRequest,
                                     @RequestHeader("requestId") String requestId,@RequestParam("pid") String pId,@RequestParam("term") String term,@RequestParam("amount") String amount)
            throws BusinessException {
        log.info("[" + requestId + "] << getProduct >>");
        return userService.getRate(term,pId,amount);
    }

    @GetMapping("/get-account-invest-by-product")
    public ResponseEntity<?> getAccountInvestByProduct(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId, @RequestParam("cif") String cif,
            @RequestParam("pid") String pId)
            throws BusinessException {
        AccountInput accountInput = new AccountInput();
        accountInput.setProductId(pId);
        accountInput.setCustId(cif);
        log.info("[" + requestId + "] << getAccountInvestByProduct >>");
        return userService.getAccountInvestByProduct(accountInput);
    }

    @PostMapping("/verify-identity")
    public ResponseEntity<?> verifyIdeEntity(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId, @RequestHeader("session") String session,
            @RequestParam("id_file") MultipartFile idFile, @RequestParam("id_type") int idType)
            throws BusinessException {
        log.info("[" + requestId + "] << verifyIdeEntity >>");
        log.info("[" + idType + "] << verifyIdeEntity >>");
        // String custId = userService.checkSession(session);
        InfoIdentity identity = new InfoIdentity();
        return savisService.callPredict(idFile, identity, idType);
    }

    @PostMapping("/verify-face")
    public ResponseEntity<?> verifyFace(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId, @RequestHeader("session") String session,
            @RequestParam("front_file") MultipartFile frontFile, @RequestParam("selfie_file") MultipartFile selfieFile)
            throws BusinessException {
        log.info("[" + requestId + "] << verifyIdeEntity >>");
        String custId = userService.checkSession(session);
        return savisService.callCheckSelfie(frontFile, selfieFile, custId);
    }

}
