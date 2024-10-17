package com.example.mybatisplus.service;

import com.example.mybatisplus.model.domain.Activity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zyc&rgl
 * @since 2022-02-25
 */
public interface ActivityService extends IService<Activity> {


    Long saveAndGetId(Activity activity);
}
