package com.lendbiz.p2p.api.service;

import com.lendbiz.p2p.api.request.VPBbankRequest;
import com.lendbiz.p2p.api.response.VPBank.VPBResDTO;

public interface VPBankService {
    VPBResDTO transFluctuations(VPBbankRequest request, String signature);
}
