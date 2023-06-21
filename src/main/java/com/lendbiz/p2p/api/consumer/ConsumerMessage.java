// package com.lendbiz.p2p.api.consumer;

// import java.time.LocalDateTime;
// import java.util.ArrayList;
// import java.util.Collections;
// import java.util.List;
// import java.util.Random;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.kafka.annotation.KafkaListener;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// import com.fasterxml.jackson.core.JsonProcessingException;
// import com.fasterxml.jackson.databind.JsonNode;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.lendbiz.p2p.api.constants.Constants;
// import com.lendbiz.p2p.api.entity.BankAccountEntity;
// import com.lendbiz.p2p.api.entity.GameConfigEntity;
// import com.lendbiz.p2p.api.entity.GameConfigLogEntity;
// import com.lendbiz.p2p.api.entity.WithdrawEntity;
// import com.lendbiz.p2p.api.producer.ProducerMessage;
// import com.lendbiz.p2p.api.repository.BankAccountRepository;
// import com.lendbiz.p2p.api.repository.CfMastRepository;
// import com.lendbiz.p2p.api.repository.GameAdminHistoryRepository;
// import com.lendbiz.p2p.api.repository.GameConfigLogRepo;
// import com.lendbiz.p2p.api.repository.GameConfigRepository;
// import com.lendbiz.p2p.api.repository.GameHistoryRepository;
// import com.lendbiz.p2p.api.repository.GameRepository;
// import com.lendbiz.p2p.api.repository.GameTurnRepository;
// import com.lendbiz.p2p.api.repository.GetGameWinRepository;
// import com.lendbiz.p2p.api.repository.WithdrawRepo;
// import com.lendbiz.p2p.api.request.AddInfoList;
// import com.lendbiz.p2p.api.request.CashOutRequest;
// import com.lendbiz.p2p.api.request.GameConfigUpdateRequest;
// import com.lendbiz.p2p.api.request.TransferMBRequest;
// import com.lendbiz.p2p.api.service.MbbankTransferService;
// import com.lendbiz.p2p.api.utils.GameConfigComparator;

// import lombok.extern.slf4j.Slf4j;

// @Service
// @Slf4j
// public class ConsumerMessage {

//     @Autowired
//     WithdrawRepo withdrawRepo;

//     @Autowired
//     MbbankTransferService mbbankTransferService;

//     @Autowired
//     CfMastRepository cfMastRepository;

//     @Autowired
//     BankAccountRepository bankAccountRepository;

//     @Autowired
//     GameRepository gameRepository;

//     @Autowired
//     GameConfigRepository gameConfigRepository;

//     @Autowired
//     GameTurnRepository gameTurnRepository;

//     @Autowired
//     GameHistoryRepository gameHistoryRepository;

//     @Autowired
//     GameAdminHistoryRepository adminHistoryRepository;

//     @Autowired
//     GetGameWinRepository gameWinRepository;

//     @Autowired
//     GameConfigLogRepo configLogRepo;

//     @Autowired
//     ProducerMessage producerMessage;

//     @KafkaListener(topics = "GAME_TOPIC_TEST", groupId = "GAME_TOPIC_TEST")
//     @Transactional(readOnly = true)
//     public List<GameConfigEntity> gameConsumer(String jsonData) {
//         log.info(String.format("#### -> Consumed message game-> %s", jsonData));
//         GameConfigUpdateRequest request = new GameConfigUpdateRequest();
//         ObjectMapper mapper = new ObjectMapper();
//         JsonNode root;
//         try {
//             root = mapper.readTree(jsonData);

//             request = mapper.readValue(root.toString(), GameConfigUpdateRequest.class);
//         } catch (JsonProcessingException e) {
//             log.info(e.getMessage());
//         }
//         GameConfigLogEntity newLog = new GameConfigLogEntity();
//         String logId = String.valueOf(System.currentTimeMillis());
//         newLog.setCustId(request.getCustId());
//         newLog.setVqId(logId);
//         newLog.setLogDate(LocalDateTime.now());

//         List<GameConfigEntity> entity = gameConfigRepository.getGameConfig(request.getCustId(),
//                 request.getGroupId(), request.getFromTime(), request.getToTime(), request.getFromDate(),
//                 request.getToDate());

