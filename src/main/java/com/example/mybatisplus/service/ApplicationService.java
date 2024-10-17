package com.example.mybatisplus.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.model.domain.Application;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mybatisplus.model.dto.CollegeActivity;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zyc&rgl
 * @since 2022-02-25
 */
public interface ApplicationService extends IService<Application> {


    Page<CollegeActivity> getCollegeToBeAudited(Page page, String college);

    Page<CollegeActivity> getUniversityToBeAudited(Page<CollegeActivity> page);

    List<Long> listIdByActivityId(Long activityId);
}
