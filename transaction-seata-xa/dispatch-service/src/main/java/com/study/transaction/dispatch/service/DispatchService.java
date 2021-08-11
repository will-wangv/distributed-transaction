package com.study.transaction.dispatch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.UUID;

/**
 * 调度相关操作
 *
 * @author will
 */
@Service
public class DispatchService implements IDispatchService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * 添加调度信息（此处仅往数据库增加一条数据）
     *
     * @param orderId 订单ID
     */
    @Transactional
    public void dispatch(String orderId) throws Exception {
        // 往数据库插入一条记录 调度系统数据库事务2
        System.err.println("我要生成运单，对应订单号：" + orderId);

        String id = UUID.randomUUID().toString();
        String sql = "insert into table_dispatch (dispatch_seq, order_id,dispatch_content) values (?,?, ?)";
        int update = jdbcTemplate.update(sql, id, orderId, "派送此订单");
        if (update != 1) {
            throw new SQLException("调度数据插入失败，原因[数据库操作]");
        }
    }
}
