package com.lendbiz.p2p.api.service;

import org.springframework.http.ResponseEntity;

import com.lendbiz.p2p.api.request.WheelConfigUpdateRequest;

public interface WheelService {

    ResponseEntity<?> updateWheelConfig(WheelConfigUpdateRequest request);

    ResponseEntity<?> updateWheelGroupTime(WheelConfigUpdateRequest request);

    ResponseEntity<?> updateWheelPrize(WheelConfigUpdateRequest request);

    ResponseEntity<?> insertWheelHistory(WheelConfigUpdateRequest request);
}