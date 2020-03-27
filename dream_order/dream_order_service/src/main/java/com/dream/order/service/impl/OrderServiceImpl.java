package com.dream.order.service.impl;

import com.dream.common.pojo.DreamResult;
import com.dream.mapper.TbOrderItemMapper;
import com.dream.mapper.TbOrderMapper;
import com.dream.mapper.TbOrderShippingMapper;
import com.dream.order.jedis.JedisClient;
import com.dream.order.pojo.OrderInfo;
import com.dream.order.service.OrderService;
import com.dream.pojo.TbOrderItem;
import com.dream.pojo.TbOrderShipping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class OrderServiceImpl implements OrderService {
    //三表查询
    @Autowired
    private TbOrderMapper tbOrderMapper;

    @Autowired
    private TbOrderItemMapper tbOrderItemMapper;

    @Autowired
    private TbOrderShippingMapper tbOrderShippingMapper;

    @Autowired //用来生成订单号
    private JedisClient jedisClient;

    @Value("${ORDER_GEN_KEY}") //订单号
    private String ORDER_GEN_KEY;
    @Value("${ORDER_DETAIL_GEN_KEY}")
    private String ORDER_DETAIL_GEN_KEY;
    @Value("${ORDER_ID_INIT}") //订单号初始值
    private String ORDER_ID_INIT;

    @Override
    public DreamResult createOrder(OrderInfo orderInfo) {
        //生成一个订单号，使用redis的incr来生成ORDER_GET_KEY对应的订单号
        //判断redis中生成的订单好的key是否存在
        if (!jedisClient.exists(ORDER_GEN_KEY)){
            //如果不存在，要设置初始值
            jedisClient.set(ORDER_GEN_KEY,ORDER_ID_INIT);
        }
        //自增一个订单号
        String orderId = jedisClient.incr(ORDER_GEN_KEY).toString();
        //向订单表中插入数据
        orderInfo.setOrderId(orderId);
        //包邮
        orderInfo.setPostFee("0");
        //订单状态--刚创建是未付款，6个情况
        orderInfo.setStatus(1);
        //创建和修改时间
        orderInfo.setCreateTime(new Date());
        orderInfo.setUpdateTime(orderInfo.getCreateTime());
        //插入数据
        tbOrderMapper.insert(orderInfo);
        //想订单明细中插入数据
        List<TbOrderItem> orderItems = orderInfo.getOrderItems();
        for (TbOrderItem orderItem:orderItems) {
            //生成一个订单明细表的主键
            Long orderDetailId = jedisClient.incr(ORDER_DETAIL_GEN_KEY);
            orderItem.setId(orderDetailId.toString());
            //设置订单的id
            orderItem.setOrderId(orderId);
            //插入数据
            tbOrderItemMapper.insert(orderItem);
        }
        //想订单物流表中插入信息
        TbOrderShipping orderShipping = orderInfo.getOrderShipping();
        //设置订单id
        orderShipping.setOrderId(orderId);
        //设置时间
        orderShipping.setCreated(new Date());
        orderShipping.setUpdated(orderShipping.getCreated());
        //插入
        tbOrderShippingMapper.insert(orderShipping);
        //把订单号返回
        return DreamResult.ok(orderId);
    }
}
