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

    //    7h send request to get AFM token time zone VN
//    @Scheduled(zone = "GMT+7", fixedRate = 7*(1000*60*60))
//    public void scheduleTasksendEmail1() {
//        // call send email method here
//        logger.info("Send email every 2s");
//    }
    // 5 phút một lần
    @Scheduled(zone = "GMT+7", cron = "0 */5 * ? * *")
    public void scheduleTasksendEmail2() {
        fundService.getTokenAFM();
        return;
    }
}
