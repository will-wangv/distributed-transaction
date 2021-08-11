package com.study.transaction.app.controller;

import com.study.transaction.app.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    @Autowired
    BusinessService businessService;

    @PostMapping("/order")
    public String order() throws Exception {
        businessService.createOrder(null);
        return "";
    }
}
