package com.lx.pk.client.common.post;

import com.lx.pk.client.common.result.LxPageResult;

import java.io.Serializable;


public class BasePost<T> implements Serializable {

    private LxPageResult<T> page;

    public LxPageResult<T> getPage() {
        return page;
    }

    public void setPage(LxPageResult<T> page) {
        this.page = page;
    }
}
