package com.lendbiz.p2p.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lendbiz.p2p.api.repository.WheelRepository;
import com.lendbiz.p2p.api.request.WheelConfigUpdateRequest;
import com.lendbiz.p2p.api.response.BaseResponse;
import com.lendbiz.p2p.api.service.WheelService;

@Service
public class WheelServiceImpl extends BaseResponse<WheelService> implements WheelService {

    @Autowired
    WheelRepository wheelRepository;

    @Override
    public ResponseEntity<?> updateWheelConfig(WheelConfigUpdateRequest request) {

        return response(toResult(wheelRepository.updateWheelConfig(request.getId(), request.getRate(),
                request.getGiftId(), request.getGroupId(),
                request.getStatus())));
    }

    @Override
    public ResponseEntity<?> updateWheelGroupTime(WheelConfigUpdateRequest request) {
        return response(toResult(
                wheelRepository.updateWheelGroupTime(request.getGroupId(), request.getFromTime(), request.getToTime(),
                        request.getFromDate(), request.getToDate(), request.getStatus(), request.getMaxPrize())));
    }

    @Override
    public ResponseEntity<?> updateWheelPrize(WheelConfigUpdateRequest request) {
        return response(toResult(wheelRepository.updateWheelPrize(request.getId(), request.getName(),
                request.getStatus(), request.getRAmount())));
    }

    @Override
    public ResponseEntity<?> insertWheelHistory(WheelConfigUpdateRequest request) {
        return response(toResult(
                wheelRepository.insertWheelHistory(request.getCustId(), request.getStatus(), request.getGiftId())));
    }

}
