package com.lendbiz.p2p.api.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lendbiz.p2p.api.response.BaseResponse;
import com.lendbiz.p2p.api.service.*;
import lombok.SneakyThrows;
import org.apache.commons.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lendbiz.p2p.api.configs.JwtProvider;
import com.lendbiz.p2p.api.constants.Constants;
import com.lendbiz.p2p.api.constants.ErrorCode;
import com.lendbiz.p2p.api.entity.AccountInput;
import com.lendbiz.p2p.api.entity.GetEndRateRequest;
import com.lendbiz.p2p.api.entity.User3GEntity;
import com.lendbiz.p2p.api.entity.VerifyAccountInput;
import com.lendbiz.p2p.api.entity.WithdrawBearRequest;
import com.lendbiz.p2p.api.exception.BusinessException;
import com.lendbiz.p2p.api.model.Mail;
import com.lendbiz.p2p.api.model.MyUserDetails;
import com.lendbiz.p2p.api.request.BearRequest;
import com.lendbiz.p2p.api.request.CashOutRequest;
import com.lendbiz.p2p.api.request.GmFundNavRequest;
import com.lendbiz.p2p.api.request.InsuranceRequest;
import com.lendbiz.p2p.api.request.LoginRequest;
import com.lendbiz.p2p.api.request.PkgSumFundRequest;
import com.lendbiz.p2p.api.request.ReqJoinRequest;
import com.lendbiz.p2p.api.request.SendEmailRequest;
import com.lendbiz.p2p.api.request.SetAccountPasswordRequest;
import com.lendbiz.p2p.api.request.SignInReq;
import com.lendbiz.p2p.api.request.UpdateAccountRequest;
import com.lendbiz.p2p.api.request.UpdateBiometricRequest;
import com.lendbiz.p2p.api.request.UpdateNotificationsRequest;
import com.lendbiz.p2p.api.request.VerifyEmailRequest;
import com.lendbiz.p2p.api.response.InfoIdentity;
import com.lendbiz.p2p.api.response.MyResponse;
import com.lendbiz.p2p.api.service.impl.UserServiceImpl;

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
public class UserController extends BaseResponse<UserService> {

    @Autowired
    UserService userService;

    @Autowired
    SavisService savisService;

    @Autowired
    VNPTService vnptService;

    @Autowired
    JwtProvider provider;
    @Autowired
    User3GService user3GService;
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private LoggingService loggingGetRequest;

    @PostMapping("/check-existed-account")
    public ResponseEntity<?> checkExistedAccount(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId,
            @RequestBody LoginRequest loginRequest) {
        log.info("[" + requestId + "] << checkExistedAccount >>");

        return userService.checkExistedAccount(loginRequest);
    }

