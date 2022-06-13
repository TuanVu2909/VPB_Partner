package com.lendbiz.p2p.api.controller;

import com.lendbiz.p2p.api.entity.Input9Pay;
import com.lendbiz.p2p.api.entity.Premium;
import com.lendbiz.p2p.api.exception.BusinessException;
import com.lendbiz.p2p.api.request.CreatePolicyPartnerRq;
import com.lendbiz.p2p.api.request.InsuranceRequest;
import com.lendbiz.p2p.api.service.InsuranceService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/lendbiz")
@Log4j2
public class InsuranceController {
    @Autowired
    InsuranceService insuranceService;
//    @GetMapping("/insurance/test")
//    public ResponseEntity<?> test(HttpServletRequest httpServletRequest)
//            throws BusinessException, UnsupportedEncodingException {
//
//        return insuranceService.premium();
//
//    }
    @PostMapping("/insurance/createPolicy-Partner")
    public ResponseEntity<?> createPolicyPartner(HttpServletRequest httpServletRequest, @RequestBody CreatePolicyPartnerRq rq)
            throws BusinessException, UnsupportedEncodingException {
        return insuranceService.createPolicy_Partner(rq);
    }
    @PostMapping("/insurance/premium")
    public ResponseEntity<?> premium(HttpServletRequest httpServletRequest, @RequestBody Premium rq)
            throws BusinessException, UnsupportedEncodingException {
        return insuranceService.premium(rq);
    }
    @GetMapping("/insurance/get-by-gycbhNumber")
    public ResponseEntity<?> getByGycbhNumber(HttpServletRequest httpServletRequest, @RequestParam String gId)
            throws BusinessException, UnsupportedEncodingException {
        return insuranceService.getByGycbhNumber(gId);
    }
    @GetMapping("/insurance/get-insurance-price")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getInsurancePackagePrice(HttpServletRequest httpServletRequest, @RequestParam("pid") String pid,
                                                      @RequestParam("age")String age)
            throws BusinessException, UnsupportedEncodingException {
        return insuranceService.getInsurancePackagePrice(pid,age);
    }
    @GetMapping("/insurance/download-file-oder")
    @Transactional(readOnly = true)
    public ResponseEntity<?> downloadFileOder(HttpServletRequest httpServletRequest, @RequestParam("gid") String gid,
                                                      @RequestParam("type")String type)
            throws BusinessException, UnsupportedEncodingException {
        return insuranceService.downloadFileOder(gid,type);
    }

    @GetMapping("/insurance/get-insurance-addition-price")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getInsuranceAdditionPrice(HttpServletRequest httpServletRequest, @RequestParam("pid") String pid,
                                                      @RequestParam("age")String age)
            throws BusinessException, UnsupportedEncodingException {
        return insuranceService.getInsuranceAdditionPrice(pid,age);
    }

    @PostMapping("/insurance/create-insurance")
    @Transactional(readOnly = true)
    public ResponseEntity<?> createInsurance(HttpServletRequest httpServletRequest,
                                             @RequestHeader("requestId") String requestId, @RequestBody InsuranceRequest request)
            throws BusinessException {
        log.info("[" + requestId + "] << createInsurance >>");

        return insuranceService.createInsurance(request);
    }
    @GetMapping("/insurance/payment-insurance")
    @Transactional(readOnly = true)
    public ResponseEntity<?> paymentInsurance(HttpServletRequest httpServletRequest,
                                             @RequestHeader("requestId") String requestId,@RequestParam("iid") String iId)
            throws BusinessException {
        log.info("[" + requestId + "] << paymentInsurance >>");

        return insuranceService.paymentInsurance(iId);
    }
    @GetMapping("/insurance/withdraw-money")
    @Transactional(readOnly = true)
    public ResponseEntity<?> withdrawMoney(HttpServletRequest httpServletRequest,
                                              @RequestHeader("requestId") String requestId,@RequestParam("cid") String cid,@RequestParam("amt") String amt)
            throws BusinessException {
        log.info("[" + requestId + "] << paymentInsurance >>");
        return insuranceService.withdrawMoney(cid,amt);
    }
    @GetMapping("/insurance/baoviet-notify")
    @Transactional(readOnly = true)
    public ResponseEntity<?> notifyBv(HttpServletRequest httpServletRequest,
                                          @RequestParam("pn") String pn,@RequestParam("type") String type,@RequestParam("key") String key)
            throws BusinessException {
        pn = pn.replace(";","");
        return insuranceService.updateRisk(pn);
    }

}
