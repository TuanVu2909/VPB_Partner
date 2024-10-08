package com.lendbiz.p2p.api.service;

import com.lendbiz.p2p.api.entity.Premium;
import com.lendbiz.p2p.api.request.CreatePolicyPartnerRq;
import com.lendbiz.p2p.api.request.InsuranceRequest;
import org.springframework.http.ResponseEntity;

public interface InsuranceService {
    public ResponseEntity<?> premium(Premium premium);
    public ResponseEntity<?> createPolicy_Partner(CreatePolicyPartnerRq rq);
    public ResponseEntity<?> getByGycbhNumber(String gycbhNumber);
    public ResponseEntity<?> getInsurancePackagePrice(String pkgId, String age);
    public ResponseEntity<?> downloadFileOder(String gid, String type);
    public ResponseEntity<?> getInsuranceAdditionPrice(String pkgId, String age);
    public ResponseEntity<?> createInsurance(InsuranceRequest insuranceRequest);
    public ResponseEntity<?> paymentInsurance(String pv_insuranceId);
    public ResponseEntity<?> withdrawMoney(String cid, String amt);
    public ResponseEntity<?> updateRisk( String rId);
    public ResponseEntity<?> getInsuranceList( String cid);
}
