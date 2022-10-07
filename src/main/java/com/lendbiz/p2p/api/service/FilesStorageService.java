package com.lendbiz.p2p.api.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface FilesStorageService {

	public String init(String key, String custId);

	public String initContracts(String mobilesms);

	public String save(MultipartFile file);

	public void saveContract(byte[] file, String path);

	public ResponseEntity<?> uploadFile(MultipartFile file, String key, String custId);
}
