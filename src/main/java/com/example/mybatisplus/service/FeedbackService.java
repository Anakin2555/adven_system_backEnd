package com.example.mybatisplus.service;

import com.example.mybatisplus.model.domain.Feedback;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zyc&rgl
 * @since 2022-02-27
 */
public interface FeedbackService extends IService<Feedback> {



    Feedback getByApplicationId(Long applicationId);

    List<Feedback> getAllFeedback(Long activityId);

    void saveAndGetId(Feedback feedback);

    List<Long> listIdByActivityId(Long activityId);
}
