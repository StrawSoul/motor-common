package com.motor.common.paging;


/**
 * @author: zlj
 * @date: 2019-05-15 下午12:38
 * @description:
 */
public class PagingRequest {
    private Integer pageNumber = 1;
    private Integer pageSize = 10;

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Paging toPaging(){
        Paging paging = new Paging();
        paging.setPageNumber(pageNumber);
        paging.setPageSize(pageSize);
        return paging;
    }
    public static Paging defaultPaging(){
        Paging paging = new Paging();
        return paging;
    }
}
