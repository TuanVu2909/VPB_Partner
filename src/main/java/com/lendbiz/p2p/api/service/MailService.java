package com.lendbiz.p2p.api.service;

import com.lendbiz.p2p.api.model.Mail;
import com.lendbiz.p2p.api.request.SendEmailRequest;
import com.lendbiz.p2p.api.request.VerifyEmailRequest;

import org.springframework.http.ResponseEntity;

public interface MailService {
    public ResponseEntity<?> sendEmail(Mail mail, SendEmailRequest request);

    public ResponseEntity<?> verifyEmail(VerifyEmailRequest request);
}
