package com.study.transaction.dispatch.service;

import io.seata.rm.tcc.TwoPhaseResult;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

public interface IDispatchService {
    // try - Prepare
    @TwoPhaseBusinessAction(name = "dispatch", commitMethod = "confirmDispatch", rollbackMethod = "cancelDispatch")
    public void tryDispatch(@BusinessActionContextParameter(paramName = "orderId") String orderId) throws Exception;

    // confirm - Commit
    public TwoPhaseResult confirmDispatch(BusinessActionContext actionContext) throws Exception;

    // cancel - Rollback
    public TwoPhaseResult cancelDispatch(BusinessActionContext actionContext) throws Exception;
}
