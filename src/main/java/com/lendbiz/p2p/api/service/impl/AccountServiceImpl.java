package com.lendbiz.p2p.api.service.impl;

import com.lendbiz.p2p.api.entity.VerifyAccountInput;
import com.lendbiz.p2p.api.repository.PackageFilterRepository;
import com.lendbiz.p2p.api.response.BaseResponse;
import com.lendbiz.p2p.api.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl extends BaseResponse<AccountService> implements AccountService {
    @Autowired
    PackageFilterRepository pfr;

    @Override
    public ResponseEntity<?> verifyAcc(VerifyAccountInput input) {
        try {
            return response(toResult(pfr.verifyAcc(input)));
        }catch (Exception e){
            return response(toResult("201",e.getMessage(),input));
        }
    }
}
