package com.lendbiz.p2p.api.controller;

import com.lendbiz.p2p.api.request.VPBbankRequest;
import com.lendbiz.p2p.api.service.VPBankService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/lendbiz/api/v1")
@Log4j2
@CrossOrigin(origins = "*")
public class VPBankController {

    @Autowired
    VPBankService vpBankService;

    @PostMapping("/notification")
    public ResponseEntity<?> getToken (
            HttpServletRequest httpServletRequest,
            @RequestBody VPBbankRequest request
    ) {
        //String requestId = httpServletRequest.getHeader("RequestId");
        return vpBankService.transFluctuations(request);
    }
}
