package com.example.mybatisplus.mapper;

import com.example.mybatisplus.model.domain.Feedback;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatisplus.model.dto.Condition;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zyc&rgl
 * @since 2022-02-27
 */
public interface FeedbackMapper extends BaseMapper<Feedback> {



    Feedback getByApplicationId(Long applicationId);

    List<Feedback> getAllFeedback(Long activityId);

    void saveAndGetId(Feedback feedback);

    List<Long> listIdByActivityId(Long activityId);
}
