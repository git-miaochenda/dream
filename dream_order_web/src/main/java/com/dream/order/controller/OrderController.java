package com.dream.order.controller;

import com.alibaba.fastjson.JSONArray;
import com.dream.common.pojo.DreamResult;
import com.dream.common.util.CookieUtils;
import com.dream.order.pojo.OrderInfo;
import com.dream.order.service.OrderService;
import com.dream.pojo.TbItem;
import com.dream.pojo.TbUser;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderController {
    @Value("${COOKIE_CART_KEY}")
    private String COOKIE_CART_KEY;

    @Autowired
    private OrderService orderService;

    //订单成功详情展示
    @RequestMapping(value = "/order/create",method = RequestMethod.POST)
    public String createOrder(OrderInfo orderInfo,HttpServletRequest request){
        //取用户id
        TbUser user = (TbUser) request.getAttribute("user");
        orderInfo.setUserId(user.getId());
        orderInfo.setBuyerNick(user.getUsername());
        //调用服务生成订单
        DreamResult result = orderService.createOrder(orderInfo);
        //取订单号传递给页面
        request.setAttribute("orderId",result.getData().toString());
        //一共多少钱，实付金额
        request.setAttribute("payment",orderInfo.getPayment());
        //预计送达时间
        //要把当前时间加上配送时间，要使用Calendar来进行时间计算，太麻烦
        //使用joda插件来完成
        DateTime dateTime = new DateTime();
        //预计三天后到达
         dateTime = dateTime.plusDays(3);
         request.setAttribute("date",dateTime.toString("yyyy-MM-dd"));
         return "success";
    }

    @RequestMapping("/order/order-cart")
    public String showOrderCart(HttpServletRequest request){
        //从购物车中获取商品列表
        List<TbItem> cartList= this.getCartList(request);
        //取用户id
        TbUser user = (TbUser) request.getAttribute("user");
        //正常来说应该根据用户id来查询收货地址列表--收货地址，在调用业务层根据
        // user_id查询tb_order_shipping表，绑定转发，这里使用静态数据,
        // 动态的前端写好了，后端自己写
        request.setAttribute("cartList",cartList);
        return "order-cart";
    }

    //从cookie中获取商品列表
    private List<TbItem> getCartList(HttpServletRequest request) {
        //从cookie中取购物车列表
        String cookieValueJson = CookieUtils.getCookieValue(request, COOKIE_CART_KEY, true);
        //判断是否有商品列表
        if (StringUtils.isBlank(cookieValueJson)){
            //返回一个空集合
            return new ArrayList<>();
        }
        //如果有，把json数据转换为一个集合
        List<TbItem> tbItems = JSONArray.parseArray(cookieValueJson, TbItem.class);
        return tbItems;
    }
}
