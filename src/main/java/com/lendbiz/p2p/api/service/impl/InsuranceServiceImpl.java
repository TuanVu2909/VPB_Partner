package com.lendbiz.p2p.api.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lendbiz.p2p.api.constants.Constants;
import com.lendbiz.p2p.api.constants.ErrorCode;
import com.lendbiz.p2p.api.entity.InsurancePrice;
import com.lendbiz.p2p.api.entity.Premium;
import com.lendbiz.p2p.api.exception.BusinessException;
import com.lendbiz.p2p.api.repository.InsurancePriceRepository;
import com.lendbiz.p2p.api.request.CreatePolicyPartnerRq;
import com.lendbiz.p2p.api.response.BaseResponse;
import com.lendbiz.p2p.api.service.InsuranceService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class InsuranceServiceImpl extends BaseResponse<InsuranceService> implements InsuranceService {
    static String BV_TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJsZW5kYml6QGJhb3ZpZXQuY29tLnZuIiwiYXV0aCI6IlBFUk1fQUdSRUVNRU5UX0NSRUFURSxQRVJNX0FHUkVFTUVOVF9ERUxFVEUsUEVSTV9BR1JFRU1FTlRfRURJVCxQRVJNX0FHUkVFTUVOVF9WSUVXLFBFUk1fQ0FSVF9DUkVBVEUsUEVSTV9DQVJUX0RFTEVURSxQRVJNX0NBUlRfRURJVCxQRVJNX0NBUlRfVklFVyxQRVJNX0NPTlRBQ1RfQ1JFQVRFLFBFUk1fQ09OVEFDVF9ERUxFVEUsUEVSTV9DT05UQUNUX0VESVQsUEVSTV9DT05UQUNUX1ZJRVcsUEVSTV9QQVlfQ0hVWUVOX1RIVSxQRVJNX1BBWV9LSEFDSF9IQU5HX1RULFBFUk1fUEFZX1RIQU5IVE9BTl9TQVUsUEVSTV9QQVlfVk5QQVksUEVSTV9QUk9EVUNUX0JWR19DUkVBVEUsUEVSTV9QUk9EVUNUX0JWR19ERUxFVEUsUEVSTV9QUk9EVUNUX0JWR19FRElULFBFUk1fUFJPRFVDVF9CVkdfVklFVyxQRVJNX1JFUE9SVF9DT01NSVNTSU9OX1ZJRVcsUEVSTV9SRVBPUlRfSU5DT01FX1ZJRVcsUEVSTV9SRVBPUlRfTFYxLFBFUk1fUkVQT1JUX1RSQU5TRkVSX1ZJRVcsUk9MRV9BR0VOQ1ksUk9MRV9SRVBPUlRfQUdFTkNZIiwiZXhwIjoxNjU1MzY4ODgzfQ.glHTIR7Y-Juz8Ewy9XePYi_TvG0mmJ4WboHzoVuC-URev55m34dzkXwPzJcP_SWH1fAI10WUwe1s-OpkuaevJQ";
    static String BV_PREMIUM_URI = "https://agency-api-dev1.baoviet.com.vn/api/agency/product/bvg/premium";
    static String BV_PARTNER_URI = "https://agency-api-dev1.baoviet.com.vn/api/agency/product/bvg/createPolicy-Partner";
    @Autowired
    private RestTemplate restTemplate;
@Autowired
    InsurancePriceRepository insurancePriceRepository;
    @Override
    public ResponseEntity<?> premium(Premium premium) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + BV_TOKEN);
        JSONObject jsonObject = new JSONObject(premium);

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

    @Override
    public ResponseEntity<?> createPolicy_Partner(CreatePolicyPartnerRq rq) {

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + BV_TOKEN);
            JSONObject jsonObject = new JSONObject(rq);
            System.out.println(jsonObject);
            HttpEntity<String> request = new HttpEntity<String>(jsonObject.toString(), headers);
            ResponseEntity<String> responseEntityStr = restTemplate.postForEntity(BV_PARTNER_URI, request, String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root;
            root = mapper.readTree(responseEntityStr.getBody());
            return response(toResult(root));
        }catch (JSONException err){
            throw new BusinessException("111", "Parse JSON fail");
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseEntity<?> getInsurancePackagePrice(String pkgId, String age) {
        ArrayList<InsurancePrice> list = (ArrayList<InsurancePrice>) insurancePriceRepository.getInsurancePackagePrice(pkgId,age);
        if (list.size() == 0)
            throw new BusinessException(Constants.FAIL, ErrorCode.NO_DATA_DESCRIPTION);
        return response(toResult(list));
    }

    @Override
    public ResponseEntity<?> getInsuranceAdditionPrice(String pkgId, String age) {
        List<InsurancePrice> list = insurancePriceRepository.getInsurancePackagePrice(pkgId,age);
        if (list.size() == 0)
            throw new BusinessException(Constants.FAIL, ErrorCode.NO_DATA_DESCRIPTION);
        return response(toResult(list));
    }


}
