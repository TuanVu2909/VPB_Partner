package com.lendbiz.p2p.api.service;

import com.lendbiz.p2p.api.entity.User3GEntity;

import java.util.Optional;

public interface User3GService {

    public Optional<User3GEntity> getUserByName(String username);

    public Boolean existsAllByUserName(String username);

    public void create(User3GEntity user3GEntity);
}
