package com.lx.pk.core.exception;

import com.lx.pk.core.constant.ErrorCodeEnum;

/**
 * Created by chenjiang on 2019/2/2
 * 全局自定义异常该异常作用于全局范围
 * 原则意义上来讲该异常只作用于web接口端
 */
public final class WebServiceException extends RuntimeException {
    private Integer code = ErrorCodeEnum.SYSTEM_ERROR.getCode();

    public WebServiceException() {
        super();
    }

    public WebServiceException(String message) {
        super(message);
    }

    public WebServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebServiceException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public WebServiceException(Throwable cause) {
        super(cause);
    }

    protected WebServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public Integer getCode() {
        return code;
    }
}
