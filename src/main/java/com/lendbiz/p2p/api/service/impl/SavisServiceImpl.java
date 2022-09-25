package com.lendbiz.p2p.api.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lendbiz.p2p.api.constants.Constants;
import com.lendbiz.p2p.api.constants.ErrorCode;
import com.lendbiz.p2p.api.constants.JsonMapper;
import com.lendbiz.p2p.api.entity.Otp;
import com.lendbiz.p2p.api.model.SavisResponse.IdentityFromSavisResponse;
import com.lendbiz.p2p.api.producer.ProducerMessage;
import com.lendbiz.p2p.api.repository.CfMastRepository;
import com.lendbiz.p2p.api.exception.BusinessException;
import com.lendbiz.p2p.api.request.SavisVerifyOtpRequest;
import com.lendbiz.p2p.api.request.SignContractRequest;
import com.lendbiz.p2p.api.request.SignContractRequestV2;
import com.lendbiz.p2p.api.response.AccesToken;
import com.lendbiz.p2p.api.response.BaseResponse;
import com.lendbiz.p2p.api.response.InfoIdentity;
import com.lendbiz.p2p.api.response.OtpResponse;
import com.lendbiz.p2p.api.response.OtpResponseNew;
import com.lendbiz.p2p.api.response.SignPdfResponse;
import com.lendbiz.p2p.api.response.UserRegisterResponse;
import com.lendbiz.p2p.api.service.SavisService;
import com.lendbiz.p2p.api.utils.StringUtil;

import com.lendbiz.p2p.api.utils.Utils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.text.WordUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

@Service("SavisSerivce")
public class SavisServiceImpl extends BaseResponse<SavisService> implements SavisService {

    @Autowired
    private RestTemplate restTemplate;

    private final String isSelfie = "TRUE";

    @Autowired
    CfMastRepository cfMastRepo;
    @Autowired
    OtpServiceImpl otpService;

    @Autowired
    private ProducerMessage producerMessage;

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

