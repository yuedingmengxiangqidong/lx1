package com.lx.pk.dal.entity;


import com.lx.pk.client.common.result.LxPageResult;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体基类
 */
public abstract class BaseEntity implements Serializable {

    private Long id;
    private Long version = 200L;
    private Date createDate;
    private Date updateDate;

    protected LxPageResult page;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public LxPageResult getPage() {
        return page;
    }

    public void setPage(LxPageResult page) {
        this.page = page;
    }
}
