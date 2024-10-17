package com.example.mybatisplus.web.controller;

import ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.service.ActivityAttachmentService;
import com.example.mybatisplus.model.domain.ActivityAttachment;

import java.util.ArrayList;
import java.util.List;


/**
 *
 *  前端控制器
 *
 *
 * @author zyc&rgl
 * @since 2022-02-27
 * @version v1.0
 */
@Controller
@RequestMapping("/api/activityAttachment")
public class ActivityAttachmentController {

    private final Logger logger = LoggerFactory.getLogger( ActivityAttachmentController.class );

    @Autowired
    private ActivityAttachmentService activityAttachmentService;

    /**
    * 描述：根据Id 查询
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getById(@PathVariable("id") Long id)throws Exception {
        ActivityAttachment  activityAttachment =  activityAttachmentService.getById(id);
        return JsonResponse.success(activityAttachment);
    }

    /**
    * 描述：根据Id删除
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse deleteById(@PathVariable("id") Long id) throws Exception {
        activityAttachmentService.removeById(id);
        return JsonResponse.success(null);
    }

    /**
    * 描述：根据Id 更新
    *
    */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResponse updateActivityAttachment(ActivityAttachment  activityAttachment) throws Exception {
        activityAttachmentService.updateById(activityAttachment);
        return JsonResponse.success(null);
    }

    /**
    * 描述:创建ActivityAttachment
    *
    */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse create(ActivityAttachment  activityAttachment) throws Exception {
        activityAttachmentService.save(activityAttachment);
        return JsonResponse.success(null);
    }

    /**
     * 描述：下载活动的附件
     *
     * 接口：/downloadActAttach
     *
     * 请求类型: get
     *
     * 参数 ：activityId
     *
     * 返回体：JsonResponse (url)
     */
    @RequestMapping(value = "/download" ,method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse downloadActAttach(@RequestParam("id") Long activityId){
        List<String> list = new ArrayList<>();
        QueryWrapper<ActivityAttachment> wrapper=new QueryWrapper<>();
        wrapper.eq("activity_id",activityId);
        for (ActivityAttachment a : activityAttachmentService.list(wrapper)) {
            list.add(a.getAttachment());
        }
        if (list.isEmpty()) {
            return JsonResponse.failure("fail");
        } else {
            return JsonResponse.success(list);
        }
    }
}

