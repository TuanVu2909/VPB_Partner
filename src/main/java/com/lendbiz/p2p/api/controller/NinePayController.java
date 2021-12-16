package com.lendbiz.p2p.api.controller;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lendbiz.p2p.api.entity.NinePayResult;
import com.lendbiz.p2p.api.exception.BusinessException;
import com.lendbiz.p2p.api.request.Create9PayRequest;
import com.lendbiz.p2p.api.service.NinePayService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/lendbiz")
@Log4j2
public class NinePayController {

	@Autowired
	NinePayService ninepayService;

	@PostMapping("/9pay/create")
	public ResponseEntity<?> createNinePayUrl(HttpServletRequest httpServletRequest,
			@RequestHeader("requestId") String requestId,
			@RequestBody Create9PayRequest request) throws UnsupportedEncodingException {
		log.info("[" + requestId + "] << createNinePayUrl >>");

		return ninepayService.create9Payment(request);
	}

	@PostMapping("/9pay/decode")
	public ResponseEntity<?> decodeNinePayResult(HttpServletRequest httpServletRequest,
			@RequestHeader("requestId") String requestId, @RequestBody String encodeString)
			throws BusinessException, UnsupportedEncodingException {
		log.info("[" + requestId + "] << decodeNinePayResult >>");
		return ninepayService.decode9Payment(encodeString);

	}

	@RequestMapping("/9pay/success")
	public ModelAndView thanhtoan(@RequestParam("result") String result,
			@RequestParam("checksum") String checksum) throws JsonMappingException, JsonProcessingException {
		// System.out.println(result + "_______" + checksum);

		String byteArray = result;

		byte[] decodedBytes = Base64.getDecoder().decode(byteArray);
		String decodedString = new String(decodedBytes);

		ObjectMapper mapper = new ObjectMapper();
		// Map<String, Object> map = mapper.readValue(decodedString, Map.class);

		// JsonNode rootNode = mapper.readTree(decodedString);

		NinePayResult res = mapper.readValue(decodedString, NinePayResult.class);

		System.out.println(res.toString());

		ModelAndView modelAndView = new ModelAndView("success-signature");
		return modelAndView;
	}

}
