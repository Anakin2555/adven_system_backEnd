package com.example.mybatisplus.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.service.HighSchoolService;
import com.example.mybatisplus.model.domain.HighSchool;


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
@RequestMapping("/api/highSchool")
public class HighSchoolController {

    private final Logger logger = LoggerFactory.getLogger( HighSchoolController.class );

    @Autowired
    private HighSchoolService highSchoolService;

    /**
    * 描述：根据Id 查询
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getById(@PathVariable("id") Long id)throws Exception {
        HighSchool  highSchool =  highSchoolService.getById(id);
        return JsonResponse.success(highSchool);
    }

    /**
    * 描述：根据Id删除
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse deleteById(@PathVariable("id") Long id) throws Exception {
        highSchoolService.removeById(id);
        return JsonResponse.success(null);
    }


    /**
    * 描述：根据Id 更新
    *
    */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResponse updateHighSchool(HighSchool  highSchool) throws Exception {
        highSchoolService.updateById(highSchool);
        return JsonResponse.success(null);
    }


    /**
    * 描述:创建HighSchool
    *
    */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse create(HighSchool  highSchool) throws Exception {
        highSchoolService.save(highSchool);
        return JsonResponse.success(null);
    }
}

