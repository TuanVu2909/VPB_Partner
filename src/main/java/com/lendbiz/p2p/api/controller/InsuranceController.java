package com.lendbiz.p2p.api.controller;

import com.lendbiz.p2p.api.entity.Input9Pay;
import com.lendbiz.p2p.api.entity.Premium;
import com.lendbiz.p2p.api.exception.BusinessException;
import com.lendbiz.p2p.api.request.CreatePolicyPartnerRq;
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
//    @GetMapping("/ins/test")
//    public ResponseEntity<?> test(HttpServletRequest httpServletRequest)
//            throws BusinessException, UnsupportedEncodingException {
//
//        return insuranceService.premium();
//
//    }
    @PostMapping("/ins/createPolicy-Partner")
    public ResponseEntity<?> createPolicyPartner(HttpServletRequest httpServletRequest, @RequestBody CreatePolicyPartnerRq rq)
            throws BusinessException, UnsupportedEncodingException {
        return insuranceService.createPolicy_Partner(rq);
    }
    @PostMapping("/ins/premium")
    public ResponseEntity<?> premium(HttpServletRequest httpServletRequest, @RequestBody Premium rq)
            throws BusinessException, UnsupportedEncodingException {
        return insuranceService.premium(rq);
    }
    @GetMapping("/ins/get-by-gycbhNumber")
    public ResponseEntity<?> getByGycbhNumber(HttpServletRequest httpServletRequest, @RequestParam String gId)
            throws BusinessException, UnsupportedEncodingException {
        return insuranceService.getByGycbhNumber(gId);
    }
    @GetMapping("/ins/get-insurance-price")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getInsurancePackagePrice(HttpServletRequest httpServletRequest, @RequestParam("pid") String pid,
                                                      @RequestParam("age")String age)
            throws BusinessException, UnsupportedEncodingException {
        return insuranceService.getInsurancePackagePrice(pid,age);
    }
    @GetMapping("/ins/download-file-oder")
    @Transactional(readOnly = true)
    public ResponseEntity<?> downloadFileOder(HttpServletRequest httpServletRequest, @RequestParam("gid") String gid,
                                                      @RequestParam("type")String type)
            throws BusinessException, UnsupportedEncodingException {
        return insuranceService.downloadFileOder(gid,type);
    }

    @GetMapping("/ins/get-insurance-addition-price")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getInsuranceAdditionPrice(HttpServletRequest httpServletRequest, @RequestParam("pid") String pid,
                                                      @RequestParam("age")String age)
            throws BusinessException, UnsupportedEncodingException {
        return insuranceService.getInsuranceAdditionPrice(pid,age);
    }
}
