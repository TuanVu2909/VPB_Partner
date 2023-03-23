package com.lendbiz.p2p.api.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import com.lendbiz.p2p.api.constants.Constants;

@Service
@Slf4j
public class ProducerMessage {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String smsJsonData) {
        log.info(String.format("#### -> Producing message logs MBBank CHI_HO ->"));
        this.kafkaTemplate.send(Constants.KAFKA.TOPIC_LOGS_MB, smsJsonData);
    }

    public void sendLogs3Gang(String smsJsonData) {
        log.info(String.format("#### -> Producing message logs 3 Gang  ->"));
        this.kafkaTemplate.send(Constants.KAFKA.TOPIC_LOGS_3GANG, smsJsonData);
    }

    public void sendCashOu3Gang(String smsJsonData) {
        log.info(String.format("#### -> Producing message cash out 3 Gang  ->"));
        this.kafkaTemplate.send(Constants.KAFKA.TOPIC_CASH_OUT_3GANG, smsJsonData);
    }

    public void sendSaveIdCard(String smsJsonData) {
        log.info(String.format("#### -> Producing message save id card  ->"));
        this.kafkaTemplate.send(Constants.KAFKA.TOPIC_SAVE_ID_CARD, smsJsonData);
    }

    public ListenableFuture<SendResult<String, String>> sendSavingMessage(String topicName, String key, String value) {
        log.info(String.format("#### -> Producing message sendCreateBear APIGATEWAY -> " + key + " " + value));
        return this.kafkaTemplate.send(topicName, key, value);
    }
    
    public void sendErrorCreateBear(String topicName, String key, String value) {
        log.info(String.format("#### -> Producing error message sendErrorCreateBear APIGATEWAY -> " + key + " " + value));
        this.kafkaTemplate.send(topicName, key, value);
    }
}