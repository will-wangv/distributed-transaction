package com.study.transaction.order.schedule;

import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;

// 兜底方案 --- 计划任务去检查~~针对长时间没有发送成功的消息~~然后重试
//@Component
public class ScheduleTask {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 3000) // spring 简单示例 定时重发
    public void reportCurrentTime() {
        System.out.println("当前时间：" + dateFormat.format(new Date()));
        // TODO 检查消息记录表,根据数据库 记录状态
        // mqservice.send() 重发
        // 如果持续异常： 不是系统自动能够解决的，需要监控预警,人工干预
    }
}