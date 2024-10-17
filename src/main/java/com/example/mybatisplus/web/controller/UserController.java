package com.example.mybatisplus.web.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.mybatisplus.model.domain.Token;
import com.example.mybatisplus.service.MailService;
import com.example.mybatisplus.service.TokenService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.service.UserService;
import com.example.mybatisplus.model.domain.User;

import java.util.Map;


/**
 * 前端控制器
 *
 * @author zyc&rgl
 * @version v1.0
 * @since 2022-02-25
 */
@Controller
@RequestMapping("/api/user")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private MailService mailService;
    @Autowired
    private TokenService tokenService;

    /**
     * 描述：根据Id 查询
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getById(@PathVariable("id") Long id) throws Exception {
        User user = userService.getById(id);
        return JsonResponse.success(user);
    }

    /**
     * 描述：根据Id删除
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse deleteById(@PathVariable("id") Long id) throws Exception {
        userService.removeById(id);
        return JsonResponse.success(null);
    }


    /**
     * 描述：根据Id 更新
     */
    @RequestMapping(value = "/alertPassword", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse updateUser(@RequestBody Map<String,String> map) throws Exception {


        User user = userService.getById(Long.parseLong(map.get("userId")));
        if (!user.getUserPassword().equals(map.get("oldPassword"))) {
            return JsonResponse.failure("密码错误");
        }
        user.setUserPassword(map.get("newPassword"));
        userService.updateById(user);
        return JsonResponse.success(null);
    }


    /**
     * 描述:创建User
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse create(User user) throws Exception {
        userService.save(user);
        return JsonResponse.success(null);
    }


    /**
     * 以下为接口设计代码
     *
     * author:zyc
     * ********************************************************************************************/


    /**
     * 描述:用户登录
     * <p>
     * 接口：/api/user/login
     * <p>
     * 参数：user（userAccount userPassword）
     * <p>
     * 返回值：成功查询返回{status:1, data: user , message}
     */

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse<User> login(@RequestParam("account") String userAccount, @RequestParam("password") String userPassword) {

        User user1 = userService.login(userAccount, userPassword);

//       返回用于 token 验证

        //判断用户存在
        if (user1 == null) {
            return JsonResponse.message(false, "fail");
        }
        user1.setToken(tokenService.getToken(user1));
        user1.setIsDeleted(null);
        user1.setUserPassword(null);
        return JsonResponse.success(user1, "success");
    }


    /**
     * 描述：用户通过账号与身份证号找回密码
     * <p>
     * 接口：/api/user/findPassword
     * <p>
     * 参数：user(userName,identifyNumber)
     * <p>
     * 返回体：success：Json success对象；fail：Json message（fail）
     */

    @RequestMapping(value = "/findPassword", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse findPassword(String userName, @RequestParam("id") String identifyNumber) {

        User user = userService.findPassword(userName, identifyNumber);

        if (user != null) {
            if (mailService.sendPassword(user)) {
                return JsonResponse.success(user.getEmail());
            } else {
                return JsonResponse.message(false, "fail");
            }
        }
        return JsonResponse.failure("fail");
    }



    @ResponseBody
    @RequestMapping("/viewMessage")
    public JsonResponse viewMessage(Long userId){
        userService.update(new UpdateWrapper<User>().set("message_number",0).eq("id",userId));
        return JsonResponse.success(null);
    }

    @ResponseBody
    @RequestMapping("/getMessage")
    public JsonResponse getMessage(Long userId){

        return JsonResponse.success(userService.getById(userId).getMessageNumber());
    }

}

