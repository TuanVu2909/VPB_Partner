package com.lendbiz.p2p.api.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lendbiz.p2p.api.exception.BusinessException;
import com.lendbiz.p2p.api.request.amber.*;
import com.lendbiz.p2p.api.response.BaseResponse;
import com.lendbiz.p2p.api.response.amber.AmberResponse;
import com.lendbiz.p2p.api.service.FundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class FundServiceImpl extends BaseResponse<FundService> implements FundService {

    private final String AMBER_URL = "http://10.255.241.142:1351";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ResponseEntity<?> testGetRestemplate() {

        try {
            HttpHeaders headers = new HttpHeaders();
            ObjectMapper mapper = new ObjectMapper();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("requestId", "1111111");
            HttpEntity<String> request = new HttpEntity(headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    "http://localhost:9019/lendbiz/get-product",
                    HttpMethod.GET,
                    request,
                    String.class,
                    (Object) null);
            Map<String, Object> map = mapper.readValue(responseEntity.getBody(), new TypeReference<>(){});
            return response(toResult(map.get("data")));
        } catch (JsonProcessingException e) {
            throw new BusinessException("111", "Parse JSON fail");
        } catch (Exception e) {
            throw new BusinessException("111", e.getMessage());
        }
    };

    // TODO API liên kết tài khoản
    @Override
    public ResponseEntity<?> linkAccount(IdentityCustomerRequest bodies) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<IdentityCustomerRequest> request = new HttpEntity(bodies, headers);
            ResponseEntity<AmberResponse> responseEntity = restTemplate.exchange(
                    AMBER_URL + "/checkmapping",
                    HttpMethod.POST,
                    request,
                    AmberResponse.class,
                    (Object) null);
            String status = responseEntity.getBody().getEC();
            String message = responseEntity.getBody().getEM();
            return response(toResult(status, message, responseEntity.getBody().getDT()));
        } catch (Exception e) {
            throw new BusinessException("111", e.getMessage());
        }
    };

    // TODO API kiểm tra OTP
    @Override
    public ResponseEntity<?> checkOTPMapping(OTPMappingRequest bodies) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<IdentityCustomerRequest> request = new HttpEntity(bodies, headers);
            ResponseEntity<AmberResponse> responseEntity = restTemplate.exchange(
                    AMBER_URL + "/otpmapping",
                    HttpMethod.POST,
                    request,
                    AmberResponse.class,
                    (Object) null);
            String status = responseEntity.getBody().getEC();
            String message = responseEntity.getBody().getEM();
            return response(toResult(status, message, responseEntity.getBody().getDT()));
        } catch (Exception e) {
            throw new BusinessException("111", e.getMessage());
        }
    };

    // TODO API gửi lại OTP
    @Override
    public ResponseEntity<?> resendOTPMapping(IdentityCustomerRequest bodies) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<IdentityCustomerRequest> request = new HttpEntity(bodies, headers);
            ResponseEntity<AmberResponse> responseEntity = restTemplate.exchange(
                    AMBER_URL + "/resendotpmapping",
                    HttpMethod.POST,
                    request,
                    AmberResponse.class,
                    (Object) null);
            String status = responseEntity.getBody().getEC();
            String message = responseEntity.getBody().getEM();
            return response(toResult(status, message, responseEntity.getBody().getDT()));
        } catch (Exception e) {
            throw new BusinessException("111", e.getMessage());
        }
    };

    // TODO API kiểm tra OTP đặt lệnh bán
    @Override
    public ResponseEntity<?> sellOtpOrder(OTPSellOrderRequest bodies) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<OTPSellOrderRequest> request = new HttpEntity(bodies, headers);
            ResponseEntity<AmberResponse> responseEntity = restTemplate.exchange(
                    AMBER_URL + "/otpordersell",
                    HttpMethod.POST,
                    request,
                    AmberResponse.class,
                    (Object) null);
            String status = responseEntity.getBody().getEC();
            String message = responseEntity.getBody().getEM();
            return response(toResult(status, message, responseEntity.getBody().getDT()));
        } catch (Exception e) {
            throw new BusinessException("111", e.getMessage());
        }
    };

    // TODO API gửi lại OTP đặt lệnh bán
    @Override
    public ResponseEntity<?> sellResendOtpOrder(OTPIdentityRequest bodies) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<OTPIdentityRequest> request = new HttpEntity(bodies, headers);
            ResponseEntity<AmberResponse> responseEntity = restTemplate.exchange(
                    AMBER_URL + "/resendotpordersell",
                    HttpMethod.POST,
                    request,
                    AmberResponse.class,
                    (Object) null);
            String status = responseEntity.getBody().getEC();
            String message = responseEntity.getBody().getEM();
            return response(toResult(status, message, responseEntity.getBody().getDT()));
        } catch (Exception e) {
            throw new BusinessException("111", e.getMessage());
        }
    };

    // TODO API kiểm tra tài khoản tồn tại
    @Override
    public ResponseEntity<?> checkAccountExist(String idCode){
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity(idCode, headers);
            ResponseEntity<AmberResponse> responseEntity = restTemplate.exchange(
                    AMBER_URL + "/checkaccounts",
                    HttpMethod.POST,
                    request,
                    AmberResponse.class,
                    (Object) null);
            String status = responseEntity.getBody().getEC();
            String message = responseEntity.getBody().getEM();
            return response(toResult(status, message, responseEntity.getBody().getDT()));
        } catch (Exception e) {
            throw new BusinessException("111", e.getMessage());
        }
    };

    // TODO API thông tin tài khoản
    @Override
    public ResponseEntity<?> accountInfo(IdentityCustomerRequest bodies){
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<IdentityCustomerRequest> request = new HttpEntity(bodies, headers);
            ResponseEntity<AmberResponse> responseEntity = restTemplate.exchange(
                    AMBER_URL + "/accounts",
                    HttpMethod.POST,
                    request,
                    AmberResponse.class,
                    (Object) null);
            String status = responseEntity.getBody().getEC();
            String message = responseEntity.getBody().getEM();
            return response(toResult(status, message, responseEntity.getBody().getDT()));
        } catch (Exception e) {
            throw new BusinessException("111", e.getMessage());
        }
    };

    // TODO API thông tin mã quỹ
    @Override
    public ResponseEntity<?> fundsInfo(){
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Object> request = new HttpEntity(null, headers);
            ResponseEntity<AmberResponse> responseEntity = restTemplate.exchange(
                    AMBER_URL + "/getfunds",
                    HttpMethod.GET,
                    request,
                    AmberResponse.class,
                    (Object) null);
            String status = responseEntity.getBody().getEC();
            String message = responseEntity.getBody().getEM();
            return response(toResult(status, message, responseEntity.getBody().getDT()));
        } catch (Exception e) {
            throw new BusinessException("111", e.getMessage());
        }
    };

    // TODO API thông tin giá NAV/CCQ
    @Override
    public ResponseEntity<?> navPrice(CCQInfoRequest bodies) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<CCQInfoRequest> request = new HttpEntity(bodies, headers);
            ResponseEntity<AmberResponse> responseEntity = restTemplate.exchange(
                    AMBER_URL + "/navprice",
                    HttpMethod.GET,
                    request,
                    AmberResponse.class,
                    (Object) null);
            String status = responseEntity.getBody().getEC();
            String message = responseEntity.getBody().getEM();
            return response(toResult(status, message, responseEntity.getBody().getDT()));
        } catch (Exception e) {
            throw new BusinessException("111", e.getMessage());
        }
    };

    // TODO API thông tin số dư CCQ
    @Override
    public ResponseEntity<?> balanceInfo(String custodycd) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity(custodycd, headers);
            ResponseEntity<AmberResponse> responseEntity = restTemplate.exchange(
                    AMBER_URL + "/sebalance",
                    HttpMethod.POST,
                    request,
                    AmberResponse.class,
                    (Object) null);
            String status = responseEntity.getBody().getEC();
            String message = responseEntity.getBody().getEM();
            return response(toResult(status, message, responseEntity.getBody().getDT()));
        } catch (Exception e) {
            throw new BusinessException("111", e.getMessage());
        }
    };

    // TODO API thông tin thỏa thuận mua CCQ theo tài khoản và quỹ
    @Override
    public ResponseEntity<?> buyCCQ(DealCCQRequest bodies) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity(bodies, headers);
            ResponseEntity<AmberResponse> responseEntity = restTemplate.exchange(
                    AMBER_URL + "/portfolioinfobuy",
                    HttpMethod.POST,
                    request,
                    AmberResponse.class,
                    (Object) null);
            String status = responseEntity.getBody().getEC();
            String message = responseEntity.getBody().getEM();
            return response(toResult(status, message, responseEntity.getBody().getDT()));
        } catch (Exception e) {
            throw new BusinessException("111", e.getMessage());
        }
    };

    // TODO API thông tin thỏa thuận bán CCQ theo tài khoản và quỹ
    @Override
    public ResponseEntity<?> sellCCQ(DealCCQRequest bodies) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity(bodies, headers);
            ResponseEntity<AmberResponse> responseEntity = restTemplate.exchange(
                    AMBER_URL + "/portfolioinfosell",
                    HttpMethod.POST,
                    request,
                    AmberResponse.class,
                    (Object) null);
            String status = responseEntity.getBody().getEC();
            String message = responseEntity.getBody().getEM();
            return response(toResult(status, message, responseEntity.getBody().getDT()));
        } catch (Exception e) {
            throw new BusinessException("111", e.getMessage());
        }
    };

    // TODO API thông tin dự kiến bán CCQ
    @Override
    public ResponseEntity<?> expectedSellInfoCCQ(SellInfoRequest bodies) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<SellInfoRequest> request = new HttpEntity(bodies, headers);
            ResponseEntity<AmberResponse> responseEntity = restTemplate.exchange(
                    AMBER_URL + "/expectedsellinfo",
                    HttpMethod.POST,
                    request,
                    AmberResponse.class,
                    (Object) null);
            String status = responseEntity.getBody().getEC();
            String message = responseEntity.getBody().getEM();
            return response(toResult(status, message, responseEntity.getBody().getDT()));
        } catch (Exception e) {
            throw new BusinessException("111", e.getMessage());
        }
    };

    // TODO API đặt mua CCQ thông thường
    @Override
    public ResponseEntity<?> buyNormalOrderCCQ(BuyNormalCCQRequest bodies) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity(bodies, headers);
            ResponseEntity<AmberResponse> responseEntity = restTemplate.exchange(
                    AMBER_URL + "/normalorder",
                    HttpMethod.POST,
                    request,
                    AmberResponse.class,
                    (Object) null);
            String status = responseEntity.getBody().getEC();
            String message = responseEntity.getBody().getEM();
            return response(toResult(status, message, responseEntity.getBody().getDT()));
        } catch (Exception e) {
            throw new BusinessException("111", e.getMessage());
        }
    };

    // TODO API đặt mua CCQ định kỳ
    @Override
    public ResponseEntity<?> buyPeriodicOrderCCQ(OrderCCQRequest bodies) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity(bodies, headers);
            ResponseEntity<AmberResponse> responseEntity = restTemplate.exchange(
                    AMBER_URL + "/siporder",
                    HttpMethod.POST,
                    request,
                    AmberResponse.class,
                    (Object) null);
            String status = responseEntity.getBody().getEC();
            String message = responseEntity.getBody().getEM();
            return response(toResult(status, message, responseEntity.getBody().getDT()));
        } catch (Exception e) {
            throw new BusinessException("111", e.getMessage());
        }
    };

    // TODO API đặt bán CCQ thông thường
    @Override
    public ResponseEntity<?> sellNormalOrderCCQ(SellNormalCCQRequest bodies) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<SellNormalCCQRequest> request = new HttpEntity(bodies, headers);
            ResponseEntity<AmberResponse> responseEntity = restTemplate.exchange(
                    AMBER_URL + "/normalsell",
                    HttpMethod.POST,
                    request,
                    AmberResponse.class,
                    (Object) null);
            String status = responseEntity.getBody().getEC();
            String message = responseEntity.getBody().getEM();
            return response(toResult(status, message, responseEntity.getBody().getDT()));
        } catch (Exception e) {
            throw new BusinessException("111", e.getMessage());
        }
    };
}
