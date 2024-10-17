package com.example.mybatisplus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mybatisplus.model.domain.User;
import com.example.mybatisplus.mapper.UserMapper;
import com.example.mybatisplus.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zyc&rgl
 * @since 2022-02-25
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Autowired
    UserMapper userMapper;

    @Override
    public User login(String account, String password) {
        QueryWrapper<User> wrapper=new QueryWrapper<>();
        wrapper.eq("user_account",account).eq("user_password",password);

        return userMapper.selectOne(wrapper);
    }

    @Override
    public User findPassword(String userName, String identifyNumber) {

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name",userName).eq("identify_number",identifyNumber);

        return userMapper.selectOne(wrapper);

    }

    @Override
    public void addMessageNumber(Long userId) {
        userMapper.addMessageNumber(userId);
    }
}
