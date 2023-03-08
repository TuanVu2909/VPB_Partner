package com.lendbiz.p2p.api.service.impl;

import com.lendbiz.p2p.api.constants.Constants;
import com.lendbiz.p2p.api.entity.BankAccountEntity;
import com.lendbiz.p2p.api.entity.CfMast;
import com.lendbiz.p2p.api.entity.amber.AFMAccountInfoEntity;
import com.lendbiz.p2p.api.entity.amber.AFMBankInfoEntity;
import com.lendbiz.p2p.api.exception.BusinessException;
import com.lendbiz.p2p.api.repository.*;
import com.lendbiz.p2p.api.request.amber.*;
import com.lendbiz.p2p.api.response.BaseResponse;
import com.lendbiz.p2p.api.response.amber.AFMAccount;
import com.lendbiz.p2p.api.response.amber.AFMAccStatus;
import com.lendbiz.p2p.api.response.amber.AFMToken;
import com.lendbiz.p2p.api.service.FundService;
import com.lendbiz.p2p.api.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.*;

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
    @Autowired
    AFMHisOrderRepository afmHisOrderRepository;

    private AFMToken accessTokenAFM = null;

    @Override
    public ResponseEntity<?> getBGAccountInfo(String mobile) {
        List<CfMast> lstCfmast;
        BankAccountEntity bankAccountEntity;
        CfMast user = null;
        try {
            lstCfmast = cfMastRepository.findByMobileSms(mobile);
            String custId = BaseService.getCustId(lstCfmast);

            for(CfMast u : lstCfmast){
                if (u.getCustid().equals(custId)){
                    user = u;
                }
            }
            bankAccountEntity = bankAccountRepository.getUserBankAccount(user.getCustid());
        }
        catch (Exception e) {
            return response(toResult(Constants.FAIL, Constants.MESSAGE_FAIL, "truy vấn không hợp lệ !"));
        }

        AFMBankInfoEntity afmBankInfoEntity = fundAmberRepository.findByBankBin(bankAccountEntity != null ? bankAccountEntity.getBankCode() : "-1");

        AFMAccount afmAccount = new AFMAccount();
        afmAccount.setLanguage(user.getLanguage());
        afmAccount.setFullname(user.getFullName());
        afmAccount.setIdcode(user.getIdCode());
        afmAccount.setIddate(user.getIdDate().toString());
        afmAccount.setIdplace(user.getIdPlace());
        afmAccount.setBirthdate(user.getDateOfBirth().toString());
        afmAccount.setSex(user.getSex());
        afmAccount.setCountry(user.getCountry());
        afmAccount.setMobile(user.getMobileSms());
        afmAccount.setAddress(user.getAddress());
        afmAccount.setEmail(user.getEmail());
        afmAccount.setBankcode(afmBankInfoEntity != null ? afmBankInfoEntity.getBankCode() : "");
        afmAccount.setCitybank("HN");
        afmAccount.setBankacc(bankAccountEntity != null ? bankAccountEntity.getBankAccount() : "");

        AFMAccountInfoEntity afmAcc = this.afmAccountInfoRepository.findByMobile(user.getMobileSms());
        if (afmAcc == null) {
            AFMAccountInfoEntity afmA = new AFMAccountInfoEntity();
            afmA.setCusId(user.getCustid());
            afmA.setIdCode(afmAccount.getIdcode());
            afmA.setMobile(afmAccount.getMobile());
            afmA.setEmail(afmAccount.getEmail());
            afmA.setBankBin(bankAccountEntity != null ? bankAccountEntity.getBankCode() : "");
            afmA.setBankAccount(afmAccount.getBankacc());

            this.afmAccountInfoRepository.save(afmA);
        }
        return response(toResult(Constants.SUCCESS, Constants.MESSAGE_SUCCESS, afmAccount));
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
                    Constants.AMBER_URL + "/agentapi/checkmapping",
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
                    Constants.AMBER_URL + "/agentapi/otpmapping",
                    HttpMethod.POST,
                    request,
                    Object.class,
                    (Object) null);

            Map<String, Object> data = new HashMap<>((Map<? extends String, ?>) responseEntity.getBody());
            if(data.get("EC").equals("0")) {
                ArrayList<Object> lst = (ArrayList<Object>) data.get("DT");
                Map<String, Object> map = (Map<String, Object>) lst.get(0);
                AFMAccountInfoEntity saveData = this.afmAccountInfoRepository.findByMobile(bodies.getMobile());
                if(saveData != null) {
                    saveData.setCustodycd(map.get("custodycd").toString());
                    saveData.setCreateDate(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
                    this.afmAccountInfoRepository.save(saveData);
                }
            }
            return response(toResult(Constants.SUCCESS, Constants.MESSAGE_SUCCESS, data));
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
                    Constants.AMBER_URL + "/agentapi/resendotpmapping",
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
                    Constants.AMBER_URL + "/agentapi/checkaccounts",
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
            ResponseEntity<?> responseEntity = restTemplate.postForEntity(Constants.AMBER_URL + "/agentapi/accounts", request, Object.class);
            Map<String, Object> data = new HashMap<>((Map<? extends String, ?>) responseEntity.getBody());
            if(data.get("EC").equals("0")) {
                ArrayList<Object> lst = (ArrayList<Object>) data.get("DT");
                Map<String, Object> map = (Map<String, Object>) lst.get(0);
                String status_id = map.get("status").toString();
                String status_vsd_id = map.get("status_vsd").toString();
                map.put("status_code", map.get("status"));
                map.put("status_vsd_code", map.get("status_vsd"));
                map.put("status", Constants.AFM_INFO_STATUS.get(map.get("status")));
                map.put("status_vsd", Constants.AFM_STATUS_VSD.get(map.get("status_vsd")));
                data.put("DT", map);

                AFMAccountInfoEntity saveData = this.afmAccountInfoRepository.findByMobile(bodies.getMobile());
                if(saveData != null) {
                    saveData.setStatus(map.get("status").toString());
                    saveData.setStatusVsd(map.get("status_vsd").toString());
                    saveData.setStatus_id(status_id);
                    saveData.setStatus_vsd_id(status_vsd_id);
                    this.afmAccountInfoRepository.save(saveData);
                }
            }
            else {
                data.put("DT", new AFMAccStatus("", "", ""));
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
                    Constants.AMBER_URL + "/agentapi/getfunds",
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
                    Constants.AMBER_URL + "/agentapi/navprice?fromdate={fromdate}&todate={todate}&symbol={symbol}",
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
                    Constants.AMBER_URL + "/agentapi/sebalance",
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
            AFMAccountInfoEntity getData = this.afmAccountInfoRepository.findByCustodycd(bodies.getCustodycd());

            if(getData == null) return response(toResult(Constants.FAIL, Constants.MESSAGE_FAIL, "Bạn chưa tạo tài khoản hoặc chưa lien kết tài khoản"));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + this.accessTokenAFM.getAccess_token());

            if(bodies.getFromdate() == null || "".equals(bodies.getFromdate()) && bodies.getTodate() == null || "".equals(bodies.getTodate())){
                bodies.setFromdate(getData.getCreateDate());
                bodies.setTodate(getToDate(getData.getCreateDate()));
            }

            HttpEntity<String> request = new HttpEntity(bodies, headers);
            ResponseEntity<?> responseEntity = restTemplate.exchange(
                    Constants.AMBER_URL + "/agentapi/portfolioinfobuy",
                    HttpMethod.POST,
                    request,
                    Object.class,
                    (Object) null);
            Map<String, Object> data = new HashMap<>((Map<? extends String, ?>) responseEntity.getBody());
            if(data.get("EC").equals("0")) {
                ArrayList<Object> lst = (ArrayList<Object>) data.get("DT");
                if(lst.size() > 0){
                    for(Object o : lst) {
                        Map<String, Object> map = (Map<String, Object>) o;
                        this.afmHisOrderRepository.updateAfmHisOrder(
                                Constants.AFM_DEAL_STATUS.get(map.get("status")).toString(),
                                map.get("custodycd").toString(),
                                map.get("symbol").toString(),
                                map.get("orderid").toString(),
                                map.get("status").toString()
                        );
                        map.put("status_code", map.get("status"));
                        map.put("status", Constants.AFM_DEAL_STATUS.get(map.get("status")));
                        lst.set(lst.indexOf(o), map);
                    }
                }
                Map<String, Object> afm = new HashMap<>();
                afm.put("bankcode", Constants.BANKCODE);
                afm.put("bankname", Constants.BANKNAME);
                afm.put("bankacc", Constants.BANKACC);
                afm.put("bankreceivename", Constants.BANKRECEIVENAME);
                afm.put("bankbranch", Constants.BANKBRANCH);

                data.put("AFM", afm);
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
            AFMAccountInfoEntity getData = this.afmAccountInfoRepository.findByCustodycd(bodies.getCustodycd());

            if(getData == null) return response(toResult(Constants.FAIL, Constants.MESSAGE_FAIL, "Bạn chưa tạo tài khoản hoặc chưa lien kết tài khoản"));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + this.accessTokenAFM.getAccess_token());

            if(bodies.getFromdate() == null || "".equals(bodies.getFromdate()) && bodies.getTodate() == null || "".equals(bodies.getTodate())){
                bodies.setFromdate(getData.getCreateDate());
                bodies.setTodate(getToDate(getData.getCreateDate()));
            }

            HttpEntity<String> request = new HttpEntity(bodies, headers);
            ResponseEntity<?> responseEntity = restTemplate.exchange(
                    Constants.AMBER_URL + "/agentapi/portfolioinfosell",
                    HttpMethod.POST,
                    request,
                    Object.class,
                    (Object) null);
            Map<String, Object> data = new HashMap<>((Map<? extends String, ?>) responseEntity.getBody());
            if(data.get("EC").equals("0")) {
                ArrayList<Object> lst = (ArrayList<Object>) data.get("DT");
                if(lst.size() > 0){
                    for(Object o : lst){
                        Map<String, Object> map = (Map<String, Object>) o;

                        this.afmHisOrderRepository.updateAfmHisOrder(
                                Constants.AFM_DEAL_STATUS.get(map.get("status")).toString(),
                                map.get("custodycd").toString(),
                                map.get("symbol").toString(),
                                map.get("orderid").toString(),
                                map.get("status").toString()
                        );

                        map.put("status_code", map.get("status"));
                        map.put("status", Constants.AFM_DEAL_STATUS.get(map.get("status")));
                        lst.set(lst.indexOf(o), map);
                    }
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
                    Constants.AMBER_URL + "/agentapi/normalorder",
                    HttpMethod.POST,
                    request,
                    Object.class,
                    (Object) null);
            Map<String, Object> data = new HashMap<>((Map<? extends String, ?>) responseEntity.getBody());
            return response(toResult(Constants.SUCCESS, Constants.MESSAGE_SUCCESS, data));
        } catch (Exception e) {
            throw new BusinessException("111", e.getMessage());
        }
    };

    // TODO 3.10 API đặt mua CCQ định kỳ SIP -> chưa mở
    @Override
    public ResponseEntity<?> buyPeriodicOrderCCQ(OrderCCQRequest bodies) {
        return response(toResult(Constants.SUCCESS, Constants.MESSAGE_SUCCESS, "Chưa mở tính năng này"));
//        try {
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);
//            headers.set("Authorization", "Bearer " + this.accessTokenAFM.getAccess_token());
//            HttpEntity<String> request = new HttpEntity(bodies, headers);
//            ResponseEntity<?> responseEntity = restTemplate.exchange(
//                    Constants.AMBER_URL + "/siporder",
//                    HttpMethod.POST,
//                    request,
//                    Object.class,
//                    (Object) null);
//            return response(toResult(Constants.SUCCESS, Constants.MESSAGE_SUCCESS, responseEntity.getBody()));
//        } catch (Exception e) {
//            throw new BusinessException("111", e.getMessage());
//        }
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
                    Constants.AMBER_URL + "/agentapi/expectedsellinfo",
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
                    Constants.AMBER_URL + "/agentapi/normalsell",
                    HttpMethod.POST,
                    request,
                    Object.class,
                    (Object) null);
            Map<String, Object> data = new HashMap<>((Map<? extends String, ?>) responseEntity.getBody());
            return response(toResult(Constants.SUCCESS, Constants.MESSAGE_SUCCESS, data));
        } catch (Exception e) {
            throw new BusinessException("111", e.getMessage());
        }
    };

    // TODO 3.13 API kiểm tra OTP đặt lệnh mua/bán
    @Override
    public ResponseEntity<?> otpOrder(OTPSellOrderRequest bodies) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + this.accessTokenAFM.getAccess_token());
            HttpEntity<OTPSellOrderRequest> request = new HttpEntity(bodies, headers);
            ResponseEntity<?> responseEntity = restTemplate.exchange(
                    Constants.AMBER_URL + "/agentapi/otporder",
                    HttpMethod.POST,
                    request,
                    Object.class,
                    (Object) null);
            Map<String, Object> data = new HashMap<>((Map<? extends String, ?>) responseEntity.getBody());
            if(data.get("EC").equals("0")) {
                Map<String, Object> dt = new HashMap<>((Map<? extends String, ?>) data.get("DT"));
                String status_id = "";
                for(Map.Entry<String, Object> entry: Constants.AFM_DEAL_STATUS.entrySet()) {
                    if(dt.get("status").toString().equals(entry.getValue())) {
                        status_id = entry.getKey();
                        break;
                    }
                }
                this.afmHisOrderRepository.saveData(
                        dt.get("custodycd").toString(),
                        dt.get("symbol").toString(),
                        dt.get("srtype").toString(),
                        dt.get("exectype").toString(),
                        dt.get("txdate").toString(),
                        dt.get("tradingdate").toString(),
                        dt.get("status").toString(),
                        dt.get("amt").toString(),
                        dt.get("qtty").toString(),
                        dt.get("orderid").toString(),
                        status_id
                );
            }
            return response(toResult(Constants.SUCCESS, Constants.MESSAGE_SUCCESS, data));
        } catch (Exception e) {
            throw new BusinessException("111", e.getMessage());
        }
    };

    // TODO 3.14 API gửi lại OTP đặt lệnh mua/bán
    @Override
    public ResponseEntity<?> resendOtpOrder(OTPIdentityRequest bodies) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + this.accessTokenAFM.getAccess_token());
            HttpEntity<OTPIdentityRequest> request = new HttpEntity(bodies, headers);
            ResponseEntity<?> responseEntity = restTemplate.exchange(
                    Constants.AMBER_URL + "/agentapi/resendotporder",
                    HttpMethod.POST,
                    request,
                    Object.class,
                    (Object) null);
            return response(toResult(Constants.SUCCESS, Constants.MESSAGE_SUCCESS, responseEntity.getBody()));
        } catch (Exception e) {
            throw new BusinessException("111", e.getMessage());
        }
    };

    private String getToDate(String createDate) {
        int year = Year.now().getValue() + 1;
        return createDate.substring(0, 6) + year;
    }
}
