package com.example.mybatisplus.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.example.mybatisplus.model.domain.Activity;
import com.example.mybatisplus.model.domain.HighSchool;
import com.example.mybatisplus.model.domain.Region;
import com.example.mybatisplus.service.HighSchoolService;
import com.example.mybatisplus.service.RegionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.service.ActivityRegionService;
import com.example.mybatisplus.model.domain.ActivityRegion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
@RequestMapping("/api/activityRegion")
public class ActivityRegionController {

    private final Logger logger = LoggerFactory.getLogger( ActivityRegionController.class );

    @Autowired
    private ActivityRegionService activityRegionService;
    @Autowired
    private RegionService regionService;
    @Autowired
    private HighSchoolService highSchoolService;

    /**
    * 描述：根据Id 查询
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getById(@PathVariable("id") Long id)throws Exception {
        ActivityRegion  activityRegion =  activityRegionService.getById(id);
        return JsonResponse.success(activityRegion);
    }

    /**
    * 描述：根据Id删除
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse deleteById(@PathVariable("id") Long id) throws Exception {
        activityRegionService.removeById(id);
        return JsonResponse.success(null);
    }

    /**
    * 描述：根据Id 更新
    *
    */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResponse updateActivityRegion(ActivityRegion  activityRegion) throws Exception {
        activityRegionService.updateById(activityRegion);
        return JsonResponse.success(null);
    }

    /**
    * 描述:创建ActivityRegion
    *
    */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse create(ActivityRegion  activityRegion) throws Exception {
        activityRegionService.save(activityRegion);
        return JsonResponse.success(null);
    }

    /**
     * ****************************************************************************
     * 以下为接口代码
     * @author zyc&rgl
     *
     */

    /**
     * 描述: 获取剩余名额
     *
     * 接口：/getRemainNumber
     *
     * 类型：get
     *
     * 参数：活动id 地区中文
     *
     * 返回：JsonResponse <map<teacher:number,student:number>>
     */
    @RequestMapping("/getRemainNumber")
    @ResponseBody
    public JsonResponse getRemainNumber(Long activityId,String regionName){
        QueryWrapper<Region> wrapper = new QueryWrapper<>();
        wrapper.eq("region_name",regionName);
        Long regionId = regionService.getOne(wrapper).getId();
        QueryWrapper<ActivityRegion> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("activity_id",activityId).eq("region_id",regionId);
        ActivityRegion activityRegion = activityRegionService.getOne(wrapper1);
        Map<String,Integer> map = new HashMap<>();
        map.put("teacherRemain",activityRegion.getMaxTeacher()-activityRegion.getCurTeacher());
        map.put("studentRemain",activityRegion.getMaxStudent()-activityRegion.getCurStudent());
        return JsonResponse.success(map);
    }

    /**
     * 描述: 获取区域内的学校
     *
     * 接口：/getHighSchool
     *
     * 类型：get
     *
     * 参数：地区中文
     *
     * 返回：JsonResponse <list<school>>
     */
    @RequestMapping(value = "/getHighSchool",method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getHighSchool(String regionName){
        QueryWrapper<Region> wrapper=new QueryWrapper<>();
        wrapper.eq("region_name",regionName);
        Long regionId = regionService.getOne(wrapper).getId();
        QueryWrapper<HighSchool> wrapper1=new QueryWrapper<>();
        wrapper1.eq("region_id",regionId);
        List<HighSchool> highSchoolList=highSchoolService.list(wrapper1);
        return JsonResponse.success(highSchoolList);
    }
}

