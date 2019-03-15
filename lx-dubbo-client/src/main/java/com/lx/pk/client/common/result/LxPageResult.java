package com.lx.pk.client.common.result;

import java.io.Serializable;
import java.util.List;


public class LxPageResult<T> implements Serializable {


    private final static int defaultPageSize = 15;//每页默认条数

    private int totalPage = 0;//总页码
    private int totalRows = 0;//总记录数
    private int pageNum = 1;//当前页码
    private int pageSize = 15;//每页记录数
    private List<T> list = null;//数据列表
    private String sql = null;//查询语句

    public int getTotalPage() {
        return (int) Math.ceil(this.totalRows / this.pageSize) + (this.totalRows % this.pageSize > 0 ? 1 : 0);
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
        if (pageSize <= 0) {
            this.pageSize = this.defaultPageSize;
        }
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}
