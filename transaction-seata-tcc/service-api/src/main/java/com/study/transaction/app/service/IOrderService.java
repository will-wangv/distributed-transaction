package com.study.transaction.app.service;

import com.alibaba.fastjson.JSONObject;
import io.seata.rm.tcc.TwoPhaseResult;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

public interface IOrderService {
    // try - Prepare
    @TwoPhaseBusinessAction(name = "createOrder", commitMethod = "confirmCreateOrder", rollbackMethod = "cancelCreateOrder")
    public void tryCreateOrder(@BusinessActionContextParameter(paramName = "orderInfo") JSONObject orderInfo) throws Exception;

    // confirm - Commit
    public TwoPhaseResult confirmCreateOrder(BusinessActionContext actionContext) throws Exception;

    // cancel - Rollback
    public TwoPhaseResult cancelCreateOrder(BusinessActionContext actionContext) throws Exception;

}
