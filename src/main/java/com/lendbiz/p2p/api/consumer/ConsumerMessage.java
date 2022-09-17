package com.lendbiz.p2p.api.consumer;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lendbiz.p2p.api.constants.Constants;
import com.lendbiz.p2p.api.entity.BankAccountEntity;
import com.lendbiz.p2p.api.entity.WithdrawEntity;
import com.lendbiz.p2p.api.repository.BankAccountRepository;
import com.lendbiz.p2p.api.repository.CfMastRepository;
import com.lendbiz.p2p.api.repository.WithdrawRepo;
import com.lendbiz.p2p.api.request.AddInfoList;
import com.lendbiz.p2p.api.request.CashOutRequest;
import com.lendbiz.p2p.api.request.TransferMBRequest;
import com.lendbiz.p2p.api.service.MbbankTransferService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ConsumerMessage {

    @Autowired
    WithdrawRepo withdrawRepo;

    @Autowired
    MbbankTransferService mbbankTransferService;

    @Autowired
    CfMastRepository cfMastRepository;

    @Autowired
    BankAccountRepository bankAccountRepository;

    @KafkaListener(topics = Constants.KAFKA.TOPIC_CASH_OUT_3GANG, groupId = Constants.KAFKA.GROUP_CASH_OUT_3GANG_ID)
    @Transactional(readOnly = true)
    public void consumeAutoCashOut(String jsonData) {
        log.info(String.format("#### -> Consumed message auto cash out-> %s", jsonData));

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root;
        try {
            root = mapper.readTree(jsonData);
            CashOutRequest cashoutRequest = new CashOutRequest();
            cashoutRequest = mapper.readValue(root.toString(), CashOutRequest.class);

            WithdrawEntity withdraw = withdrawRepo.subtractBalance(cashoutRequest.getCustId(),
                    cashoutRequest.getAmount(), "2");

            if (withdraw.getPStatus().equalsIgnoreCase("01")) {

                BankAccountEntity bankEntity = bankAccountRepository.getUserBankAccount(cashoutRequest.getCustId());
                String transferType = "INHOUSE";
                if (!bankEntity.getBankCode().equalsIgnoreCase("970422")) {
                    transferType = "FAST";
                }

                TransferMBRequest transRequest = new TransferMBRequest();
                transRequest.setBankCode(bankEntity.getBankCode());
                transRequest.setDebitName("LB CAPITAL");
                transRequest.setDebitResourceNumber("0000340872730");
                transRequest.setDebitType("ACCOUNT");

                transRequest.setCreditName(bankEntity.getBankAcName());
                transRequest.setCreditResourceNumber(bankEntity.getBankAccount());
                transRequest.setCreditType("ACCOUNT");

                transRequest.setTransferAmount(String.valueOf((int) cashoutRequest.getAmount()));
                transRequest.setTransferType(transferType);
                AddInfoList addInfo = new AddInfoList("requestId", withdraw.getDes(), "1");
                AddInfoList[] lstAddInfo = new AddInfoList[] {
                        addInfo
                };

                transRequest.setAddInfoList(lstAddInfo);
                System.out.println((int) cashoutRequest.getAmount());
                // transRequest.set
                mbbankTransferService.transfer(String.valueOf(System.currentTimeMillis()),
                        transRequest);

            } else {

            }

        } catch (JsonProcessingException e) {
            log.info(e.getMessage());
        }

    }

}