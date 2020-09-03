package com.motor.common.paging;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
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
        this.data = new ArrayList<>();
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

    public int size(){
        return this.data == null ? 0: this.data.size();
    }

    public boolean isEmpty() {
        return data == null ? true: data.isEmpty();
    }

    public boolean contains(Object o) {
        return data.contains(o);
    }

    public Iterator<T> iterator() {
        return data.iterator();
    }

    public Object[] toArray() {
        return data.toArray();
    }

    public <T1> T1[] toArray(T1[] a) {
        return data.toArray(a);
    }

    public boolean add(T t) {
        return data.add(t);
    }

    public boolean remove(Object o) {
        return data.remove(o);
    }

    public boolean containsAll(Collection<?> c) {
        return data.containsAll(c);
    }

    public boolean addAll(Collection<? extends T> c) {
        return data.addAll(c);
    }

    public boolean removeAll(Collection<?> c) {
        return data.removeAll(c);
    }

    public boolean retainAll(Collection<?> c) {
        return data.retainAll(c);
    }

    public void clear() {
        data.clear();
    }

}
