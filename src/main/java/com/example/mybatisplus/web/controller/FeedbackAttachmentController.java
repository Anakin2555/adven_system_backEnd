package com.example.mybatisplus.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mybatisplus.model.domain.Application;
import com.example.mybatisplus.service.ApplicationService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.service.FeedbackAttachmentService;
import com.example.mybatisplus.model.domain.FeedbackAttachment;

import java.util.ArrayList;
import java.util.List;


/**
 * 前端控制器
 *
 * @author zyc&rgl
 * @version v1.0
 * @since 2022-02-27
 */
@Controller
@RequestMapping("/api/feedbackAttachment")
public class FeedbackAttachmentController {

    private final Logger logger = LoggerFactory.getLogger(FeedbackAttachmentController.class);

    @Autowired
    private FeedbackAttachmentService feedbackAttachmentService;

    @Autowired
    private ApplicationService applicationService;

    /**
     * 描述：根据Id 查询
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getById(@PathVariable("id") Long id) throws Exception {
        FeedbackAttachment feedbackAttachment = feedbackAttachmentService.getById(id);
        return JsonResponse.success(feedbackAttachment);
    }

    /**
     * 描述：根据Id删除
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse deleteById(@PathVariable("id") Long id) throws Exception {
        feedbackAttachmentService.removeById(id);
        return JsonResponse.success(null);
    }


    /**
     * 描述：根据Id 更新
     */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResponse updateFeedbackAttachment(FeedbackAttachment feedbackAttachment) throws Exception {
        feedbackAttachmentService.updateById(feedbackAttachment);
        return JsonResponse.success(null);
    }


    /**
     * 描述:创建FeedbackAttachment
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse create(FeedbackAttachment feedbackAttachment) throws Exception {
        feedbackAttachmentService.save(feedbackAttachment);
        return JsonResponse.success(null);
    }


    /**
     * 描述：下载该用户反馈时上传的附件
     */
    @RequestMapping(value = "/downloadFedAttach", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getByApplyId(Long activityId, Long userId) {
        QueryWrapper<Application> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId).eq("activity_id", activityId);
        Long applicationId = applicationService.getOne(wrapper).getId();
        List<FeedbackAttachment> list =feedbackAttachmentService.getByApplyId(applicationId);
        if (list == null) {
            return JsonResponse.failure("fail");
        } else {
            return JsonResponse.success(list);
        }
    }
}

