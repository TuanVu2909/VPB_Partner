package com.lendbiz.p2p.api.service.impl;

import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lendbiz.p2p.api.constants.Constants;
import com.lendbiz.p2p.api.constants.ErrorCode;
import com.lendbiz.p2p.api.constants.JsonMapper;
import com.lendbiz.p2p.api.exception.BusinessException;
import com.lendbiz.p2p.api.producer.ProducerMessage;
import com.lendbiz.p2p.api.repository.InsurancePriceRepository;
import com.lendbiz.p2p.api.request.ConvertIdCardRequest;
import com.lendbiz.p2p.api.request.GetBankNameRequest;
import com.lendbiz.p2p.api.request.MbTransHistoryRequest;
import com.lendbiz.p2p.api.request.TransferMBRequest;
import com.lendbiz.p2p.api.response.BaseResponse;
import com.lendbiz.p2p.api.response.MBIDCardResponse;
import com.lendbiz.p2p.api.response.MbAccessToken;
import com.lendbiz.p2p.api.service.MbbankTransferService;
import com.lendbiz.p2p.api.utils.MbUltils;

import lombok.extern.log4j.Log4j2;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@Log4j2
public class MbbankTransferServiceImpl extends BaseResponse<MbbankTransferService> implements MbbankTransferService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    InsurancePriceRepository insurancePriceRepository;

    @Autowired
    private ProducerMessage producerMessage;

    @Override
    public ResponseEntity<?> getToken() {
        String plainCreds = Constants.MBBANK_BASIC_AUTHEN_STRING;
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);

        logger.info("---------Start Get mb token Access---------------");
        final String uri = Constants.MB_GET_TOKEN_URL;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", "Basic " + base64Creds);

        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("grant_type", "client_credentials");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(multiValueMap, headers);

        System.out.println(JsonMapper.writeValueAsString(request));

        ResponseEntity<String> responseEntityStr = restTemplate.postForEntity(uri, request, String.class);

        // mapping response
        if (responseEntityStr.getStatusCodeValue() == 200) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root;
            logger.info("Start get token access: {}", responseEntityStr.getBody());
            try {
                root = mapper.readTree(responseEntityStr.getBody());
                MbAccessToken result = new MbAccessToken();
                result = mapper.readValue(root.toString(), MbAccessToken.class);
                return response(toResult(result));
            } catch (JsonProcessingException e) {
                throw new BusinessException(ErrorCode.FAILED_TO_JSON, ErrorCode.FAILED_TO_JSON_DESCRIPTION);
            }

        } else {
            throw new BusinessException(ErrorCode.FAILED_TO_EXECUTE, ErrorCode.FAILED_TO_EXECUTE_DESCRIPTION);
        }

    }

    public String token() {
        String plainCreds = Constants.MBBANK_BASIC_AUTHEN_STRING;
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);

        logger.info("---------Start Get mb token Access---------------");
        final String uri = Constants.MB_GET_TOKEN_URL;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", "Basic " + base64Creds);

        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("grant_type", "client_credentials");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(multiValueMap, headers);

        System.out.println(JsonMapper.writeValueAsString(request));

        ResponseEntity<String> responseEntityStr = restTemplate.postForEntity(uri, request, String.class);

        // mapping response
        if (responseEntityStr.getStatusCodeValue() == 200) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root;
            logger.info("Start get token access: {}", responseEntityStr.getBody());
            try {
                root = mapper.readTree(responseEntityStr.getBody());
                MbAccessToken result = new MbAccessToken();
                result = mapper.readValue(root.toString(), MbAccessToken.class);
                return result.getAccess_token();
            } catch (JsonProcessingException e) {
                throw new BusinessException(ErrorCode.FAILED_TO_JSON, ErrorCode.FAILED_TO_JSON_DESCRIPTION);
            }

        } else {
            throw new BusinessException(ErrorCode.FAILED_TO_EXECUTE, ErrorCode.FAILED_TO_EXECUTE_DESCRIPTION);
        }

    }

    @Override
    public ResponseEntity<?> getBankName(GetBankNameRequest getBankNameRequest) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token());
        headers.set("clientMessageId", UUID.randomUUID().toString());
        headers.set("transactionId", UUID.randomUUID().toString());

        JSONObject jsonObject = new JSONObject(getBankNameRequest);

        HttpEntity<String> request = new HttpEntity<String>(jsonObject.toString(),
                headers);
        ResponseEntity<String> responseEntityStr = restTemplate.postForEntity(Constants.MB_GET_NAME_URL, request,
                String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root;
        try {
            root = mapper.readTree(responseEntityStr.getBody());
            return response(toResult(root));
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String convertIdCard(ConvertIdCardRequest convertRequest) {

        JSONObject jsonObject = new JSONObject(convertRequest);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // headers.set("Authorization", "Bearer " + token());
        headers.set("user", Constants.LENDBIZ);
        try {
            headers.set("hmac", MbUltils.hmacGenerator(jsonObject.toString()));
        } catch (Exception e1) {
            throw new BusinessException(Constants.ERROR, "Fail generate HMAC!");
        }

        System.out.println(jsonObject.toString());

        HttpEntity<String> request = new HttpEntity<String>(jsonObject.toString(),
                headers);
        ResponseEntity<String> responseEntityStr = restTemplate.postForEntity(Constants.MB_CONVERT_ID_CARD, request,
                String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root;
        try {
            MBIDCardResponse result = new MBIDCardResponse();
            root = mapper.readTree(responseEntityStr.getBody());
            result = mapper.readValue(root.toString(), MBIDCardResponse.class);
            return result.getCardID();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseEntity<?> transfer(String requestId, TransferMBRequest transferRequest) {
        JSONObject jsonObjectLogs = new JSONObject();
        MbTransHistoryRequest requestLogs = new MbTransHistoryRequest();
        String originalCreditNumber = transferRequest.getCreditResourceNumber();
        try {
            String transactionId = "QQCH" + RandomStringUtils.randomNumeric(8);

            // transferRequest.setBankCode("970406");
            // transferRequest.setDebitName("CONG TY CO PHAN LENDBIZ VIETNAM");
            // transferRequest.setDebitResourceNumber("0001604822947");
            // transferRequest.setDebitType("ACCOUNT");
            transferRequest.setObject("dn");
            transferRequest.setRemark("LENDBIZ-CHUYENTIEN-" + transactionId);
            transferRequest.setServiceType("CHI_HO");
            // transferRequest.setTransferAmount("1500000");
            // transferRequest.setTransferFee("0");
            transferRequest.setCustomerType("DOANH_NGHIEP");
            transferRequest.setCustomerLevel(1);

            if (transferRequest.getCreditType().equalsIgnoreCase("CARD")) {
                String idCard = convertIdCard(
                        new ConvertIdCardRequest(transactionId, transferRequest.getCreditResourceNumber()));

                System.out.println("idCard: " + idCard);
                transferRequest.setCreditResourceNumber(idCard);
            }

            String signatureKey = transferRequest.getDebitResourceNumber() + transferRequest.getDebitName()
                    + transferRequest.getCreditResourceNumber() + transferRequest.getCreditName()
                    + transferRequest.getTransferAmount();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + token());
            headers.set("clientMessageId", transactionId);
            headers.set("signature", MbUltils.generateSha256Rsa(signatureKey));
            headers.set("transactionId", transactionId);

            JSONObject jsonObject = new JSONObject(transferRequest);
            System.out.println(jsonObject.toString());
            HttpEntity<String> request = new HttpEntity<String>(jsonObject.toString(),
                    headers);

            String tranType = "6001";
            if (transferRequest.getDebitType().equalsIgnoreCase("ACCOUNT")
                    && transferRequest.getCreditType().equalsIgnoreCase("ACCOUNT")
                    && transferRequest.getTransferType().equalsIgnoreCase("INHOUSE")) {
                tranType = "6001";
            } else if (transferRequest.getDebitType().equalsIgnoreCase("ACCOUNT")
                    && transferRequest.getCreditType().equalsIgnoreCase("CARD")
                    && transferRequest.getTransferType().equalsIgnoreCase("INHOUSE")) {
                tranType = "6002";

            } else {
                tranType = "6003";
            }
            // Save log
            requestLogs.setAmount(transferRequest.getTransferAmount());
            requestLogs.setTranType(tranType);
            requestLogs.setRequestId(requestId);
            requestLogs.setBankTransId(transactionId);
            requestLogs.setFt("");
            requestLogs.setTranDate("setTranDate");
            requestLogs.setCurrency("VND");
            requestLogs.setTranDetail("LENDBIZ-CHUYENTIEN-" + transactionId);
            requestLogs.setStatus(0);
            requestLogs.setDebitSourceName(transferRequest.getDebitName());
            requestLogs.setDebitSourceNumber(transferRequest.getDebitResourceNumber());
            requestLogs.setCreditSourceName(transferRequest.getCreditName());
            requestLogs.setCreditSourceNumber(transferRequest.getCreditResourceNumber());
            requestLogs.setCreditFastName("creditFastName");
            requestLogs.setCreditFastBankName("creditFastName");
            requestLogs.setCreditIdCard(originalCreditNumber);
            requestLogs.setChannel("channel");
            requestLogs.setChannelName("channelName");
            requestLogs.setReconcileStatus(0);
            requestLogs.setAddInfo("addInfo");
            requestLogs.setRequestType(0);
            jsonObjectLogs = new JSONObject(requestLogs);
            // Send request log to kafka server
            producerMessage.sendMessage(jsonObjectLogs.toString());
            // End log

            ResponseEntity<String> responseEntityStr = restTemplate.postForEntity(Constants.MB_TRANSFER, request,
                    String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root;
            root = mapper.readTree(responseEntityStr.getBody());
            // Save success log
            requestLogs.setStatus(0);
            requestLogs.setRequestType(1);
            requestLogs.setFt(root.get("data").get("ftNumber").textValue());
            jsonObjectLogs = new JSONObject(requestLogs);
            producerMessage.sendMessage(jsonObjectLogs.toString());
            return response(toResult(root));
        } catch (Exception e) {
            logger.info(e.getMessage());
            requestLogs.setRequestType(1);
            requestLogs.setStatus(99);
            jsonObjectLogs = new JSONObject(requestLogs);
            producerMessage.sendMessage(jsonObjectLogs.toString());
            throw new BusinessException(ErrorCode.UNKNOWN_ERROR, e.getMessage());
        }
    }

    // @Override
    // public ResponseEntity<?> premium(Premium premium) {
    // HttpHeaders headers = new HttpHeaders();
    // headers.setContentType(MediaType.APPLICATION_JSON);
    // headers.set("Authorization", "Bearer " + BV_TOKEN);
    // JSONObject jsonObject = new JSONObject(premium);

    // HttpEntity<String> request = new HttpEntity<String>(jsonObject.toString(),
    // headers);
    // ResponseEntity<String> responseEntityStr =
    // restTemplate.postForEntity(BV_PREMIUM_URI, request, String.class);
    // ObjectMapper mapper = new ObjectMapper();
    // JsonNode root;
    // try {
    // root = mapper.readTree(responseEntityStr.getBody());
    // return response(toResult(root));
    // } catch (JsonMappingException e) {
    // e.printStackTrace();
    // } catch (JsonProcessingException e) {
    // e.printStackTrace();
    // }
    // return null;
    // }

    // @Override
    // public ResponseEntity<?> createPolicy_Partner(CreatePolicyPartnerRq rq) {

    // try {
    // HttpHeaders headers = new HttpHeaders();
    // headers.setContentType(MediaType.APPLICATION_JSON);
    // headers.set("Authorization", "Bearer " + BV_TOKEN);
    // JSONObject jsonObject = new JSONObject(rq);
    // System.out.println(jsonObject);
    // HttpEntity<String> request = new HttpEntity<String>(jsonObject.toString(),
    // headers);
    // ResponseEntity<String> responseEntityStr =
    // restTemplate.postForEntity(BV_PARTNER_URI, request,
    // String.class);
    // ObjectMapper mapper = new ObjectMapper();
    // JsonNode root;
    // root = mapper.readTree(responseEntityStr.getBody());
    // return response(toResult(root));
    // } catch (JSONException err) {
    // throw new BusinessException("111", "Parse JSON fail");
    // } catch (JsonMappingException e) {
    // e.printStackTrace();
    // } catch (JsonProcessingException e) {
    // e.printStackTrace();
    // }
    // return null;
    // }

    // @Override
    // public ResponseEntity<?> getByGycbhNumber(String gycbhNumber) {
    // try {
    // ;
    // HttpHeaders headers = new HttpHeaders();
    // headers.setContentType(MediaType.APPLICATION_JSON);
    // headers.set("Authorization", "Bearer " + BV_TOKEN);
    // JSONObject jsonObject = new JSONObject();
    // jsonObject.put("gycbhNumber", gycbhNumber);

    // HttpEntity<String> request = new HttpEntity<String>(jsonObject.toString(),
    // headers);
    // ResponseEntity<String> responseEntityStr =
    // restTemplate.postForEntity(BV_ODER_INFO_URI, request,
    // String.class);
    // ObjectMapper mapper = new ObjectMapper();
    // JsonNode root;
    // root = mapper.readTree(responseEntityStr.getBody());
    // return response(toResult(root));
    // } catch (JSONException err) {
    // throw new BusinessException("111", "Parse JSON fail");
    // } catch (JsonMappingException e) {
    // e.printStackTrace();
    // } catch (JsonProcessingException e) {
    // e.printStackTrace();
    // }
    // return null;
    // }

    // @Override
    // public ResponseEntity<?> getInsurancePackagePrice(String pkgId, String age) {
    // ArrayList<InsurancePrice> list = (ArrayList<InsurancePrice>)
    // insurancePriceRepository
    // .getInsurancePackagePrice(pkgId, age);
    // if (list.size() == 0)
    // throw new BusinessException(Constants.FAIL, ErrorCode.NO_DATA_DESCRIPTION);
    // return response(toResult(list));
    // }

    // @Override
    // public ResponseEntity<?> downloadFileOder(String gid, String type) {
    // try {
    // ;
    // HttpHeaders headers = new HttpHeaders();
    // headers.setContentType(MediaType.APPLICATION_JSON);
    // headers.set("Authorization", "Bearer " + BV_TOKEN);
    // JSONObject jsonObject = new JSONObject();
    // jsonObject.put("gycbhNumber", gid);
    // jsonObject.put("type", type);
    // HttpEntity<String> request = new HttpEntity<String>(jsonObject.toString(),
    // headers);
    // ResponseEntity<String> responseEntityStr =
    // restTemplate.postForEntity(BV_ODER_DOWNLOAD_URI, request,
    // String.class);
    // ObjectMapper mapper = new ObjectMapper();
    // JsonNode root;
    // root = mapper.readTree(responseEntityStr.getBody());
    // return response(toResult(root));
    // } catch (JSONException err) {
    // throw new BusinessException("111", "Parse JSON fail");
    // } catch (JsonMappingException e) {
    // e.printStackTrace();
    // } catch (JsonProcessingException e) {
    // e.printStackTrace();
    // }
    // return null;
    // }

    // @Override
    // public ResponseEntity<?> getInsuranceAdditionPrice(String pkgId, String age)
    // {
    // List<InsurancePrice> list =
    // insurancePriceRepository.getInsurancePackagePrice(pkgId, age);
    // if (list.size() == 0)
    // throw new BusinessException(Constants.FAIL, ErrorCode.NO_DATA_DESCRIPTION);
    // return response(toResult(list));
    // }

}
