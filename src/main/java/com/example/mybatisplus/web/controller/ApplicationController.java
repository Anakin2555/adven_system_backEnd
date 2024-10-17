package com.example.mybatisplus.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.model.domain.*;
import com.example.mybatisplus.service.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.mybatisplus.common.JsonResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 前端控制器
 *
 * @author zyc&rgl
 * @version v1.0
 * @since 2022-02-25
 */
@Controller
@RequestMapping("/api/application")
public class ApplicationController {

    private final Logger logger = LoggerFactory.getLogger(ApplicationController.class);

    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private UserService userService;
    @Autowired
    private HighSchoolService highSchoolService;
    @Autowired
    private RegionService regionService;
    @Autowired
    private ActivityRegionService activityRegionService;
    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private FeedbackAttachmentService feedbackAttachmentService;

    /**
     * 描述：根据Id 查询
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getById(@PathVariable("id") Long id) throws Exception {
        Application application = applicationService.getById(id);
        return JsonResponse.success(application);
    }

    /**
     * 描述：根据Id删除
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse deleteById(@PathVariable("id") Long id) throws Exception {
        applicationService.removeById(id);
        return JsonResponse.success(null);
    }

    /**
     * 描述：根据Id 更新
     */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResponse updateApplication(Application application) {
        applicationService.updateById(application);
        return JsonResponse.success(null);
    }

