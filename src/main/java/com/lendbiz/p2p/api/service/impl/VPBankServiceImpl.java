package com.lendbiz.p2p.api.service.impl;

import com.lendbiz.p2p.api.entity.bank.VPBankEntity;
import com.lendbiz.p2p.api.request.VPBbankRequest;
import com.lendbiz.p2p.api.response.BaseResponse;
import com.lendbiz.p2p.api.service.VPBankService;
import com.lendbiz.p2p.api.service.base.BaseService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@Log4j2
public class VPBankServiceImpl extends BaseResponse<VPBankService> implements VPBankService{

    @Override
    public ResponseEntity<?> transFluctuations(VPBbankRequest request) {

        String x = "11092023 14:10:10";
        Timestamp y = BaseService.convertStringToTimestamp(x);

        VPBankEntity vpBankEntity = new VPBankEntity();
        //vpBankEntity.set

        System.out.println(y);
        return null;
    }
}
