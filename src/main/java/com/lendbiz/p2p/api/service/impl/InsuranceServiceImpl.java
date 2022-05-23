package com.lendbiz.p2p.api.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lendbiz.p2p.api.constants.Constants;
import com.lendbiz.p2p.api.response.BaseResponse;
import com.lendbiz.p2p.api.service.InsuranceService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class InsuranceServiceImpl extends BaseResponse<InsuranceService> implements InsuranceService {
    static String BV_TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJsZW5kYml6QGJhb3ZpZXQuY29tLnZuIiwiYXV0aCI6IlBFUk1fQUdSRUVNRU5UX0NSRUFURSxQRVJNX0FHUkVFTUVOVF9ERUxFVEUsUEVSTV9BR1JFRU1FTlRfRURJVCxQRVJNX0FHUkVFTUVOVF9WSUVXLFBFUk1fQ0FSVF9DUkVBVEUsUEVSTV9DQVJUX0RFTEVURSxQRVJNX0NBUlRfRURJVCxQRVJNX0NBUlRfVklFVyxQRVJNX0NPTlRBQ1RfQ1JFQVRFLFBFUk1fQ09OVEFDVF9ERUxFVEUsUEVSTV9DT05UQUNUX0VESVQsUEVSTV9DT05UQUNUX1ZJRVcsUEVSTV9QQVlfQ0hVWUVOX1RIVSxQRVJNX1BBWV9LSEFDSF9IQU5HX1RULFBFUk1fUEFZX1RIQU5IVE9BTl9TQVUsUEVSTV9QQVlfVk5QQVksUEVSTV9QUk9EVUNUX0JWR19DUkVBVEUsUEVSTV9QUk9EVUNUX0JWR19ERUxFVEUsUEVSTV9QUk9EVUNUX0JWR19FRElULFBFUk1fUFJPRFVDVF9CVkdfVklFVyxQRVJNX1JFUE9SVF9DT01NSVNTSU9OX1ZJRVcsUEVSTV9SRVBPUlRfSU5DT01FX1ZJRVcsUEVSTV9SRVBPUlRfTFYxLFBFUk1fUkVQT1JUX1RSQU5TRkVSX1ZJRVcsUk9MRV9BR0VOQ1ksUk9MRV9SRVBPUlRfQUdFTkNZIiwiZXhwIjoxNjU1MzY4ODgzfQ.glHTIR7Y-Juz8Ewy9XePYi_TvG0mmJ4WboHzoVuC-URev55m34dzkXwPzJcP_SWH1fAI10WUwe1s-OpkuaevJQ";
    static String BV_PREMIUM_URI = "https://agency-api-dev1.baoviet.com.vn/api/agency/product/bvg/premium";
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ResponseEntity<?> premium() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + BV_TOKEN);
        JSONObject jsonObject = new JSONObject();
        String x = "string";
        jsonObject.put("checkReuse", x);
        jsonObject.put("checkTtskNdbh", x);
        jsonObject.put("chuongTrinhBh", "1");
        jsonObject.put("insuranceTarget", "New");
        jsonObject.put("ngoaitruChk", "0");


        jsonObject.put("ngoaitruPhi", 0);
        jsonObject.put("nguoidbhNgaysinh", "01/01/1992");
        jsonObject.put("nhakhoaChk", "0");
        jsonObject.put("nhakhoaPhi", 0);
        jsonObject.put("oldPolicyNumber", "string");


        jsonObject.put("qlChinhPhi", 0);
        jsonObject.put("serial", "string");
        jsonObject.put("smcnChk", "0");
        jsonObject.put("smcnPhi", 0);
        jsonObject.put("smcnSotienbh", 0);


        jsonObject.put("thaisanChk", "0");
        jsonObject.put("thaisanPhi", 0);
        jsonObject.put("thoihanbhTu", "05/07/2022");
        jsonObject.put("tncnChk", "0");
        jsonObject.put("tncnPhi", 0);

        jsonObject.put("tncnSotienbh", 0);
        jsonObject.put("tongPhiBH", 0);
        jsonObject.put("tuoi", 0);

        HttpEntity<String> request = new HttpEntity<String>(jsonObject.toString(), headers);
        ResponseEntity<String> responseEntityStr = restTemplate.postForEntity(BV_PREMIUM_URI, request, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root;
//String x = "W3sicHJpY2UiOjEwMDAwMCwiZGlzY291bnQiOjAsImFtb3VudCI6MTAwMDAwLCJYXJkX3NlcmkiOiI2NTM4NjUxMTUxNDQ2MTcyIiwiY2FyZF9jb2RlIjoiSURHK0JoY1JaSHpZdWptNVVDRDFKVXNqM2pKV3dwOVIwRHN1YkxhS3dLOD0iLCJleHBpcmVkX2F0IjoiMjAyMi0wNS0wNiAxNjoyMjowMCJ9LHsicHJpY2UiOjEwMDAwMCwiZGlzY291bnQiOjAsImFtb3VudCI6MTAwMDAwLCJjYXJkX3NlcmkiOiIwNDg4MTQ3ODE5MzkxOTUwIiwiY2FyZF9jb2RlIjoib0s0OVBjb1AyMHJzRUxEMDAwem4zaFFQSzNyYkJLWlpCcGxtVGJyeU5JQT0iLCJleHBpcmVkX2F0IjoiMjAyMi0wNS0wNiAxNjoyMjowMCJ9LHsicHJpY2UiOjEwMDAwMCwiZGlzY291bnQiOjAsImFtb3VudCI6MTAwMDAwLCJjYXJkX3NlcmkiOiI2MDk2NjU5NjAwNjc1MDkyIiwiY2FyZF9jb2RlIjoiblIwZVRXWklLbXlTWCtvMlpSZXFINTlOcDJsWXJJNm9HVkJRMnNXQ1F5QT0iLCJleHBpcmVkX2F0IjoiMjAyMi0wNS0wNiAxNjoyMjowMCJ9XQ==";
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







}
