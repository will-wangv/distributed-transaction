package com.study.transaction.dispatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
public class DispatchApplication {
    public static void main(String[] args) throws Exception {
        System.out.println("#分配外卖小哥的运单系统启动中...");
        SpringApplication.run(DispatchApplication.class, args);
    }
}
