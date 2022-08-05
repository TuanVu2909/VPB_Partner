//package com.lendbiz.p2p.api.controller.base;
//
//import java.net.URLEncoder;
//import java.nio.charset.StandardCharsets;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//
//import com.lendbiz.p2p.api.constans.ErrorCode;
//import com.lendbiz.p2p.api.response.ApiResponse;
//import com.lendbiz.p2p.api.utils.StringUtil;
//
//public abstract class BaseController {
//	/*-----------------------------------------------
//	  * Property
//	  *-----------------------------------------------*/
//	  /**
//	   * Logger
//	   */
//	  protected Logger logger = LoggerFactory.getLogger(this.getClass());
//
//	  /*-----------------------------------------------
//	  * Protected
//	  *-----------------------------------------------*/
//
//	  /**
//	   * Create response
//	   *
//	   * @param response
//	   * @return response
//	   */
//
//	  
//	  protected <T> ResponseEntity<ApiResponse<T>> response(ApiResponse<T> response) {
//	      if (response == null) {
//	        throw new IllegalArgumentException("Please set responseBean.");
//	      }
//
//	      if (StringUtil.isEmty(response.getStatus())) {
//	        response.setStatus(ErrorCode.SUCCESS);
//	      }
//	      return new ResponseEntity<ApiResponse<T>>(response, HttpStatus.OK);
//	    }
//
//	  /**
//	   * create file response
//	   *
//	   * @param fileName file name
//	   * @param file byte array
//	   * @return response
//	   * @throws Exception
//	   */
//	  protected ResponseEntity<byte[]> response(String fileName, byte[] file)
//	      throws Exception {
//	    HttpHeaders headers = new HttpHeaders();
//	    headers.add("Content-Disposition", "attachment; filename*=UTF-8''"
//	        + URLEncoder.encode(fileName, StandardCharsets.UTF_8.name()));
//
//	    // Create response
//	    return ResponseEntity.ok().headers(headers).contentLength(file.length)
//	        .contentType(
//	            MediaType.parseMediaType("application/octet-stream"))
//	        .body(file);
//	  }
//
//}
