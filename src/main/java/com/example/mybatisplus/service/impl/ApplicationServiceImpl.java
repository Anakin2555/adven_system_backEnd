package com.example.mybatisplus.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.model.domain.Application;
import com.example.mybatisplus.mapper.ApplicationMapper;
import com.example.mybatisplus.model.dto.CollegeActivity;
import com.example.mybatisplus.service.ApplicationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zyc&rgl
 * @since 2022-02-25
 */
@Service
public class ApplicationServiceImpl extends ServiceImpl<ApplicationMapper, Application> implements ApplicationService {


    @Autowired
    ApplicationMapper applicationMapper;


    @Override
    public Page<CollegeActivity> getCollegeToBeAudited(Page page, String college) {
        return applicationMapper.getCollegeToBeAudited(page,college);
    }

    @Override
    public Page<CollegeActivity> getUniversityToBeAudited(Page<CollegeActivity> page) {
        return applicationMapper.getUniversityToBeAudited(page);



    }

    @Override
    public List<Long> listIdByActivityId(Long activityId) {
        return applicationMapper.listIdByActivityId(activityId);
    }
}
