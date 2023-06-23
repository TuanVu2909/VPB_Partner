package com.lendbiz.p2p.api.service;

import org.springframework.http.ResponseEntity;

import com.lendbiz.p2p.api.request.GameConfigUpdateRequest;

public interface GameService {

    ResponseEntity<?> getGameConfig(GameConfigUpdateRequest request);

    ResponseEntity<?> getGameTurn(GameConfigUpdateRequest request);

    ResponseEntity<?> getGameHistory(GameConfigUpdateRequest request);

    ResponseEntity<?> getAdminGameHistory(GameConfigUpdateRequest request);

    ResponseEntity<?> getAdminGameUser(GameConfigUpdateRequest request);

    ResponseEntity<?> getGameWin();

    ResponseEntity<?> updateGameConfig(GameConfigUpdateRequest request);

    ResponseEntity<?> updateGameGroupTime(GameConfigUpdateRequest request);

    ResponseEntity<?> updateGamePrize(GameConfigUpdateRequest request);

    ResponseEntity<?> insertGameHistory(GameConfigUpdateRequest request);
}