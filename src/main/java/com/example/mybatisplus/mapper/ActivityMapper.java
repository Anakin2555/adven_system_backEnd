package com.example.mybatisplus.mapper;

import com.example.mybatisplus.model.domain.Activity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zyc&rgl
 * @since 2022-02-25
 */
public interface ActivityMapper extends BaseMapper<Activity> {

    Long saveAndGetId(Activity activity);
}
