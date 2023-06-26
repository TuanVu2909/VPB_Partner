package com.lendbiz.p2p.api.service;

import org.springframework.http.ResponseEntity;

import com.lendbiz.p2p.api.request.GameConfigUpdateRequest;
import com.lendbiz.p2p.api.request.GetRateByDayRequest;

public interface GameService {

    ResponseEntity<?> getGameConfig(GameConfigUpdateRequest request);

    ResponseEntity<?> getGameTurn(GameConfigUpdateRequest request);

    ResponseEntity<?> getGameHistory(GameConfigUpdateRequest request);

    ResponseEntity<?> getAdminGameHistory(GameConfigUpdateRequest request);

    ResponseEntity<?> getAdminGameUser(GameConfigUpdateRequest request);

    ResponseEntity<?> getAdminRateByDay(GetRateByDayRequest request);

    ResponseEntity<?> getAdminTotalPrize(GameConfigUpdateRequest request);

    ResponseEntity<?> getGameWin();

    ResponseEntity<?> getGameDay();

    ResponseEntity<?> updateGameConfig(GameConfigUpdateRequest request);

    ResponseEntity<?> updateGameConfigAdmin(GameConfigUpdateRequest request);

    ResponseEntity<?> updateGameGroupTime(GameConfigUpdateRequest request);

    ResponseEntity<?> updateGamePrize(GameConfigUpdateRequest request);

    ResponseEntity<?> insertGameHistory(GameConfigUpdateRequest request);
}