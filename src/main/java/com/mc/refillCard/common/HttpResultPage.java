package com.mc.refillCard.common;

/**
 * @Author: MC
 * @Date2020-12-03
 */

public class HttpResultPage {

    /**
     * 总条数
     */
    private Integer total;
    /**
     *  分页数
     */
    private Integer page;
    /**
     * 页条数
     */
    private Integer size;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
