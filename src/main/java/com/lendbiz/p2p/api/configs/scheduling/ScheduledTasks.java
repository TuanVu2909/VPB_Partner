package com.lendbiz.p2p.api.configs.scheduling;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class ScheduledTasks {
    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
//    2s gửi 1 tin nhắn theo giờ VN
//    @Scheduled(zone = "GMT+7", fixedRate = 2000)
//    public void scheduleTasksendEmail1() {
//        // call send email method here
//        logger.info("Send email every 2s");
//    }
    // 15h45'00s hàng ngày gửi 1 tin nhắn theo giờ VN
//    @Scheduled(zone = "GMT+7", cron = "00 45 15 * * *")
//    public void scheduleTasksendEmail2() {
//        // call send email method here
//        logger.info("Send email every 15h45' dally");
//        return;
//    }
}
