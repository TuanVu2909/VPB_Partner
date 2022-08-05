package com.lendbiz.p2p.api.controller;

import com.lendbiz.p2p.api.exception.BusinessException;
import com.lendbiz.p2p.api.service.InsuranceService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/partner/api")
@Log4j2
public class PartnerInsuranceController {
    @Autowired
    InsuranceService insuranceService;

    @GetMapping("/bv/insurance/baoviet-notify")
    @Transactional(readOnly = true)
    public ResponseEntity<?> notifyBv(HttpServletRequest httpServletRequest,
                                      @RequestParam("pn") String pn, @RequestParam("type") String type, @RequestParam("key") String key)
            throws BusinessException {
        pn = pn.replace(";","");
        return insuranceService.updateRisk(pn);
    }

}
