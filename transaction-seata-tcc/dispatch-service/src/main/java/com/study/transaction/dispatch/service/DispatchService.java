package com.study.transaction.dispatch.service;

import io.seata.rm.tcc.TwoPhaseResult;
import io.seata.rm.tcc.api.BusinessActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Map;
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
    public void tryDispatch(String orderId) throws Exception {
        // 往数据库插入一条记录 调度系统数据库事务2
        System.err.println("【try】我要生成运单，对应订单号：" + orderId);

        String id = UUID.randomUUID().toString();
        String sql = "insert into table_dispatch (dispatch_seq, order_id,dispatch_content) values (?,?, ?)";
        int update = jdbcTemplate.update(sql, id, orderId, "派送此订单");
        if (update != 1) {
            throw new SQLException("调度数据插入失败，原因[数据库操作]");
        }
    }

    @Transactional
    public TwoPhaseResult confirmDispatch(BusinessActionContext actionContext) throws Exception {
        // TODO 检测数据是否存在
        Object orderId = actionContext.getActionContext("orderId");
        System.err.println("【confirm】我要生成运单，对应订单号：" + orderId);

        Map<String, Object> value = jdbcTemplate.queryForMap(
                "select * from table_dispatch where order_id = ?", orderId);
        if (value == null || value.isEmpty()) {
            return new TwoPhaseResult(false, "提交失败");
        }
        return new TwoPhaseResult(true, "提交成功");
    }

    @Transactional
    public TwoPhaseResult cancelDispatch(BusinessActionContext actionContext) throws Exception {
        Object orderId = actionContext.getActionContext("orderId");
        System.err.println("【cancel】我要生成运单，对应订单号：" + orderId);

        String sql = "delete FROM table_dispatch where order_id = ?";
        jdbcTemplate.update(sql, orderId);
        return new TwoPhaseResult(true, "回滚成功");
    }
}
