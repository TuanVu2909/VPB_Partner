package com.lendbiz.p2p.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lendbiz.p2p.api.request.amber.*;
import org.springframework.http.ResponseEntity;

public interface FundService {
    ResponseEntity<?> testGetRestemplate() throws JsonProcessingException;
    ResponseEntity<?> linkAccount(IdentityCustomerRequest identityCustomerRequest);
    ResponseEntity<?> checkOTPMapping(OTPMappingRequest otpMappingRequest);
    ResponseEntity<?> resendOTPMapping(IdentityCustomerRequest identityCustomerRequest);
    ResponseEntity<?> sellOtpOrder(OTPSellOrderRequest otpSellOrderRequest);
    ResponseEntity<?> sellResendOtpOrder(OTPIdentityRequest otpIdentityRequest);
    ResponseEntity<?> checkAccountExist (String idCode);
    ResponseEntity<?> accountInfo(IdentityCustomerRequest identityCustomerRequest);
    ResponseEntity<?> fundsInfo();
    ResponseEntity<?> navPrice(CCQInfoRequest ccqInfoRequest);
    ResponseEntity<?> balanceInfo(String custodycd);
    ResponseEntity<?> buyCCQ(DealCCQRequest dealCCQRequest);
    ResponseEntity<?> sellCCQ(DealCCQRequest dealCCQRequest);
    ResponseEntity<?> expectedSellInfoCCQ(SellInfoRequest sellInfoRequest);
    ResponseEntity<?> buyNormalOrderCCQ(BuyNormalCCQRequest buyNormalCCQRequest);
    ResponseEntity<?> buyPeriodicOrderCCQ(OrderCCQRequest orderCCQRequest);
    ResponseEntity<?> sellNormalOrderCCQ(SellNormalCCQRequest sellNormalCCQRequest);
}
