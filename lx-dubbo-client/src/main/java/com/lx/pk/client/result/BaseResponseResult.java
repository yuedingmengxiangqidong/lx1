package com.lx.pk.client.result;

import java.io.Serializable;

/**
 * Created by chenjiang on 2018/12/14.
 */
public class BaseResponseResult<T> implements Serializable {
    private T data;
    private BaseError error;

    public boolean isSuccess() {

        if (this.error == null) {
            return true;
        } else {
            return false;
        }

    }

    public BaseError getError() {
        return error;
    }

    public BaseResponseResult<T> setError(BaseError error) {
        this.error = error;
        return this;
    }

    public BaseResponseResult<T> addError(BaseError error) {
        this.error = error;
        return this;
    }

    public BaseResponseResult<T> setError(String errorMsg) {
        this.error = new BaseError(errorMsg);
        return this;
    }

    public T getData() {
        return data;
    }

    public BaseResponseResult<T> setData(T data) {
        this.data = data;
        return this;
    }
}
