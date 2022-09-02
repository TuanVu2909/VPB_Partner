package com.lendbiz.p2p.api.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface FilesStorageService {

	public String init(String key, String custId);

	public String save(MultipartFile file);

	public ResponseEntity<?> uploadFile(MultipartFile file, String key, String custId);
}
