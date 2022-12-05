package com.lendbiz.p2p.api.service.impl;

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
    public ResponseEntity<?> vertifyIdentity(MultipartFile imgFrontId, MultipartFile imgBackId, String mobile) {
        String hashImgFrontId = this.uploadImage(imgFrontId, "imgFrontId", "imgFrontId");
        String hashImgBackId = this.uploadImage(imgBackId, "imgBackId", "imgBackId");
        if(hashImgFrontId == null || hashImgBackId == null){
            return response(toResult(Constants.FAIL, Constants.MESSAGE_FAIL, "Không lấy được mã hash của hình ảnh"));
        }
        logger.info("============= Start eKYC vertifyIdentity (VNPT) =============");

        // Phí 800 vnd
        HttpHeaders headers = new HttpHeaders();
        Map<String, Object> bodies = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", Constants.VNPT_TOKEN);
        headers.set("Token-id", mobile);
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
            if(root.get("statusCode").asInt() == 200 &&
                    (root.get("object").get("match_front_back").get("match_sex").asText().equals("no") ||
                     root.get("object").get("match_front_back").get("match_bod").asText().equals("no") ||
                     root.get("object").get("match_front_back").get("match_id").asText().equals("no") ||
                     root.get("object").get("match_front_back").get("match_valid_date").asText().equals("no") ||
                     root.get("object").get("match_front_back").get("match_name").asText().equals("no")
                    )
            ) {
                return response(toResult(Constants.FAIL, Constants.MESSAGE_FAIL, "Giấy tờ có mặt trước và mặt sau không khớp"));
            }
            if(root.get("statusCode").asInt() == 200 && root.get("object").get("tampering").get("is_legal").asText().equals("no")) {
                return response(toResult(Constants.FAIL, Constants.MESSAGE_FAIL, "Giấy tờ là giả mạo"));
            }
        }
        catch (Exception e) {
            root = BaseService.stringToRoot(e.getMessage());
            if(root.get("statusCode").asInt() == 400 && root.get("message").asText().equals("IDG-00010003")) {
                return response(toResult(Constants.FAIL, Constants.MESSAGE_FAIL, "Chất lượng ảnh đầu vào không đạt chuẩn (ảnh quá mờ hoặc bị tẩy xóa)"));
            }
            if(root.get("statusCode").asInt() == 400 && root.get("message").asText().equals("IDG-00010202")) {
                return response(toResult(Constants.FAIL, Constants.MESSAGE_FAIL, "Dữ liệu đầu vào không phải là ảnh"));
            }
            if(root.get("statusCode").asInt() == 400 && root.get("message").asText().equals("IDG-00010445")) {
                return response(toResult(Constants.FAIL, Constants.MESSAGE_FAIL, "Loại giấy tờ không hợp lệ"));
            }
            else {
                return response(toResult(Constants.FAIL, Constants.MESSAGE_FAIL, "Lỗi hệ thống"));
            }
        }
        // TODO INSERT TO TABLE
        return response(toResult(Constants.SUCCESS, Constants.MESSAGE_SUCCESS, root.get("object").get("result")));
    }

    @SneakyThrows
    @Override
    public ResponseEntity<?> vertifySelfie(MultipartFile imgFrontId, MultipartFile imgSelfie, String mobile) {
        String hashImgFrontId = this.uploadImage(imgFrontId, "imgFrontId", "imgFrontId");
        String hashImgSelfie = this.uploadImage(imgSelfie, "imgSelfie", "imgSelfie");
        if(hashImgFrontId == null || hashImgSelfie == null){
            return response(toResult(Constants.FAIL, Constants.MESSAGE_FAIL, "Không lấy được mã hash của hình ảnh"));
        }

        logger.info("============= Start eKYC vertifySelfie (VNPT) =============");
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

            if(root.get("statusCode").asInt() == 200 && root.get("object").get("multiple_faces").asText().equals("true")){
                return response(toResult(Constants.FAIL, Constants.MESSAGE_FAIL, "Ảnh có nhiều hơn 1 khuôn mặt"));
            }

            if(root.get("statusCode").asInt() == 200 && root.get("object").get("msg").asText().equals("NOMATCH")){
                return response(toResult(Constants.FAIL, Constants.MESSAGE_FAIL, "Khuôn mặt không khớp"));
            }
        }
        catch (Exception e) {
            root = BaseService.stringToRoot(e.getMessage());
            if(root.get("statusCode").asInt() == 400) {
                return response(toResult(Constants.FAIL, Constants.MESSAGE_FAIL, "Không tìm thấy khuôn mặt"));
            }
            else {
                return response(toResult(Constants.FAIL, Constants.MESSAGE_FAIL, "Lỗi hệ thống"));
            }
        }
        // TODO INSERT TO TABLE
        return response(toResult(Constants.SUCCESS, Constants.MESSAGE_SUCCESS, root.get("object").get("result")));
    }
}
