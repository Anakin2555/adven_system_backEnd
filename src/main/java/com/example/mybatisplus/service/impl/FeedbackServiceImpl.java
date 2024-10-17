package com.example.mybatisplus.service.impl;

import com.example.mybatisplus.model.domain.Feedback;
import com.example.mybatisplus.mapper.FeedbackMapper;
import com.example.mybatisplus.service.FeedbackService;
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
 * @since 2022-02-27
 */
@Service
public class FeedbackServiceImpl extends ServiceImpl<FeedbackMapper, Feedback> implements FeedbackService {

    @Autowired
    FeedbackMapper feedbackMapper;



    @Override
    public Feedback getByApplicationId(Long applicationId) {
        return feedbackMapper.getByApplicationId(applicationId);
    }

    @Override
    public List<Feedback> getAllFeedback(Long activityId) {


        return feedbackMapper.getAllFeedback(activityId);
    }

    @Override
    public void saveAndGetId(Feedback feedback) {
        feedbackMapper.saveAndGetId(feedback);
    }

    @Override
    public List<Long> listIdByActivityId(Long activityId) {

        return feedbackMapper.listIdByActivityId(activityId);
    }
}
