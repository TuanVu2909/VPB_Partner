package com.lendbiz.p2p.api.controller;

import com.lendbiz.p2p.api.entity.Input9Pay;
import com.lendbiz.p2p.api.exception.BusinessException;
import com.lendbiz.p2p.api.service.InsuranceService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/lendbiz")
@Log4j2
public class InsuranceController {
    @Autowired
    InsuranceService insuranceService;
    @GetMapping("/ins/test")
    public ResponseEntity<?> test(HttpServletRequest httpServletRequest)
            throws BusinessException, UnsupportedEncodingException {


        return insuranceService.premium();

    }

}
