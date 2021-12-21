package com.lendbiz.p2p.api.service.impl;

import com.google.common.base.Optional;
import com.lendbiz.p2p.api.entity.CfRelation;
import com.lendbiz.p2p.api.entity.IdCard;
import com.lendbiz.p2p.api.repository.IdCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IdCardImpl {
    @Autowired
    IdCardRepository idCardRepository;

    public Iterable<IdCard> findAll() {
        return idCardRepository.findAll();
    }
    
    public IdCard create(IdCard idCard) {
        return idCardRepository.save(idCard);
    }
}
