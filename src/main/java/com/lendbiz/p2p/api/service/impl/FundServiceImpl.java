package com.lendbiz.p2p.api.service.impl;

import com.lendbiz.p2p.api.constants.Constants;
import com.lendbiz.p2p.api.exception.BusinessException;
import com.lendbiz.p2p.api.request.amber.*;
import com.lendbiz.p2p.api.response.BaseResponse;
import com.lendbiz.p2p.api.service.FundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

@Service
public class FundServiceImpl extends BaseResponse<FundService> implements FundService {

    @Autowired
    private RestTemplate restTemplate;

    // TODO API get token
    @Override
    public ResponseEntity<?> getTokenAFM() {
        LoginRequest bodies = new LoginRequest();
        bodies.setGrant_type(Constants.GRANT_TYPE);
        bodies.setClient_id(Constants.CLIENT_ID);
        bodies.setClient_secret(Constants.CLIENT_SECRET);
        bodies.setUsername(Constants.USERNAME);
        bodies.setPassword(Constants.PASSWORD);

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<LoginRequest> request = new HttpEntity(bodies, headers);
            ResponseEntity<?> responseEntity = restTemplate.exchange(
                    Constants.AMBER_URL + "/ssoagent/oauth/token",
                    HttpMethod.POST,
                    request,
                    Object.class,
                    (Object) null);
            return response(toResult(Constants.SUCCESS, Constants.MESSAGE_SUCCESS, responseEntity.getBody()));
        }
        catch (Exception e) {
            throw new BusinessException("111", e.getMessage());
        }
    };

    // TODO API liên kết tài khoản
    @Override
    public ResponseEntity<?> linkAccount(String AFMToken, IdentityCustomerRequest bodies) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer "+AFMToken);
            HttpEntity<IdentityCustomerRequest> request = new HttpEntity(bodies, headers);
            ResponseEntity<?> responseEntity = restTemplate.exchange(
                    Constants.AMBER_URL + "/checkmapping",
                    HttpMethod.POST,
                    request,
                    Object.class,
                    (Object) null);
            return response(toResult(Constants.SUCCESS, Constants.MESSAGE_SUCCESS, responseEntity.getBody()));
        } catch (Exception e) {
            throw new BusinessException("111", e.getMessage());
        }
    };

    // TODO API kiểm tra OTP
    @Override
    public ResponseEntity<?> checkOTPMapping(String AFMToken, OTPMappingRequest bodies) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer "+AFMToken);
            HttpEntity<OTPMappingRequest> request = new HttpEntity(bodies, headers);
            ResponseEntity<?> responseEntity = restTemplate.exchange(
                    Constants.AMBER_URL + "/otpmapping",
                    HttpMethod.POST,
                    request,
                    Object.class,
                    (Object) null);
            return response(toResult(Constants.SUCCESS, Constants.MESSAGE_SUCCESS, responseEntity.getBody()));
        } catch (Exception e) {
            throw new BusinessException("111", e.getMessage());
        }
    };

    // TODO API gửi lại OTP
    @Override
    public ResponseEntity<?> resendOTPMapping(String AFMToken, IdentityCustomerRequest bodies) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer "+AFMToken);
            HttpEntity<IdentityCustomerRequest> request = new HttpEntity(bodies, headers);
            ResponseEntity<?> responseEntity = restTemplate.exchange(
                    Constants.AMBER_URL + "/resendotpmapping",
                    HttpMethod.POST,
                    request,
                    Object.class,
                    (Object) null);
            return response(toResult(Constants.SUCCESS, Constants.MESSAGE_SUCCESS, responseEntity.getBody()));
        } catch (Exception e) {
            throw new BusinessException("111", e.getMessage());
        }
    };

    // TODO API kiểm tra OTP đặt lệnh bán
    @Override
    public ResponseEntity<?> sellOtpOrder(String AFMToken, OTPSellOrderRequest bodies) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer "+AFMToken);
            HttpEntity<OTPSellOrderRequest> request = new HttpEntity(bodies, headers);
            ResponseEntity<?> responseEntity = restTemplate.exchange(
                    Constants.AMBER_URL + "/otpordersell",
                    HttpMethod.POST,
                    request,
                    Object.class,
                    (Object) null);
            return response(toResult(Constants.SUCCESS, Constants.MESSAGE_SUCCESS, responseEntity.getBody()));
        } catch (Exception e) {
            throw new BusinessException("111", e.getMessage());
        }
    };

    // TODO API gửi lại OTP đặt lệnh bán
    @Override
    public ResponseEntity<?> sellResendOtpOrder(String AFMToken, OTPIdentityRequest bodies) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer "+AFMToken);
            HttpEntity<OTPIdentityRequest> request = new HttpEntity(bodies, headers);
            ResponseEntity<?> responseEntity = restTemplate.exchange(
                    Constants.AMBER_URL + "/resendotpordersell",
                    HttpMethod.POST,
                    request,
                    Object.class,
                    (Object) null);
            return response(toResult(Constants.SUCCESS, Constants.MESSAGE_SUCCESS, responseEntity.getBody()));
        } catch (Exception e) {
            throw new BusinessException("111", e.getMessage());
        }
    };

    // TODO API kiểm tra tài khoản tồn tại
    @Override
    public ResponseEntity<?> checkAccountExist(String AFMToken, String idCode){
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer "+AFMToken);
            HttpEntity<String> request = new HttpEntity(idCode, headers);
            ResponseEntity<?> responseEntity = restTemplate.exchange(
                    Constants.AMBER_URL + "/checkaccounts",
                    HttpMethod.POST,
                    request,
                    Object.class,
                    (Object) null);
            return response(toResult(Constants.SUCCESS, Constants.MESSAGE_SUCCESS, responseEntity.getBody()));
        } catch (Exception e) {
            throw new BusinessException("111", e.getMessage());
        }
    };

    // TODO API thông tin tài khoản
    @Override
    public ResponseEntity<?> accountInfo(String AFMToken, IdentityCustomerRequest bodies){
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer "+AFMToken);
            HttpEntity<IdentityCustomerRequest> request = new HttpEntity(bodies, headers);
            ResponseEntity<?> responseEntity = restTemplate.exchange(
                    Constants.AMBER_URL + "/accounts",
                    HttpMethod.POST,
                    request,
                    Object.class,
                    (Object) null);
            return response(toResult(Constants.SUCCESS, Constants.MESSAGE_SUCCESS, responseEntity.getBody()));
        } catch (Exception e) {
            throw new BusinessException("111", e.getMessage());
        }
    };

    // TODO API thông tin mã quỹ
    @Override
    public ResponseEntity<?> fundsInfo(String AFMToken){
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer "+AFMToken);
            HttpEntity<Object> request = new HttpEntity(null, headers);
            ResponseEntity<?> responseEntity = restTemplate.exchange(
                    Constants.AMBER_URL + "/getfunds",
                    HttpMethod.GET,
                    request,
                    Object.class,
                    (Object) null);
            return response(toResult(Constants.SUCCESS, Constants.MESSAGE_SUCCESS, responseEntity.getBody()));
        } catch (Exception e) {
            throw new BusinessException("111", e.getMessage());
        }
    };

    // TODO API thông tin giá NAV/CCQ
    @Override
    public ResponseEntity<?> navPrice(String AFMToken, CCQInfoRequest bodies) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer "+AFMToken);
            HttpEntity<CCQInfoRequest> request = new HttpEntity(headers);
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> params = mapper.convertValue(bodies, Map.class);
            ResponseEntity<?> responseEntity = restTemplate.exchange(
                    Constants.AMBER_URL + "/navprice?fromdate={fromdate}&todate={todate}&symbol={symbol}",
                    HttpMethod.GET,
                    request,
                    Object.class,
                    params);
            return response(toResult(Constants.SUCCESS, Constants.MESSAGE_SUCCESS, responseEntity.getBody()));
        } catch (Exception e) {
            throw new BusinessException("111", e.getMessage());
        }
    };

    // TODO API thông tin số dư CCQ
    @Override
    public ResponseEntity<?> balanceInfo(String AFMToken, String custodycd) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer "+AFMToken);
            HttpEntity<String> request = new HttpEntity(custodycd, headers);
            ResponseEntity<?> responseEntity = restTemplate.exchange(
                    Constants.AMBER_URL + "/sebalance",
                    HttpMethod.POST,
                    request,
                    Object.class,
                    (Object) null);
            return response(toResult(Constants.SUCCESS, Constants.MESSAGE_SUCCESS, responseEntity.getBody()));
        } catch (Exception e) {
            throw new BusinessException("111", e.getMessage());
        }
    };

    // TODO API thông tin thỏa thuận mua CCQ theo tài khoản và quỹ
    @Override
    public ResponseEntity<?> buyCCQ(String AFMToken, DealCCQRequest bodies) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer "+AFMToken);
            HttpEntity<String> request = new HttpEntity(bodies, headers);
            ResponseEntity<?> responseEntity = restTemplate.exchange(
                    Constants.AMBER_URL + "/portfolioinfobuy",
                    HttpMethod.POST,
                    request,
                    Object.class,
                    (Object) null);
            return response(toResult(Constants.SUCCESS, Constants.MESSAGE_SUCCESS, responseEntity.getBody()));
        } catch (Exception e) {
            throw new BusinessException("111", e.getMessage());
        }
    };

    // TODO API thông tin thỏa thuận bán CCQ theo tài khoản và quỹ
    @Override
    public ResponseEntity<?> sellCCQ(String AFMToken, DealCCQRequest bodies) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer "+AFMToken);
            HttpEntity<String> request = new HttpEntity(bodies, headers);
            ResponseEntity<?> responseEntity = restTemplate.exchange(
                    Constants.AMBER_URL + "/portfolioinfosell",
                    HttpMethod.POST,
                    request,
                    Object.class,
                    (Object) null);
            return response(toResult(Constants.SUCCESS, Constants.MESSAGE_SUCCESS, responseEntity.getBody()));
        } catch (Exception e) {
            throw new BusinessException("111", e.getMessage());
        }
    };

    // TODO API thông tin dự kiến bán CCQ
    @Override
    public ResponseEntity<?> expectedSellInfoCCQ(String AFMToken, SellInfoRequest bodies) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer "+AFMToken);
            HttpEntity<SellInfoRequest> request = new HttpEntity(bodies, headers);
            ResponseEntity<?> responseEntity = restTemplate.exchange(
                    Constants.AMBER_URL + "/expectedsellinfo",
                    HttpMethod.POST,
                    request,
                    Object.class,
                    (Object) null);
            return response(toResult(Constants.SUCCESS, Constants.MESSAGE_SUCCESS, responseEntity.getBody()));
        } catch (Exception e) {
            throw new BusinessException("111", e.getMessage());
        }
    };

    // TODO API đặt mua CCQ thông thường
    @Override
    public ResponseEntity<?> buyNormalOrderCCQ(String AFMToken, BuyNormalCCQRequest bodies) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer "+AFMToken);
            HttpEntity<String> request = new HttpEntity(bodies, headers);
            ResponseEntity<?> responseEntity = restTemplate.exchange(
                    Constants.AMBER_URL + "/normalorder",
                    HttpMethod.POST,
                    request,
                    Object.class,
                    (Object) null);
            return response(toResult(Constants.SUCCESS, Constants.MESSAGE_SUCCESS, responseEntity.getBody()));
        } catch (Exception e) {
            throw new BusinessException("111", e.getMessage());
        }
    };

    // TODO API đặt mua CCQ định kỳ
    @Override
    public ResponseEntity<?> buyPeriodicOrderCCQ(String AFMToken, OrderCCQRequest bodies) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer "+AFMToken);
            HttpEntity<String> request = new HttpEntity(bodies, headers);
            ResponseEntity<?> responseEntity = restTemplate.exchange(
                    Constants.AMBER_URL + "/siporder",
                    HttpMethod.POST,
                    request,
                    Object.class,
                    (Object) null);
            return response(toResult(Constants.SUCCESS, Constants.MESSAGE_SUCCESS, responseEntity.getBody()));
        } catch (Exception e) {
            throw new BusinessException("111", e.getMessage());
        }
    };

    // TODO API đặt bán CCQ thông thường
    @Override
    public ResponseEntity<?> sellNormalOrderCCQ(String AFMToken, SellNormalCCQRequest bodies) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer "+AFMToken);
            HttpEntity<SellNormalCCQRequest> request = new HttpEntity(bodies, headers);
            ResponseEntity<?> responseEntity = restTemplate.exchange(
                    Constants.AMBER_URL + "/normalsell",
                    HttpMethod.POST,
                    request,
                    Object.class,
                    (Object) null);
            return response(toResult(Constants.SUCCESS, Constants.MESSAGE_SUCCESS, responseEntity.getBody()));
        } catch (Exception e) {
            throw new BusinessException("111", e.getMessage());
        }
    };
}
