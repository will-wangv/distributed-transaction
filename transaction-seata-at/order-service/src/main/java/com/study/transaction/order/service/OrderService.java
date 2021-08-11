package com.study.transaction.order.service;

import com.alibaba.fastjson.JSONObject;
import io.seata.rm.tcc.TwoPhaseResult;
import io.seata.rm.tcc.api.BusinessActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @author will
 */
@Service
public class OrderService implements IOrderService {

    @Autowired
    JdbcTemplate jdbcTemplate; // spring提供的

    /**
     * 创建订单
     */
    @Transactional
    public void createOrder(JSONObject orderInfo) throws Exception {
        String orderId = orderInfo.getString("orderId");
        System.err.println("我要创建订单:" + orderId);

        // 1. 订单信息 - 插入订单系统，订单数据库（事务-1）
        String sql = "insert into table_order (order_id, user_id, order_content, create_time) values (?, ?, ?,now())";
        jdbcTemplate.update(sql, orderInfo.get("orderId"), orderInfo.get("userId"),
                orderInfo.get("orderContent"));
    }
}