    @Override
    public ResponseEntity<?> callPredict(MultipartFile file, InfoIdentity identity, int type, String mobile) {
        logger.info("---------Start call predict---------------");
        // JSONObject sObj;
        // Object temp;

        saveFileKafka(file, mobile, type);

        try {
            byte[] fileContent = Base64.encodeBase64(file.getBytes());
            String result = new String(fileContent);
            System.out.println(result);

            Map<String, Object> map = new HashMap<>();
            map.put("mobile", mobile);
            map.put("file", result);
            map.put("fileName", file.getOriginalFilename());

            JSONObject jsonObjectLogs = new JSONObject(map);
            producerMessage.sendSaveIdCard(jsonObjectLogs.toString());

        } catch (Exception e) {
            logger.info(e.getMessage());
        }

        final String uri = Constants.ESIGN_PREDICT;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set(Constants.ESIGN_API_KEY, Constants.ESIGN_VALUE_HEADER);

        MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();
        ByteArrayResource contentsAsResource = convertFile(file);
        multiValueMap.add("input", contentsAsResource);

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(multiValueMap, headers);
        ResponseEntity<String> responseEntityStr = restTemplate.postForEntity(uri, request, String.class);

        // mapping response
        if (responseEntityStr.getStatusCodeValue() == 200) {

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root;
            logger.info("[Call perdict] response : {}", responseEntityStr.getBody());

            try {
                root = mapper.readTree(responseEntityStr.getBody());

                // int sideType =
                // root.get("output").get(0).get("class_name").get("normalized").get("value").asInt();

                // String isReal = root.get("output").get(0).get("id") != null
                // ?
                // root.get("output").get(0).get("id").get("validate").get("id_check").asText()
                // : "BACKNOCHECK";

                // if (!isReal.equals("REAL") && !isReal.equals("BACKNOCHECK")) {
                // throw new BusinessException(ErrorCode.ID_FAKE,
                // ErrorCode.ID_FAKE_DESCRIPTION);
                // }

                // validateIdentityCard(type, sideType);

                // mặt trước
                identity.setFullName(root.get("output").get(0).get("ho_ten") != null
                        ? root.get("output").get(0).get("ho_ten").get("value").asText()
                        : null);
                identity.setAddress(root.get("output").get(0).get("ho_khau_thuong_tru") != null
                        ? root.get("output").get(0).get("ho_khau_thuong_tru").get("value").asText()
                        : null);
                identity.setBirthDay(root.get("output").get(0).get("ngay_sinh") != null
                        ? root.get("output").get(0).get("ngay_sinh").get("value").asText()
                        : null);
                identity.setSex(root.get("output").get(0).get("gioi_tinh") != null
                        ? root.get("output").get(0).get("gioi_tinh").get("value").asText()
                        : null);
                identity.setNation(root.get("output").get(0).get("quoc_tich") != null
                        ? root.get("output").get(0).get("quoc_tich").get("value").asText()
                        : null);
                identity.setExpirationDate(root.get("output").get(0).get("ngay_het_han") != null
                        ? root.get("output").get(0).get("ngay_het_han").get("value").asText()
                        : null);
                identity.setType(root.get("output").get(0).get("class_name") != null
                        ? root.get("output").get(0).get("class_name").get("normalized").get("value").asInt()
                        : 9999);
                identity.setIdNo(root.get("output").get(0).get("id") != null
                        ? root.get("output").get(0).get("id").get("value").asText()
                        : null);
                identity.setDateIssued(root.get("output").get(0).get("ngay_cap") != null
                        ? root.get("output").get(0).get("ngay_cap").get("normalized").get("value").asText()
                        : null);
                identity.setIssuedBy(root.get("output").get(0).get("noi_cap") != null
                        ? root.get("output").get(0).get("noi_cap").get("value").asText()
                        : null);

                // identity.setBirthday(root.get("output").get(0).get("ngay_sinh").get("normalized")
                // .get("value_unidecode").asText());
                // identity.setAddress(root.get("output").get(0).get("nguyen_quan").get("value_unidecode").asText());

            } catch (JsonProcessingException e) {
                throw new BusinessException(ErrorCode.FAILED_TO_JSON, ErrorCode.FAILED_TO_JSON_DESCRIPTION);
            }

        }

        if (type == 3) {
            if (identity.getType() != 1 && identity.getType() != 3 && identity.getType() != 5) {
                throw new BusinessException(ErrorCode.FAILED_IDENTITY, ErrorCode.FAILED_IDENTITY_DESCRIPTION);
            }

        } else {
            if (identity.getType() == 1 || identity.getType() == 3 || identity.getType() == 52
                    || identity.getType() == 53 || identity.getType() == 5) {
                throw new BusinessException(ErrorCode.FAILED_IDENTITY, ErrorCode.FAILED_IDENTITY_DESCRIPTION);
            }

            if (identity.getIdNo() == null) {
                throw new BusinessException(ErrorCode.FAILED_IDENTITY, ErrorCode.FAILED_IDENTITY_DESCRIPTION);
            } else {
                if (cfMastRepo.findByIdCode(identity.getIdNo(), mobile).size() > 0) {
                    throw new BusinessException(ErrorCode.USER_EXISTED, ErrorCode.USER_EXISTED_DESCRIPTION);
                }
            }
        }

        return response(toResult(identity));
    }

