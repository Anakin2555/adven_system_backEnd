package com.example.mybatisplus.service;

import com.example.mybatisplus.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zyc&rgl
 * @since 2022-02-25
 */
public interface UserService extends IService<User> {

    User login(String userAccount,String userPassword);

    User findPassword(String userName, String identifyNumber);

    void addMessageNumber(Long userId);
}
