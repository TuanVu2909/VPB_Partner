package com.lendbiz.p2p.api.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface VNPTService {
    ResponseEntity<?> vertifyIdentity(MultipartFile imgFrontId, MultipartFile imgBackId, String mobile);
    ResponseEntity<?> vertifySelfie(MultipartFile imgFrontId, MultipartFile imgSelfie, String mobile);
}