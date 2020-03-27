package com.dream.sso.service;

import com.dream.common.pojo.DreamResult;

public interface UserLoginService {
    /**
     * 现在所有的网站都是首选使用手机号进行登陆，要发送验证码
     * 但是所有的网站都必须支持账号和密码登录
     * @param username
     * @param password
     * @return
     */
    DreamResult login(String username,String password);

    /**
     * 登录通过token获取用户信息
     * @param token
     * @return
     */
    DreamResult getUserByToken(String token);
}
