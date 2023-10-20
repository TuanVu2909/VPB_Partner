package com.lendbiz.p2p.api.service.impl;

import com.lendbiz.p2p.api.constants.Constants;
import com.lendbiz.p2p.api.constants.ErrorCode;
import com.lendbiz.p2p.api.repository.VPBankRepository;
import com.lendbiz.p2p.api.request.VPBbankRequest;
import com.lendbiz.p2p.api.response.ApiResponse;
import com.lendbiz.p2p.api.response.BaseResponse;
import com.lendbiz.p2p.api.response.VPBank.VPBResDTO;
import com.lendbiz.p2p.api.service.VPBankService;
import com.lendbiz.p2p.api.service.base.BaseService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;

@Service
@Log4j2
public class VPBankServiceImpl extends BaseResponse<VPBankService> implements VPBankService {

    @Autowired
    private VPBankRepository vpBankRepository;

    @Override
    public VPBResDTO transFluctuations(VPBbankRequest request, String signature) {

        try{
            vpBankRepository.insertVPBTrans(
                    request.getMasterAccountNumber(),
                    request.getVirtualAccountNumber(),
                    request.getVirtualName(),
                    request.getVirtualAlkey(),
                    request.getAmount(),
                    request.getBookingDate(),
                    request.getTransactionDate(),
                    request.getTransactionId(),
                    request.getRemark(),
                    signature
            );
        }
        catch(Exception e){
            System.out.println("error => "+e);
            return new VPBResDTO("400", ErrorCode.EXCEPTION_ERROR, e.getMessage(), request.getTransactionId());
        }
        return new VPBResDTO("200", "", "", request.getTransactionId());
    }
}
