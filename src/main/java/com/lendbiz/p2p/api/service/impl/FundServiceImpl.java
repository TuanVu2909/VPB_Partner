package com.lendbiz.p2p.api.service.impl;

import com.lendbiz.p2p.api.constants.Constants;
import com.lendbiz.p2p.api.entity.BankAccountEntity;
import com.lendbiz.p2p.api.entity.CfMast;
import com.lendbiz.p2p.api.entity.amber.AFMAccountInfoEntity;
import com.lendbiz.p2p.api.entity.amber.AFMBankInfoEntity;
import com.lendbiz.p2p.api.exception.BusinessException;
import com.lendbiz.p2p.api.repository.AFMAccountInfoRepository;
import com.lendbiz.p2p.api.repository.BankAccountRepository;
import com.lendbiz.p2p.api.repository.CfMastRepository;
import com.lendbiz.p2p.api.repository.FundAmberRepository;
import com.lendbiz.p2p.api.request.amber.*;
import com.lendbiz.p2p.api.response.BaseResponse;
import com.lendbiz.p2p.api.response.amber.AFMAccount;
import com.lendbiz.p2p.api.response.amber.AFMAccInfo;
import com.lendbiz.p2p.api.response.amber.AFMToken;
import com.lendbiz.p2p.api.service.FundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FundServiceImpl extends BaseResponse<FundService> implements FundService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    FundAmberRepository fundAmberRepository;
    @Qualifier("cfMastRepository")
    @Autowired
    CfMastRepository cfMastRepository;
    @Qualifier("bankAccountRepository")
    @Autowired
    BankAccountRepository bankAccountRepository;
    @Autowired
    AFMAccountInfoRepository afmAccountInfoRepository;

    private AFMToken accessTokenAFM = null;

    @Override
    public ResponseEntity<?> getBGAccountInfo(String mobile) {
        List<CfMast> data;
        BankAccountEntity bankAccountEntity;
        try {
            data = cfMastRepository.findByMobileSms(mobile);
            bankAccountEntity = bankAccountRepository.getUserBankAccount(data.get(0).getCustid());
        }
        catch (Exception e) {
            return response(toResult(Constants.FAIL, Constants.MESSAGE_FAIL, "truy vấn không hợp lệ !"));
        }

        AFMAccount afmAccount = new AFMAccount();
        afmAccount.setLanguage(data.get(0).getLanguage());
        afmAccount.setFullname(data.get(0).getFullName());
        afmAccount.setIdcode(data.get(0).getIdCode());
        afmAccount.setIddate(data.get(0).getIdDate().toString());
        afmAccount.setIdplace(data.get(0).getIdPlace());
        afmAccount.setBirthdate(data.get(0).getDateOfBirth().toString());
        afmAccount.setSex(data.get(0).getSex());
        afmAccount.setCountry(data.get(0).getCountry());
        afmAccount.setMobile(data.get(0).getMobileSms());
        afmAccount.setAddress(data.get(0).getAddress());
        afmAccount.setEmail(data.get(0).getEmail());
        afmAccount.setBankcode(bankAccountEntity != null ? bankAccountEntity.getBankCode() : "");
        afmAccount.setCitybank("");
        afmAccount.setBankacc(bankAccountEntity != null ? bankAccountEntity.getBankAccount() : "");

        AFMAccountInfoEntity saveData = new AFMAccountInfoEntity();
        saveData.setCusId(data.get(0).getCustid());
        saveData.setIdCode(afmAccount.getIdcode());
        saveData.setMobile(afmAccount.getMobile());
        saveData.setEmail(afmAccount.getEmail());
        saveData.setBankBin(afmAccount.getBankcode());
        saveData.setBankAccount(afmAccount.getBankacc());
        saveData.setCreateDate(java.time.LocalDate.now().toString());

        this.afmAccountInfoRepository.save(saveData);
        return response(toResult(Constants.SUCCESS, Constants.MESSAGE_SUCCESS, afmAccount));
    };

    @Override
    public ResponseEntity<?> getAFMBankInfo() {
        List<AFMBankInfoEntity> data = fundAmberRepository.listBankInfo();
        return response(toResult(Constants.SUCCESS, Constants.MESSAGE_SUCCESS, data));
    };

    @Override
    public void getTokenAFM() {
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
            ResponseEntity<AFMToken> responseEntity = restTemplate.exchange(
                    Constants.AMBER_URL + "/ssoagent/oauth/token",
                    HttpMethod.POST,
                    request,
                    AFMToken.class,
                    (Object) null);
            this.accessTokenAFM = responseEntity.getBody();
            return;
        }
        catch (Exception e) {
            throw new BusinessException("111", e.getMessage());
        }
    };

    // *****************************************************************************************************************

    // TODO  3.1 API liên kết tài khoản
    @Override
    public ResponseEntity<?> linkAccount(IdentityCustomerRequest bodies) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + this.accessTokenAFM.getAccess_token());
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

    // TODO 3.1.1 API kiểm tra OTP
    @Override
    public ResponseEntity<?> checkOTPMapping(OTPMappingRequest bodies) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + this.accessTokenAFM.getAccess_token());
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

    // TODO 3.1.2 API gửi lại OTP
    @Override
    public ResponseEntity<?> resendOTPMapping(IdentityCustomerRequest bodies) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + this.accessTokenAFM.getAccess_token());
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

    // TODO 3.2 API kiểm tra tài khoản tồn tại
    @Override
    public ResponseEntity<?> checkAccountExist(String idCode) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + this.accessTokenAFM.getAccess_token());
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

    // TODO 3.3 API thông tin tài khoản
    @Override
    public ResponseEntity<?> accountInfo(IdentityCustomerRequest bodies){
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + this.accessTokenAFM.getAccess_token());
            HttpEntity<IdentityCustomerRequest> request = new HttpEntity(bodies, headers);
            ResponseEntity<?> responseEntity = restTemplate.exchange(
                    Constants.AMBER_URL + "/accounts",
                    HttpMethod.POST,
                    request,
                    Object.class,
                    (Object) null);
            Map<String, Object> data = new HashMap<>((Map<? extends String, ?>) responseEntity.getBody());
            if(data.get("EC").equals("0")) {
                ArrayList<Object> lst = (ArrayList<Object>) data.get("DT");
                Map<String, Object> map = (Map<String, Object>) lst.get(0);
                map.put("status", Constants.AFM_INFO_STATUS.get(map.get("status")));
                map.put("status_vsd", Constants.AFM_STATUS_VSD.get(map.get("status_vsd")));
                data.put("DT", map);

                AFMAccountInfoEntity saveData = this.afmAccountInfoRepository.findByMobile(bodies.getMobile());
                if(saveData != null) {
                    saveData.setStatus(map.get("status").toString());
                    saveData.setStatusVsd(map.get("status_vsd").toString());
                    saveData.setCustodycd(map.get("custodycd").toString());

                    this.afmAccountInfoRepository.save(saveData);
                }
            }
            else {
                data.put("DT", new AFMAccInfo("", "", ""));
                data.put("EC", data.get("EC").toString());
            }
            return response(toResult(Constants.SUCCESS, Constants.MESSAGE_SUCCESS, data));
        } catch (Exception e) {
            throw new BusinessException("111", e.getMessage());
        }
    };

    // TODO 3.4 API thông tin mã quỹ
    @Override
    public ResponseEntity<?> fundsInfo(){
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + this.accessTokenAFM.getAccess_token());
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

    // TODO 3.5 API thông tin giá NAV/CCQ
    @Override
    public ResponseEntity<?> navPrice(CCQInfoRequest bodies) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + this.accessTokenAFM.getAccess_token());
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

    // TODO 3.6 API thông tin số dư CCQ
    @Override
    public ResponseEntity<?> balanceInfo(String custodycd) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + this.accessTokenAFM.getAccess_token());
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

    // TODO 3.7 API thông tin thỏa thuận mua CCQ theo tài khoản và quỹ
    @Override
    public ResponseEntity<?> buyCCQ(DealCCQRequest bodies) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + this.accessTokenAFM.getAccess_token());
            HttpEntity<String> request = new HttpEntity(bodies, headers);
            ResponseEntity<?> responseEntity = restTemplate.exchange(
                    Constants.AMBER_URL + "/portfolioinfobuy",
                    HttpMethod.POST,
                    request,
                    Object.class,
                    (Object) null);
            Map<String, Object> data = new HashMap<>((Map<? extends String, ?>) responseEntity.getBody());
            if(data.get("EC").equals("0")) {
                ArrayList<Object> lst = (ArrayList<Object>) data.get("DT");

                for(Object o : lst){
                    Map<String, Object> map = (Map<String, Object>) o;
                    map.put("status", Constants.AFM_DEAL_STATUS.get(map.get("status")));
                    lst.set(lst.indexOf(o), map);
                }
                data.put("DT", lst);
            }
            return response(toResult(Constants.SUCCESS, Constants.MESSAGE_SUCCESS, data));
        } catch (Exception e) {
            throw new BusinessException("111", e.getMessage());
        }
    };

    // TODO 3.8 API thông tin thỏa thuận bán CCQ theo tài khoản và quỹ
    @Override
    public ResponseEntity<?> sellCCQ(DealCCQRequest bodies) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + this.accessTokenAFM.getAccess_token());
            HttpEntity<String> request = new HttpEntity(bodies, headers);
            ResponseEntity<?> responseEntity = restTemplate.exchange(
                    Constants.AMBER_URL + "/portfolioinfosell",
                    HttpMethod.POST,
                    request,
                    Object.class,
                    (Object) null);
            Map<String, Object> data = new HashMap<>((Map<? extends String, ?>) responseEntity.getBody());
            if(data.get("EC").equals("0")) {
                ArrayList<Object> lst = (ArrayList<Object>) data.get("DT");

                for(Object o : lst){
                    Map<String, Object> map = (Map<String, Object>) o;
                    map.put("status", Constants.AFM_DEAL_STATUS.get(map.get("status")));
                    lst.set(lst.indexOf(o), map);
                }
                data.put("DT", lst);
            }
            return response(toResult(Constants.SUCCESS, Constants.MESSAGE_SUCCESS, data));
        } catch (Exception e) {
            throw new BusinessException("111", e.getMessage());
        }
    };

    // TODO 3.9 API đặt mua CCQ thông thường
    @Override
    public ResponseEntity<?> buyNormalOrderCCQ(BuyNormalCCQRequest bodies) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + this.accessTokenAFM.getAccess_token());
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

    // TODO 3.10 API đặt mua CCQ định kỳ SIP
    @Override
    public ResponseEntity<?> buyPeriodicOrderCCQ(OrderCCQRequest bodies) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + this.accessTokenAFM.getAccess_token());
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

    // TODO 3.11 API thông tin dự kiến bán CCQ
    @Override
    public ResponseEntity<?> expectedSellInfoCCQ(SellInfoRequest bodies) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + this.accessTokenAFM.getAccess_token());
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

    // TODO 3.12 API đặt bán CCQ thông thường
    @Override
    public ResponseEntity<?> sellNormalOrderCCQ(SellNormalCCQRequest bodies) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + this.accessTokenAFM.getAccess_token());
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

    // TODO 3.13 API kiểm tra OTP đặt lệnh bán
    @Override
    public ResponseEntity<?> sellOtpOrder(OTPSellOrderRequest bodies) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + this.accessTokenAFM.getAccess_token());
            HttpEntity<OTPSellOrderRequest> request = new HttpEntity(bodies, headers);
            ResponseEntity<?> responseEntity = restTemplate.exchange(
                    Constants.AMBER_URL + "/otporder",
                    HttpMethod.POST,
                    request,
                    Object.class,
                    (Object) null);
            return response(toResult(Constants.SUCCESS, Constants.MESSAGE_SUCCESS, responseEntity.getBody()));
        } catch (Exception e) {
            throw new BusinessException("111", e.getMessage());
        }
    };

    // TODO 3.14 API gửi lại OTP đặt lệnh bán
    @Override
    public ResponseEntity<?> sellResendOtpOrder(OTPIdentityRequest bodies) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + this.accessTokenAFM.getAccess_token());
            HttpEntity<OTPIdentityRequest> request = new HttpEntity(bodies, headers);
            ResponseEntity<?> responseEntity = restTemplate.exchange(
                    Constants.AMBER_URL + "/resendotporder",
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
