package com.motor.common.paging;

import java.io.Serializable;
import java.util.List;

public class Paging implements Serializable {

    private static final long serialVersionUID = -7793140542419978714L;
    private int pageNumber = 1;
    private int currentPage ;
    private int total;
    private int pageSize= 10;
    private List<String> orderBy;

    public Paging() {
    }

    public Paging(int currentPage, int pageSize, int total) {
        this.currentPage = currentPage;
        this.total = total;
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber <=0 ? 1 : pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber <=0 ? 1: pageNumber;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<String> getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(List<String> orderBy) {
        this.orderBy = orderBy;
    }
    /**
     */
    public int getPageCount(){
        return total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
    }

    public int offset(){
        return (this.getPageNumber() - 1) * this.getPageSize();
    }

    public int limit(){
        return this.getPageSize();
    }
}
