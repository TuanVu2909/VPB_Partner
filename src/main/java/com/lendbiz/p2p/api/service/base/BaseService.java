/***************************************************************************
 * Copyright 2018 by VIETIS - All rights reserved.                *    
 **************************************************************************/
package com.lendbiz.p2p.api.service.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lendbiz.p2p.api.constants.ErrorCode;
import com.lendbiz.p2p.api.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class BaseService {

	public BaseService(Environment env) {
	}

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	public static ByteArrayResource convertFile(MultipartFile sourceImage) {
		ByteArrayResource resource = null;
		try {
			resource = new ByteArrayResource(sourceImage.getBytes()) {
				@Override
				public String getFilename() {
					return sourceImage.getOriginalFilename();
				}
			};
		} catch (IOException e) {
			throw new BusinessException(ErrorCode.FAILED_TO_FILE, ErrorCode.FAILED_TO_FILE_DESCRIPTION);
		}

		return resource;
	}

	public static JsonNode stringToRoot (String msg) throws JsonProcessingException {
		JsonNode result = null;
		for (int i=0; i<msg.length(); i++){
			if(msg.charAt(i) == '{'){
				String sub = msg.substring(i);
				result = new ObjectMapper().readTree(sub);
				break;
			}
		}
		return result;
	}
}