    /**
     * 描述:创建Application
     */
    @RequestMapping(value = "/createApplication", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse create(@RequestBody Application application) throws Exception {

        if(applicationService.getOne(new QueryWrapper<Application>().eq("user_id",application.getUserId()).eq("activity_id",application.getActivityId()))!=null){
            return JsonResponse.failure("重复报名");
        }

        Activity activity = activityService.getById(application.getActivityId());
        User user = userService.getById(application.getUserId());
        application.setType(activity.getType());
        if (activity.getCollegeAudit()) {
            application.setCollegePass(0);
        } else {
            application.setCollegePass(-1);
        }
        if (activity.getUniversityAudit()) {
            application.setUniversityPass(0);
        } else {
            application.setUniversityPass(-1);
        }
        application.setReject(false);
        application.setUserName(user.getUserName());
        application.setCollege(user.getCollege());
        if (user.getType().equals("1")) {//学生
            application.setGpa(user.getGpa());
            application.setGrade(user.getGrade());
            application.setUserType("1");
        } else {//教师
            application.setUserType("2");
        }
        if (application.getHighSchoolId() != null) {
            application.setRegion(regionService.getById(highSchoolService.getById(application.getHighSchoolId()).getRegionId()).getRegionName());
        } else {
            application.setRegion("全部地区");
        }
        application.setIsDeleted(false);
        applicationService.save(application);
        return JsonResponse.success(null);
    }

    /**
     * 以下为接口设计代码
     * <p>
     * author:zyc&rgl
     ********************************************************************************************/
    @RequestMapping(value = "/pass", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse pass(String userType, Long userId, Long activityId) {
        QueryWrapper<Application> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId).eq("activity_id", activityId);
        Application application = applicationService.getOne(wrapper);
        if (userType.contains("学院")) {
            application.setCollegePass(1);
        } else if (userType.contains("学校")) {
            application.setUniversityPass(1);
            //将地区当前通过用户加一
            ActivityRegion activityRegionServiceOne = activityRegionService.getOne(new QueryWrapper<ActivityRegion>()
                    .eq("activity_id", application.getActivityId())
                    .eq("region_name", application.getRegion()));

            if (application.getUserType().equals("1")) {
                System.out.println(1);
                activityRegionServiceOne.setCurStudent(activityRegionServiceOne.getCurStudent() + 1);
            } else {
                activityRegionServiceOne.setCurTeacher(activityRegionServiceOne.getCurTeacher() + 1);
            }
            activityRegionService.updateById(activityRegionServiceOne);
        } else {
            return JsonResponse.failure("没有权限");
        }
        applicationService.updateById(application);
        //更新用户提醒
        userService.addMessageNumber(userId);
        return JsonResponse.success(null);
    }

    @RequestMapping(value = "/reject", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse reject(String userType, Long userId, Long activityId) {
        if (!(userType.contains("学院") || userType.contains("学校"))) {
            return JsonResponse.failure("没有权限");
        }
        UpdateWrapper<Application> wrapper = new UpdateWrapper<>();
        wrapper.set("reject", 1).eq("activity_id", activityId).eq("user_id", userId);
        applicationService.update(wrapper);
        //更新用户提醒
        userService.addMessageNumber(userId);
        return JsonResponse.success(null);
    }

    /**
     * 批量审核和拒绝
     */
    @RequestMapping("/passMultiply")
    @ResponseBody
    public JsonResponse passMultiply(@RequestBody Map<String, String> map) {
        String userType = map.get("userType");
        String idsString = map.get("userIds");
        String[] userIdsString = idsString.split(",");
        List<Long> userIds = new ArrayList<>();
        for (String s : userIdsString) {
            userIds.add(Long.parseLong(s));
        }
        Long activityId = Long.parseLong(map.get("activityId"));
        for (Long userId : userIds) {
            pass(userType, userId, activityId);
        }
        return JsonResponse.success(null);
    }

    @RequestMapping("/rejectMultiply")
    @ResponseBody
    public JsonResponse rejectMultiply(@RequestBody Map<String, String> map) {
        String userType = map.get("userType");
        String idsString = map.get("userIds");
        idsString = idsString.replace("[", "");
        idsString = idsString.replace("]", "");
        String[] userIdsString = idsString.split(",");
        List<Long> userIds = new ArrayList<>();
        for (String s : userIdsString) {
            userIds.add(Long.parseLong(s));
        }
        Long activityId = Long.parseLong(map.get("activityId"));
        if (!(userType.contains("学院") || userType.contains("学校"))) {
            return JsonResponse.failure("没有权限");
        }
        UpdateWrapper<Application> wrapper = new UpdateWrapper<>();
        wrapper.set("reject", 1).eq("activity_id", activityId).in("user_id", userIds);
        applicationService.update(wrapper);
        //更新用户提醒
        userIds.forEach(aLong -> userService.addMessageNumber(aLong));
        return JsonResponse.success(null);
    }

    @RequestMapping(value = "/getAuditStatus", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getAuditStatus(Long userId, Long activityId) {
        Map<String, String> map = new HashMap<>();
        Application application = applicationService.getOne(new QueryWrapper<Application>().eq("user_id", userId).eq("activity_id", activityId));
        if (application == null) {
            return JsonResponse.failure("fail");
        }
        map.put("collegePass", application.getCollegePass().toString());
        map.put("universityPass", application.getUniversityPass().toString());
        map.put("reject", application.getReject().toString());
        return JsonResponse.success(map);
    }


    @RequestMapping(value = "/getApplicationByRegion", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getApplicationByRegion(@RequestParam(required = false, defaultValue = "1") Integer pageNo,
                                               @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                               String type, String region, Long activityId, String searchString) {
        Page<Application> page = new Page<>(pageNo, pageSize);
        QueryWrapper<Application> wrapper = new QueryWrapper<Application>().eq("reject", 0).eq("region", region).eq("activity_id", activityId);
        if (type.contains("学院")) {
            wrapper.eq("college", type).eq("college_pass", 0);
        }
        if(type.contains("学校")){
            wrapper.eq("university_pass",0);
        }
        if (searchString != null && !searchString.isEmpty()) {
            wrapper.like("user_name", searchString);
        }
        applicationService.page(page, wrapper);
        if (!region.equals("全部地区")) {
            page.getRecords().forEach(application -> application.setSchoolName(highSchoolService.getById(application.getHighSchoolId()).getSchoolName()));
        }
        return JsonResponse.success(page);
    }


    @RequestMapping("/deleteOne")
    @ResponseBody
    public JsonResponse deleteOne(Long activityId,Long userId){

        Feedback feedback =feedbackService.getOne(new QueryWrapper<Feedback>().eq("user_id",userId).eq("activity_id",activityId));

        if(feedback!=null){
            feedbackAttachmentService.remove(new QueryWrapper<FeedbackAttachment>().eq("feedback_id",feedback.getId()));
            feedbackService.removeById(feedback.getId());
        }

        applicationService.remove(new QueryWrapper<Application>().eq("user_id",userId).eq("activity_id",activityId));



        return JsonResponse.success(null);
    }
}
