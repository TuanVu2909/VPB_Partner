package com.lendbiz.p2p.api.service.impl;


import com.lendbiz.p2p.api.entity.Otp;
import com.lendbiz.p2p.api.repository.OtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OtpServiceImpl {
    @Autowired
    OtpRepository otpRepository;

    public void create (Otp otp){
        otpRepository.save(otp);
    }
    public void delete (){
        otpRepository.deleteAll();
    }
    public String findIdByOtp(String otp){
        return otpRepository.findIDByOtp(otp);
    }

}
