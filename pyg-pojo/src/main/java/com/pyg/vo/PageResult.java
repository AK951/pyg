package com.pyg.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Description: 分页包装类
 *
 * @author AK
 * @date 2018/8/6 21:12
 * @since 1.0.0
 */
public class PageResult implements Serializable {
    private Long total;
    private List<?> rows;

    public PageResult() {
    }

    public PageResult(Long total, List<?> rows) {
        this.total = total;
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }
}