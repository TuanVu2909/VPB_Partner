package com.lendbiz.p2p.api.service;

import com.lendbiz.p2p.api.entity.AuthProfileEntity;
import com.lendbiz.p2p.api.entity.CustomUserDetails;
import com.lendbiz.p2p.api.repository.UserProfileRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class UserProfileService implements UserDetailsService {
    @Autowired
    UserProfileRepository userProfileRepository;
    @Override
    public UserDetails loadUserByUsername(String tlName) throws UsernameNotFoundException {
        // Kiểm tra xem user có tồn tại trong database không?
        AuthProfileEntity authProfile = userProfileRepository.findByTlName(tlName);
        if (authProfile == null) {
            throw new UsernameNotFoundException(tlName);
        }
        return new CustomUserDetails(authProfile);
    }


    // JWTAuthenticationFilter sẽ sử dụng hàm này
    @Transactional
    public CustomUserDetails loadUserById(String id) {
        AuthProfileEntity authProfile = userProfileRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found with id : " + id)
        );

        return new CustomUserDetails(authProfile);
    }
}
