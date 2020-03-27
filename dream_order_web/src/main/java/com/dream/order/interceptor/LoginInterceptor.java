package com.dream.order.interceptor;

import com.dream.common.pojo.DreamResult;
import com.dream.common.util.CookieUtils;
import com.dream.pojo.TbUser;
import com.dream.sso.service.UserLoginService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//登录拦截器
public class LoginInterceptor implements HandlerInterceptor {

    @Value("${COOKIE_TOKEN_KEY}")
    private String COOKIE_TOKEN_KEY;

    @Value("${SSO_URL}")
    private String SSO_URL;

    @Autowired
    private UserLoginService userLoginService;

    //之前执行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //先从Cookie中获取token
        String token = CookieUtils.getCookieValue(request, COOKIE_TOKEN_KEY);
        /*
         * 如果没有token,直接跳转到sso的登录界面，而且还要把当前请求的url传递给sso系统
         * 当用户登录完成之后直接跳转到该路径
         * 需要在请求的url中添加一个回调地址--重定向
         * */
        if (StringUtils.isBlank(token)) {
            //跳转到sso的登录界面，并且把当前拦截的操作的url传过去，档登陆后，继续当前操作
            String url = SSO_URL + "/page/login?redirect=" + request.getRequestURL().toString(); //getRequestURL得到全部完整路径
            //重定向
            response.sendRedirect(url);
            //拦截
            return false;
        }
        //如果有token 需要调用sso服务，根据token查找到用户
        DreamResult result = userLoginService.getUserByToken(token);
        TbUser user = null;
        //如果查到了
        if (result != null && result.getStatus() == 200) { //==200成功
            user = (TbUser) result.getData();
        } else {
            //有token但是登录过期了--去登录
            String url = SSO_URL + "/page/login?redirect=" + request.getRequestURL().toString();
            //重定向
            response.sendRedirect(url);
            //拦截
            return false;
        }

        //把查到的用户绑定到request中，直接传到要处理的Controller中，要不然在Controller中还要调用service查一下
        request.setAttribute("user", user);
        return true;//已经登陆了 就放行
    }

    //之后执行
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    //环绕执行
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