//         if (entity.get(0).getConfigId().intValue() == 7) {
//             Collections.sort(entity, new GameConfigComparator());
//             return entity;
//         }

//         boolean isContinue = false;
//         List<GameConfigEntity> newListEntity = new ArrayList<>();
//         double totalChance = 0;

//         for (int i = 0; i < entity.size(); i++) {
//             totalChance = totalChance + entity.get(i).getRate() * 2;

//             if (!isContinue) {
//                 Random random = new Random();
//                 int chance = random.nextInt(200 - 1 + 1)
//                         + 1;
//                 if (chance <= totalChance) {
//                     GameConfigEntity newChance = new GameConfigEntity();
//                     newChance.setConfigId(entity.get(i).getConfigId());
//                     newChance.setFromDate(entity.get(i).getFromDate());
//                     newChance.setFromTime(entity.get(i).getFromTime());
//                     newChance.setToDate(entity.get(i).getToDate());
//                     newChance.setToTime(entity.get(i).getToTime());
//                     newChance.setId(entity.get(i).getId());
//                     newChance.setMaxPrize(entity.get(i).getMaxPrize());
//                     newChance.setName(entity.get(i).getName());
//                     newChance.setRAmount(entity.get(i).getRAmount());
//                     newChance.setRate(100.0);
//                     newListEntity.add(newChance);
//                     isContinue = true;
//                 }

//                 if (!isContinue) {
//                     // totalChance = totalChance + entity.get(i).getRate() * 2;
//                     GameConfigEntity newChance = new GameConfigEntity();
//                     newChance.setConfigId(entity.get(i).getConfigId());
//                     newChance.setFromDate(entity.get(i).getFromDate());
//                     newChance.setFromTime(entity.get(i).getFromTime());
//                     newChance.setToDate(entity.get(i).getToDate());
//                     newChance.setToTime(entity.get(i).getToTime());
//                     newChance.setId(entity.get(i).getId());
//                     newChance.setMaxPrize(entity.get(i).getMaxPrize());
//                     newChance.setName(entity.get(i).getName());
//                     newChance.setRAmount(entity.get(i).getRAmount());
//                     newChance.setRate(0.0);
//                     newListEntity.add(newChance);
//                     isContinue = false;
//                 }

//             } else {
//                 GameConfigEntity newChance = new GameConfigEntity();
//                 newChance.setConfigId(entity.get(i).getConfigId());
//                 newChance.setFromDate(entity.get(i).getFromDate());
//                 newChance.setFromTime(entity.get(i).getFromTime());
//                 newChance.setToDate(entity.get(i).getToDate());
//                 newChance.setToTime(entity.get(i).getToTime());
//                 newChance.setId(entity.get(i).getId());
//                 newChance.setMaxPrize(entity.get(i).getMaxPrize());
//                 newChance.setName(entity.get(i).getName());
//                 newChance.setRAmount(entity.get(i).getRAmount());
//                 newChance.setRate(0.0);
//                 newListEntity.add(newChance);
//             }
//         }

//         boolean isOk = false;
//         for (int i = 0; i < newListEntity.size(); i++) {
//             if (newListEntity.get(i).getRate() > 0) {
//                 isOk = true;
//                 break;
//             }
//         }

//         if (!isOk) {
//             List<GameConfigEntity> ifNothingReturn = new ArrayList<>();
//             for (int i = 0; i < newListEntity.size(); i++) {
//                 if (newListEntity.get(i).getId() == 1) {
//                     GameConfigEntity newChance = new GameConfigEntity();
//                     newChance.setConfigId(entity.get(i).getConfigId());
//                     newChance.setFromDate(entity.get(i).getFromDate());
//                     newChance.setFromTime(entity.get(i).getFromTime());
//                     newChance.setToDate(entity.get(i).getToDate());
//                     newChance.setToTime(entity.get(i).getToTime());
//                     newChance.setId(entity.get(i).getId());
//                     newChance.setMaxPrize(entity.get(i).getMaxPrize());
//                     newChance.setName(entity.get(i).getName());
//                     newChance.setRAmount(entity.get(i).getRAmount());
//                     newChance.setRate(100.0);
//                     ifNothingReturn.add(newChance);

