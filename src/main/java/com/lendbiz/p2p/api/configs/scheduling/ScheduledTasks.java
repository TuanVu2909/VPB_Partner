package com.lendbiz.p2p.api.configs.scheduling;

import com.lendbiz.p2p.api.service.FundService;
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

    // 1s executive below action with time zone VN
//    @Scheduled(zone = "GMT+7", fixedRate = 1000)
//    public void scheduleTask() {
//        // call send email method here
//        logger.info(!!!!!!!!!!!!!!!!!!!);
//    }
    // 5 phút một lần
    @Scheduled(zone = "GMT+7", cron = "0 */1 * ? * *")
    public void scheduleTaskGetToken() {
        fundService.getTokenAFM();
    }
}
