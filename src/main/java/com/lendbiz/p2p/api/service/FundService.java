package com.lendbiz.p2p.api.service;

import com.lendbiz.p2p.api.request.amber.*;
import com.lendbiz.p2p.api.response.amber.AFMToken;
import org.springframework.http.ResponseEntity;

public interface FundService {
    ResponseEntity<?> getBGAccountInfo(String mobile);
    ResponseEntity<?> getAFMBankInfo();
    ResponseEntity<?> openAFMAccount();
    // ================================
    void getTokenAFM();
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
