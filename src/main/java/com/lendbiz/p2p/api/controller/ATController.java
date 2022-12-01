package com.lendbiz.p2p.api.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lendbiz.p2p.api.entity.ATEntity;
import com.lendbiz.p2p.api.entity.NotificationsPushEntity;
import com.lendbiz.p2p.api.repository.ATRepository;
import com.lendbiz.p2p.api.repository.PushRepository;
import com.lendbiz.p2p.api.service.impl.UserServiceImpl;

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
@RequestMapping("/lendbiz/at")
@Log4j2
@CrossOrigin(origins = "*")
public class ATController {

    @Autowired
    ATRepository atRepository;

    @Autowired
    UserServiceImpl serviceImpl;

    @Transactional(readOnly = true)
    @Scheduled(initialDelay = 1 * 60, fixedDelay = 1 * 30 * 1000)
    public void postBack() {

        List<ATEntity> atList = atRepository.postBack();
        for (ATEntity atEntity : atList) {
            log.info("Start postback " + atEntity.getCustid());

            try {
                serviceImpl.accessTradePostBack(atEntity.getPubid(), atEntity.getCustid());
                atRepository.status(atEntity.getCustid(), 1);
            } catch (Exception e) {
                atRepository.status(atEntity.getCustid(), 98);
                log.info("Error postback: " + e.getMessage());
            }

        }
        log.info("End post back");

    }

    @Transactional(readOnly = true)
    @Scheduled(initialDelay = 1 * 60, fixedDelay = 1 * 30 * 1000)
    public void postBackAccept() {

        List<ATEntity> atList = atRepository.accept();
        for (ATEntity atEntity : atList) {
            log.info("Start postback " + atEntity.getCustid());

            try {
                serviceImpl.accessTradeUpdate(atEntity.getCustid(), "1");
                atRepository.status(atEntity.getCustid(), 2);
            } catch (Exception e) {
                atRepository.status(atEntity.getCustid(), 99);
                log.info("Error postback: " + e.getMessage());
            }

        }
        log.info("End post back");

    }

    @Transactional(readOnly = true)
    @Scheduled(initialDelay = 1 * 60, fixedDelay = 1 * 30 * 1000)
    public void postBackReject() {

        List<ATEntity> atList = atRepository.reject();
        for (ATEntity atEntity : atList) {
            log.info("Start postback " + atEntity.getCustid());

            try {
                serviceImpl.accessTradeUpdate(atEntity.getCustid(), "2");
                atRepository.status(atEntity.getCustid(), 3);
            } catch (Exception e) {
                atRepository.status(atEntity.getCustid(), 99);
                log.info("Error postback: " + e.getMessage());
            }

        }
        log.info("End post back");

    }

}