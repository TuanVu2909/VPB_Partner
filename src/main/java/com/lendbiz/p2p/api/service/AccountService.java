package com.lendbiz.p2p.api.service;

import com.lendbiz.p2p.api.entity.VerifyAccountInput;
import com.lendbiz.p2p.api.request.LoginRequest;
import org.springframework.http.ResponseEntity;

public interface AccountService {

    public ResponseEntity<?> verifyAcc(VerifyAccountInput input);
}
