package com.lendbiz.p2p.api.service;

import com.lendbiz.p2p.api.request.VPBbankRequest;
import org.springframework.http.ResponseEntity;

public interface VPBankService {

    ResponseEntity<?> transFluctuations(VPBbankRequest request);

}
