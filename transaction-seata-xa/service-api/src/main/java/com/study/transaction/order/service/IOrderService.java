package com.study.transaction.order.service;

import com.alibaba.fastjson.JSONObject;

public interface IOrderService {
    public void createOrder(JSONObject orderInfo) throws Exception;
}
