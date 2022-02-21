package com.lendbiz.p2p.api.service.impl;

import com.lendbiz.p2p.api.entity.Card9PayEntity;
import com.lendbiz.p2p.api.exception.BusinessException;
import com.lendbiz.p2p.api.repository.Card9PayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Card9PayServiceImpl {
    @Autowired
    Card9PayRepository card9PayRepository;
    public List<Card9PayEntity> getAll(){
        return card9PayRepository.findAll();
    }
    public void create(Card9PayEntity card9PayEntity){
        try {
            card9PayRepository.save(card9PayEntity);
        }catch (Exception e){
            throw new BusinessException("01",e.getMessage());
        }
        }
}
