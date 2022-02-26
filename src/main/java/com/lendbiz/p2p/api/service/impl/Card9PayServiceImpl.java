package com.lendbiz.p2p.api.service.impl;

import com.lendbiz.p2p.api.entity.Card9PayEntity;
import com.lendbiz.p2p.api.exception.BusinessException;
import com.lendbiz.p2p.api.repository.Card9PayRepository;
import com.lendbiz.p2p.api.repository.PackageFilterRepository;
import com.lendbiz.p2p.api.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Card9PayServiceImpl extends BaseResponse<NinePayServiceImpl> {
    @Autowired
    Card9PayRepository card9PayRepository;
    @Autowired
    PackageFilterRepository filterRepository;

    public List<Card9PayEntity> getAll() {
        return card9PayRepository.findAll();
    }

    public ResponseEntity<?> getAllByCustId(String cif) {
        return response(toResult(card9PayRepository.findByCustId(cif)));
    }
    public ResponseEntity<?> findByDate(String sDate,String eDate,String cif) {
        return response(toResult(filterRepository.findTrans9PayByDate(sDate,eDate,cif)));
    }
    public void create(Card9PayEntity card9PayEntity) {
        try {
            card9PayRepository.save(card9PayEntity);
        } catch (Exception e) {
            throw new BusinessException("01", e.getMessage());
        }
    }
}
