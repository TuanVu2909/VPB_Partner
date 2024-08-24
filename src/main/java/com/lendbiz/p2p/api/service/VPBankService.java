package com.lendbiz.p2p.api.service;

import com.lendbiz.p2p.api.entity.bank.VPBControlEntity;
import com.lendbiz.p2p.api.request.VPBbankRequest;
import com.lendbiz.p2p.api.response.VPBank.ExternalTransferDTO;
import com.lendbiz.p2p.api.response.VPBank.TranferDTO;
import com.lendbiz.p2p.api.response.VPBank.VPBResDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import java.util.List;

public interface VPBankService {
    // BD so du
    VPBResDTO transFluctuations(VPBbankRequest request, String signature);

    ResponseEntity<?> getVPBToken(String headerAuthorization, MultiValueMap<String, String> form_data_urlencoded);

    ResponseEntity<?> getBankList(String headerAuthorization, String headerIdn_app, String headerX_request_id);

    // danh sach cac chi nhanh ngan hang
    ResponseEntity<?> getBranchList(String headerAuthorization, String headerIdn_app, String headerX_request_id, String bankId);

    // truy vấn thông tin người thụ hưởng
    ResponseEntity<?> getBeneficiaryInfo(String headerAuthorization, String headerIdn_app, String headerX_request_id, String bankCode, String benNumber, String benType);

    // truy vấn thông tin giao dịch
    ResponseEntity<?> getFTListInfo(String headerAuthorization, String headerIdn_app, String headerX_request_id, String accountNumber, String referenceNumber);

    // truy vấn thông tin số dư tài khoản chuyên chi
    ResponseEntity<?> getPartnerAccountInfo(String headerAuthorization, String headerIdn_app, String headerX_request_id, String accountNumber);

    // chuyen khoan noi bo VPB
    ResponseEntity<?> internalTransfer(String headerAuthorization, String headerIdn_app, String headerX_request_id, TranferDTO data);

    // chuyen khoan lien Ngan hang
    ResponseEntity<?> externalTransfer(String headerAuthorization, String headerIdn_app, String headerX_request_id, ExternalTransferDTO data);

    String sign(String plainText);

    List<VPBControlEntity> getLogs();

    String writeFile();

    ResponseEntity<?> sendFileSFTP();

    VPBResDTO testConnectDatabase(String ft);
}
