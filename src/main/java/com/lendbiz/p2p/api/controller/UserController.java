package com.lendbiz.p2p.api.controller;

import javax.servlet.http.HttpServletRequest;

import com.lendbiz.p2p.api.configs.JwtProvider;
import com.lendbiz.p2p.api.entity.AccountInput;
import com.lendbiz.p2p.api.entity.User3GEntity;
import com.lendbiz.p2p.api.entity.VerifyAccountInput;
import com.lendbiz.p2p.api.exception.BusinessException;
import com.lendbiz.p2p.api.model.MyUserDetails;
import com.lendbiz.p2p.api.repository.ProductGMRepository;
import com.lendbiz.p2p.api.request.*;
import com.lendbiz.p2p.api.response.BearResponse;
import com.lendbiz.p2p.api.response.InfoIdentity;
import com.lendbiz.p2p.api.response.MyResponse;
import com.lendbiz.p2p.api.service.SavisService;
import com.lendbiz.p2p.api.service.User3GService;
import com.lendbiz.p2p.api.service.UserService;

import com.lendbiz.p2p.api.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
    @Autowired
    JwtProvider provider;
    @Autowired
    User3GService user3GService;
    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/api/login")
    public ResponseEntity<?> login(HttpServletRequest httpServletRequest, @RequestHeader("requestId") String requestId,
            @RequestBody LoginRequest loginRequest) {
        log.info("[" + requestId + "] << login >>");

        return userService.login(loginRequest);
    }

    @PostMapping("/api/register")
    public ResponseEntity<?> register(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId, @RequestBody ReqJoinRequest reqJoinRequest)
            throws BusinessException {
        log.info("[" + requestId + "] << register >>");
        return userService.register(reqJoinRequest);

    }

    @PostMapping("/api/verify-account")
    public ResponseEntity<?> verifyAccount(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId, @RequestBody VerifyAccountInput verifyAccountInput)
            throws BusinessException {
        return userService.verifyAcc(verifyAccountInput);
    }

    @PostMapping("/api/set-account-password")
    @Transactional(readOnly = true)
    public ResponseEntity<?> verifyAccount(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId, @RequestBody SetAccountPasswordRequest setAccountPasswordRequest)
            throws BusinessException {
        return userService.setAccountPassword(setAccountPasswordRequest);
    }

    @PostMapping("/api/create-bear")
    @Transactional(readOnly = true)
    public ResponseEntity<?> createBear(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId, @RequestBody AccountInput accountInput)
            throws BusinessException {
        log.info("[" + requestId + "] << create bear >>");
        return userService.createBear(accountInput);
    }

    @GetMapping("/api/get-account-asset")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getAccountAsset(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId, @RequestParam String cif)
            throws BusinessException {
        log.info("[" + requestId + "] << getAccountAsset >>");
        return userService.getAccountAsset(cif);
    }

    @GetMapping("/api/get-account-invest")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getAccountInvest(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId, @RequestParam String cif)
            throws BusinessException {
        log.info("[" + requestId + "] << getAccountInvest >>");
        return userService.getAccountInvest(cif);
    }

    @GetMapping("/api/get-product")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getProduct(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId)
            throws BusinessException {
        log.info("[" + requestId + "] << getProduct >>");
        return userService.getProduct();
    }

    @GetMapping("/api/get-paytype")
    @Transactional(readOnly = true)

    public ResponseEntity<?> getPayType(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId)
            throws BusinessException {
        log.info("[" + requestId + "] << get-paytype >>");
        return userService.getPayType();
    }

    @GetMapping("/api/get-term")
    @Transactional(readOnly = true)

    public ResponseEntity<?> getTerm(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId, @RequestParam("pid") String pId)
            throws BusinessException {
        log.info("[" + requestId + "] << get-term >>");
        return userService.getTerm(pId);
    }

    @GetMapping("/api/get-rate")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getRate(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId, @RequestParam("pid") String pId,
            @RequestParam("term") String term, @RequestParam("amount") String amount)
            throws BusinessException {
        log.info("[" + requestId + "] << get-rate >>");
        return userService.getRate(term, pId, amount);
    }

    @GetMapping("/api/get-account-invest-by-product")
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

    @PostMapping("/api/verify-identity")
    public ResponseEntity<?> verifyIdeEntity(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId, @RequestHeader("session") String session,
            @RequestParam("id_file") MultipartFile idFile, @RequestParam("id_type") int idType)
            throws BusinessException {

        // String custId = userService.checkSession(session);
        InfoIdentity identity = new InfoIdentity();
        return savisService.callPredict(idFile, identity, idType);
    }


    //Authorization


    @PostMapping("/auth/signup")
    public ResponseEntity<?> createUser3G(@RequestBody User3GEntity user) {

       MyResponse response = new MyResponse();

        response.setData("Đăng ký thành công");
        response.setMessage("success");
        response.setStatus("200");
        user3GService.create(user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("/auth/signin")
    public ResponseEntity<?> signin(@RequestBody SignInReq req) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(),req.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = provider.crateToken(authentication);
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        MyResponse response = new MyResponse();
        response.setData(token);
        response.setMessage("ok");
        response.setStatus("200");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/api/update-ref")
    @Transactional(readOnly = true)
    public ResponseEntity<?> updateReferenceId(HttpServletRequest httpServletRequest,
                                               @RequestHeader("requestId") String requestId, @RequestBody AccountInput accountInput)
            throws BusinessException, UnsupportedEncodingException {
        log.info("[" + requestId + "] << check-updateReferenceId >>");
        return userService.updateReferenceId(accountInput);

    }

    @GetMapping("/api/get-coin")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getCoin(HttpServletRequest httpServletRequest,
                                     @RequestHeader("requestId") String requestId, @RequestParam("cif") String cId)
            throws BusinessException, UnsupportedEncodingException {
        log.info("[" + requestId + "] << check-get-coin >>");

        return userService.getCoin(cId);

    }

    @PostMapping("/api/change-coin")
    @Transactional(readOnly = true)
    public ResponseEntity<?> changeCoin(HttpServletRequest httpServletRequest,
                                        @RequestHeader("requestId") String requestId, @RequestBody AccountInput input)
            throws BusinessException, UnsupportedEncodingException {
        log.info("[" + requestId + "] << check-change-coin>>");

        return userService.changeCoin(input);

    }
    @PostMapping("/api/verify-face")
    public ResponseEntity<?> verifyFace(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId, @RequestHeader("session") String session,
            @RequestParam("front_file") MultipartFile frontFile, @RequestParam("selfie_file") MultipartFile selfieFile)
            throws BusinessException {

        String custId = userService.checkSession(session);
        return savisService.callCheckSelfie(frontFile, selfieFile, custId);
    }

    @PostMapping("/api/product-info")
    public ResponseEntity<?> productInfo(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId, @RequestBody BearRequest bearRequest)
            throws BusinessException {
        log.info("[" + requestId + "] << productInfo >>");

        return userService.getProductInfo(bearRequest);
    }

    @GetMapping("/api/bank-info")
    public ResponseEntity<?> bankInfo(HttpServletRequest httpServletRequest,
                                         @RequestHeader("requestId") String requestId)
            throws BusinessException {
        log.info("[" + requestId + "] << bankInfo >>");

        return userService.getBankInfo();
    }

    @GetMapping("/api/get-ins-pck")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getInsurancePackage(HttpServletRequest httpServletRequest,
                                      @RequestHeader("requestId") String requestId)
            throws BusinessException {
        log.info("[" + requestId + "] << getInsurancePackage >>");

        return userService.getInsurancePackage();
    }

    @GetMapping("/api/bv/get-relation")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getRelation(HttpServletRequest httpServletRequest,
                                                 @RequestHeader("requestId") String requestId)
            throws BusinessException {
        log.info("[" + requestId + "] << getRelation >>");

        return userService.getRelation();
    }
    @PostMapping("/api/create-ins")
    @Transactional(readOnly = true)
    public ResponseEntity<?> createInsurance(HttpServletRequest httpServletRequest,
                                             @RequestHeader("requestId") String requestId, @RequestBody InsuranceRequest request)
            throws BusinessException {
        log.info("[" + requestId + "] << createInsurance >>");

        return userService.createInsurance(request);
    }

    @GetMapping("/api/get-bg-tran-his")
    public ResponseEntity<?> getTransHistory(HttpServletRequest httpServletRequest,
            @RequestHeader("session") String session,
            @RequestHeader("requestId") String requestId)
            throws BusinessException {
        log.info("[" + requestId + "] << getTransHistory >>");

        String customerId = userService.checkSession(session);

        return userService.getTransHistory(customerId);
    }

}
