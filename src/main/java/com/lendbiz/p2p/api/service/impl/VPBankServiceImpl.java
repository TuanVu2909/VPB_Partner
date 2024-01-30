package com.lendbiz.p2p.api.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lendbiz.p2p.api.configs.RSA.CipherUtility;
import com.lendbiz.p2p.api.constants.ErrorCode;
import com.lendbiz.p2p.api.entity.KeysManageEntity;
import com.lendbiz.p2p.api.entity.bank.VPBankEntity;
import com.lendbiz.p2p.api.helper.SignatureNumber;
import com.lendbiz.p2p.api.repository.KeysManageRepository;
import com.lendbiz.p2p.api.repository.VPBankRepository;
import com.lendbiz.p2p.api.request.VPBbankRequest;
import com.lendbiz.p2p.api.response.BaseResponse;
import com.lendbiz.p2p.api.response.VPBank.CurlResponse;
import com.lendbiz.p2p.api.response.VPBank.ExternalTransferDTO;
import com.lendbiz.p2p.api.response.VPBank.TranferDTO;
import com.lendbiz.p2p.api.response.VPBank.VPBResDTO;
import com.lendbiz.p2p.api.service.VPBankService;
import com.lendbiz.p2p.api.service.base.CurlService;

import lombok.extern.log4j.Log4j2;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import java.net.URL;
import java.security.PrivateKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
public class VPBankServiceImpl extends BaseResponse<VPBankService> implements VPBankService {

    @Autowired
    private VPBankRepository vpBankRepository;

    @Autowired
    private CipherUtility cipherUtility;

    @Autowired
    private KeysManageRepository keysManageRepository;

    @Autowired
    private CurlService curlService;

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    @Transactional
    public VPBResDTO transFluctuations(VPBbankRequest request, String signature) {
        //boolean checkSuccess = this.checkSignature(request, signature);
        if(true){
            VPBankEntity vpBankEntity = null;
            try {
                vpBankEntity = vpBankRepository.insertNotify(
                        request.getMasterAccountNumber(),
                        request.getAmount(),
                        request.getTransactionDate(),
                        request.getTransactionId(),
                        request.getRemark(),
                        signature);
            }
            catch(Exception e){
                logger.error("error => "+e.getMessage());
                return new VPBResDTO("400", ErrorCode.EXCEPTION_ERROR, e.getMessage(), request.getTransactionId());
            }
            if (vpBankEntity != null && "00".equals(vpBankEntity.getStatus())){
                return new VPBResDTO("200", "", "", request.getTransactionId());
            }
            logger.error("error => No data");
            return new VPBResDTO("500", "-1", "No data", request.getTransactionId());

        }
        else {
            System.out.println("Xác thực không thành công");
            return new VPBResDTO("400", "", "Xác thực không thành công", request.getTransactionId());
        }
    }

    private final String VPB_URI = "https://uat-ob-gatewaylb-int.vpbank.com.vn";

    @Override
    public ResponseEntity<?> getVPBToken(String headerAuthorization, MultiValueMap<String, String> form_data_urlencoded) {

        String scopeVal = form_data_urlencoded.getFirst("scope");
        String grant_typeVal = form_data_urlencoded.getFirst("grant_type");

        String dataType = "data-urlencode";
        String url = VPB_URI + "/token";
        String method = "POST";

        Map<String, Object> requestHeaders = new HashMap<>();
        requestHeaders.put("Content-Type", "application/x-www-form-urlencoded");
        requestHeaders.put("Authorization", "Basic "+headerAuthorization);

        Map<String, Object> bodies = new HashMap<>();
        bodies.put("scope", scopeVal);
        bodies.put("grant_type", grant_typeVal);

        CurlResponse resAPI = curlService.executeCurlCommand(url, method, requestHeaders, bodies, dataType);

        return this.resAPI_42_112_38_103(resAPI);
    }

    @Override
    public ResponseEntity<?> getBankList(String headerAuthorization, String headerIdn_app, String headerX_request_id) {

        String url = VPB_URI + "/payments/v1/banks/";
        String method = "GET";

        Map<String, Object> requestHeaders = new HashMap<>();
        requestHeaders.put("Content-Type", "application/json");
        requestHeaders.put("Authorization", "Bearer "+headerAuthorization);
        requestHeaders.put("IDN-App", headerIdn_app);
        requestHeaders.put("x-request-id", headerX_request_id);

        CurlResponse resAPI = curlService.executeCurlCommand(url, method, requestHeaders, null, null);

        return this.resAPI_42_112_38_103(resAPI);
    }