    @GetMapping("/check-version")
    public ResponseEntity<?> checkVersionOutdated(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId,
            @RequestParam String version) {
        log.info("[" + requestId + "] << version >>");

        return userService.checkVersion3GangOutdated(version);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(HttpServletRequest httpServletRequest, @RequestHeader("requestId") String requestId,
            @RequestBody LoginRequest loginRequest) {
        log.info("[" + requestId + "] << login >>");

        return userService.login(loginRequest);
    }

    @PostMapping("/register")
    @Transactional(readOnly = true)
    public ResponseEntity<?> register(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId, @RequestBody ReqJoinRequest reqJoinRequest)
            throws BusinessException {
        log.info("[" + requestId + "] << register >>");
        return userService.register(reqJoinRequest);

    }

    @PostMapping("/resend-otp")
    @Transactional(readOnly = true)
    public ResponseEntity<?> resendOtp(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId, @RequestBody ReqJoinRequest reqJoinRequest)
            throws BusinessException {
        log.info("[" + requestId + "] << resend-otp >>");
        return userService.resendOtp(reqJoinRequest);

    }

    @PostMapping("/verify-account")
    @Transactional(readOnly = true)
    public ResponseEntity<?> verifyAccount(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId, @RequestBody VerifyAccountInput verifyAccountInput)
            throws BusinessException {
        return userService.verifyAcc(verifyAccountInput);
    }

    @GetMapping("/get-user-info")
    public ResponseEntity<?> getUserInfo(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId, @RequestParam String mobile)
            throws BusinessException {
        log.info("[" + requestId + "] << get-user-info >>");
        String requestString = "mobile=" + mobile;
        loggingGetRequest.logRequest(httpServletRequest, requestString);

        return userService.getUserInfo(mobile);
    }

    @PostMapping("/set-account-password")
    @Transactional(readOnly = true)
    public ResponseEntity<?> setPassword(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId,
            @RequestBody SetAccountPasswordRequest setAccountPasswordRequest)
            throws BusinessException {
        return userService.setAccountPassword(setAccountPasswordRequest);
    }

    @PostMapping("/update-account-info")
    @Transactional(readOnly = true)
    public ResponseEntity<?> updateAccountInfo(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId,
            @RequestBody UpdateAccountRequest updateAccountRequest)
            throws BusinessException {
        return userService.updateAccountInfo(updateAccountRequest);
    }

    @PostMapping("/update-bank-account-info")
    @Transactional(readOnly = true)
    public ResponseEntity<?> updateBankAccountInfo(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId,
            @RequestBody UpdateAccountRequest updateAccountRequest)
            throws BusinessException {
        return userService.updateBankAccountInfo(updateAccountRequest);
    }

    @PostMapping("/update-biometric-state")
    @Transactional(readOnly = true)
    public ResponseEntity<?> updateBiometricState(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId,
            @RequestBody UpdateBiometricRequest request)
            throws BusinessException {
        return userService.updateBioState(request);
    }

    @PostMapping("/create-bear")
    @Transactional(readOnly = true)
    public ResponseEntity<?> createBear(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId, @RequestBody AccountInput accountInput)
            throws BusinessException {
        log.info("[" + requestId + "] << create bear >>");
        return userService.createBear(accountInput);
    }

    @PostMapping("/withdraw-bear")
    @Transactional(readOnly = true)
    public ResponseEntity<?> wiBear(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId, @RequestBody WithdrawBearRequest request)
            throws BusinessException {
        log.info("[" + requestId + "] << withdraw bear >>");
        return userService.withdrawBear(request);
    }

    @PostMapping("/end-bear")
    @Transactional(readOnly = true)
    public ResponseEntity<?> endBear(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId, @RequestBody AccountInput accountInput)
            throws BusinessException {
        log.info("[" + requestId + "] << end bear >>");
        return userService.endBear(accountInput.getCustId(), accountInput.getDoc_no());
    }

    @PostMapping("/end-rate-cal")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getEndRate(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId, @RequestBody GetEndRateRequest request)
            throws BusinessException {
        log.info("[" + requestId + "] << end rate info >>");
        return userService.getEndRate(request);
    }

    @GetMapping("/get-account-asset")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getAccountAsset(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId, @RequestParam String cif)
            throws BusinessException {
        log.info("[" + requestId + "] << getAccountAsset >>");
        String requestString = "cif=" + cif;
        loggingGetRequest.logRequest(httpServletRequest, requestString);
        return userService.getAccountAsset(cif);
    }

    @GetMapping("/get-account-invest")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getAccountInvest(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId, @RequestParam String cif)
            throws BusinessException {
        log.info("[" + requestId + "] << getAccountInvest >>");
        String requestString = "cif=" + cif;
        loggingGetRequest.logRequest(httpServletRequest, requestString);
        return userService.getAccountInvest(cif);
    }

    @GetMapping("/get-account-portfolio")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getAccountPortfolio(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId, @RequestParam String cif)
            throws BusinessException {
        log.info("[" + requestId + "] << getAccountPortfolio >>");
        String requestString = "cif=" + cif;
        loggingGetRequest.logRequest(httpServletRequest, requestString);
        return userService.getPortfolioInvest(cif);
    }

    @GetMapping("/get-account-notifications")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getAccountNotifications(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId, @RequestParam String cif)
            throws BusinessException {
        log.info("[" + requestId + "] << getAccountNotifications >>");
        String requestString = "cif=" + cif;
        loggingGetRequest.logRequest(httpServletRequest, requestString);
        return userService.getAccountNotifications(cif);
    }

    @PostMapping("/update-notifications")
    @Transactional(readOnly = true)
    public ResponseEntity<?> updateNoti(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId, @RequestBody UpdateNotificationsRequest updateRequest)
            throws BusinessException {
        log.info("[" + requestId + "] << update-notifications >>");
        return userService.updateNotifications(updateRequest);
    }

    @GetMapping("/get-account-statements")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getStatements(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId, @RequestParam String cif)
            throws BusinessException {
        log.info("[" + requestId + "] << getAccountNotifications >>");
        String requestString = "cif=" + cif;
        loggingGetRequest.logRequest(httpServletRequest, requestString);
        return userService.getStatements(cif);
    }

    @GetMapping("/get-product")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getProduct(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId)
            throws BusinessException {
        log.info("[" + requestId + "] << getProduct >>");
        String requestString = "";
        loggingGetRequest.logRequest(httpServletRequest, requestString);
        return userService.getProduct();
    }

    @GetMapping("/get-paytype")
    @Transactional(readOnly = true)

    public ResponseEntity<?> getPayType(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId)
            throws BusinessException {
        log.info("[" + requestId + "] << get-paytype >>");
        String requestString = "";
        loggingGetRequest.logRequest(httpServletRequest, requestString);
        return userService.getPayType();
    }

    @GetMapping("/get-term")
    @Transactional(readOnly = true)

    public ResponseEntity<?> getTerm(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId, @RequestParam("pid") String pId)
            throws BusinessException {
        log.info("[" + requestId + "] << get-term >>");
        String requestString = "pId=" + pId;
        loggingGetRequest.logRequest(httpServletRequest, requestString);
        return userService.getTerm(pId);
    }

    @GetMapping("/get-rate")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getRate(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId, @RequestParam("pid") String pId,
            @RequestParam("term") String term, @RequestParam("amount") String amount)
            throws BusinessException {

        String requestString = "pid=" + pId + "term=" + term + "amount=" + amount;

        loggingGetRequest.logRequest(httpServletRequest, requestString);

        log.info("[" + requestId + "] << get-rate >>");
        return userService.getRate(term, pId, amount);
    }

    @GetMapping("/get-rate-conf")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getConfigRate(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId)
            throws BusinessException {
        log.info("[" + requestId + "] << get-rate >>");
        String requestString = "";
        loggingGetRequest.logRequest(httpServletRequest, requestString);
        return userService.getConfigRate();
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
        String requestString = "cif=" + cif + "pid=" + pId;
        loggingGetRequest.logRequest(httpServletRequest, requestString);
        return userService.getAccountInvestByProduct(accountInput);
    }

    @PostMapping("/verify-identity")
    public ResponseEntity<?> verifyIdeEntity(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId, @RequestHeader("session") String session,
            @RequestParam("id_file") MultipartFile idFile, @RequestParam("id_type") int idType)
            throws BusinessException {

        // String custId = userService.checkSession(session);
        InfoIdentity identity = new InfoIdentity();
        return savisService.callPredict(idFile, identity, idType, session);
    }

    @PostMapping("/3gang/ekyc/vertify-identity")
    public ResponseEntity<?> vertifyId(
            @RequestParam("imgFrontId") MultipartFile imgFrontId,
            @RequestParam("imgBackId")  MultipartFile imgBackId,
            @RequestHeader("session") String session
            )
    {
        if (session == null || session.equals("")) return new ResponseEntity<>(response(toResult(Constants.FAIL, Constants.MESSAGE_FAIL, "mobile is not empty")) , HttpStatus.OK);
        if(imgFrontId.getSize()<=0) return new ResponseEntity<>(response(toResult(Constants.FAIL, Constants.MESSAGE_FAIL, "imgFrontId is not empty")) , HttpStatus.OK);
        if(imgBackId.getSize()<=0) return new ResponseEntity<>(response(toResult(Constants.FAIL, Constants.MESSAGE_FAIL, "imgBackId is not empty")), HttpStatus.OK);
        return vnptService.vertifyIdentity(imgFrontId, imgBackId, session);
    }

    @PostMapping("/3gang/ekyc/vertify-selfie")
    public ResponseEntity<?> vertifySelfie(
            @RequestParam("imgFrontId") MultipartFile imgFrontId,
            @RequestParam("imgSelfie") MultipartFile imgSelfie,
            @RequestHeader("session") String session
    )
    {
        if (session == null || session.equals("")) return new ResponseEntity<>(response(toResult(Constants.FAIL, Constants.MESSAGE_FAIL, "mobile is not empty")) , HttpStatus.OK);
        if(imgFrontId.getSize()<=0) return new ResponseEntity<>(response(toResult(Constants.FAIL, Constants.MESSAGE_FAIL, "imgFrontId is not empty")) , HttpStatus.OK);
        if(imgSelfie.getSize()<=0) return new ResponseEntity<>(response(toResult(Constants.FAIL, Constants.MESSAGE_FAIL, "imgSelfie is not empty")), HttpStatus.OK);
        return vnptService.vertifySelfie(imgFrontId, imgSelfie, session);
    }
    // Authorization

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
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = provider.crateToken(authentication);
            MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
            MyResponse response = new MyResponse();
            response.setData(token);
            response.setMessage("SUCCESSFUL!");
            response.setStatus("00");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            MyResponse response = new MyResponse();
            response.setStatus("99");
            response.setMessage("Tài khoản hoặc mật khẩu không đúng");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/update-ref")
    @Transactional(readOnly = false)
    public ResponseEntity<?> updateReferenceId(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId, @RequestBody AccountInput accountInput)
            throws BusinessException, UnsupportedEncodingException {
        log.info("[" + requestId + "] << check-updateReferenceId >>");
        return userService.updateReferenceId(accountInput);

    }

    @GetMapping("/get-ref-list")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getRefList(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId, @RequestParam("cif") String cId)
            throws BusinessException, UnsupportedEncodingException {
        log.info("[" + requestId + "] << get-ref-list >>");
        String requestString = "cif=" + cId;
        loggingGetRequest.logRequest(httpServletRequest, requestString);
        return userService.getRefList(cId);

    }

    @GetMapping("/get-coin")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getCoin(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId, @RequestParam("cif") String cId)
            throws BusinessException, UnsupportedEncodingException {
        log.info("[" + requestId + "] << check-get-coin >>");
        String requestString = "cif=" + cId;
        loggingGetRequest.logRequest(httpServletRequest, requestString);
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

        // String custId = userService.checkSession(session);
        return savisService.callCheckSelfie(frontFile, selfieFile, session);
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
        log.info("[" + requestId + "] << bankInfo >>");
        String requestString = "";
        loggingGetRequest.logRequest(httpServletRequest, requestString);
        return userService.getBankInfo();
    }

    @GetMapping("/get-ins-pck")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getInsurancePackage(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId)
            throws BusinessException {
        log.info("[" + requestId + "] << getInsurancePackage >>");
        String requestString = "";
        loggingGetRequest.logRequest(httpServletRequest, requestString);
        return userService.getInsurancePackage();
    }

    @GetMapping("/get-relation")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getRelation(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId)
            throws BusinessException {
        log.info("[" + requestId + "] << getRelation >>");
        String requestString = "";
        loggingGetRequest.logRequest(httpServletRequest, requestString);
        return userService.getRelation();
    }

    @PostMapping("/create-ins")
    @Transactional(readOnly = true)
    public ResponseEntity<?> createInsurance(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId, @RequestBody InsuranceRequest request)
            throws BusinessException {
        log.info("[" + requestId + "] << createInsurance >>");

        return userService.createInsurance(request);
    }

    @PostMapping("/cr-nav-daily")
    @Transactional(readOnly = true)
    public ResponseEntity<?> createNavDaily(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId, @RequestBody GmFundNavRequest request)
            throws BusinessException {
        log.info("[" + requestId + "] << create-nav-daily >>");

        return userService.createNavDaily(request);
    }

    @PostMapping("/save-pkg-fund")
    @Transactional(readOnly = true)
    public ResponseEntity<?> savePkgFund(HttpServletRequest httpServletRequest,
            @RequestBody PkgSumFundRequest request)
            throws BusinessException {
        System.out.println("55555");

        return userService.savePkgFundInfo(request);
    }

    @GetMapping("/get-pkg-fund-list")
    public ResponseEntity<?> getPkgFundList(HttpServletRequest httpServletRequest)
            throws BusinessException {

        return userService.getPkgFundInfo();
    }

    @GetMapping("/get-fund-list")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getFundList(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId)
            throws BusinessException {
        log.info("[" + requestId + "] << getFundList >>");
        String requestString = "";
        loggingGetRequest.logRequest(httpServletRequest, requestString);
        return userService.getFundList();
    }

    @GetMapping("/get-invest-pk")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getInvestPackage(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId)
            throws BusinessException {
        log.info("[" + requestId + "] << getInvestPackage >>");
        String requestString = "";
        loggingGetRequest.logRequest(httpServletRequest, requestString);
        return userService.getInvestPackage();
    }

    @GetMapping("/get-nav")
    public ResponseEntity<?> getNav(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId)
            throws BusinessException {
        log.info("[" + requestId + "] << nav >>");
        String requestString = "";
        loggingGetRequest.logRequest(httpServletRequest, requestString);
        return userService.getFundNAV();
    }

    @GetMapping("/get-nav-fid")
    public ResponseEntity<?> getNavByFid(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId, @RequestParam("fid") String fid)
            throws BusinessException {
        log.info("[" + requestId + "] << nav >>");

        return userService.getFundNAByFundID(fid);
    }

    @PostMapping("/cr-fund-iv")
    @Transactional(readOnly = true)
    public ResponseEntity<?> createFundInvest(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId, @RequestBody GmFundNavRequest request)
            throws BusinessException {
        log.info("[" + requestId + "] << createFundInvest >>");

        return userService.createFundInvest(request);
    }

    @PostMapping("/cr-fund-ivo")
    @Transactional(readOnly = true)
    public ResponseEntity<?> createFundInvestOptionally(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId, @RequestBody GmFundNavRequest request)
            throws BusinessException {
        log.info("[" + requestId + "] << createFundInvest >>");

        return userService.createFundInvestOptionally(request);
    }

    @GetMapping("/get-detail-pk")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getInvestPackageDetail(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId, @RequestParam("pkId") String pkId)
            throws BusinessException {
        log.info("[" + requestId + "] << getInvestPackageDetail >>");
        String requestString = "pkId=" + pkId;
        loggingGetRequest.logRequest(httpServletRequest, requestString);
        return userService.getInvestPackageDetail(pkId);
    }

    @GetMapping("/get-fund-invest")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getFundInvest(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId, @RequestParam("cid") String cid)
            throws BusinessException {
        log.info("[" + requestId + "] << getFundInvest >>");
        String requestString = "cid=" + cid;
        loggingGetRequest.logRequest(httpServletRequest, requestString);
        return userService.getFundInvest(cid);
    }

    @GetMapping("/gen-transfercode")
    @Transactional(readOnly = true)
    public ResponseEntity<?> genTransferCode(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId, @RequestParam("cid") String cid,
            @RequestParam("amt") String amount)
            throws BusinessException {
        log.info("[" + requestId + "] << genTransferCode >>");
        String requestString = "cid=" + cid + "amt=" + amount;
        loggingGetRequest.logRequest(httpServletRequest, requestString);

        return userService.genTransferCode(amount, cid);
    }

    @GetMapping("/get-fund-invest-detail")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getFundInvestDetail(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId, @RequestParam("cid") String cid,
            @RequestParam("pkid") String pkid)
            throws BusinessException {
        log.info("[" + requestId + "] << getFundInvest >>");
        String requestString = "cid=" + cid + "pkid=" + pkid;
        loggingGetRequest.logRequest(httpServletRequest, requestString);

        return userService.getFundInvestDetail(cid, pkid);
    }

    @Autowired
    MailService mailService;

    @PostMapping("/send-email-otp")
    public ResponseEntity<?> sendEmailOtp(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId,
            @RequestBody SendEmailRequest request)
            throws BusinessException {
        log.info("[" + requestId + "] << getTransHistory >>");

        // String otp = new DecimalFormat("000000").format(new
        // Random().nextInt(999999));

        Mail mail = new Mail();
        mail.setMailFrom("tuht@lendbiz.vn");
        mail.setMailTo(request.getEmail());
        mail.setMailSubject("[3Gang] Xác thực email");
        mail.setMailContent("Mã xác thực email 3Gang của bạn là: " + request.getOtp());

        return mailService.sendEmail(mail, request);
    }

    @PostMapping("/verify-email-otp")
    @Transactional(readOnly = true)
    public ResponseEntity<?> verifyEmail(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId,
            @RequestBody VerifyEmailRequest request)
            throws BusinessException {
        return mailService.verifyEmail(request);
    }

    @PostMapping("/withdraw")
    @Transactional(readOnly = true)
    public ResponseEntity<?> withdraw(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId, @RequestBody CashOutRequest request)
            throws BusinessException {
        log.info("[" + requestId + "] << withdraw >>");
        return userService.withdraw(request);
    }

    @GetMapping("/image/{img}/{type}")
    public void showImage(HttpServletResponse response, @PathVariable String img, @PathVariable String type)
            throws IOException {
        response.setContentType("image/png");

        String urlImage = "";

        InputStream inputStream = null;

        urlImage = "images/" + img + "." + type;

        File file = new File(urlImage);
        try {
            inputStream = new FileInputStream(file);
            int nRead;
            while ((nRead = inputStream.read()) != -1) {
                response.getWriter().write(nRead);
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    @GetMapping("/avatar/{custid}/{source}/{imgname}/{type}")
    public void showImageMan(HttpServletResponse response, @PathVariable String custid, @PathVariable String source,
            @PathVariable String imgname, @PathVariable String type)
            throws IOException {
        response.setContentType("image/png");

        String urlImage = "";

        InputStream inputStream = null;

        urlImage = "images/" + custid + "/" + source + "/" + imgname + "." + type;

        System.out.println(urlImage);

        File file = new File(urlImage);
        try {
            inputStream = new FileInputStream(file);
            int nRead;
            while ((nRead = inputStream.read()) != -1) {
                response.getWriter().write(nRead);
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    @Autowired
    FilesStorageService fileService;

    @PostMapping("/upload-file")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file,
            @RequestParam("cusId") String cusId) {

        String key = "avatar";
        log.info("[API UPLOAD] request param key:{} id", key, cusId);
        try {
            return fileService.uploadFile(file, key, cusId);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.UNKNOWN_ERROR, e.getMessage());
        }

    }

    public static void main(String[] args) {
        System.out.println(WordUtils.capitalize("LÊ HOÀNG NGUYÊN".toLowerCase()));
    }

    @RequestMapping("/view/{typeContract}/{phone}")
    public void showPDF(HttpServletResponse response, @PathVariable String typeContract, @PathVariable String phone)
            throws IOException {

        response.setContentType("application/pdf");
        String urlPdf = "";

        InputStream inputStream = null;
        if (typeContract.equalsIgnoreCase("1")) {
            urlPdf = "contracts/dieukhoandichvu/dk.pdf";
        } else {
            urlPdf = "contracts/sign/" + phone + "/signed_3gang.pdf";
        }

        File file = new File(urlPdf);
        try {
            inputStream = new FileInputStream(file);
            int nRead;
            while ((nRead = inputStream.read()) != -1) {
                response.getWriter().write(nRead);
            }
            inputStream.close();
        } catch (IOException e) {
            log.info(e.getMessage());
            urlPdf = "contracts/dieukhoandichvu/dk.pdf";
            try {
                File fileException = new File(urlPdf);
                inputStream = new FileInputStream(fileException);
                int nRead;
                while ((nRead = inputStream.read()) != -1) {
                    response.getWriter().write(nRead);
                }
                inputStream.close();
            } catch (IOException ex) {
                log.info(e.getMessage());
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    // @Scheduled(initialDelay = 1 * 60, fixedDelay = 2 * 5000)
    public void autoSign()
            throws BusinessException {

        userService.autoSignContract();

    }

}
