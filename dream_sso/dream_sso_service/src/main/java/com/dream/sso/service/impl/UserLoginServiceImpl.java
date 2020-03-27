package com.dream.sso.service.impl;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.ParseException;
import com.dream.common.pojo.DreamResult;
import com.dream.mapper.TbUserMapper;
import com.dream.pojo.TbUser;
import com.dream.pojo.TbUserExample;
import com.dream.sso.jedis.JedisClient;
import com.dream.sso.service.UserLoginService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class UserLoginServiceImpl implements UserLoginService {

    @Autowired
    private TbUserMapper tbUserMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${USER_INFO}")
    private String USER_INFO;

    @Value("${EXPIRE_TIME}")
    private Integer EXPIRE_TIME;

    @Override
    public DreamResult login(String username, String password) {
        //1、效验用户名和密码是否为空
        if (StringUtils.isEmpty(username)||StringUtils.isEmpty(password)){
            return DreamResult.build(400,"用户名或密码错误！");
        }
        //2、在校验用户名
        TbUserExample tbUserExample = new TbUserExample();
        TbUserExample.Criteria criteria = tbUserExample.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<TbUser> users = tbUserMapper.selectByExample(tbUserExample);
        if (users==null || users.size()==0){
            return DreamResult.build(400,"用户名或密码错误!");
        }
        //3、在校验户名唯一，如密码--用果查到了，肯定集合中就1条数据
        TbUser tbUser = users.get(0);
        //把密码加密之后进行比较
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!md5Password.equals(tbUser.getPassword())){
            return DreamResult.build(400,"用户名或密码错误!");
        }
        //4、如果校验成功--账号和密码正确，登陆成功
        //需要把登陆信息保存到redis中作为模拟的session--每一个用户的登陆的token都必须为宜切随机
        //4、1随机生成一个token
        String token = UUID.randomUUID().toString();
        //4、2把要保持的用户信息的密码设置为null
        tbUser.setPassword(null);
        //4、3存放用户数据到redis中，使用redis的客户端，为了方便管理，加一个前缀USER_INFO:token
//        jedisClient.set(USER_INFO+":"+token, JSONUtils.toJSONString(tbUser));
        try {
            jedisClient.set(USER_INFO+":"+token, JSON.json(tbUser));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //设置过期时间
        jedisClient.expire(USER_INFO+":"+token,EXPIRE_TIME);
        //单点登录中，其他系统如果要从redis中查看是否用登陆信息，
        // 需要一个此token sessionId->放到cookie中--在表现层中设置

        return DreamResult.ok(token);
    }

    @Override
    public DreamResult getUserByToken(String token) {
        //1、直接根据token从redis中进行查询
        String jsonString = jedisClient.get(USER_INFO + ":" + token);
        //2、判断是否查询到
        if (StringUtils.isNotBlank(jsonString)){
            //3、每一次重新访问首页，实际上都是重新把登录状态重新计时
            jedisClient.expire(USER_INFO+":"+token,EXPIRE_TIME);
            //把查询到的对象返回
            try {
                TbUser user = JSON.parse(jsonString, TbUser.class);
                //返回对象
                return DreamResult.ok(user);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return DreamResult.build(400,"用户已过期!");
    }
}
