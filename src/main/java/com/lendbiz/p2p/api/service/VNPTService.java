package com.lendbiz.p2p.api.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface VNPTService {
    String uploadImage(MultipartFile image, String title, String description);
    JsonNode compareImage(String hashImgFrontId, String hashImgSelfie, String mobile);
    ResponseEntity<?> extractOCR(String hashImgFrontId, String hashImgBackId, String hashImgSelfie);
}