package com.lendbiz.p2p.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface VNPTService {
    ResponseEntity<?> vertifyIdentity(MultipartFile imgFrontId, MultipartFile imgBackId) throws JsonProcessingException;
    ResponseEntity<?> vertifySelfie(MultipartFile imgFrontId, MultipartFile imgSelfie);
}