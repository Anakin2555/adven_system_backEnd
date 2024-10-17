package com.example.mybatisplus.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.model.domain.*;
import com.example.mybatisplus.model.dto.CollegeActivity;
import com.example.mybatisplus.service.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.mybatisplus.common.JsonResponse;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;


/**
 * 前端控制器
 *
 * @author zyc&rgl
 * @version v1.0
 * @since 2022-02-25
 */
@Controller
@RequestMapping("/api/activity")
public class ActivityController {

    private final Logger logger = LoggerFactory.getLogger(ActivityController.class);

    @Autowired
    private ActivityService activityService;
    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private RegionService regionService;
    @Autowired
    private ActivityRegionService activityRegionService;
    @Autowired
    private ActivityAttachmentService activityAttachmentService;
    @Autowired
    private ActivityRuleService activityRuleService;
    @Autowired
    private HighSchoolService highSchoolService;
    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private FeedbackAttachmentService feedbackAttachmentService;

    /**
     * 描述：根据Id 查询
     * 包含了除活动基本信息外，还有各种地区、学校信息，以及活动报名规则和活动附件(业务逻辑层实现多表查询)
     */
    @RequestMapping(value = "/getOne", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getById(@RequestParam("id") Long id) throws Exception {
        Activity activity = activityService.getById(id);
        activity.setRegions(activityRegionService.list(new QueryWrapper<ActivityRegion>().eq("activity_id", activity.getId())));
        for (ActivityRegion ar : activity.getRegions()) {
            ar.setHighSchoolList(highSchoolService.list(new QueryWrapper<HighSchool>().eq("region_id", ar.getRegionId())));
        }
        activity.setAttachments(activityAttachmentService.list(new QueryWrapper<ActivityAttachment>().eq("activity_id", activity.getId())));
        activity.setCollege(activityRuleService.list(new QueryWrapper<ActivityRule>().eq("activity_id", activity.getId()).isNull("grade")));
        activity.setGrade(activityRuleService.list(new QueryWrapper<ActivityRule>().eq("activity_id", activity.getId()).isNull("college")));
        return JsonResponse.success(activity);
    }

    /**
     * 描述：根据Id删除
     */
    @RequestMapping(value = "/deleteActivity", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse deleteById(Long id) throws Exception {
        activityService.removeById(id);
        return JsonResponse.success(null);
    }

    /**
     * 描述：根据Id 更新
     */
    @RequestMapping(value = "/editActivity", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse updateActivity(Activity activity) throws Exception {
        activityService.updateById(activity);
        return JsonResponse.success(null);
    }



    /**
     * 描述:创建Activity
     */
    @RequestMapping(value = "/createActivity", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse create(@RequestBody Activity activity) throws Exception {
        //处理上传文件的路径
        activity.setBanner(activity.getBanner().replace("/file", ""));
        activity.setCover(activity.getCover().replace("/file", ""));
        for (ActivityAttachment activityAttachment : activity.getAttachments()) {
            activityAttachment.setAttachment(activityAttachment.getAttachment().replace("/file", ""));
        }
        activityService.saveAndGetId(activity);
        if (activity.getId() != 0) {
            //设置地区以及地区老师和学生的数量
            for (ActivityRegion region : activity.getRegions()) {
                if (region.getMaxStudent() == 0 && region.getMaxTeacher() == 0) {
                    activity.getRegions().remove(region);
                } else {
                    region.setCurStudent(0);
                    region.setCurTeacher(0);
                    region.setIsDeleted(false);
                    region.setActivityId(activity.getId());
                    QueryWrapper<Region> wrapper = new QueryWrapper<>();
                    wrapper.eq("region_name", region.getRegionName());
                    region.setRegionId(regionService.getOne(wrapper).getId());
                }
            }
            activityRegionService.saveBatch(activity.getRegions());
            //设置创建活动的附件
            for (ActivityAttachment a :
                    activity.getAttachments()) {
                a.setActivityId(activity.getId());
                a.setIsDeleted(false);
            }
            activityAttachmentService.saveBatch(activity.getAttachments());
            //设置允许参加的学院、年级
            for (ActivityRule activityRule : activity.getCollege()) {
                activityRule.setActivityId(activity.getId());
                activityRule.setIsDeleted(false);
            }
            for (ActivityRule activityRule : activity.getGrade()) {
                activityRule.setActivityId(activity.getId());
                activityRule.setIsDeleted(false);
            }
            activityRuleService.saveBatch(activity.getCollege());
            activityRuleService.saveBatch(activity.getGrade());
            return JsonResponse.success(null);
        } else {
            return JsonResponse.message(false, "fail");
        }
    }

    /**
     * 以下为接口设计代码
     *
     * author:zyc
     * ********************************************************************************************/

    /**
     * 描述：根据前端传递的分页信息进行分页查询
     * <p>
     * 接口：/getAllActivity
     * <p>
     * 请求类型: get
     * <p>
     * 参数：pageNo default=1 , pageSIze default=10
     * <p>
     * 返回体：分号页面的活动， JsonResponse list<Activity>
     */
    @RequestMapping(value = "/getAllActivity", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getAllActivities(@RequestParam(required = false, defaultValue = "1") Integer pageNo,
                                         @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                         String type, String searchString) throws Exception {
        Page<Activity> page = new Page<>(pageNo, pageSize);
        QueryWrapper<Activity> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        if (type != null) {
            if (type.equals("线上活动")) {
                wrapper.eq("type", "线上活动");
            } else if (type.equals("线下活动")) {
                wrapper.eq("type", "线下活动");
            }
        }
        if (searchString != null) {
            wrapper.like("title", searchString).like("information", searchString);
        }

        List<Activity> activities = activityService.page(page, wrapper).getRecords();
        for (Activity a : activities) {
            QueryWrapper<ActivityRegion> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("activity_id", a.getId());
            a.setRegions(activityRegionService.list(wrapper1));
        }
        page.setRecords(activities);
        return JsonResponse.success(page);
    }

    /**
     * 描述：根据前端传递的用户信息与分页信息进行查询
     * <p>
     * 接口：/getMyActivity
     * <p>
     * 请求类型: get
     * <p>
     * 参数：user, pageNo default=1 , pageSIze default=10
     * <p>
     * 返回体：用户参与的且分好页面的活动， JsonResponse list<Activity>
     */
    @RequestMapping(value = "/getMyActivity", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getMyActivity(@RequestParam(required = false, defaultValue = "1") Integer pageNo,
                                      @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                      Long userId) {
        QueryWrapper<Application> applicationQueryWrapper = new QueryWrapper<>();
        applicationQueryWrapper.eq("user_id", userId).orderByDesc("id");
        List<Application> applications = applicationService.list(applicationQueryWrapper);
        List<Long> activityId = new ArrayList<>();
        for (Application e : applications) {
            activityId.add(e.getActivityId());
        }
        if (activityId.isEmpty()) {
            return JsonResponse.failure("没有活动");
        }
        QueryWrapper<Activity> activityQueryWrapper = new QueryWrapper<>();
        activityQueryWrapper.in("id", activityId);
        Page<Activity> page = new Page<>(pageNo, pageSize);
        List<Activity> activities = activityService.page(page, activityQueryWrapper).getRecords();
        int i = 0;
        for (Activity a : activities) {
            //设置地区
            a.setRegions(activityRegionService.list(new QueryWrapper<ActivityRegion>().eq("activity_id", a.getId())));

            Application application = applicationService.getOne(new QueryWrapper<Application>().eq("activity_id", a.getId())
                    .eq("user_id",userId));
            a.setCollegePass(application.getCollegePass());
            a.setUniversityPass(application.getUniversityPass());
            a.setReject(application.getReject());
            ++i;
        }
        page.setRecords(activities);
        return JsonResponse.success(page);
    }

    /**
     * 获取一个学院中的所有待审核活动
     * 参数：学院
     * <p>
     * 活动列表：本活动在当前学院的申请条数、活动基本信息、当前活动完全通过人数
     * <p>
     * map(String,Object)
     */
    @RequestMapping(value = "/getToBeAudited", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getCollegeToBeAudited(@RequestParam(required = false, defaultValue = "1") Integer pageNo,
                                              @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                              String college) {
        Page<CollegeActivity> page = new Page<>(pageNo,pageSize);
        if(college==null){
            applicationService.getUniversityToBeAudited(page);
        }else{
            applicationService.getCollegeToBeAudited(page,college);
        }
        List<CollegeActivity> collegeActivityArrayList = page.getRecords();
        for (CollegeActivity c : collegeActivityArrayList) {
            //存放活动基本信息
            Activity activity = activityService.getById(c.getActivityId());
            //存放活动中的地区信息
            activity.setRegions(activityRegionService.list(new QueryWrapper<ActivityRegion>()
                    .eq("activity_id",activity.getId())));
            c.setActivity(activity);
            //存放该活动中所有已经通过审核的人数
            activity.getRegions().forEach(activityRegion -> {
                c.setCountAuditedStudent(c.getCountAuditedStudent()+activityRegion.getCurStudent());
                c.setCountAuditedTeacher(c.getCountAuditedTeacher()+activityRegion.getCurTeacher());
            });
        }
        page.setRecords(collegeActivityArrayList);
        return JsonResponse.success(page);
    }

    /**
     *
     * 获取该活动剩余可参加的人数
     *
     */
    @RequestMapping("/getRestCount")
    @ResponseBody
    public JsonResponse getRestCount(Long activityId){
        AtomicReference<Integer> count= new AtomicReference<>(0);
        activityRegionService.list(new QueryWrapper<ActivityRegion>().eq("activity_id",activityId)).forEach(
                activityRegion -> count.updateAndGet(v -> v + (activityRegion.getMaxStudent() + activityRegion.getMaxTeacher() - activityRegion.getCurTeacher() - activityRegion.getCurStudent()))
        );
        return JsonResponse.success(count.get());
    }

    /**
     *
     * 删除活动，根据id
     *
     *
     */
    @RequestMapping("/deleteOne")
    @ResponseBody
    public JsonResponse deleteOne(Long activityId){
        //删除关联地区
        activityRegionService.remove(new QueryWrapper<ActivityRegion>().eq("activity_id",activityId));
        //删除活动附件
        activityAttachmentService.remove(new QueryWrapper<ActivityAttachment>().eq("activity_id",activityId));
        //删除报名规则
        activityRuleService.remove(new QueryWrapper<ActivityRule>().eq("activity_id",activityId));


        //获取对应反馈id
        List<Long> feedbackIds=feedbackService.listIdByActivityId(activityId);
        if(!feedbackIds.isEmpty()){
            //删除对应反馈附件
            feedbackAttachmentService.remove(new QueryWrapper<FeedbackAttachment>().in("feedback_id",feedbackIds));
            //删除对应反馈
            feedbackService.remove(new QueryWrapper<Feedback>().eq("activity_id",activityId));
        }
        //删除对应申请
        applicationService.remove(new QueryWrapper<Application>().eq("activity_id",activityId));
        //删除该活动
        activityService.removeById(activityId);
        return JsonResponse.success(null);

    }
}