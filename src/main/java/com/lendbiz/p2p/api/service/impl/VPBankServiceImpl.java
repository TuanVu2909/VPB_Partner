package com.lendbiz.p2p.api.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lendbiz.p2p.api.configs.RSA.CipherUtility;
import com.lendbiz.p2p.api.constants.Constants;
import com.lendbiz.p2p.api.constants.ErrorCode;
import com.lendbiz.p2p.api.entity.KeysManageEntity;
import com.lendbiz.p2p.api.exception.BusinessException;
import com.lendbiz.p2p.api.repository.KeysManageRepository;
import com.lendbiz.p2p.api.repository.VPBankRepository;
import com.lendbiz.p2p.api.request.VPBbankRequest;
import com.lendbiz.p2p.api.response.BaseResponse;
import com.lendbiz.p2p.api.response.VPBank.VPBResDTO;
import com.lendbiz.p2p.api.service.VPBankService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.security.PrivateKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Log4j2
public class VPBankServiceImpl extends BaseResponse<VPBankService> implements VPBankService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private VPBankRepository vpBankRepository;

    @Autowired
    private CipherUtility cipherUtility;

    @Autowired
    private KeysManageRepository keysManageRepository;

    @Override
    public VPBResDTO transFluctuations(VPBbankRequest request, String signature) {
        boolean checkSuccess = this.checkSignature(request, signature);
        if(checkSuccess){
            System.out.println("Xác thực thành công");
            try {
                vpBankRepository.insertVPBTrans(
                        request.getMasterAccountNumber(),
                        request.getVirtualAccountNumber(),
                        request.getVirtualName(),
                        request.getVirtualAlkey(),
                        request.getAmount(),
                        request.getBookingDate(),
                        request.getTransactionDate(),
                        request.getTransactionId(),
                        request.getRemark(),
                        signature
                );
            }
            catch(Exception e){
                System.out.println("error => "+e);
                return new VPBResDTO("400", ErrorCode.EXCEPTION_ERROR, e.getMessage(), request.getTransactionId());
            }
        return new VPBResDTO("200", "", "", request.getTransactionId());
        }
        else {
            System.out.println("Xác thực không thành công");
            return new VPBResDTO("400", "", "Xác thực không thành công", request.getTransactionId());
        }
    }

    private boolean checkSignature(VPBbankRequest request, String signature) {
        String plain = "456 5000 0100";
        List<KeysManageEntity> keysManage = keysManageRepository.getAllKeyVPBank();
        try {
            String priKeyStr = keysManage.get(0).getPrivate_key();
            PrivateKey priKey = cipherUtility.decodePrivateKey(priKeyStr);

            System.out.println("chuoi ma hoa: " + signature);

            // Decrypt plain as a text.
            String plainContent = cipherUtility.decrypt(signature, priKey);
            System.out.println("chuoi da duoc giai ma: " + plainContent);
            String x[] = plainContent.split(" ");
            if(
                request.getMasterAccountNumber().equals(x[0]) &&
                request.getAmount().equals(x[1]) &&
                request.getTransactionId().equals(x[2])
            ) { return true; }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public ResponseEntity<?> getVPBToken() {

        HttpHeaders headers = new HttpHeaders();
        Map<String, Object> bodies = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", Constants.VPB_BASIC_AUTHEN);

        bodies.put("scope", "make_internal_transfer init_payments_data_read make_external_fund_transfer own_trasfer_history_read");
        bodies.put("grant_type", "client_credentials");

        HttpEntity<?> request = new HttpEntity(bodies, headers);
        JsonNode root = null;

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    Constants.VPB_URL + "/security/token",
                    HttpMethod.POST,
                    request,
                    String.class,
                    (Object) null);
            root = mapper.readTree(responseEntity.getBody());
        }
        catch (Exception e) {
            throw new BusinessException(Constants.FAIL, Constants.MESSAGE_FAIL);
        }

        if(root == null) {
            throw new BusinessException(ErrorCode.UNKNOWN_ERROR, ErrorCode.UNKNOWN_ERROR_DESCRIPTION);
        }
        return response(toResult(Constants.SUCCESS, Constants.MESSAGE_SUCCESS, root));
    }
}
