package com.example.mybatisplus.service;

import com.example.mybatisplus.model.domain.Token;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mybatisplus.model.domain.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zyc&rgl
 * @since 2022-03-02
 */
public interface TokenService extends IService<Token> {
    public Token getToken(User user);
}
