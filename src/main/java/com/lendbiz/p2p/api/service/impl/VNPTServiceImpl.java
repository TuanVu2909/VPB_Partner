package com.lendbiz.p2p.api.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lendbiz.p2p.api.constants.Constants;
import com.lendbiz.p2p.api.entity.vnpt.BgEkycEntity;
import com.lendbiz.p2p.api.exception.BusinessException;
import com.lendbiz.p2p.api.repository.BgEkycRepository;
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

    @Autowired
    private BgEkycRepository bgEkycRepository;

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
    public ResponseEntity<?> vertifyIdentity(MultipartFile imgFrontId, MultipartFile imgBackId, String mobile) {
        String hashImgFrontId = this.uploadImage(imgFrontId, "imgFrontId", "imgFrontId");
        String hashImgBackId = this.uploadImage(imgBackId, "imgBackId", "imgBackId");
        logger.info("============= Start eKYC vertifyIdentity (VNPT) =============");

        // Phí 800 vnd
        BgEkycEntity bgEkyc = this.bgEkycRepository.findByMobileSms(mobile);
        if(bgEkyc == null) bgEkyc = new BgEkycEntity();
        // Lưu số lần call API vertifyIdentity vào DB
        bgEkyc.setMobileSms(mobile);
        bgEkyc.setApiOrc(bgEkyc.getApiOrc() + 1); // default ApiOrc = 0
        this.bgEkycRepository.save(bgEkyc);

        HttpHeaders headers = new HttpHeaders();
        Map<String, Object> bodies = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", Constants.VNPT_TOKEN);
        headers.set("Token-id", Constants.VNPT_ID);
        headers.set("Token-key", Constants.VNPT_KEY);

        bodies.put("img_front", hashImgFrontId);
        bodies.put("img_back", hashImgBackId);
        bodies.put("client_session", mobile);
        bodies.put("type", -1); // -1: CMND, CCCD cũ/ mới
        bodies.put("token", Constants.VNPT_ID);

        HttpEntity<?> request = new HttpEntity(bodies, headers);
        JsonNode root = null;
        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    Constants.VNPT_DOMAIN + "/ai/v1/ocr/id",
                    HttpMethod.POST,
                    request,
                    String.class,
                    (Object) null);

            root = mapper.readTree(responseEntity.getBody());
            // field common
            // idFontType,idBackType -> loai giay to:  2 -> HC, 3 -> CMQD, 4 -> BLX
            // idFontType,idBackType -> loai giay to:  0 -> CM9, 1 -> CCCD,CM12, 5 -> CCGC
            String idFontType = root.get("object").get("type_id").asText();
            String idBackType = root.get("object").get("back_type_id").asText();

            if(root.get("statusCode").asInt() == 200) {
                String imgDupplicate = root.get("object").get("dupplication_warning").asBoolean() ? root.get("object").get("dupplication_warning").asText() : null;
                if("true".equals(imgDupplicate)) {
                    return response(toResult(Constants.FAIL, Constants.MESSAGE_FAIL, "Input không hợp lệ"));
                }

                if(idFontType.equals("2") || idFontType.equals("3") || idFontType.equals("4") ||
                   idBackType.equals("2") || idBackType.equals("3") || idBackType.equals("4")){
                    return response(toResult(Constants.FAIL, Constants.MESSAGE_FAIL, "Chỉ được phép sử dụng chứng minh nhân dân, Căn cước công dân, Căn cước gắn chíp"));
                }
                if(!idFontType.equals(idBackType)){
                    return response(toResult(Constants.FAIL, Constants.MESSAGE_FAIL, "Giấy tờ mặt trước và sau không cùng loại"));
                }
                if(!(root.get("object").get("checking_result_front").get("corner_cut_result").asText().equals("0"))){
                    return response(toResult(Constants.FAIL, Constants.MESSAGE_FAIL, "Giấy tờ mặt trước bị cắt hoặc bị che"));
                }
                if(!(root.get("object").get("checking_result_back").get("corner_cut_result").asText().equals("0"))){
                    return response(toResult(Constants.FAIL, Constants.MESSAGE_FAIL, "Giấy tờ mặt sau bị cắt hoặc bị che"));
                }
                if(root.get("object").get("tampering").get("is_legal").asText().equals("no") ||
                   root.get("object").get("id_fake_warning").asText().equals("yes")) {
                    return response(toResult(Constants.FAIL, Constants.MESSAGE_FAIL, "Giấy tờ là giả mạo"));
                }
                if(root.get("object").get("expire_warning").asText().equals("yes") ||
                   root.get("object").get("back_expire_warning").asText().equals("yes")){
                    return response(toResult(Constants.FAIL, Constants.MESSAGE_FAIL, "Giấy tờ hết hạn sử dụng"));
                }
                if(root.get("object").get("corner_warning").asText().equals("yes") ||
                   root.get("object").get("back_corner_warning").asText().equals("yes")){
                    return response(toResult(Constants.FAIL, Constants.MESSAGE_FAIL, "Giấy tờ bị mất góc"));
                }
                // CCGC
                if(idFontType.equals("5") && idBackType.equals("5")) {
                    if(root.get("object").get("match_front_back").get("match_sex").asText().equals("no") ||
                       root.get("object").get("match_front_back").get("match_bod").asText().equals("no") ||
                       root.get("object").get("match_front_back").get("match_id").asText().equals("no") ||
                       root.get("object").get("match_front_back").get("match_valid_date").asText().equals("no") ||
                       root.get("object").get("match_front_back").get("match_name").asText().equals("no")
                    ){ return response(toResult(Constants.FAIL, Constants.MESSAGE_FAIL, "Giấy tờ có mặt trước và mặt sau không khớp")); }
                }
                // còn lại là CM9, CM12, CCCD
            }
        }
        catch (Exception e) {
            try {
                root = BaseService.stringToRoot(e.getMessage());
            } catch (JsonProcessingException jsonProcessingException) {
                jsonProcessingException.printStackTrace();
            }
            if(root.get("statusCode").asInt() == 400){
                if(root.get("message").asText().equals("IDG-00010003")){
                    return response(toResult(Constants.FAIL, Constants.MESSAGE_FAIL, "Input không hợp lệ"));
                }
                if(root.get("message").asText().equals("IDG-00010202")){
                    return response(toResult(Constants.FAIL, Constants.MESSAGE_FAIL, "Dữ liệu đầu vào không phải là ảnh"));
                }
                if(root.get("message").asText().equals("IDG-00010445")){
                    return response(toResult(Constants.FAIL, Constants.MESSAGE_FAIL, "Loại giấy tờ không hợp lệ"));
                }
                if(root.get("message").asText().equals("IDG-00010448")){
                    return response(toResult(Constants.FAIL, Constants.MESSAGE_FAIL, "Mặt trước giấy tờ không hợp lệ"));
                }
                if(root.get("message").asText().equals("IDG-00010449")){
                    return response(toResult(Constants.FAIL, Constants.MESSAGE_FAIL, "Mặt sau giấy tờ không hợp lệ"));
                }
                else {
                    return response(toResult(Constants.FAIL, Constants.MESSAGE_FAIL, root.get("errors").asText()));
                }
            }
        }

        // Đến đây là vertifyIdentity không có lỗi -> insert vào DB
        bgEkyc.setIdNo(root.get("object").get("id").asText());
        bgEkyc.setTypeId(root.get("object").get("type_id").asInt());
        bgEkyc.setCardType(root.get("object").get("card_type").asText());
        bgEkyc.setName(root.get("object").get("name").asText());
        bgEkyc.setBirthDay(root.get("object").get("birth_day").asText());
        bgEkyc.setNationality(root.get("object").get("nationality").asText());
        bgEkyc.setGender(root.get("object").get("gender").asText());
        bgEkyc.setOriginLocation(root.get("object").get("origin_location").asText());
        bgEkyc.setRecentLocation(root.get("object").get("recent_location").asText());
        bgEkyc.setIssueDate(root.get("object").get("issue_date").asText());
        bgEkyc.setValidDate(root.get("object").get("valid_date").asText());
        bgEkyc.setIssuePlace(root.get("object").get("issue_place").asText());
        bgEkyc.setOrcSuccess("YES");

        this.bgEkycRepository.save(bgEkyc);
        return response(toResult(Constants.SUCCESS, Constants.MESSAGE_SUCCESS, bgEkyc));
    }

    @SneakyThrows
    @Override
    public ResponseEntity<?> vertifySelfie(MultipartFile imgFrontId, MultipartFile imgSelfie, String mobile) {
        String hashImgFrontId = this.uploadImage(imgFrontId, "imgFrontId", "imgFrontId");
        String hashImgSelfie = this.uploadImage(imgSelfie, "imgSelfie", "imgSelfie");

        logger.info("============= Start eKYC vertifySelfie (VNPT) =============");
        // Phí 800 vnd
        BgEkycEntity bgEkyc = this.bgEkycRepository.findByMobileSms(mobile);
        if(bgEkyc == null) bgEkyc = new BgEkycEntity();
        // Lưu số lần call API vertifyIdentity vào DB
        bgEkyc.setMobileSms(mobile);
        bgEkyc.setApiCompare(bgEkyc.getApiCompare() + 1); // default ApiCompare = 0
        this.bgEkycRepository.save(bgEkyc);

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

            if(root.get("statusCode").asInt() == 200){
                if(root.get("object").get("multiple_faces").asText().equals("true")){
                    return response(toResult(Constants.FAIL, Constants.MESSAGE_FAIL, "Ảnh có nhiều hơn 1 khuôn mặt"));
                }
                if(root.get("object").get("msg").asText().equals("NOMATCH")){
                    return response(toResult(Constants.FAIL, Constants.MESSAGE_FAIL, "Khuôn mặt không khớp"));
                }
            }
        }
        catch (Exception e) {
            root = BaseService.stringToRoot(e.getMessage());
            if(root.get("statusCode").asInt() == 400) {
                return response(toResult(Constants.FAIL, Constants.MESSAGE_FAIL, "Không tìm thấy khuôn mặt"));
            }
            else {
                return response(toResult(Constants.FAIL, Constants.MESSAGE_FAIL, root.get("errors").asText()));
            }
        }

        bgEkyc.setCompareSuccess("YES");
        this.bgEkycRepository.save(bgEkyc);
        return response(toResult(Constants.SUCCESS, Constants.MESSAGE_SUCCESS, root.get("object")));
    }
}
