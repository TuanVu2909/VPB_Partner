package com.lendbiz.p2p.api.service.impl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lendbiz.p2p.api.constants.Constants;
import com.lendbiz.p2p.api.exception.BusinessException;
import com.lendbiz.p2p.api.response.BaseResponse;
import com.lendbiz.p2p.api.service.VNPTService;
import com.lendbiz.p2p.api.service.base.BaseService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

        String hashImgFrontId = vnptService.uploadImage(imgFrontId, "imgFrontId", "imgFrontId");
                String hashImgBackId = vnptService.uploadImage(imgBackId, "imgBackId", "imgBackId");
                String hashImgSelfie = vnptService.uploadImage(imgSelfie, "imgSelfie", "imgSelfie");

                logger.info("============= Start eKYC (VNPT) =============");

                if(hashImgFrontId == null && hashImgBackId == null && hashImgSelfie == null){
                return response(toResult(Constants.FAIL, Constants.MESSAGE_FAIL, "Không lấy được mã hash của hình ảnh"));
                }

                JsonNode resFaceCompare = vnptService.compareImage(hashImgFrontId, hashImgSelfie, mobile);

                if(resFaceCompare.get("statusCode").asInt() == 400) {
                return response(toResult(Constants.FAIL, Constants.MESSAGE_FAIL, "Không tìm thấy khuôn mặt"));
                }

                if(resFaceCompare.get("statusCode").asInt() == 200 && resFaceCompare.get("object").get("multiple_faces").asText().equals("true")){
                return response(toResult(Constants.FAIL, Constants.MESSAGE_FAIL, "Ảnh có nhiều hơn 1 khuôn mặt"));
                }

                if(resFaceCompare.get("statusCode").asInt() == 200 && resFaceCompare.get("object").get("msg").asText().equals("NOMATCH")){
                return response(toResult(Constants.FAIL, Constants.MESSAGE_FAIL, "Khuôn mặt không khớp"));
                }

                return response(toResult(Constants.SUCCESS, Constants.MESSAGE_SUCCESS, resFaceCompare.get("object").get("result")));

    String uploadImage(MultipartFile image, String title, String description);
            JsonNode compareImage(String hashImgFrontId, String hashImgSelfie, String mobile);
            JsonNode extractOCR(String hashImgFrontId, String hashImgBackId) throws JsonProcessingException;

@Service
public class VNPTServiceImpl extends BaseResponse<VNPTService> implements VNPTService {

    @Autowired
    private RestTemplate restTemplate;

    public String uploadImage(MultipartFile image, String title, String description) {
        // Phí 0 vnd
        HttpHeaders headers = new HttpHeaders();
        MultiValueMap<String, Object> bodies = new LinkedMultiValueMap<>();
        ObjectMapper mapper = new ObjectMapper();
        ByteArrayResource contentImageFile = BaseService.convertFile(image);

        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("Authorization", Constants.VNPT_TOKEN);
        headers.set("Token-id", Constants.VNPT_ID);
        headers.set("Token-key", Constants.VNPT_KEY);

        bodies.add("file", contentImageFile);
        bodies.add("title", title);
        bodies.add("description", description);

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity(bodies, headers);

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    Constants.VNPT_DOMAIN + "/file-service/v1/addFile",
                    HttpMethod.POST,
                    request,
                    String.class,
                    (Object) null);
            if(responseEntity.getStatusCodeValue() == 200) {
                JsonNode root = mapper.readTree(responseEntity.getBody());
                return root.get("object").get("hash").textValue();
            }
        }
        catch (Exception e) {
            throw new BusinessException("111", e.getMessage());
        }
        return null;
    }

    @Override
    public ResponseEntity<?> vertifyIdentity(MultipartFile imgFrontId, MultipartFile imgBackId) throws JsonProcessingException {
        // Phí 800 vnd
        String hashImgFrontId = this.uploadImage(imgFrontId, "imgFrontId", "imgFrontId");
        String hashImgBackId = this.uploadImage(imgBackId, "imgBackId", "imgBackId");

        HttpHeaders headers = new HttpHeaders();
        Map<String, Object> bodies = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", Constants.VNPT_TOKEN);
        headers.set("Token-id", Constants.VNPT_ID);
        headers.set("Token-key", Constants.VNPT_KEY);

        bodies.put("img_front", hashImgFrontId);
        bodies.put("img_back", hashImgBackId);
        bodies.put("client_session", Constants.VNPT_ID);
        bodies.put("type", -1); // -1: CMND, CCCD cũ/ mới
        bodies.put("token", Constants.VNPT_ID);

        HttpEntity<?> request = new HttpEntity(bodies, headers);
        JsonNode root;
        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    Constants.VNPT_DOMAIN + "/ai/v1/ocr/id",
                    HttpMethod.POST,
                    request,
                    String.class,
                    (Object) null);
            root = mapper.readTree(responseEntity.getBody());
        }
        catch (Exception e) {
            root = BaseService.stringToRoot(e.getMessage());
        }
        //return root;
        return null;
    }

    @Override
    public ResponseEntity<?> vertifySelfie(MultipartFile imgFrontId, MultipartFile imgSelfie) {
        String hashImgFrontId = this.uploadImage(imgFrontId, "imgFrontId", "imgFrontId");
        String hashImgSelfie = this.uploadImage(imgSelfie, "imgSelfie", "imgSelfie");

        // Phí 800 vnd
        HttpHeaders headers = new HttpHeaders();
        Map<String, Object> bodies = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", Constants.VNPT_TOKEN);
        headers.set("Token-id", Constants.VNPT_ID);
        headers.set("Token-key", Constants.VNPT_KEY);

        bodies.put("img_front", hashImgFrontId);
        bodies.put("img_face", hashImgSelfie);
        bodies.put("client_session", mobile);
        bodies.put("token", Constants.VNPT_ID);

        HttpEntity<?> request = new HttpEntity(bodies, headers);
        JsonNode root;
        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    Constants.VNPT_DOMAIN + "/ai/v1/face/compare",
                    HttpMethod.POST,
                    request,
                    String.class,
                    (Object) null);
            root = mapper.readTree(responseEntity.getBody());
        }
        catch (Exception e) {
            root = BaseService.stringToRoot(e.getMessage());
        }
        //return root;
        return null;
    }
}
