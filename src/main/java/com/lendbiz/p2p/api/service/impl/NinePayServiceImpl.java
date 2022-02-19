/*
 * Apache License, Version 2.0
 *
 * Copyright (c) 2021 TU HOANG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lendbiz.p2p.api.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lendbiz.p2p.api.constants.Constants;
import com.lendbiz.p2p.api.constants.ErrorCode;
import com.lendbiz.p2p.api.entity.Card9PayDetails;
import com.lendbiz.p2p.api.entity.Card9PayEntity;
import com.lendbiz.p2p.api.entity.Card9PayService;
import com.lendbiz.p2p.api.entity.Input9Pay;
import com.lendbiz.p2p.api.exception.BusinessException;
import com.lendbiz.p2p.api.request.Create9PayRequest;
import com.lendbiz.p2p.api.response.BaseResponse;
import com.lendbiz.p2p.api.response.Card9PayResponse;
import com.lendbiz.p2p.api.response.NinePayResponse;
import com.lendbiz.p2p.api.response.ProductResponse;
import com.lendbiz.p2p.api.service.NinePayService;

import com.lendbiz.p2p.api.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/***********************************************************************
 *
 * @package：com.lendbiz.p2p.api.service.impl，@class-name：UserServiceImpl.java
 *
 * @copyright Copyright: 2021-2022
 * @creator Hoang Thanh Tu <br/>
 * @create-time Apr 9, 2021 10:43:45 AM
 *
 ***********************************************************************/
@Service("ninepay")
public class NinePayServiceImpl extends BaseResponse<NinePayService> implements NinePayService {

    private static final String MERCHANT_KEY = "QMVnnQ";

    private static final String MERCHANT_SECRET_KEY = "lwQqFFFEnoLEoeXztZfASxfExvNJEliz1En";

    private static final String END_POINT = "https://sand-payment.9pay.vn";

    private static final String BASE_URL = "https://uat.lendbiz.vn/mobile/lendbiz/9pay/success";

    private static final String PARTNER_KEY = "L6WPKJXN4Y";

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private Card9PayServiceImpl service9;

    @Override
    public ResponseEntity<?> create9Payment(Create9PayRequest request) throws UnsupportedEncodingException {
        String time = String.valueOf(System.currentTimeMillis());
        String invoiceNo = randomInvoiceNo();
        String amount = request.getAmount();

        Map<String, String> map = new TreeMap<String, String>();
        map.put("merchantKey", MERCHANT_KEY);
        map.put("time", time);
        map.put("invoice_no", invoiceNo);
        map.put("amount", amount);
        map.put("description", request.getDescription());
        map.put("return_url", BASE_URL);
        map.put("back_url", BASE_URL);
        if (request.isCardToken()) {
            map.put("card_token", request.getCardTokenValue());
        }
        map.put("save_token", "1");

        String queryHttp = http_build_query(map);
        String mesage = buildMessage(queryHttp, time);
        String signature = signature(mesage, MERCHANT_SECRET_KEY);
        byte[] byteArrray = jsonEncode(map).getBytes();
        String baseEncode = Base64.getEncoder().encodeToString(byteArrray);

        Map<String, String> queryBuild = new HashMap<String, String>();
        queryBuild.put("baseEncode", baseEncode);
        queryBuild.put("signature", signature);
        // This is direct link for 9Pay Gateway
        String redirectUrl = END_POINT + "/portal?" + http_build_query(queryBuild);
        // System.out.println(redirectUrl);

        return response(toResult(redirectUrl));
    }

