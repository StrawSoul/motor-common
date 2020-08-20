package com.motor.common.paging;

import java.io.Serializable;
import java.util.List;

public class PageList<T> implements Serializable {
    private static final long serialVersionUID = 5575965586438413458L;
    private Paging paging;
    private List<T> data;

    public PageList(Paging paging, List<T> data) {
        this.paging = paging;
        this.data = data;
    }

    public PageList() {
    }

    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Integer size(){
        return this.data == null ? 0: this.data.size();
    }

}
