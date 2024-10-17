package com.example.mybatisplus.service;


import com.example.mybatisplus.model.domain.User;


public interface MailService {

    boolean sendPassword(User user);
}
