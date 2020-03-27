package com.dream.sso.controller;

import com.dream.common.pojo.DreamResult;
import com.dream.pojo.TbUser;
import com.dream.sso.service.UserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserRegisterController {

    @Autowired
    private UserRegisterService userRegisterService;

    /**
     * 登录 限定请求方式只能是GET
     * @param param
     * @param type
     * @return
     */
    @RequestMapping(value = "/user/check/{param}/{type}",method = RequestMethod.GET)
    @ResponseBody
    public DreamResult userCheck( @PathVariable String param,@PathVariable Integer type){

        return userRegisterService.checkUserInfo(param, type);
    }

    /**
     * 注册
     * @param tbUser
     * @return
     */
    @RequestMapping(value = "/user/register",method = RequestMethod.POST)
    @ResponseBody
    public DreamResult userRegister(TbUser tbUser){
        return userRegisterService.userRegister(tbUser);
    }
}
