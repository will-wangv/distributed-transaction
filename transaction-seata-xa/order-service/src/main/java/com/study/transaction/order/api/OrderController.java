package com.study.transaction.order.api;

import com.alibaba.fastjson.JSONObject;
import com.study.transaction.order.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * http API
 *
 * @author will
 */
@RestController
@RequestMapping("/order-api")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    // 下订单后
    @PostMapping("/order")
    public String dispatch(@RequestBody String orderInfo) throws Exception {
        orderService.createOrder(JSONObject.parseObject(orderInfo));
        return "ok";
    }

}
