package com.example.mybatisplus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mybatisplus.model.domain.Activity;
import com.example.mybatisplus.mapper.ActivityMapper;
import com.example.mybatisplus.model.domain.User;
import com.example.mybatisplus.service.ActivityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zyc&rgl
 * @since 2022-02-25
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements ActivityService {

    @Autowired
    ActivityMapper activityMapper;

    @Override
    public Long saveAndGetId(Activity activity) {

        return activityMapper.saveAndGetId(activity);
    }
}
