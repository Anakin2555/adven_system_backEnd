package com.example.mybatisplus.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.service.TokenService;
import com.example.mybatisplus.model.domain.Token;


/**
 *
 *  前端控制器
 *
 *
 * @author zyc&rgl
 * @since 2022-03-02
 * @version v1.0
 */
@Controller
@RequestMapping("/api/token")
public class TokenController {

    private final Logger logger = LoggerFactory.getLogger( TokenController.class );

    @Autowired
    private TokenService tokenService;

    /**
    * 描述：根据Id 查询
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getById(@PathVariable("id") Long id)throws Exception {
        Token  token =  tokenService.getById(id);
        return JsonResponse.success(token);
    }

    /**
    * 描述：根据Id删除
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse deleteById(@PathVariable("id") Long id) throws Exception {
        tokenService.removeById(id);
        return JsonResponse.success(null);
    }


    /**
    * 描述：根据Id 更新
    *
    */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResponse updateToken(Token  token) throws Exception {
        tokenService.updateById(token);
        return JsonResponse.success(null);
    }


    /**
    * 描述:创建Token
    *
    */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse create(Token  token) throws Exception {
        tokenService.save(token);
        return JsonResponse.success(null);
    }
}

