package com.lendbiz.p2p.api.service;

import java.io.UnsupportedEncodingException;

import com.lendbiz.p2p.api.entity.Input9Pay;
import com.lendbiz.p2p.api.request.Create9PayRequest;

import org.springframework.http.ResponseEntity;

public interface NinePayService {

    public ResponseEntity<?> create9Payment(Create9PayRequest request) throws UnsupportedEncodingException;

    public ResponseEntity<?> decode9Payment(String encodeString) throws UnsupportedEncodingException;
    public ResponseEntity<?> getCardProducts(String serviceId) ;
    public ResponseEntity<?> buyCard(Input9Pay input9Pay);
    public ResponseEntity<?> findTransaction(String cId);
    public ResponseEntity<?> balance();

}
