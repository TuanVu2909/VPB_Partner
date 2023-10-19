/***************************************************************************
 * Copyright 2018 by VIETIS - All rights reserved.                *    
 **************************************************************************/
package com.lendbiz.p2p.api.service.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lendbiz.p2p.api.constants.ErrorCode;
import com.lendbiz.p2p.api.entity.CfMast;
import com.lendbiz.p2p.api.exception.BusinessException;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;
import com.lendbiz.p2p.api.producer.ProducerMessage;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class BaseService {



//	public BaseService(Environment env) {
//	}

	protected static Logger logger = LoggerFactory.getLogger(BaseService.class);

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

	public static String getCustId(List<CfMast> lstCfmast) {
		List<CfMast> newLstCfmast = new ArrayList<>();
		String custId = null;
		if (lstCfmast.size() > 1) {
			lstCfmast.forEach((n) -> {
				try {
					if (n.getStatus().equalsIgnoreCase("A")) {
						newLstCfmast.add(n);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			if (newLstCfmast.size() > 0) {
				custId = newLstCfmast.get(0).getCustid();
			} else {
				custId = lstCfmast.get(0).getCustid();
			}
		} else if (lstCfmast.size() == 1) {
			custId = lstCfmast.get(0).getCustid();
		}
		return custId;
	}

	public static Timestamp convertStringToTimestamp(String strDate) {
		String pattern = "ddMMyyyy HH:mm:ss";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		LocalDateTime localDateTime = LocalDateTime.from(formatter.parse(strDate));
		Timestamp timestamp = Timestamp.valueOf(localDateTime);
		return timestamp;
	}
}
