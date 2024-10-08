package com.lendbiz.p2p.api.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lendbiz.p2p.api.constants.Constants;
import com.lendbiz.p2p.api.constants.ErrorCode;
import com.lendbiz.p2p.api.entity.BaoVietEntity;
import com.lendbiz.p2p.api.entity.CfMast;
import com.lendbiz.p2p.api.entity.InsuranceAdditionPrice;
import com.lendbiz.p2p.api.entity.InsuranceList;
import com.lendbiz.p2p.api.entity.InsurancePrice;
import com.lendbiz.p2p.api.entity.NotifyEntity;
import com.lendbiz.p2p.api.entity.Premium;
import com.lendbiz.p2p.api.entity.WithdrawEntity;
import com.lendbiz.p2p.api.exception.BusinessException;
import com.lendbiz.p2p.api.model.bvpremium.PremiumConverter;
import com.lendbiz.p2p.api.model.bvpremium.PremiumModel;
import com.lendbiz.p2p.api.model.listbvaddbasevm.BvPartner;
import com.lendbiz.p2p.api.model.listbvaddbasevm.Converter;
import com.lendbiz.p2p.api.repository.BaoVietRepo;
import com.lendbiz.p2p.api.repository.CfMastRepository;
import com.lendbiz.p2p.api.repository.InsuranceAdditionPriceRepository;
import com.lendbiz.p2p.api.repository.InsuranceListRepository;
import com.lendbiz.p2p.api.repository.InsurancePriceRepository;
import com.lendbiz.p2p.api.repository.Insurance_infoRepository;
import com.lendbiz.p2p.api.repository.NotifyRepo;
import com.lendbiz.p2p.api.repository.WithdrawRepo;
import com.lendbiz.p2p.api.request.AttachFile;
import com.lendbiz.p2p.api.request.CreatePolicyPartnerRequest;
import com.lendbiz.p2p.api.request.CreatePolicyPartnerRq;
import com.lendbiz.p2p.api.request.InsuranceRequest;
import com.lendbiz.p2p.api.request.baovietEntity.AddressDistrictData;
import com.lendbiz.p2p.api.request.baovietEntity.BvLogin;
import com.lendbiz.p2p.api.request.baovietEntity.InvoiceInfo;
import com.lendbiz.p2p.api.request.baovietEntity.ListBvgAddBaseVM;
import com.lendbiz.p2p.api.request.baovietEntity.QuocTich;
import com.lendbiz.p2p.api.request.baovietEntity.ReceiverUser;
import com.lendbiz.p2p.api.request.baovietEntity.SaleToEmp;
import com.lendbiz.p2p.api.response.BaseResponse;
import com.lendbiz.p2p.api.service.InsuranceService;
import com.lendbiz.p2p.api.utils.Utils;

@Service
public class InsuranceServiceImpl extends BaseResponse<InsuranceService> implements InsuranceService {
    static String BV_TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJsZW5kYml6QGJhb3ZpZXQuY29tLnZuIiwiYXV0aCI6IlBFUk1fQUdSRUVNRU5UX0NSRUFURSxQRVJNX0FHUkVFTUVOVF9ERUxFVEUsUEVSTV9BR1JFRU1FTlRfRURJVCxQRVJNX0FHUkVFTUVOVF9WSUVXLFBFUk1fQ0FSVF9DUkVBVEUsUEVSTV9DQVJUX0RFTEVURSxQRVJNX0NBUlRfRURJVCxQRVJNX0NBUlRfVklFVyxQRVJNX0NPTlRBQ1RfQ1JFQVRFLFBFUk1fQ09OVEFDVF9ERUxFVEUsUEVSTV9DT05UQUNUX0VESVQsUEVSTV9DT05UQUNUX1ZJRVcsUEVSTV9QQVlfQ0hVWUVOX1RIVSxQRVJNX1BBWV9LSEFDSF9IQU5HX1RULFBFUk1fUEFZX1RIQU5IVE9BTl9TQVUsUEVSTV9QQVlfVk5QQVksUEVSTV9QUk9EVUNUX0JWR19DUkVBVEUsUEVSTV9QUk9EVUNUX0JWR19ERUxFVEUsUEVSTV9QUk9EVUNUX0JWR19FRElULFBFUk1fUFJPRFVDVF9CVkdfVklFVyxQRVJNX1JFUE9SVF9DT01NSVNTSU9OX1ZJRVcsUEVSTV9SRVBPUlRfSU5DT01FX1ZJRVcsUEVSTV9SRVBPUlRfTFYxLFBFUk1fUkVQT1JUX1RSQU5TRkVSX1ZJRVcsUk9MRV9BRE1JTixST0xFX0FHRU5DWSxST0xFX1JFUE9SVF9BR0VOQ1kiLCJleHAiOjE2NzAxMjIzNjB9.MD2o0C5EShRZdsQVj9Hy3axf-0uIhWPeBGC0ha0nvCmv_m7n0RPa4ii8LyycF8mq9kz7ouKsTQFoWDT0AMS-_w";
    static String BV_PREMIUM_URI = "https://agency-api-dev.baoviet.com.vn/api/agency/product/bvg/premium";
    static String BV_PARTNER_URI = "https://agency-api-dev.baoviet.com.vn/api/agency/product/bvg/createPolicy-Partner";
    static String BV_ODER_DOWNLOAD_URI = "https://agency-api-dev.baoviet.com.vn/api/agency/document/download-file-agreement";
    static String BV_NOTIFY_PAYMENT = "https://agency-api-dev.baoviet.com.vn/api/agency/payment/notify-payment";
    static String BV_ODER_INFO_URI = "https://agency-api-dev.baoviet.com.vn/api/agency/product/agreement/get-by-gycbhNumber";
    static String BV_API_LOGIN = "https://agency-api-dev.baoviet.com.vn/api/agency/account/login";

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    InsurancePriceRepository insurancePriceRepository;
    @Autowired
    NotifyRepo notifyRepo;
    @Autowired
    CfMastRepository cfMastRepository;
    @Autowired
    Insurance_infoRepository insurance_infoRepository;
    @Autowired
    InsuranceListRepository insuranceListRepository;

