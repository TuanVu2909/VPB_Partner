package com.lendbiz.p2p.api.service.impl;

import com.lendbiz.p2p.api.request.GetBankNameRequest;
import com.lendbiz.p2p.api.response.BaseResponse;
import com.lendbiz.p2p.api.service.VPBankService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class VPBankServiceImpl extends BaseResponse<VPBankService> implements VPBankService{
    @Override
    public ResponseEntity<?> transFluctuations(String requestId, GetBankNameRequest request) {
        return null;
    }
}
