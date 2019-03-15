package com.lx.pk.core.exception;

import com.lx.pk.core.constant.ErrorCodeEnum;

/**
 * Created by chenjiang on 2018/12/14.
 * 该异常类为全局通用公共自定义异常处理类,
 * 原则意义上来讲只作用于Dubbo服务端
 */
public final class DubboServiceException extends RuntimeException {

    private Integer code = ErrorCodeEnum.SYSTEM_ERROR.getCode();

    public DubboServiceException() {
        super();
    }

    public DubboServiceException(String message) {
        super(message);
    }

    public DubboServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public DubboServiceException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public DubboServiceException(Throwable cause) {
        super(cause);
    }

    protected DubboServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public Integer getCode() {
        return code;
    }
}