    @Autowired
    BaoVietRepo baoVietRepo;

    public String getToken() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + BV_TOKEN);
            BvLogin loginRequest = new BvLogin();
            loginRequest.setUsername("lendbiz@baoviet.com.vn");
            loginRequest.setPassword("Lendbiz@123");
            JSONObject jsonObject = new JSONObject(loginRequest);

            HttpEntity<String> request = new HttpEntity<String>(jsonObject.toString(), headers);
            ResponseEntity<String> responseEntityStr = restTemplate.postForEntity(BV_API_LOGIN, request,
                    String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root;
            try {
                root = mapper.readTree(responseEntityStr.getBody());
                return root.get("id_token").asText();
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            throw new BusinessException("200", e.getMessage());
        }

        return null;
    }

    @Override
    public ResponseEntity<?> premium(Premium premium) {

        if (Utils.getAge(premium.getNguoidbhNgaysinh()) < 6) {
            throw new BusinessException(ErrorCode.UNKNOWN_ERROR, "Người được bảo hiểm phải từ 6 tuổi trở lên");
        }

        if (Utils.getAge(premium.getNguoidbhNgaysinh()) > 60) {
            throw new BusinessException(ErrorCode.UNKNOWN_ERROR, "Người được bảo hiểm phải từ 6 đến 60 tuổi");
        }

        if (premium.getSmcnChk().equals("1") && premium.getSmcnSotienbh() == 300000000) {
            throw new BusinessException(ErrorCode.UNKNOWN_ERROR,
                    "Quyền lợi sinh mạng cá nhân phải nhỏ hơn 300 triệu");
        }

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + getToken());
            JSONObject jsonObject = new JSONObject(premium);
            logger.info("[Premium request]: " + jsonObject.toString());

            HttpEntity<String> request = new HttpEntity<String>(jsonObject.toString(), headers);
            ResponseEntity<String> responseEntityStr = restTemplate.postForEntity(BV_PREMIUM_URI, request,
                    String.class);

            // ObjectMapper mapper = new ObjectMapper();
            // JsonNode root;
            try {
                // root = mapper.readTree(responseEntityStr.getBody());

                PremiumModel model = new PremiumModel();
                model = PremiumConverter.fromJsonString(responseEntityStr.getBody());

                return response(toResult(model));
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.UNKNOWN_ERROR, getErrMessage(e.getMessage()));
        }

        return null;
    }

    private String getErrMessage(String s) {

        // Find the start and end indexes of the message part
        int startIndex = s.indexOf("\"message\" : ");
        int endIndex = s.indexOf("\",", startIndex);

        if (startIndex != -1 && endIndex != -1) {
            // Extract the message string
            String message = s.substring(startIndex + "\"message\" : \"".length(), endIndex);
            return message;
        } else {
            return "Không xác định lỗi!";
        }
    }

    @Override
    public ResponseEntity<?> createPolicy_Partner(CreatePolicyPartnerRq rq) {

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + getToken());
            JSONObject jsonObject = new JSONObject(rq);
            System.out.println(jsonObject);
            HttpEntity<String> request = new HttpEntity<String>(jsonObject.toString(), headers);
            ResponseEntity<String> responseEntityStr = restTemplate.postForEntity(BV_PARTNER_URI, request,
                    String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root;
            root = mapper.readTree(responseEntityStr.getBody());
            return response(toResult(root));
        } catch (JSONException err) {
            throw new BusinessException("111", "Parse JSON fail");
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseEntity<?> getByGycbhNumber(String gycbhNumber) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + getToken());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("gycbhNumber", gycbhNumber);

            HttpEntity<String> request = new HttpEntity<String>(jsonObject.toString(), headers);
            ResponseEntity<String> responseEntityStr = restTemplate.postForEntity(BV_ODER_INFO_URI, request,
                    String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root;
            root = mapper.readTree(responseEntityStr.getBody());
            return response(toResult(root));
        } catch (JSONException err) {
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
        ArrayList<InsurancePrice> list = (ArrayList<InsurancePrice>) insurancePriceRepository
                .getInsurancePackagePrice(pkgId, age);
        if (list.size() == 0)
            throw new BusinessException(Constants.FAIL, ErrorCode.NO_DATA_DESCRIPTION);
        return response(toResult(list));
    }

    @Override
    public ResponseEntity<?> downloadFileOder(String gid, String type) {
        try {
            ;
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + getToken());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("gycbhNumber", gid);
            jsonObject.put("type", type);
            HttpEntity<String> request = new HttpEntity<String>(jsonObject.toString(), headers);
            ResponseEntity<String> responseEntityStr = restTemplate.postForEntity(BV_ODER_DOWNLOAD_URI, request,
                    String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root;
            root = mapper.readTree(responseEntityStr.getBody());
            return response(toResult(root));
        } catch (JSONException err) {
            throw new BusinessException("111", "Parse JSON fail");
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Autowired
    InsuranceAdditionPriceRepository insuranceAdditionPriceRepository;

    @Override
    public ResponseEntity<?> getInsuranceAdditionPrice(String pkgId, String age) {
        List<InsuranceAdditionPrice> list = insuranceAdditionPriceRepository.getInsuranceAdditionPrice(pkgId, age);
        if (list.size() == 0)
            throw new BusinessException(Constants.FAIL, ErrorCode.NO_DATA_DESCRIPTION);
        return response(toResult(list));
    }

    private int roundUP(double d) {
        double dAbs = Math.abs(d);
        int i = (int) dAbs;
        double result = dAbs - (double) i;
        if (result == 0.0) {
            return (int) d;
        } else {
            return (int) d < 0 ? -(i + 1) : i + 1;
        }
    }

    @Autowired
    WithdrawRepo withdrawRepo;

    @Override
    public ResponseEntity<?> createInsurance(InsuranceRequest insuranceRequest) {
        logger.info("[InsuranceRequest]: " + new JSONObject(insuranceRequest).toString());
        Optional<CfMast> cfMast = cfMastRepository.findByCustid(insuranceRequest.getPv_custId());

        String age = String.valueOf(Utils.getAge(insuranceRequest.getPv_insuredPersonBirthDate()));
        List<InsuranceAdditionPrice> listAdd = insuranceAdditionPriceRepository
                .getInsuranceAdditionPrice(insuranceRequest.getPv_packageId(), age);
        ArrayList<BaoVietEntity> listPackage = baoVietRepo.getInsurancePackage();

        InsurancePrice insurancePrice = insurancePriceRepository
                .getInsurancePackagePrice(insuranceRequest.getPv_packageId(), age).get(0);

        CreatePolicyPartnerRequest request = new CreatePolicyPartnerRequest();
        long sumPrice = Long.parseLong(insurancePrice.getPrice());
        request.setGuaranteeCard(1);
        // request.setSoNguoiThamGia(1);
        request.setContactAddress("71 nsl");
        request.setContactCategoryType("PERSON");
        request.setContactCode("15606");
        request.setContactDob(Utils.convertDateToString(cfMast.get().getDateOfBirth()));
        request.setContactEmail(cfMast.get().getEmail());
        request.setContactIdNumber(cfMast.get().getIdCode());
        request.setContactName(cfMast.get().getFullName());
        request.setContactPhone(cfMast.get().getPhone());
        request.setDepartmentId("A000009455");
        request.setPromoDiscount(0);
        request.setHasGks(false);
        request.setHasNguoinhantien(false);
        request.setHasNguoithuhuong(false);
        if (!insuranceRequest.getPv_beneficiaryFullName().equals("")) {
            request.setHasNguoithuhuong(true);
            request.setNguoinhanName(insuranceRequest.getPv_beneficiaryFullName());
            request.setNguoinhanCmnd(insuranceRequest.getPv_beneficiaryIdNumber());
            request.setNguointNgaysinh(insuranceRequest.getPv_beneficiaryBirthDate());
            switch (insuranceRequest.getPv_InsuredRelationId()) {
                case "1":
                    request.setNguoinhanQuanhe("31");
                    break;
                case "2":
                    request.setNguoinhanQuanhe("32");
                    break;
                case "3":
                    request.setNguoinhanQuanhe("33");
                    break;
                case "4":
                    request.setNguoinhanQuanhe("34");
                    break;
                default:
                    request.setNguoinhanQuanhe("30");
                    break;
            }
        }

        request.setHasTThoadonGTG(false);

        request.setIsShowDgrr(false);
        request.setInsuranceTarget("New");
        // InvoiceInfo invoiceInfo = new InvoiceInfo();
        InvoiceInfo invoiceInfo = new InvoiceInfo();
        invoiceInfo.setCheck("0");
        request.setInvoiceInfo(invoiceInfo);
        request.setLineId("BVG");
        // ListBvgAddBaseVM listBvgAddBaseVM = new ListBvgAddBaseVM();
        ListBvgAddBaseVM listBvgAddBaseVM = new ListBvgAddBaseVM();

        if (insuranceRequest.getContent().equals("") || insuranceRequest.getContent() != null) {
            request.setHasGks(true);
            AttachFile attachFile = new AttachFile();
            attachFile.setAttachmentID(insuranceRequest.getAttachmentId());
            // attachFile.setContent(insuranceRequest.getContent());
            attachFile.setContent(insuranceRequest.getContent());

            attachFile.setFileType("image/" + insuranceRequest.getFileType());
            attachFile.setFilename(insuranceRequest.getFilename());
            AttachFile[] lstFiles = new AttachFile[1];
            lstFiles[0] = attachFile;
            request.setAttachFiles(lstFiles);
            listBvgAddBaseVM.setGksFile(attachFile);
        }

        listBvgAddBaseVM.setChuongTrinhBh(insuranceRequest.getPv_packageId());
        listBvgAddBaseVM.setDiscount(0);
        listBvgAddBaseVM.setHasExtracare(false);
        listBvgAddBaseVM.setHasNguoithuhuong(false);
        if (!insuranceRequest.getPv_beneficiaryFullName().equals("")) {
            listBvgAddBaseVM.setHasNguoithuhuong(true);
            listBvgAddBaseVM.setNguoithName(insuranceRequest.getPv_beneficiaryFullName());
            listBvgAddBaseVM.setNguoithCmnd(insuranceRequest.getPv_beneficiaryIdNumber());
            listBvgAddBaseVM.setNguoithNgaysinh(insuranceRequest.getPv_beneficiaryBirthDate());
            switch (insuranceRequest.getPv_InsuredRelationId()) {
                case "1":
                    listBvgAddBaseVM.setNguoithQuanhe("31");
                    break;
                case "2":
                    listBvgAddBaseVM.setNguoithQuanhe("32");
                    break;
                case "3":
                    listBvgAddBaseVM.setNguoithQuanhe("33");
                    break;
                case "4":
                    listBvgAddBaseVM.setNguoithQuanhe("34");
                    break;
                default:
                    listBvgAddBaseVM.setNguoithQuanhe("30");
                    break;
            }
        }

        listBvgAddBaseVM.setKetqua("Sick");
        // ImgGks imgGks = new ImgGks("", "", "", "");
        // ImgGks imgGks = new ImgGks();
        // listBvgAddBaseVM.setImgGks(imgGks);

        listBvgAddBaseVM.setNgoaitruChk(insuranceRequest.getPv_isOutPatientFee());
        listBvgAddBaseVM.setNgoaitruPhi(Long.parseLong(listAdd.get(0).getPrice()));
        if (insuranceRequest.getPv_isOutPatientFee().equals("0")) {
            listBvgAddBaseVM.setNgoaitruPhi(0);
        }
        listBvgAddBaseVM.setNguoidbhCmnd(insuranceRequest.getPv_insuredPersonIdNumber());
        listBvgAddBaseVM.setNguoidbhGioitinh(insuranceRequest.getPv_insuredPersonGender());
        listBvgAddBaseVM.setNguoidbhName(insuranceRequest.getPv_insuredPersonFullName());
        listBvgAddBaseVM.setNguoidbhNgaysinh(insuranceRequest.getPv_insuredPersonBirthDate());
        switch (insuranceRequest.getPv_RelationId()) {
            case "1":
                listBvgAddBaseVM.setNguoidbhQuanhe("31");
                break;
            case "2":
                listBvgAddBaseVM.setNguoidbhQuanhe("32");
                break;
            case "3":
                listBvgAddBaseVM.setNguoidbhQuanhe("33");
                break;
            case "4":
                listBvgAddBaseVM.setNguoidbhQuanhe("34");
                break;
            default:
                listBvgAddBaseVM.setNguoidbhQuanhe("30");
                break;
        }

        listBvgAddBaseVM.setNhakhoaChk(insuranceRequest.getPv_isDentistryFee());
        // listBvgAddBaseVM.setNhakhoaChk("0");
        listBvgAddBaseVM.setNhakhoaPhi(Float.parseFloat(listAdd.get(2).getPrice()));
        if (insuranceRequest.getPv_isDentistryFee().equals("0")) {
            listBvgAddBaseVM.setNhakhoaPhi(0);
        }
        listBvgAddBaseVM.setPercentId(0);
        listBvgAddBaseVM.setQlChinhPhi(insuranceRequest.getPv_fee());
        listBvgAddBaseVM.setSmcnChk(insuranceRequest.getPv_isLifeFee());
        Float lifeFee = Float.parseFloat(listAdd.get(3).getPrice());
        // listBvgAddBaseVM.setSmcnChk("0");
        lifeFee = sumPrice * lifeFee;
        listBvgAddBaseVM.setSmcnPhi(lifeFee);
        listBvgAddBaseVM.setSmcnSotienbh(insuranceRequest.getPv_isLifeFeeValue());
        if (insuranceRequest.getPv_isLifeFee().equals("0")) {
            listBvgAddBaseVM.setSmcnPhi(0);
            listBvgAddBaseVM.setSmcnSotienbh(0);
        }
        // listBvgAddBaseVM.setSmcnSotienbhTemp("0");
        listBvgAddBaseVM.setTanggiamPhi(0);
        listBvgAddBaseVM.setIsShowPersonList("1");
        listBvgAddBaseVM.setThaisanChk(insuranceRequest.getPv_isPregnantFee());
        // listBvgAddBaseVM.setThaisanPhi(0);
        listBvgAddBaseVM.setTncnChk(insuranceRequest.getPv_isAccidentFee());
        float accident = Float.parseFloat(listAdd.get(1).getPrice());
        accident = accident * sumPrice;
        listBvgAddBaseVM.setTncnPhi(accident);
        listBvgAddBaseVM.setTncnSotienbh(insuranceRequest.getPv_isAccidentFeeValue());
        if (insuranceRequest.getPv_isAccidentFee().equals("0")) {
            listBvgAddBaseVM.setTncnPhi(0);
            listBvgAddBaseVM.setTncnSotienbh(0);
        }
        // listBvgAddBaseVM.setTncnSotienbhTemp("0");
        listBvgAddBaseVM.setTongPhiBH(sumPrice);
        listBvgAddBaseVM.setTuoi(26);
        listBvgAddBaseVM.setPolicyParent(insuranceRequest.getInsuranceIdNumber());

        listBvgAddBaseVM.setSerial("WwhpI6Jy");
        // listBvgAddBaseVM.setInsuranceTarget("New");
        listBvgAddBaseVM.setLoading(0);
        listBvgAddBaseVM.setPersonOrder(1);
        listBvgAddBaseVM.setCheckReuse(0);
        // listBvgAddBaseVM.setCanhBao(false);3
        // listBvgAddBaseVM.setCollapse(false);
        // listBvgAddBaseVM.setLaNYCBH(true);
        // listBvgAddBaseVM.setInsuredName(insuranceRequest.getPv_insuredPersonFullName());
        // listBvgAddBaseVM.setIdPasswport(insuranceRequest.getPv_insuredPersonIdNumber());
        // listBvgAddBaseVM.setRelationship(listBvgAddBaseVM.getNguoidbhQuanhe());
        // QuocTich quocTich = new QuocTich();
        QuocTich quocTich = new QuocTich();
        quocTich.setQuocTichCode(insuranceRequest.getPv_insuredPersonNationalityCode());
        quocTich.setQuocTichName(insuranceRequest.getPv_insuredPersonNationality());
        listBvgAddBaseVM.setQuocTich(quocTich);
        // listBvgAddBaseVM.setSex("1");
        // ListBvgAddBaseVM[] list = new ListBvgAddBaseVM[1];
        ListBvgAddBaseVM[] list = new ListBvgAddBaseVM[1];
        list[0] = listBvgAddBaseVM;
        request.setListBvgAddBaseVM(list);
        request.setNguoiycCmnd(cfMast.get().getIdCode());
        request.setNguoiycName(cfMast.get().getFullName());
        request.setNguoiycNgaysinh(Utils.convertDateToString(cfMast.get().getDateOfBirth()));
        request.setPromoPercent(0);

        request.setReceiveMethod("1");
        // ReceiverUser receiverUser = new ReceiverUser();
        ReceiverUser receiverUser = new ReceiverUser();
        receiverUser.setAddress(cfMast.get().getAddress());
        receiverUser.setEmail(cfMast.get().getEmail());
        receiverUser.setMobile(cfMast.get().getMobileSms());
        receiverUser.setEmailHide(cfMast.get().getEmail());
        receiverUser.setMobileHide(cfMast.get().getMobileSms());
        receiverUser.setName(cfMast.get().getFullName());
        receiverUser.setNgaySinh(Utils.convertDateToString(cfMast.get().getDateOfBirth()));
        // AddressDistrictData addressDistrictData = new AddressDistrictData("00000000",
        // "Địa chỉ khác, Khác", "Khác");
        AddressDistrictData addressDistrictData = new AddressDistrictData("00000000", "Địa chỉ khác, Khác", "Khác");
        receiverUser.setAddressDistrictData(addressDistrictData);
        request.setReceiverUser(receiverUser);
        // SaleToEmp saleToEmp = new SaleToEmp("", "");
        SaleToEmp saleToEmp = new SaleToEmp("", "");
        request.setSaleToEmp(saleToEmp);

        request.setTangGiamKhac(0);
        request.setThoihanbhTu(insuranceRequest.getPv_startDate());
        request.setThoihanbhDen(
                java.time.LocalDate.now().plusMonths(12).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String date = insuranceRequest.getPv_startDate();
            // convert String to LocalDate
            LocalDate localDate = LocalDate.parse(date, formatter);
            request.setThoihanbhDen(localDate.plusMonths(12).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        } catch (Exception e) {
            throw new BusinessException("99", e.getMessage());
        }
        System.out.println(request.getThoihanbhDen());
        request.setConfirmMethod(0);
        request.setGycFiles(new AttachFile[1]);
        request.setTotalPremium(insuranceRequest.getPv_isTotalFee());
        // request.setInceptionDate(request.getThoihanbhTu());
        // request.setExpiredDate(request.getThoihanbhDen());
        request.setKenhPhanPhoi("MSB_CN");
        request.setContactCif("CIF_TUNA");
        request.setCheckTtskNdbh("0");
        request.setTotalPremium(insuranceRequest.getPv_isTotalFee());
        String status = "100";
        request.setStatusPolicy("100");

        // request.setGiftCodeAgencyDiscount(0);
        if (insuranceRequest.getQ1().equals("1")
                || insuranceRequest.getQ2().equals("1")
                || insuranceRequest.getQ3().equals("1")
                || insuranceRequest.getQ4().equals("1")) {

            request.setTtskCheck("1");
            insuranceRequest.setPv_isSick("1");
            request.setStatusPolicy("93");
            status = "93";
        } else {

            request.setTtskCheck("0");
            insuranceRequest.setPv_isSick("0");

            if (Utils.getAge(insuranceRequest.getPv_insuredPersonBirthDate()) < 6
                    || (insuranceRequest.getPv_isLifeFee().equals("1")
                            && insuranceRequest.getPv_isLifeFeeValue() == 300000000)) {
                request.setStatusPolicy("93");
                status = "93";
            }

        }

        request.setTtskCheck(insuranceRequest.getPv_isSick());
        request.setQ1(insuranceRequest.getQ1());
        request.setQ1Question(insuranceRequest.getQ1Qestion());
        request.setQ1QuestionDesc(insuranceRequest.getQ1Desciption());
        request.setQ1QuestionId(insuranceRequest.getQ1QuestionId());
        request.setQ1QuestionNote(insuranceRequest.getQ1QuestionNote());

        request.setQ2(insuranceRequest.getQ2());
        request.setQ2Question(insuranceRequest.getQ2Qestion());
        request.setQ2QuestionDesc(insuranceRequest.getQ2Desciption());
        request.setQ2QuestionId(insuranceRequest.getQ2QuestionId());
        request.setQ2QuestionNote(insuranceRequest.getQ2QuestionNote());

        request.setQ3(insuranceRequest.getQ3());
        request.setQ3Question(insuranceRequest.getQ3Qestion());
        request.setQ3QuestionDesc(insuranceRequest.getQ3Desciption());
        request.setQ3QuestionId(insuranceRequest.getQ3QuestionId());
        request.setQ3QuestionNote(insuranceRequest.getQ3QuestionNote());

        request.setQ4(insuranceRequest.getQ4());
        request.setQ4Question(insuranceRequest.getQ4Qestion());
        request.setQ4QuestionDesc(insuranceRequest.getQ4Desciption());
        request.setQ4QuestionId(insuranceRequest.getQ4QuestionId());
        request.setQ4QuestionNote(insuranceRequest.getQ4QuestionNote());

        NotifyEntity checkDk = notifyRepo.createInsurance(insuranceRequest.getPv_custId(),
                insuranceRequest.getPv_packageId(),
                insuranceRequest.getPv_startDate(),
                String.valueOf(insuranceRequest.getPv_fee()),
                insuranceRequest.getPv_beneficiaryFullName(),
                insuranceRequest.getPv_beneficiaryBirthDate(),
                insuranceRequest.getPv_beneficiaryIdNumber(),
                insuranceRequest.getPv_RelationId(),
                insuranceRequest.getPv_isSick(),
                insuranceRequest.getPv_isTreatedIn3Years(),
                insuranceRequest.getPv_isTreatedNext12Months(),
                insuranceRequest.getPv_isTreatedSpecialIn3Years(),
                insuranceRequest.getPv_isRejectInsurance(),
                insuranceRequest.getPv_isNormal(),
                insuranceRequest.getPv_isConfirm(),
                insuranceRequest.getPv_requireId(),
                insuranceRequest.getPv_insuredPersonFullName(),
                insuranceRequest.getPv_insuredPersonBirthDate(),
                insuranceRequest.getPv_insuredPersonGender(),
                insuranceRequest.getPv_insuredPersonIdNumber(),
                insuranceRequest.getPv_insuredPersonMobile(),
                insuranceRequest.getPv_insuredPersonEmail(),
                insuranceRequest.getPv_insuredPersonAddress(),
                insuranceRequest.getPv_ParentInsuranceCode(),
                insuranceRequest.getPv_InsuredRelationId(),
                insuranceRequest.getPv_insuredPersonNationality(),

                "",

                "",

                "",

                "",

                "",
                "111",
                "",
                String.valueOf((int) insuranceRequest.getPv_isAccidentFeeValue()),
                String.valueOf((int) insuranceRequest.getPv_isLifeFeeValue()),
                "",
                "",
                "insuranceRequest.getContent()");

        logger.info("===> check đủ điều kiện" + checkDk.getDes());

        if (!checkDk.getPStatus().equals("01")) {
            throw new BusinessException("01", checkDk.getDes());
        }

        if (!request.getStatusPolicy().equals("93")) {
            try {
                WithdrawEntity withdraw = withdrawRepo.subtractBalance(insuranceRequest.getPv_custId(),
                        roundUP(insuranceRequest.getPv_fee()), "22");
                if (withdraw.getPStatus().equalsIgnoreCase("01")) {
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    headers.set("Authorization", "Bearer " + getToken());
                    JSONObject jsonObject = new JSONObject(request);
                    logger.info("[Create Insurance request]: " + jsonObject.toString());
                    HttpEntity<String> requestHttp = new HttpEntity<String>(jsonObject.toString(), headers);
                    ResponseEntity<String> responseEntityStr = restTemplate.postForEntity(BV_PARTNER_URI, requestHttp,
                            String.class);
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode root;
                    root = mapper.readTree(responseEntityStr.getBody());
                    insuranceRequest.setPv_requireId(root.get("gycbhNumber").asText());

                    BvPartner response = new BvPartner();
                    response = Converter.fromJsonString(responseEntityStr.getBody());

                    if (request.getStatusPolicy().equalsIgnoreCase("100")) {
                        notifyPayment("3Gang", insuranceRequest.getPv_requireId(), insuranceRequest.getPv_fee());
                    }

                    String outPatientTotal = "0";
                    String dentistryTotal = "0";
                    String pregnantTotal = "0";
                    if (!insuranceRequest.getPv_isOutPatientFee().equals("0")) {
                        if (insuranceRequest.getPv_packageId().equals("1")) {
                            outPatientTotal = listPackage.get(0).getOutPatientFee();
                        }
                        if (insuranceRequest.getPv_packageId().equals("2")) {
                            outPatientTotal = listPackage.get(1).getOutPatientFee();
                        }
                        if (insuranceRequest.getPv_packageId().equals("3")) {
                            outPatientTotal = listPackage.get(2).getOutPatientFee();
                        }
                        if (insuranceRequest.getPv_packageId().equals("4")) {
                            outPatientTotal = listPackage.get(3).getOutPatientFee();
                        }
                        if (insuranceRequest.getPv_packageId().equals("5")) {
                            outPatientTotal = listPackage.get(4).getOutPatientFee();
                        }

                    }

                    if (!insuranceRequest.getPv_isDentistryFee().equals("0")) {

                        if (insuranceRequest.getPv_packageId().equals("1")) {
                            dentistryTotal = listPackage.get(0).getDentistryFee();
                        }
                        if (insuranceRequest.getPv_packageId().equals("2")) {
                            dentistryTotal = listPackage.get(1).getDentistryFee();
                        }
                        if (insuranceRequest.getPv_packageId().equals("3")) {
                            dentistryTotal = listPackage.get(2).getDentistryFee();
                        }
                        if (insuranceRequest.getPv_packageId().equals("4")) {
                            dentistryTotal = listPackage.get(3).getDentistryFee();
                        }
                        if (insuranceRequest.getPv_packageId().equals("5")) {
                            dentistryTotal = listPackage.get(4).getDentistryFee();
                        }
                    }

                    if (!insuranceRequest.getPv_isPregnantFee().equals("0")) {

                        if (insuranceRequest.getPv_packageId().equals("1")) {
                            pregnantTotal = listPackage.get(0).getPregnantFee();
                        }
                        if (insuranceRequest.getPv_packageId().equals("2")) {
                            pregnantTotal = listPackage.get(1).getPregnantFee();
                        }
                        if (insuranceRequest.getPv_packageId().equals("3")) {
                            pregnantTotal = listPackage.get(2).getPregnantFee();
                        }
                        if (insuranceRequest.getPv_packageId().equals("4")) {
                            pregnantTotal = listPackage.get(3).getPregnantFee();
                        }
                        if (insuranceRequest.getPv_packageId().equals("5")) {
                            pregnantTotal = listPackage.get(4).getPregnantFee();
                        }

                    }

                    NotifyEntity notify = notifyRepo.createInsurance(insuranceRequest.getPv_custId(),
                            insuranceRequest.getPv_packageId(),
                            insuranceRequest.getPv_startDate(),
                            String.valueOf(insuranceRequest.getPv_fee()),
                            insuranceRequest.getPv_beneficiaryFullName(),
                            insuranceRequest.getPv_beneficiaryBirthDate(),
                            insuranceRequest.getPv_beneficiaryIdNumber(),
                            insuranceRequest.getPv_RelationId(),
                            insuranceRequest.getPv_isSick(),
                            insuranceRequest.getPv_isTreatedIn3Years(),
                            insuranceRequest.getPv_isTreatedNext12Months(),
                            insuranceRequest.getPv_isTreatedSpecialIn3Years(),
                            insuranceRequest.getPv_isRejectInsurance(),
                            insuranceRequest.getPv_isNormal(),
                            insuranceRequest.getPv_isConfirm(),
                            insuranceRequest.getPv_requireId(),
                            insuranceRequest.getPv_insuredPersonFullName(),
                            insuranceRequest.getPv_insuredPersonBirthDate(),
                            insuranceRequest.getPv_insuredPersonGender(),
                            insuranceRequest.getPv_insuredPersonIdNumber(),
                            insuranceRequest.getPv_insuredPersonMobile(),
                            insuranceRequest.getPv_insuredPersonEmail(),
                            insuranceRequest.getPv_insuredPersonAddress(),
                            insuranceRequest.getPv_ParentInsuranceCode(),
                            insuranceRequest.getPv_InsuredRelationId(),
                            insuranceRequest.getPv_insuredPersonNationality(),

                            String.valueOf(response.getListBvgAddBaseVM().get(0).getNgoaitruPhi()),

                            String.valueOf(response.getListBvgAddBaseVM().get(0).getTncnPhi()),

                            String.valueOf(response.getListBvgAddBaseVM().get(0).getSmcnPhi()),

                            String.valueOf(response.getListBvgAddBaseVM().get(0).getNhakhoaPhi()),

                            String.valueOf(response.getListBvgAddBaseVM().get(0).getThaisanPhi()),
                            status,
                            outPatientTotal,
                            String.valueOf((int) insuranceRequest.getPv_isAccidentFeeValue()),
                            String.valueOf((int) insuranceRequest.getPv_isLifeFeeValue()),
                            dentistryTotal,
                            pregnantTotal,
                            "insuranceRequest.getContent()");

                    logger.info("===>" + notify.getDes());
                    return response(toResult(response));
                } else {
                    throw new BusinessException("01",
                            "Số dư không đủ");
                }

            } catch (Exception err) {
                logger.info(insuranceRequest.getPv_custId() + " ERROR " + err.getMessage());
                throw new BusinessException("01",
                        getErrMessage(err.getMessage()));
            }

        } else {
            NotifyEntity notify = notifyRepo.createInsurance(insuranceRequest.getPv_custId(),
                    insuranceRequest.getPv_packageId(),
                    insuranceRequest.getPv_startDate(),
                    String.valueOf(insuranceRequest.getPv_fee()),
                    insuranceRequest.getPv_beneficiaryFullName(),
                    insuranceRequest.getPv_beneficiaryBirthDate(),
                    insuranceRequest.getPv_beneficiaryIdNumber(),
                    insuranceRequest.getPv_RelationId(),
                    insuranceRequest.getPv_isSick(),
                    insuranceRequest.getPv_isTreatedIn3Years(),
                    insuranceRequest.getPv_isTreatedNext12Months(),
                    insuranceRequest.getPv_isTreatedSpecialIn3Years(),
                    insuranceRequest.getPv_isRejectInsurance(),
                    insuranceRequest.getPv_isNormal(),
                    insuranceRequest.getPv_isConfirm(),
                    insuranceRequest.getPv_requireId(),
                    insuranceRequest.getPv_insuredPersonFullName(),
                    insuranceRequest.getPv_insuredPersonBirthDate(),
                    insuranceRequest.getPv_insuredPersonGender(),
                    insuranceRequest.getPv_insuredPersonIdNumber(),
                    insuranceRequest.getPv_insuredPersonMobile(),
                    insuranceRequest.getPv_insuredPersonEmail(),
                    insuranceRequest.getPv_insuredPersonAddress(),
                    insuranceRequest.getPv_ParentInsuranceCode(),
                    insuranceRequest.getPv_InsuredRelationId(),
                    insuranceRequest.getPv_insuredPersonNationality(),

                    "",

                    "",

                    "",

                    "",

                    "",
                    status,
                    "",
                    String.valueOf((int) insuranceRequest.getPv_isAccidentFeeValue()),
                    String.valueOf((int) insuranceRequest.getPv_isLifeFeeValue()),
                    "",
                    "",
                    "insuranceRequest.getContent()");

            logger.info("===>" + notify.getDes());

            String error = "Chương trình bảo hiểm không áp dụng với khách hàng: \n"
                    + "\n"
                    + " 1. Dưới 6 tuổi\n"
                    + " 2. Kê khai bệnh\n"
                    + " 3. Quyền lợi sinh mạng cá nhân bằng 300 triệu.\n";

            if (notify.getPStatus().equals("000")) {
                throw new BusinessException("01", notify.getDes());
            } else {
                throw new BusinessException("01", error);
            }

        }

        // throw new BusinessException("01", "Số dư không đủ để thực hiện giao dịch!");

        // return response(toResult(createPolicy_Partner(request,
        // insuranceRequest.getPv_custId())));
    }

    @Override
    public ResponseEntity<?> paymentInsurance(String pv_insuranceId) {
        System.out.println("pv_insuranceId:   " + pv_insuranceId);
        NotifyEntity notify = notifyRepo.paymentInsurance(pv_insuranceId);
        if (!notify.getPStatus().equals("01")) {
            throw new BusinessException(notify.getPStatus(), notify.getDes());
        }
        return response(toResult(notify));
    }

    @Override
    public ResponseEntity<?> withdrawMoney(String cid, String amt) {
        NotifyEntity notify = notifyRepo.withdrawMoney(cid, amt);
        if (!notify.getPStatus().equals("01")) {
            throw new BusinessException(notify.getPStatus(), notify.getDes());
        }
        return response(toResult(notify));
    }

    @Override
    public ResponseEntity<?> updateRisk(String rId) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + getToken());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("gycbhNumber", rId);
            HttpEntity<String> request = new HttpEntity<String>(jsonObject.toString(), headers);
            ResponseEntity<String> responseEntityStr = restTemplate.postForEntity(BV_ODER_INFO_URI, request,
                    String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root;
            root = mapper.readTree(responseEntityStr.getBody());
            String policyId = root.get("statusPolicyId").asText();
            notifyRepo.updateRisk(policyId, rId);
            return response(toResult("Thông báo được gửi thành công"));
        } catch (JSONException err) {
            throw new BusinessException("111", "Parse JSON fail");
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return response(toResult("Thông báo được gửi thành công"));
    }

    public void notifyPayment(String paymentCode, String gycbhNo, double paymentFee) {
        try {
            List<String> lstGycbh = new ArrayList<>();
            lstGycbh.add(gycbhNo);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + getToken());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("bvTxnRef", paymentCode);
            jsonObject.put("gycbhNumber", lstGycbh);
            jsonObject.put("paymentFee", paymentFee);

            logger.info(jsonObject.toString());

            HttpEntity<String> request = new HttpEntity<String>(jsonObject.toString(), headers);
            ResponseEntity<String> responseEntityStr = restTemplate.postForEntity(BV_NOTIFY_PAYMENT, request,
                    String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root;
            root = mapper.readTree(responseEntityStr.getBody());

        } catch (JSONException err) {
            throw new BusinessException("111", "Parse JSON fail");
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        logger.info("OK");
    }

    @Override
    public ResponseEntity<?> getInsuranceList(String cid) {
        ArrayList<InsuranceList> list = (ArrayList<InsuranceList>) insuranceListRepository.getInsuranceList(cid);
        if (list.size() == 0)
            throw new BusinessException(Constants.FAIL, ErrorCode.NO_DATA_DESCRIPTION);
        return response(toResult(list));
    }

}
