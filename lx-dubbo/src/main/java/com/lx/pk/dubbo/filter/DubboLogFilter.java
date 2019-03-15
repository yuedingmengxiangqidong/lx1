package com.lx.pk.dubbo.filter;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;

/**
 * Created by chenjiang on 2018/12/14.
 */
@Activate(group = {Constants.PROVIDER})
public class DubboLogFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        if (Boolean.TRUE.toString().equals(invocation.getAttachments().get(Constants.ASYNC_KEY))) {
            RpcContext.getContext().getAttachments().remove(Constants.ASYNC_KEY);
        }
        return invoker.invoke(invocation);
    }
}
