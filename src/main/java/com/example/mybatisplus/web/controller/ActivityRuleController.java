package com.example.mybatisplus.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.service.ActivityRuleService;
import com.example.mybatisplus.model.domain.ActivityRule;


/**
 *
 *  前端控制器
 *
 *
 * @author zyc&rgl
 * @since 2022-03-04
 * @version v1.0
 */
@Controller
@RequestMapping("/api/activityRule")
public class ActivityRuleController {

    private final Logger logger = LoggerFactory.getLogger( ActivityRuleController.class );

    @Autowired
    private ActivityRuleService activityRuleService;

    /**
    * 描述：根据Id 查询
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getById(@PathVariable("id") Long id)throws Exception {
        ActivityRule  activityRule =  activityRuleService.getById(id);
        return JsonResponse.success(activityRule);
    }

    /**
    * 描述：根据Id删除
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse deleteById(@PathVariable("id") Long id) throws Exception {
        activityRuleService.removeById(id);
        return JsonResponse.success(null);
    }


    /**
    * 描述：根据Id 更新
    *
    */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResponse updateActivityRule(ActivityRule  activityRule) throws Exception {
        activityRuleService.updateById(activityRule);
        return JsonResponse.success(null);
    }


    /**
    * 描述:创建ActivityRule
    *
    */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse create(ActivityRule  activityRule) throws Exception {
        activityRuleService.save(activityRule);
        return JsonResponse.success(null);
    }
}

