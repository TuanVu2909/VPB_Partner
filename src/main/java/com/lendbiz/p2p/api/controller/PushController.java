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

import com.lendbiz.p2p.api.entity.NotificationsPushEntity;
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
@RequestMapping("/lendbiz/push")
@Log4j2
@CrossOrigin(origins = "*")
public class PushController {

    @Autowired
    PushRepository pushRepository;

    @Autowired
    UserServiceImpl serviceImpl;

    // @GetMapping("/push-notification")
    @Transactional(readOnly = true)
    // @Scheduled(initialDelay = 1 * 60, fixedDelay = 1 * 3 * 1000)
    public void getToken() {

        List<NotificationsPushEntity> lstNoti = pushRepository.getOsNotifications();
        for (NotificationsPushEntity notificationsPushEntity : lstNoti) {
            log.info("Start auto push");
            serviceImpl.createNotificationOneSignal(notificationsPushEntity);
        }
        log.info("End auto push");

    }

}