    public static String jsonEncode(Map<String, String> array) throws UnsupportedEncodingException {
        String string = "";
        Iterator it = array.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry) it.next();
            String key = entry.getKey();
            String value = entry.getValue();
            if (!it.hasNext()) {
                string = string + '"' + key + '"' + ":" + '"' + value + '"';
            } else {
                string = string + '"' + key + '"' + ":" + '"' + value + '"' + ",";
            }
        }
        return "{" + string + "}";
    }

    public static String buildMessage(String queryHttp, String time) {
        StringBuffer sb = new StringBuffer();
        sb.append("POST");
        sb.append("\n");
        sb.append(END_POINT + "/payments/create");
        sb.append("\n");
        sb.append(time);
        sb.append("\n");
        sb.append(queryHttp);
        return sb.toString();
    }

    public static String signature(String queryHttp, String secretKey) {
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] hmac = sha256_HMAC.doFinal(queryHttp.getBytes());
            return Base64.getEncoder().encodeToString(hmac);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String randomInvoiceNo() {
        int leftLimit = 48;
        int rightLimit = 122;
        int targetStringLength = 10;
        Random random = new Random();
        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
        return generatedString;
    }

    public static String http_build_query(Map<String, String> array) {
        String reString = "";
        Iterator it = array.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry) it.next();
            String key = entry.getKey();
            String value = entry.getValue();
            reString += key + "=" + value + "&";
        }
        reString = reString.substring(0, reString.length() - 1);
        reString = java.net.URLEncoder.encode(reString);
        reString = reString.replace("%3D", "=").replace("%26", "&");
        return reString;
    }

    @Override
    public ResponseEntity<?> decode9Payment(String encodeString) throws UnsupportedEncodingException {
        String byteArrray = encodeString;

        byte[] decodedBytes = Base64.getDecoder().decode(byteArrray);
        String decodedString = new String(decodedBytes);

        return response(toResult(decodedString));
    }

    @Override
    public ResponseEntity<?> getCardProducts(String serviceId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        HashMap<String, String> fieldMap = new HashMap<>();

        fieldMap.put("type", "1");
        fieldMap.put("service_id", serviceId);
        String[] rq9Pay = Utils.getSignatureNinePay(fieldMap);
        map.add("request_id", rq9Pay[0]);
        map.add("partner_id", PARTNER_KEY);
        map.add("service_id", serviceId);
        map.add("signature", rq9Pay[1]);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        ResponseEntity<String> responseEntityStr;
        try {
            responseEntityStr = restTemplate.postForEntity(Constants.NINE_PAY_PRODUCTS, request, String.class);
        } catch (Exception e) {
            throw new BusinessException(Constants.FAIL, e.getMessage());
        }
        // mapping response
        if (responseEntityStr.getStatusCodeValue() == 200) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root;
            logger.info("[Call api get otp] response : {}", responseEntityStr.getBody());
            try {
                root = mapper.readTree(responseEntityStr.getBody());
                if (root.get("success").toString().equals("false")) {
                    throw new BusinessException(root.get("error").get("code").toString(), root.get("error").get("message").toString());

                }
                ProductResponse[] myObjects = mapper.readValue(root.get("data").get("products").toString(), ProductResponse[].class);
                if (myObjects.length == 0) {
                    throw new BusinessException(ErrorCode.NO_DATA, ErrorCode.NO_DATA_DESCRIPTION);
                }
                return response(toResult(myObjects));
            } catch (JsonProcessingException e) {
                throw new BusinessException(ErrorCode.FAILED_TO_JSON, ErrorCode.FAILED_TO_JSON_DESCRIPTION);
            }

        } else {
            throw new BusinessException(ErrorCode.FAILED_TO_EXECUTE, ErrorCode.FAILED_TO_EXECUTE_DESCRIPTION);
        }
    }

    @Override
    public ResponseEntity<?> buyCard(Input9Pay input9Pay) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        HashMap<String, String> fieldMap = new HashMap<>();

        fieldMap.put("type", "2");
        fieldMap.put("product_id", input9Pay.getProductId());
        fieldMap.put("qua", input9Pay.getQuantity());

        String[] rq9Pay = Utils.getSignatureNinePay(fieldMap);
        map.add("request_id", rq9Pay[0]);
        map.add("partner_id", PARTNER_KEY);
        map.add("product_id", input9Pay.getProductId());
        map.add("quantity", input9Pay.getQuantity());
        map.add("signature", rq9Pay[1]);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        ResponseEntity<String> responseEntityStr;
        try {
            responseEntityStr = restTemplate.postForEntity(Constants.NINE_PAY_CARD, request, String.class);
        } catch (Exception e) {
            throw new BusinessException(Constants.FAIL, e.getMessage());
        }
        // mapping response
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root;
//            String x = "W3sicHJpY2UiOjEwMDAwMCwiZGlzY291bnQiOjAsImFtb3VudCI6MTAwMDAwLCJjYXJkX3NlcmkiOiI2NTM4NjUxMTUxNDQ2MTcyIiwiY2FyZF9jb2RlIjoiSURHK0JoY1JaSHpZdWptNVVDRDFKVXNqM2pKV3dwOVIwRHN1YkxhS3dLOD0iLCJleHBpcmVkX2F0IjoiMjAyMi0wNS0wNiAxNjoyMjowMCJ9LHsicHJpY2UiOjEwMDAwMCwiZGlzY291bnQiOjAsImFtb3VudCI6MTAwMDAwLCJjYXJkX3NlcmkiOiIwNDg4MTQ3ODE5MzkxOTUwIiwiY2FyZF9jb2RlIjoib0s0OVBjb1AyMHJzRUxEMDAwem4zaFFQSzNyYkJLWlpCcGxtVGJyeU5JQT0iLCJleHBpcmVkX2F0IjoiMjAyMi0wNS0wNiAxNjoyMjowMCJ9LHsicHJpY2UiOjEwMDAwMCwiZGlzY291bnQiOjAsImFtb3VudCI6MTAwMDAwLCJjYXJkX3NlcmkiOiI2MDk2NjU5NjAwNjc1MDkyIiwiY2FyZF9jb2RlIjoiblIwZVRXWklLbXlTWCtvMlpSZXFINTlOcDJsWXJJNm9HVkJRMnNXQ1F5QT0iLCJleHBpcmVkX2F0IjoiMjAyMi0wNS0wNiAxNjoyMjowMCJ9XQ==";
        try {
            root = mapper.readTree(responseEntityStr.getBody());
            if (root.get("success").toString().equals("false")) {
                throw new BusinessException(root.get("error").get("code").toString(), root.get("error").get("message").toString());
            }
            Card9PayResponse response = mapper.readValue(root.get("data").toString(), Card9PayResponse.class);
            byte[] dc = Base64.getDecoder().decode(response.getCards());
            String data = new String(dc, "UTF-8");
            System.out.println(data);
            Card9PayEntity card9PayEntity = new Card9PayEntity();
            Card9PayDetails[] card9PayDetailsList = mapper.readValue(data, Card9PayDetails[].class);
            for (int i = 0; i < card9PayDetailsList.length; i++) {
                Card9PayDetails card9PayDetails = card9PayDetailsList[i];
                card9PayEntity.setStatus("Y");
                card9PayEntity.setCard_seri(card9PayDetails.getCard_seri());
                card9PayEntity.setPid("111");
                card9PayEntity.setPrice(card9PayDetails.getPrice());
                card9PayEntity.setTransId("111133");
                String id = String.valueOf((int) Math.floor(Math.random() * 100000));
                card9PayEntity.setId(id);
                card9PayEntity.setPayDate(Utils.getDate());
                card9PayEntity.setCif(input9Pay.getCif());
                card9PayEntity.setCard_code(card9PayDetails.getCard_code());
                service9.create(card9PayEntity);
                String codeDe = Utils.decrypt(card9PayDetails.getCard_code());
                card9PayDetailsList[i].setCard_code(codeDe);
            }

            return response(toResult(card9PayDetailsList));
        } catch (JsonProcessingException e) {
            throw new BusinessException(ErrorCode.FAILED_TO_JSON, ErrorCode.FAILED_TO_JSON_DESCRIPTION);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public ResponseEntity<?> findTransaction(String cId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        HashMap<String, String> fieldMap = new HashMap<>();

        fieldMap.put("type", "3");
        fieldMap.put("content_id", cId);


        String[] rq9Pay = Utils.getSignatureNinePay(fieldMap);
        map.add("request_id", rq9Pay[0]);
        map.add("partner_id", PARTNER_KEY);
        map.add("content_id", cId);
        map.add("content_type", "TRANSACTION");
        map.add("signature", rq9Pay[1]);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        ResponseEntity<String> responseEntityStr;
        try {
            responseEntityStr = restTemplate.postForEntity(Constants.NINE_PAY_INFO_TRANS, request, String.class);
        } catch (Exception e) {
            throw new BusinessException(Constants.FAIL, e.getMessage());
        }
        // mapping response
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root;
        try {
            root = mapper.readTree(responseEntityStr.getBody());
            if (root.get("success").toString().equals("false")) {
                throw new BusinessException(root.get("error").get("code").toString(), root.get("error").get("message").toString());
            }
            Card9PayResponse response = mapper.readValue(root.get("data").toString(), Card9PayResponse.class);
            byte[] dc = Base64.getDecoder().decode(response.getCards());
            String data = new String(dc, "UTF-8");
//                System.out.println(data);
//                Card9PayDetails[] card9PayDetailsList = mapper.readValue(data,Card9PayDetails[].class);
//                Card9PayDetails card9PayDetails = card9PayDetailsList[0];
            return response(toResult(response));
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new BusinessException(ErrorCode.FAILED_TO_JSON, ErrorCode.FAILED_TO_JSON_DESCRIPTION);
        }

    }

    @Override
    public ResponseEntity<?> balance() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        HashMap<String, String> fieldMap = new HashMap<>();
        String date = Utils.getDate();
        fieldMap.put("type", "4");
        fieldMap.put("rq_time", date);


        String[] rq9Pay = Utils.getSignatureNinePay(fieldMap);
        map.add("request_id", rq9Pay[0]);
        map.add("partner_id", PARTNER_KEY);
        map.add("request_time", date);
        map.add("signature", rq9Pay[1]);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        ResponseEntity<String> responseEntityStr;
        try {
            responseEntityStr = restTemplate.postForEntity(Constants.NINE_PAY_BALANCE, request, String.class);
        } catch (Exception e) {
            throw new BusinessException(Constants.FAIL, e.getMessage());
        }
        // mapping response
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root;
        try {
            root = mapper.readTree(responseEntityStr.getBody());
            if (root.get("success").toString().equals("false")) {
                throw new BusinessException(root.get("error").get("code").toString(), root.get("error").get("message").toString());
            }
            Card9PayResponse response = mapper.readValue(root.get("data").toString(), Card9PayResponse.class);
            byte[] dc = Base64.getDecoder().decode(response.getCards());
            String data = new String(dc, "UTF-8");
//                System.out.println(data);
//                Card9PayDetails[] card9PayDetailsList = mapper.readValue(data,Card9PayDetails[].class);
//                Card9PayDetails card9PayDetails = card9PayDetailsList[0];
            return response(toResult(response));
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new BusinessException(ErrorCode.FAILED_TO_JSON, ErrorCode.FAILED_TO_JSON_DESCRIPTION);
        }
    }

}
