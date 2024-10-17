package com.example.mybatisplus.mapper;

import com.example.mybatisplus.model.domain.Token;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zyc&rgl
 * @since 2022-03-02
 */

public interface TokenMapper extends BaseMapper<Token> {


    Token selectByUserId(long id);
}
