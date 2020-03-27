package com.dream.sso.service;

import com.dream.common.pojo.DreamResult;
import com.dream.pojo.TbUser;

public interface UserRegisterService {

    /**
     * 检测账号是否可用
     * @param param 校验数据
     * @param type 类型 1、username 2、phone 3、email
     * @return
     */
    DreamResult checkUserInfo(String param,Integer type);

    /**
     * 注册
     * @param user 注册数据
     * @return
     */
    DreamResult userRegister(TbUser user);
}
