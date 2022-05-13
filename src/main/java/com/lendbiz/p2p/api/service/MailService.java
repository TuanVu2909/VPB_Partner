package com.lendbiz.p2p.api.service;

import com.lendbiz.p2p.api.model.Mail;

import org.springframework.http.ResponseEntity;

public interface MailService {
    public ResponseEntity<?> sendEmail(Mail mail);
}
