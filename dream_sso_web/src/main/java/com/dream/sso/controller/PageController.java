package com.dream.sso.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class PageController {
    //用于访问注册和登录界面
    @RequestMapping("/page/{page}")
    public String page(@PathVariable String page, String redirect, Model model){
        //redirect并不是一定有的参数，所以判断一下
        if (StringUtils.isNotBlank(redirect)){
            model.addAttribute("redirect",redirect);
        }
        return page;
    }
}
