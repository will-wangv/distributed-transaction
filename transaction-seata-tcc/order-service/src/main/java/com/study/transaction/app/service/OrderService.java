package com.study.transaction.app.service;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
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
    public void tryCreateOrder(JSONObject orderInfo) throws Exception {
        String orderId = orderInfo.getString("orderId");
        System.err.println("【try】我要创建订单:" + orderId);

        // 1. 订单信息 - 插入订单系统，订单数据库（事务-1）
        // TODO 订单状态 -- 过渡状态【临时 数据不可用】
        String sql = "insert into table_order (order_id, user_id, order_content, create_time) values (?, ?, ?,now())";
        // 1. 添加订单记录
        jdbcTemplate.update(sql, orderInfo.get("orderId"), orderInfo.get("userId"),
                orderInfo.get("orderContent"));
    }

    @Transactional
    public TwoPhaseResult confirmCreateOrder(BusinessActionContext actionContext) throws Exception {
        // TODO 检测数据是否存在
        Object orderId = ((JSONObject)actionContext.getActionContext("orderInfo")).getString("orderId");
        System.err.println("【confirm】我要创建订单:" + orderId);
        // TODO 修改为可用
        Map<String, Object> value = jdbcTemplate.queryForMap(
                "select * from table_order where order_id = ?", orderId);
        if (value == null || value.isEmpty()) {
            return new TwoPhaseResult(false, "提交失败");
        }
        return new TwoPhaseResult(true, "提交成功");
    }

    @Transactional
    public TwoPhaseResult cancelCreateOrder(BusinessActionContext actionContext) throws Exception {
        // 此处注意空回滚, 也就是之前的操作没弄完，就回滚了，这种情况，就可以理解为空回滚
        Object orderId = ((JSONObject)actionContext.getActionContext("orderInfo")).getString("orderId");
        System.err.println("【cancel】我要创建订单:" + orderId);
        // 根据业务逻辑，除了删除之外，你可以修改数据 状态 ---
        String sql = "delete FROM  table_order where order_id = ?";
        jdbcTemplate.update(sql, orderId);
        return new TwoPhaseResult(true, "回滚成功");
    }

}
