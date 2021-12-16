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
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.lendbiz.p2p.api.request.Create9PayRequest;
import com.lendbiz.p2p.api.response.BaseResponse;
import com.lendbiz.p2p.api.service.NinePayService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

}
