package com.study.transaction.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(value = {"classpath:dubbo-provider.xml"})
public class OrderApplication {

    public static void main(String[] args) throws Exception {
        System.out.println("推荐看一个蚂蚁金服的技术分享：https://tech.antfin.com/community/live/462/data/736");
        SpringApplication.run(OrderApplication.class, args);
    }
}
