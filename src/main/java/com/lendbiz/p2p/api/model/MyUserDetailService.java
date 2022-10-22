package com.lendbiz.p2p.api.model;

import com.lendbiz.p2p.api.entity.User3GEntity;
import com.lendbiz.p2p.api.service.User3GService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    User3GService service;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User3GEntity userOp = service.getUserByName(username).orElseThrow(()->new UsernameNotFoundException("user not found"));
        return new MyUserDetails(userOp);
    }
}
