package com.example.mybatisplus.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.model.domain.Application;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatisplus.model.dto.CollegeActivity;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zyc&rgl
 * @since 2022-02-25
 */
public interface ApplicationMapper extends BaseMapper<Application> {

    Page<CollegeActivity> getCollegeToBeAudited(Page page, String college);

    Page<CollegeActivity> getUniversityToBeAudited(Page<CollegeActivity> page);

    List<Long> listIdByActivityId(Long activityId);
}
