package com.icbc.common;

import java.io.Serializable;
import java.util.List;

//分页查询结果封装类
public class PageResult implements Serializable {
    private static final long serialVersionUID = -8315881228799534049L;

    //总页数
    private int pages;

    //当前页数据
    private List<?> rows;

    public PageResult(int pages, List<?> rows) {
        this.pages = pages;
        this.rows = rows;
    }

    public PageResult() {
    }


    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }


}