    private void validateIdentityCard(int inputType, int responseType) {

        if (inputType == 0 || inputType == 1) {
            if (responseType != inputType) {
                throw new BusinessException(ErrorCode.NOT_MATCHING_TYPE,
                        ErrorCode.NOT_MATCHING_CMND_DESCRIPTION);

            }

            if (inputType == 0) {
                if (responseType != inputType) {
                    throw new BusinessException(ErrorCode.NOT_MATCHING_SIDE,
                            ErrorCode.NOT_MATCHING_FRONT_CMND_DESCRIPTION);

                }
            } else {
                if (responseType != inputType) {
                    throw new BusinessException(ErrorCode.NOT_MATCHING_SIDE,
                            ErrorCode.NOT_MATCHING_BACK_CMND_DESCRIPTION);
                }
            }

        }

        if (inputType == 2 || inputType == 3) {
            if (responseType != inputType) {
                throw new BusinessException(ErrorCode.NOT_MATCHING_TYPE,
                        ErrorCode.NOT_MATCHING_CCCD_DESCRIPTION);

            }

            if (inputType == 2) {
                if (responseType != inputType) {
                    throw new BusinessException(ErrorCode.NOT_MATCHING_SIDE,
                            ErrorCode.NOT_MATCHING_FRONT_CCCD_DESCRIPTION);

                }
            } else {
                if (responseType != inputType) {
                    throw new BusinessException(ErrorCode.NOT_MATCHING_SIDE,
                            ErrorCode.NOT_MATCHING_BACK_CCCD_DESCRIPTION);
                }
            }

        }

        if (inputType == 5) {
            if (responseType != inputType) {
                throw new BusinessException(ErrorCode.NOT_MATCHING_TYPE,
                        ErrorCode.NOT_MATCHING_HC_DESCRIPTION);

            }

            if (inputType == 5) {
                if (responseType != inputType) {
                    throw new BusinessException(ErrorCode.NOT_MATCHING_SIDE,
                            ErrorCode.NOT_MATCHING_SIDE_HC_DESCRIPTION);

                }
            }

        }

        if (inputType == 6 || inputType == 52) {
            if (responseType != inputType) {
                throw new BusinessException(ErrorCode.NOT_MATCHING_TYPE,
                        ErrorCode.NOT_MATCHING_CMNDQD_DESCRIPTION);

            }

            if (inputType == 6) {
                if (responseType != inputType) {
                    throw new BusinessException(ErrorCode.NOT_MATCHING_SIDE,
                            ErrorCode.NOT_MATCHING_FRONT_CMNDQD_DESCRIPTION);

                }
            } else {
                if (responseType != inputType) {
                    throw new BusinessException(ErrorCode.NOT_MATCHING_SIDE,
                            ErrorCode.NOT_MATCHING_BACK_CMNDQD_DESCRIPTION);
                }
            }
        }

        if (inputType == 7 || inputType == 53) {
            if (responseType != inputType) {
                throw new BusinessException(ErrorCode.NOT_MATCHING_TYPE,
                        ErrorCode.NOT_MATCHING_CMCAND_DESCRIPTION);

            }

            if (inputType == 7) {
                if (responseType != inputType) {
                    throw new BusinessException(ErrorCode.NOT_MATCHING_SIDE,
                            ErrorCode.NOT_MATCHING_FRONT_CMCAND_DESCRIPTION);

                }
            } else {
                if (responseType != inputType) {
                    throw new BusinessException(ErrorCode.NOT_MATCHING_SIDE,
                            ErrorCode.NOT_MATCHING_BACK_CMCAND_DESCRIPTION);
                }
            }
        }

    }

    private ByteArrayResource convertFile(MultipartFile sourceImage) {
        ByteArrayResource resource = null;
        try {
            resource = new ByteArrayResource(sourceImage.getBytes()) {
                @Override
                public String getFilename() {
                    return sourceImage.getOriginalFilename();
                }
            };
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.FAILED_TO_FILE, ErrorCode.FAILED_TO_FILE_DESCRIPTION);
        }

