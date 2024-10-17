package com.example.mybatisplus.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.service.RegionService;
import com.example.mybatisplus.model.domain.Region;


/**
 *
 *  前端控制器
 *
 *
 * @author zyc&rgl
 * @since 2022-02-25
 * @version v1.0
 */
@Controller
@RequestMapping("/api/region")
public class RegionController {

    private final Logger logger = LoggerFactory.getLogger( RegionController.class );

    @Autowired
    private RegionService regionService;

    /**
    * 描述：根据Id 查询
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getById(@PathVariable("id") Long id)throws Exception {
        Region  region =  regionService.getById(id);
        return JsonResponse.success(region);
    }

    /**
    * 描述：根据Id删除
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse deleteById(@PathVariable("id") Long id) throws Exception {
        regionService.removeById(id);
        return JsonResponse.success(null);
    }


    /**
    * 描述：根据Id 更新
    *
    */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResponse updateRegion(Region  region) throws Exception {
        regionService.updateById(region);
        return JsonResponse.success(null);
    }


    /**
    * 描述:创建Region
    *
    */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse create(Region  region) throws Exception {
        regionService.save(region);
        return JsonResponse.success(null);
    }
}

