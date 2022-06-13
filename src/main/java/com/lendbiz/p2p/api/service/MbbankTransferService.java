package com.lendbiz.p2p.api.service;

import com.lendbiz.p2p.api.entity.Premium;
import com.lendbiz.p2p.api.request.CreatePolicyPartnerRq;
import com.lendbiz.p2p.api.request.GetBankNameRequest;
import com.lendbiz.p2p.api.response.AccesToken;
import com.lendbiz.p2p.api.response.MbAccessToken;

import org.springframework.http.ResponseEntity;

public interface MbbankTransferService {
    public ResponseEntity<?> getToken();

    public ResponseEntity<?> getBankName(GetBankNameRequest request);

    // public ResponseEntity<?> createPolicy_Partner(CreatePolicyPartnerRq rq);

    // public ResponseEntity<?> getByGycbhNumber(String gycbhNumber);

    // public ResponseEntity<?> getInsurancePackagePrice(String pkgId, String age);

    // public ResponseEntity<?> downloadFileOder(String gid, String type);

    // public ResponseEntity<?> getInsuranceAdditionPrice(String pkgId, String age);
}
