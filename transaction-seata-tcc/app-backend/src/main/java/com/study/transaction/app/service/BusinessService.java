package com.study.transaction.app.service;

import com.alibaba.fastjson.JSONObject;
import com.study.transaction.dispatch.service.IDispatchService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author will
 */
@Service
public class BusinessService {

    @Autowired
    IOrderService orderService;

    @Autowired
    IDispatchService dispatchService;

    /**
     * 下订单、派发运单
     */
    @GlobalTransactional(timeoutMills = 600000)
    public void createOrder(JSONObject orderInfo) throws Exception {
        orderService.tryCreateOrder(orderInfo);
        dispatchService.tryDispatch(orderInfo.getString("orderId"));
        int i = 1 / 0; // 人造异常
    }
}
