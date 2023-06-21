package com.lendbiz.p2p.api.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lendbiz.p2p.api.constants.ErrorCode;
import com.lendbiz.p2p.api.entity.GameConfigEntity;
import com.lendbiz.p2p.api.entity.GameConfigLogEntity;
import com.lendbiz.p2p.api.entity.GameHistoryEntity;
import com.lendbiz.p2p.api.exception.BusinessException;
import com.lendbiz.p2p.api.repository.GameAdminHistoryRepository;
import com.lendbiz.p2p.api.repository.GameConfigLogRepo;
import com.lendbiz.p2p.api.repository.GameConfigRepository;
import com.lendbiz.p2p.api.repository.GameHistoryRepository;
import com.lendbiz.p2p.api.repository.GameRepository;
import com.lendbiz.p2p.api.repository.GameTurnRepository;
import com.lendbiz.p2p.api.repository.GetGameWinRepository;
import com.lendbiz.p2p.api.request.GameConfigUpdateRequest;
import com.lendbiz.p2p.api.response.BaseResponse;
import com.lendbiz.p2p.api.service.GameService;
import com.lendbiz.p2p.api.utils.GameConfigComparator;

@Service
public class GameServiceImpl extends BaseResponse<GameService> implements GameService {

    @Autowired
    GameRepository gameRepository;

    @Autowired
    GameConfigRepository gameConfigRepository;

    @Autowired
    GameTurnRepository gameTurnRepository;

    @Autowired
    GameHistoryRepository gameHistoryRepository;

    @Autowired
    GameAdminHistoryRepository adminHistoryRepository;

    @Autowired
    GetGameWinRepository gameWinRepository;

    @Autowired
    GameConfigLogRepo configLogRepo;

