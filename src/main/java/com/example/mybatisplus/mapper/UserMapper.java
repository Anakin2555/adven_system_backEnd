package com.example.mybatisplus.mapper;

import com.example.mybatisplus.model.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zyc&rgl
 * @since 2022-02-25
 */
public interface UserMapper extends BaseMapper<User> {

    void addMessageNumber(Long userId);
}
