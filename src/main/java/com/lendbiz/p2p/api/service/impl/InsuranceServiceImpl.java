package com.lendbiz.p2p.api.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lendbiz.p2p.api.constants.Constants;
import com.lendbiz.p2p.api.constants.ErrorCode;
import com.lendbiz.p2p.api.entity.*;
import com.lendbiz.p2p.api.exception.BusinessException;
import com.lendbiz.p2p.api.repository.*;
import com.lendbiz.p2p.api.request.CreatePolicyPartnerRequest;
import com.lendbiz.p2p.api.request.CreatePolicyPartnerRq;
import com.lendbiz.p2p.api.request.InsuranceRequest;
import com.lendbiz.p2p.api.request.baovietEntity.AddressDistrictData;
import com.lendbiz.p2p.api.request.baovietEntity.ImgGks;
import com.lendbiz.p2p.api.request.baovietEntity.InvoiceInfo;
import com.lendbiz.p2p.api.request.baovietEntity.ListBvgAddBaseVM;
import com.lendbiz.p2p.api.request.baovietEntity.QuocTich;
import com.lendbiz.p2p.api.request.baovietEntity.ReceiverUser;
import com.lendbiz.p2p.api.request.baovietEntity.SaleToEmp;
import com.lendbiz.p2p.api.response.BaseResponse;
import com.lendbiz.p2p.api.service.InsuranceService;
import com.lendbiz.p2p.api.utils.Utils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InsuranceServiceImpl extends BaseResponse<InsuranceService> implements InsuranceService {
    static String BV_TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJsZW5kYml6QGJhb3ZpZXQuY29tLnZuIiwiYXV0aCI6IlBFUk1fQUdSRUVNRU5UX0NSRUFURSxQRVJNX0FHUkVFTUVOVF9ERUxFVEUsUEVSTV9BR1JFRU1FTlRfRURJVCxQRVJNX0FHUkVFTUVOVF9WSUVXLFBFUk1fQ0FSVF9DUkVBVEUsUEVSTV9DQVJUX0RFTEVURSxQRVJNX0NBUlRfRURJVCxQRVJNX0NBUlRfVklFVyxQRVJNX0NPTlRBQ1RfQ1JFQVRFLFBFUk1fQ09OVEFDVF9ERUxFVEUsUEVSTV9DT05UQUNUX0VESVQsUEVSTV9DT05UQUNUX1ZJRVcsUEVSTV9QQVlfQ0hVWUVOX1RIVSxQRVJNX1BBWV9LSEFDSF9IQU5HX1RULFBFUk1fUEFZX1RIQU5IVE9BTl9TQVUsUEVSTV9QQVlfVk5QQVksUEVSTV9QUk9EVUNUX0JWR19DUkVBVEUsUEVSTV9QUk9EVUNUX0JWR19ERUxFVEUsUEVSTV9QUk9EVUNUX0JWR19FRElULFBFUk1fUFJPRFVDVF9CVkdfVklFVyxQRVJNX1JFUE9SVF9DT01NSVNTSU9OX1ZJRVcsUEVSTV9SRVBPUlRfSU5DT01FX1ZJRVcsUEVSTV9SRVBPUlRfTFYxLFBFUk1fUkVQT1JUX1RSQU5TRkVSX1ZJRVcsUk9MRV9BRE1JTixST0xFX0FHRU5DWSxST0xFX1JFUE9SVF9BR0VOQ1kiLCJleHAiOjE2NzAxMjIzNjB9.MD2o0C5EShRZdsQVj9Hy3axf-0uIhWPeBGC0ha0nvCmv_m7n0RPa4ii8LyycF8mq9kz7ouKsTQFoWDT0AMS-_w";
    static String BV_PREMIUM_URI = "https://agency-api-dev1.baoviet.com.vn/api/agency/product/bvg/premium";
    static String BV_PARTNER_URI = "https://agency-api-dev1.baoviet.com.vn/api/agency/product/bvg/createPolicy-Partner";
    static String BV_ODER_DOWNLOAD_URI = "https://agency-api-dev1.baoviet.com.vn/api/agency/document/download-file-agreement";
    static String BV_ODER_INFO_URI = "https://agency-api-dev1.baoviet.com.vn/api/agency/product/agreement/get-by-gycbhNumber";
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

    @Override
    public ResponseEntity<?> premium(Premium premium) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + BV_TOKEN);
            JSONObject jsonObject = new JSONObject(premium);

            HttpEntity<String> request = new HttpEntity<String>(jsonObject.toString(), headers);
            ResponseEntity<String> responseEntityStr = restTemplate.postForEntity(BV_PREMIUM_URI, request,
                    String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root;
            try {
                root = mapper.readTree(responseEntityStr.getBody());
                return response(toResult(root));
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
    public ResponseEntity<?> createPolicy_Partner(CreatePolicyPartnerRq rq) {

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + BV_TOKEN);
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
            headers.set("Authorization", "Bearer " + BV_TOKEN);
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
            headers.set("Authorization", "Bearer " + BV_TOKEN);
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

    @Override
    public ResponseEntity<?> createInsurance(InsuranceRequest insuranceRequest) {
        String age = String.valueOf(Utils.getAge(insuranceRequest.getPv_beneficiaryBirthDate()));
        List<InsuranceAdditionPrice> listAdd = insuranceAdditionPriceRepository
                .getInsuranceAdditionPrice(insuranceRequest.getPv_packageId(), age);
        InsurancePrice insurancePrice = insurancePriceRepository
                .getInsurancePackagePrice(insuranceRequest.getPv_packageId(), age).get(0);
        Optional<CfMast> cfMast = cfMastRepository.findByCustid(insuranceRequest.getPv_custId());
        CreatePolicyPartnerRequest request = new CreatePolicyPartnerRequest();
        long sumPrice = Long.parseLong(insurancePrice.getPrice());
        request.setGuaranteeCard("0");
        request.setSoNguoiThamGia(1);
        request.setContactAddress("71 nsl");
        request.setContactCategoryType("PERSON");
        request.setContactCode("15606");
        request.setContactDob(insuranceRequest.getPv_beneficiaryBirthDate());
        request.setContactEmail(cfMast.get().getEmail());
        request.setContactIdNumber(insuranceRequest.getPv_beneficiaryIdNumber());
        request.setContactName(insuranceRequest.getPv_beneficiaryFullName());
        request.setContactPhone(cfMast.get().getPhone());
        request.setDepartmentId("A000009455");
        request.setDiscount(0);
        request.setHasGks(false);
        request.setHasNguoinhantien(false);
        request.setHasNguoithuhuong(false);
        request.setHasTThoadonGTG(false);

        request.setIsShowDgrr("false");
        request.setInsuranceTarget("New");
        // InvoiceInfo invoiceInfo = new InvoiceInfo();
        InvoiceInfo invoiceInfo = new InvoiceInfo();
        invoiceInfo.setCheck("0");
        request.setInvoiceInfo(invoiceInfo);
        request.setLineId("BVG");
        // ListBvgAddBaseVM listBvgAddBaseVM = new ListBvgAddBaseVM();
        ListBvgAddBaseVM listBvgAddBaseVM = new ListBvgAddBaseVM();

        listBvgAddBaseVM.setChuongTrinhBh(insuranceRequest.getPv_packageId());
        listBvgAddBaseVM.setDiscount(0);
        listBvgAddBaseVM.setHasExtracare(false);
        listBvgAddBaseVM.setHasNguoithuhuong(false);
        // ImgGks imgGks = new ImgGks("", "", "", "");
        ImgGks imgGks = new ImgGks();
        listBvgAddBaseVM.setImgGks(imgGks);

        listBvgAddBaseVM.setNgoaitruChk(insuranceRequest.getPv_isOutPatientFee());
        listBvgAddBaseVM.setNgoaitruPhi(Long.parseLong(listAdd.get(0).getPrice()));
        if (insuranceRequest.getPv_isOutPatientFee().equals("0")) {
            listBvgAddBaseVM.setNgoaitruPhi(0);
        }
        listBvgAddBaseVM.setNguoidbhCmnd(insuranceRequest.getPv_insuredPersonIdNumber());
        listBvgAddBaseVM.setNguoidbhGioitinh(insuranceRequest.getPv_insuredPersonGender());
        listBvgAddBaseVM.setNguoidbhName(insuranceRequest.getPv_insuredPersonFullName());
        listBvgAddBaseVM.setNguoidbhNgaysinh(insuranceRequest.getPv_insuredPersonBirthDate());
        switch (insuranceRequest.getPv_InsuredRelationId()) {
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
            listBvgAddBaseVM.setSmcnSotienbh("0");
        }
        listBvgAddBaseVM.setSmcnSotienbhTemp("0");
        listBvgAddBaseVM.setTanggiamPhi(0);
        listBvgAddBaseVM.setIsShowPersonList("1");
        listBvgAddBaseVM.setThaisanChk("0");
        listBvgAddBaseVM.setThaisanPhi(0);
        listBvgAddBaseVM.setTncnChk(insuranceRequest.getPv_isAccidentFee());
        float accident = Float.parseFloat(listAdd.get(1).getPrice());
        accident = accident * sumPrice;
        listBvgAddBaseVM.setTncnPhi(accident);
        listBvgAddBaseVM.setTncnSotienbh(insuranceRequest.getPv_isAccidentFeeValue());
        if (insuranceRequest.getPv_isAccidentFee().equals("0")) {
            listBvgAddBaseVM.setTncnPhi(0);
            listBvgAddBaseVM.setTncnSotienbh("0");
        }
        listBvgAddBaseVM.setTncnSotienbhTemp("0");
        listBvgAddBaseVM.setTongPhiBH(sumPrice);
        listBvgAddBaseVM.setTuoi(26);

        listBvgAddBaseVM.setSerial("WwhpI6Jy");
        listBvgAddBaseVM.setInsuranceTarget("New");
        listBvgAddBaseVM.setLoading(0);
        listBvgAddBaseVM.setPersonOrder(1);
        listBvgAddBaseVM.setCheckReuse("0");
        listBvgAddBaseVM.setCanhBao(false);
        listBvgAddBaseVM.setCollapse(false);
        listBvgAddBaseVM.setLaNYCBH(true);
        listBvgAddBaseVM.setInsuredName(insuranceRequest.getPv_insuredPersonFullName());
        listBvgAddBaseVM.setIdPasswport(insuranceRequest.getPv_insuredPersonIdNumber());
        listBvgAddBaseVM.setRelationship(listBvgAddBaseVM.getNguoidbhQuanhe());
        // QuocTich quocTich = new QuocTich();
        QuocTich quocTich = new QuocTich();
        quocTich.setQuocTichCode("12");
        quocTich.setQuocTichName("Việt Nam");
        listBvgAddBaseVM.setQuocTich(quocTich);
        listBvgAddBaseVM.setSex("1");
        // ListBvgAddBaseVM[] list = new ListBvgAddBaseVM[1];
        ListBvgAddBaseVM[] list = new ListBvgAddBaseVM[1];
        list[0] = listBvgAddBaseVM;
        request.setListBvgAddBaseVM(list);
        request.setNguoiycCmnd(insuranceRequest.getPv_beneficiaryIdNumber());
        request.setNguoiycName(insuranceRequest.getPv_beneficiaryFullName());
        request.setNguoiycNgaysinh(insuranceRequest.getPv_beneficiaryBirthDate());
        request.setPercentId(0);
        request.setTtskCheck("0");
        request.setQ1("0");
        request.setQ2("0");
        request.setQ3("0");
        request.setReceiveMethod("1");
        // ReceiverUser receiverUser = new ReceiverUser();
        ReceiverUser receiverUser = new ReceiverUser();
        receiverUser.setAddress(cfMast.get().getAddress());
        receiverUser.setEmail(cfMast.get().getEmail());
        receiverUser.setMobile(cfMast.get().getMobileSms());
        receiverUser.setEmailHide(cfMast.get().getEmail());
        receiverUser.setMobileHide(cfMast.get().getMobileSms());
        receiverUser.setName(cfMast.get().getFullName());
        receiverUser.setNgaySinh(insuranceRequest.getPv_beneficiaryBirthDate());
        // AddressDistrictData addressDistrictData = new AddressDistrictData("00000000",
        // "Địa chỉ khác, Khác", "Khác");
        AddressDistrictData addressDistrictData = new AddressDistrictData("00000000", "Địa chỉ khác, Khác", "Khác");
        receiverUser.setAddressDistrictData(addressDistrictData);
        request.setReceiverUser(receiverUser);
        // SaleToEmp saleToEmp = new SaleToEmp("", "");
        SaleToEmp saleToEmp = new SaleToEmp("", "");
        request.setSaleToEmp(saleToEmp);
        request.setStatusPolicy("100");
        request.setTanggiamPhi(0);
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
        request.setGycFiles(new Object[1]);
        request.setTotalPremium(insuranceRequest.getPv_isTotalFee());
        request.setInceptionDate(request.getThoihanbhTu());
        request.setExpiredDate(request.getThoihanbhDen());
        request.setKenhPhanPhoi("MSB_CN");
        request.setContactCif("CIF_TUNA");
        request.setCheckTtskNdbh("0");
        request.setTongPhiBH(insuranceRequest.getPv_isTotalFee());

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + BV_TOKEN);
            JSONObject jsonObject = new JSONObject(request);
            System.out.println(jsonObject);
            HttpEntity<String> requestHttp = new HttpEntity<String>(jsonObject.toString(), headers);
            ResponseEntity<String> responseEntityStr = restTemplate.postForEntity(BV_PARTNER_URI, requestHttp,
                    String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root;
            root = mapper.readTree(responseEntityStr.getBody());
            insuranceRequest.setPv_requireId(root.get("gycbhNumber").asText());
            notifyRepo.createInsurance(insuranceRequest.getPv_custId(),
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
                    insuranceRequest.getPv_isOutPatientFee(),
                    insuranceRequest.getPv_isAccidentFee(),
                    insuranceRequest.getPv_isLifeFee(),
                    insuranceRequest.getPv_isDentistryFee(),
                    insuranceRequest.getPv_isPregnantFee());
            return response(toResult(root));
        } catch (JSONException err) {
            throw new BusinessException("111", "Parse JSON fail");
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;

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
            headers.set("Authorization", "Bearer " + BV_TOKEN);
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

    @Override
    public ResponseEntity<?> getInsuranceList(String cid) {
        ArrayList<InsuranceList> list = (ArrayList<InsuranceList>) insuranceListRepository.getInsuranceList(cid);
        if (list.size() == 0)
            throw new BusinessException(Constants.FAIL, ErrorCode.NO_DATA_DESCRIPTION);
        return response(toResult(list));
    }

}
