package com.lendbiz.p2p.api.service;

import java.util.Optional;

import com.lendbiz.p2p.api.request.SignContractRequest;
import com.lendbiz.p2p.api.request.SignContractRequestV2;
import com.lendbiz.p2p.api.response.AccesToken;
import com.lendbiz.p2p.api.response.InfoIdentity;
import com.lendbiz.p2p.api.response.OtpResponse;
import com.lendbiz.p2p.api.response.SignPdfResponse;
import com.lendbiz.p2p.api.response.UserRegisterResponse;

import org.springframework.web.multipart.MultipartFile;

public interface SavisService {

	AccesToken getToken();

	Optional<InfoIdentity> callPredict(MultipartFile file, InfoIdentity identity, String type);

	Boolean callCheckSelfie(MultipartFile frontId, MultipartFile selfie, String threshold);
	
	Optional<UserRegisterResponse> callRegisterKyc(MultipartFile frontId);

	Optional<OtpResponse> getOtp();

	Boolean validateOtp(String otp);
	
	Optional<SignPdfResponse> signPdf(MultipartFile contract, SignContractRequest request);

	Optional<SignPdfResponse> signContract(MultipartFile contract, SignContractRequestV2 request);

}
