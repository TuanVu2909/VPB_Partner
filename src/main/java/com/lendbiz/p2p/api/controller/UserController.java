package com.lendbiz.p2p.api.controller;

import javax.servlet.http.HttpServletRequest;

import com.lendbiz.p2p.api.entity.AccountInput;
import com.lendbiz.p2p.api.entity.VerifyAccountInput;
import com.lendbiz.p2p.api.exception.BusinessException;
import com.lendbiz.p2p.api.repository.ProductGMRepository;
import com.lendbiz.p2p.api.request.BearRequest;
import com.lendbiz.p2p.api.request.LoginRequest;
import com.lendbiz.p2p.api.request.ReqJoinRequest;
import com.lendbiz.p2p.api.request.SetAccountPasswordRequest;
import com.lendbiz.p2p.api.response.BearResponse;
import com.lendbiz.p2p.api.response.InfoIdentity;
import com.lendbiz.p2p.api.service.SavisService;
import com.lendbiz.p2p.api.service.UserService;

import com.lendbiz.p2p.api.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j2;

import java.io.UnsupportedEncodingException;

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
        return userService.verifyAcc(verifyAccountInput);
    }

    @PostMapping("/set-account-password")
    @Transactional(readOnly = true)
    public ResponseEntity<?> verifyAccount(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId, @RequestBody SetAccountPasswordRequest setAccountPasswordRequest)
            throws BusinessException {
        return userService.setAccountPassword(setAccountPasswordRequest);
    }

    @PostMapping("/create-bear")
    @Transactional(readOnly = true)
    public ResponseEntity<?> createBear(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId, @RequestBody AccountInput accountInput)
            throws BusinessException {
        log.info("[" + requestId + "] << create bear >>");
        return userService.createBear(accountInput);
    }

    @GetMapping("/get-account-asset")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getAccountAsset(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId, @RequestParam String cif)
            throws BusinessException {
        log.info("[" + requestId + "] << getAccountAsset >>");
        return userService.getAccountAsset(cif);
    }

    @GetMapping("/get-account-invest")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getAccountInvest(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId, @RequestParam String cif)
            throws BusinessException {
        log.info("[" + requestId + "] << getAccountInvest >>");
        return userService.getAccountInvest(cif);
    }

    @GetMapping("/get-product")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getProduct(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId)
            throws BusinessException {
        log.info("[" + requestId + "] << getProduct >>");
        return userService.getProduct();
    }

    @GetMapping("/get-paytype")
    @Transactional(readOnly = true)

    public ResponseEntity<?> getPayType(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId)
            throws BusinessException {
        log.info("[" + requestId + "] << get-paytype >>");
        return userService.getPayType();
    }

    @GetMapping("/get-term")
    @Transactional(readOnly = true)

    public ResponseEntity<?> getTerm(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId, @RequestParam("pid") String pId)
            throws BusinessException {
        log.info("[" + requestId + "] << get-term >>");
        return userService.getTerm(pId);
    }

    @GetMapping("/get-rate")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getRate(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId, @RequestParam("pid") String pId,
            @RequestParam("term") String term, @RequestParam("amount") String amount)
            throws BusinessException {
        log.info("[" + requestId + "] << get-rate >>");
        return userService.getRate(term, pId, amount);
    }

    @GetMapping("/get-account-invest-by-product")
    @Transactional(readOnly = true)
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

        // String custId = userService.checkSession(session);
        InfoIdentity identity = new InfoIdentity();
        return savisService.callPredict(idFile, identity, idType);
    }
    @PostMapping("/update-ref")
    @Transactional(readOnly = true)
    public ResponseEntity<?> updateReferenceId(HttpServletRequest httpServletRequest,
                                               @RequestHeader("requestId") String requestId, @RequestBody AccountInput accountInput)
            throws BusinessException, UnsupportedEncodingException {
        log.info("[" + requestId + "] << check-updateReferenceId >>");
        return userService.updateReferenceId(accountInput);

    }

    @GetMapping("/get-coin")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getCoin(HttpServletRequest httpServletRequest,
                                     @RequestHeader("requestId") String requestId, @RequestParam("cif") String cId)
            throws BusinessException, UnsupportedEncodingException {
        log.info("[" + requestId + "] << check-get-coin >>");

        return userService.getCoin(cId);

    }

    @PostMapping("/change-coin")
    @Transactional(readOnly = true)
    public ResponseEntity<?> changeCoin(HttpServletRequest httpServletRequest,
                                        @RequestHeader("requestId") String requestId, @RequestBody AccountInput input)
            throws BusinessException, UnsupportedEncodingException {
        log.info("[" + requestId + "] << check-change-coin>>");

        return userService.changeCoin(input);

    }
    @PostMapping("/verify-face")
    public ResponseEntity<?> verifyFace(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId, @RequestHeader("session") String session,
            @RequestParam("front_file") MultipartFile frontFile, @RequestParam("selfie_file") MultipartFile selfieFile)
            throws BusinessException {

        String custId = userService.checkSession(session);
        return savisService.callCheckSelfie(frontFile, selfieFile, custId);
    }

    @PostMapping("/product-info")
    public ResponseEntity<?> productInfo(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId, @RequestBody BearRequest bearRequest)
            throws BusinessException {
        log.info("[" + requestId + "] << productInfo >>");

        return userService.getProductInfo(bearRequest);
    }

    @GetMapping("/bank-info")
    public ResponseEntity<?> bankInfo(HttpServletRequest httpServletRequest,
                                         @RequestHeader("requestId") String requestId)
            throws BusinessException {
        log.info("[" + requestId + "] << productInfo >>");

        return userService.getBankInfo();
    }

    @GetMapping("/get-bg-tran-his")
    public ResponseEntity<?> getTransHistory(HttpServletRequest httpServletRequest,
            @RequestHeader("session") String session,
            @RequestHeader("requestId") String requestId)
            throws BusinessException {
        log.info("[" + requestId + "] << getTransHistory >>");

        String customerId = userService.checkSession(session);

        return userService.getTransHistory(customerId);
    }

}