//                 } else {
//                     GameConfigEntity newChance = new GameConfigEntity();
//                     newChance.setConfigId(entity.get(i).getConfigId());
//                     newChance.setFromDate(entity.get(i).getFromDate());
//                     newChance.setFromTime(entity.get(i).getFromTime());
//                     newChance.setToDate(entity.get(i).getToDate());
//                     newChance.setToTime(entity.get(i).getToTime());
//                     newChance.setId(entity.get(i).getId());
//                     newChance.setMaxPrize(entity.get(i).getMaxPrize());
//                     newChance.setName(entity.get(i).getName());
//                     newChance.setRAmount(entity.get(i).getRAmount());
//                     newChance.setRate(0.0);
//                     ifNothingReturn.add(newChance);
//                 }
//             }
//             Collections.sort(ifNothingReturn, new GameConfigComparator());

//             for (int i = 0; i < ifNothingReturn.size(); i++) {
//                 if (ifNothingReturn.get(i).getId() == 1) {
//                     newLog.setL1(ifNothingReturn.get(i).getRate());
//                 }
//                 if (ifNothingReturn.get(i).getId() == 2) {
//                     newLog.setL2(ifNothingReturn.get(i).getRate());
//                 }
//                 if (ifNothingReturn.get(i).getId() == 3) {
//                     newLog.setL3(ifNothingReturn.get(i).getRate());
//                 }
//                 if (ifNothingReturn.get(i).getId() == 4) {
//                     newLog.setL4(ifNothingReturn.get(i).getRate());
//                 }
//                 if (ifNothingReturn.get(i).getId() == 5) {
//                     newLog.setL5(ifNothingReturn.get(i).getRate());
//                 }
//                 if (ifNothingReturn.get(i).getId() == 6) {
//                     newLog.setL6(ifNothingReturn.get(i).getRate());
//                 }
//                 if (ifNothingReturn.get(i).getId() == 7) {
//                     newLog.setL7(ifNothingReturn.get(i).getRate());
//                 }
//                 if (ifNothingReturn.get(i).getId() == 8) {
//                     newLog.setL8(ifNothingReturn.get(i).getRate());
//                 }
//                 if (ifNothingReturn.get(i).getId() == 9) {
//                     newLog.setL9(ifNothingReturn.get(i).getRate());
//                 }
//                 if (ifNothingReturn.get(i).getId() == 10) {
//                     newLog.setL10(ifNothingReturn.get(i).getRate());
//                 }

//             }

//             try {
//                 configLogRepo.save(newLog);
//             } catch (Exception e) {
//                 System.out.println(e.getMessage());
//             }

//             return ifNothingReturn;
//         }
//         Collections.sort(newListEntity, new GameConfigComparator());

//         for (int i = 0; i < newListEntity.size(); i++) {
//             if (newListEntity.get(i).getId() == 1) {
//                 newLog.setL1(newListEntity.get(i).getRate());
//             }
//             if (newListEntity.get(i).getId() == 2) {
//                 newLog.setL2(newListEntity.get(i).getRate());
//             }
//             if (newListEntity.get(i).getId() == 3) {
//                 newLog.setL3(newListEntity.get(i).getRate());
//             }
//             if (newListEntity.get(i).getId() == 4) {
//                 newLog.setL4(newListEntity.get(i).getRate());
//             }
//             if (newListEntity.get(i).getId() == 5) {
//                 newLog.setL5(newListEntity.get(i).getRate());
//             }
//             if (newListEntity.get(i).getId() == 6) {
//                 newLog.setL6(newListEntity.get(i).getRate());
//             }
//             if (newListEntity.get(i).getId() == 7) {
//                 newLog.setL7(newListEntity.get(i).getRate());
//             }
//             if (newListEntity.get(i).getId() == 8) {
//                 newLog.setL8(newListEntity.get(i).getRate());
//             }
//             if (newListEntity.get(i).getId() == 9) {
//                 newLog.setL9(newListEntity.get(i).getRate());
//             }
//             if (newListEntity.get(i).getId() == 10) {
//                 newLog.setL10(newListEntity.get(i).getRate());
//             }

//         }
//         try {
//             configLogRepo.save(newLog);
//         } catch (Exception e) {
//             System.out.println(e.getMessage());
//         }
//         return newListEntity;

//     }

// }