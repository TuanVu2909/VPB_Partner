package com.lendbiz.p2p.api.controller;

import com.lendbiz.p2p.api.constants.ErrorCode;
import com.lendbiz.p2p.api.request.VPBbankRequest;
import com.lendbiz.p2p.api.response.VPBank.VPBResDTO;
import com.lendbiz.p2p.api.service.VPBankService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/v1")
@Log4j2
@CrossOrigin(origins = "*")
public class VPBankController {

    @Autowired
    VPBankService vpBankService;

    @PostMapping("/notification")
    public VPBResDTO VPBankNotification (
            HttpServletRequest httpServletRequest,
            @RequestBody @Valid VPBbankRequest request
    ) {

        if(httpServletRequest.getHeader("signature") == null || "".equals(httpServletRequest.getHeader("signature"))) {
            return new VPBResDTO("400", ErrorCode.INVALID_DATA_REQUEST, "header 'signature' is not empty !", request.getTransactionId());
        }
        String signature = httpServletRequest.getHeader("signature");
        return vpBankService.transFluctuations(request, signature);
    }
}
