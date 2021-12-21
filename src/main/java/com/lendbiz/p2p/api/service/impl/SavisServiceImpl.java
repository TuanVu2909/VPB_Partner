package com.lendbiz.p2p.api.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lendbiz.p2p.api.constants.Constants;
import com.lendbiz.p2p.api.constants.ErrorCode;
import com.lendbiz.p2p.api.constants.JsonMapper;
import com.lendbiz.p2p.api.entity.IdCard;
import com.lendbiz.p2p.api.model.exception.BusinessException;
import com.lendbiz.p2p.api.request.SavisVerifyOtpRequest;
import com.lendbiz.p2p.api.request.SignContractRequest;
import com.lendbiz.p2p.api.request.SignContractRequestV2;
import com.lendbiz.p2p.api.response.AccesToken;
import com.lendbiz.p2p.api.response.InfoIdentity;
import com.lendbiz.p2p.api.response.OtpResponse;
import com.lendbiz.p2p.api.response.OtpResponseNew;
import com.lendbiz.p2p.api.response.SignPdfResponse;
import com.lendbiz.p2p.api.response.UserRegisterResponse;
import com.lendbiz.p2p.api.service.SavisService;
import com.lendbiz.p2p.api.service.base.BaseService;
import com.lendbiz.p2p.api.utils.StringUtil;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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

