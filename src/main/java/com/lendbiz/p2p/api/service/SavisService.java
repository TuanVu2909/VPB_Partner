package com.lendbiz.p2p.api.service;

import java.util.Optional;

import com.lendbiz.p2p.api.request.SignContractRequest;
import com.lendbiz.p2p.api.request.SignContractRequestV2;
import com.lendbiz.p2p.api.response.AccesToken;
import com.lendbiz.p2p.api.response.InfoIdentity;
import com.lendbiz.p2p.api.response.OtpResponse;
import com.lendbiz.p2p.api.response.SignPdfResponse;
import com.lendbiz.p2p.api.response.UserRegisterResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface SavisService {

	AccesToken getToken();

	public ResponseEntity<?> callPredict(MultipartFile file, InfoIdentity identity, int type);

	ResponseEntity<?> callCheckSelfie(MultipartFile frontId, MultipartFile selfie, String custId);

	Optional<UserRegisterResponse> callRegisterKyc(MultipartFile frontId);

	Optional<OtpResponse> getOtp(String docId);

	Boolean validateOtp(String otp,String docId);

	Optional<SignPdfResponse> signPdf(MultipartFile contract, SignContractRequest request);

	Optional<SignPdfResponse> signContract(MultipartFile contract, SignContractRequestV2 request);

}