    @Override
    public ResponseEntity<?> getGameConfig(GameConfigUpdateRequest request) {
        try {

            GameConfigLogEntity newLog = new GameConfigLogEntity();
            String logId = String.valueOf(System.currentTimeMillis());
            newLog.setCustId(request.getCustId());
            newLog.setVqId(logId);
            newLog.setLogDate(LocalDateTime.now());

            List<GameConfigEntity> entity = gameConfigRepository.getGameConfig(request.getCustId(),
                    request.getGroupId(), request.getFromTime(), request.getToTime(), request.getFromDate(),
                    request.getToDate());

            if (entity.get(0).getConfigId().intValue() == 7) {
                Collections.sort(entity, new GameConfigComparator());
                return response(toResult(entity));
            }

            boolean isContinue = false;
            List<GameConfigEntity> newListEntity = new ArrayList<>();
            double totalChance = 0;

            for (int i = 0; i < entity.size(); i++) {
                totalChance = totalChance + entity.get(i).getRate() * 2;

                if (!isContinue) {
                    Random random = new Random();
                    int chance = random.nextInt(200 - 1 + 1)
                            + 1;
                    if (chance <= totalChance) {
                        GameConfigEntity newChance = new GameConfigEntity();
                        newChance.setConfigId(entity.get(i).getConfigId());
                        newChance.setFromDate(entity.get(i).getFromDate());
                        newChance.setFromTime(entity.get(i).getFromTime());
                        newChance.setToDate(entity.get(i).getToDate());
                        newChance.setToTime(entity.get(i).getToTime());
                        newChance.setId(entity.get(i).getId());
                        newChance.setMaxPrize(entity.get(i).getMaxPrize());
                        newChance.setName(entity.get(i).getName());
                        newChance.setRAmount(entity.get(i).getRAmount());
                        newChance.setRate(100.0);
                        newListEntity.add(newChance);
                        isContinue = true;
                    }

                    if (!isContinue) {
                        // totalChance = totalChance + entity.get(i).getRate() * 2;
                        GameConfigEntity newChance = new GameConfigEntity();
                        newChance.setConfigId(entity.get(i).getConfigId());
                        newChance.setFromDate(entity.get(i).getFromDate());
                        newChance.setFromTime(entity.get(i).getFromTime());
                        newChance.setToDate(entity.get(i).getToDate());
                        newChance.setToTime(entity.get(i).getToTime());
                        newChance.setId(entity.get(i).getId());
                        newChance.setMaxPrize(entity.get(i).getMaxPrize());
                        newChance.setName(entity.get(i).getName());
                        newChance.setRAmount(entity.get(i).getRAmount());
                        newChance.setRate(0.0);
                        newListEntity.add(newChance);
                        isContinue = false;
                    }

                } else {
                    GameConfigEntity newChance = new GameConfigEntity();
                    newChance.setConfigId(entity.get(i).getConfigId());
                    newChance.setFromDate(entity.get(i).getFromDate());
                    newChance.setFromTime(entity.get(i).getFromTime());
                    newChance.setToDate(entity.get(i).getToDate());
                    newChance.setToTime(entity.get(i).getToTime());
                    newChance.setId(entity.get(i).getId());
                    newChance.setMaxPrize(entity.get(i).getMaxPrize());
                    newChance.setName(entity.get(i).getName());
                    newChance.setRAmount(entity.get(i).getRAmount());
                    newChance.setRate(0.0);
                    newListEntity.add(newChance);
                }
            }

            boolean isOk = false;
            for (int i = 0; i < newListEntity.size(); i++) {
                if (newListEntity.get(i).getRate() > 0) {
                    isOk = true;
                    break;
                }
            }

            if (!isOk) {
                List<GameConfigEntity> ifNothingReturn = new ArrayList<>();
                for (int i = 0; i < newListEntity.size(); i++) {
                    if (newListEntity.get(i).getId() == 1) {
                        GameConfigEntity newChance = new GameConfigEntity();
                        newChance.setConfigId(entity.get(i).getConfigId());
                        newChance.setFromDate(entity.get(i).getFromDate());
                        newChance.setFromTime(entity.get(i).getFromTime());
                        newChance.setToDate(entity.get(i).getToDate());
                        newChance.setToTime(entity.get(i).getToTime());
                        newChance.setId(entity.get(i).getId());
                        newChance.setMaxPrize(entity.get(i).getMaxPrize());
                        newChance.setName(entity.get(i).getName());
                        newChance.setRAmount(entity.get(i).getRAmount());
                        newChance.setRate(100.0);
                        ifNothingReturn.add(newChance);

                    } else {
                        GameConfigEntity newChance = new GameConfigEntity();
                        newChance.setConfigId(entity.get(i).getConfigId());
                        newChance.setFromDate(entity.get(i).getFromDate());
                        newChance.setFromTime(entity.get(i).getFromTime());
                        newChance.setToDate(entity.get(i).getToDate());
                        newChance.setToTime(entity.get(i).getToTime());
                        newChance.setId(entity.get(i).getId());
                        newChance.setMaxPrize(entity.get(i).getMaxPrize());
                        newChance.setName(entity.get(i).getName());
                        newChance.setRAmount(entity.get(i).getRAmount());
                        newChance.setRate(0.0);
                        ifNothingReturn.add(newChance);
                    }
                }
                Collections.sort(ifNothingReturn, new GameConfigComparator());

                for (int i = 0; i < ifNothingReturn.size(); i++) {
                    if (ifNothingReturn.get(i).getId() == 1) {
                        newLog.setL1(ifNothingReturn.get(i).getRate());
                    }
                    if (ifNothingReturn.get(i).getId() == 2) {
                        newLog.setL2(ifNothingReturn.get(i).getRate());
                    }
                    if (ifNothingReturn.get(i).getId() == 3) {
                        newLog.setL3(ifNothingReturn.get(i).getRate());
                    }
                    if (ifNothingReturn.get(i).getId() == 4) {
                        newLog.setL4(ifNothingReturn.get(i).getRate());
                    }
                    if (ifNothingReturn.get(i).getId() == 5) {
                        newLog.setL5(ifNothingReturn.get(i).getRate());
                    }
                    if (ifNothingReturn.get(i).getId() == 6) {
                        newLog.setL6(ifNothingReturn.get(i).getRate());
                    }
                    if (ifNothingReturn.get(i).getId() == 7) {
                        newLog.setL7(ifNothingReturn.get(i).getRate());
                    }
                    if (ifNothingReturn.get(i).getId() == 8) {
                        newLog.setL8(ifNothingReturn.get(i).getRate());
                    }
                    if (ifNothingReturn.get(i).getId() == 9) {
                        newLog.setL9(ifNothingReturn.get(i).getRate());
                    }
                    if (ifNothingReturn.get(i).getId() == 10) {
                        newLog.setL10(ifNothingReturn.get(i).getRate());
                    }

                }

                try {
                    configLogRepo.save(newLog);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                return response(toResult(ifNothingReturn));
            }
            Collections.sort(newListEntity, new GameConfigComparator());

            for (int i = 0; i < newListEntity.size(); i++) {
                if (newListEntity.get(i).getId() == 1) {
                    newLog.setL1(newListEntity.get(i).getRate());
                }
                if (newListEntity.get(i).getId() == 2) {
                    newLog.setL2(newListEntity.get(i).getRate());
                }
                if (newListEntity.get(i).getId() == 3) {
                    newLog.setL3(newListEntity.get(i).getRate());
                }
                if (newListEntity.get(i).getId() == 4) {
                    newLog.setL4(newListEntity.get(i).getRate());
                }
                if (newListEntity.get(i).getId() == 5) {
                    newLog.setL5(newListEntity.get(i).getRate());
                }
                if (newListEntity.get(i).getId() == 6) {
                    newLog.setL6(newListEntity.get(i).getRate());
                }
                if (newListEntity.get(i).getId() == 7) {
                    newLog.setL7(newListEntity.get(i).getRate());
                }
                if (newListEntity.get(i).getId() == 8) {
                    newLog.setL8(newListEntity.get(i).getRate());
                }
                if (newListEntity.get(i).getId() == 9) {
                    newLog.setL9(newListEntity.get(i).getRate());
                }
                if (newListEntity.get(i).getId() == 10) {
                    newLog.setL10(newListEntity.get(i).getRate());
                }

            }
            try {
                configLogRepo.save(newLog);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return response(toResult(newListEntity));
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.UNKNOWN_ERROR, e.getMessage());
        }

    }

    @Override
    public ResponseEntity<?> getGameTurn(GameConfigUpdateRequest request) {
        try {
            return response(toResult(gameTurnRepository.getGameTurn(request.getCustId(),
                    1)));
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.UNKNOWN_ERROR, e.getMessage());
        }

    }