    @Override
    public ResponseEntity<?> getBranchList(String headerAuthorization, String headerIdn_app, String headerX_request_id, String bankId) {
        String url = VPB_URI + "/payments/v1/banks/" + bankId;
        String method = "GET";

        Map<String, Object> requestHeaders = new HashMap<>();
        requestHeaders.put("Content-Type", "application/json");
        requestHeaders.put("Authorization", "Bearer "+headerAuthorization);
        requestHeaders.put("IDN-App", headerIdn_app);
        requestHeaders.put("x-request-id", headerX_request_id);

        CurlResponse resAPI = curlService.executeCurlCommand(url, method, requestHeaders, null, null);

        return this.resAPI_42_112_38_103(resAPI);
    }

    @Override
    public ResponseEntity<?> getBeneficiaryInfo(String headerAuthorization, String headerIdn_app, String headerX_request_id, String bankCode, String benNumber, String benType) {
        String url = VPB_URI + "/payments/v1/beneficiary/info?benType=" + benType;
        String method = "GET";

        Map<String, Object> requestHeaders = new HashMap<>();
        requestHeaders.put("Content-Type", "application/json");
        requestHeaders.put("Authorization", "Bearer "+headerAuthorization);
        requestHeaders.put("IDN-App", headerIdn_app);
        requestHeaders.put("x-request-id", headerX_request_id);
        requestHeaders.put("bankId", bankCode);
        requestHeaders.put("benNumber", benNumber);

        CurlResponse resAPI = curlService.executeCurlCommand(url, method, requestHeaders, null, null);

        return this.resAPI_42_112_38_103(resAPI);
    }

    @Override
    public ResponseEntity<?> getFTListInfo(String headerAuthorization, String headerIdn_app, String headerX_request_id, String accountNumber, String referenceNumber) {
        String url = VPB_URI + "/payments/v1/transfer/ftlist";
        String method = "GET";

        Map<String, Object> requestHeaders = new HashMap<>();
        requestHeaders.put("Content-Type", "application/json");
        requestHeaders.put("Authorization", "Bearer "+headerAuthorization);
        requestHeaders.put("IDN-App", headerIdn_app);
        requestHeaders.put("x-request-id", headerX_request_id);
        requestHeaders.put("accountNumber", accountNumber);
        requestHeaders.put("referenceNumber", referenceNumber);

        CurlResponse resAPI = curlService.executeCurlCommand(url, method, requestHeaders, null, null);

        return this.resAPI_42_112_38_103(resAPI);
    }

    @Override
    public ResponseEntity<?> getPartnerAccountInfo(String headerAuthorization, String headerIdn_app, String headerX_request_id, String accountNumber) {
        String url = VPB_URI + "/payments/v1/partner-account/info";
        String method = "GET";

        Map<String, Object> requestHeaders = new HashMap<>();
        requestHeaders.put("Content-Type", "application/json");
        requestHeaders.put("Authorization", "Bearer "+headerAuthorization);
        requestHeaders.put("IDN-App", headerIdn_app);
        requestHeaders.put("x-request-id", headerX_request_id);
        requestHeaders.put("accountNumber", accountNumber);

        CurlResponse resAPI = curlService.executeCurlCommand(url, method, requestHeaders, null, null);

        return this.resAPI_42_112_38_103(resAPI);
    }

    @Override
    public ResponseEntity<?> internalTransfer(String headerAuthorization, String headerIdn_app, String headerX_request_id, TranferDTO data) {

        String dataType = "data-raw";
        String url = VPB_URI + "/payments/v1/transfer/internal";
        String method = "POST";

        Map<String, Object> requestHeaders = new HashMap<>();
        requestHeaders.put("Content-Type", "application/json");
        requestHeaders.put("Authorization", "Bearer " + headerAuthorization);
        requestHeaders.put("x-request-id", headerX_request_id);
        requestHeaders.put("IDN-App", headerIdn_app);

        String plainText = data.getReferenceNumber() + data.getSourceNumber() + data.getTargetNumber() + data.getAmount();
        logger.info("plainText-InternalTransfer: \n" + plainText);
        String sign3gang = this.sign(plainText);
        logger.info("sign3gang-InternalTransfer: \n" + sign3gang);

        data.setSignature(sign3gang);

        Map<String, Object> bodies = new HashMap<>();
        bodies.put("referenceNumber", data.getReferenceNumber());
        bodies.put("service", data.getService());
        bodies.put("transactionType", data.getTransactionType());
        bodies.put("sourceNumber", data.getSourceNumber());
        bodies.put("targetNumber", data.getTargetNumber());
        bodies.put("amount", data.getAmount());
        bodies.put("currency", data.getCurrency());
        bodies.put("remark", data.getRemark());
        bodies.put("signature", data.getSignature());

        CurlResponse resAPI = curlService.executeCurlCommand(url, method, requestHeaders, bodies, dataType);

        return this.resAPI_42_112_38_103(resAPI);
    }

