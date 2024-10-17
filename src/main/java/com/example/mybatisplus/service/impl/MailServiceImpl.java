package com.example.mybatisplus.service.impl;

import com.example.mybatisplus.config.MailConfig;
import com.example.mybatisplus.model.domain.User;
import com.example.mybatisplus.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;


@Service
public class MailServiceImpl implements MailService {

    @Autowired
    MailSender mailSender;
    @Autowired
    MailConfig mailConfig;

    @Override
    public boolean sendPassword(User user) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailConfig.getUsername());
        message.setTo(user.getEmail());
        message.setSubject("APR-System 系统消息--密码找回");
        message.setText("您当前的账号为:"+user.getUserAccount()+"\n"+"您当前的密码为:"+user.getUserPassword());

        try {
            mailSender.send(message);
            return true;
        } catch (MailException e) {
            e.printStackTrace();
            return false;
        }


    }
}
