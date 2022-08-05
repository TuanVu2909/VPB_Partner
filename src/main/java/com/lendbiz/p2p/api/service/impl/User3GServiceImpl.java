package com.lendbiz.p2p.api.service.impl;

import com.lendbiz.p2p.api.constants.ErrorCode;
import com.lendbiz.p2p.api.entity.User3GEntity;
import com.lendbiz.p2p.api.exception.BusinessException;
import com.lendbiz.p2p.api.repository.User3GRepository;
import com.lendbiz.p2p.api.response.BaseResponse;
import com.lendbiz.p2p.api.service.User3GService;
import com.lendbiz.p2p.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class User3GServiceImpl implements User3GService {

    @Autowired
    User3GRepository user3GRepository;
    @Override
    public Optional<User3GEntity> getUserByName(String username) {

        return user3GRepository.getUserByUserName(username);
    }

    @Override
    public Boolean existsAllByUserName(String username) {
        return user3GRepository.existsAllByUserName(username);
    }

    @Override
    public void create(User3GEntity user3GEntity) {
        if (user3GRepository.existsAllByUserName(user3GEntity.getUserName())){
            throw new BusinessException("01", "Account already exists");
        }
        user3GRepository.saveUser(user3GEntity.getUserName(),user3GEntity.getPassword(),user3GEntity.getRole());
    }


}
