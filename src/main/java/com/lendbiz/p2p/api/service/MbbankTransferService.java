package com.lendbiz.p2p.api.service;

import org.springframework.http.ResponseEntity;

import com.lendbiz.p2p.api.request.ConvertIdCardRequest;
import com.lendbiz.p2p.api.request.GetBankNameRequest;
import com.lendbiz.p2p.api.request.TransferMBRequest;

public interface MbbankTransferService {
    public ResponseEntity<?> getToken();

    public ResponseEntity<?> getBankName(String requestId, GetBankNameRequest request);

    public String convertIdCard(ConvertIdCardRequest convertRequest);

    public ResponseEntity<?> transfer(String requestId, TransferMBRequest transferRequest);

}
