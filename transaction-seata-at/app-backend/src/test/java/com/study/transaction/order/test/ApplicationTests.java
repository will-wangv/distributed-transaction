package com.study.transaction.order.test;

import com.alibaba.fastjson.JSONObject;
import com.study.transaction.order.BackendApplication;
import com.study.transaction.order.service.BusinessService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BackendApplication.class)
public class ApplicationTests {
    @Before
    public void start() {
        System.out.println("开始测试##############");
    }

    @After
    public void finish() {
        System.out.println("结束##############");
    }

    @Autowired
    BusinessService businessService;

    @Test
    public void orderCreate() throws Exception {
        // 订单号生成
        String orderId = UUID.randomUUID().toString();
        JSONObject orderInfo = new JSONObject();
        orderInfo.put("orderId", orderId);
        orderInfo.put("userId", "will");
        orderInfo.put("orderContent", "买了沙县小吃");
        businessService.createOrder( orderInfo);
        System.out.println("订单创建成功");
    }

}
