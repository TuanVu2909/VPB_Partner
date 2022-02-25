package com.lendbiz.p2p.api.service.impl;

import java.util.List;

import com.lendbiz.p2p.api.constants.ErrorCode;
import com.lendbiz.p2p.api.entity.Card9PayEntity;
import com.lendbiz.p2p.api.entity.Card9PayEntity_v2;
import com.lendbiz.p2p.api.exception.BusinessException;
import com.lendbiz.p2p.api.repository.Card9PayRepository;
import com.lendbiz.p2p.api.repository.DynamicRepository;
import com.lendbiz.p2p.api.repository.PackageFilterRepository;
import com.lendbiz.p2p.api.repository.RoleRepository;
import com.lendbiz.p2p.api.response.BaseResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class Card9PayServiceImpl extends BaseResponse<NinePayServiceImpl> {
    @Autowired
    Card9PayRepository card9PayRepository;

    @Autowired
    PackageFilterRepository filter;

    @Autowired
    DynamicRepository dynamicRepository;

    @Autowired
    RoleRepository roleRepository;

    public List<Card9PayEntity> getAll() {
        return card9PayRepository.findAll();
    }

    public ResponseEntity<?> getAllByCustId(String cif) {
        return response(toResult(card9PayRepository.findByCustId(cif)));
    }

    public void create(Card9PayEntity card9PayEntity) {
        try {
            card9PayRepository.save(card9PayEntity);
        } catch (Exception e) {
            throw new BusinessException("01", e.getMessage());
        }
    }

    public ResponseEntity<?> getTranTest(String cif) {
        return response(toResult(card9PayRepository.findByCustId(cif)));
    }

    public ResponseEntity<?> getTransHistory(String cif) {
        List<Card9PayEntity_v2> lstCard9Pay;
        try {
            lstCard9Pay = dynamicRepository.findViaProcedure(cif);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.ERROR_500, ErrorCode.ERROR_500_DESCRIPTION);
        }
        
        if (lstCard9Pay.isEmpty()) {
            throw new BusinessException(ErrorCode.NO_DATA, ErrorCode.NO_DATA_DESCRIPTION);
        }

        return response(toResult(lstCard9Pay));
    }
}
