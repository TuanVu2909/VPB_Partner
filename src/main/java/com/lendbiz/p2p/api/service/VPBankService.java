package com.lendbiz.p2p.api.service;

import com.lendbiz.p2p.api.request.VPBbankRequest;
import com.lendbiz.p2p.api.response.VPBank.VPBResDTO;
import org.springframework.http.ResponseEntity;

public interface VPBankService {
    VPBResDTO transFluctuations(VPBbankRequest request, String signature);
    ResponseEntity<?> getVPBToken();
}