    @Override
    public ResponseEntity<?> externalTransfer(String headerAuthorization, String headerIdn_app, String headerX_request_id, ExternalTransferDTO data) {
        String dataType = "data-raw";
        String url = VPB_URI + "/payments/v1/transfer/external";
        String method = "POST";

        Map<String, Object> requestHeaders = new HashMap<>();
        requestHeaders.put("Content-Type", "application/json");
        requestHeaders.put("Authorization", "Bearer " + headerAuthorization);
        requestHeaders.put("x-request-id", headerX_request_id);
        requestHeaders.put("IDN-App", headerIdn_app);

        String plainText = data.getReferenceNumber() + data.getSourceNumber() + data.getTargetNumber() + data.getAmount();
        logger.info("plainText-ExternalTransfer: \n" + plainText);
        String sign3gang = this.sign(plainText);
        logger.info("sign3gang-ExternalTransfer: \n" + sign3gang);

        data.setSignature(sign3gang);

        Map<String, Object> bodies = new HashMap<>();
        bodies.put("referenceNumber", data.getReferenceNumber());
        bodies.put("service", data.getService());
        bodies.put("transactionType", data.getTransactionType());
        bodies.put("sourceNumber", data.getSourceNumber());
        bodies.put("targetNumber", data.getTargetNumber());
        bodies.put("amount", data.getAmount());
        bodies.put("currency", data.getCurrency());
        bodies.put("remark", data.getRemark());
        bodies.put("signature", data.getSignature());
        bodies.put("bankId", data.getBankCode());

        CurlResponse resAPI = curlService.executeCurlCommand(url, method, requestHeaders, bodies, dataType);

        return this.resAPI_42_112_38_103(resAPI);
    }

    public String sign (String plainText){
        URL url = Thread.currentThread().getContextClassLoader().getResource("certificate/certificate.cer");
        String CERT_FILE = url.getPath();
        SignatureNumber sn = new SignatureNumber();
        Base64 base64 = new Base64();
        String base64String = new String(base64.encode(plainText.getBytes()));
        System.out.println("\nbase64String: "+base64String);
        String signature = "";
        try {
            signature = sn.sign(base64String);
            System.out.println("\nsignature: "+signature);
//            boolean isOK = sn.verify(base64String, signature, CERT_FILE);
//            if (isOK) {
//                System.out.println("NOTIFY Verify OK!");
//            } else {
//                System.out.println("NOTIFY Verify FAIL!");
//            }
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return signature;
    }

    @Override
    @Transactional
    public VPBResDTO testConnectDatabase(String ft) {
        VPBankEntity vpBankEntity = null;
        try {
            vpBankEntity = vpBankRepository.selectNoti(ft);
        }
        catch(Exception e){
            logger.error("error => "+e.getMessage());
            return new VPBResDTO("400", ErrorCode.EXCEPTION_ERROR, e.getMessage(), "");
        }
        if (vpBankEntity != null && "00".equals(vpBankEntity.getStatus())){
            return new VPBResDTO("200", vpBankEntity.getStatus(), vpBankEntity.getDes(), "");
        }
        logger.error("error => No data");
        return new VPBResDTO("500", "-1", "No data", "");
    }

    public boolean checkSignature(VPBbankRequest request, String signature) {
        String plain = "456 5000 0100";
        List<KeysManageEntity> keysManage = keysManageRepository.getAllKeyVPBank();
        try {
            String priKeyStr = keysManage.get(0).getPrivate_key();
            PrivateKey priKey = cipherUtility.decodePrivateKey(priKeyStr);

            System.out.println("chuoi ma hoa: " + signature);

            // Decrypt plain as a text.
            String plainContent = cipherUtility.decrypt(signature, priKey);
            System.out.println("chuoi da duoc giai ma: " + plainContent);
            String x[] = plainContent.split(" ");
            if(
                    request.getMasterAccountNumber().equals(x[0]) &&
                            request.getAmount().equals(x[1]) &&
                            request.getTransactionId().equals(x[2])
            ) { return true; }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    private ResponseEntity<?> resAPI_42_112_38_103 (CurlResponse resAPI){
        if("json".equals(resAPI.getType())){
            try {
                Object jsonRes = new ObjectMapper().readValue(resAPI.getData(), Object.class);
                if(jsonRes != null) {
                    return new ResponseEntity<>(jsonRes, HttpStatus.OK);
                }
                else {
                    return new ResponseEntity<>("data is error", HttpStatus.BAD_REQUEST);
                }
            }
            catch(JsonProcessingException e) {
                e.printStackTrace();
                return new ResponseEntity<>("data is not converted", HttpStatus.BAD_REQUEST);
            }
        }
        else if ("xml".equals(resAPI.getType())){
            return new ResponseEntity<>(resAPI.getData(), HttpStatus.GATEWAY_TIMEOUT);
        }
        return null;
    }
}
