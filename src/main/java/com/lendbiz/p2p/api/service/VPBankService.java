package com.lendbiz.p2p.api.service;

import com.lendbiz.p2p.api.request.GetBankNameRequest;
import org.springframework.http.ResponseEntity;

public interface VPBankService {
    ResponseEntity<?> transFluctuations(String requestId, GetBankNameRequest request);
}
