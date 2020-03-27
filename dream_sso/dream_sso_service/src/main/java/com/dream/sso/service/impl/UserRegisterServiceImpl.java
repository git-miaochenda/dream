package com.dream.sso.service.impl;

import com.dream.common.pojo.DreamResult;
import com.dream.mapper.TbUserMapper;
import com.dream.pojo.TbUser;
import com.dream.pojo.TbUserExample;
import com.dream.sso.service.UserRegisterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

@Service
public class UserRegisterServiceImpl implements UserRegisterService {

    @Autowired
    private TbUserMapper tbUserMapper;

    @Override
    public DreamResult checkUserInfo(String param, Integer type) {

        //1、type的不同需要设置不同的条件
        TbUserExample tbUserExample = new TbUserExample();
        TbUserExample.Criteria criteria = tbUserExample.createCriteria();
        //2、设置查询参数
        if (type == 1) { //检查username
            //username不能为空--前端已经表单验证过了，后面也继续做处理
            if (StringUtils.isEmpty(param)) { //如果参数是空
                return DreamResult.ok(false);
            }
            //设置参数
            criteria.andUsernameEqualTo(param);
        } else if (type == 2) { //检查phone
            criteria.andPhoneEqualTo(param);
        } else if (type == 3) { //检查email
            criteria.andEmailEqualTo(param);
        } else {
            //fei 1 2 3 非法
            return DreamResult.build(400, "非法参数");
        }
        //3、执行查询
        List<TbUser> tbUsers = tbUserMapper.selectByExample(tbUserExample);
        //4、如果查询到了数据--注册账号不可用，已经存在 false
        if (tbUsers != null && tbUsers.size() > 0) {
            return DreamResult.ok(false);
        }
        //5、如果没有查询到数据，可用
        return DreamResult.ok(true);
    }

    @Override
    public DreamResult userRegister(TbUser user) {
        //1、验证数据
        //1、1 用户名和密码不能为空
        if (StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword())) {
            return DreamResult.build(400, "注册失败，请效验数据后再提交数据!");
        }
        //1.2效验用户名是否已经被注册
        DreamResult dreamResult = checkUserInfo(user.getUsername(), 1);
        if (!(boolean) dreamResult.getData()) { //false代表不可用
            return DreamResult.build(400, "注册失败，请效验数据后再提交!");
        }
        //1.3手机号码是否被注册
        if (StringUtils.isNotBlank(user.getPhone())) {
            DreamResult result = checkUserInfo(user.getPhone(), 2);
            if (!(boolean) result.getData()) {// false表示不可用
                return DreamResult.build(400, "注册失败，请效验数据后再提交数据!");
            }
        }
        //1、4邮箱是否被注册
        if (StringUtils.isNotBlank(user.getEmail())) {
            DreamResult result = checkUserInfo(user.getEmail(), 3);
            if (!(boolean) result.getData()) {//false代表不可用
                return DreamResult.build(400, "注册失败，请效验数据后再提交数据！");
            }
        }
        //2、如果效验通过，补全其他属性 id是自增的 还有两个创建时间和修改时间
        user.setCreated(new Date());
        user.setUpdated(user.getCreated());
        //3、对密码进行加密，使用加密工具
        String md5Password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(md5Password);
        //4、插入数据
        tbUserMapper.insertSelective(user);
        return DreamResult.ok();

    }
}
