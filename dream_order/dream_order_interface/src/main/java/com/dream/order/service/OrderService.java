package com.dream.order.service;

import com.dream.common.pojo.DreamResult;
import com.dream.order.pojo.OrderInfo;

public interface OrderService {
    /**
     * 创建订单详情
     * @param orderInfo
     * @return
     */
    DreamResult createOrder(OrderInfo orderInfo);
}