    @Override
    public ResponseEntity<?> getGameHistory(GameConfigUpdateRequest request) {
        try {

            return response(toResult(gameHistoryRepository.getGameHistory(request.getCustId(),
                    1)));
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.UNKNOWN_ERROR, e.getMessage());
        }

    }

    @Override
    public ResponseEntity<?> getAdminGameHistory(GameConfigUpdateRequest request) {
        try {

            return response(toResult(adminHistoryRepository.getAdminGameHistory(request.getCustId(),
                    1)));
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.UNKNOWN_ERROR, e.getMessage());
        }

    }

    @Override
    public ResponseEntity<?> updateGameConfig(GameConfigUpdateRequest request) {

        return response(toResult(gameRepository.updateGameConfig(request.getId(), request.getRate(),
                request.getGiftId(), request.getGroupId(),
                request.getStatus())));
    }

    @Override
    public ResponseEntity<?> updateGameGroupTime(GameConfigUpdateRequest request) {
        return response(toResult(
                gameRepository.updateGameGroupTime(request.getGroupId(), request.getFromTime(), request.getToTime(),
                        request.getFromDate(), request.getToDate(), request.getStatus(), request.getMaxPrize())));
    }

    @Override
    public ResponseEntity<?> updateGamePrize(GameConfigUpdateRequest request) {
        return response(toResult(gameRepository.updateGamePrize(request.getId(), request.getName(),
                request.getStatus(), request.getPrizeAmount())));
    }

    @Override
    public ResponseEntity<?> insertGameHistory(GameConfigUpdateRequest request) {
        try {
            return response(toResult(gameRepository.insertGameHistory(request.getCustId(), request.getStatus(),
                    request.getGiftId(), request.getRate(), request.getId())));
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.ERROR_500, e.getMessage());
        }

    }

    @Override
    public ResponseEntity<?> getGameWin() {
        try {
            return response(toResult(gameWinRepository.getGameWin()));
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.UNKNOWN_ERROR, e.getMessage());
        }
    }

}
