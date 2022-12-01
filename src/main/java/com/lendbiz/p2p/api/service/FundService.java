package com.lendbiz.p2p.api.service;

import com.lendbiz.p2p.api.request.amber.*;
import org.springframework.http.ResponseEntity;

public interface FundService {
    ResponseEntity<?> getBGAccountInfo(String mobile);
    ResponseEntity<?> getAFMBankInfo();
    ResponseEntity<?> openAFMAccount();
    // ================================
    ResponseEntity<?> getTokenAFM();
    ResponseEntity<?> linkAccount(String AFMToken, IdentityCustomerRequest identityCustomerRequest);
    ResponseEntity<?> checkOTPMapping(String AFMToken, OTPMappingRequest otpMappingRequest);
    ResponseEntity<?> resendOTPMapping(String AFMToken, IdentityCustomerRequest identityCustomerRequest);
    ResponseEntity<?> sellOtpOrder(String AFMToken, OTPSellOrderRequest otpSellOrderRequest);
    ResponseEntity<?> sellResendOtpOrder(String AFMToken, OTPIdentityRequest otpIdentityRequest);
    ResponseEntity<?> checkAccountExist (String AFMToken, String idCode);
    ResponseEntity<?> accountInfo(String AFMToken, IdentityCustomerRequest identityCustomerRequest);
    ResponseEntity<?> fundsInfo(String AFMToken);
    ResponseEntity<?> navPrice(String AFMToken, CCQInfoRequest ccqInfoRequest);
    ResponseEntity<?> balanceInfo(String AFMToken, String custodycd);
    ResponseEntity<?> buyCCQ(String AFMToken, DealCCQRequest dealCCQRequest);
    ResponseEntity<?> sellCCQ(String AFMToken, DealCCQRequest dealCCQRequest);
    ResponseEntity<?> expectedSellInfoCCQ(String AFMToken, SellInfoRequest sellInfoRequest);
    ResponseEntity<?> buyNormalOrderCCQ(String AFMToken, BuyNormalCCQRequest buyNormalCCQRequest);
    ResponseEntity<?> buyPeriodicOrderCCQ(String AFMToken, OrderCCQRequest orderCCQRequest);
    ResponseEntity<?> sellNormalOrderCCQ(String AFMToken, SellNormalCCQRequest sellNormalCCQRequest);
}
