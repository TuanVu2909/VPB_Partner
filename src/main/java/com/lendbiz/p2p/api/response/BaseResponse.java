package com.lendbiz.p2p.api.response;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import com.lendbiz.p2p.api.constans.Constans;
import com.lendbiz.p2p.api.exception.NotFoundException;
import com.lendbiz.p2p.api.utils.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/***********************************************************************  
 *  
 *   @package：com.lendbiz.p2p.api.response，@class-name：BaseResponse.java   
 *     
 *   @copyright       Copyright:   2021-2022     
 *   @creator         Hoang Thanh Tu <br/>   
 *   @create-time   Apr 9, 2021   10:55:55 AM   
 *
 ***********************************************************************/
public abstract class BaseResponse<S> {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	protected <T> ResponseEntity<ApiResponse<T>> response(ApiResponse<T> response) {
		if (response == null) {
			throw new IllegalArgumentException("Please set responseBean.");
		}

		if (StringUtil.isEmty(response.getStatus())) {
			response.setStatus(Constans.SUCCESS);
		}
		return new ResponseEntity<ApiResponse<T>>(response, HttpStatus.OK);
	}

	protected ResponseEntity<byte[]> response(String fileName, byte[] file) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition",
				"attachment; filename*=UTF-8''" + URLEncoder.encode(fileName, StandardCharsets.UTF_8.name()));

		// Create response
		return ResponseEntity.ok().headers(headers).contentLength(file.length)
				.contentType(MediaType.parseMediaType("application/octet-stream")).body(file);
	}

	protected <T> ApiResponse<T> toResult(Optional<T> optional) {
		if (!optional.isPresent())
			throw new NotFoundException();
		return toResult(optional.get());
	}

	protected <T> ApiResponse<T> toResult(T t) {
		ApiResponse<T> response = new ApiResponse<T>();
		response.setStatus(Constans.SUCCESS);
		response.setMessage(Constans.MESSAGE_SUCCESS);
		response.setData(t);
		return response;
	}

	protected <T> ApiResponse<T> toResult(String status, String message, T t) {
		ApiResponse<T> response = new ApiResponse<T>();
		response.setStatus(status);
		response.setMessage(message);
		response.setData(t);
		return response;
	}

	protected <T> ApiResponse<T> toErrorResult(String status, String message, String errorCode, String errorDetail) {
		ApiResponse<T> response = new ApiResponse<T>();
		ErrorInfo errorInfo = new ErrorInfo(errorCode, errorDetail);
		response.setStatus(status);
		response.setMessage(message);
		response.setErrorInfo(errorInfo);
		return response;
	}

}
