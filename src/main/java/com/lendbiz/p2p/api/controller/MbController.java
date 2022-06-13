package com.lendbiz.p2p.api.controller;

import javax.servlet.http.HttpServletRequest;

import com.lendbiz.p2p.api.request.GetBankNameRequest;
import com.lendbiz.p2p.api.service.MbbankTransferService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/get-token")
    public ResponseEntity<?> getToken(HttpServletRequest httpServletRequest) {
        GetBankNameRequest request = new GetBankNameRequest();
        request.setAccountNumber("0001220599181");
        request.setBankCode("970422");
        request.setCardNumber("");

        // AddInfo addInfo = new AddInfo("", "");
        // AddInfo[] lstInfo = { addInfo };
        // request.setAddInfo(lstInfo);

        // String hashText = request.getAccountNumber() + "_" + request.getCardNumber()
        // + "_" + request.getBankCode() + "_"
        // + request.getAddInfo()[0].getName() + "_" +
        // request.getAddInfo()[0].getValue();

        // String sha256hex = DigestUtils.sha256Hex(hashText);
        // System.out.println(sha256hex);

        // request.setChecksum(sha256hex);

        return mbbankTransferService.getBankName(request);
    }

}