package com.lendbiz.p2p.api.service.impl;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.lendbiz.p2p.api.model.Mail;
import com.lendbiz.p2p.api.response.BaseResponse;
import com.lendbiz.p2p.api.service.MailService;

@Service("mailService")
public class MailServiceImpl extends BaseResponse<MailService> implements MailService {

    @Autowired
    JavaMailSender mailSender;

    public ResponseEntity<?> sendEmail(Mail mail) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setSubject(mail.getMailSubject());
            mimeMessageHelper.setFrom(new InternetAddress(mail.getMailFrom(), "3Gang.com.vn"));
            mimeMessageHelper.setTo(mail.getMailTo());
            mimeMessageHelper.setText(mail.getMailContent());

            mailSender.send(mimeMessageHelper.getMimeMessage());

        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return response(toResult("ok"));
    }
}
