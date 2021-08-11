package com.study.transaction.order.controller;

import com.study.transaction.order.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/biz/")
public class BusinessController {
    @Autowired
    BusinessService businessService;

    @PostMapping("/order")
    public String order() throws Exception {
        businessService.createOrder(null);
        return "";
    }
}
