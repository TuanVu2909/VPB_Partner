package com.lendbiz.p2p.api.controller;

import com.lendbiz.p2p.api.request.amber.*;
import com.lendbiz.p2p.api.service.FundService;
// import com.twilio.Twilio;
// import com.twilio.rest.api.v2010.account.Message;
// import com.twilio.type.PhoneNumber;
// import org.springframework.http.HttpStatus;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lendbiz/fund")
@Log4j2
public class FundController {

    @Autowired
    private FundService fundService;

    @GetMapping("/test")
    public String testAPI () {
        return "Ping success !";
    }
//    @GetMapping(value = "/sendSMS")
//    public ResponseEntity<String> sendSMS() {
//
//        Twilio.init("ACe7d91d1e8db819ac9b65e45a93e29c9a","2a645a8fcdb94d8c2927790af79609cb");
//
//        Message.creator(new PhoneNumber("+84334767768"),
//                new PhoneNumber("+17432442387"), "Mã OTP của bạn là: 666999. Ahihi test Service OTP").create();
//
//        return new ResponseEntity<>("Message sent successfully", HttpStatus.OK);
//    }
// -------------------------------------------------------------------------------

    @GetMapping("/ssoagent/oauth/token")
    public ResponseEntity<?> getTokenAFM () {
        return fundService.getTokenAFM();
    }

    @PostMapping("/checkmapping")
    public ResponseEntity<?> linkAccount (
            @RequestHeader String AFMToken,
            @RequestBody IdentityCustomerRequest identityCustomerRequest) {
        return fundService.linkAccount(AFMToken, identityCustomerRequest);
    }

    @PostMapping("/otpmapping")
    public ResponseEntity<?> checkOTPMapping (
            @RequestHeader String AFMToken,
            @RequestBody OTPMappingRequest otpMappingRequest) {
        return fundService.checkOTPMapping(AFMToken, otpMappingRequest);
    }

    @PostMapping("/resendotpmapping")
    public ResponseEntity<?> resendOTPMapping (
            @RequestHeader String AFMToken,
            @RequestBody IdentityCustomerRequest identityCustomerRequest) {
        return fundService.resendOTPMapping(AFMToken, identityCustomerRequest);
    }

    @PostMapping("/otpordersell")
    public ResponseEntity<?> sellOtpOrder (
            @RequestHeader String AFMToken,
            @RequestBody OTPSellOrderRequest otpSellOrderRequest) {
        return fundService.sellOtpOrder(AFMToken, otpSellOrderRequest);
    }

    @PostMapping("/resendotpordersell")
    public ResponseEntity<?> sellResendOtpOrder (
            @RequestHeader String AFMToken,
            @RequestBody OTPIdentityRequest otpIdentityRequest) {
        return fundService.sellResendOtpOrder(AFMToken, otpIdentityRequest);
    }

    @PostMapping("/checkaccounts")
    public ResponseEntity<?> checkAccountExist (
            @RequestHeader String AFMToken,
            @RequestBody String idCode) {
        return fundService.checkAccountExist(AFMToken, idCode);
    }

    @PostMapping("/accounts")
    public ResponseEntity<?> accountInfo (
            @RequestHeader String AFMToken,
            @RequestBody IdentityCustomerRequest identityCustomerRequest) {
        return fundService.accountInfo(AFMToken, identityCustomerRequest);
    }

    @GetMapping("/getfunds")
    public ResponseEntity<?> fundsInfo (@RequestHeader String AFMToken) {
        return fundService.fundsInfo(AFMToken);
    }

    @PostMapping("/navprice")
    public ResponseEntity<?> navPrice (
            @RequestHeader String AFMToken,
            @RequestBody CCQInfoRequest ccqInfoRequest) {
        return fundService.navPrice(AFMToken, ccqInfoRequest);
    }

    @PostMapping("/sebalance")
    public ResponseEntity<?> balanceInfo (
            @RequestHeader String AFMToken,
            @RequestBody String custodycd) {
        return fundService.balanceInfo(AFMToken, custodycd);
    }

    @PostMapping("/portfolioinfobuy")
    public ResponseEntity<?> buyCCQ (
            @RequestHeader String AFMToken,
            @RequestBody DealCCQRequest dealCCQRequest) {
        return fundService.buyCCQ(AFMToken, dealCCQRequest);
    }

    @PostMapping("/portfolioinfosell")
    public ResponseEntity<?> sellCCQ (
            @RequestHeader String AFMToken,
            @RequestBody DealCCQRequest dealCCQRequest) {
        return fundService.sellCCQ(AFMToken, dealCCQRequest);
    }

    @PostMapping("/expectedsellinfo")
    public ResponseEntity<?> expectedSellInfoCCQ (
            @RequestHeader String AFMToken,
            @RequestBody SellInfoRequest sellInfoRequest) {
        return fundService.expectedSellInfoCCQ(AFMToken, sellInfoRequest);
    }

    @PostMapping("/normalorder")
    public ResponseEntity<?> buyNormalOrderCCQ (
            @RequestHeader String AFMToken,
            @RequestBody BuyNormalCCQRequest buyNormalCCQRequest) {
        return fundService.buyNormalOrderCCQ(AFMToken, buyNormalCCQRequest);
    }

    @PostMapping("/siporder")
    public ResponseEntity<?> buyPeriodicOrderCCQ (
            @RequestHeader String AFMToken,
            @RequestBody OrderCCQRequest orderCCQRequest) {
        return fundService.buyPeriodicOrderCCQ(AFMToken, orderCCQRequest);
    }

    @PostMapping("/normalsell")
    public ResponseEntity<?> sellNormalOrderCCQ (
            @RequestHeader String AFMToken,
            @RequestBody SellNormalCCQRequest sellNormalCCQRequest) {
        return fundService.sellNormalOrderCCQ(AFMToken, sellNormalCCQRequest);
    }
}
