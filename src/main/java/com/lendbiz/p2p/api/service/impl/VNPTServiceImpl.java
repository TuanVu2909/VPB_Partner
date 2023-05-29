package com.lendbiz.p2p.api.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lendbiz.p2p.api.constants.Constants;
import com.lendbiz.p2p.api.constants.ErrorCode;
import com.lendbiz.p2p.api.entity.vnpt.BgEkycEntity;
import com.lendbiz.p2p.api.exception.BusinessException;
import com.lendbiz.p2p.api.producer.ProducerMessage;
import com.lendbiz.p2p.api.repository.BgEkycRepository;
import com.lendbiz.p2p.api.repository.CfMastRepository;
import com.lendbiz.p2p.api.response.BaseResponse;
import com.lendbiz.p2p.api.service.VNPTService;
import com.lendbiz.p2p.api.service.base.BaseService;
import com.lendbiz.p2p.api.utils.Utils;
import lombok.SneakyThrows;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class VNPTServiceImpl extends BaseResponse<VNPTService> implements VNPTService{

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private BgEkycRepository bgEkycRepository;

    @Qualifier("cfMastRepository")
    @Autowired
    CfMastRepository cfMastRepo;

    @Autowired
    ProducerMessage producerMessage;

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
            if(responseEntity.getStatusCodeValue() == 200){
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

        //this.saveFileKafka(imgFrontId, mobile, 0);
        //this.saveFileKafka(imgBackId, mobile, 1);

        // Phí 800 vnd
        BgEkycEntity bgEkyc = this.bgEkycRepository.findByMobileSms(mobile);
        String currentDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        // chua co data
        if(bgEkyc == null){
            bgEkyc = new BgEkycEntity();
            bgEkyc.setMobileSms(mobile);
            bgEkyc.setEkycDate(currentDate);
            // Lưu số lần call API vertifyIdentity vào DB
            bgEkyc.setApiOrc(bgEkyc.getApiOrc() + 1); // default ApiOrc = 0
        }
        // có data
        else {
            // ngày hiện tại là ngày eKYC -> check số lần ekyc
            if(currentDate.equals(bgEkyc.getEkycDate())){
                if(bgEkyc.getApiOrc()>=3) throw new BusinessException(ErrorCode.EKYC_LIMIT, ErrorCode.EKYC_LIMIT_DESC);
                // Lưu số lần call API vertifyIdentity vào DB
                bgEkyc.setApiOrc(bgEkyc.getApiOrc() + 1); // default ApiOrc = 0
            }
            // ngày hiện tại khac ngày eKYC -> reset lại ngày, so lần call API
            else{
                bgEkyc.setEkycDate(currentDate);
                bgEkyc.setApiCompare(1);
                bgEkyc.setApiOrc(1);
            }
        }
        this.bgEkycRepository.save(bgEkyc);

        HttpHeaders headers = new HttpHeaders();
        Map<String, Object> bodies = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", Constants.VNPT_TOKEN);
        headers.set("Token-id", Constants.VNPT_ID);
        headers.set("Token-key", Constants.VNPT_KEY);

        bodies.put("img_front", hashImgFrontId);
        bodies.put("client_session", mobile);

        // check giay tờ thật giả
        HttpEntity<?> requestLiveCard = new HttpEntity(bodies, headers);
        JsonNode rootLiveCard = null;
        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    Constants.VNPT_DOMAIN + "/ai/v1/card/liveness",
                    HttpMethod.POST,
                    requestLiveCard,
                    String.class,
                    (Object) null);

            rootLiveCard = mapper.readTree(responseEntity.getBody());
        }
        catch (Exception e){
            throw new BusinessException(Constants.FAIL, Constants.MESSAGE_FAIL);
        }

        if(rootLiveCard == null){throw new BusinessException(ErrorCode.VNPT_CARD_LIVENESS, ErrorCode.VNPT_CARD_LIVENESS_DESC);}

        if(!"success".equals(rootLiveCard.get("object").get("liveness").asText())){
            throw new BusinessException(ErrorCode.VNPT_CARD_FAKE, ErrorCode.VNPT_CARD_FAKE_DESC);
        }

        // xủ lý tiếp (bóc tách orc)
        bodies.put("img_back", hashImgBackId);
        bodies.put("type", -1); // -1: CMND, CCCD cũ/ mới
        bodies.put("token", Constants.VNPT_ID);

        HttpEntity<?> requestOrc = new HttpEntity(bodies, headers);
        JsonNode rootOrc = null;
        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    Constants.VNPT_DOMAIN + "/ai/v1/ocr/id",
                    HttpMethod.POST,
                    requestOrc,
                    String.class,
                    (Object) null);

            rootOrc = mapper.readTree(responseEntity.getBody());
        }
        catch (Exception e){
            rootOrc = BaseService.stringToRoot(e.getMessage());
            if(rootOrc.get("statusCode").asInt() == 400){
                if(rootOrc.get("message").asText().equals("IDG-00010003")){
                    throw new BusinessException(ErrorCode.VNPT_INVALID_INPUT, ErrorCode.VNPT_INVALID_INPUT_DESC);
                }
                if(rootOrc.get("message").asText().equals("IDG-00010202")){
                    throw new BusinessException(ErrorCode.VNPT_NO_IMAGE, ErrorCode.VNPT_NO_IMAGE_DESC);
                }
                if(rootOrc.get("message").asText().equals("IDG-00010445")){
                    throw new BusinessException(ErrorCode.VNPT_INVALID_INPUT, ErrorCode.VNPT_INVALID_INPUT_DESC);
                }
                if(rootOrc.get("message").asText().equals("IDG-00010448")){
                    throw new BusinessException(ErrorCode.VNPT_ID_FRONT_INVALID, ErrorCode.VNPT_ID_FRONT_INVALID_DESC);
                }
                if(rootOrc.get("message").asText().equals("IDG-00010449")){
                    throw new BusinessException(ErrorCode.VNPT_ID_BACK_INVALID, ErrorCode.VNPT_ID_BACK_INVALID_DESC);
                }
                else {
                    throw new BusinessException(ErrorCode.UNKNOWN_ERROR, rootOrc.get("errors").asText());
                }
            }
        }
        // field common
        // idFontType,idBackType -> loai giay to:  2 -> HC, 3 -> CMQD, 4 -> BLX
        // idFontType,idBackType -> loai giay to:  0 -> CM9, 1 -> CCCD,CM12, 5 -> CCGC
        String idFontType = rootOrc.get("object").get("type_id").asText();
        String idBackType = rootOrc.get("object").get("back_type_id").asText();

        if(rootOrc.get("statusCode").asInt() == 200){
//            if(root.get("object").has("dupplication_warning")){
//                if("true".equals(root.get("object").get("dupplication_warning").asText())){
//                    throw new BusinessException(ErrorCode.VNPT_INVALID_INPUT, ErrorCode.VNPT_INVALID_INPUT_DESC);
//                }
//            }
            if(idFontType.equals("2") || idFontType.equals("3") || idFontType.equals("4") ||
                    idBackType.equals("2") || idBackType.equals("3") || idBackType.equals("4")){
                throw new BusinessException(ErrorCode.VNPT_INVALID_TYPE, ErrorCode.VNPT_INVALID_TYPE_DESC);
            }
            if(!idFontType.equals(idBackType)){
                throw new BusinessException(ErrorCode.VNPT_INVALID_ID_TYPE, ErrorCode.VNPT_INVALID_ID_TYPE_DESC);
            }
            if(!(rootOrc.get("object").get("checking_result_front").get("corner_cut_result").asText().equals("0"))){
                throw new BusinessException(ErrorCode.VNPT_ID_FRONT_COVER, ErrorCode.VNPT_ID_FRONT_COVER_DESC);
            }
            if(!(rootOrc.get("object").get("checking_result_back").get("corner_cut_result").asText().equals("0"))){
                throw new BusinessException(ErrorCode.VNPT_ID_BACK_COVER, ErrorCode.VNPT_ID_BACK_COVER_DESC);
            }
            if(rootOrc.get("object").get("tampering").get("is_legal").asText().equals("no") ||
                    rootOrc.get("object").get("id_fake_warning").asText().equals("yes")){
                throw new BusinessException(ErrorCode.VNPT_INVALID_INPUT_DESC, ErrorCode.VNPT_INVALID_INPUT_DESC);
            }
            if(rootOrc.get("object").get("expire_warning").asText().equals("yes") ||
                    rootOrc.get("object").get("back_expire_warning").asText().equals("yes")){
                throw new BusinessException(ErrorCode.VNPT_ID_EXPIRED, ErrorCode.VNPT_ID_EXPIRED_DESC);
            }
            if(rootOrc.get("object").get("corner_warning").asText().equals("yes") ||
                    rootOrc.get("object").get("back_corner_warning").asText().equals("yes")){
                throw new BusinessException(ErrorCode.VNPT_ID_NO_CORNER, ErrorCode.VNPT_ID_NO_CORNER_DESC);
            }
            // CCGC
            if(idFontType.equals("5") && idBackType.equals("5")){
                if(rootOrc.get("object").get("match_front_back").get("match_sex").asText().equals("no") ||
                        rootOrc.get("object").get("match_front_back").get("match_bod").asText().equals("no") ||
                        rootOrc.get("object").get("match_front_back").get("match_id").asText().equals("no") ||
                        rootOrc.get("object").get("match_front_back").get("match_valid_date").asText().equals("no") ||
                        rootOrc.get("object").get("match_front_back").get("match_name").asText().equals("no")
                ){ throw new BusinessException(ErrorCode.VNPT_ID_NO_MATCH, ErrorCode.VNPT_ID_NO_MATCH_DESC); }
            }

            if(cfMastRepo.findByIdCode(rootOrc.get("object").get("id").asText(), mobile).size() > 0){
                throw new BusinessException(ErrorCode.USER_EXISTED, ErrorCode.USER_EXISTED_DESCRIPTION);
            }

            Date nDate;
            try {
                nDate = Utils.convertStringToDateTechcombank(rootOrc.get("object").get("birth_day").asText());
            } catch (Exception e) {
                nDate = Utils.convertStringToDate3Gang(rootOrc.get("object").get("birth_day").asText());
            }

            LocalDate startDate = nDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            Period yearOld = Period.between(startDate, LocalDate.now());

            if (yearOld.getYears() < 18) {
                throw new BusinessException(ErrorCode.FAILED_IDENTITY, ErrorCode.FAILED_OLD_INVALID);
            }

            // Đến đây là vertifyIdentity không có lỗi -> insert vào DB
            // Xử lý những field có thể bị null
            String convNationality = "-".equals(rootOrc.get("object").get("nationality").asText()) ? "N/A" : rootOrc.get("object").get("nationality").asText();
            String baseStr = rootOrc.get("object").get("gender").asText();
            String convGender = "";
            // 001:Nam, 002:Nữ, 003:Khác
            if("Nam".equals(baseStr)){
                convGender = "001";
            }else if("Nữ".equals(baseStr)){
                convGender = "002";
            } else {
                convGender = "001";
            }
            String convValidDate = "-".equals(rootOrc.get("object").get("valid_date").asText()) ? "31/12/9999" : rootOrc.get("object").get("valid_date").asText();

            bgEkyc.setIdNo(rootOrc.get("object").get("id").asText());
            bgEkyc.setTypeId(rootOrc.get("object").get("type_id").asInt());
            bgEkyc.setCardType(rootOrc.get("object").get("card_type").asText());
            bgEkyc.setName(rootOrc.get("object").get("name").asText());
            bgEkyc.setBirthDay(rootOrc.get("object").get("birth_day").asText());
            bgEkyc.setNationality(convNationality);
            bgEkyc.setGender(convGender);
            bgEkyc.setOriginLocation(rootOrc.get("object").get("origin_location").asText().replaceAll("\n", ", "));
            bgEkyc.setRecentLocation(rootOrc.get("object").get("recent_location").asText().replaceAll("\n", ", "));
            bgEkyc.setIssueDate(rootOrc.get("object").get("issue_date").asText());
            bgEkyc.setValidDate(convValidDate);
            bgEkyc.setIssuePlace(rootOrc.get("object").get("issue_place").asText().replaceAll("\n", ", "));
            bgEkyc.setOrcSuccess("YES");

            this.bgEkycRepository.save(bgEkyc);
        }
        return response(toResult(Constants.SUCCESS, Constants.MESSAGE_SUCCESS, bgEkyc));
    }

    @SneakyThrows
    @Override
    public ResponseEntity<?> vertifySelfie(MultipartFile imgFrontId, MultipartFile imgSelfie, String mobile) {
        String hashImgFrontId = this.uploadImage(imgFrontId, "imgFrontId", "imgFrontId");
        String hashImgSelfie = this.uploadImage(imgSelfie, "imgSelfie", "imgSelfie");

        //this.saveFileKafka(imgSelfie, mobile, 2);

        // Phí 800 vnd
        BgEkycEntity bgEkyc = this.bgEkycRepository.findByMobileSms(mobile);
        String currentDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        // chua co data
        if(bgEkyc == null) {
            bgEkyc = new BgEkycEntity();
            bgEkyc.setMobileSms(mobile);
            bgEkyc.setEkycDate(currentDate);
            // Lưu số lần call API vertifyIdentity vào DB
            bgEkyc.setApiCompare(bgEkyc.getApiCompare() + 1); // default ApiCompare = 0
        }
        // có data
        else {
            // ngày hiện tại là ngày eKYC -> check số lần ekyc
            if(currentDate.equals(bgEkyc.getEkycDate())) {
                if(bgEkyc.getApiCompare()>=3) throw new BusinessException(ErrorCode.EKYC_LIMIT, ErrorCode.EKYC_LIMIT_DESC);
                // Lưu số lần call API vertifyIdentity vào DB
                bgEkyc.setApiCompare(bgEkyc.getApiCompare() + 1); // default ApiCompare = 0
            }
            // ngày hiện tại khac ngày eKYC(currentDate>lastDate) -> reset lại ngày, so lần call API
            else {
                bgEkyc.setEkycDate(currentDate);
                bgEkyc.setApiCompare(1);
                bgEkyc.setApiOrc(1);
            }
        }
        this.bgEkycRepository.save(bgEkyc);

        HttpHeaders headers = new HttpHeaders();
        Map<String, Object> bodies = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", Constants.VNPT_TOKEN);
        headers.set("Token-id", Constants.VNPT_ID);
        headers.set("Token-key", Constants.VNPT_KEY);

        bodies.put("img_face", hashImgSelfie);
        bodies.put("client_session", mobile);
        bodies.put("token", Constants.VNPT_ID);

        // check khuon mat that gia
        HttpEntity<?> requestFF = new HttpEntity(bodies, headers);
        JsonNode rootFF = null;

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    Constants.VNPT_DOMAIN + "/ai/v1/face/liveness",
                    HttpMethod.POST,
                    requestFF,
                    String.class,
                    (Object) null);
            rootFF = mapper.readTree(responseEntity.getBody());
        }
        catch (Exception e) {

        }

        if(rootFF == null) { throw new BusinessException(ErrorCode.VNPT_FACE_LIVENESS, ErrorCode.VNPT_FACE_LIVENESS_DESC); }

        if(!"success".equals(rootFF.get("object").get("liveness").asText())) {
            throw new BusinessException(ErrorCode.VNPT_FACE_FAKE, ErrorCode.VNPT_FACE_FAKE_DESC);
        }

        bodies.put("img_front", hashImgFrontId);

        HttpEntity<?> requestCompare = new HttpEntity(bodies, headers);
        JsonNode rootCompare;
        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    Constants.VNPT_DOMAIN + "/ai/v1/face/compare",
                    HttpMethod.POST,
                    requestCompare,
                    String.class,
                    (Object) null);
            rootCompare = mapper.readTree(responseEntity.getBody());
        }
        catch (Exception e) {
            rootCompare = BaseService.stringToRoot(e.getMessage());
            if(rootCompare.get("statusCode").asInt() == 400){
                throw new BusinessException(ErrorCode.VNPT_FACE_NO_FIND, ErrorCode.VNPT_FACE_NO_FIND_DESC);
            }
            else {
                throw new BusinessException(ErrorCode.UNKNOWN_ERROR, rootCompare.get("errors").asText());
            }
        }

        if(rootCompare.get("statusCode").asInt() == 200) {
            if(rootCompare.get("object").get("multiple_faces").asBoolean() == true) {
                throw new BusinessException(ErrorCode.VNPT_MULTIPLE_FACES, ErrorCode.VNPT_MULTIPLE_FACES_DESC);
            }
            if(rootCompare.get("object").get("msg").asText().equals("NOMATCH")) {
                throw new BusinessException(ErrorCode.VNPT_FACE_NO_MATCH, ErrorCode.VNPT_FACE_NO_MATCH_DESC);
            }
        }

        bgEkyc.setCompareSuccess("YES");
        this.bgEkycRepository.save(bgEkyc);
        return response(toResult(Constants.SUCCESS, Constants.MESSAGE_SUCCESS, rootCompare.get("object")));
    }

    public void saveFileKafka(MultipartFile file, String mobile, int type) {
        try {
            byte[] fileContent = Base64.encodeBase64(file.getBytes());
            String data = new String(fileContent, "UTF-8");

            Map<String, Object> map = new HashMap<>();
            map.put("mobile", mobile);
            map.put("file", data);
            map.put("fileName", type + "_" + file.getOriginalFilename());

            JSONObject jsonObjectLogs = new JSONObject(map);
            producerMessage.sendSaveIdCard(jsonObjectLogs.toString());

        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }

}
