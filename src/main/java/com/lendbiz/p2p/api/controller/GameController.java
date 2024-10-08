package com.lendbiz.p2p.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lendbiz.p2p.api.exception.BusinessException;
import com.lendbiz.p2p.api.request.GameConfigUpdateRequest;
import com.lendbiz.p2p.api.request.GetRateByDayRequest;
import com.lendbiz.p2p.api.service.GameService;

/***********************************************************************
 *
 * @package：com.lendbiz.p2p.api.controller，@class-name：UserController.java
 *
 * @copyright Copyright: 2021-2022
 * @creator Hoang Thanh Tu <br/>
 * @create-time Apr 9, 2021 10:57:13 AM
 *
 ***********************************************************************/
@RestController
@RequestMapping("/lendbiz/game")
@CrossOrigin(origins = "*")
public class GameController {

    @Autowired
    GameService gameService;

    @PostMapping("/update-game")
    @Transactional(readOnly = true)
    public ResponseEntity<?> updateGame(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId, @RequestHeader("apiType") int apiType,
            @RequestBody GameConfigUpdateRequest request)
            throws BusinessException {
        if (apiType == 1) {
            return gameService.updateGameConfig(request);
        }

        if (apiType == 2) {
            return gameService.updateGameGroupTime(request);
        }

        if (apiType == 3) {
            return gameService.updateGamePrize(request);
        }

        if (apiType == 4) {
            return gameService.insertGameHistory(request);
        }

        if (apiType == 5) {
            return gameService.updateGameConfigAdmin(request);
        }

        throw new BusinessException(
                "Thiếu apiType trong header: 1 - update GameConfig | 2 - updateGameGroupTime | 3 - updateGamePrize | 4 - insert play log");

    }

    @PostMapping("/get-game-config")
    @Transactional
    public ResponseEntity<?> getGameConfig(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId,
            @RequestBody GameConfigUpdateRequest request)
            throws BusinessException {
        return gameService.getGameConfig(request);

    }

    @PostMapping("/get-game-turn")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getGameTurn(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId,
            @RequestBody GameConfigUpdateRequest request)
            throws BusinessException {
        return gameService.getGameTurn(request);

    }

    @PostMapping("/get-game-his")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getGameHistory(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId,
            @RequestBody GameConfigUpdateRequest request)
            throws BusinessException {
        return gameService.getGameHistory(request);

    }

    @PostMapping("/get-admin-game-his")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getAdminGameHistory(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId,
            @RequestBody GameConfigUpdateRequest request)
            throws BusinessException {
        return gameService.getAdminGameHistory(request);

    }

    @PostMapping("/get-admin-game-user")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getAdminGameUser(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId,
            @RequestBody GameConfigUpdateRequest request)
            throws BusinessException {
        return gameService.getAdminGameUser(request);

    }

    @PostMapping("/get-admin-total-prize")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getAdminGameTotalPrize(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId,
            @RequestBody GameConfigUpdateRequest request)
            throws BusinessException {
        return gameService.getAdminTotalPrize(request);

    }

    @PostMapping("/get-admin-rate-by-day")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getAdminGameRateByDay(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId,
            @RequestBody GetRateByDayRequest request)
            throws BusinessException {
        return gameService.getAdminRateByDay(request);

    }

    @GetMapping("/get-game-win")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getGameWin(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId)
            throws BusinessException {
        return gameService.getGameWin();

    }

    @GetMapping("/get-game-day")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getGameDay(HttpServletRequest httpServletRequest,
            @RequestHeader("requestId") String requestId)
            throws BusinessException {

        return gameService.getGameDay();

    }

}