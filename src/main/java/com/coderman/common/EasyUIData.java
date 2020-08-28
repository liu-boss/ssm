package com.coderman.common;

import java.util.List;

/**
 * @Author zhangyukang
 * @Date 2020/8/14 20:29
 * @Version 1.0
 **/
public class EasyUIData<T> {
    private long total;
    private List<T> rows;

    public EasyUIData(long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
