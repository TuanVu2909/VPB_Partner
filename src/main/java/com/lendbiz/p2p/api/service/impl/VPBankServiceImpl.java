package com.lendbiz.p2p.api.service.impl;

import com.lendbiz.p2p.api.configs.RSA.CipherUtility;
import com.lendbiz.p2p.api.constants.Constants;
import com.lendbiz.p2p.api.constants.ErrorCode;
import com.lendbiz.p2p.api.entity.KeysManageEntity;
import com.lendbiz.p2p.api.repository.KeysManageRepository;
import com.lendbiz.p2p.api.repository.VPBankRepository;
import com.lendbiz.p2p.api.request.VPBbankRequest;
import com.lendbiz.p2p.api.response.ApiResponse;
import com.lendbiz.p2p.api.response.BaseResponse;
import com.lendbiz.p2p.api.response.VPBank.VPBResDTO;
import com.lendbiz.p2p.api.service.VPBankService;
import com.lendbiz.p2p.api.service.base.BaseService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.sql.Timestamp;
import java.util.List;

@Service
@Log4j2
public class VPBankServiceImpl extends BaseResponse<VPBankService> implements VPBankService {

    @Autowired
    private VPBankRepository vpBankRepository;

    @Autowired
    private CipherUtility cipherUtility;

    @Autowired
    private KeysManageRepository keysManageRepository;

    @Override
    public VPBResDTO transFluctuations(VPBbankRequest request, String signature) {
        this.testRSA();
//        try {
//            vpBankRepository.insertVPBTrans(
//                    request.getMasterAccountNumber(),
//                    request.getVirtualAccountNumber(),
//                    request.getVirtualName(),
//                    request.getVirtualAlkey(),
//                    request.getAmount(),
//                    request.getBookingDate(),
//                    request.getTransactionDate(),
//                    request.getTransactionId(),
//                    request.getRemark(),
//                    signature
//            );
//        }
//        catch(Exception e){
//            System.out.println("error => "+e);
//            return new VPBResDTO("400", ErrorCode.EXCEPTION_ERROR, e.getMessage(), request.getTransactionId());
//        }
//        return new VPBResDTO("200", "", "", request.getTransactionId());
        return null;
    }

    private void testRSA() {
        String plain = "456 5000";
        KeyPair keyPair = cipherUtility.getKeyPair();

        String pub

        List<KeysManageEntity> keysManage = keysManageRepository.getAllKeyVPBank();
        try {
            String pubKeyStr = keysManage.get(0).getPublic_key();
            System.out.println("pubKeyStr: " + pubKeyStr);
            PublicKey pubKey = cipherUtility.decodePublicKey(pubKeyStr);

            String priKeyStr = keysManage.get(0).getPrivate_key();
            System.out.println("priKeyStr: " + priKeyStr);
            PrivateKey priKey = cipherUtility.decodePrivateKey(priKeyStr);


            // Encrypt plain as a cipher.
            String cipherContent = cipherUtility.encrypt(plain, pubKey);
            System.out.println("chuoi da duoc ma hoa: " + cipherContent);

            // Decrypt plain as a text.
            String plainContent = cipherUtility.decrypt(cipherContent, priKey);
            System.out.println("chuoi da duoc giai ma: " + plainContent);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
