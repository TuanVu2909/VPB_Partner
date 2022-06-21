package com.lendbiz.p2p.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lendbiz.p2p.api.request.TransferMBRequest;
import com.lendbiz.p2p.api.service.MbbankTransferService;

import lombok.extern.log4j.Log4j2;

/***********************************************************************
 *
 * @package：com.lendbiz.p2p.api.controller，@class-name：UserController.java
 *
 * @copyright Copyright: 2021-2022
 * @creator Hoang Thanh Tu <br/>
 * @create-time Apr 9, 2021 10:57:13 AM
 *
 ***********************************************************************/
@RestController
@RequestMapping("/lendbiz/mb")
@Log4j2
@CrossOrigin(origins = "*")
public class MbController {

    @Autowired
    MbbankTransferService mbbankTransferService;

    @PostMapping("/transfer")
    public ResponseEntity<?> getToken(HttpServletRequest httpServletRequest,
            @RequestBody TransferMBRequest request) {
        String requestId = httpServletRequest.getHeader("RequestId");
        return mbbankTransferService.transfer(requestId, request);
    }

}