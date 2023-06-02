package com.lendbiz.p2p.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lendbiz.p2p.api.constants.ErrorCode;
import com.lendbiz.p2p.api.entity.GameHistoryEntity;
import com.lendbiz.p2p.api.exception.BusinessException;
import com.lendbiz.p2p.api.repository.GameConfigRepository;
import com.lendbiz.p2p.api.repository.GameHistoryRepository;
import com.lendbiz.p2p.api.repository.GameRepository;
import com.lendbiz.p2p.api.repository.GameTurnRepository;
import com.lendbiz.p2p.api.request.GameConfigUpdateRequest;
import com.lendbiz.p2p.api.response.BaseResponse;
import com.lendbiz.p2p.api.service.GameService;

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

    @Override
    public ResponseEntity<?> getGameConfig(GameConfigUpdateRequest request) {
        try {
            return response(toResult(gameConfigRepository.getGameConfig(request.getCustId(),
                    request.getGroupId(), request.getFromTime(), request.getToTime(), request.getFromDate(),
                    request.getToDate())));
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

}