        return resource;
    }

    @Override
    public ResponseEntity<?> callCheckSelfie(MultipartFile frontId, MultipartFile selfie, String mobile) {
        logger.info("---------Start call face_general---------------");

        saveFileKafka(selfie, mobile, 0);

        final String uri = Constants.ESIGN_FACE_GENERAL;
        boolean isMatching = false;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set(Constants.ESIGN_API_KEY, Constants.ESIGN_VALUE_HEADER);

        MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();
        ByteArrayResource imageCard = convertFile(frontId);
        multiValueMap.add("image_card", imageCard);
        ByteArrayResource imageGeneral = convertFile(selfie);
        multiValueMap.add("image_general", imageGeneral);
        multiValueMap.add("threshold", Constants.THRESHOLD);

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(multiValueMap, headers);

        ResponseEntity<String> responseEntityStr = restTemplate.postForEntity(uri, request, String.class);

        // mapping response
        if (responseEntityStr.getStatusCodeValue() == 200) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root;
            logger.info("[Call face_general] response : {}", responseEntityStr.getBody());
            try {
                root = mapper.readTree(responseEntityStr.getBody());
                if (root.get("output").get("is_matched").get("value").asText().equalsIgnoreCase(isSelfie)) {
                    // cfMastRepo.activeAccount(custId);
                    isMatching = true;
                } else {
                    throw new BusinessException(ErrorCode.FAILED_TO_JSON, ErrorCode.FAILED_TO_JSON_DESCRIPTION);
                }
            } catch (JsonProcessingException e) {
                throw new BusinessException(ErrorCode.FAILED_TO_JSON, ErrorCode.FAILED_TO_JSON_DESCRIPTION);
            }

        } else {
            throw new BusinessException(ErrorCode.FAILED_TO_EXECUTE, ErrorCode.FAILED_TO_EXECUTE_DESCRIPTION);
        }
        return response(toResult(isMatching));
    }

    @Override
    public Boolean validateOtp(String otp, String id) {
        AccesToken access = getToken();
        logger.info("---------Start call api verify otp---------------");
        final String uri = Constants.VERIFY_OTP_URL;
        HttpHeaders headers = new HttpHeaders();
        headers.set("apikey", Constants.GET_OTP_HEAD_VALUE_2);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", access.getToken_type() + " " + access.getAccess_token());
        String lendbiz = "lbz";
        String otpId = otpService.findIdByOtp(otp);
        String requestJson = "{\"appID\": \"" + lendbiz + "\", \"userID\":\"" + id + "\",\"token\":\"" + otp
                + "\",\"uniqueIdentifier\":\"" + otpId + "\"}";
        logger.info("[Call verify otp] request : {}", requestJson);

        HttpEntity<String> request = new HttpEntity<String>(requestJson, headers);

        System.out.println(request.toString());

        ResponseEntity<String> responseEntityStr = restTemplate.postForEntity(uri, request, String.class);

        // mapping response
        if (responseEntityStr.getStatusCodeValue() == 200) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root;
            logger.info("[Call api validate otp] response : {}", responseEntityStr.getBody());
            try {
                root = mapper.readTree(responseEntityStr.getBody());
                if (root.get("otpStatus").asBoolean()) {
                    return true;
                } else {
                    return false;
                }
            } catch (JsonProcessingException e) {
                throw new BusinessException(ErrorCode.FAILED_TO_JSON, ErrorCode.FAILED_TO_JSON_DESCRIPTION);
            }

        } else {
            throw new BusinessException(ErrorCode.FAILED_TO_EXECUTE, ErrorCode.FAILED_TO_EXECUTE_DESCRIPTION);
        }
    }

    @Override
    public Optional<OtpResponse> getOtp(String id) {
        AccesToken access = getToken();
        logger.info("---------Start call api get otp---------------");
        final String uri = Constants.GET_OTP_URl_2;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("apikey", Constants.GET_OTP_HEAD_VALUE_2);
        // headers.set("Authorization", "Basic Og==");
        headers.add("Authorization", access.getToken_type() + " " + access.getAccess_token());
        // UriComponentsBuilder builder =
        // UriComponentsBuilder.fromHttpUrl(uri).queryParam("userName",
        // Constants.USER_NAME_CLIENT);
        String lendbiz = "lbz";
        String otpId = Utils.createOtpId();
        String description = "lendbiz";
        String requestJson = "{\"appID\": \"" + lendbiz + "\", \"userID\":\"" + id
                + "\",\"ttl\":2,\"verificationAttempt\":3,\"uniqueIdentifier\":\"" + otpId + "\"}";
        logger.info("[Call api get otp] request : {}", requestJson);
        HttpEntity<String> request = new HttpEntity<>(requestJson, headers);
        // HttpEntity<?> entity = new HttpEntity<>(headers);
        // ResponseEntity<String> responseEntityStr =
        // restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity,
        // String.class);
        ResponseEntity<String> responseEntityStr = restTemplate.postForEntity(uri, request, String.class);
        System.out.println(responseEntityStr.toString());
        // mapping response
        if (responseEntityStr.getStatusCodeValue() == 200) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root;
            logger.info("[Call api get otp] response : {}", responseEntityStr.getBody());
            try {
                root = mapper.readTree(responseEntityStr.getBody());
                OtpResponseNew otpResponseNew = new OtpResponseNew();
                otpResponseNew = mapper.readValue(root.toString(), OtpResponseNew.class);
                OtpResponse response = new OtpResponse();
                response.setData(otpResponseNew.getToken());
                response.setCode(200);
                response.setMessage("success");
                Otp otp = new Otp(otpId, otpResponseNew.getToken());
                otpService.create(otp);
                return Optional.of(response);
            } catch (JsonProcessingException e) {
                throw new BusinessException(ErrorCode.FAILED_TO_JSON, ErrorCode.FAILED_TO_JSON_DESCRIPTION);
            }

        } else {
            throw new BusinessException(ErrorCode.FAILED_TO_EXECUTE, ErrorCode.FAILED_TO_EXECUTE_DESCRIPTION);
        }
    }

    @Override
    public Optional<UserRegisterResponse> callRegisterKyc(MultipartFile frontId) {
        logger.info("---------Start call register user face---------------");
        final String uri = Constants.ESIGN_REGISTER;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set(Constants.ESIGN_API_KEY, Constants.ESIGN_VALUE_HEADER);

        MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();
        ByteArrayResource imageCard = convertFile(frontId);
        multiValueMap.add("image", imageCard);
        multiValueMap.add("metadata", Constants.METADATA);
        // String str = Utils.generateId(8);
        // multiValueMap.add("user_id", str);
        multiValueMap.add("user_id", "269EC824-0D4C-46E3-8749-9F4CCDCB12A7");

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(multiValueMap, headers);

        ResponseEntity<String> responseEntityStr = restTemplate.postForEntity(uri, request, String.class);

        // mapping response
        if (responseEntityStr.getStatusCodeValue() == 200) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root;

            try {
                root = mapper.readTree(responseEntityStr.getBody());
                UserRegisterResponse result = mappingObRegiset(root);
                return Optional.of(result);
            } catch (JsonProcessingException e) {
                throw new BusinessException(ErrorCode.FAILED_TO_JSON, ErrorCode.FAILED_TO_JSON_DESCRIPTION);
            }

        } else {
            throw new BusinessException(ErrorCode.FAILED_TO_EXECUTE, ErrorCode.FAILED_TO_EXECUTE_DESCRIPTION);
        }
    }

    private UserRegisterResponse mappingObRegiset(JsonNode root) {
        UserRegisterResponse response = new UserRegisterResponse();
        response.setUserAttrId(root.get("output").get("user_attributes").get("id").asText());
        response.setUserId(root.get("output").get("user_attributes").get("user_id").asText());
        response.setAge(root.get("output").get("user_attributes").get("age").asText());
        response.setCroppedGeneralUrl(root.get("output").get("user_attributes").get("cropped_general_url").asText());
        response.setCroppedUrl(root.get("output").get("face_attributes").get("cropped_url").asText());
        response.setFaceAttrId(root.get("output").get("face_attributes").get("id").asText());
        response.setGender(root.get("output").get("user_attributes").get("gender").asText());
        response.setGeneralUrl(root.get("output").get("user_attributes").get("general_url").asText());
        response.setImageUrl(root.get("output").get("face_attributes").get("image_url").asText());
        response.setLastUpdate(root.get("output").get("user_attributes").get("last_updated").asText());
        response.setRawUserId(root.get("output").get("face_attributes").get("raw_user_id").asText());
        response.setRequestId(root.get("request_id").asText());
        logger.info("[Call register user face] response : {}", response.toString());

        return response;
    }

    @Override
    public Optional<SignPdfResponse> signPdf(MultipartFile contract, SignContractRequest signRequest) {
        logger.info("---------Start call sign pdf---------------");
        final String uri = Constants.SIGN_PDF;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set(Constants.OTP_API_KEY, Constants.OTP_VALUE_HEADER);

        MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();
        ByteArrayResource imageCard = convertFile(contract);
        multiValueMap.add("fileSign", imageCard);
        multiValueMap.add("slotLabel", Constants.SLOT_LABEL);
        multiValueMap.add("userPin", Constants.USER_PIN);
        multiValueMap.add("alias", Constants.ALIAS);
        multiValueMap.add("isVisible", Constants.IS_VISIBLE);
        multiValueMap.add("page", signRequest.getPage());
        multiValueMap.add("llx", signRequest.getLlx());
        multiValueMap.add("lly", signRequest.getLly());
        multiValueMap.add("urx", signRequest.getUrx());
        multiValueMap.add("ury", signRequest.getUry());
        // multiValueMap.add("detail", signRequest.getDetail());
        multiValueMap.add("detail", "1,6");
        if (StringUtil.isEmty(signRequest.getReason())) {
            multiValueMap.add("reason", "Ä�á»“ng Ã½ kÃ½ há»£p Ä‘á»“ng");
        }

        if (StringUtil.isEmty(signRequest.getLocation())) {
            multiValueMap.add("location", "HÃ  Ná»™i");
        }

        if (StringUtil.isEmty(signRequest.getContactInfo())) {
            multiValueMap.add("contactInfo", "khu Ä‘Ã´ thá»‹ Ä�áº¡i Kim");
        }

        try {
            File signImage = new File(Constants.SIGN_IMAGE_DEFAULT);
            FileInputStream input = new FileInputStream(signImage);
            MultipartFile imgMultiPartFile = new MockMultipartFile("sign_pdf", signImage.getName(), "text/plain",
                    IOUtils.toByteArray(input));
            ByteArrayResource signature = convertFile(imgMultiPartFile);
            multiValueMap.add("image", signature);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(multiValueMap, headers);

        ResponseEntity<String> responseEntityStr = restTemplate.postForEntity(uri, request, String.class);

        // mapping response
        if (responseEntityStr.getStatusCodeValue() == 200) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root;
            // logger.info("[Call sign pdf] response : {}", responseEntityStr.getBody());
            try {
                root = mapper.readTree(responseEntityStr.getBody());
                SignPdfResponse result = new SignPdfResponse();
                result = mapper.readValue(root.toString(), SignPdfResponse.class);
                // result.setCode(root.get("code").toString());
                // result.setData(root.get("data").toString());

                return Optional.of(result);
            } catch (JsonProcessingException e) {
                throw new BusinessException(ErrorCode.FAILED_TO_JSON, ErrorCode.FAILED_TO_JSON_DESCRIPTION);
            }

        } else {
            throw new BusinessException(ErrorCode.FAILED_TO_EXECUTE, ErrorCode.FAILED_TO_EXECUTE_DESCRIPTION);
        }
    }

    @Override
    public Optional<SignPdfResponse> signContract(MultipartFile contract, SignContractRequestV2 signRequest) {
        AccesToken access = getToken();
        logger.info("---------Start call sign pdf---------------");
        String uri = Constants.SIGN_PDF;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.add("Authorization", access.getToken_type() + " " + access.getAccess_token());
        headers.add("X-WSO2-CLIENT-CERTIFICATE", Constants.X_WSO2);

        MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();
        ByteArrayResource imageCard = convertFile(contract);
        multiValueMap.add("fileSign", imageCard);
        multiValueMap.add("slotLabel",
                signRequest.getIsLBC().equalsIgnoreCase("lbc") ? Constants.LBC_SLOT_LABEL : Constants.SLOT_LABEL);
        multiValueMap.add("userPin",
                signRequest.getIsLBC().equalsIgnoreCase("lbc") ? Constants.LBC_USER_PIN : Constants.USER_PIN);
        multiValueMap.add("alias",
                signRequest.getIsLBC().equalsIgnoreCase("lbc") ? Constants.LBC_ALIAS : Constants.ALIAS);
        multiValueMap.add("isVisible", Constants.IS_VISIBLE);

        multiValueMap.add("positions", generatePositionParam(signRequest.getPositions()));

        multiValueMap.add("detail", signRequest.getDetail());
        if (StringUtil.isEmty(signRequest.getReason())) {
            multiValueMap.add("reason", "Ký hợp đồng 3Gang");
        }

        if (StringUtil.isEmty(signRequest.getLocation())) {
            multiValueMap.add("location", "Hà Nội");
        }

        if (StringUtil.isEmty(signRequest.getContactInfo())) {
            multiValueMap.add("contactInfo", "Khu độ thị Đại Kim");
        }

        if (signRequest.getType().equalsIgnoreCase("client")) {
            multiValueMap.add("signedBy", WordUtils.capitalize(signRequest.getSignedBy().toLowerCase()));
        } else {
            try {
                File signImage = new File(Constants.SIGN_IMAGE_DEFAULT);
                FileInputStream input = new FileInputStream(signImage);
                MultipartFile imgMultiPartFile = new MockMultipartFile("sign_pdf",
                        signImage.getName(), "text/plain",
                        IOUtils.toByteArray(input));
                ByteArrayResource signature = convertFile(imgMultiPartFile);
                multiValueMap.add("imageData", signature);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri).queryParam("type", signRequest.getType());

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(multiValueMap, headers);

        System.out.println(builder.toUriString());

        ResponseEntity<String> responseEntityStr = restTemplate.exchange(builder.toUriString(), HttpMethod.POST,
                request, String.class);

        // ResponseEntity<String> responseEntityStr =
        // restTemplate.postForEntity("https://uat-gateway.trustca.vn/signing/1.1.7/pdf",
        // request, String.class);
        // mapping response
        if (responseEntityStr.getStatusCodeValue() == 200) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root;
            // logger.info("[Call sign pdf] response : {}", responseEntityStr.getBody());
            try {
                root = mapper.readTree(responseEntityStr.getBody());
                SignPdfResponse result = new SignPdfResponse();
                result = mapper.readValue(root.toString(), SignPdfResponse.class);
                // result.setCode(root.get("code").toString());
                // result.setData(root.get("data").toString());

                return Optional.of(result);
            } catch (JsonProcessingException e) {
                throw new BusinessException(ErrorCode.FAILED_TO_JSON, ErrorCode.FAILED_TO_JSON_DESCRIPTION);
            }

        } else {
            throw new BusinessException(ErrorCode.FAILED_TO_EXECUTE, ErrorCode.FAILED_TO_EXECUTE_DESCRIPTION);
        }
    }

    @Override
    public AccesToken getToken() {
        String plainCreds = Constants.BASIC_AUTHEN_STRING;
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);

        logger.info("---------Start Get token Access---------------");
        final String uri = Constants.GET_TOKEN_URL;
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
            // logger.info("Start get token access: {}", responseEntityStr.getBody());
            try {
                root = mapper.readTree(responseEntityStr.getBody());
                AccesToken result = new AccesToken();
                result = mapper.readValue(root.toString(), AccesToken.class);
                return result;
            } catch (JsonProcessingException e) {
                throw new BusinessException(ErrorCode.FAILED_TO_JSON, ErrorCode.FAILED_TO_JSON_DESCRIPTION);
            }

        } else {
            throw new BusinessException(ErrorCode.FAILED_TO_EXECUTE, ErrorCode.FAILED_TO_EXECUTE_DESCRIPTION);
        }

    }

    private String generatePositionParam(ArrayList<String> positions) {
        String param = "";
        StringBuilder strBuilder = new StringBuilder(param);

        if (positions.size() > 1) {
            for (String s : positions) {
                strBuilder.append(s);
                strBuilder.append(",");
            }
            strBuilder.deleteCharAt(strBuilder.length() - 1);
        } else {
            strBuilder = new StringBuilder(positions.get(0));
        }

        return strBuilder.toString();
    }
}
