package com.lx.pk.client.result;

import com.lx.pk.core.constant.ErrorCodeEnum;

import java.io.Serializable;

/**
 * Created by chenjiang on 2018/12/14.
 */
public class BaseError implements Serializable {
    /**
     * 错误代码
     */
    private int code = 9999;
    /**
     * 错误消息
     */
    private String message;

    public BaseError(final String message) {
        this(-1, message);
    }

    public BaseError(final int code, final String message) {
        this.code = code;
        this.message = message;
    }

    public BaseError(ErrorCodeEnum codeEnum) {

        this.code = codeEnum.getCode();
        this.message = codeEnum.getMessage();
    }

    public BaseError(final String code, final String message) {
        this.code = Integer.valueOf(code);
        this.message = message;
    }

}
