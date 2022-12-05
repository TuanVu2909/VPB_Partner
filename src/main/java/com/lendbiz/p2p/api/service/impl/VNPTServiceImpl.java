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

@Service
public class VNPTServiceImpl extends BaseResponse<VNPTService> implements VNPTService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
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

    @SneakyThrows
    @Override
    public JsonNode compareImage(String hashImgFrontId, String hashImgSelfie, String mobile) {
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
        return root;
    }

    @Override
    public ResponseEntity<?> extractOCR(String hashImgFrontId, String hashImgBackId, String hashImgSelfie) {
        return null;
    }



}
