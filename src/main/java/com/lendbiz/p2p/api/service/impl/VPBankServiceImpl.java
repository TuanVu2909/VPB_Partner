package com.lendbiz.p2p.api.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lendbiz.p2p.api.configs.RSA.CipherUtility;
import com.lendbiz.p2p.api.constants.Constants;
import com.lendbiz.p2p.api.constants.ErrorCode;
import com.lendbiz.p2p.api.entity.KeysManageEntity;
import com.lendbiz.p2p.api.exception.BusinessException;
import com.lendbiz.p2p.api.helper.SignatureNumber;
import com.lendbiz.p2p.api.repository.KeysManageRepository;
import com.lendbiz.p2p.api.repository.VPBankRepository;
import com.lendbiz.p2p.api.request.VPBbankRequest;
import com.lendbiz.p2p.api.response.BaseResponse;
import com.lendbiz.p2p.api.response.VPBank.VPBResAPI;
import com.lendbiz.p2p.api.response.VPBank.VPBResDTO;
import com.lendbiz.p2p.api.service.VPBankService;
import com.lendbiz.p2p.api.service.base.CurlService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.URL;
import java.security.PrivateKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.CommandLineRunner;


@Service
@Log4j2
public class VPBankServiceImpl extends BaseResponse<VPBankService> implements VPBankService, CommandLineRunner{

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private VPBankRepository vpBankRepository;

    @Autowired
    private CipherUtility cipherUtility;

    @Autowired
    private KeysManageRepository keysManageRepository;

    @Autowired
    private CurlService curlService;

    @Override
    public VPBResDTO transFluctuations(VPBbankRequest request, String signature) {
        //boolean checkSuccess = this.checkSignature(request, signature);
        if(true){
            System.out.println("Xác thực thành công");
            try {
                vpBankRepository.insertVPBTrans(
                        request.getMasterAccountNumber(),
                        request.getVirtualAccountNumber(),
                        request.getVirtualName(),
                        request.getVirtualAlkey(),
                        request.getAmount(),
                        request.getBookingDate(),
                        request.getTransactionDate(),
                        request.getTransactionId(),
                        request.getRemark(),
                        signature
                );
            }
            catch(Exception e){
                System.out.println("error => "+e.getMessage());
                return new VPBResDTO("400", ErrorCode.EXCEPTION_ERROR, e.getMessage(), request.getTransactionId());
            }
        return new VPBResDTO("200", "", "", request.getTransactionId());
        }
        else {
            System.out.println("Xác thực không thành công");
            return new VPBResDTO("400", "", "Xác thực không thành công", request.getTransactionId());
        }
    }

    private boolean checkSignature(VPBbankRequest request, String signature) {
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

    @Override
    public ResponseEntity<?> getVPBToken() {
        Map<String, String> requestHeaders = new HashMap<>();
        String url = "https://postman-rest-api-learner.glitch.me/info";
        requestHeaders.put("Content-Type", "application/json");
        requestHeaders.put("Connection", "keep-alive");


        Map<String, Object> bodies = new HashMap<>();
        bodies.put("name", "le cao son");
        bodies.put("age", 30);

        JSONObject requestBody = new JSONObject(bodies);

        VPBResAPI resAPI = curlService.executeCurlCommand(url, "POST", requestHeaders, requestBody.toString());

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> res = null;

        try {
            res = objectMapper.readValue(resAPI.getData(), new TypeReference<Map<String, Object>>() {});
        }
        catch(IOException e) {
            e.printStackTrace();
        }

        if(resAPI.getCode() == 0 && res != null) {
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }
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
    public void run(String... args) throws Exception {

    }
}
