package com.lendbiz.p2p.api.controller;

import com.lendbiz.p2p.api.request.amber.*;
import com.lendbiz.p2p.api.service.FundService;
// import com.twilio.Twilio;
// import com.twilio.rest.api.v2010.account.Message;
// import com.twilio.type.PhoneNumber;
// import org.springframework.http.HttpStatus;
import com.lendbiz.p2p.api.service.UserService;
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
    @Autowired
    private UserService userService;

    @GetMapping("/test")
    public String testAPI () {
        userService.jobHandleAffiliate1();
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

    @GetMapping("bgaccountinfo")
    public ResponseEntity<?> getBGAccountInfo (
            @RequestHeader String mobile
    ) { return fundService.getBGAccountInfo(mobile); }

// -------------------------------------------------------------------------------

    @PostMapping("/checkmapping")
    public ResponseEntity<?> linkAccount (
            @RequestBody IdentityCustomerRequest identityCustomerRequest) {
        return fundService.linkAccount(identityCustomerRequest);
    }

    @PostMapping("/otpmapping")
    public ResponseEntity<?> checkOTPMapping (
            @RequestBody OTPMappingRequest otpMappingRequest) {
        return fundService.checkOTPMapping(otpMappingRequest);
    }

    @PostMapping("/resendotpmapping")
    public ResponseEntity<?> resendOTPMapping (
            @RequestBody IdentityCustomerRequest identityCustomerRequest) {
        return fundService.resendOTPMapping(identityCustomerRequest);
    }

    @PostMapping("/otporder")
    public ResponseEntity<?> otpOrder (
            @RequestBody OTPSellOrderRequest otpOrderRequest) {
        return fundService.otpOrder(otpOrderRequest);
    }

    @PostMapping("/resendotporder")
    public ResponseEntity<?> resendOtpOrder (
            @RequestBody OTPIdentityRequest otpIdentityRequest) {
        return fundService.resendOtpOrder(otpIdentityRequest);
    }

    @PostMapping("/checkaccounts")
    public ResponseEntity<?> checkAccountExist (
            @RequestBody String idCode) {
        return fundService.checkAccountExist(idCode);
    }

    @PostMapping("/accounts")
    public ResponseEntity<?> accountInfo (
            @RequestBody IdentityCustomerRequest identityCustomerRequest) {
        return fundService.accountInfo(identityCustomerRequest);
    }

    @GetMapping("/getfunds")
    public ResponseEntity<?> fundsInfo () {
        return fundService.fundsInfo();
    }

    @PostMapping("/navprice")
    public ResponseEntity<?> navPrice (
            @RequestBody CCQInfoRequest ccqInfoRequest) {
        return fundService.navPrice(ccqInfoRequest);
    }

    @PostMapping("/sebalance")
    public ResponseEntity<?> balanceInfo (
            @RequestBody String custodycd) {
        return fundService.balanceInfo(custodycd);
    }

    @PostMapping("/portfolioinfobuy")
    public ResponseEntity<?> buyCCQ (
            @RequestBody DealCCQRequest dealCCQRequest) {
        return fundService.buyCCQ(dealCCQRequest);
    }

    @PostMapping("/portfolioinfosell")
    public ResponseEntity<?> sellCCQ (
            @RequestBody DealCCQRequest dealCCQRequest) {
        return fundService.sellCCQ(dealCCQRequest);
    }

    @PostMapping("/expectedsellinfo")
    public ResponseEntity<?> expectedSellInfoCCQ (
            @RequestBody SellInfoRequest sellInfoRequest) {
        return fundService.expectedSellInfoCCQ(sellInfoRequest);
    }

    @PostMapping("/normalorder")
    public ResponseEntity<?> buyNormalOrderCCQ (
            @RequestBody BuyNormalCCQRequest buyNormalCCQRequest) {
        return fundService.buyNormalOrderCCQ(buyNormalCCQRequest);
    }

    @PostMapping("/siporder")
    public ResponseEntity<?> buyPeriodicOrderCCQ (
            @RequestBody OrderCCQRequest orderCCQRequest) {
        return fundService.buyPeriodicOrderCCQ(orderCCQRequest);
    }

    @PostMapping("/normalsell")
    public ResponseEntity<?> sellNormalOrderCCQ (
            @RequestBody SellNormalCCQRequest sellNormalCCQRequest) {
        return fundService.sellNormalOrderCCQ(sellNormalCCQRequest);
    }
}
