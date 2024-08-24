package com.lendbiz.p2p.api.controller;

import com.lendbiz.p2p.api.constants.ErrorCode;
import com.lendbiz.p2p.api.entity.bank.VPBControlEntity;
import com.lendbiz.p2p.api.entity.bank.VPBankEntity;
import com.lendbiz.p2p.api.repository.VPBankRepository;
import com.lendbiz.p2p.api.request.VPBbankRequest;
import com.lendbiz.p2p.api.response.VPBank.ExternalTransferDTO;
import com.lendbiz.p2p.api.response.VPBank.TranferDTO;
import com.lendbiz.p2p.api.response.VPBank.VPBLogsDTO;
import com.lendbiz.p2p.api.response.VPBank.VPBResDTO;
import com.lendbiz.p2p.api.service.VPBankService;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1")
@Log4j2
@CrossOrigin(origins = "*")
@Transactional
public class VPBankController {

    @Autowired
    VPBankService vpBankService;
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private VPBankRepository vpBankRepository;

    @GetMapping("/testPing")
    public String testPing () {
        return "Ping to server getVPBToken_3 !";
    }

    @GetMapping("/testPingDB")
    public VPBResDTO testPingDB (@RequestHeader("ft") String ft) {
        return vpBankService.testConnectDatabase(ft);
    }

    @GetMapping("/getLogs")
    public List<VPBControlEntity> getLogs() {
        return  vpBankService.getLogs();
    }

    @GetMapping("/fileWriter")
    public String fileWriter() {
        return  vpBankService.writeFile();
    }

    //================================================

    @PostMapping("/notification")
    public VPBResDTO VPBankNotification (
            HttpServletRequest httpServletRequest,
            @RequestBody @Valid VPBbankRequest request
    ) {
        logger.info("REQUEST FROM VPBANK [API_NOTIFICATION]\n"+request.toString());
        try {
            VPBankEntity vpBankEntity = vpBankRepository.insertLogs(request.toString(), null, "42.112.38.103 REQUEST FROM VPBANK [API_NOTIFICATION]");
            logger.info(vpBankEntity.getDes());
        } catch(Exception e) {
            logger.info("EXCEPTION FROM VPBANK [API_NOTIFICATION]\n"+e.getMessage());
        }

        if(httpServletRequest.getHeader("signature") == null || "".equals(httpServletRequest.getHeader("signature"))) {
            return new VPBResDTO("400", ErrorCode.INVALID_DATA_REQUEST, "header 'signature' is not empty !", request.getTransactionId());
        }
        String signature = httpServletRequest.getHeader("signature");
        return vpBankService.transFluctuations(request, signature);
    }

    @PostMapping("/getVPBToken")
    public ResponseEntity<?> getVPBToken (
            @RequestHeader("Authorization") String headerAuthorization,
            @RequestBody MultiValueMap<String, String> form_data_urlencoded
    ) {
        return vpBankService.getVPBToken(headerAuthorization, form_data_urlencoded);
    }

    @GetMapping("/getBankList")
    public ResponseEntity<?> getBankList (
            @RequestHeader("Authorization") String headerAuthorization,
            @RequestHeader("IDN-App") String headerIdn_app,
            @RequestHeader("x-request-id") String headerX_request_id

    ) {
        return vpBankService.getBankList(headerAuthorization, headerIdn_app, headerX_request_id);
    }

    @GetMapping("/getBranchList")
    public ResponseEntity<?> getBranchList (
            @RequestHeader("Authorization") String headerAuthorization,
            @RequestHeader("IDN-App") String headerIdn_app,
            @RequestHeader("x-request-id") String headerX_request_id,
            @RequestHeader("bankId") String bankId
    ) {
        return vpBankService.getBranchList(headerAuthorization, headerIdn_app, headerX_request_id, bankId);
    }

    @GetMapping("/getBeneficiaryInfo")
    public ResponseEntity<?> getBeneficiaryInfo (
            @RequestHeader("Authorization") String headerAuthorization,
            @RequestHeader("IDN-App") String headerIdn_app,
            @RequestHeader("x-request-id") String headerX_request_id,
            @RequestHeader("bankCode") String bankCode,
            @RequestHeader("benNumber") String benNumber,
            @RequestHeader("benType") String benType // internal, external
    ) {
        return vpBankService.getBeneficiaryInfo(headerAuthorization, headerIdn_app, headerX_request_id, bankCode, benNumber, benType);
    }

    @GetMapping("/getFTListInfo")
    public ResponseEntity<?> getFTListInfo (
            @RequestHeader("Authorization") String headerAuthorization,
            @RequestHeader("IDN-App") String headerIdn_app,
            @RequestHeader("x-request-id") String headerX_request_id,
            @RequestHeader("accountNumber") String accountNumber,
            @RequestHeader("referenceNumber") String referenceNumber
    ) {
        return vpBankService.getFTListInfo(headerAuthorization, headerIdn_app, headerX_request_id, accountNumber, referenceNumber);
    }

    @GetMapping("/getPartnerAccountInfo")
    public ResponseEntity<?> getPartnerAccountInfo (
            @RequestHeader("Authorization") String headerAuthorization,
            @RequestHeader("IDN-App") String headerIdn_app,
            @RequestHeader("x-request-id") String headerX_request_id,
            @RequestHeader("accountNumber") String accountNumber
    ) {
        return vpBankService.getPartnerAccountInfo(headerAuthorization, headerIdn_app, headerX_request_id, accountNumber);
    }

    @PostMapping("/internalTransfer")
    public ResponseEntity<?> internalTransfer (
            @RequestHeader("Authorization") String headerAuthorization,
            @RequestHeader("IDN-App") String headerIdn_app,
            @RequestHeader("x-request-id") String headerX_request_id,
            @RequestBody TranferDTO data
    ) {
        return vpBankService.internalTransfer(headerAuthorization, headerIdn_app, headerX_request_id, data);
    }

    @PostMapping("/externalTransfer")
    public ResponseEntity<?> externalTransfer (
            @RequestHeader("Authorization") String headerAuthorization,
            @RequestHeader("IDN-App") String headerIdn_app,
            @RequestHeader("x-request-id") String headerX_request_id,
            @RequestBody ExternalTransferDTO data
    ) {
        return vpBankService.externalTransfer(headerAuthorization, headerIdn_app, headerX_request_id, data);
    }

    @GetMapping("/getSign")
    public ResponseEntity<?> getSign (@RequestHeader("plainText") String plainText) {
        System.out.println("plainText: "+plainText);
        return new ResponseEntity<>(vpBankService.sign(plainText), HttpStatus.OK);
    }
}
