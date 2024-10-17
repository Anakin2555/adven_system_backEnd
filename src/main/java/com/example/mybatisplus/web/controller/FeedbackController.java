package com.example.mybatisplus.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.mapper.FeedbackMapper;
import com.example.mybatisplus.model.domain.Application;
import com.example.mybatisplus.model.domain.FeedbackAttachment;
import com.example.mybatisplus.model.dto.Condition;
import com.example.mybatisplus.service.ApplicationService;
import com.example.mybatisplus.service.FeedbackAttachmentService;
import com.example.mybatisplus.service.HighSchoolService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.service.FeedbackService;
import com.example.mybatisplus.model.domain.Feedback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 前端控制器
 *
 * @author zyc&rgl
 * @version v1.0
 * @since 2022-02-27
 */
@Controller
@RequestMapping("/api/feedback")
public class FeedbackController {

    private final Logger logger = LoggerFactory.getLogger(FeedbackController.class);

    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private FeedbackAttachmentService feedbackAttachmentService;
    @Autowired
    private HighSchoolService highSchoolService;

    /**
     * 描述：根据Id 查询
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getById(@PathVariable("id") Long id) throws Exception {
        Feedback feedback = feedbackService.getById(id);
        return JsonResponse.success(feedback);
    }

    /**
     * 描述：根据Id删除
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse deleteById(@PathVariable("id") Long id) throws Exception {
        feedbackService.removeById(id);
        return JsonResponse.success(null);
    }

    /**
     * 描述：根据Id 更新
     */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResponse updateFeedback(Feedback feedback) throws Exception {
        feedbackService.updateById(feedback);
        return JsonResponse.success(null);
    }

    /**
     * 描述:创建Feedback
     */
    @RequestMapping(value = "/addFeedback", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse create(@RequestBody Feedback feedback) throws Exception {
        System.out.println(feedback);
        QueryWrapper<Application> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", feedback.getUserId()).eq("activity_id", feedback.getActivityId());
        Long applicationId = applicationService.getOne(wrapper).getId();
        QueryWrapper<Feedback> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("application_id", applicationId);
        if (feedbackService.getOne(wrapper1) == null) {
            feedback.setApplicationId(applicationId);
            feedback.setIsDeleted(false);
            feedbackService.saveAndGetId(feedback);
            feedback.getAttachments().forEach(feedbackAttachment -> {
                feedbackAttachment.setFeedbackId(feedback.getId());
                feedbackAttachment.setIsDeleted(false);
                feedbackAttachment.setAttachment(feedbackAttachment.getAttachment().replace("/file",""));
            });
            feedbackAttachmentService.saveBatch(feedback.getAttachments());

            return JsonResponse.success(null);
        } else {
            return JsonResponse.failure("feedback existed");
        }
    }

    /**
     * 描述：根据用户的申请id查询对应反馈
     */
    @RequestMapping(value = "/getMyFeedback", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getMyFeedback(Long applicationId) {
        Feedback feedback = feedbackService.getByApplicationId(applicationId);

        return JsonResponse.success(feedback);
    }

    /**
     * 描述：根据活动id以及限制条件查询所有反馈
     * <p>
     * 不能改
     */
    @RequestMapping(value = "/getAllFeedbackByActivityId", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getAllFeedback(@RequestParam(required = false, defaultValue = "1") Integer pageNo,
                                       @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                       Long activityId , String type) {
        Page<Feedback> page = new Page<>(pageNo,pageSize);
        QueryWrapper<Feedback> wrapper = new QueryWrapper<Feedback>().eq("activity_id", activityId).orderByDesc("id");
        if(!type.isEmpty()){
            wrapper.eq("type",type);
        }
        List<Feedback> list = feedbackService.page(page, wrapper).getRecords();
        for (Feedback f : list) {
            f.setAttachments(feedbackAttachmentService.list(new QueryWrapper<FeedbackAttachment>().eq("feedback_id",f.getId())));

            Application application = applicationService.getById(f.getApplicationId());

            f.setHighSchool(highSchoolService.getById(application.getHighSchoolId()).getSchoolName());
            f.setRegion(application.getRegion());
        }
        page.setRecords(list);
        return JsonResponse.success(page);
    }

    /**
     * 根据管理员的用户类型以及查询字段查询所有反馈
     *
     *
     */
    @RequestMapping(value = "/getAllFeedbackByAdmin" ,method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getAllFeedbackByAdmin(@RequestParam(required = false, defaultValue = "1") Integer pageNo,
                                              @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                              String type ,String searchString){
        Page<Feedback> page = new Page<>(pageNo, pageSize);
        QueryWrapper<Feedback> wrapper = new QueryWrapper<>();
        if(type.contains("学院")){
            wrapper.eq("college",type);
        }
        if(!searchString.isEmpty()){
            wrapper.like("activity_name",searchString);
        }
        feedbackService.page(page,wrapper);
        List<Feedback> feedbacks= page.getRecords();
        feedbacks.forEach(f -> {
            Application application = applicationService.getById(f.getApplicationId());
            f.setHighSchool(highSchoolService.getById(application.getHighSchoolId()).getSchoolName());
            f.setRegion(application.getRegion());
            f.setAttachments(feedbackAttachmentService.list(new QueryWrapper<FeedbackAttachment>().eq("feedback_id",f.getId())));
        });
        page.setRecords(feedbacks);
        return JsonResponse.success(page);
    }

    /**
     *
     * 删除反馈
     *
     */

    @RequestMapping("/deleteOne")
    @ResponseBody
    public JsonResponse deleteOne(Long feedbackId){
        feedbackAttachmentService.remove(new QueryWrapper<FeedbackAttachment>().eq("feedback_id", feedbackId));
        feedbackService.removeById(feedbackId);
        return JsonResponse.success(null);
    }

    /**
     *
     * 批量删除活动反馈
     *
     *
     */
    @RequestMapping(value = "/deleteMultiply" , method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse deleteMultiple(@RequestParam String feedbackIds){

        System.out.println(feedbackIds);

        if(feedbackIds.contains("[")){
            feedbackIds=feedbackIds.replace("[","");
            feedbackIds=feedbackIds.replace("]","");
        }
        String[] Ids=feedbackIds.split(",");
        List<Long> ids = new ArrayList<>();
        new ArrayList<>(Arrays.asList(Ids)).forEach(Id-> ids.add(Long.parseLong(Id)));

        feedbackAttachmentService.remove(new QueryWrapper<FeedbackAttachment>().in("feedback_id", ids));
        feedbackService.remove(new QueryWrapper<Feedback>().in("id",ids));
        return JsonResponse.success(null);
    }
}