// package com.lendbiz.p2p.api.configs;

// import java.util.ArrayList;
// import java.util.Date;
// import java.util.List;
// import java.util.Map;
// import java.util.stream.Collectors;

// import com.lendbiz.p2p.api.entity.CmdMenuEntity;
// import com.lendbiz.p2p.api.entity.SearchEntity;
// import com.lendbiz.p2p.api.entity.SearchFldEntity;

// import com.lendbiz.p2p.api.entity.TreeCmdMenuEntity;
// import com.lendbiz.p2p.api.repository.CmdMenuRepository;
// import com.lendbiz.p2p.api.repository.SearchFldRepository;
// import com.lendbiz.p2p.api.repository.SearchRepository;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.ApplicationArguments;
// import org.springframework.boot.ApplicationRunner;
// import org.springframework.kafka.annotation.KafkaListener;
// import org.springframework.kafka.core.KafkaTemplate;
// import org.springframework.stereotype.Component;

// @Component
// public class DataLoader implements ApplicationRunner {

//     @KafkaListener(topics = "demo", groupId = "spring-kafka")
//     public void listen(String message) {
//         System.out.println("Received Message in group - spring-kafka: " + message);
//         sendMessage("Now: " + message);
//     }

//     @Autowired
//     private KafkaTemplate<String, String> kafkaTemplate;

//     public void sendMessage(String msg) {
//         kafkaTemplate.send("test", msg);
//     }

//     @Override
//     public void run(ApplicationArguments args) throws Exception {
//         // for (int i = 0; i < 1000; i++) {
//         // sendMessage("Now: " + new Date());
//         // Thread.sleep(2000);
//         // }

//     }

// }
