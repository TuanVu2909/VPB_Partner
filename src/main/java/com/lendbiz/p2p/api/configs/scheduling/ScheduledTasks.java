package com.lendbiz.p2p.api.configs.scheduling;

import com.lendbiz.p2p.api.service.FundService;
import com.lendbiz.p2p.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class ScheduledTasks {
    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
    @Autowired
    private FundService fundService;
    @Autowired
    private UserService userService;

    // 1s executive below action with time zone VN
//    @Scheduled(zone = "GMT+7", fixedRate = 1000)
//    public void scheduleTask() {
//        // call send email method here
//        logger.info("+++++++++++++ 123123 +++++++++++++++");
//    }
    // 5 phút một lần
    @Scheduled(zone = "GMT+7", cron = "0 */5 * ? * *")
    public void scheduleTaskGetToken() {
        fundService.getTokenAFM();
    }

    // 15p một lần
    @Scheduled(zone = "GMT+7", fixedRate = (1000*60)*15)
    //@Scheduled(zone = "GMT+7", fixedRate = 1000)
    public void jobAffiliate() {
        userService.jobHandleAffiliate0();
        userService.jobHandleAffiliate1();
        userService.jobHandleAffiliate2();
        userService.jobHandleAffiliate3();
    }
}