@Service
public class SavisServiceImpl extends BaseService implements SavisService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    public IdCardImpl idCardImpl;

    private final String isSelfie = "TRUE";

    public SavisServiceImpl(Environment env) {
        super(env);
    }

    @Override
    public Optional<InfoIdentity> callPredict(MultipartFile file, InfoIdentity identity, String type) {
        logger.info("---------Start call predict---------------");
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
            if (Constants.TYPE_FRONT_IDENTITY.equalsIgnoreCase(type)) {
                try {
                    root = mapper.readTree(responseEntityStr.getBody());
                    String bd = root.get("output").get(0).get("ngay_sinh").get("value").asText();

                    DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

                    Date birthDay = null;

                    try {
                        birthDay = df.parse(bd);

                    } catch (ParseException e) {

                        birthDay = new Date();
                    }

                    // mặt trước
                    identity.setFullName(root.get("output").get(0).get("ho_ten").get("value").asText());
                    identity.setAddress(root.get("output").get(0).get("ho_khau_thuong_tru").get("value").asText());
                    identity.setNgaysinh(birthDay);
                    identity.setDomicile(root.get("output").get(0).get("nguyen_quan").get("value").asText());
                    identity.setType(root.get("output").get(0).get("class_name").get("value").asText());
                    identity.setIdentityId(root.get("output").get(0).get("id").get("value").asText());
                    identity.setBirthday(root.get("output").get(0).get("ngay_sinh").get("normalized")
                            .get("value_unidecode").asText());
                    identity.setAddress(root.get("output").get(0).get("nguyen_quan").get("value_unidecode").asText());
                    IdCard idCard = new IdCard();
                    idCard.setAddress(identity.getAddress());
                    idCard.setBirthDay(identity.getNgaysinh());
                    idCard.setFullName(identity.getFullName());
                    idCard.setDomicile(identity.getDomicile());
                    idCard.setIdentity(identity.getIdentityId());
                    idCard.setType(identity.getType());
                    String id = String.valueOf((int) Math.floor(Math.random() * 100000));
                    idCard.setId(id);
                    idCard.setCustID(identity.getCustId());
                    idCard = idCardImpl.create(idCard);

                } catch (JsonProcessingException e) {
                    throw new BusinessException(ErrorCode.FAILED_TO_JSON, ErrorCode.FAILED_TO_JSON_DESCRIPTION);
                }
            } else if (Constants.TYPE_BACK_IDENTITY.equalsIgnoreCase(type)) {
                try {

                    root = mapper.readTree(responseEntityStr.getBody());
                    Date dateR = null;
                    DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                    String dr = root.get("output").get(0).get("ngay_cap").get("normalized")
                            .get("value_unidecode").asText();
                    try {
                        dateR = df.parse(dr);

                    } catch (ParseException e) {

                        dateR = new Date();
                    }
                    identity.setIssuedBy(root.get("output").get(0).get("noi_cap").get("value")
                            .asText());
                    identity.setType("CĂN CƯỚC CÔNG DÂN - MẶT SAU");
                    identity.setDateRange(dateR);
                    identity.setDateIssued(root.get("output").get(0).get("ngay_cap").get("normalized")
                            .get("value_unidecode").asText());

                    IdCard idCardBack = new IdCard();
                    idCardBack.setDateRange(identity.getDateRange());
                    idCardBack.setIssuedBy(identity.getIssuedBy());
                    idCardBack.setType(identity.getType());
                    idCardBack.setCustID(identity.getCustId());
                    String id2 = String.valueOf((int) Math.floor(Math.random() * 100000));
                    idCardBack.setId(id2);

                    idCardBack = idCardImpl.create(idCardBack);
                } catch (JsonProcessingException e) {
                    throw new BusinessException(ErrorCode.FAILED_TO_JSON, ErrorCode.FAILED_TO_JSON_DESCRIPTION);
                }
            }

        } else {
            throw new BusinessException(ErrorCode.FAILED_TO_EXECUTE, ErrorCode.FAILED_TO_EXECUTE_DESCRIPTION);
        }
        logger.info("[Call perdict] infoIdentity result : {}", JsonMapper.writeValueAsString(identity));

        return Optional.of(identity);
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
    public Boolean callCheckSelfie(MultipartFile frontId, MultipartFile selfie, String threshold) {
        logger.info("---------Start call face_general---------------");
        final String uri = Constants.ESIGN_FACE_GENERAL;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set(Constants.ESIGN_API_KEY, Constants.ESIGN_VALUE_HEADER);

        MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();
        ByteArrayResource imageCard = convertFile(frontId);
        multiValueMap.add("image_card", imageCard);
        ByteArrayResource imageGeneral = convertFile(selfie);
        multiValueMap.add("image_general", imageGeneral);
        multiValueMap.add("threshold", threshold);

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
    public Optional<OtpResponse> getOtp() {
        AccesToken access = getToken();
        logger.info("---------Start call api get otp---------------");
        final String uri = Constants.GET_OTP;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(Constants.OTP_API_KEY, Constants.OTP_VALUE_HEADER);
        // headers.set("Authorization", "Basic Og==");
        headers.add("Authorization", access.getToken_type() + " " + access.getAccess_token());
        // UriComponentsBuilder builder =
        // UriComponentsBuilder.fromHttpUrl(uri).queryParam("userName",
        // Constants.USER_NAME_CLIENT);
        String lendbiz = "LENDBIZ";
        String des = "OTP for LENDBIZ";
        String requestJson = "{\"userName\": \"" + Constants.USER_NAME_CLIENT + "\", \"appRequest\":\"" + lendbiz
                + "\",\"step\":120,\"totpSize\":6,\"description\":\"" + des + "\"}";

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
                response.setData(otpResponseNew.getData().getOtp());
                response.setCode(otpResponseNew.getCode());
                response.setMessage(otpResponseNew.getMessage());
                response.setTraceId(otpResponseNew.getTraceId());

                return Optional.of(response);
            } catch (JsonProcessingException e) {
                throw new BusinessException(ErrorCode.FAILED_TO_JSON, ErrorCode.FAILED_TO_JSON_DESCRIPTION);
            }

        } else {
            throw new BusinessException(ErrorCode.FAILED_TO_EXECUTE, ErrorCode.FAILED_TO_EXECUTE_DESCRIPTION);
        }
    }

    @Override
    public Boolean validateOtp(String otp) {
        AccesToken access = getToken();
        logger.info("---------Start call api verify otp---------------");
        final String uri = Constants.VALIDATE_OTP;
        HttpHeaders headers = new HttpHeaders();
        headers.set(Constants.OTP_API_KEY, Constants.OTP_VALUE_HEADER);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", access.getToken_type() + " " + access.getAccess_token());
        SavisVerifyOtpRequest requestObj = new SavisVerifyOtpRequest();
        requestObj.setUserName(Constants.USER_NAME_CLIENT);
        requestObj.setOtp(otp);
        String requestJson = "{\"userName\": \"" + Constants.USER_NAME_CLIENT + "\", \"appRequest\":\""
                + Constants.LENDBIZ + "\",\"step\":120,\"otp\":\"" + otp + "\",\"description\":\"" + Constants.DES
                + "\"}";

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
                if (root.get("data").asBoolean()) {
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

        if (signRequest.getType().equalsIgnoreCase("client")) {
            multiValueMap.add("signedBy", signRequest.getSignedBy());
        }
        multiValueMap.add("positions", generatePositionParam(signRequest.getPositions()));

        multiValueMap.add("detail", signRequest.getDetail());
        if (StringUtil.isEmty(signRequest.getReason())) {
            multiValueMap.add("reason", "Ä�á»“ng Ã½ kÃ½ há»£p Ä‘á»“ng");
        }

        if (StringUtil.isEmty(signRequest.getLocation())) {
            multiValueMap.add("location", "HÃ  Ná»™i");
        }

        if (StringUtil.isEmty(signRequest.getContactInfo())) {
            multiValueMap.add("contactInfo", "khu Ä‘Ã´ thá»‹ Ä�áº¡i Kim");
        }

        // try {
        // File signImage = new File(Constants.SIGN_IMAGE_DEFAULT);
        // FileInputStream input = new FileInputStream(signImage);
        // MultipartFile imgMultiPartFile = new MockMultipartFile("sign_pdf",
        // signImage.getName(), "text/plain",
        // IOUtils.toByteArray(input));
        // ByteArrayResource signature = convertFile(imgMultiPartFile);
        // multiValueMap.add("image", signature);
        // } catch (IOException e) {
        // e.printStackTrace();
        // }

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
            logger.info("Start get token access: {}", responseEntityStr.getBody());
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
