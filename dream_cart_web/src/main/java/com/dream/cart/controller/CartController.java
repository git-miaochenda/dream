package com.dream.cart.controller;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.fastjson.JSONArray;
import com.dream.common.pojo.DreamResult;
import com.dream.common.util.CookieUtils;
import com.dream.pojo.TbItem;
import com.dream.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController {

    @Autowired
    private ItemService itemService;

    @Value("${DREAM_CART_COOKIE_KEY}")
    private String DREAM_CART_COOKIE_KEY;

    @Value("${COOKIE_CART_EXPIRE}")
    private Integer COOKIE_CART_EXPIRE;

    /**
     * 删除购物车商品
     * @param itemId
     * @param response
     * @param request
     * @return
     */
    @RequestMapping("/cart/delete/{itemId}")
    public String deleteCartItem(@PathVariable Long itemId,HttpServletResponse response,HttpServletRequest request){
        //1、从cookie中获取商品列表
        List<TbItem> cartList = getCartList(request);
        //遍历查找商品
        for (TbItem item:cartList){
            if (item.getId()==itemId.longValue()){
                //从集合中删除
                cartList.remove(item);
                break;
            }
        }
        //重新把列表写入cookie
        try {
            CookieUtils.setCookie(request,response,DREAM_CART_COOKIE_KEY,JSON.json(cartList),COOKIE_CART_EXPIRE,true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //返回重定向到购物车界面
        return "redirect:/cart/cart.html"; //redirect：重定向 forward:转发
    }

    /**
     * 修改购物车商品数量
     * @param itemId
     * @param num
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/cart/update/num/{itemId}/{num}")
    @ResponseBody //重定向
    public DreamResult updateNum(@PathVariable Long itemId,@PathVariable Integer num,
                                 HttpServletRequest request,HttpServletResponse response){
        //1、从cookie中获取商品列表
        List<TbItem> cartList = getCartList(request);
        //遍历查找商品
        for (TbItem item:cartList){
            if (item.getId()==itemId.longValue()){
                //更改商品数量
                item.setNum(num);
                break;
            }
        }
        //重新把列表写入cookie
        try {
            CookieUtils.setCookie(request,response,DREAM_CART_COOKIE_KEY,JSON.json(cartList),COOKIE_CART_EXPIRE,true);//true转码
        } catch (IOException e) {
            e.printStackTrace();
        }
        return DreamResult.ok();
    }

    /**
     * 展示购物车商品列表
     * @param request
     * @return
     */
    @RequestMapping("/cart/cart")
    public String showCart(HttpServletRequest request){
        //从Cookie中获取商品列表
        List<TbItem> cartList = this.getCartList(request);
        request.setAttribute("cartList",cartList);
        return "cart";
    }

    /**
     * 添加购物车商品
     * @param itemId
     * @param num
     * @param response
     * @param request
     * @return
     */
    @RequestMapping("/cart/add/{itemId}")
    public String addCart(@PathVariable Long itemId, Integer num,
                          HttpServletResponse response, HttpServletRequest request){
        //先从Cookie中获取商品列表
        List<TbItem> cartList = this.getCartList(request);
        //判断购物车中是否有此商品 默认没有
        boolean flag=false;
        //不管是否为空都遍历
        for (TbItem item:cartList) {
            //两个类型都是包装类型Long，要比较相等 转成基本类型
            if (item.getId() == itemId.longValue()) {
                //有此商品，把原来的商品数量上加上添加的商品数量
                item.setNum(item.getNum()+num);
                //item的num字段原意是库存，这里要放到购物车中，num的字段临时做购买数量来看待
                flag=true; //有商品了
                break;
            }
        }
        //如果购物车中没有商品，新添加到购物车中
        if (!flag){
            //1、先根据itemId查找到商品信息
            TbItem tbItem = itemService.selectByKey(itemId);
            //把库存的字段num作为购买数量使用
            tbItem.setNum(num);
            //取第一张图片显示
            String img = tbItem.getImage();
            if (StringUtils.isNotBlank(img)){//取图片中的第一张
                tbItem.setImage(img.split(",")[0]);
            }
            //加入到购物车--商品列表
            cartList.add(tbItem);
        }
        //把购物车写到cookie

        try {
            CookieUtils.setCookie(request,response,DREAM_CART_COOKIE_KEY, JSON.json(cartList),COOKIE_CART_EXPIRE,true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //返回成功界面
        return "cartSuccess";
    }

    //从cookie中获取商品列表
    private List<TbItem> getCartList(HttpServletRequest request) {
        //从cookie中取购物车列表
        String cookieValueJson = CookieUtils.getCookieValue(request, DREAM_CART_COOKIE_KEY, true);//isDecoder是否转码
        //判断是否有商品列表
        if (StringUtils.isBlank(cookieValueJson)){ //isBlank没有
            //没有返回一个空集合
            return new ArrayList<>();
        }
        //如果有，把json数据转换为一个集合
        List<TbItem> tbItems = JSONArray.parseArray(cookieValueJson, TbItem.class);
        return tbItems;
    }
}
